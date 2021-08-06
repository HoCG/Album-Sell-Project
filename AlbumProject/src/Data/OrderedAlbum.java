package Data;

import java.sql.*;
import java.util.Calendar;

import Database.Database;
import MGR.Manageable;
import MGR.Manager_Of_SQL_Data;

public class OrderedAlbum extends Manager_Of_SQL_Data implements Manageable { // 주문내역. 외부 업체로 인해 납품을 받을경우역시 여기에 내역이 담긴다.
	// Manageable에 업데이트, 딜리트 공통 클래스 만들어두고 구현하기.
	private int oid;
	private String UserId;
	private int count;
	private Date Date;
	private String AlbumName;
	private int AllPay;
	private Album album;
	private User user;

	@Override
	public String getName() {
		return UserId;
	}

	int getCount() {
		return count;
	}

	public Date getDate() {
		return Date;
	}

	public String getAlbumName() {
		return AlbumName;
	}

	int getAllPay() {
		return AllPay;
	}

	// 생성자 선언

	// 데이터 불러올때 쓰는 생성자
	public OrderedAlbum(int id, String n, int cnt, Date Dt, String AN, int AP) {
		this.oid = id;
		this.UserId = n;
		this.user = (User) AlbumShop.UserMgr.find(this.UserId);
		this.count = cnt;
		this.Date = Dt;
		this.AlbumName = AN;
		this.album = (Album) AlbumShop.AlbumMgr.find(this.AlbumName);
		this.AllPay = AP;
	}

	// 데이터 생성시에 사용하는 생성자.
	public OrderedAlbum(String n, int cnt, int AP, String AN) {
		this.UserId = n;
		this.user = (User) AlbumShop.UserMgr.find(this.UserId);
		this.count = cnt;
		this.AlbumName = AN;
		this.AllPay = AP;
		this.album = (Album) AlbumShop.AlbumMgr.find(this.AlbumName);
	}

	public OrderedAlbum() {
	}

	@Override
	public void print() {
		System.out.printf("%d %s %s %s %d\n", oid, UserId, Date, AlbumName, AllPay);
	}

	public String[] getOrderedAlbumArray() {
		String[] array = { this.UserId, this.Date.toString(), this.AlbumName, Integer.toString(this.count),Integer.toString(this.AllPay) };
		return array;
	}

	@Override
	public boolean matches(String kwd) {
		if (("" + Date).contains(kwd))
			return true;
		if (kwd.equals(UserId))
			return true;
		if (kwd.equals(AlbumName))
			return true;
		if (kwd.equals("" + AllPay))
			return true;
		return false;
	}

	@Override
	public void getDataForOid(int id) {
		OrderedAlbum c = getOrderedAlbum("SELECT * FROM orderedalbum WHERE oid ='" + id + "'");
		if (c != null) {
			this.oid = c.oid;
			this.UserId = c.UserId;
			this.user = (User) AlbumShop.UserMgr.find(this.UserId);
			this.count = c.count;
			this.Date = c.Date;
			this.AlbumName = c.AlbumName;
			this.album = (Album) AlbumShop.AlbumMgr.find(this.AlbumName);
			this.AllPay = c.AllPay;
		}
	}

	// 이건 어찌보면 실제로 앨범을 구입하는 행위라고 볼수 있다.
	@Override
	public Boolean createData() { // 앨범의 개수가 0개일때는 생성이 불가능 하도록 만들고, 앨범의 이름과 일치하지 않는다면 예약이 되지 않도록 진행.
		int oid = Database.getInstance().getId("SELECT oid FROM orderedalbum");
		Date date = new Date(Calendar.getInstance().getTimeInMillis());
		int NewCount = this.album.getCount() - this.count;
		int NewPoint = this.user.getPoint() + AllPay / 1000; // 저장될 포인트
		int NewSavePoint = this.user.getSavePoint() + AllPay / 1000; // 저장될 세이브포인트
		if (NewCount >= 0) {
			UseSQL_For_Data("INSERT INTO orderedalbum " + "VALUES ('" + oid + "','" + UserId + "','" + count + "','"
					+ date + "', '" + AlbumName + "', '" + AllPay + "')");

			// 구매시 앨범의 개수가 줄어들게 되므로
			UseSQL_For_Data("UPDATE album SET " + "count = '" + NewCount + "' WHERE name = '" + AlbumName + "'");

			// 포인트도 수정되도록 프로그램 구성해보기. 포인트 누적.
			UseSQL_For_Data("UPDATE users SET " + "savepoint = '" + NewSavePoint + "', point = '" + NewPoint + "' WHERE id = '" + UserId + "'");

			return true;
		} else {
			System.out.println("죄송하지만 앨범재고를 초과해서 구매하셨군요. 다시 진행해주세요.");
			return false;
		}
	}

	
	@Override
	public void UpdateData() {
		Date date = new Date(Calendar.getInstance().getTimeInMillis());
		UseSQL_For_Data("UPDATE orderedalbum SET " + "username = '" + this.UserId + "'" + ", date = '" + date
				+ "', albumname = '" + this.AlbumName + "', AllPay = '" + this.AllPay + "' WHERE oid = '" + this.oid
				+ "'");
	}

	@Override
	public void DeleteData() {
		UseSQL_For_Data("DELETE FROM orderedalbum WHERE oid = '" + this.oid + "'");
	}

	private OrderedAlbum getOrderedAlbum(String sql) {
		OrderedAlbum c = null;
		try {
			Statement stmt = Database.getInstance().getConnection().createStatement();
			ResultSet rset = stmt.executeQuery(sql);
			while (rset.next()) {
				int oid = rset.getInt(1);
				String name = rset.getString(2);
				int count = rset.getInt(3);
				Date date = rset.getDate(4);
				String albumname = rset.getString(5);
				int allpay = rset.getInt(6);
				c = new OrderedAlbum(oid, name, count, date, albumname, allpay);
			}
			rset.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
}

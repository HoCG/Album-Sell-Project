package Data;

import java.sql.*;
import java.util.Calendar;

import Database.Database;
import MGR.Manageable;
import MGR.Manager_Of_SQL_Data;

public class OrderedAlbum extends Manager_Of_SQL_Data implements Manageable { // �ֹ�����. �ܺ� ��ü�� ���� ��ǰ�� ������쿪�� ���⿡ ������ ����.
	// Manageable�� ������Ʈ, ����Ʈ ���� Ŭ���� �����ΰ� �����ϱ�.
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

	// ������ ����

	// ������ �ҷ��ö� ���� ������
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

	// ������ �����ÿ� ����ϴ� ������.
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

	// �̰� ����� ������ �ٹ��� �����ϴ� ������� ���� �ִ�.
	@Override
	public Boolean createData() { // �ٹ��� ������ 0���϶��� ������ �Ұ��� �ϵ��� �����, �ٹ��� �̸��� ��ġ���� �ʴ´ٸ� ������ ���� �ʵ��� ����.
		int oid = Database.getInstance().getId("SELECT oid FROM orderedalbum");
		Date date = new Date(Calendar.getInstance().getTimeInMillis());
		int NewCount = this.album.getCount() - this.count;
		int NewPoint = this.user.getPoint() + AllPay / 1000; // ����� ����Ʈ
		int NewSavePoint = this.user.getSavePoint() + AllPay / 1000; // ����� ���̺�����Ʈ
		if (NewCount >= 0) {
			UseSQL_For_Data("INSERT INTO orderedalbum " + "VALUES ('" + oid + "','" + UserId + "','" + count + "','"
					+ date + "', '" + AlbumName + "', '" + AllPay + "')");

			// ���Ž� �ٹ��� ������ �پ��� �ǹǷ�
			UseSQL_For_Data("UPDATE album SET " + "count = '" + NewCount + "' WHERE name = '" + AlbumName + "'");

			// ����Ʈ�� �����ǵ��� ���α׷� �����غ���. ����Ʈ ����.
			UseSQL_For_Data("UPDATE users SET " + "savepoint = '" + NewSavePoint + "', point = '" + NewPoint + "' WHERE id = '" + UserId + "'");

			return true;
		} else {
			System.out.println("�˼������� �ٹ���� �ʰ��ؼ� �����ϼ̱���. �ٽ� �������ּ���.");
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

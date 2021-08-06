package Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import Database.Database;
import MGR.Manageable;
import MGR.Manager_Of_SQL_Data;

public class Album extends Manager_Of_SQL_Data implements Manageable { // 앨범의 갯수를 추가 및 줄이는 과정이 요구. 구매와 환불, 입고와 출고에 따라
																		// 수정해가며 프로그램
	// 작성을 요함. 데이터베이스를 필요에따라 바로바로 수정할 수 있는 코드를 요함.
	// 또한 중요한 포인트로서 이를 매니저 클래스에서 동작하게 할지, 아니면 앨범 자체적으로 동작되게 할지 역시 고려사항.
	// 앨범 구매내역이나 입고내역을 한번에 저장할 수 있는 데이터베이스가 있으면 좋을듯.
	private int oid;
	private String name;
	private String genre;
	private int price;
	private Artist Artist;
	private String ArtistName;
	private int count;

	int getId() {
		return oid;
	}

	@Override
	public String getName() {
		return name;
	}

	public String getGenre() {
		return genre;
	}

	public int getPrice() {
		return price;
	}

	public String getArtistName() {
		return ArtistName;
	}

	public int getCount() {
		return count;
	}

	public Album(int id, String n, String genre, int pr, String AN, int cnt) {
		this.oid = id;
		this.name = n;
		this.genre = genre;
		this.price = pr;
		this.ArtistName = AN;
		this.count = cnt;
	}

	public Album(String n, String genre, int pr, String AN, int cnt) {
		this.name = n;
		this.genre = genre;
		this.price = pr;
		this.ArtistName = AN;
		this.count = cnt;
	}

	public Album(Album A, int cnt) {
		this.name = A.getName();
		this.ArtistName = A.getArtistName();
		this.price = A.getPrice();
		this.count = cnt;
	}

	public Album() {

	}

	@Override
	public void print() {
		System.out.printf("%d %s %d %s %d \n", oid, name, price, ArtistName, count);
	}

	public String[] getAlbumArray() {
		String[] array = { this.name, this.genre, Integer.toString(this.price), this.ArtistName,
				Integer.toString(this.count) };
		return array;
	}

	@Override
	public boolean matches(String kwd) {
		if (name.contains(kwd))
			return true;
		if (kwd.equals(genre))
			return true;
		if (kwd.equals(ArtistName))
			return true;
		if (kwd.equals("" + price))
			return true;
		return false;
	}

	// Constructor:

	@Override
	public void getDataForOid(int id) {
		Album c = getAlbum("SELECT * FROM album WHERE oid ='" + id + "'");
		if (c != null) {
			oid = c.oid;
			name = c.name;
			genre = c.genre;
			price = c.price;
			ArtistName = c.ArtistName;
			Artist = (Artist) AlbumShop.ArtistMgr.find(ArtistName);
			count = c.count;
			Artist.addAlbum(c);
		}
	}

	@Override
	public Boolean createData() {
		int oid = Database.getInstance().getId("SELECT oid FROM album");
		UseSQL_For_Data("INSERT INTO album " + "VALUES ('" + oid + "','" + name + "', '" + genre + "','" + price
				+ "', '" + ArtistName + "', '" + count + "')");
		return true;
	}

	@Override
	public void UpdateData() {
		UseSQL_For_Data("UPDATE album SET " + "name = '" + this.name + "'" + "genre = '" + this.genre + "', price = '"
				+ this.price + "', artistname = '" + this.ArtistName + "', count = '" + this.count + "' WHERE name = '"
				+ this.name + "'");
	}

	@Override
	public void DeleteData() {
		UseSQL_For_Data("DELETE FROM album WHERE oid = '" + this.oid + "'");
	}

	private Album getAlbum(String sql) {
		Album c = null;
		try {
			Statement stmt = Database.getInstance().getConnection().createStatement();
			ResultSet rset = stmt.executeQuery(sql);
			while (rset.next()) {
				int oid = rset.getInt(1);
				String name = rset.getString(2);
				String genre = rset.getString(3);
				int price = rset.getInt(4);
				String artistname = rset.getString(5);
				int count = rset.getInt(6);
				c = new Album(oid, name, genre, price, artistname, count);
			}
			rset.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

}

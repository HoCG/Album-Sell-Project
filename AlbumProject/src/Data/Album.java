package Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import Database.Database;
import MGR.Manageable;
import MGR.Manager_Of_SQL_Data;

public class Album extends Manager_Of_SQL_Data implements Manageable { // �ٹ��� ������ �߰� �� ���̴� ������ �䱸. ���ſ� ȯ��, �԰�� ��� ����
																		// �����ذ��� ���α׷�
	// �ۼ��� ����. �����ͺ��̽��� �ʿ信���� �ٷιٷ� ������ �� �ִ� �ڵ带 ����.
	// ���� �߿��� ����Ʈ�μ� �̸� �Ŵ��� Ŭ�������� �����ϰ� ����, �ƴϸ� �ٹ� ��ü������ ���۵ǰ� ���� ���� �������.
	// �ٹ� ���ų����̳� �԰����� �ѹ��� ������ �� �ִ� �����ͺ��̽��� ������ ������.
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

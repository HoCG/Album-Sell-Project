package Data;

import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

import Database.Database;
import MGR.Manageable;
import MGR.Manager_Of_SQL_Data;

public class Artist extends Manager_Of_SQL_Data implements Manageable {

	private int oid;
	private String name;
	private String realName;
	private String mainGenre;
	ArrayList<Album> ArtistAlbum = new ArrayList<>();

	String getrealName() {
		return realName;
	}

	String getmainGenre() {
		return mainGenre;
	}

	// 생성자 선언
	public Artist(int id, String n, String rn, String mg) {
		this.oid = id;
		this.name = n;
		this.realName = rn;
		this.mainGenre = mg;
	}

	public Artist(String n, String rn, String mg) {
		this.name = n;
		this.realName = rn;
		this.mainGenre = mg;
	}

	public Artist() {
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void print() {
		System.out.printf("%d %s %s \n", oid, name, realName);
		for (Album ab : ArtistAlbum) {
			System.out.print("\t");
			ab.print();
		}
	}

	//이거 왜만든거임...?
	public String[] getArtistArray() {
		String AllAlbum1 = "";
		String AllAlbum2 = "";
		int Forcount = 0;
		for (Album am : ArtistAlbum) {
			if (Forcount == 0) {
				AllAlbum1 = am.getName();
				AllAlbum2 = AllAlbum2.concat(AllAlbum1);
			}
			else {
				AllAlbum1 = am.getName();
				AllAlbum2 = AllAlbum2.concat(", " + AllAlbum1);
			}
			Forcount++;
		}
		String[] array = { this.name, this.realName, this.mainGenre, AllAlbum2 };
		return array;
	}

	@Override
	public boolean matches(String kwd) {
		if (kwd.equals(name))
			return true;
		if (kwd.equals("" + realName))
			return true;
		return false;
	}

	public void addAlbum(Album ab) {
		ArtistAlbum.add(ab);
	}

	public ArrayList<Album> getAlbumList() {
		return ArtistAlbum;
	}

	@Override
	public void getDataForOid(int id) {
		Artist c = getArtist("SELECT * FROM artist WHERE oid ='" + id + "'");
		if (c != null) {
			this.oid = c.oid;
			this.name = c.name;
			this.realName = c.realName;
			this.mainGenre = c.mainGenre;
		}
	}

	@Override
	public Boolean createData() {
		int oid = Database.getInstance().getId("SELECT oid FROM artist");
		UseSQL_For_Data("INSERT INTO artist " + "VALUES ('" + oid + "','" + name + "', '" + realName + "', '"
				+ mainGenre + "')");
		return true;
	}

	@Override
	public void UpdateData() {
		UseSQL_For_Data("UPDATE artist SET " + "name = '" + this.name + "'" + ", realname = '" + this.realName
				+ "', maingenre = '" + this.mainGenre + "' WHERE oid = '" + this.oid + "'");
	}

	@Override
	public void DeleteData() {
		UseSQL_For_Data("DELETE FROM artist WHERE oid = '" + this.oid + "'");
	}

	private Artist getArtist(String sql) {
		Artist c = null;
		try {
			Statement stmt = Database.getInstance().getConnection().createStatement();
			ResultSet rset = stmt.executeQuery(sql);
			while (rset.next()) {
				int oid = rset.getInt(1);
				String name = rset.getString(2);
				String realName = rset.getString(3);
				String mainGenre = rset.getString(4);
				c = new Artist(oid, name, realName, mainGenre);
			}
			rset.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

}

package Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import Database.Database;
import MGR.*;

//사용될 장르 클래스
public class Genre extends Manager_Of_SQL_Data implements Manageable {

	private int oid;
	private String Genre;

	public Genre(int oid, String Genre) {
		this.oid = oid;
		this.Genre = Genre;
	}

	public Genre(String Genre) {
		this.Genre = Genre;
	}

	public Genre() {
	}

	public String getName() {
		return this.Genre;
	}

	@Override
	public boolean matches(String kwd) {
		if (kwd.equals(Genre))
			return true;
		return false;
	}

	@Override
	public void getDataForOid(int oid) {
		Genre c = getGenre("SELECT * FROM Genre WHERE oid ='" + oid + "'");
		if (c != null) {
			this.oid = c.oid;
			this.Genre = c.Genre;
		}
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub
	}

	@Override
	public Boolean createData() {
		UseSQL_For_Data("INSERT INTO genre " + "VALUES ('" + this.oid + "','" + this.Genre + "')");
		return true;
	}

	@Override
	public void UpdateData() {
		int oid = Database.getInstance().getId("SELECT oid FROM users");
		UseSQL_For_Data("UPDATE genre SET " + "genre = '" + this.Genre + "' WHERE oid = '" + this.oid + "'");
	}

	@Override
	public void DeleteData() {
		UseSQL_For_Data("DELETE FROM genre WHERE oid = '" + this.oid + "'");
	}

	private Genre getGenre(String sql) {
		Genre c = null;
		try {
			Statement stmt = Database.getInstance().getConnection().createStatement();
			ResultSet rset = stmt.executeQuery(sql);
			while (rset.next()) {
				int oid = rset.getInt(1);
				String Genre = rset.getString(2);
				c = new Genre(oid, Genre);
			}
			rset.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
}

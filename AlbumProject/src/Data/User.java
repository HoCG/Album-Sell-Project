package Data;

import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import Database.Database;
import MGR.Manageable;
import MGR.Manager_Of_SQL_Data;

public class User extends Manager_Of_SQL_Data implements Manageable {

	// 데이터들 정의
	private int oid;
	private String ID;
	private String Password;
	private String name;
	private String phoneNumber;
	private String level;
	public int point;
	private int savepoint; // 등급 계산에 사용될 누적 포인트

	// 생성자 부분들
	public User(int oid, String n, String ID, String Password, String p, int point, int savepoint) {
		this.oid = oid;
		this.ID = ID;
		this.Password = Password;
		this.name = n;
		this.phoneNumber = p;
		this.point = point;
		this.savepoint = savepoint;

		// 자체적으로 누적 포인트에 따른 등급을 계산.
		if (this.savepoint >= 50 && this.savepoint < 350)
			this.level = "Bronze";
		else if (this.savepoint >= 350 && this.savepoint < 500)
			this.level = "Silver";
		else if (this.savepoint >= 500 && this.savepoint < 1000)
			this.level = "Gold";
		else if (this.savepoint >= 1000 && this.savepoint < 2000)
			this.level = "Platinum";
		else if (this.savepoint >= 2000)
			this.level = "Diamond";
		else
			this.level = "None";
	}
	
	User() {
	}

	public User(String n, String p, int point) {
		this.name = n;
		this.phoneNumber = p;
		this.point = point;
	}

	public User(String n, String ID, String Password, String p) {
		this.name = n;
		this.ID = ID;
		this.Password = Password;
		this.phoneNumber = p;
		this.point = 0;
		this.savepoint = 0;
	}

	// 변수들을 전달해주는 함수들.

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getUserId() {
		return ID;
	}

	public String getPassword() {
		return Password;
	}

	public int getPoint() {
		return point;
	}

	public int getSavePoint() {
		return savepoint;
	}

	
	// 매니지어블을 임플리먼츠 함으로써 사용되는 함수들.
	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean matches(String kwd) {
		if (this.name.contains(kwd))
			return true;
		if (kwd.equals("" + this.phoneNumber))
			return true;
		if (kwd.equals(this.Password))
			return true;
		if (kwd.equals(this.ID))
			return true;
		return false;
	}

	public String[] getUserArray() {
		String[] array = {this.name ,this.ID, this.phoneNumber, this.level, Integer.toString(this.point),
				Integer.toString(this.savepoint) };
		return array;
	}

	// Constructor:

	@Override
	public void print() {
		System.out.printf("%d %s %s %s %S\n", this.oid, this.ID, this.Password, this.name, this.phoneNumber);
	}

	@Override
	public void getDataForOid(int id) {
		User c = getUser("SELECT * FROM users WHERE oid ='" + id + "'");
		if (c != null) {
			this.oid = c.oid;
			this.name = c.name;
			this.ID = c.ID;
			this.Password = c.Password;
			this.phoneNumber = c.phoneNumber;
			this.level = c.level;
			this.point = c.point;
			this.savepoint = c.savepoint;
		}
	}

	// 가지고 온 함수를 통해 데이터를 생성.
	@Override
	public Boolean createData() {
		int oid = Database.getInstance().getId("SELECT oid FROM users");
		UseSQL_For_Data("INSERT INTO users " + "VALUES ('" + oid + "','" + this.name + "', '" + this.ID + "','"
				+ this.Password + "', '" + this.phoneNumber + "', '" + this.point + "', '" + this.savepoint + "')");
		return true;
	}

	@Override
	public void UpdateData() {
		UseSQL_For_Data("UPDATE users SET " + "name = '" + this.name + "'" + ", phonenumber = '" + this.phoneNumber
				+ "', point = '" + this.point + "', savepoint = '" + this.savepoint + "' WHERE oid = '" + this.oid
				+ "'");
	}

	@Override
	public void DeleteData() {
		UseSQL_For_Data("DELETE FROM users WHERE oid = '" + this.oid + "'");
	}

	private User getUser(String sql) {
		User c = null;
		try {
			Statement stmt = Database.getInstance().getConnection().createStatement();
			ResultSet rset = stmt.executeQuery(sql);
			while (rset.next()) {
				int oid = rset.getInt(1);
				String name = rset.getString(2);
				String ID = rset.getString(3);
				String password = rset.getString(4);
				String phone = rset.getString(5);
				int point = rset.getInt(6);
				int savepoint = rset.getInt(7);
				c = new User(oid, name, ID, password, phone, point, savepoint);
			}
			rset.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

}

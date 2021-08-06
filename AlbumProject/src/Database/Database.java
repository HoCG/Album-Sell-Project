package Database;

import java.sql.*;
import java.util.Date;
import java.util.Vector;

public class Database {
	private static Connection con;

	private static Database uniqueInstance;
	String driver = "org.mariadb.jdbc.Driver";

	public static Database getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new Database();
		}
		return uniqueInstance;
	}

	private Database() {
		try {
			Class.forName(driver);
			//������ ��� ���� �õ�.
			con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/albumproject", "root", "2412");
			if (con != null) {
				System.out.println("DB ���� ����");
			}
		} catch (ClassNotFoundException e) {
			System.out.println("����̹� �ε� ����");
		} catch (SQLException e) {
			System.out.println("DB ���� ����");
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		return con;
	}

	// Get a fresh object ID
	public int getId(String sql) {
		int id = 0;
		try {
			Statement stmt = con.createStatement();
			ResultSet rset = stmt.executeQuery(sql);
			while (rset.next()) {
				id = rset.getInt(1);
			}
			rset.close();
			id++; //���� ������ id������ id���� �Ѱ��ش�.
			//int updateCount = stmt.executeUpdate("UPDATE oid SET last_id = '" + id + "'");
			//>> ���� oid��� �����ͺ��̽��� �ִٸ� ���ǹ��ϰ� ����ȴ�.
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id;
	}
}

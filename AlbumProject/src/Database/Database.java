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
			//마리아 디비에 연결 시도.
			con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/albumproject", "root", "2412");
			if (con != null) {
				System.out.println("DB 접속 성공");
			}
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
		} catch (SQLException e) {
			System.out.println("DB 접속 실패");
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
			id++; //제일 마지막 id값으로 id값을 넘겨준다.
			//int updateCount = stmt.executeUpdate("UPDATE oid SET last_id = '" + id + "'");
			//>> 만약 oid라는 데이터베이스가 있다면 유의미하게 적용된다.
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id;
	}
}

package MGR;

import java.sql.*;
import Database.Database;


//SQL 사용을 위해 각 데이터들이 사용하게될 공통 함수
public class Manager_Of_SQL_Data {

	public void UseSQL_For_Data(String sql) {
		try {
			Statement stmt = Database.getInstance().getConnection().createStatement();
			int updateCount = stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

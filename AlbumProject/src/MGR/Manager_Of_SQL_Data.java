package MGR;

import java.sql.*;
import Database.Database;


//SQL ����� ���� �� �����͵��� ����ϰԵ� ���� �Լ�
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

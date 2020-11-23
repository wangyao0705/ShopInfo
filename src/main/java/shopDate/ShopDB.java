package shopDate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ShopDB {
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	String DB_URL = "jdbc:postgresql://localhost:5432/wang";
	String USER = "postgres";
	String PASS = "postgres";

	public Connection getDBcon() throws ClassNotFoundException {
		if (conn == null) {

			try {
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection(DB_URL, USER, PASS);
				stmt = conn.createStatement();
			} catch (SQLException e) {
			}
		}
		return conn;
	}

	public void closeDb() throws SQLException {
		if (rs != null) {
			rs.close();
		}
		if (stmt != null) {
			stmt.close();
		}
		if (conn != null) {
			conn.close();
		}
	}

	public void insertShopDate(Shop shop) throws SQLException {
		String sql = "insert into shopinfo(shop_name,shop_tel,shop_address)values('";
		sql += shop.getName() + "','";
		sql += shop.getTel() + "','";
		sql += shop.getAddress() + "')";
		System.out.println(sql);
		stmt.executeUpdate(sql);
	}

}

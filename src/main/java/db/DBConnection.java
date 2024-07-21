package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	private static Connection connection;
	private static DBConnection instance;
	
	public DBConnection() {
		// TODO Auto-generated constructor stub
		String url = "jdbc:sqlserver://localhost:1433;databaseName=QLHieuSachTuNhan;trustServerCertificate=true";
		try {
			connection  = DriverManager.getConnection(url, "sa", "050602");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}
	public static DBConnection getInstance() {
		if(instance == null)
			instance = new DBConnection();
		return instance;
	}

}

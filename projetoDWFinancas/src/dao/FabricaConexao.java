package dao;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaConexao {
	// {Class.forName("com.mysql.jdbc.Driver");}
	public Connection getConnection() throws SQLException {
		
		return DriverManager.getConnection("jdbc:mysql://localHost/dbfinancasfacil",
				"root", "123456");

	}

	public boolean closeConnection(Connection c) throws SQLException {

		c.close();

		return c.isClosed();

	}

}

package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.model.Session;
import main.model.User;

public class SessionDao extends AbstractDao {

	public SessionDao(ConnectionGateway connGateway) {
		super(connGateway);
	}
	
	public Session getSession(String username, String pass) throws Exception{
		int id;
		try{
			id = validation(username, pass);
		} catch(SQLException e){
			throw new Exception("Username/Password Incorrect.");
		}
		String selectSql = "select `full_name`, `email`," +
				"`role` from `inventory` where `id` = ?";
		Connection conn = this.connGateway.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(selectSql);
		prepStmt.setInt(1, id);
		ResultSet rs = prepStmt.executeQuery();

		Session session = new Session();
		User user = new User(rs.getString(1), rs.getString(2));
		session.setUser(user);
		if(rs.getString(3) == "Inventory Manager"){
			session.getUser().setInventoryManager(session);
		} else if(rs.getString(3) == "Production Manager"){
			session.getUser().setProductionManager(session);
		} else if(rs.getString(3) == "Admin"){
			session.getUser().setAdmin(session);
		} else {
			throw new Exception("User Role not set.");
		}
		
		rs.close();
		prepStmt.close();
		this.connGateway.closeConnection(conn);
		return session;
	}

	public int validation(String username, String password) throws SQLException{
		String selectSql = "select `user_id` from `login` where `username` = ? AND `password` = ?";
		Connection conn = this.connGateway.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(selectSql);
		prepStmt.setString(1, username);
		prepStmt.setString(2, password);
		
		ResultSet rs = prepStmt.executeQuery();
		
		rs.next();
		int user_id = rs.getInt(1);
		
		rs.close();
		prepStmt.close();
		this.connGateway.closeConnection(conn);
		return user_id;
	}
}

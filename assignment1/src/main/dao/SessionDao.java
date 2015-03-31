package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import main.model.Session;
import main.model.User;

public class SessionDao extends AbstractDao {

	public SessionDao(ConnectionGateway connGateway) {
		super(connGateway);
	}
	
	public Session getSession(User tempUser) throws Exception {
		String selectSql = "select `full_name`, `email`," +
				"`role` from `user` where `email` = ?";
		Connection conn = this.connGateway.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(selectSql);
		prepStmt.setNString(1, tempUser.getEmail());
		ResultSet rs = prepStmt.executeQuery();

		boolean noRows = rs.next();
		if(!noRows){
			throw new Exception("Bad Login");
		}
		
		User user = new User(rs.getString(1), rs.getString(2), 
				rs.getString(3));
		Session session = new Session(user);
		session.setUser(user);
		if(rs.getString(3).equals("Inventory Manager")){
			session.getUser().setInventoryManager(session);
		} else if(rs.getString(3).equals("Production Manager")){
			session.getUser().setProductionManager(session);
		} else if(rs.getString(3).equals("Admin")){
			session.getUser().setAdmin(session);
		} else {
			throw new Exception("User Role not set. Found: " 
					+ rs.getString(3));
		}
		
		rs.close();
		prepStmt.close();
		this.connGateway.closeConnection(conn);
		return session;
	}
}

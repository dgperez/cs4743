package main.model;

import main.dao.ConnectionGateway;
import main.dao.SessionDao;

public class Authenticator {
	private String username;
	
	private String password;
	
	private ConnectionGateway connGateway;
	
	public Authenticator(String username, String password, ConnectionGateway connGateway){
		this.username = username;
		this.password = password;
		this.connGateway = connGateway;
	}

	public Session Authenticate() throws Exception {
		SessionDao sessionDao = new SessionDao(connGateway);
		Session session;
		try {
			session = sessionDao.getSession(username, password);
		} catch (Exception e) {
			throw e;
		}
		return session;
	}
}
package main.model;

import main.dao.ConnectionGateway;
import main.dao.SessionDao;

public class Authenticator {
	
	private User user;
	
	private ConnectionGateway connGateway;
	
	public Authenticator(User user, 
			ConnectionGateway connGateway){
		this.user = user;
		this.connGateway = connGateway;
	}

	public Session Authenticate() throws Exception {
		SessionDao sessionDao = new SessionDao(connGateway);
		Session session;
		try {
			session = sessionDao.getSession(this.user);
		} catch (Exception e) {
			throw e;
		}
		return session;
	}
}
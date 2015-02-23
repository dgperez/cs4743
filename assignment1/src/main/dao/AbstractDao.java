package main.dao;

public abstract class AbstractDao {

	public static ConnectionGateway connGateway;
	
	public AbstractDao(ConnectionGateway connGateway) {
		AbstractDao.connGateway = connGateway;
	}
	
}

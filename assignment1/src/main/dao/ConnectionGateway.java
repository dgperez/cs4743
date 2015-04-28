package main.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionGateway {
	
	private Properties connectionParams;
	
	private static String propertyFilePath = "/properties/db.properties";
	
	public ConnectionGateway() {
		this.initProperties();		
	}
	
	private void initProperties(){
		this.connectionParams = new Properties();
		if(System.getProperty("os.name").contains("Mac OS X")){ 
			try {
				InputStream stream = 
						this.getClass().getResourceAsStream(
								ConnectionGateway.propertyFilePath);
				this.connectionParams.load(stream);
				stream.close();
			} catch (FileNotFoundException fnfe) {
				// TODO Auto-generated catch block
				fnfe.printStackTrace();
			} catch (IOException ioex){
				ioex.printStackTrace();
			}
		} else { 
			this.connectionParams = new Properties();
			this.connectionParams.put("db.user", "nlw716");
			this.connectionParams.put("db.password", "i56q7uR6Vl51qQ4tLbOE");
			this.connectionParams.put("db.hostname", 
					"devcloud.fulgentcorp.com");
			this.connectionParams.put("db.port", "3306");
			this.connectionParams.put("db.default_schema", "nlw716");
		}
		
	}
	
	private String getConnectionString(){
		String temp = 
				String.format("jdbc:mysql://%s:%s/%s?user=%s&password=%s", 
					this.connectionParams.getProperty("db.hostname"),
					this.connectionParams.getProperty("db.port"),
					this.connectionParams.getProperty("db.default_schema"),
					this.connectionParams.getProperty("db.user"),
					this.connectionParams.getProperty("db.password"));
		return temp;
	}
	
	public Connection getConnection(){
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(this.getConnectionString());
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public void closeConnection(Connection conn){
		try {
			if(conn.isValid(10)){
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

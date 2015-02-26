package main.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;


public class ConnectionGateway {
	
	private Properties connectionParams;

	private static String macSqlConnPropPath = 
			"/Users/kylehaley/dev/school/courses/cs/cs4743/" +
			"assignment1/misc/properties/db.properties";
	
	private static String windowsSqlConnPropPath = "windows absolute path" +
			"to misc/properties/db.properties here";
	
	private static String propertyFilePath;
	
	private static int openConnections = 0;
	
	public ConnectionGateway() {
		this.initProperties();		
	}
	
	private void initProperties(){
		
		
		this.connectionParams = new Properties();
		if(System.getProperty("os.name").contains("Mac OS X")){
			ConnectionGateway.propertyFilePath = 
					ConnectionGateway.macSqlConnPropPath;
			try {
				FileInputStream file = new FileInputStream(
						new File(ConnectionGateway.propertyFilePath));
				this.connectionParams.load(file);
				file.close();
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
			this.connectionParams.put("db.hostname", "devcloud.fulgentcorp.com");
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

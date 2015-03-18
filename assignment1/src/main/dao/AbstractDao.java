package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map.Entry;

public abstract class AbstractDao {

	protected ConnectionGateway connGateway;
	
	public AbstractDao(ConnectionGateway connGateway) {
		this.connGateway = connGateway;
	}
	
	protected int insertOrUpdate_TypeTable(int typeTable, String value) 
			throws SQLException{
		String tableName = "";
		String columnName = "";
		String[] tempArray = this.getTypeTableValues(typeTable);
		tableName = tempArray[0];
		columnName = tempArray[1];
		if(tableName.isEmpty()){
			return 0;
		}
		
		Connection conn = this.connGateway.getConnection();
		String selectLocation = "select `pid` from `"+tableName+"` where" +
				" `"+columnName+"` = ?;";
		PreparedStatement prepStmt = 
				conn.prepareStatement(selectLocation);
		prepStmt.setNString(1, value);
		ResultSet rs = prepStmt.executeQuery();
		int id = 0;
		if(rs.next()){
			id = rs.getInt(1);
		}
		if(0 == id){
			String insertSql = "insert into `"+tableName+"` " +
					"(`"+columnName+"`) VALUES (?);";
			prepStmt = conn.prepareStatement(insertSql);
			prepStmt.setNString(1, value);
			prepStmt.execute();
			prepStmt.close();
			prepStmt = conn.prepareStatement(selectLocation);
			prepStmt.setNString(1, value);
			rs = prepStmt.executeQuery();
			rs.next();
			id = rs.getInt(1);
			rs.close();
		}
		rs.close();
		prepStmt.close();
		connGateway.closeConnection(conn);
		return id;
	}
	
	protected Entry<Integer, String> selectType(int typeTable, int pid) 
			throws SQLException{
		String tableName = "";
		String columnName = "";
		String[] tempArray = this.getTypeTableValues(typeTable);
		tableName = tempArray[0];
		columnName = tempArray[1];
		if(tableName.isEmpty()){
			return null;
		}
		String selectEntry = "select `"+columnName+"` from `"
				+tableName+"` where `pid` = ?;";
		Connection conn = this.connGateway.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(selectEntry);
		prepStmt.setInt(1, pid);
		ResultSet rs = prepStmt.executeQuery();
		String value = "";
		if(rs.next()){
			value = rs.getString(1);
			rs.close();
			this.connGateway.closeConnection(conn);
		} else {
			rs.close();
			this.connGateway.closeConnection(conn);
			throw new SQLException(
					String.format("Could not select from %s.", tableName));
		}
		prepStmt.close();
		HashMap<Integer, String> temp = new HashMap<Integer, String>();
		temp.put(pid, value);
		
		Entry<Integer, String> tempEntry = temp.entrySet().iterator().next();
		
		return tempEntry;
	}
	
	protected String[] getTypeTableValues(int typeTable){
		String tableName = "";
		String columnName = "";
		switch(typeTable){
			case 1:
				tableName = "locations";
				columnName = "location_name";
				break;
			case 2:
				tableName = "vendors";
				columnName = "vendor_name";
				break;
			case 3:
				tableName = "unit_of_quantities";
				columnName = "quantity_name";
				break;
			default:
				break;
		}
		if(tableName.isEmpty()){
			return null;
		} 
		String[] temp = new String[2];
		temp[0] = tableName;
		temp[1] = columnName;
		return temp;
	}
	
}

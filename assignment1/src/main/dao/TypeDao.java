package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class TypeDao extends AbstractDao {

	public TypeDao(ConnectionGateway connGateway) {
		super(connGateway);
	}

	public HashMap<Integer, String> getTypeList(int typeTable) 
			throws SQLException{
		String tableName = "";
		String columnName = "";
		String[] tempArray = this.getTypeTableValues(typeTable);
		tableName = tempArray[0];
		columnName = tempArray[1];
		if(tableName.isEmpty()){
			return null;
		}
		String selectSql = "select `pid`, `"+columnName
				+"` from `"+tableName+"`;";
		Connection conn = this.connGateway.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(selectSql);
		ResultSet rs = prepStmt.executeQuery();
		prepStmt.close();
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		while(rs.next()){
			int pid = rs.getInt(1);
			String value = rs.getString(2);
			map.put(pid, value);
		}
		this.connGateway.closeConnection(conn);
		return map;
	}
	
}

package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import main.model.Part;

public class PartDao extends AbstractDao {

	public PartDao(ConnectionGateway connGateway) {
		super(connGateway);
	}

	public Part addPart(Part part) throws SQLException {
		Connection tempConn = this.connGateway.getConnection();
		int vendorId = this.insertOrUpdate_TypeTable(2, 
				part.getVendor(), tempConn);
		int unitOfQuantityId = this.insertOrUpdate_TypeTable(3, 
				part.getUnitOfQuantity().getValue(), tempConn);
		if(0 == unitOfQuantityId || 
				0 == vendorId){
			throw new SQLException(
					String.format("vendorId: %d, unitOfQuantityId: %d", 
							vendorId, unitOfQuantityId));
		}
		
		String insertPart = "insert into `parts` " +
				"(`part_number`,`part_name`,`vendor_id`," +
				"`extern_part_number`,`unit_of_quantities_id`) " +
				"VALUES (?, ?, ?, ?, ?);";
		
		PreparedStatement prepStmt = tempConn.prepareStatement(insertPart);
		
		prepStmt.setNString(1, part.getPartNumber());
		prepStmt.setNString(2, part.getPartName());
		prepStmt.setInt(3, vendorId);
		prepStmt.setNString(4, part.getExternalPartNumber());
		prepStmt.setInt(5, unitOfQuantityId);
		
		prepStmt.execute();
		prepStmt.close();
		
		String getIdSql = "select last_insert_id();";
		prepStmt = tempConn.prepareStatement(getIdSql);
		ResultSet rs = prepStmt.executeQuery();
		rs.next();
		int partId = rs.getInt(1);
		rs.close();
		prepStmt.close();
		this.connGateway.closeConnection(tempConn);
		Part newPart = this.getPart(partId);
		return newPart;
	}
	
	public void editPart(Part part) throws SQLException{
		Connection conn = this.connGateway.getConnection();
		int vendorId = this.insertOrUpdate_TypeTable(2, 
				part.getVendor(), conn);
		int unitOfQuantityId = this.insertOrUpdate_TypeTable(3, 
				part.getUnitOfQuantity().getValue(), conn);
		
		String updatePart = "update `parts` set `part_number` = ?," +
				" `part_name` = ?," +
				" `vendor_id` = ?," +
				" `extern_part_number` = ?," +
				" `unit_of_quantities_id` = ?" +
				" where `pid` = ?;";
		PreparedStatement prepStmt = conn.prepareStatement(updatePart);
		prepStmt.setNString(1, part.getPartNumber());
		prepStmt.setNString(2, part.getPartName());
		prepStmt.setInt(3, vendorId);
		prepStmt.setNString(4, part.getPartNumber());
		prepStmt.setInt(5, unitOfQuantityId);
		prepStmt.setInt(6, part.getId());
		prepStmt.execute();
		prepStmt.close();
		this.connGateway.closeConnection(conn);
	}
	
	public ArrayList<Part> getParts() throws SQLException{
		String selectParts = "select `pid`,`part_number`,`part_name`," +
				"`vendor_id`,`extern_part_number`,`unit_of_quantities_id` " +
				"from `parts`;";
		Connection conn = this.connGateway.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(selectParts);
		ResultSet rs = prepStmt.executeQuery();
		// Originally had two queries hitting the database
		TypeDao types = new TypeDao(this.connGateway);
		HashMap<Integer, String> vendors = types.getTypeList(2, conn);
		HashMap<Integer, String> units = types.getTypeList(3, conn);
		ArrayList<Part> parts = new ArrayList<Part>();
		while(rs.next()){
			int id = rs.getInt(1);
			String partNumber = rs.getString(2);
			String partName = rs.getString(3);
			Entry<Integer, String> vendor = null;
			int vendor_id = rs.getInt(4);
			for(Entry<Integer, String> entry : vendors.entrySet()){
				if(entry.getKey() == vendor_id){
					vendor = entry;
				}
			}
			String externPartNumber = rs.getString(5);
			Entry<Integer, String> unitOfQuantity = null;
			int units_of_quantity_id = rs.getInt(6);
			for(Entry<Integer, String> entry : units.entrySet()){
				if(entry.getKey() == units_of_quantity_id){
					unitOfQuantity = entry;
				}
			}
			Part tempPart = new Part(id, partNumber, partName, 
					vendor.getValue(), unitOfQuantity, externPartNumber);
			parts.add(tempPart);
		}
		rs.close();
		prepStmt.close();
		this.connGateway.closeConnection(conn);
		return parts;
	}
	
	public Part getPart(int pid) throws SQLException{
		String selectParts = "select `pid`,`part_number`,`part_name`," +
				"`vendor_id`,`extern_part_number`,`unit_of_quantities_id` " +
				"from `parts` where `pid` = ?;";
		Connection conn = this.connGateway.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(selectParts);
		prepStmt.setInt(1, pid);
		ResultSet rs = prepStmt.executeQuery();
		rs.next();
		int id = rs.getInt(1);
		String partNumber = rs.getString(2);
		String partName = rs.getString(3);
		Entry<Integer, String> vendor = this.selectType(
				AbstractDao.TableType.VENDORS.getType(), rs.getInt(4), conn);
		String externPartNumber = rs.getString(5);
		Entry<Integer, String> unitOfQuantity = 
				this.selectType(
						AbstractDao.TableType.UNITS_OF_QUANTITY.getType(), 
							rs.getInt(6), conn);
		Part tempPart = new Part(id, partNumber, partName, 
				vendor.getValue(), unitOfQuantity, externPartNumber);
		rs.close();
		prepStmt.close();
		this.connGateway.closeConnection(conn);
		return tempPart;
	}
	
	public Part getPart(int pid, Connection conn) throws SQLException{
		String selectParts = "select `pid`,`part_number`,`part_name`," +
				"`vendor_id`,`extern_part_number`,`unit_of_quantities_id` " +
				"from `parts` where `pid` = ?;";
		PreparedStatement prepStmt = conn.prepareStatement(selectParts);
		prepStmt.setInt(1, pid);
		ResultSet rs = prepStmt.executeQuery();
		rs.next();
		int id = rs.getInt(1);
		String partNumber = rs.getString(2);
		String partName = rs.getString(3);
		Entry<Integer, String> vendor = this.selectType(
				AbstractDao.TableType.VENDORS.getType(), rs.getInt(4), conn);
		String externPartNumber = rs.getString(5);
		Entry<Integer, String> unitOfQuantity = 
				this.selectType(
						AbstractDao.TableType.UNITS_OF_QUANTITY.getType(), 
							rs.getInt(6), conn);
		Part tempPart = new Part(id, partNumber, partName, 
				vendor.getValue(), unitOfQuantity, externPartNumber);
		rs.close();
		prepStmt.close();
		return tempPart;
	}
	
	public void deletePart(Part part) throws SQLException{
		String deleteSql = "delete from `parts` where `pid` = ?;";
		Connection conn = this.connGateway.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(deleteSql);
		prepStmt.setInt(1, part.getId());
		prepStmt.execute();
		prepStmt.close();
		this.connGateway.closeConnection(conn);
	}
}

package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map.Entry;

import main.model.Item;
import main.model.Part;

public class ItemDao extends AbstractDao {

	public ItemDao(ConnectionGateway connGateway) {
		super(connGateway);
	}
	
	public void addItem(Item item) throws SQLException{
		String insertSql = "insert into `inventory` " +
				"(`parts_id`,`quantity`,`locations_id`) VALUES " +
				"(?, ?, ?);";
		int locationId = 
				this.insertOrUpdate_TypeTable(1, 
						item.getLocation().getValue());
		Connection conn = this.connGateway.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(insertSql);
		prepStmt.setInt(1, item.getPart().getId());
		prepStmt.setInt(2, item.getQuantity());
		prepStmt.setInt(3, locationId);
		boolean execute = prepStmt.execute();
		prepStmt.close();
		this.connGateway.closeConnection(conn);
		if(!execute){
			throw new SQLException("Could not add new item to inventory.");
		}
	}
	
	public void editItem(Item item) throws SQLException{
		int locationId = this.insertOrUpdate_TypeTable(1, 
				item.getLocation().getValue());
		String updateSql = "update `inventory` set `parts_id` = ?, " +
				"`quantity` = ?, " +
				"`locations_id` = ? " +
				" where `pid` = ?;";
		Connection conn = this.connGateway.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(updateSql);
		
		prepStmt.setInt(1, item.getPart().getId());
		prepStmt.setInt(2, item.getQuantity());
		prepStmt.setInt(3, locationId);
		
		boolean execute = prepStmt.execute();
		prepStmt.close();
		this.connGateway.closeConnection(conn);
		if(!execute){
			throw new SQLException(
					String.format("Could not edit item with id: %d", 
							item.getId()));
		}
	}
	
	public ArrayList<Item> getItems() throws SQLException{
		String selectSql = "select `pid`,`parts_id`,`quantity`," +
				"`locations_id` from `inventory`;";
		Connection conn = this.connGateway.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(selectSql);
		ResultSet rs = prepStmt.executeQuery();
		prepStmt.close();
		this.connGateway.closeConnection(conn);
		ArrayList<Item> items = new ArrayList<Item>();
		PartDao partDao = new PartDao(this.connGateway);
		while(rs.next()){
			int id = rs.getInt(1);
			int partId = rs.getInt(2);
			int quantity = rs.getInt(3);
			int locationId = rs.getInt(4);
			Part part = partDao.getPart(partId);
			Entry<Integer, String> locationEntry = 
					this.selectType(1, locationId);
			Item tempItem = new Item(id, part, quantity, locationEntry);
			items.add(tempItem);
		}
		rs.close();
		return items;
	}
	
	public void deleteItem(Item item) throws SQLException{
		String deleteSql = "delete from `inventory` where `pid` = ?;";
		Connection conn = this.connGateway.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(deleteSql);
		prepStmt.setInt(1, item.getId());
		boolean execute = prepStmt.execute();
		prepStmt.close();
		this.connGateway.closeConnection(conn);
		if(!execute){
			throw new SQLException(
					String.format("Could not delete item with id: %d", 
							item.getId()));
		}
	}

}
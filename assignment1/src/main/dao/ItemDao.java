package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import main.model.Item;
import main.model.Part;

public class ItemDao extends AbstractDao {

	public ItemDao(ConnectionGateway connGateway) {
		super(connGateway);
	}
	
	public Item addItem(Item item) throws SQLException{
		String insertSql = "insert into `inventory` " +
				"(`parts_id`,`quantity`,`locations_id`,`last_modified`) " +
				"VALUES (?, ?, ?, null);";
		int locationId = 
				this.insertOrUpdate_TypeTable(1, 
						item.getLocation().getValue());
		Connection conn = this.connGateway.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(insertSql);
		prepStmt.setInt(1, item.getPart().getId());
		prepStmt.setInt(2, item.getQuantity());
		prepStmt.setInt(3, locationId);
		prepStmt.execute();
		prepStmt.close();
		
		String getIdSql = "select last_insert_id();";
		prepStmt = conn.prepareStatement(getIdSql);
		ResultSet rs = prepStmt.executeQuery();
		rs.next();
		int itemId = rs.getInt(1);
		rs.close();
		prepStmt.close();
		this.connGateway.closeConnection(conn);
		Item tempItem = this.getItem(itemId);
		return tempItem;
	}
	
	public Item editItem(Item item) throws SQLException{
		if(!this.getItemTimestamp(item.getId())
				.equals(item.getLastModified())){
			System.out.println(item.getLastModified() + " " + this.getItemTimestamp(item.getId()));
			throw new SQLException("This item has been modified by another " +
					"user. We will refresh the view.");
		}
		
		int locationId = this.insertOrUpdate_TypeTable(1, 
				item.getLocation().getValue());
		String updateSql = "update `inventory` set `parts_id` = ?, " +
				"`quantity` = ?, " +
				"`locations_id` = ?," +
				"`last_modified` = null" +
				" where `pid` = ?;";
		Connection conn = this.connGateway.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(updateSql);
		
		prepStmt.setInt(1, item.getPart().getId());
		prepStmt.setInt(2, item.getQuantity());
		prepStmt.setInt(3, locationId);
		prepStmt.setInt(4, item.getId());
		
		prepStmt.execute();
		prepStmt.close();
		this.connGateway.closeConnection(conn);
		return this.getItem(item.getId());
	}
	
	private Timestamp getItemTimestamp(int id) throws SQLException{
		String selectSql = "SELECT `last_modified` FROM `inventory` " +
				"WHERE `pid` = ?;";
		Connection conn = this.connGateway.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(selectSql);
		prepStmt.setInt(1, id);
		ResultSet rs = prepStmt.executeQuery();
		rs.next();
		Timestamp temp = rs.getTimestamp(1);
		return temp;
	}
	
	public ArrayList<Item> getItems() throws SQLException{
		String selectSql = "select `pid`,`parts_id`,`quantity`," +
				"`locations_id`, `last_modified` from `inventory`;";
		Connection conn = this.connGateway.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(selectSql);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<Item> items = new ArrayList<Item>();
		TypeDao types = new TypeDao(this.connGateway);
		HashMap<Integer, String> locations = types.getTypeList(1);
		while(rs.next()){
			int id = rs.getInt(1);
			int partId = rs.getInt(2);
			int quantity = rs.getInt(3);
			int locationId = rs.getInt(4);
			Timestamp lastModified = rs.getTimestamp(5);
			PartDao partDao = new PartDao(this.connGateway);
			Part part = partDao.getPart(partId);
			Entry<Integer, String> locationEntry = null;
			for(Entry<Integer, String> entry : locations.entrySet()){
				if(entry.getKey() == locationId){
					locationEntry = entry;
				}
			}
			Item tempItem = new Item(id, part, quantity, locationEntry);
			tempItem.setLastModified(lastModified);
			items.add(tempItem);
		}
		rs.close();
		prepStmt.close();
		this.connGateway.closeConnection(conn);
		return items;
	}
	
	public Item getItem(int pid) throws SQLException{
		String selectParts = "select `pid`,`parts_id`,`quantity`," +
				"`locations_id`, `last_modified` from `inventory` " +
				"where `pid` = ?;";
		Connection conn = this.connGateway.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(selectParts);
		prepStmt.setInt(1, pid);
		ResultSet rs = prepStmt.executeQuery();
		rs.next();
		int id = rs.getInt(1);
		int partsId = rs.getInt(2);
		int quantity = rs.getInt(3);
		Entry<Integer, String> location = this.selectType(
				AbstractDao.TableType.LOCATIONS.getType(), rs.getInt(4));
		Timestamp lastModified = rs.getTimestamp(5);
		PartDao partDao = new PartDao(this.connGateway);
		Item item = new Item(id, partDao.getPart(partsId), quantity, location);
		item.setLastModified(lastModified);
		rs.close();
		prepStmt.close();
		this.connGateway.closeConnection(conn);
		return item;
	}
	
	public void deleteItem(Item item) throws SQLException{
		String deleteSql = "delete from `inventory` where `pid` = ?;";
		Connection conn = this.connGateway.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(deleteSql);
		prepStmt.setInt(1, item.getId());
		prepStmt.execute();
		prepStmt.close();
		this.connGateway.closeConnection(conn);
	}
}

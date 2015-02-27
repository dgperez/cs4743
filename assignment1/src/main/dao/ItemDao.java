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
		prepStmt.execute();
		prepStmt.close();
		this.connGateway.closeConnection(conn);
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
		
		prepStmt.execute();
		prepStmt.close();
		this.connGateway.closeConnection(conn);
	}
	
	public ArrayList<Item> getItems() throws SQLException{
		String selectSql = "select `pid`,`parts_id`,`quantity`," +
				"`locations_id` from `inventory`;";
		Connection conn = this.connGateway.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(selectSql);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<Item> items = new ArrayList<Item>();
		while(rs.next()){
			int id = rs.getInt(1);
			int partId = rs.getInt(2);
			int quantity = rs.getInt(3);
			int locationId = rs.getInt(4);
			PartDao partDao = new PartDao(this.connGateway);
			Part part = partDao.getPart(partId);
			Entry<Integer, String> locationEntry = 
					this.selectType(1, locationId);
			Item tempItem = new Item(id, part, quantity, locationEntry);
			items.add(tempItem);
		}
		rs.close();
		prepStmt.close();
		this.connGateway.closeConnection(conn);
		return items;
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

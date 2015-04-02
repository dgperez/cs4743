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
import main.model.ProductTemplate;

public class ItemDao extends AbstractDao {

	public ItemDao(ConnectionGateway connGateway) {
		super(connGateway);
	}
	
	public Item addItem(Item item) throws SQLException{
		Connection conn = this.connGateway.getConnection();
		String insertSql = "insert into `inventory` " +
				"(`parts_id`,`quantity`,`locations_id`,`last_modified`) " +
				"VALUES (?, ?, ?, null);";
		int locationId = 
				this.insertOrUpdate_TypeTable(1, 
						item.getLocation().getValue(), conn);
		
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
		Item tempItem = this.getItem(itemId, conn);
		this.connGateway.closeConnection(conn);
		return tempItem;
	}
	
	public Item editItem(Item item) throws SQLException{
		Connection conn = this.connGateway.getConnection();
		if(!this.getItemTimestamp(item.getId(),conn)
				.equals(item.getLastModified())){
			throw new SQLException("This item has been modified by another " +
					"user. We will refresh the view.");
		}
		
		int locationId = this.insertOrUpdate_TypeTable(1, 
				item.getLocation().getValue(), conn);
		String partOrProduct = "";
		int partOrProductId = 0;
		if(!item.hasProduct()){
			partOrProductId = item.getPart().getId();
			partOrProduct = "`parts_id`";
		} else {
			partOrProductId = item.getProductTemplate().getId();
			partOrProduct = "`product_templates_id`";
		}
		String updateSql = "update `inventory` set " + partOrProduct 
				+ " = ?, " +
				"`quantity` = ?, " +
				"`locations_id` = ?," +
				"`last_modified` = null" +
				" where `pid` = ?;";
		
		PreparedStatement prepStmt = conn.prepareStatement(updateSql);
		
		prepStmt.setInt(1, partOrProductId);
		prepStmt.setInt(2, item.getQuantity());
		prepStmt.setInt(3, locationId);
		prepStmt.setInt(4, item.getId());
		
		prepStmt.execute();
		prepStmt.close();
		Item tempItem = this.getItem(item.getId(), conn);
		this.connGateway.closeConnection(conn);
		return tempItem;
	}
	
	private Timestamp getItemTimestamp(int id, Connection conn) 
			throws SQLException{
		String selectSql = "SELECT `last_modified` FROM `inventory` " +
				"WHERE `pid` = ?;";
		PreparedStatement prepStmt = conn.prepareStatement(selectSql);
		prepStmt.setInt(1, id);
		ResultSet rs = prepStmt.executeQuery();
		rs.next();
		Timestamp temp = rs.getTimestamp(1);
		return temp;
	}
	
	public ArrayList<Item> getItems() throws SQLException{
		String selectSql = "select `pid`,`parts_id`, `product_templates_id`, "
				+ "`quantity`, `locations_id`, `last_modified` "
				+ "from `inventory`;";
		Connection conn = this.connGateway.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(selectSql);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<Item> items = new ArrayList<Item>();
		TypeDao types = new TypeDao(this.connGateway);
		HashMap<Integer, String> locations = types.getTypeList(1, conn);
		while(rs.next()){
			int id = rs.getInt(1);
			int partId = rs.getInt(2);
			int productTemplateId = rs.getInt(3);
			int quantity = rs.getInt(4);
			int locationId = rs.getInt(5);
			Timestamp lastModified = rs.getTimestamp(6);
			Part part = null;
			ProductTemplate productTemplate = null;
			if(partId != 0){
				PartDao partDao = new PartDao(this.connGateway);
				part = partDao.getPart(partId, conn);
			} else {
				ProductTemplateDao productTemplateDao = 
						new ProductTemplateDao(this.connGateway);
				productTemplate = productTemplateDao
						.getProductTemplate(productTemplateId, conn);
			}
			Entry<Integer, String> locationEntry = null;
			for(Entry<Integer, String> entry : locations.entrySet()){
				if(entry.getKey() == locationId){
					locationEntry = entry;
				}
			}
			Item item = null;
			if(partId != 0){
				item = new Item(id, part, quantity, locationEntry);
			} else {
				item = new Item(id, productTemplate, quantity, 
						locationEntry);
			}
			item.setLastModified(lastModified);
			items.add(item);
		}
		rs.close();
		prepStmt.close();
		this.connGateway.closeConnection(conn);
		return items;
	}
	
	public Item getItem(int pid) throws SQLException{
		String selectParts = "select `pid`,`parts_id`,`product_templates_id`,"
				+ "`quantity`, `locations_id`, `last_modified` "
				+ "from `inventory` where `pid` = ?;";
		Connection conn = this.connGateway.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(selectParts);
		prepStmt.setInt(1, pid);
		ResultSet rs = prepStmt.executeQuery();
		rs.next();
		int id = rs.getInt(1);
		int partId = rs.getInt(2);
		int productTemplateId = rs.getInt(3);
		int quantity = rs.getInt(4);
		Entry<Integer, String> locationEntry = this.selectType(
				AbstractDao.TableType.LOCATIONS.getType(), rs.getInt(5), conn);
		Timestamp lastModified = rs.getTimestamp(6);
		Part part = null;
		ProductTemplate productTemplate = null;
		if(partId != 0){
			PartDao partDao = new PartDao(this.connGateway);
			part = partDao.getPart(partId, conn);
		} else {
			ProductTemplateDao productTemplateDao = 
					new ProductTemplateDao(this.connGateway);
			productTemplate = productTemplateDao
					.getProductTemplate(productTemplateId, conn);
		}
		Item item = null;
		if(partId != 0){
			item = new Item(id, part, quantity, locationEntry);
		} else {
			item = new Item(id, productTemplate, quantity, 
					locationEntry);
		}
		item.setLastModified(lastModified);
		rs.close();
		prepStmt.close();
		this.connGateway.closeConnection(conn);
		return item;
	}
	
	public Item getItem(int pid, Connection conn) throws SQLException{
		String selectParts = "select `pid`,`parts_id`,`product_templates_id`,"
				+ "`quantity`, `locations_id`, `last_modified` "
				+ "from `inventory` where `pid` = ?;";
		PreparedStatement prepStmt = conn.prepareStatement(selectParts);
		prepStmt.setInt(1, pid);
		ResultSet rs = prepStmt.executeQuery();
		rs.next();
		int id = rs.getInt(1);
		int partId = rs.getInt(2);
		int productTemplateId = rs.getInt(3);
		int quantity = rs.getInt(4);
		Entry<Integer, String> locationEntry = this.selectType(
				AbstractDao.TableType.LOCATIONS.getType(), rs.getInt(5), conn);
		Timestamp lastModified = rs.getTimestamp(6);
		Part part = null;
		ProductTemplate productTemplate = null;
		if(partId != 0){
			PartDao partDao = new PartDao(this.connGateway);
			part = partDao.getPart(partId, conn);
		} else {
			ProductTemplateDao productTemplateDao = 
					new ProductTemplateDao(this.connGateway);
			productTemplate = productTemplateDao
					.getProductTemplate(productTemplateId, conn);
		}
		Item item = null;
		if(partId != 0){
			item = new Item(id, part, quantity, locationEntry);
		} else {
			item = new Item(id, productTemplate, quantity, 
					locationEntry);
		}
		item.setLastModified(lastModified);
		rs.close();
		prepStmt.close();
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

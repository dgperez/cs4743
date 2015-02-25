package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import main.model.Item;

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
	}
	
	public void editItem(Item item){}

}

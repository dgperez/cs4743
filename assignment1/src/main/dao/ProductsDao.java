package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.model.Item;
import main.model.ProductTemplate;
import main.model.ProductTemplatePart;

public class ProductsDao extends AbstractDao {

	public ProductsDao(ConnectionGateway connGateway) {
		super(connGateway);
	}

	public Item addProduct(Item item) throws SQLException{
		String lockSql = "LOCK TABLES `inventory` WRITE;";
		String unlockSql = "UNLOCK TABLES;";
		String selectSql = "SELECT `pid`, " +
				"`parts_id`, " + 
				"`quantity`, " +
				"`locations_id`, " +
				"`last_modified` " +
				"FROM `inventory` WHERE `parts_id` = ? "
				+ "ORDER BY `locations_id` ASC;";
		Connection conn = this.connGateway.getConnection();
		conn.setAutoCommit(false);
		conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		PreparedStatement prepStmt;
		SQLException temp = null;
		Item tempItem = null;
		try {
			prepStmt = conn.prepareStatement(lockSql);
			prepStmt.execute();
			prepStmt.close();
			prepStmt = conn.prepareCall(selectSql);
			for(ProductTemplatePart ptp : 
					item.getProductTemplate().getProductTemplateParts()){
				prepStmt.setInt(1, ptp.getPart().getId());
				ResultSet rs = prepStmt.executeQuery();
				boolean quantity_met = false;
				int quantity = ptp.getPartQuantity();
				while(rs.next()){
					int temp_quantity = rs.getInt(3);
					if(temp_quantity >= quantity){
						quantity_met = true;
					}
					if(quantity_met){
						break;
					}
				}
				rs.close();
				if(!quantity_met){
					throw new SQLException("Product quantity requirements "
							+ "cannot be met.");
				}
			}
			prepStmt.close();
			String insertSql = "insert into `inventory` " +
					"(`product_templates_id`,`quantity`,`locations_id`,"
					+ "`last_modified`) VALUES (?, ?, ?, null);";
			prepStmt = conn.prepareStatement(insertSql);
			prepStmt.setInt(1, item.getProductTemplate().getId());
			prepStmt.setInt(2, 1);
			prepStmt.setInt(3, item.getLocation().getKey());
			prepStmt.execute();
			prepStmt.close();
			prepStmt = conn.prepareStatement(selectSql);
			String updateSql = "UPDATE `inventory` " +
								"SET " +
								"`quantity` = ?, " +
								"`last_modified` = null " +
								"WHERE `pid` = ?;";
			PreparedStatement prepStmtUpdate = conn.prepareCall(updateSql);
			for(ProductTemplatePart ptp : 
				item.getProductTemplate().getProductTemplateParts()){
				prepStmt.setInt(1, ptp.getPart().getId());
				ResultSet rs = prepStmt.executeQuery();
				boolean quantity_met = false;
				int quantity = ptp.getPartQuantity();
				while(rs.next()){
					int temp_quantity = rs.getInt(3);
					if(temp_quantity >= quantity){
						quantity_met = true;
					}
					if(quantity_met){
						prepStmtUpdate.setInt(1, (temp_quantity - quantity));
						prepStmtUpdate.setInt(2, rs.getInt(1));
						break;
					}
				}
				rs.close();
				if(!quantity_met){
					throw new SQLException("Product quantity requirements "
							+ "cannot be met.");
				}
			}
			conn.commit();
			String getIdSql = "select last_insert_id();";
			prepStmt = conn.prepareStatement(getIdSql);
			ResultSet rs = prepStmt.executeQuery();
			rs.next();
			int itemId = rs.getInt(1);
			rs.close();
			prepStmt.close();
			ItemDao itemDao = new ItemDao(this.connGateway);
			tempItem = itemDao.getItem(itemId);
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
			temp = e;
		} finally {
			prepStmt = conn.prepareStatement(unlockSql);
			prepStmt.execute();
			prepStmt.close();
			conn.setAutoCommit(true);
			this.connGateway.closeConnection(conn);
			if(temp != null){
				throw temp;
			}
		}
		return tempItem;
	}
}

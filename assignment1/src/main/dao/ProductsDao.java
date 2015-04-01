package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.model.ProductTemplate;
import main.model.ProductTemplatePart;

public class ProductsDao extends AbstractDao {

	public ProductsDao(ConnectionGateway connGateway) {
		super(connGateway);
	}


	public void addProduct(ProductTemplate productTemplate) 
			throws SQLException {
		String startTransaction = "START TRANSACTION;";
		String commit = "COMMIT;";
		String rollback = "ROLLBACK;";
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
		PreparedStatement prepStmt;
		try {
			prepStmt = conn.prepareCall(lockSql);
			prepStmt.execute();
			for(ProductTemplatePart ptp : 
					productTemplate.getProductTemplateParts()){
				prepStmt = conn.prepareCall(selectSql);
				prepStmt.setInt(1, ptp.getPart().getId());
				ResultSet rs = prepStmt.executeQuery();
				boolean quantity_met = false;
				int quantity = ptp.getPartQuantity();
				while(rs.next()){
					int temp_quantity = rs.getInt(3);
					if(temp_quantity == quantity){
						quantity_met = true;
					}
					if(quantity_met){
						break;
					}
				}
			}
			prepStmt = conn.prepareCall(startTransaction);
			prepStmt.execute();
			lockSql = "LOCK TABLES `product_templates` WRITE;";
			prepStmt = conn.prepareCall(lockSql);
			prepStmt.execute();
			String updateSql = "UPDATE `inventory` " +
					"SET `quantity`= `quantity`-? " + 
					"WHERE `parts_id`=?";
			lockSql = "LOCK TABLES `inventory` WRITE;";
			for(ProductTemplatePart ptp : 
					productTemplate.getProductTemplateParts()){
				prepStmt = conn.prepareCall(lockSql);
				prepStmt.execute();
				prepStmt = conn.prepareCall(updateSql);
				prepStmt.setInt(1, ptp.getPartQuantity());
				prepStmt.setInt(2, ptp.getPart().getId());
				prepStmt.execute();
			}
			lockSql = "LOCK TABLES `product_templates` WRITE;";
			prepStmt = conn.prepareStatement(lockSql);
			prepStmt.execute();
			updateSql = "UPDATE `product_templates` " + 
					"SET `quantity`=? " + 
					"WHERE `pid`=?";
			prepStmt = conn.prepareCall(updateSql);
			prepStmt.setInt(1, (productTemplate.getQuantity()+1));
			prepStmt.setInt(2, productTemplate.getId());
			prepStmt.execute();
			prepStmt = conn.prepareCall(commit);
			prepStmt.execute();
		} catch (SQLException e) {
			prepStmt = conn.prepareCall(rollback);
			prepStmt.execute();
			e.printStackTrace();
		} finally {
			prepStmt = conn.prepareCall(unlockSql);
			prepStmt.execute();
			prepStmt.close();
			this.connGateway.closeConnection(conn);
		}
	}

	public void deleteProduct(ProductTemplate productTemplate){

	}
}

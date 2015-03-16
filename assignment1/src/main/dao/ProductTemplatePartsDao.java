package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.model.Part;
import main.model.ProductTemplatePart;

public class ProductTemplatePartsDao extends AbstractDao {

	public ProductTemplatePartsDao(ConnectionGateway connGateway) {
		super(connGateway);
	}
	
	public ProductTemplatePart addProductTemplatePart(
			ProductTemplatePart productTemplatePart) throws SQLException{
		Connection tempConn = this.connGateway.getConnection();
		String insertSql = "INSERT INTO `nlw716`.`product_template_parts` " +
				"(`parts_id`, `product_template_id`, `quantity`) " +
				"VALUES (?, ?, ?);";
		
		PreparedStatement prepStmt = tempConn.prepareStatement(insertSql);
		prepStmt.setInt(1, productTemplatePart.getPart().getId());
		prepStmt.setInt(2, productTemplatePart.getProductTemplateId());
		prepStmt.setInt(3, productTemplatePart.getPartQuantity());
		
		prepStmt.execute();
		prepStmt.close();
		
		String getIdSql = "select last_insert_id();";
		prepStmt = tempConn.prepareStatement(getIdSql);
		ResultSet rs = prepStmt.executeQuery();
		rs.next();
		int productTemplatePartId = rs.getInt(1);
		ProductTemplatePart tempProductTemplatePart = 
				this.getProductTemplatePart(productTemplatePartId);
		rs.close();
		prepStmt.close();
		this.connGateway.closeConnection(tempConn);
		return tempProductTemplatePart;
	}
	
	public List<ProductTemplatePart> addProductTemplateParts(
			List<ProductTemplatePart> productTemplateParts) 
					throws SQLException {
		ArrayList<ProductTemplatePart> allTemplateParts = 
				new ArrayList<ProductTemplatePart>();
		for(ProductTemplatePart ptp : productTemplateParts){
			ProductTemplatePart temp = this.addProductTemplatePart(ptp);
			allTemplateParts.add(temp);
		}
		return allTemplateParts;
	}
	
	public void editProductTemplatePart(
			ProductTemplatePart productTemplatePart) throws SQLException{
		String updateSql = "UPDATE `product_template_parts` " +
				"SET `parts_id` = ?, " +
				"`product_template_id` = ?, " +
				"`quantity` = ? " +
				"WHERE `pid` = ?;";
		Connection conn = this.connGateway.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(updateSql);
		prepStmt.setInt(1, productTemplatePart.getPart().getId());
		prepStmt.setInt(2, productTemplatePart.getProductTemplateId());
		prepStmt.setInt(3, productTemplatePart.getPartQuantity());
		prepStmt.setInt(4, productTemplatePart.getId());
		
		prepStmt.execute();
		prepStmt.close();
		this.connGateway.closeConnection(conn);
	}
	
	public List<ProductTemplatePart> getProductTemplateParts(
			int productTemplateId) throws SQLException{
		String selectSql = "SELECT `pid`, `parts_id`, " +
				"`product_template_id`, `quantity` FROM " +
				"`product_template_parts` WHERE `product_template_id` = ?;";
		Connection conn = this.connGateway.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(selectSql);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<ProductTemplatePart> productTemplateParts = 
				new ArrayList<ProductTemplatePart>();
		while(rs.next()){
			int productTemplatePartId = rs.getInt(1);
			int partId = rs.getInt(2);
			int prodTemplateId = rs.getInt(3);
			int quantity = rs.getInt(4);
			PartDao partDao = new PartDao(this.connGateway);
			Part part = partDao.getPart(partId);
			ProductTemplatePart productTemplatePart = 
					new ProductTemplatePart(productTemplatePartId, part, 
							quantity);
			productTemplatePart.setProductTemplateId(prodTemplateId);
			productTemplateParts.add(productTemplatePart);
		}
		rs.close();
		prepStmt.close();
		this.connGateway.closeConnection(conn);
		return productTemplateParts;
	}
	
	public ProductTemplatePart getProductTemplatePart(int pid) 
			throws SQLException{
		Connection conn = this.connGateway.getConnection();
		String selectSql = "SELECT `pid`, `parts_id`, " +
				"`product_template_id`, `quantity` " +
				"FROM `product_template_parts` WHERE `pid` = ?;";
		PreparedStatement prepStmt = conn.prepareStatement(selectSql);
		prepStmt.setInt(1, pid);
		ResultSet rs = prepStmt.executeQuery();
		rs.next();
		int productTemplatePartId = rs.getInt(1);
		int partId = rs.getInt(2);
		int productTemplateId = rs.getInt(3);
		int quantity = rs.getInt(4);
		PartDao partDao = new PartDao(this.connGateway);
		Part part = partDao.getPart(partId);
		ProductTemplatePart productTemplatePart = 
				new ProductTemplatePart(productTemplatePartId, part, quantity);
		productTemplatePart.setProductTemplateId(productTemplateId);
		rs.close();
		prepStmt.close();
		this.connGateway.closeConnection(conn);
		return productTemplatePart;
	}

	public void deleteProductTemplatePart(
			ProductTemplatePart productTemplatePart) throws SQLException{
		String deleteSql = "DELETE FROM `product_template_parts` " +
				"WHERE `pid` = ?;";
		Connection conn = this.connGateway.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(deleteSql);
		prepStmt.setInt(1, productTemplatePart.getId());
		prepStmt.execute();
		prepStmt.close();
		this.connGateway.closeConnection(conn);
	}
}

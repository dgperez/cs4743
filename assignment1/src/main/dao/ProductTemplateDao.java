package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.model.ProductTemplate;
import main.model.ProductTemplatePart;

public class ProductTemplateDao extends AbstractDao {

	public ProductTemplateDao(ConnectionGateway connGateway) {
		super(connGateway);
	}

	public ProductTemplate addProductTemplate(ProductTemplate productTemplate) 
			throws SQLException {
		Connection conn = this.connGateway.getConnection();
		String insertSql = "INSERT INTO `nlw716`.`product_templates` " +
				"(`product_number`, `product_description`) VALUES (?, ?);";
		
		PreparedStatement prepStmt = conn.prepareStatement(insertSql);
		prepStmt.setNString(1, productTemplate.getProductNumber());
		prepStmt.setNString(2, productTemplate.getProductDescription());
		
		prepStmt.execute();
		prepStmt.close();
		
		String getIdSql = "select last_insert_id();";
		
		prepStmt = conn.prepareStatement(getIdSql);
		ResultSet rs = prepStmt.executeQuery();
		rs.next();
		int productTemplateId = rs.getInt(1);
		rs.close();
		ProductTemplatePartsDao productTemplatePartsDao = 
				new ProductTemplatePartsDao(this.connGateway);
		List<ProductTemplatePart> addedTemplateParts = 
				productTemplatePartsDao.addProductTemplateParts(
						productTemplate.getProductTemplateParts());
		productTemplate.setProductTemplateParts(addedTemplateParts);
		productTemplate.setProductTemplatePartsParentId(productTemplateId);
		productTemplate.setId(productTemplateId);
		return productTemplate;
	}
	
	public ProductTemplate editProductTemplate(ProductTemplate productTemplate) 
			throws SQLException{
		String updateSql = "UPDATE `product_templates` " +
				"SET `product_number`=?," +
				"`product_description`=?," +
				"WHERE `pid` = ?";
		Connection conn = this.connGateway.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(updateSql);
		prepStmt.setNString(1, productTemplate.getProductNumber());
		prepStmt.setNString(2, productTemplate.getProductDescription());
		prepStmt.setInt(3, productTemplate.getId());
		
		prepStmt.execute();
		prepStmt.close();
		this.connGateway.closeConnection(conn);
		return productTemplate;
	}
	
	public ArrayList<ProductTemplate> getProductTemplates() 
			throws SQLException {
		String selectSql = "SELECT `pid`, `product_number`, " +
				"`product_description` FROM `product_templates`";
		Connection conn = this.connGateway.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(selectSql);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<ProductTemplate> productTemplates = 
				new ArrayList<ProductTemplate>();
		ProductTemplatePartsDao productTemplatePartsDao = 
				new ProductTemplatePartsDao(this.connGateway);
		while(rs.next()){
			int productTemplateId = rs.getInt(1);
			String productNumber = rs.getString(2);
			String productDescription = rs.getString(3);
			ArrayList<ProductTemplatePart> parts = 
					productTemplatePartsDao.
						getProductTemplateParts(productTemplateId);
			ProductTemplate productTemplate = 
					new ProductTemplate(productTemplateId, 
							productNumber, productDescription);
			productTemplate.setProductTemplateParts(parts);
			productTemplates.add(productTemplate);
		}
		rs.close();
		prepStmt.close();
		this.connGateway.closeConnection(conn);
		return productTemplates;
	}
	
	public ProductTemplate getProductTemplate(int pid) throws SQLException{
		String selectSql = "SELECT `pid`, `product_number`, " +
				"`product_description` FROM `product_templates` " +
				"WHERE `pid` = ?;";
		Connection conn = this.connGateway.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(selectSql);
		prepStmt.setInt(1, pid);
		ResultSet rs = prepStmt.executeQuery();
		rs.next();
		int productTemplateId = rs.getInt(1);
		String productNumber = rs.getString(2);
		String productDescription = rs.getString(3);
		rs.close();
		prepStmt.close();
		
		ProductTemplatePartsDao productTemplatePartsDao = 
				new ProductTemplatePartsDao(this.connGateway);
		ArrayList<ProductTemplatePart> parts = 
				productTemplatePartsDao.
					getProductTemplateParts(productTemplateId);
		
		this.connGateway.closeConnection(conn);
		
		ProductTemplate productTemplate = 
				new ProductTemplate(productTemplateId, productNumber, 
						productDescription);
		productTemplate.setProductTemplateParts(parts);
		return productTemplate;
	}
	
	public void deleteProductTemplate(ProductTemplate productTemplate) 
			throws SQLException{
		ProductTemplatePartsDao productTemplatePartsDao = 
				new ProductTemplatePartsDao(this.connGateway);
		productTemplatePartsDao.deleteProductTemplateParts(
				productTemplate.getProductTemplateParts());
		String deleteSql = "DELETE FROM `product_templates` WHERE `pid` = ?;";
		Connection conn = this.connGateway.getConnection();
		PreparedStatement prepStmt = conn.prepareStatement(deleteSql);
		prepStmt.setInt(1, productTemplate.getId());
		prepStmt.execute();
		prepStmt.close();
		this.connGateway.closeConnection(conn);
	}
}

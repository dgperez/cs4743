package main.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		productTemplate.setProductTemplatePartsParentId(productTemplateId);
		ProductTemplatePartsDao productTemplatePartsDao = 
				new ProductTemplatePartsDao(this.connGateway);
		List<ProductTemplatePart> addedTemplateParts = 
				productTemplatePartsDao.addProductTemplateParts(
						productTemplate.getProductTemplateParts());
		productTemplate.setProductTemplateParts(addedTemplateParts);
		return productTemplate;
	}
	
}

package id.co.qualitas.dao.impl;

import java.math.BigDecimal;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import id.co.qualitas.dao.BaseDao;
import id.co.qualitas.dao.interfaces.DeviceDao;
import id.co.qualitas.dao.interfaces.EmployeeDao;
import id.co.qualitas.dao.interfaces.UomConversionDao;
import id.co.qualitas.domain.request.UomConversion;
import id.co.qualitas.domain.response.DOHistoryResponse;
import id.co.qualitas.domain.response.WSMessage;

@Repository
@Transactional
public class UomConversionDaoImpl extends BaseDao implements UomConversionDao {
	@Autowired
	private SessionFactory sessionFactory;

//	@Override
	public WSMessage getConversion(String idVendorSap, String materialNo, String qtySap,
			String uomSap) {
		String query = " SELECT TOP 1"
				+ "	qty_sap as qtySap, "
				+ "	uom_sap as uomSap, "
				+ "	CAST(ROUND(((:qtySap/qty_sap) * qty_oem), 3) AS Decimal(10,3))  as qtyOem, "
				+ "	CAST((:qtySap/qty_sap) * qty_oem AS Decimal(38,38)) as baseQtyOem, "
				+ "	CAST((:qtySap/qty_sap) * qty_oem AS VARCHAR) as baseQtyOemString, "
				+ " qty_oem as oriQtyOem, "
				+ "	uom_oem as uomOem, "
				+ "	qty_sap/qty_oem as multiplier "
				+ " FROM uom_conversion "
				+ " WHERE id_vendor_sap LIKE :idVendorSap "
				+ "	 AND material_no LIKE :materialNo "
				+ "	 AND uom_sap LIKE :uomSap ";
	
		Session session = sessionFactory.getCurrentSession();
	
		WSMessage message = new WSMessage();
		
		try {
	
			UomConversion conversionResult = new UomConversion();
			
			conversionResult = (UomConversion) session.createSQLQuery(query)
				.setString("idVendorSap", idVendorSap)
				.setString("materialNo", materialNo)
				.setString("qtySap", qtySap)
				.setString("uomSap", uomSap)
				.setResultTransformer(Transformers.aliasToBean(UomConversion.class))
				.uniqueResult();
			
			message.setIdMessage(1);
			message.setMessage("Success getting conversion result");
			message.setResult(conversionResult);
	
		}catch (Exception e) {
			message.setIdMessage(0);
			message.setMessage(e.getMessage());
		}
		
		return message;
	}
	
	@Override
	public WSMessage getConversion(String idVendorSap, String materialNo, BigDecimal qtySap,
			String uomSap) {
		String query = " SELECT TOP 1"
				+ "	qty_sap as qtySap, "
				+ "	uom_sap as uomSap, "
				+ "	CAST(ROUND(((:qtySap/qty_sap) * qty_oem), 3) AS Decimal(10,3))  as qtyOem, "
				+ "	CAST((:qtySap/qty_sap) * qty_oem AS Decimal(38,15)) as baseQtyOem, "
				+ "	CAST((:qtySap/qty_sap) * qty_oem AS VARCHAR) as baseQtyOemString, "
				+ "	uom_oem as uomOem, "
				+ "	qty_sap/qty_oem as multiplier "
				+ " FROM uom_conversion "
				+ " WHERE id_vendor_sap LIKE :idVendorSap "
				+ "	 AND material_no LIKE :materialNo "
				+ "	 AND uom_sap LIKE :uomSap ";
	
		Session session = sessionFactory.getCurrentSession();
	
		WSMessage message = new WSMessage();	
		
		try {
	
			UomConversion conversionResult = new UomConversion();
			
			conversionResult = (UomConversion) session.createSQLQuery(query)
				.setString("idVendorSap", idVendorSap)
				.setString("materialNo", materialNo)
				.setBigDecimal("qtySap", qtySap)
				.setString("uomSap", uomSap)
				.setResultTransformer(Transformers.aliasToBean(UomConversion.class))
				.uniqueResult();
			
			message.setIdMessage(1);
			message.setMessage("Success getting conversion result");
			message.setResult(conversionResult);
	
		}catch (Exception e) {
			message.setIdMessage(0);
			message.setMessage(e.getMessage());
		}
		
		return message;
	}

	
}


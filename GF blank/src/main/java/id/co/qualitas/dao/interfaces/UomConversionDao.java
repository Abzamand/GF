package id.co.qualitas.dao.interfaces;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import id.co.qualitas.domain.response.WSMessage;


public interface UomConversionDao {
//	WSMessage getConversion(String idVendorSap, String materialNo, String qtySap, String uomSap);

	WSMessage getConversion(String idVendorSap, String materialNo, BigDecimal qtySap, String uomSap);
	
}

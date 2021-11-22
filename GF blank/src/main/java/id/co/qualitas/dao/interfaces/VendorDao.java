package id.co.qualitas.dao.interfaces;

import id.co.qualitas.domain.response.Vendor;

public interface VendorDao {

	public Vendor getVendorDetail(String idSloc, String idPlant);
	
	public String getIdVendorSapByUsername(String username);

	Vendor getSlocDetail(String idSloc, String idPlant);
}

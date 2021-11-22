package id.co.qualitas.domain.request;

import java.util.Date;

import id.co.qualitas.domain.response.SAP_ShipmDODetailResponse;

public class PGIDetail extends SAP_ShipmDODetailResponse{
	private int idPGIHeader;
	
	public PGIDetail() {
	}

	public int getIdPGIHeader() {
		return idPGIHeader;
	}

	public void setIdPGIHeader(int idPGIHeader) {
		this.idPGIHeader = idPGIHeader;
	}
	

	
}

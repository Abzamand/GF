package id.co.qualitas.domain;

import java.io.Serializable;

public class LoginDetail extends MasterDomain implements Serializable{
	private static final long serialVersionUID = 1579086272072691905L;
	
	private String full_name;

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
}

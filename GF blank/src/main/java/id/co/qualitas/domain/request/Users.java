package id.co.qualitas.domain.request;

import id.co.qualitas.domain.MasterDomain;

public class Users extends MasterDomain {
	private int username;
	private String userlogin;
	private String password;
	private String regisId;
	private String type;
	private int changed;
	
	private String oldPassword;
	public int getUsername() {
		return username;
	}
	public void setUsername(int username) {
		this.username = username;
	}
	public String getUserlogin() {
		return userlogin;
	}
	public void setUserlogin(String userlogin) {
		this.userlogin = userlogin;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRegisId() {
		return regisId;
	}
	public void setRegisId(String regisId) {
		this.regisId = regisId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getChanged() {
		return changed;
	}
	public void setChanged(int changed) {
		this.changed = changed;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
}

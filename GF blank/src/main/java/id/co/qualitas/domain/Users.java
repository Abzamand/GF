package id.co.qualitas.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Users extends MasterDomain implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1507968686333391636L;
	@Id
	@Column(length=100)
	private String username;
	@Column(length=100)
	private String password;
	@Transient
	private List<Group> listGroup;
	@Transient
	private List<Authority> listAuthority;
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public List<Group> getListGroup() {
		return listGroup;
	}
	public void setListGroup(List<Group> listGroup) {
		this.listGroup = listGroup;
	}
	public List<Authority> getListAuthority() {
		return listAuthority;
	}
	public void setListAuthority(List<Authority> listAuthority) {
		this.listAuthority = listAuthority;
	}
}

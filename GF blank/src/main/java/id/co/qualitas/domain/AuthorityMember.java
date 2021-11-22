package id.co.qualitas.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="authority_members")
public class AuthorityMember implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8759058741850593002L;
	@Id
	@Column(length=100)
	private String username;
	@Id
	@Column(length=50)
	private String authority;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
}

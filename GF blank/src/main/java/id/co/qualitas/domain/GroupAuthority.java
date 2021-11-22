package id.co.qualitas.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="group_authorities")
public class GroupAuthority extends MasterDomain implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5022962190748834223L;
	@Id
	@Column(length=50)
	private String groupId;
	@Id
	@Column(length=50)
	private String authority;
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
}

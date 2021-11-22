package id.co.qualitas.domain;

import java.beans.Transient;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Authority {
	@Id
	@Column(length=50)
	private String authority;
	private String description;
	@Column(nullable=true)
	private BigInteger menuId;
	private boolean selected;
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigInteger getMenuId() {
		return menuId;
	}
	public void setMenuId(BigInteger menuId) {
		this.menuId = menuId;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Authority [authority=");
		builder.append(authority);
		builder.append(", description=");
		builder.append(description);
		builder.append(", menuId=");
		builder.append(menuId);
		builder.append(", selected=");
		builder.append(selected);
		builder.append("]");
		return builder.toString();
	}
	
	
}

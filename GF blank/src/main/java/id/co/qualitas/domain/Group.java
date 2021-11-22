package id.co.qualitas.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

public class Group extends MasterDomain{
	private String id;
	private String groupName;
	private String tipe;
	private Integer totalPage;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getTipe() {
		return tipe;
	}
	public void setTipe(String tipe) {
		this.tipe = tipe;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Group [id=");
		builder.append(id);
		builder.append(", groupName=");
		builder.append(groupName);
		builder.append(", tipe=");
		builder.append(tipe);
		builder.append(", totalPage=");
		builder.append(totalPage);
		builder.append("]");
		return builder.toString();
	}
	
	
}

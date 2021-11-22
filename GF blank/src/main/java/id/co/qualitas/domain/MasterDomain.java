package id.co.qualitas.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class MasterDomain{
	@Column(columnDefinition="tinyint(1) default true", nullable=false)
	protected int enabled;
	@Column(columnDefinition="tinyint(1) default false", nullable=false)
	protected int deleted;
	@Column(columnDefinition="varchar(255) default 'SYSTEM'", nullable=false)
	protected String createdBy;
	@Column(columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP", nullable=false)
	protected Date createdDate;
	@Column(length=255)
	protected String updatedBy;
	@Column(columnDefinition="TIMESTAMP" )
	protected Date updatedDate;
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	
}

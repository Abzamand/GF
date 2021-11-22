package id.co.qualitas.domain;

import java.util.Date;

public class FilterReport {
	private Date from;
	private Date to;
	private String idSloc;
	private String idPlant;
	private String status;
	private String docType;
	private Integer totalPage;
	private Integer curPage;
	private Object search;
	public Date getFrom() {
		return from;
	}
	public void setFrom(Date from) {
		this.from = from;
	}
	public Date getTo() {
		return to;
	}
	public void setTo(Date to) {
		this.to = to;
	}
	public String getIdSloc() {
		return idSloc;
	}
	public void setIdSloc(String idSloc) {
		this.idSloc = idSloc;
	}
	public String getIdPlant() {
		return idPlant;
	}
	public void setIdPlant(String idPlant) {
		this.idPlant = idPlant;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getCurPage() {
		return curPage;
	}
	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}
	public Object getSearch() {
		return search;
	}
	public void setSearch(Object search) {
		this.search = search;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FilterReport [from=");
		builder.append(from);
		builder.append(", to=");
		builder.append(to);
		builder.append(", idSloc=");
		builder.append(idSloc);
		builder.append(", idPlant=");
		builder.append(idPlant);
		builder.append(", status=");
		builder.append(status);
		builder.append(", docType=");
		builder.append(docType);
		builder.append(", totalPage=");
		builder.append(totalPage);
		builder.append(", curPage=");
		builder.append(curPage);
		builder.append(", search=");
		builder.append(search);
		builder.append("]");
		return builder.toString();
	}
	
	
}

package id.co.qualitas.domain;


public class Sloc extends MasterDomain {
	private String id;
	private String idPlant;
	private String idSloc;
	private String slocName;
	private String plantName;
	private String loadingPoint;
	private Integer totalPage;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdPlant() {
		return idPlant;
	}
	public void setIdPlant(String idPlant) {
		this.idPlant = idPlant;
	}
	public String getIdSloc() {
		return idSloc;
	}
	public void setIdSloc(String idSloc) {
		this.idSloc = idSloc;
	}
	public String getSlocName() {
		return slocName;
	}
	public void setSlocName(String slocName) {
		this.slocName = slocName;
	}
	public String getPlantName() {
		return plantName;
	}
	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}
	public String getLoadingPoint() {
		return loadingPoint;
	}
	public void setLoadingPoint(String loadingPoint) {
		this.loadingPoint = loadingPoint;
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
		builder.append("Sloc [id=");
		builder.append(id);
		builder.append(", idPlant=");
		builder.append(idPlant);
		builder.append(", idSloc=");
		builder.append(idSloc);
		builder.append(", slocName=");
		builder.append(slocName);
		builder.append(", plantName=");
		builder.append(plantName);
		builder.append(", loadingPoint=");
		builder.append(loadingPoint);
		builder.append(", totalPage=");
		builder.append(totalPage);
		builder.append("]");
		return builder.toString();
	}
	
}

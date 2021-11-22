package id.co.qualitas.domain;


public class Material extends MasterDomain {
	private String id;
	private String name;
	private String idPlant;
	private String idSloc;
	private String materialType;
	private String materialTypeName;
	private Integer totalPage;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	public String getMaterialTypeName() {
		return materialTypeName;
	}
	public void setMaterialTypeName(String materialTypeName) {
		this.materialTypeName = materialTypeName;
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
		builder.append("Material [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", idPlant=");
		builder.append(idPlant);
		builder.append(", idSloc=");
		builder.append(idSloc);
		builder.append(", materialType=");
		builder.append(materialType);
		builder.append(", materialTypeName=");
		builder.append(materialTypeName);
		builder.append(", totalPage=");
		builder.append(totalPage);
		builder.append("]");
		return builder.toString();
	}
}

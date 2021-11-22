package id.co.qualitas.domain;

import java.util.Arrays;

public class EmployeeMD extends MasterDomain {
	private String userlogin;
	private int id;
	private String name;
	private String role;
	int status;
	private String email;
	byte[] photo;
	private String photoString;
	private Integer totalPage;
	private String password;
	private String idPlant;
	private String namePlant;
	private int isPasswordChanged;
	
	public int getIsPasswordChanged() {
		return isPasswordChanged;
	}

	public void setIsPasswordChanged(int isPasswordChanged) {
		this.isPasswordChanged = isPasswordChanged;
	}

	public String getNamePlant() {
		return namePlant;
	}

	public void setNamePlant(String namePlant) {
		this.namePlant = namePlant;
	}
	public String getIdPlant() {
		return idPlant;
	}
	public void setIdPlant(String idPlant) {
		this.idPlant = idPlant;
	}
	public String getUserlogin() {
		return userlogin;
	}
	public void setUserlogin(String userlogin) {
		this.userlogin = userlogin;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public String getPhotoString() {
		return photoString;
	}
	public void setPhotoString(String photoString) {
		this.photoString = photoString;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EmployeeMD [userlogin=");
		builder.append(userlogin);
		builder.append(", id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", role=");
		builder.append(role);
		builder.append(", status=");
		builder.append(status);
		builder.append(", email=");
		builder.append(email);
		builder.append(", photo=");
		builder.append(Arrays.toString(photo));
		builder.append(", photoString=");
		builder.append(photoString);
		builder.append(", totalPage=");
		builder.append(totalPage);
		builder.append(", password=");
		builder.append(password);
		builder.append("]");
		return builder.toString();
	}


}

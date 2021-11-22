package id.co.qualitas.domain;

import java.util.Arrays;

import javax.persistence.Lob;


public class VendorMD extends MasterDomain {
	
	private Integer id;
	private String idSap;
	private String userlogin;
	private String name;
	private String role;
	private String idSloc;
	private String sloc;
	private String idPlant;
	private String plant;
	private String phone;
	private String address;
	int status;
	private String email;
	byte[] photo;
	private String photoString;
	private Object photoObj;
	private Integer totalPage;
	private String password;
	
	private String baseIdSloc;
	private String baseIdPlant;
	

	private int isPasswordChanged;
	
	public int getIsPasswordChanged() {
		return isPasswordChanged;
	}

	public void setIsPasswordChanged(int isPasswordChanged) {
		this.isPasswordChanged = isPasswordChanged;
	}
	
	public String getBaseIdSloc() {
		return baseIdSloc;
	}
	public void setBaseIdSloc(String baseIdSloc) {
		this.baseIdSloc = baseIdSloc;
	}
	public String getBaseIdPlant() {
		return baseIdPlant;
	}
	public void setBaseIdPlant(String baseIdPlant) {
		this.baseIdPlant = baseIdPlant;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIdSap() {
		return idSap;
	}
	public void setIdSap(String idSap) {
		this.idSap = idSap;
	}
	public String getUserlogin() {
		return userlogin;
	}
	public void setUserlogin(String userlogin) {
		this.userlogin = userlogin;
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
	public String getIdSloc() {
		return idSloc;
	}
	public void setIdSloc(String idSloc) {
		this.idSloc = idSloc;
	}
	public String getSloc() {
		return sloc;
	}
	public void setSloc(String sloc) {
		this.sloc = sloc;
	}
	public String getIdPlant() {
		return idPlant;
	}
	public void setIdPlant(String idPlant) {
		this.idPlant = idPlant;
	}
	public String getPlant() {
		return plant;
	}
	public void setPlant(String plant) {
		this.plant = plant;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public Object getPhotoObj() {
		return photoObj;
	}
	public void setPhotoObj(Object photoObj) {
		this.photoObj = photoObj;
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
		builder.append("VendorMD [id=");
		builder.append(id);
		builder.append(", idSap=");
		builder.append(idSap);
		builder.append(", userlogin=");
		builder.append(userlogin);
		builder.append(", name=");
		builder.append(name);
		builder.append(", role=");
		builder.append(role);
		builder.append(", idSloc=");
		builder.append(idSloc);
		builder.append(", sloc=");
		builder.append(sloc);
		builder.append(", idPlant=");
		builder.append(idPlant);
		builder.append(", plant=");
		builder.append(plant);
		builder.append(", phone=");
		builder.append(phone);
		builder.append(", address=");
		builder.append(address);
		builder.append(", status=");
		builder.append(status);
		builder.append(", email=");
		builder.append(email);
		builder.append(", photo=");
		builder.append(Arrays.toString(photo));
		builder.append(", photoString=");
		builder.append(photoString);
		builder.append(", photoObj=");
		builder.append(photoObj);
		builder.append(", totalPage=");
		builder.append(totalPage);
		builder.append(", password=");
		builder.append(password);
		builder.append("]");
		return builder.toString();
	}
	
}

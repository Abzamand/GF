package id.co.qualitas.domain;


public class MemberRequest extends MasterDomain {
	private String id;
	private String full_name;
	private String nickname;
	private String email;
	private String password;
	private byte[] photo;
	private String photoFilename;
	private byte[] cProfile;
	private String cProfileFilename;
	private Object listCatalog;
	private Object listRole;
	private String username;
	private int enabledMember;
	private boolean resetPassword;
	private String updated_date;
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getEnabledMember() {
		return enabledMember;
	}
	public void setEnabledMember(int enabledMember) {
		this.enabledMember = enabledMember;
	}
	public String getPhotoFilename() {
		return photoFilename;
	}
	public void setPhotoFilename(String photoFilename) {
		this.photoFilename = photoFilename;
	}
	public String getcProfileFilename() {
		return cProfileFilename;
	}
	public void setcProfileFilename(String cProfileFilename) {
		this.cProfileFilename = cProfileFilename;
	}
	public String getUpdated_date() {
		return updated_date;
	}
	public void setUpdated_date(String updated_date) {
		this.updated_date = updated_date;
	}
	public boolean isResetPassword() {
		return resetPassword;
	}
	public void setResetPassword(boolean resetPassword) {
		this.resetPassword = resetPassword;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public byte[] getcProfile() {
		return cProfile;
	}
	public void setcProfile(byte[] cProfile) {
		this.cProfile = cProfile;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public Object getListCatalog() {
		return listCatalog;
	}
	public void setListCatalog(Object listCatalog) {
		this.listCatalog = listCatalog;
	}
	public Object getListRole() {
		return listRole;
	}
	public void setListRole(Object listRole) {
		this.listRole = listRole;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}

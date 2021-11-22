package id.co.qualitas.domain;

public class Device extends MasterDomain {
	private String id;
	private String device;
	private String phoneNo;
	private int username;
	private String userlogin;
	private String ssaid;
	private int status;
	private Integer totalPage;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public int getUsername() {
		return username;
	}
	public void setUsername(int username) {
		this.username = username;
	}
	public String getUserlogin() {
		return userlogin;
	}
	public void setUserlogin(String userlogin) {
		this.userlogin = userlogin;
	}
	public String getSsaid() {
		return ssaid;
	}
	public void setSsaid(String ssaid) {
		this.ssaid = ssaid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
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
		builder.append("Device [id=");
		builder.append(id);
		builder.append(", device=");
		builder.append(device);
		builder.append(", phoneNo=");
		builder.append(phoneNo);
		builder.append(", username=");
		builder.append(username);
		builder.append(", userlogin=");
		builder.append(userlogin);
		builder.append(", ssaid=");
		builder.append(ssaid);
		builder.append(", status=");
		builder.append(status);
		builder.append(", totalPage=");
		builder.append(totalPage);
		builder.append("]");
		return builder.toString();
	}

}

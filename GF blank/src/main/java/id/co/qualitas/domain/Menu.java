package id.co.qualitas.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Menu extends MasterDomain implements Serializable{
	private static final long serialVersionUID = 1579086272072691905L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private int parentId;
	private String menuName;
	private String path;
	@Column(length=2)
	private String menuType;
	private int priority;
	private String templateUrl;
	private String controller;
	private String pathHastag;
	@Transient
	private List<Menu> menu;
	
	public List<Menu> getMenu() {
		return menu;
	}
	public void setMenu(List<Menu> menu) {
		this.menu = menu;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getMenuType() {
		return menuType;
	}
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getTemplateUrl() {
		return templateUrl;
	}
	public void setTemplateUrl(String templateUrl) {
		this.templateUrl = templateUrl;
	}
	public String getController() {
		return controller;
	}
	public void setController(String controller) {
		this.controller = controller;
	}
	public String getPathHastag() {
		return pathHastag;
	}
	public void setPathHastag(String pathHastag) {
		this.pathHastag = pathHastag;
	}
}

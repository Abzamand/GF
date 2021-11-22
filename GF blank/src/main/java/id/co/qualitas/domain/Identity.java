package id.co.qualitas.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Identity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1579086272072691905L;

	@Id
	@Column(length= 20)
	private String id;
	
	private int lastCounter;
	
	private int digit;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getLastCounter() {
		return lastCounter;
	}

	public void setLastCounter(int lastCounter) {
		this.lastCounter = lastCounter;
	}

	public int getDigit() {
		return digit;
	}

	public void setDigit(int digit) {
		this.digit = digit;
	}

}

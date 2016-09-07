package com.mogikanensoftware.xml.service.data.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Item implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false)
	private Date dateTime;

	@Column(nullable = false)
	private String itemType;

	@Column
	private String targetType;

	@Column
	private String targetName;

	@Column
	private String message;

	@ManyToOne(fetch = FetchType.EAGER)
	private Result result;
	
	public Item() {
		super();
	}

	public Item(Date dateTime, String itemType, String targetType, String targetName, String message) {
		super();
		this.dateTime = dateTime;
		this.itemType = itemType;
		this.targetType = targetType;
		this.targetName = targetName;
		this.message = message;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	
	@Override
	public String toString() {
		return "Item [id=" + id + ", dateTime=" + dateTime + ", itemType=" + itemType + ", targetType=" + targetType
				+ ", targetName=" + targetName + ", message=" + message + "]";
	}


}

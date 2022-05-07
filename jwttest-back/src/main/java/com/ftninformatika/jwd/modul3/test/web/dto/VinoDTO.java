package com.ftninformatika.jwd.modul3.test.web.dto;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;

public class VinoDTO {

	Long id;
	
	String name;
	
	@Length(max = 120)
	String description;
	
	@Min(0)
	int year;
	
	int stock;
	
	@Min(0)
	double price;
	
	Long typeId;
	String typeName;
	
	Long wineryId;
	String wineryName;
	
	public VinoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Long getWineryId() {
		return wineryId;
	}
	public void setWineryId(Long wineryId) {
		this.wineryId = wineryId;
	}
	public String getWineryName() {
		return wineryName;
	}
	public void setWineryName(String wineryName) {
		this.wineryName = wineryName;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	
}

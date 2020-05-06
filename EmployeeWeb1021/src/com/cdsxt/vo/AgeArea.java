package com.cdsxt.vo;

public class AgeArea {
	private String area;
	private int count;
	public AgeArea(String area, int count) {
		super();
		this.area = area;
		this.count = count;
	}
	public AgeArea() {
		super();
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
}

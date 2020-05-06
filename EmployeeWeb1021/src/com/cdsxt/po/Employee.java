package com.cdsxt.po;

public class Employee {
	private int id;
	private String name;
	private int age;
	private int gender;
	public Employee(int id, String name, int age, int gender) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
	}
	public Employee() {
		super();
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	
	
}

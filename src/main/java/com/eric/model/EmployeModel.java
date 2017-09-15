package com.eric.model;

import java.io.Serializable;

/**
 * 企業員工資料模型
 * @author yung.ho
 *
 */
public class EmployeModel implements Serializable {

	/**
	 * 自動產生序列號 
	 */
	private static final long serialVersionUID = 8633274819932809940L;
	private Long id;			//員工編號
	private String name;		//員工姓名
	private String deptName;	//部門名稱
	private Long phone;			//分機號碼
	private String seat;		//座位
	
	public EmployeModel() {
		super();
	}
	
	public EmployeModel(Long id, String name, String deptName, Long phone, String seat) {
		super();
		this.id = id;
		this.name = name;
		this.deptName = deptName;
		this.phone = phone;
		this.seat = seat;
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
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public Long getPhone() {
		return phone;
	}
	public void setPhone(Long phone) {
		this.phone = phone;
	}
	public String getSeat() {
		return seat;
	}
	public void setSeat(String seat) {
		this.seat = seat;
	}
	
	
}

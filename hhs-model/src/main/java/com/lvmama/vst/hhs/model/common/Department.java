package com.lvmama.vst.hhs.model.common;

import java.io.Serializable;
import java.util.List;

public class Department implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2734881058998923978L;
	
	private int deptNo;
	private String deptName;
	private String deanName;
	private List<String> courses;

	public Department() {
		
	}

	public Department(int deptNo, String deptName, String deanName, List<String> courses) {
		super();
		this.deptNo = deptNo;
		this.deptName = deptName;
		this.deanName = deanName;
		this.courses = courses;
	}

	public int getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeanName() {
		return deanName;
	}

	public void setDeanName(String deanName) {
		this.deanName = deanName;
	}

	public List<String> getCourses() {
		return courses;
	}

	public void setCourses(List<String> courses) {
		this.courses = courses;
	}

	
}

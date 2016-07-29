package com.lvmama.vst.hhs.model.stamp;

import java.io.Serializable;


public class StampContact implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String lastName;
	private String firstName;
	private String fullName;
	private String phone;
	private String gender;
	private String idType;
	private String idNo;
	private String email;
	//字符串：PEOPLE_TYPE_ADULT=成人，PEOPLE_TYPE_CHILD=儿童，PEOPLE_TYPE_OLDER=老人
	private String peopleType;
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPeopleType() {
		return peopleType;
	}
	public void setPeopleType(String peopleType) {
		this.peopleType = peopleType;
	}
	@Override
	public String toString() {
		return "StampContact [lastName=" + lastName + ", firstName=" + firstName + ", fullName=" + fullName
				+ ", phone=" + phone + ", gender=" + gender + ", idType=" + idType + ", idNo=" + idNo + ", email="
				+ email + ", peopleType=" + peopleType + "]";
	}
}

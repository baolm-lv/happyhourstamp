package com.lvmama.vst.common.utils;

class Uid {
	String user;
	int id;

	public Uid() {

	}
	
	public Uid(int id, String user) {
		this.id = id;
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
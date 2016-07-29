/**
 * 
 */
package com.lvmama.vst.hhs.common.web;

import java.io.Serializable;

import com.google.gson.Gson;

/**
 * @author baolm
 *
 */
public class BaseResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5606983754692908256L;
	
	private String code;
	private String msg;
	
	public BaseResponse() {
		super();
	}
	
	public BaseResponse(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}

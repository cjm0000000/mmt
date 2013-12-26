package com.github.cjm0000000.mmt.shared.access;

/**
 * API return code
 * @author lemon
 * @version 1.0
 *
 */
public class ReturnCode {
	private int errcode;
	private String errmsg;
	
	public int getErrcode() {
		return errcode;
	}
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
}

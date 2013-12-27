package com.github.cjm0000000.mmt.shared.access;

import com.github.cjm0000000.mmt.core.access.JSONResponse;

/**
 * API return code
 * @author lemon
 * @version 1.0
 *
 */
public class ReturnCode implements JSONResponse {
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

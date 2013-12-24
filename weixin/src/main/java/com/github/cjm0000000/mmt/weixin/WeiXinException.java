package com.github.cjm0000000.mmt.weixin;

import com.github.cjm0000000.mmt.core.MmtException;

/**
 * WeiXinException
 * 
 * @author lemon
 * @version 1.0
 * 
 */
public class WeiXinException extends MmtException {
	private static final long serialVersionUID = 7389653752728366688L;

	/**
	 * Constructs an {@code WeiXinException} with {@code null} as its error detail
	 * message.
	 */
	public WeiXinException() {
		super();
	}

	public WeiXinException(String message) {
		super(message);
	}

	public WeiXinException(String message, Throwable cause) {
		super(message, cause);
	}

	public WeiXinException(Throwable cause) {
		super(cause);
	}
}

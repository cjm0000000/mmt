package com.github.cjm0000000.mmt.yixin;

import com.github.cjm0000000.mmt.core.MmtException;

/**
 * YiXinException
 * @author lemon
 * @version 1.0
 *
 */
public class YiXinException extends MmtException {
	static final long serialVersionUID = 1662900257135756746L;

	/**
	 * Constructs an {@code WeiXinException} with {@code null} as its error detail
	 * message.
	 */
	public YiXinException() {
		super();
	}

	public YiXinException(String message) {
		super(message);
	}

	public YiXinException(String message, Throwable cause) {
		super(message, cause);
	}

	public YiXinException(Throwable cause) {
		super(cause);
	}
}

package com.github.cjm0000000.mmt.web;

import com.github.cjm0000000.mmt.core.MmtException;

/**
 * WebException
 * 
 * @author lemon
 * @version 2.0
 * 
 */
public class WebException extends MmtException {
	private static final long serialVersionUID = 6194875590742810733L;

	/**
	 * Constructs an {@code WebException} with {@code null} as its error detail
	 * message.
	 */
	public WebException() {
		super();
	}

	public WebException(String message) {
		super(message);
	}

	public WebException(String message, Throwable cause) {
		super(message, cause);
	}

	public WebException(Throwable cause) {
		super(cause);
	}
}

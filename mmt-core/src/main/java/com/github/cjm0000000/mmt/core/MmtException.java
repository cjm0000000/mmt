package com.github.cjm0000000.mmt.core;

/**
 * MmtException
 * 
 * @author lemon
 * @version 1.0
 * 
 */
public class MmtException extends RuntimeException {
	private static final long serialVersionUID = 3122515464042906415L;

	/**
	 * Constructs an {@code MmtException} with {@code null} as its error detail
	 * message.
	 */
	public MmtException() {
		super();
	}

	public MmtException(String message) {
		super(message);
	}

	public MmtException(String message, Throwable cause) {
		super(message, cause);
	}

	public MmtException(Throwable cause) {
		super(cause);
	}
}

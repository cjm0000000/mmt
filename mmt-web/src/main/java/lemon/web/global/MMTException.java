package lemon.web.global;

/**
 * MMTException<br>
 * 用于处理Spring抛出的异常
 * @author lemon
 * @version 1.0
 *
 */
public class MMTException extends RuntimeException {
	static final long serialVersionUID = 1662900257135756746L;

	/**
	 * Constructs an {@code MMTException} with {@code null} as its error detail
	 * message.
	 */
	public MMTException() {
		super();
	}

	public MMTException(String message) {
		super(message);
	}

	public MMTException(String message, Throwable cause) {
		super(message, cause);
	}

	public MMTException(Throwable cause) {
		super(cause);
	}
}

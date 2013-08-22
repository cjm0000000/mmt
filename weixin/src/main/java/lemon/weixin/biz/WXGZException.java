package lemon.weixin.biz;

/**
 * WXGZException
 * @author lemon
 *
 */
public class WXGZException extends Exception {
	static final long serialVersionUID = 1662900257135756746L;

	/**
	 * Constructs an {@code WXGZException} with {@code null} as its error detail
	 * message.
	 */
	public WXGZException() {
		super();
	}

	public WXGZException(String message) {
		super(message);
	}

	public WXGZException(String message, Throwable cause) {
		super(message, cause);
	}

	public WXGZException(Throwable cause) {
		super(cause);
	}
}

package lemon.weixin.biz;

/**
 * WXGZException
 * @author lemon
 *
 */
public class WeiXinException extends RuntimeException {
	static final long serialVersionUID = 1662900257135756746L;

	/**
	 * Constructs an {@code WXGZException} with {@code null} as its error detail
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

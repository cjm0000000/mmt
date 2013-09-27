package lemon.weixin.biz;

import lemon.shared.MmtException;

/**
 * WeiXinException
 * @author lemon
 * @version 1.0
 *
 */
public class WeiXinException extends MmtException {
	static final long serialVersionUID = 1662900257135756746L;

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

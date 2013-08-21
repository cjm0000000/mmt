package lemon.weixin.gateway;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lemon.shared.api.MmtAPI;
import lemon.weixin.biz.WXGZAPI;

/**
 * The gateway of weixin
 * @author lemzhang
 *
 */
public class WXMPGateWay extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		MmtAPI api = new WXGZAPI();
		String msg = null;
		out.print(api.getReplayMsg(msg));
	}


}

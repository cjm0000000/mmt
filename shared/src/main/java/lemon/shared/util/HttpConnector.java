package lemon.shared.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import lemon.shared.MMTContext;

/**
 * HTTP 消息管理器
 * 
 * @author lemon
 * @version 1.0
 * 
 */
public class HttpConnector {
	private static final String POST = "POST";
	private static final String GET = "GET";

	/**
	 * 发送POST消息
	 * 
	 * @param url
	 * @return
	 */
	public static String post(String url) {
		try {
			return connect(url, POST, null, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发送POST消息
	 * 
	 * @param url
	 * @param msg
	 * @return
	 */
	public static String post(String url, String msg) {
		try {
			return connect(url, POST, msg, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发送POST消息
	 * 
	 * @param url
	 * @param msg
	 * @param params
	 * @return
	 */
	public static String post(String url, String msg, Map<String, Object> params) {
		try {
			return connect(url, POST, msg, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取消息
	 * 
	 * @param url
	 * @return
	 */
	public static String get(String url) {
		try {
			return connect(url, GET, null, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取消息
	 * @param url
	 * @param params
	 * @return
	 */
	public static String get(String url, Map<String, Object> params) {
		try {
			return connect(url, GET, null, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * connect to the URL and send a message
	 * 
	 * @param strUrl
	 * @param method
	 * @param sendMsg
	 * @param params
	 * @return
	 * @throws IOException
	 */
	private static String connect(String strUrl, String method, String sendMsg,
			Map<String, Object> params) throws IOException {
		if (null != params)
			strUrl = compile(strUrl, params);
		URL url = new URL(strUrl);
		HttpURLConnection con = null;
		try {
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod(method);
			con.setConnectTimeout(5000);
			if (method.equals(POST)) {
				con.setDoOutput(true);
				// send message
				sendBytes(con, sendMsg);
			} else
				con.setDoOutput(false);
			// get reply message
			return getReceive(con);
		} finally {
			if (con != null)
				con.disconnect();
		}
	}

	/**
	 * Send bytes
	 * 
	 * @param con
	 * @param msg
	 * @throws IOException
	 */
	private static void sendBytes(HttpURLConnection con, String msg)
			throws IOException {
		OutputStream os = null;
		try {
			os = con.getOutputStream();
			os.write(msg.getBytes(MMTContext.LOCAL_CHARSET));
			os.flush();
		} finally {
			if (null != os)
				os.close();
		}
	}

	/**
	 * 获取回复消息
	 * 
	 * @param con
	 * @return
	 * @throws IOException
	 */
	private static String getReceive(HttpURLConnection con) throws IOException {
		InputStream is = null;
		try {
			is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					MMTContext.LOCAL_CHARSET));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null)
				sb.append(line);
			return sb.toString();
		} finally {
			if (null != is)
				is.close();
		}
	}

	/**
	 * 把参数编译到URL
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	private static String compile(String url, Map<String, Object> params) {
		if (null == params || params.size() == 0)
			return url;
		StringBuffer sb = new StringBuffer();
		sb.append("?");
		for (Map.Entry<String, Object> entry : params.entrySet())
			sb.append(entry.getKey()).append("=").append(entry.getValue())
					.append("&");
		sb.delete(sb.length() - 1, sb.length());
		return url + sb.toString();
	}
	
}

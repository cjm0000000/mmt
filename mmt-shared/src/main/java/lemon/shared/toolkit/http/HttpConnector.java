package lemon.shared.toolkit.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.github.cjm0000000.mmt.core.MmtException;
import com.github.cjm0000000.mmt.core.config.MmtCharset;

/**
 * HTTP 消息管理器
 * 
 * @author lemon
 * @version 1.0
 * 
 */
public final class HttpConnector {
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
			throw new MmtException("发送消息遇到错误。", e.getCause());
		}
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
			throw new MmtException("发送消息遇到错误。", e.getCause());
		}
	}
	
	/**
	 * 发送POST消息
	 * 
	 * @param url
	 * @param msg
	 * @param params
	 * @return
	 */
	public static String post(String url, Map<String, Object> params) {
		try {
			return connect(url, POST, null, params);
		} catch (IOException e) {
			throw new MmtException("发送消息遇到错误。", e.getCause());
		}
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
			throw new MmtException("发送消息遇到错误。", e.getCause());
		}
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
			throw new MmtException("接收消息遇到错误。", e.getCause());
		}
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
			throw new MmtException("接收消息遇到错误。", e.getCause());
		}
	}
	
	/**
	 * 上传文件
	 * @param strUrl
	 * @param params
	 * @param file
	 * @param fileName
	 * @return
	 */
	public static String uploadFile(String strUrl, Map<String, Object> params,
			byte[] file,String fileName) {
		try {
			if (null != params)
				strUrl = compile(strUrl, params);
			URL url = new URL(strUrl);
			HttpURLConnection con = null;
			try {
				con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("POST");
				con.setConnectTimeout(60000);
				con.setDoOutput(true);
				
				String BOUNDARY = "----WebKitFormBoundaryANMJ5hvoEYHzawuE"; // 分隔符
				
				StringBuilder sb = new StringBuilder();
				sb.append("--");
				sb.append(BOUNDARY);
				sb.append("\r\n");
				sb.append("Content-Disposition: form-data; filelength="
						+ file.length + "; filename=\"" + fileName + "\"\r\n");
				sb.append("Content-Type: application/octet-stream\r\n\r\n");
				
				byte[] data = sb.toString().getBytes();  
	            byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();  
	              
				con.setRequestProperty("Content-Type",
						"multipart/form-data; boundary=" + BOUNDARY); // 设置表单类型和分隔符
				con.setRequestProperty("Content-Length",
						String.valueOf(data.length + end_data.length)); // 设置内容长度
				
				//上传
				try (OutputStream os = con.getOutputStream()) {
					os.write(data);
					os.write(file);
					os.write(end_data);
					os.flush();
				} 
				// get reply message
				return getReceive(con);
			} finally {
				if (con != null)
					con.disconnect();
			}
		} catch (IOException e) {
			throw new MmtException("上传文件遇到错误。", e.getCause());
		}
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
				if(null != sendMsg)
					sendBytes(con, sendMsg.getBytes(MmtCharset.LOCAL_CHARSET));
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
	private static void sendBytes(HttpURLConnection con, byte[] msg)
			throws IOException {
		try (OutputStream os = con.getOutputStream()) {
			os.write(msg);
			os.flush();
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
		try (InputStream is = con.getInputStream()){
			BufferedReader br = new BufferedReader(new InputStreamReader(is, MmtCharset.LOCAL_CHARSET));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null)
				sb.append(line);
			return sb.toString();
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

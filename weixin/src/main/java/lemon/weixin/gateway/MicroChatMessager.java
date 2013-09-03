package lemon.weixin.gateway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static lemon.weixin.WeiXin.LOCAL_CHARSET;
import static lemon.weixin.WeiXin.TARGET_CHARSET;

/**
 * WeiXin message robot
 * @author lemon
 *
 */
public class MicroChatMessager {
	
	/**
	 * send message via HTTP
	 * @param strUrl
	 * @param method
	 * @param msg
	 * @param parameters
	 * @return
	 * @throws IOException
	 */
	public static String send(String strUrl,String method,String msg, Object... parameters) throws IOException {
		URL url = new URL(compile(strUrl, parameters));
		HttpURLConnection con  = null;
		try{
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod(method);
			con.setConnectTimeout(5000);
			if(method.equals("POST")){
				con.setDoOutput(true);
				//send message
				sendBytes(con, msg);
			}
			else
				con.setDoOutput(false);
			// get reply message
			return getReceive(con);
		}finally{
			if(con != null)
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
			os.write(msg.getBytes(TARGET_CHARSET));
			os.flush();
		} finally {
			if (null != os)
				os.close();
		}
	}
	
	/**
	 * compile URL<BR>
	 * regex=#{MMT}
	 * @param url
	 * @param params
	 * @return
	 */
	private static String compile(String url, Object... params){
		if(null == params || params.length == 0)
			return url;
		for (Object object : params)
			url = url.replaceFirst("#\\{MMT\\}",String.valueOf(object));
		return url;
	}
	
	private static String getReceive(HttpURLConnection con) throws IOException {
		InputStream is = null;
		try {
			is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,LOCAL_CHARSET));
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

}

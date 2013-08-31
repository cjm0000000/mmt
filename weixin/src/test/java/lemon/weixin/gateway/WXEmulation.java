package lemon.weixin.gateway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import lemon.weixin.WeiXin;
import static lemon.weixin.util.WXHelper.LOCAL_CHARSET;
import static lemon.weixin.util.WXHelper.TARGET_CHARSET;

public class WXEmulation {
	private static final String _URL = "http://58.100.127.55/mmt-web/microchat/mmtchat0801012";

	public static void main(String[] args) {
		WXEmulation wx = new WXEmulation();
		//wx.pushTextMsg();
		wx.testSignature();
	}

	public void pushTextMsg() {
		String txtMsg = " <xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName> <CreateTime>1348831860</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[这是一个测试]]></Content><MsgId>1234567890123456</MsgId></xml>";
		try {
			String returnMsg = process(txtMsg);
			System.out.println(returnMsg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * process message
	 * 
	 * @param msg
	 * @throws IOException
	 */
	private String process(String msg) throws IOException {
		HttpURLConnection con = null;
		try {
			// Create Connection
			con = createConnection();
			// Send message
			sendBytes(con, msg);
			// get replay
			return getReceive(con);
		} finally {
			if (null != con)
				con.disconnect();
		}
	}

	/**
	 * create a connection via http
	 * 
	 * @return
	 * @throws IOException
	 */
	private HttpURLConnection createConnection() throws IOException {
		URL url = new URL(_URL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setConnectTimeout(5000);
		con.setDoOutput(true);
		return con;
	}

	/**
	 * Send bytes
	 * 
	 * @param con
	 * @param msg
	 * @throws IOException
	 */
	private void sendBytes(HttpURLConnection con, String msg)
			throws IOException {
		OutputStream os = null;
		try {
			os = con.getOutputStream();
			os.write(msg.getBytes());
			os.flush();
		} finally {
			if (null != os)
				os.close();
		}
	}

	private String getReceive(HttpURLConnection con) throws IOException {
		InputStream is = null;
		try {
			is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null)
				sb.append(line);
			return new String(sb.toString().getBytes(LOCAL_CHARSET),
					TARGET_CHARSET);
		} finally {
			if (null != is)
				is.close();
		}
	}
	
	public void testSignature(){
		String signature = "794c3811a093f3b21817d8998b979e50f647019f";
		String timestamp = "1377959222";
		String nonce = "1378062726";
		String echostr = "5918734435873848631";
		String url = "http://115.28.1.159/mmt-web/microchat/925f51c7ea78ad4581d377bb89542a7bc779cc3c?signature="+signature+"&timestamp="+timestamp+"&nonce="+nonce+"&echostr="+echostr;
		String result = WeiXin.getMsg(url);
		System.out.println(result);
	}

}

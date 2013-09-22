package lemon.shared.toolkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Weather {
	public static String test(String urlStr) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection con = null;
		try {
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setConnectTimeout(5000);

			con.setDoOutput(false);
			// get reply message
			return getReceive(con);
		} finally {
			if (con != null)
				con.disconnect();
		}
	}

	private static String getReceive(HttpURLConnection con) throws IOException {
		InputStream is = null;
		try {
			is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
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
	
	public static void main(String[] args) throws IOException{
		String cityid = "101310204";
		String url = "http://m.weather.com.cn/data5/city.xml";
		System.out.println(test(url));
		String[] citys = test(url).split(",");
		for (String str : citys) {
			String provinceCode = "310501";
			url = "http://m.weather.com.cn/data5/city"+provinceCode+".xml";
			System.out.println(test(url));
		}
		url = "http://m.weather.com.cn/data/"+cityid+".html";
		System.out.println(test(url));
	}
}

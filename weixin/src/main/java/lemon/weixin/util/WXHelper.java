package lemon.weixin.util;

import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * Some help function
 * 
 * @author lemon
 * 
 */
public final class WXHelper {
	/** This is local charset */
	public static final String LOCAL_CHARSET = "UTF-8";
	/** This is remote charset */
	public static final String TARGET_CHARSET = "GBK";
	/** CDATA prefix */
	public static final String PREFIX_CDATA = "<![CDATA[";
	/** CDATA suffix */
	public static final String SUFFIX_CDATA = "]]>";

	private static Log logger = LogFactory.getLog(WXHelper.class);

	/**
	 * Get Xstream instance
	 * 
	 * @return
	 */
	public static XStream createXstream() {
		return new XStream(new XppDriver() {
			@Override
			public HierarchicalStreamWriter createWriter(Writer out) {
				return new PrettyPrintWriter(out) {
					boolean cdata = false;

					@Override
					public void startNode(String name,
							@SuppressWarnings("rawtypes") Class clazz) {
						super.startNode(name, clazz);
						cdata = name.equalsIgnoreCase("value");
					}

					@Override
					public void setValue(String text) {
						super.setValue(text);
					}

					@Override
					protected void writeText(QuickWriter writer, String text) {
						if (cdata) {
							writer.write(PREFIX_CDATA);
							writer.write(text);
							writer.write(SUFFIX_CDATA);
						} else {
							writer.write(text);
						}
					}
				};
			}
		});
	}

	/**
	 * convert string with CDATA
	 * 
	 * @param str
	 * @return
	 */
	public static String cDATA(String str) {
		return PREFIX_CDATA + str + SUFFIX_CDATA;
	}

	public static String sha1(String str) {
		try {
			byte[] digest = MessageDigest.getInstance("SHA1").digest(
					str.getBytes());
			return toHexString(digest);
		} catch (NoSuchAlgorithmException e) {
			logger.error("ERROR:SHA1 faild! " + e.getMessage());
		}
		return null;
	}

	private static String toHexString(byte[] digest) {
		String str = "";
		String tempStr = "";

		for (int i = 0; i < digest.length; i++) {
			tempStr = (Integer.toHexString(digest[i] & 0xff));
			if (tempStr.length() == 1) {
				str = str + "0" + tempStr;
			} else {
				str = str + tempStr;
			}
		}
		return str.toLowerCase();
	}
}
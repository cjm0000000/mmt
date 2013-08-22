package lemon.weixin.util;

import java.io.Writer;

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
public class WXHelper {
	/** This is local charset */
	public static final String LOCAL_CHARSET = "UTF-8";
	/** This is remote charset */
	public static final String TARGET_CHARSET = "UTF-8";
	/** CDATA prefix */
	public static final String PREFIX_CDATA = "<![CDATA[";
	/** CDATA suffix */
	public static final String SUFFIX_CDATA = "]]>";

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
}
package lemon.shared.toolkit.xstream;

import java.io.Writer;
import java.lang.reflect.Field;

import lemon.shared.toolkit.xstream.annotations.XStreamCDATA;
import lemon.shared.toolkit.xstream.annotations.XStreamProcessCDATA;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * XStream help function
 * 
 * @author lemon
 * @version 1.0
 * 
 */
public final class XStreamHelper {

	/** CDATA prefix */
	private static final String PREFIX_CDATA = "<![CDATA[";
	/** CDATA suffix */
	private static final String SUFFIX_CDATA = "]]>";
	
	/**
	 * Get XStream XML parser
	 * 
	 * @return
	 */
	public static XStream createXMLXStream() {
		return new XStream(new XppDriver() {
			@Override
			public HierarchicalStreamWriter createWriter(Writer out) {
				return new PrettyPrintWriter(out) {
					boolean cdata = false;
					Class<?> targetClass = null;
					@Override
					public void startNode(String name,
							@SuppressWarnings("rawtypes") Class clazz) {
						super.startNode(name, clazz);
						//业务处理，对于用XStreamCDATA标记的Field，需要加上CDATA标签
						@SuppressWarnings("unchecked")
						XStreamProcessCDATA xStreamProcessCDATA = (XStreamProcessCDATA) clazz.getAnnotation(XStreamProcessCDATA.class);
						if(xStreamProcessCDATA == null){
						//if(!name.equals("xml")){
							cdata = needCDATA(targetClass, name);
						}else{
							targetClass = clazz;
						}
					}

					@Override
					protected void writeText(QuickWriter writer, String text) {
						if (cdata) {
							writer.write(cDATA(text));
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
	private static String cDATA(String str) {
		return PREFIX_CDATA + str + SUFFIX_CDATA;
	}
	
	/**
	 * scan XStreamCDATA label from all the class
	 * @param targetClass
	 * @param fieldAlias
	 * @return
	 */
	private static boolean needCDATA(Class<?> targetClass, String fieldAlias){
		boolean cdata = false;
		//first, scan self
		cdata = existsCDATA(targetClass, fieldAlias);
		if(cdata) return cdata;
		//if cdata is false, scan supperClass until java.lang.Object
		Class<?> superClass = targetClass.getSuperclass();
		while(!superClass.equals(Object.class)){
			cdata = existsCDATA(superClass, fieldAlias);
			if(cdata) return cdata;
			superClass = superClass.getClass().getSuperclass();
		}
		return false;
	}
	
	/**
	 * check fields if exists XStreamCDATA label
	 * @param clazz
	 * @param fieldAlias XStream field alias name
	 * @return
	 */
	private static boolean existsCDATA(Class<?> clazz, String fieldAlias){
		//scan fields
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			//1. exists XStreamCDATA
			if(field.getAnnotation(XStreamCDATA.class) != null ){
				XStreamAlias xStreamAlias = field.getAnnotation(XStreamAlias.class);
				//2. exists XStreamAlias
				if(null != xStreamAlias){
					if(fieldAlias.equals(xStreamAlias.value()))//matched
						return true;
				}else{// not exists XStreamAlias
					if(fieldAlias.equals(field.getName()))
						return true;
				}
			}
		}
		return false;
	}
}
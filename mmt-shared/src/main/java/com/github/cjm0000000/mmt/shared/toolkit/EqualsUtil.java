package com.github.cjm0000000.mmt.shared.toolkit;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Equals util
 * @author lemon
 * @version 1.1
 *
 */
public final class EqualsUtil {

	/**
	 * 深层次比较两个对象是否相等<br>
	 * 这里认为两个对象类型相同，所有字段的值都一样的话，就相等<BR>
	 * 这是个通用的比较方法，省去每个类都重写equals方法，效率在目前场景不是很重要
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static boolean deepEquals(Object obj1, Object obj2){
		//内存地址相同
		if(obj1 == obj2)
			return true;
		//其中一个为空
		if(obj1 == null || obj2 == null)
			return false;
		if(obj1.equals(obj2))
			return true;
		//类型不相同
		if(!obj1.getClass().equals(obj2.getClass()))
			return false;
		//分解|比较
		List<Field> fields1 = parserFields(obj1);
		Map<String, Object> fieldsMap1 = new HashMap<>(fields1.size());
		for (Field field : fields1) 
			try {
				field.setAccessible(true);
				fieldsMap1.put(field.getName(), field.get(obj1));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				return false;
			}
		List<Field> fields2 = parserFields(obj2);
		for (Field field : fields2) {
			field.setAccessible(true);
			Object temp = null;
			try {
				temp = field.get(obj2);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				return false;
			}
			if(temp == null){
				if(fieldsMap1.get(field.getName()) == null)
					continue;
				else
					return false;
			}else{
				if(temp.equals(fieldsMap1.get(field.getName())))
					continue;
				else return false;
			}
		}
		return true;
	}
	
	/**
	 * 提取类（包含超类）中的所有字段到List
	 * @param obj
	 * @return
	 */
	private static List<Field> parserFields(Object obj){
		List<Field> list = new ArrayList<>();
		addFields(list, obj.getClass());
		Class<?> superClass = obj.getClass().getSuperclass();
		while(!superClass.equals(Object.class)){
			addFields(list, superClass);
			superClass = superClass.getClass().getSuperclass();
		}
		return list;
	}
	
	/**
	 * 提取给定类中的私有字段到List
	 * @param list
	 * @param clz	给定的类
	 */
	private static void addFields(List<Field> list, Class<?> clz){
		Field[] fields = clz.getDeclaredFields();
		for (Field field : fields) 
			list.add(field);
	}
}

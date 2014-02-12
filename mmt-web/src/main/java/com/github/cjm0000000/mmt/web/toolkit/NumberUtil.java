package com.github.cjm0000000.mmt.web.toolkit;

/**
 * 数字处理工具
 * @author lemon
 * @version 1.1
 *
 */
public class NumberUtil {
	
	/**
	 * 取最近的2的N次方<br>
	 * Copy from HashMap
	 * @param number
	 * @return
	 */
	public static int roundUpToPowerOf2(int number) {
		// assert number >= 0 : "number must be non-negative";
		int rounded = number >= Integer.MAX_VALUE ? Integer.MAX_VALUE
				: (rounded = Integer.highestOneBit(number)) != 0 ? (Integer
						.bitCount(number) > 1) ? rounded << 1 : rounded : 1;
		return rounded;
	}
	
}

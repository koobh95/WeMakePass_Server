package com.github.koobh95.util;

/**
 * String 객체에 자주 사용되는 로직을 모아놓은 유틸리티 클래스
 * 
 * @author BH-Ku
 * @since 2024-01-10
 */
public class StringUtil {
	/**
	 * String 객체에 대해서 Null 혹은 공백 여부를 체크한다.
	 *  
	 * @param str 검증할 String 객체
	 * @return 
	 */
	public static boolean isEmpty(String str) {
		if(str == null || str.length() == 0)
			return true;
		return false;
	}
}

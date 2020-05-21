package com.server.pica.util;

import java.util.ArrayList;
import java.util.List;

// 문자열 파싱 관련
public class StringUtil {
	private static String TAG_SPLIT = "/";

	// 태그가 '/' 로 입력된 태그들 파싱해서 리스트로 던져주기
	public static List<String> insertTags(String tags) {
		String[] sp = tags.split(TAG_SPLIT);
		// 파싱실패시 null리턴
		if (sp == null || sp.length <= 0) {
			return null;
		}
		List<String> result = new ArrayList<String>();
		for (String tag : sp) {
			result.add(tag);
		}
		return result;
	}
}

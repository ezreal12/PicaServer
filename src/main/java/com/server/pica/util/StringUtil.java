package com.server.pica.util;

import java.util.ArrayList;
import java.util.List;

// ���ڿ� �Ľ� ����
public class StringUtil {
	private static String TAG_SPLIT = "/";

	// �±װ� '/' �� �Էµ� �±׵� �Ľ��ؼ� ����Ʈ�� �����ֱ�
	public static List<String> insertTags(String tags) {
		String[] sp = tags.split(TAG_SPLIT);
		// �Ľ̽��н� null����
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

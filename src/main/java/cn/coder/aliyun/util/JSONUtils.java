package cn.coder.aliyun.util;

public class JSONUtils {

	public static String getString(String json, String key) {
		if (json == null || key == null)
			return null;
		int startIndex = json.indexOf(key);
		if (startIndex == -1)
			return null;
		int start = json.indexOf("\"", startIndex + key.length() + 2);
		int end = json.indexOf("\"", start + 1);
		return json.substring(start + 1, end);
	}

}

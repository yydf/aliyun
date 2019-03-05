package cn.coder.aliyun.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class OSSUtils {
	private static final TimeZone gmtTZ = new SimpleTimeZone(0, "GMT");

	public static String formatIso8601Date(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		df.setTimeZone(gmtTZ);
		return df.format(date);
	}

}

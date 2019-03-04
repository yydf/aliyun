package cn.coder.aliyun.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class OSSUtils {
	private static final TimeZone gmtTZ = new SimpleTimeZone(0, "GMT");

	public static String formatIso8601Date(Date date) {
		SimpleDateFormat rfc822DateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		rfc822DateFormat.setTimeZone(gmtTZ);
		return rfc822DateFormat.format(date);
	}

}

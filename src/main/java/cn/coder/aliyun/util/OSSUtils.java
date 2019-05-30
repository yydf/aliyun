package cn.coder.aliyun.util;

import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class OSSUtils {
	private static final TimeZone gmtTZ = new SimpleTimeZone(0, "GMT");
	// RFC 822 Date Format
	private static final String RFC822_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss z";

	public static String formatIso8601Date(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		df.setTimeZone(gmtTZ);
		return df.format(date);
	}

	/**
	 * Formats Date to GMT string.
	 */
	public static String formatRfc822Date(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(RFC822_DATE_FORMAT, Locale.US);
		df.setTimeZone(gmtTZ);
		return df.format(date);
	}

	public static void addHeaders(HttpURLConnection conn, Map<String, String> headers) {
		for (Entry<String, String> entry : headers.entrySet()) {
			conn.addRequestProperty(entry.getKey(), entry.getValue());
		}
	}
}

package cn.coder.aliyun.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SignUtils {
	private static Mac mac2 = null;
	private static Object obj2 = new Object();

	public static String signURL(Map<String, String> parameterMap, String secretAccessKey, String url)
			throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException {
		List<String> sortedKeys = new ArrayList<String>(parameterMap.keySet());
		Collections.sort(sortedKeys);

		StringBuilder stringToSign = new StringBuilder();
		stringToSign.append("GET").append("&");
		stringToSign.append(percentEncode("/")).append("&");
		StringBuilder canonicalizedQueryString = new StringBuilder();
		for (String key : sortedKeys) {
			String value = (String) parameterMap.get(key);
			canonicalizedQueryString.append("&").append(percentEncode(key)).append("=").append(percentEncode(value));
		}
		stringToSign.append(percentEncode(canonicalizedQueryString.toString().substring(1)));
		synchronized (obj2) {
			if (mac2 == null) {
				mac2 = Mac.getInstance("HmacSHA1");
				mac2.init(new SecretKeySpec((secretAccessKey + "&").getBytes("UTF-8"),
						"http://www.w3.org/2000/09/xmldsig#hmac-sha1"));
			}
		}
		byte[] bytes = mac2.doFinal(stringToSign.toString().getBytes("UTF-8"));
		String signature = URLEncoder.encode(Base64.getEncoder().encodeToString(bytes), "UTF-8");

		StringBuilder requestURL = new StringBuilder(url);
		requestURL.append("?Signature=").append(signature);
		for (Map.Entry<String, String> e : parameterMap.entrySet()) {
			requestURL.append("&").append(e.getKey()).append("=").append(percentEncode(e.getValue()));
		}
		return requestURL.toString();
	}

	private static String percentEncode(String value) throws UnsupportedEncodingException {
		if (value == null) {
			return "";
		}
		String encoded = URLEncoder.encode(value, "UTF-8");
		return encoded.replace("+", "%20").replace("*", "%2A").replace("~", "%7E").replace("/", "%2F");
	}

	public static Map<String, String> getCommonParameters(String accessKeyId, String action) {
		Map<String, String> parameterMap = new HashMap<>();
		parameterMap.put("Action", action);
		parameterMap.put("Version", "2014-06-18");
		parameterMap.put("AccessKeyId", accessKeyId);
		parameterMap.put("Timestamp", OSSUtils.formatIso8601Date(new Date()));
		parameterMap.put("SignatureMethod", "HMAC-SHA1");
		parameterMap.put("SignatureVersion", "1.0");
		parameterMap.put("SignatureNonce", UUID.randomUUID().toString());
		parameterMap.put("Format", "JSON");
		return parameterMap;
	}

}

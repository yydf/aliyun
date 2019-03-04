package cn.coder.aliyun.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceClient {
	private static final Logger logger = LoggerFactory.getLogger(ServiceClient.class);

	protected static String getJSON(String url) {
		StringBuilder sb = new StringBuilder();
		try {
			final char[] temp = new char[1024];
			InputStream inputStream = new URL(url).openStream();
			InputStreamReader reader = new InputStreamReader(inputStream, "utf-8");
			int len;
			while ((len = reader.read(temp)) > 0) {
				sb.append(new String(temp, 0, len));
			}
			reader.close();
			// 释放资源
			inputStream.close();
		} catch (IOException e) {
			logger.error("[GET]" + url + " faild", e);
		}
		return sb.toString();
	}
}

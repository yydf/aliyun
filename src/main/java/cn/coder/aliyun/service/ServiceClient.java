package cn.coder.aliyun.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.coder.aliyun.util.OSSUtils;
import cn.coder.aliyun.util.SignUtils;

public abstract class ServiceClient {
	private static final Logger logger = LoggerFactory.getLogger(ServiceClient.class);
	private static final int CHUNK_SIZE = 102400;
	protected final String accessKeyId;
	protected final String secretAccessKey;

	public ServiceClient(String accessKey, String secretKey) {
		this.accessKeyId = accessKey;
		this.secretAccessKey = secretKey;
	}

	protected synchronized boolean sign(String resourcePath, Map<String, String> headers) {
		return SignUtils.sign(resourcePath, headers, accessKeyId, secretAccessKey);
	}

	protected synchronized String signRequest(Map<String, String> parameterMap, String url) {
		try {
			return SignUtils.signURL(parameterMap, this.secretAccessKey, url);
		} catch (Exception e) {
			logger.error("Sing request faild", e);
			return null;
		}
	}

	protected void sendRequestCore(String uri, InputStream input, Map<String, String> headers) throws IOException {
		HttpURLConnection conn = (HttpURLConnection) new URL(uri).openConnection();
		// 发送POST请求必须设置如下两行
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setUseCaches(false);
		conn.setChunkedStreamingMode(CHUNK_SIZE);
		conn.setRequestMethod("PUT");
		// 赋值header
		OSSUtils.addHeaders(conn, headers);
		// 连接
		conn.connect();

		// 发送请求参数
		if (input != null) {
			byte[] data = new byte[CHUNK_SIZE];
			OutputStream os = conn.getOutputStream();
			int ch;
			// if (listener != null)
			// {
			// int sum = 0;
			// int total = input.available();
			// while ((ch = input.read(data, 0, data.length)) != -1)
			// {
			// os.write(data, 0, ch);
			// sum += ch;
			// listener.upload(ch, sum, total);
			// }
			// }
			// else
			// {
			while ((ch = input.read(data, 0, data.length)) != -1) {
				os.write(data, 0, ch);
			}
			// }
			os.flush();
			os.close();
			input.close();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("responseCode=" + conn.getResponseCode());
			Set<Entry<String, List<String>>> headers2 = conn.getHeaderFields().entrySet();
			for (Entry<String, List<String>> entry : headers2) {
				logger.debug(entry.getKey() + ":" + entry.getValue().get(0));
			}
		}
		conn.disconnect();
	}

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

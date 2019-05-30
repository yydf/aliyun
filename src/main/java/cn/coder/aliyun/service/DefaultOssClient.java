package cn.coder.aliyun.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.coder.aliyun.OssException;
import cn.coder.aliyun.util.Mimetypes;
import cn.coder.aliyun.util.OSSUtils;

public class DefaultOssClient extends ServiceClient {
	private static final Logger logger = LoggerFactory.getLogger(DefaultOssClient.class);
	private static final String ENDPOINT = "http://%s.oss-cn-beijing.aliyuncs.com";
	private static final String HEADER_DATE = "Date";
	private static final String HEADER_CONTENT_TYPE = "Content-Type";
	private static final Mimetypes MIME = Mimetypes.getInstance();
	private String bucketName;

	public DefaultOssClient(String accessKey, String secretKey) {
		super(accessKey, secretKey);
	}

	public boolean putObject(String key, InputStream input) throws OssException {
		if (key == null || key.trim().length() == 0)
			throw new OssException("The object key can not be null");
		if (key.startsWith("/"))
			key = key.substring(1);
		return sendRequest(key, input);
	}

	private boolean sendRequest(String key, InputStream inputStream) throws OssException {
		if (bucketName == null || bucketName.trim().length() == 0)
			throw new OssException("The bucket name can not be null");
		Map<String, String> headers = new HashMap<>();
		headers.put(HEADER_DATE, OSSUtils.formatRfc822Date(new Date()));
		headers.put(HEADER_CONTENT_TYPE, MIME.getMimetype(key));
		String resourcePath = "/" + bucketName + "/" + key;
		if (sign(resourcePath, headers)) {
			String uri = String.format(ENDPOINT, bucketName) + "/" + key;
			try {
				long start = System.currentTimeMillis();
				if (logger.isDebugEnabled())
					logger.debug("开始上传=>" + resourcePath);
				sendRequestCore(uri, inputStream, headers);
				if (logger.isDebugEnabled())
					logger.debug(resourcePath + "上传完成=>" + (System.currentTimeMillis() - start) + "ms");
				return true;
			} catch (IOException e) {
				logger.error("PutObject失败", e);
			}
			return false;
		}
		logger.debug("签名失败");
		throw new OssException("签名失败");
	}

	public void setBucket(String bucket) {
		this.bucketName = bucket;
	}

}

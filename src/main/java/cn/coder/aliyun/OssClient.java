package cn.coder.aliyun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.coder.aliyun.service.DefaultOssClient;

public class OssClient {
	private static final Logger logger = LoggerFactory.getLogger(OssClient.class);
	private static DefaultOssClient oss;

	public static DefaultOssClient register(String accessKeyId, String secretAccessKey) {
		if (oss == null) {
			oss = new DefaultOssClient(accessKeyId, secretAccessKey);
			if (logger.isDebugEnabled())
				logger.debug("OSS client registered");
		}
		return oss;
	}

	public static DefaultOssClient getInstance() throws OssException {
		if (oss == null)
			throw new OssException("Register first");
		return oss;
	}
}

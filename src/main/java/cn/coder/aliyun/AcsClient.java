package cn.coder.aliyun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.coder.aliyun.service.DefaultAcsClient;

public class AcsClient {

	private static final Logger logger = LoggerFactory.getLogger(AcsClient.class);
	private static DefaultAcsClient acs;

	public static DefaultAcsClient getInstance() {
		return acs;
	}

	public static DefaultAcsClient register(String accessKeyId, String secretAccessKey) {
		acs = new DefaultAcsClient(accessKeyId, secretAccessKey);
		if(logger.isDebugEnabled())
			logger.debug("ACS client registered");
		return acs;
	}

}

package cn.coder.aliyun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.coder.aliyun.service.DefaultAcsClient;

public class AcsClient {

	private static final Logger logger = LoggerFactory.getLogger(AcsClient.class);
	private static DefaultAcsClient acs;

	public static DefaultAcsClient getInstance() throws AcsException {
		if(acs == null)
			throw new AcsException("Register first");
		return acs;
	}

	public static synchronized DefaultAcsClient register(String accessKeyId, String secretAccessKey) {
		if (acs == null) {
			acs = new DefaultAcsClient(accessKeyId, secretAccessKey);
			if (logger.isDebugEnabled())
				logger.debug("ACS client registered");
		}
		return acs;
	}

}

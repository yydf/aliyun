package cn.coder.aliyun;

import cn.coder.aliyun.service.DefaultAcsClient;

public class AcsClient {

	private static DefaultAcsClient acs;

	public static DefaultAcsClient getInstance() {
		return acs;
	}

	public static void register(String accessKeyId, String secretAccessKey) {
		acs = new DefaultAcsClient(accessKeyId, secretAccessKey, null, null);
	}

}

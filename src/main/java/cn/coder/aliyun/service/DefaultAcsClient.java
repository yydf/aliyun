package cn.coder.aliyun.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.coder.aliyun.AcsException;
import cn.coder.aliyun.util.JSONUtils;
import cn.coder.aliyun.util.SignUtils;

public class DefaultAcsClient extends ServiceClient {

	private static final Logger logger = LoggerFactory.getLogger(DefaultAcsClient.class);
	private static final Calendar calendar = Calendar.getInstance();
	private String poolKey;

	public DefaultAcsClient(String accessKey, String secretKey) {
		super(accessKey, secretKey);
	}

	public boolean sendSms(String signName, String mobile, String templateCode, String templateParam) {
		Map<String, String> parameterMap = SignUtils.getCommonParameters(this.accessKeyId, "SendSms");

		parameterMap.put("Version", "2017-05-25");
		parameterMap.put("RegionId", "cn-hangzhou");
		parameterMap.put("PhoneNumbers", mobile);
		parameterMap.put("SignName", signName);
		parameterMap.put("TemplateCode", templateCode);
		if (templateParam != null) {
			parameterMap.put("TemplateParam", templateParam);
		}
		String requestURL = signRequest(parameterMap, "https://dysmsapi.aliyuncs.com/");
		String json = getJSON(requestURL.toString());
		if (logger.isDebugEnabled())
			logger.debug("[sendSms]" + json);
		return json != null && json.contains("\"Message\":\"OK\"");
	}

	public String bindAxb(String phoneA, String phoneB, String outId) throws AcsException {
		if(this.poolKey == null || this.poolKey.trim().length() == 0)
			throw new AcsException("The pool key can not be null");
		Map<String, String> parameterMap = SignUtils.getCommonParameters(this.accessKeyId, "BindAxb");
		calendar.setTime(new Date());
		calendar.add(Calendar.SECOND, 65);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		parameterMap.put("Version", "2017-05-25");
		parameterMap.put("RegionId", "cn-hangzhou");
		parameterMap.put("Expiration", sdf.format(calendar.getTime()));
		parameterMap.put("PhoneNoA", phoneA);
		parameterMap.put("PhoneNoB", phoneB);
		parameterMap.put("PoolKey", this.poolKey);
		parameterMap.put("OutId", outId);
		String requestURL = signRequest(parameterMap, "https://dyplsapi.aliyuncs.com/");
		String json = getJSON(requestURL.toString());
		if (logger.isDebugEnabled())
			logger.debug("[bindAxb]" + json);
		// System.out.println(json);
		if (json != null && json.contains("\"Message\":\"OK\""))
			return JSONUtils.getString(json, "SecretNo");
		return null;
	}

	public void setPoolKey(String poolKey) {
		this.poolKey = poolKey;
	}

}

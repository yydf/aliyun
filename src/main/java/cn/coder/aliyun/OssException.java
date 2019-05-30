package cn.coder.aliyun;

public class OssException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 835763153702475710L;

	public OssException(String errMsg) {
		super(errMsg);
	}
}

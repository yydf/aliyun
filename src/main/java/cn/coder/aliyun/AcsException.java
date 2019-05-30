package cn.coder.aliyun;

public class AcsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8344480609252668979L;

	public AcsException(String errMsg) {
		super(errMsg);
	}
}

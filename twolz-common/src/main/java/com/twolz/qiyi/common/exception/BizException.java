/**
 * 
 */
package com.twolz.qiyi.common.exception;


import lombok.Getter;

/**
 * @author liuwei 异常基类
 * @param <T>
 * @param <T>
 */
public class BizException extends RuntimeException implements java.io.Serializable {

	private static final long serialVersionUID = 7839794124233372377L;
	
	/**
	 * 是否是动态构建的，即加参数的消息
	 */
	private boolean isDynamic=false;

	private String errorCode;
	
	private String errorMsg;

	@Getter
	private String[] errors;
	
	/**
	 * 用户数据
	 */
	private Object userData;
	
	/**
	 * 空的异常构造子
	 */
	public BizException() {
		super();
	}

	/**
	 * @param errorCode
	 */
	public BizException(String errorCode) {
		super();
		this.errorCode=errorCode;
	}
	
	/**
	 * @param errorCode
	 * @param errorMsg
	 */
	public BizException(String errorCode,String errorMsg) {
		super();
		this.errorCode=errorCode;
		this.errorMsg=errorMsg;
	}

	public BizException(String errorCode,String...errors){
		super();
		this.errors = errors;
		this.errorCode = errorCode;
	}

	/**
	 * @param errorCode
	 * @param cause
	 */
	public BizException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Object getUserData() {
		return userData;
	}

	public void setUserData(Object userData) {
		this.userData = userData;
	}

	public boolean isDynamic() {
		return isDynamic;
	}

	public void setDynamic(boolean isDynamic) {
		this.isDynamic = isDynamic;
	}

	public void setDynamicMsg(String errorCode,String errorMsg){
		this.isDynamic=true;
		this.errorCode=errorCode;
		this.errorMsg=errorMsg;
	}

	
}

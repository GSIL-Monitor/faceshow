package com.faceshow.common.MQ;

import java.io.Serializable;

public class ResultDto<T> implements Serializable {

	private static final long serialVersionUID = -4999241212328665075L;
	public static final boolean RESULT_SUCCESS = true;
	public static final boolean RESULT_FAIL = false;
	/**
	 * 执行结果(成功(SUCCESS);失败(FAIL))
	 */
	private boolean result=true;

	private long errCode;

	/**
	 * 执行结果是FAIL,errMsg表示具体的错误详情
	 */
	private String errMsg;
	
	private T object;

	public void setIntResult(int intResult){
		if (intResult>0){
			result = true;
		}else{
			result = false;
		}
	}

	public boolean isSuccess() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public boolean isResult() {
		return this.result;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	
    public T getObject() {
    	return object;
    }

	
    public void setObject(T object) {
    	this.object = object;
    }

	public long getErrCode() {
		return errCode;
	}

	public void setErrCode(long errCode) {
		this.errCode = errCode;
	}
}

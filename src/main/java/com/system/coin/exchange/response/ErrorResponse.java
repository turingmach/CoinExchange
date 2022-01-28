package com.system.coin.exchange.response;

public class ErrorResponse extends Response {

	public ErrorResponse(String errMsg) {
		super();
		this.errMsg = errMsg;
	}

	private String errMsg;

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}

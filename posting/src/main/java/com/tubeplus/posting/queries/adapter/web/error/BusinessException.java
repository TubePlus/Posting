package com.tubeplus.posting.queries.adapter.web.error;



public class BusinessException extends RuntimeException {
    //    private static final long serialVersionUID = -7099930022202674652L;	// No Usage
    private ErrorCode errorCode;

    public BusinessException(final ErrorCode errorCode, String customDisplayMessage) {
        super(customDisplayMessage);
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode) {
//        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}

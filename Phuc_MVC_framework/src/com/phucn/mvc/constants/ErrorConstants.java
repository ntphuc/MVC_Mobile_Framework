package com.phucn.mvc.constants;

public class ErrorConstants {
	
    public static final int SUCCESS = 0;              // thanh cong
    public static final int ERROR_DATA = 1;           // loi do tra ve du lieu sai
    public static final int ERROR_NO_CONNECTION = 404;  // loi ko ket noi
    public static final int ERROR_UNKNOWN = 3;        // loi ngoai le
    public static final int ERROR_CODE_SUCCESS = 200;
    
    public static final String ERROR_CODE_STRING = "errorCode";
    public static final String RESPONSE_STRING = "response";
    
    public static final int ERROR_COMMON = 201;  // loi chung

    public static final int ERROR_OBJECT_NOT_EXISTS = 212;

    public static final int ERROR_SESSION_RESET = 215;//(errorCode=215)	session time out

    public static final int ERROR_ACCOUNT_LOCKED_REQUEST = 218 ;//(errorCode=218)
    public static final int ERROR_LOCATION_DELETED = 219 ;//(errorCode=219)

}
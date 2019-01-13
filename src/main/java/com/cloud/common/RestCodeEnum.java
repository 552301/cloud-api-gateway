package com.cloud.common;

public enum RestCodeEnum {


    /****************************   码表  **********************************/

    SUCCESS(200, "Success"),
    ERROR_400_JSON(400, "Bad Request"),
    ERROR_401_JSON(401, "Unauthorized"),
    ERROR_403_JSON(403, "Forbidden"),
    ERROR_404_JSON(404, "Not Found"),
    ERROR_415_JSON(415, "Unsupported Media Type"),
    ERROR_500_JSON(500, "Internal Server Error"),
    LOGIN_FAILED(100001, "用户名或密码错误"),
    UNHANDLED_EXCEPTION(100002, "未处理异常"),
    ACCESS_DENIED(100403, "Access Denied");;

    /*********************************************************************/

    private final int code;
    private final String message;

    // 枚举默认构造函数为 private
    RestCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}

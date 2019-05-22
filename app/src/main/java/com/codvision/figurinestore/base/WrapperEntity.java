package com.codvision.figurinestore.base;

/**
 * Created by xjc on 2017/6/8.
 */

public class WrapperEntity<T> {
    /**
     * 请求结果是否成功
     */
    private boolean status;

    /**
     * 结果返回code
     */
    private String code;

    /**
     * 结果返回信息
     */
    private String message;

    /**
     * 返回时间戳
     */
    private String timeStamp;
    /**
     * 返回结果
     */
    private T data;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}

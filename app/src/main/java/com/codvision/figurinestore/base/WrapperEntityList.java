package com.codvision.figurinestore.base;

import java.util.List;

/**
 * Created by xjc on 2017/6/7.
 */

public class WrapperEntityList<T> {
    /**
     * 请求结果是否成功
     */
    private Boolean status;

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
    private List<T> data;

    public Boolean getStatus() {
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

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}

package com.company.project.core;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 统一API响应结果封装
 */
@NoArgsConstructor
@Getter
@Slf4j
public class ResponseDTO<T> {
    private int code;
    private String message;
    private T data;

    public ResponseDTO setCode(ResultCode resultCode) {
        this.code = resultCode.getCode();
        return this;
    }

    public ResponseDTO setMessage(String message) {
        this.message = message;
        return this;
    }

    public ResponseDTO setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }


    public static <T> ResponseDTO<T> success() {
        return success(null);
    }

    public static <T> ResponseDTO<T> success(T data) {
        return success(ResultCode.SUCCESS.getMsg(), data);
    }

    public static <T> ResponseDTO<T> success(String msg, T data) {
        return new ResponseDTO().setCode(ResultCode.SUCCESS).setMessage(msg).setData(data);
    }


    public static <T> ResponseDTO<T> fail(String msg) {
        return fail(msg, null);
    }

    public static <T> ResponseDTO<T> fail(String msg, T data) {
        return new ResponseDTO().setCode(ResultCode.FAIL).setMessage(msg).setData(data);
    }

    public boolean fail() {
        if (ResultCode.SUCCESS.getCode() == code) {
            return false;
        }
        return true;
    }

    /**
     * 获取结果数据
     * <p>
     * 此方法用于系统之间调用接收数据
     *
     * @param msg
     * @param param
     * @return
     * @author HuangCanCan
     */
    public T data(String msg, Object param) {
        if (this.fail()) {
            log.error(JSON.toJSONString(msg), JSON.toJSONString(param), JSON.toJSONString(this.getMessage()));
            throw new ServiceException(this.getMessage());
        }
        return this.getData();
    }

}

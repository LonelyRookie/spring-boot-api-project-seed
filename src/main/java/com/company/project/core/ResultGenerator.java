package com.company.project.core;

/**
 * 响应结果生成工具
 */
@Deprecated
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";

    public static ResponseDTO genSuccessResult() {
        return new ResponseDTO()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE);
    }

    public static <T> ResponseDTO<T> genSuccessResult(T data) {
        return new ResponseDTO()
                .setCode(ResultCode.SUCCESS)
                .setMessage(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static <T> ResponseDTO<T> genSuccessResult(String msg, T data) {
        return new ResponseDTO()
                .setCode(ResultCode.SUCCESS)
                .setMessage(msg)
                .setData(data);
    }

    public static ResponseDTO genFailResult(String message) {
        return new ResponseDTO()
                .setCode(ResultCode.FAIL)
                .setMessage(message);
    }
}

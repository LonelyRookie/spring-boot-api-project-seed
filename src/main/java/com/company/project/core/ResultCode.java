package com.company.project.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应码枚举，参考HTTP状态码的语义
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    /**
     * 请求成功
     */
    SUCCESS(200, "请求成功"),
    /**
     * 请求失败
     */
    FAIL(400, "请求失败"),
    /**
     * 未认证（签名错误）
     */
    UNAUTHORIZED(401, "未认证"),
    /**
     * 接口不存在
     */
    NOT_FOUND(404, "接口不存在"),
    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500, "服务器内部错误");

    private int code;

    private String msg;

}

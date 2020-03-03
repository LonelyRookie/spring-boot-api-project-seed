package com.company.project.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 异常处理通知
 *
 * @ClassName ExceptionHandlerAdvice
 * @Description 异常处理通知
 * @Author HuangCanCan
 * @Date 2019/8/13 17:27
 * @Version 1.0
 **/
@ControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ResponseDTO> handle(ServiceException ex) {
        log.error("业务发生异常，异常信息为：{}", ex.getMessage());
        return ResponseEntity.ok(ResponseDTO.fail(ex.getMessage()));
    }

}

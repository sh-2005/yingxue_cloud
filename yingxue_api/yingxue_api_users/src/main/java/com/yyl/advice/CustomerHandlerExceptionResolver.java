package com.yyl.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice //注解用在类上,给所有控制添加额外处理机制
public class CustomerHandlerExceptionResolver {

    private static final Logger log= LoggerFactory.getLogger(CustomerHandlerExceptionResolver.class);

    private Map<String,Object> result=new HashMap<>();

    //@ExceptionHandler 用在方法上：代表当前方法按在出现value属性指定的异常以及子类型异常时，所做的操作。
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Map<String,Object> handle(Exception e){
        if (e instanceof DataIntegrityViolationException){

            log.error("违反完整性约束："+e.getMessage());
            result.put("err_msg","违反完整性约束!");
            return result;
        }
        if (e instanceof DuplicateKeyException){
            log.error("主键冲突:",e.getMessage());
            result.put("err_msg","主键冲突!");
            return  result;
        }
        log.error("错误信息:"+e.getMessage());
        result.put("err_msg",e.getMessage());
        return result;
    }
}

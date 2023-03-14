package com.yyl.inters;

import com.yyl.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Component
public class TokenInterceptor  implements HandlerInterceptor {

    private static final Logger log= LoggerFactory.getLogger(TokenInterceptor.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取每一次请求的token信息
        String token = request.getParameter("token");
        log.info("当前传递的token为:{}",token);
        String tokenKey ="session_"+token;
        System.out.println(tokenKey);
        User o = (User) redisTemplate.opsForValue().get(tokenKey);
        if (o == null){
            throw new RuntimeException("提示：令牌无效，无效token");
        }else{
            request.setAttribute("token",token);
            request.setAttribute("user",o);  //日后在用户服务的控制器方法中可以在直接获取用户信息.
            return true;
        }
    }
}

package com.yyl.feignclients;

import com.yyl.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("api-users")
public interface UserClient {

    @GetMapping("/userInfo/{id}")
    public User user(@PathVariable("id")Integer id);

}

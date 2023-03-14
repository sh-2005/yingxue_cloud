package com.yyl.feignclients;

import com.yyl.entity.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("api-categorys")
public interface CategoryClients {

    @GetMapping("/categories/{id}")
     Category findOne(@PathVariable("id") Integer id);
}

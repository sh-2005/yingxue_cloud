package com.yyl.controller;

import com.yyl.entity.User;
import com.yyl.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    public Map<String,Object> users(@RequestParam(value = "page",defaultValue = "1") Integer pageNow,@RequestParam(value = "per_page",defaultValue = "5")
            Integer rows,  @RequestParam(required = false)String id, String name, String phone){
        HashMap<String, Object> result = new HashMap<>();
        log.info("分页信息  当前页;{},每页展示的条数;{}",pageNow,rows);
        log.info("搜索的值 id:{},name;{},phone:{}",id,name,phone);
        List<User> allKeywords = userService.findAllKeywords(pageNow,rows,id,name,phone);
        Long totalCountsByKeywords = userService.findTotalCountsByKeywords(id,name,phone);
        log.info("list的数据为:{}",allKeywords);
        log.info("总条数为:{}",totalCountsByKeywords);
        result.put("total_count",totalCountsByKeywords);
        result.put("items",allKeywords);
        return result;
    }



}

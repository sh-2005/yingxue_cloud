package com.yyl.controller;

import com.yyl.service.VideoSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping
public class ApiSearchController {

    private static final Logger log= LoggerFactory.getLogger(ApiSearchController.class);

    @Autowired
    private VideoSearchService videoSearchService;


    @GetMapping("/search/videos")
    public Map<String, Object> videos(@RequestParam(value = "q")String q,
                       @RequestParam(value = "page",defaultValue = "1")Integer page,
                       @RequestParam(value = "per_page",defaultValue = "5")Integer rows) throws IOException {
        Map<String, Object> videos = videoSearchService.videos(q, page, rows);
        return videos;

    }



}

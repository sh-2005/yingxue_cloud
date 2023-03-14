package com.yyl.feignclients;

import com.yyl.entity.Video;
import com.yyl.vo.VideoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("api-videos")
public interface VideoClients {
    @PostMapping("publish")
    public Video publish(@RequestBody Video video);
    @GetMapping("/video/findBy/{id}")
    public  VideoVo findById(@PathVariable("id") Integer videoId);

}

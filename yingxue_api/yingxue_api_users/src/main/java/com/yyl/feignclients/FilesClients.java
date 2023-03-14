package com.yyl.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
@FeignClient("api-files")
public interface FilesClients {

    @PostMapping(value = "files",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String,String> upload(@RequestPart("file") MultipartFile file);
}

package com.yyl.controller;

import com.yyl.utils.AliyunOSSUtil;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping
public class ApiFilesController {

    private static final Logger log= LoggerFactory.getLogger(ApiFilesController.class);

    @PostMapping(value = "files",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String,String> upload(@RequestPart("file")MultipartFile file){
        Map<String,String> result = new HashMap<>();
        String filename = file.getOriginalFilename();
        log.info("接收的文件名称为:{}",filename);
        log.info("接收的文件大小为;{}",file.getSize());
        //获取文件名后缀
        String extension = FilenameUtils.getExtension(filename);
        //生成一个uuid文件名称
        String uuidFileName= UUID.randomUUID().toString().replace("-","");
        //生成文件名
        String newFileName=uuidFileName+"."+extension;
        //视频名称
        String videoFileName="video/"+newFileName;
        log.info("视频名称:{}",videoFileName);
        //封面图片名称
        //截取文件名
        String[] split = newFileName.split("\\.");
        //拼接图片名
        String coverFileName="cover/"+split[0]+".jpg";
        log.info("封面图片名称:{}",coverFileName);
        //上传图片
        AliyunOSSUtil.uploadFileByte(file,"yingxue-springcloud",videoFileName);
        //截取第一帧
        AliyunOSSUtil.interceptVideoCover("yingxue-springcloud",videoFileName,coverFileName);

        result.put("video","https://yingxue-springcloud.oss-cn-beijing.aliyuncs.com/"+videoFileName);

        result.put("cover","https://yingxue-springcloud.oss-cn-beijing.aliyuncs.com/"+coverFileName);

        return result;
    }


}

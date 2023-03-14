package com.yyl.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yyl.vo.VideoVo;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class VideoConsumer {

    private static final Logger log= LoggerFactory.getLogger(VideoConsumer.class);
    
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = "videos",type = "fanout"),
            value = @Queue
    ))
    public void receive(String message) throws IOException {
        log.info("MQ接收到的video信息为:{}",message);
        VideoVo videoVo=new ObjectMapper().readValue(message,VideoVo.class);
        //创建索引请求对象
        IndexRequest indexRequest = new IndexRequest("video", "video", videoVo.getId().toString());
        indexRequest.source(message,XContentType.JSON);
        //添加索引
        IndexResponse index = restHighLevelClient.index(indexRequest,RequestOptions.DEFAULT);
        log.info("vidoe信息录入ES的状态为:{}",index.status());
    }

}

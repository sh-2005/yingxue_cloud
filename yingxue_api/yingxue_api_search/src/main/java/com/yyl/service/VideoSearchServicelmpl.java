package com.yyl.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yyl.controller.ApiSearchController;
import com.yyl.vo.VideoVo;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class VideoSearchServicelmpl implements VideoSearchService {

    private static final Logger log= LoggerFactory.getLogger(ApiSearchController.class);

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    @Override
    public Map<String, Object> videos(String q, Integer page, Integer rows) throws IOException {
        Map<String,Object> result=new HashMap<>();
        int start=(page-1)*rows;
        //创建搜索对象
        SearchRequest searchRequest = new SearchRequest();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder
                .from(start)
                .size(rows)   //queryStringQuery  多字段搜索
                .query(QueryBuilders.queryStringQuery(q).field("title"));
                    //在哪个索引库里搜索    //在哪个索引库中的哪个类型搜索
        searchRequest.indices("video").types("video").source(searchSourceBuilder);
        SearchResponse search=null;
        try {
            //参数1:请求对象   参数2:默认配置
            search = restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
            //符合条件总数
            long totalHits = search.getHits().totalHits;
            if (totalHits>0){
                result.put("total_count",totalHits);
                SearchHit[] hits = search.getHits().getHits();
                List<VideoVo> videoVos=new ArrayList<>();
                for(SearchHit hit:hits){
                    //获取当前文档json格式数据
                    String sourceAsString = hit.getSourceAsString();
                    log.info("符合条件的结果为:{}",sourceAsString);
                    //直接将json转为对应videoVo对象
                    VideoVo videoVo = new ObjectMapper().readValue(sourceAsString,VideoVo.class);
                    videoVo.setId(Integer.parseInt(hit.getId()));
                    videoVos.add(videoVo);
                }
                result.put("items",videoVos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}

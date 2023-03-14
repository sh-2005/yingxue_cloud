package com.yyl.service;

import java.io.IOException;
import java.util.Map;

public interface VideoSearchService {

    public Map<String,Object> videos(String q,Integer page,Integer rows) throws IOException;

}




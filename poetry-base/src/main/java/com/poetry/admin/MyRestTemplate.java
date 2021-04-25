package com.poetry.admin;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

/**
 * http请求简单封装
 */
@Lazy
@Configuration
@SuppressWarnings("unchecked")
public class MyRestTemplate {
    @Autowired(required = false)
    private RestTemplate restTemplate;

    public String post(String url, Map<String, ?> requestParams) {
        ResponseEntity<String> rss = request(url, HttpMethod.POST, requestParams);
        return rss.getBody();
    }

    public String get(String url, Map<String, ?> requestParams) {
        ResponseEntity<String> rss = request(url, HttpMethod.GET, requestParams);
        return rss.getBody();
    }

    public String delete(String url, Map<String, ?> requestParams) {
        ResponseEntity<String> rss = request(url, HttpMethod.DELETE, requestParams);
        return rss.getBody();
    }

    public String put(String url, Map<String, ?> requestParams) {
        ResponseEntity<String> rss = request(url, HttpMethod.PUT, requestParams);
        return rss.getBody();
    }

    public ResponseEntity<String> request(String url, HttpMethod method, Map<String, ?> requestParams) {
        return request(url, method, requestParams, Collections.EMPTY_MAP);
    }

    public ResponseEntity<String> request(String url, HttpMethod method, Map<String, ?> requestParams, Map<String, String> headerParams) {
        HttpHeaders requestHeaders = new HttpHeaders();
        if (headerParams != null && headerParams.size() > 0) {
            headerParams.forEach((key, value) -> requestHeaders.add(key, value));
        }
        HttpEntity<String> requestEntity = new HttpEntity<>(null, requestHeaders);
        return restTemplate.exchange(url, method, requestEntity, String.class, requestParams);
    }

    public ResponseEntity<String> delete(String url, HttpMethod method, Object requestParams) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type2 = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type2);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> requestEntity = new HttpEntity<>(JSON.toJSONString(requestParams), headers);
        return restTemplate.exchange(url, method, requestEntity, String.class);
    }

    public ResponseEntity<String> postForEntity(String url, MultiValueMap<String, ?> requestBody, Map<String, String> headerParams) {
        HttpHeaders requestHeaders = new HttpHeaders();
        if (headerParams != null && headerParams.size() > 0) {
            headerParams.forEach((key, value) -> requestHeaders.add(key, value));
        }
        HttpEntity<MultiValueMap> requestEntity = new HttpEntity<>(requestBody, requestHeaders);
        return restTemplate.postForEntity(url, requestEntity, String.class);
    }

    public ResponseEntity<String> postForEntity(String url, Object request) {
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate.postForEntity(url, request, String.class);
    }

}
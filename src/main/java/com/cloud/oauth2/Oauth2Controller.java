package com.cloud.oauth2;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class Oauth2Controller {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/oauth/default/callback")
    public void back(@RequestParam(value = "code") String code, HttpServletRequest request){
        log.info("Get callback code is: {}", code);
        MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
        requestEntity.add("grant_type", "authorization_code");
        requestEntity.add("code", code);
        requestEntity.add("client_id", "client");
        requestEntity.add("client_secret","123456");
        requestEntity.add("scope","all");
        requestEntity.add("Authorization","Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNTQ3NjU1NjUzfQ.1LNvMwNZIEU8jClQQsI-p8Fud4lCnRtbL6AS8a1mFnXWvesGpkijy3zLWMlrtHUSKi13MQjBHNSzkSYroKDyCw");
        requestEntity.add("redirect_uri", "http://localhost:8080/oauth/default/callback");
        try {
            String result = restTemplate.postForObject("http://client:123456@localhost:8080/oauth/token",requestEntity, String.class);
            log.info("result is: {}", result);
        } catch (Exception e){
            log.error(e.getMessage());
        }

    }

}

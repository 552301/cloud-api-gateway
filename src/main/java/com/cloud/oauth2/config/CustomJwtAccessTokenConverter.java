package com.cloud.oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Map;


@Configuration
public class CustomJwtAccessTokenConverter extends JwtAccessTokenConverter {

    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        return null;
    }
}

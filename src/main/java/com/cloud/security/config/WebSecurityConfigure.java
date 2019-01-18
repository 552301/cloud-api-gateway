package com.cloud.security.config;

import com.cloud.security.filter.JwtTokenSecurityFilter;
import com.cloud.security.filter.UsernamePasswordLoginFilter;
import com.cloud.security.filter.WhiteListFilter;
import com.cloud.security.service.JwtTokenAuthentication;
import com.cloud.security.service.UsernamePasswordAuthentication;
import com.cloud.security.service.WhiteListAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {

    @Value("${security.header}")
    private String AUTHORIZATION_HEADER_KEY = "Authorization";

    @Value("${security.secret}")
    private String AUTHORIZATION_SECRET = "https://github.com/hzwy23";

    @Value("${security.salt}")
    private String AUTHORIZATION_SALT_KEY = "Bearer ";

    @Autowired
    private UsernamePasswordAuthentication usernamePasswordAuthentication;

    // 负责拦截Jwt Token校验
    @Autowired
    private JwtTokenAuthentication jwtAuthenticationToken;

    @Autowired
    private WhiteListFilter whiteListFilter;

    // 白名单过滤，白名单请求不进行权限校验
    @Autowired
    private WhiteListAuthentication whiteListAuthentication;

    // 注入自自定义过滤器
    @Autowired
    private JwtTokenSecurityFilter jwtTokenSecurityFilter;

    // 负责拦截登陆请求
    @Autowired
    private UsernamePasswordLoginFilter usernamePasswordLoginFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated()
                .and().cors()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and().csrf().disable()
                .addFilterBefore(whiteListFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(usernamePasswordLoginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtTokenSecurityFilter, FilterSecurityInterceptor.class);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(usernamePasswordAuthentication)
                .authenticationProvider(jwtAuthenticationToken)
                .authenticationProvider(whiteListAuthentication);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}

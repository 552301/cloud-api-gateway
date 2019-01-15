package com.cloud.security.config;

import com.cloud.security.filter.TokenFilterSecurityInterceptor;
import com.cloud.security.filter.UsernamePasswordLoginFilter;
import com.cloud.security.service.JwtTokenAuthentication;
import com.cloud.security.service.UsernamePasswordAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private JwtTokenAuthentication jwtAuthenticationToken;


    @Autowired
    private TokenFilterSecurityInterceptor tokenFilterSecurityInterceptor;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        UsernamePasswordLoginFilter loginFilter = new UsernamePasswordLoginFilter(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_SECRET, AUTHORIZATION_SALT_KEY);
        loginFilter.afterProperty(authenticationManagerBean());

        http.authorizeRequests()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated()
                .and().cors()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable()
                .addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(tokenFilterSecurityInterceptor, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(usernamePasswordAuthentication)
                .authenticationProvider(jwtAuthenticationToken);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}

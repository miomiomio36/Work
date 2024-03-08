package com.example.demo.config;

import com.example.demo.filter.CorFilter;
import com.example.demo.filter.MyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<CorFilter> corFilterRegister(){
        FilterRegistrationBean<CorFilter> registrationBean = new FilterRegistrationBean<>();
        CorFilter corFilter = new CorFilter();
        registrationBean.setFilter(corFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
    @Bean
    public FilterRegistrationBean<MyFilter> myFilterRegister(){
        FilterRegistrationBean<MyFilter> registrationBean = new FilterRegistrationBean<>();
        MyFilter corFilter = new MyFilter();
        registrationBean.setFilter(corFilter);
        registrationBean.addUrlPatterns("/admin/*");
        registrationBean.addUrlPatterns("/student/*");
        registrationBean.addUrlPatterns("/teacher/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}

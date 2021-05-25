package com.haustlyb.html2pdf.configs;

import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${hsr.file.uploadPath}")
    private String uploadPath;

    /**
     * org.beetl.core.GroupTemplate配置，用于执行article的静态化
     */
    @Bean
    public GroupTemplate groupTemplate(){
        StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
        org.beetl.core.Configuration cfg = null;
        try {
            cfg = org.beetl.core.Configuration.defaultConfiguration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        Map<String,Object> globalSharedVars = new HashMap<>();
        globalSharedVars.put("\u0061\u0075\u0074\u0068\u006f\u0072","\u674e\u4e00\u535a");
        gt.setSharedVars(globalSharedVars);
        return gt;
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/upload/**").addResourceLocations("file:"+uploadPath);
    }
}

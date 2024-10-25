package com.kiloit.onlyadmin.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileUploadConfiguration  implements WebMvcConfigurer{
    @Value("${file-upload.server-path}")
    private String serverPath ;

    @Override
    public void addResourceHandlers(@SuppressWarnings("null") ResourceHandlerRegistry resourceHandlerRegistry){
     resourceHandlerRegistry.addResourceHandler("/upload/**").addResourceLocations("file:"+serverPath);
    }
}

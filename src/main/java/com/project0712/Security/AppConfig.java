package com.project0712.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public String defaultFilterProcessesUrl(){
        return "/";
    }
}

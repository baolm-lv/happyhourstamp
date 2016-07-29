package com.lvmama.vst.hhs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean(name="hhsClient")
    public RestTemplate hhsClient()
    {
        RestTemplate restTemplate = new RestTemplate();
//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
//        messageConverters.add(new MappingJackson2HttpMessageConverter());
//        restTemplate.setMessageConverters(messageConverters);
        return restTemplate;
    }
}

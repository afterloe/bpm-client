package cn.cityworks.soa.dapeng.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * create by afterloe on 2017/10/13
 */
@Configuration
public class Init implements Serializable {

    @Bean
    @Scope("prototype")
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return objectMapper;
    }
}

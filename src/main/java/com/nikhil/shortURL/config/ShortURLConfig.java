package com.nikhil.shortURL.config;

import com.nikhil.shortURL.utils.Base62Encoder;
import com.nikhil.shortURL.utils.SnowflakeIdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShortURLConfig {

    @Bean
    public SnowflakeIdGenerator snowflakeIdGenerator(
            @Value("${app.snowflake.machine-id}") long machineId
    ) {
        return new SnowflakeIdGenerator(machineId);
    }

    @Bean
    public Base62Encoder base62Encoder() {
        return new Base62Encoder();
    }
}
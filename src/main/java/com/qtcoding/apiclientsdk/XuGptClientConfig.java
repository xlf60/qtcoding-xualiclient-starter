package com.qtcoding.apiclientsdk;


import com.qtcoding.apiclientsdk.client.XuAliClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Author xlf
 * Description
 * date 2023/10/15
 */
@Configuration
@ConfigurationProperties("xugpt.client")
@Data
@ComponentScan
public class XuGptClientConfig {

    private String api_key;

    @Bean
    public XuAliClient xuAliClient() {
        return new XuAliClient(api_key);
    }

}

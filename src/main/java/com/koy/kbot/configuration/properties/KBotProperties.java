package com.koy.kbot.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description
 * @Auther Koy  https://github.com/Koooooo-7
 * @Date 2020/06/04
 */
@Component
@ConfigurationProperties(prefix = "k-bot")
@Data
public class KBotProperties {

    private String token;
    private String cmd;
    private List<String> subCmd;

}

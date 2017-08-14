package com.onefactor.dsp.bootstrap;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Настройки запуска системы
 */
@Configuration
@ConfigurationProperties(prefix="bootstrap")
@Getter
@Setter
public class BootstrapSettings {

    private String adminMail;
    private String adminPassword;

}

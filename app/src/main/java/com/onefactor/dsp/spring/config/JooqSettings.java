package com.onefactor.dsp.spring.config;

import lombok.Getter;
import lombok.Setter;
import org.jooq.SQLDialect;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Параметры JOOQ'а
 */
@Configuration()
@ConfigurationProperties(prefix = "jooq")
@Getter
@Setter
public class JooqSettings {
    private SQLDialect dialect;
    private boolean isRenderCatalog;

    public boolean isPostgres() {
        return dialect.family() == SQLDialect.POSTGRES;
    }

}

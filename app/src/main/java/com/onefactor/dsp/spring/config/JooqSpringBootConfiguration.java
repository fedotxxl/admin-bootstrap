package com.onefactor.dsp.spring.config;

import org.jooq.ConnectionProvider;
import org.jooq.ExecuteListenerProvider;
import org.jooq.TransactionProvider;
import org.jooq.conf.Settings;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация Spring Boot для JOOQ'а
 */
@Configuration
public class JooqSpringBootConfiguration {

    @Autowired
    private JooqSettings settings;

    @Bean
    public org.jooq.Configuration jooqConfig(ConnectionProvider connectionProvider, TransactionProvider transactionProvider, ExecuteListenerProvider executeListenerProvider) {
        Settings s = new Settings();

        s.setRenderCatalog(settings.isRenderCatalog());

        return new DefaultConfiguration()
                .set(s)
                .derive(connectionProvider)
                .derive(transactionProvider)
                .derive(settings.getDialect());
    }

}

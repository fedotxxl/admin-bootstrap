package com.onefactor.dsp;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.belov.soyuz.json.JacksonUtils;
import io.thedocs.soyuz.is;
import io.thedocs.soyuz.to;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Конфигурация запуска Spring Boot приложения
 */
@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
@Import({App.Config.class})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Configuration
    @EnableCaching
    @EnableConfigurationProperties
    @EnableTransactionManagement(proxyTargetClass = true)
    public static class Config {
        @Bean
        public ObjectMapper objectMapper() {
            ObjectMapper mapper = new ObjectMapper();

            mapper.findAndRegisterModules();
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

            SimpleModule module = new SimpleModule();
            module.addSerializer(ZonedDateTime.class, new JsonSerializer<ZonedDateTime>() {
                @Override
                public void serialize(ZonedDateTime zonedDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                    jsonGenerator.writeString(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZZ").format(zonedDateTime));
                }
            });
            mapper.registerModule(module);
            mapper.registerModule(getLocalDateDeserializerModule());


            mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            JacksonUtils.CONFIGURER_CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES.accept(mapper);
            JacksonUtils.CONFIGURER_FAIL_SAFE.accept(mapper);

            return mapper;
        }


        private SimpleModule getLocalDateDeserializerModule() {
            SimpleModule module = new SimpleModule();
            List<DateTimeFormatter> patterns = to.list(DateTimeFormatter.ISO_LOCAL_DATE, DateTimeFormatter.ISO_DATE_TIME);

            module.addDeserializer(LocalDate.class, new JsonDeserializer<LocalDate>() {
                @Override
                public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                    String value = p.readValueAs(String.class);

                    if (is.t(value)) {
                        for (DateTimeFormatter pattern : patterns) {
                            try {
                                return LocalDate.parse(value, pattern);
                            } catch (Exception e) {
                            }
                        }

                        throw new IllegalStateException("Unknown LocalDate format " + value);
                    } else {
                        return null;
                    }
                }
            });

            return module;
        }
    }
}

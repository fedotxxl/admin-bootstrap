package com.onefactor.dsp.auth;

import io.thedocs.soyuz.log.LoggerEvents;
import io.thedocs.soyuz.to;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Изменяем длительность java сессии
 * https://dzone.com/articles/spring-java-configuration
 */
public class HttpSessionIntervalListener implements HttpSessionListener, ApplicationContextAware {

    private static final LoggerEvents loge = LoggerEvents.getInstance(HttpSessionIntervalListener.class);

    private int maxIntervalInSeconds;

    public HttpSessionIntervalListener(int maxIntervalInSeconds) {
        this.maxIntervalInSeconds = maxIntervalInSeconds;
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setMaxInactiveInterval(maxIntervalInSeconds);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try {
            if (applicationContext instanceof WebApplicationContext) {
                ((WebApplicationContext) applicationContext).getServletContext().addListener(this);
            } else {
                //Either throw an exception or fail gracefully, up to you
                throw new RuntimeException("Must be inside a web application context");
            }
        } catch (UnsupportedOperationException e) {
            loge.error("http.session.e", to.map("e", e.toString()));
        }
    }
}

/*
 * LoginUrlOr403AuthenticationEntryPoint
 * Copyright (c) 2012 Cybervision. All rights reserved.
 */
package com.onefactor.dsp.auth;

import com.google.common.net.HttpHeaders;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginUrlOr403AuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

    private String loginFormUrl;

    public LoginUrlOr403AuthenticationEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
        this.loginFormUrl = loginFormUrl;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (isJsonRequest(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
        } else if (isNginxRequest(request)) {
            response.setHeader(HttpHeaders.LOCATION, this.loginFormUrl);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
        } else {
            super.commence(request, response, authException);
        }
    }

    private boolean isJsonRequest(HttpServletRequest request) {
        String accept = request.getHeader("Accept");
        return accept != null && accept.toLowerCase().contains("application/json");
    }

    private boolean isNginxRequest(HttpServletRequest request) {
        return request.getHeader("X-Original-URI") != null;
    }
}

/*
 * AuthController
 * Copyright (c) 2012 Cybervision. All rights reserved.
 */
package com.onefactor.dsp.auth;

import com.onefactor.dsp.domain.RedirectRestResponse;
import com.onefactor.dsp.domain.RestResponse;
import com.onefactor.dsp.domain.UserRepresentation;
import com.onefactor.dsp.user.UserService;
import com.onefactor.dsp.utils.InputViolation;
import io.belov.soyuz.err.Err;
import io.thedocs.soyuz.to;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Аудентификация - REST методы
 */
@RestController
@RequestMapping("/api/auth/")
public class AuthResource {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @GetMapping("/logged-in")
    UserRepresentation getCurrentUser() {
        return to.nullOr(AuthUtils.getCurrentUser(), UserRepresentation::new);
    }

    @RequestMapping("/login")
    RestResponse login(@RequestBody LoginRequest request, HttpServletRequest httpRequest, HttpServletResponse response) {
        RestResponse answer = new RedirectRestResponse("/app/");

        if (!authService.authenticate(request.getMail(), request.getPassword(), httpRequest, response)) {
            if (userService.isExistsAndDisabled(request.getMail())) {
                answer.add(Err.field("mail").code(InputViolation.auth_error_accountIsBlocked).build());
            } else {
                answer.add(Err.field("mail").code(InputViolation.auth_error_invalidCredentials).build());
            }
        }

        if (!answer.isOk()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        return answer;
    }

    @Getter
    private static class LoginRequest {
        private String mail;
        private String password;
    }

}

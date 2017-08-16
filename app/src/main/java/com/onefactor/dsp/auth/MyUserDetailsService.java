/*
 * UserDetailsService
 * Copyright (c) 2012 Cybervision. All rights reserved.
 */
package com.onefactor.dsp.auth;

import com.onefactor.dsp.domain.User;
import com.onefactor.dsp.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Spring Security - реализация UserDetailsService
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findActiveByMail(username);

        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " is not found");
        } else {
            return new MyUserDetails(user);
        }
    }
}

package com.onefactor.dsp.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created on 03.07.17.
 */
@Getter
public class UserRepresentation {
    private int id;
    private String mail;
    private String firstName;
    private String lastName;
    private boolean isDisabled;
    private boolean isRoleAdmin;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    private Set<String> permissions;

    public UserRepresentation(User user) {
        id = user.getId();
        mail = user.getMail();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        isDisabled = user.isDisabled();
        isRoleAdmin = user.isRoleAdmin();
        createdAt = user.getCreatedAt();
        lastLoginAt = user.getLastLoginAt();
        permissions = user.getPermissionsAsString();
    }
}

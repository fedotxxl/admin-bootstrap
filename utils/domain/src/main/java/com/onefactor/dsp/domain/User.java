package com.onefactor.dsp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.thedocs.soyuz.db.jooq.crud.CrudBeanI;
import io.thedocs.soyuz.is;
import io.thedocs.soyuz.to;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

import static com.onefactor.dsp.domain.User.Permission.*;

/**
 * Пользователь
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements CrudBeanI, CommonFilterItemCastableI {

    private static final Set<Permission> ADMIN_PERMISSIONS = to.set(
            PERM_ADMIN,
            PERM_USER_READ, PERM_USER_UPDATE
    );

    private int id;
    private String mail;
    private String password;
    private String firstName;
    private String lastName;
    private boolean isDisabled;
    private boolean isRoleAdmin;
    private boolean isActivated;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;

    public LocalDateTime getCreatedAt() {
        return to.or(createdAt, LocalDateTime.now());
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public boolean hasId() {
        return is.t(id);
    }

    public boolean hasPermission(Permission permission) {
        return getPermissions().contains(permission);
    }

    @JsonIgnore
    public Set<Permission> getPermissions() {
        Set<Permission> answer = to.set();

        if (isRoleAdmin) {
            answer.addAll(ADMIN_PERMISSIONS);
        }

        return answer;
    }

    @JsonIgnore
    public Set<String> getPermissionsAsString() {
        return to.set(getPermissions(), Enum::toString);
    }

    @Override
    public CommonFilterItem toCommonFilterItem() {
        return new CommonFilterItem(id, CommonFilterItem.Type.USER, mail);
    }

    public enum Permission {
        PERM_ADMIN,
        PERM_USER_READ, PERM_USER_UPDATE,
    }
}

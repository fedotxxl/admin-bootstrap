package com.onefactor.dsp.auth;

import com.onefactor.dsp.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.Nullable;

/**
 * Безопасность - вспомогательные методы
 */
public class AuthUtils {

    @Nullable
    public static User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof MyUserDetails) {
            return ((MyUserDetails) principal).getUser();
        } else {
            return null;
        }
    }

    public static int getCurrentUserId() {
        User user = getCurrentUser();

        if (user == null) {
            return -1;
        } else {
            return user.getId();
        }
    }

    public static boolean hasPermission(User.Permission permission) {
        User user = getCurrentUser();

        if (user == null) {
            return false;
        } else {
            return user.hasPermission(permission);
        }
    }
}

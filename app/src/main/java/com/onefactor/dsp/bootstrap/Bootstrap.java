package com.onefactor.dsp.bootstrap;

import com.onefactor.dsp.domain.User;
import com.onefactor.dsp.user.UserService;
import io.thedocs.soyuz.log.LoggerEvents;
import lombok.AllArgsConstructor;

import javax.annotation.PostConstruct;

/**
 * Дополнительные действия при запуске приложения
 */
@AllArgsConstructor
public class Bootstrap {

    private static final LoggerEvents loge = LoggerEvents.getInstance(Bootstrap.class);

    private BootstrapSettings settings;
    private UserService userService;

    @PostConstruct
    private void setUp() {
        checkAndCreateUsers();
    }

    private void checkAndCreateUsers() {
        if (userService.list().size() == 0) {
            userService.save(
                    User.builder()
                            .mail(settings.getAdminMail())
                            .password(userService.encodePassword(settings.getAdminPassword()))
                            .isRoleAdmin(true)
                            .isActivated(true)
                            .build()
            );
        }
    }

}

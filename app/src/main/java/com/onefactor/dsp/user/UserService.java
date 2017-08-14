package com.onefactor.dsp.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.eventbus.Subscribe;
import com.onefactor.dsp.auth.AppPasswordEncoder;
import com.onefactor.dsp.auth.UserAuthenticatedEvent;
import com.onefactor.dsp.domain.User;
import com.onefactor.dsp.domain.UserListRequest;
import io.belov.soyuz.bus.GuavaBusBean;
import io.belov.soyuz.utils.func;
import io.belov.soyuz.validator.FluentValidator;
import io.belov.soyuz.validator.FvCustomValidatorResult;
import io.thedocs.soyuz.db.jooq.crud.CrudServiceI;
import io.thedocs.soyuz.is;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Пользователи - сервис
 */
@Service
@AllArgsConstructor
@Transactional
public class UserService implements CrudServiceI<User, UserDao, UserListRequest>, AppPasswordEncoder, GuavaBusBean {

    private final ExecutorService pool = new DelegatingSecurityContextExecutorService(Executors.newCachedThreadPool());

    private UserDao dao;
    private ShaPasswordEncoder passwordEncoder;
    private Validator validator;

    public User save(UserUpdateRequest request) {
        User user = null;

        if (request.hasId()) {
            user = get(request.getId());
        }

        user = request.toUser(this, user);

        validator.get().validate(user).ifHasErrorsThrowAnException();

        return save(user);
    }

    public User findByMail(String username) {
        return dao.findByMail(username);
    }

    public User findActiveByMail(String username) {
        return func.checkAndApply(dao.findByMail(username), u -> {
            return (u.isDisabled()) ? null : u;
        });
    }

    public boolean hasUser(String mail) {
        return dao.hasUser(mail);
    }

    @Override
    public String encodePassword(String password) {
        return passwordEncoder.encodePassword(password, null);
    }

    @Override
    public UserDao getDao() {
        return dao;
    }

    @Override
    public ExecutorService getPool() {
        return pool;
    }

    public void enable(int id) {
        validator.get().validate(get(id)).ifHasErrorsThrowAnException();

        dao.setIsDisabled(id, false);
    }

    public void disable(int id) {
        dao.setIsDisabled(id, true);
    }

    @Subscribe
    public void on(UserAuthenticatedEvent e) {
        dao.updateLastLoginAt(e.getMail());
    }

    public boolean isExistsAndDisabled(String mail) {
        User user = findByMail(mail);

        if (user != null && user.isDisabled()) {
            return true;
        } else {
            return false;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class UserUpdateRequest {
        private int id;
        private String mail;
        private String password;
        private String firstName;
        private String lastName;

        @JsonProperty("disabled")
        private boolean isDisabled;

        @JsonProperty("role_admin")
        private boolean isRoleAdmin;

        public void setId(int id) {
            this.id = id;
        }

        public boolean hasId() {
            return is.t(id);
        }

        public User toUser(AppPasswordEncoder passwordEncoder, User user) {
            User.UserBuilder builder = User.builder();

            builder.id(id)
                    .mail(mail)
                    .firstName(firstName)
                    .lastName(lastName)
                    .isDisabled(isDisabled)
                    .isRoleAdmin(isRoleAdmin);

            if (user != null) {
                builder.createdAt(user.getCreatedAt())
                        .lastLoginAt(user.getLastLoginAt())
                        .password(user.getPassword());
            } else {
                builder.createdAt(LocalDateTime.now());
            }

            if (is.t(password)) {
                builder.password(passwordEncoder.encodePassword(password));
            }

            return builder.build();
        }
    }

    @Component
    public static class Validator {

        private final FluentValidator<User> fv;

        public Validator(UserDao dao) {
            this.fv = FluentValidator.of(User.class)
                    .string("mail").notEmpty().mail().custom((o, v) -> {
                        if (is.t(v) && !dao.isUniqueMail(v, o.getId())) {
                            return FvCustomValidatorResult.failure("notUnique");
                        } else {
                            return FvCustomValidatorResult.success();
                        }
                    }).b()
                    .string("firstName").notEmpty().b()
                    .string("lastName").notEmpty().b()
                    .string("password").notEmpty().b()
                    .build();
        }

        public FluentValidator<User> get() {
            return fv;
        }
    }
}

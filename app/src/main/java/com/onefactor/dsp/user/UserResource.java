package com.onefactor.dsp.user;

import com.onefactor.dsp.domain.CommonFilterItem;
import com.onefactor.dsp.domain.User;
import com.onefactor.dsp.domain.UserListRequest;
import com.onefactor.dsp.domain.UserRepresentation;
import io.thedocs.soyuz.db.jooq.crud.CollectionEntity;
import io.thedocs.soyuz.to;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Пользователи - методы REST
 */
@RestController
@RequestMapping("/api/users/")
public class UserResource {

    @Autowired
    private UserService service;

    @PostMapping("/")
    @PreAuthorize("hasAuthority('PERM_USER_READ')")
    CollectionEntity list(@RequestBody UserListRequest request) {
        return service.listWithTotal(request).transform(UserRepresentation::new);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PERM_USER_READ')")
    UserRepresentation get(@PathVariable("id") int id) {
        return new UserRepresentation(service.get(id));
    }

    @PostMapping("/new")
    @PreAuthorize("hasAuthority('PERM_USER_UPDATE')")
    UserRepresentation create(@RequestBody UserService.UserUpdateRequest user) {
        return new UserRepresentation(service.save(user));
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('PERM_USER_UPDATE')")
    UserRepresentation update(@PathVariable("id") int id, @RequestBody UserService.UserUpdateRequest user) {
        user.setId(id);

        return new UserRepresentation(service.save(user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PERM_USER_UPDATE')")
    void delete(@PathVariable("id") int id) {
        service.delete(id);
    }

    @PostMapping("/{id}/enable")
    @PreAuthorize("hasAuthority('PERM_USER_UPDATE')")
    void enable(@PathVariable("id") int id) {
        service.enable(id);
    }

    @PostMapping("/{id}/disable")
    @PreAuthorize("hasAuthority('PERM_USER_UPDATE')")
    void disable(@PathVariable("id") int id) {
        service.disable(id);
    }

    @GetMapping("/filter-items")
    @PreAuthorize("hasAuthority('PERM_ADMIN')")
    List<CommonFilterItem> getFilterItems() {
        List<CommonFilterItem> answer = to.list();

        answer.addAll(to.list(service.list(), User::toCommonFilterItem));

        return answer;
    }

}

package com.onefactor.dsp.user;

import com.onefactor.dsp.dao.jooq.tables.records.JuserRecord;
import com.onefactor.dsp.domain.CommonFilterItem;
import com.onefactor.dsp.domain.User;
import com.onefactor.dsp.domain.UserFilterBody;
import com.onefactor.dsp.domain.UserListRequest;
import io.belov.soyuz.db.jooq.JooqUtils;
import io.thedocs.soyuz.db.jooq.crud.CrudDaoI;
import io.thedocs.soyuz.db.jooq.crud.RecordReadWriteMapper;
import io.thedocs.soyuz.is;
import io.thedocs.soyuz.to;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

import static com.onefactor.dsp.dao.jooq.Tables.JUSER;

/**
 * Пользователи - методы работы с БД
 */
@Repository
public class UserDao implements CrudDaoI<User, Record, UserListRequest> {

    private DSLContext dsl;
    private Mapper mapper;

    private JooqEntryData<User, Record> jooqEntryData;

    @Autowired
    public UserDao(DSLContext dsl, Mapper mapper) {
        this.dsl = dsl;
        this.mapper = mapper;
        this.jooqEntryData = new JooqEntryData<>(dsl, JUSER, mapper);
    }

    @Override
    public JooqEntryData<User, Record> getJooqEntryData() {
        return jooqEntryData;
    }

    @Override
    public Collection<Condition> getListConditions(UserListRequest request) {
        Collection<Condition> answer = to.list();

        UserFilterBody filter = request.getFilter();

        if (filter.hasItems()) {
            if (filter.hasItems(CommonFilterItem.Type.USER)) {
                answer.add(JUSER.ID.in(filter.getItemIds(CommonFilterItem.Type.USER)));
            }
        }

        return answer;
    }

    public boolean isUniqueMail(String mail, Integer id) {
        return dsl.selectCount().from(JUSER).where(JUSER.MAIL.equalIgnoreCase(mail).and(JUSER.ID.notEqual(id))).fetchOne(0, Integer.class) == 0;
    }

    @Override
    public User insertOrUpdate(User user) {
        if (user.hasId()) {
            dsl.update(JUSER).set(mapper.toFields(user)).where(JUSER.ID.eq(user.getId())).execute();
        } else {
            user.setId(dsl.insertInto(JUSER).set(mapper.toFields(user)).returning(JUSER.ID).fetchOne().getId());
        }

        return user;
    }

    @Nullable
    public User findByMail(String mail) {
        if (is.t(mail)) {
            return dsl.select().from(JUSER).where(JUSER.MAIL.eq(mail.toLowerCase())).fetchOne(mapper);
        } else {
            return null;
        }
    }

    public boolean hasUser(String mail) {
        return dsl.selectCount().from(JUSER).where(JUSER.MAIL.eq(mail.toLowerCase())).fetchOne(0, int.class) > 0;
    }

    public void setIsDisabled(int id, boolean isDisabled) {
        dsl.update(JUSER).set(JUSER.IS_DISABLED, isDisabled).where(JUSER.ID.eq(id)).execute();
    }

    public void updateLastLoginAt(String mail) {
        dsl.update(JUSER).set(JUSER.LAST_LOGIN_AT, LocalDateTime.now()).where(JUSER.MAIL.eq(mail.toLowerCase())).execute();
    }

    @Component
    public static class Mapper implements RecordReadWriteMapper<User> {
        @Override
        public User map(Record record) {
            JuserRecord r = record.into(JUSER);

            return User.builder()
                    .id(r.getId())
                    .createdAt(r.getCreatedAt())
                    .firstName(r.getFirstName())
                    .isActivated(r.getIsActivated())
                    .isDisabled(r.getIsDisabled())
                    .isRoleAdmin(r.getIsRoleAdmin())
                    .lastLoginAt(r.getLastLoginAt())
                    .lastName(r.getLastName())
                    .mail(r.getMail())
                    .password(r.getPassword())
                    .build();
        }

        @Override
        public Map<? extends Field<?>, ?> toFields(User u) {
            Map<? extends Field<?>, ?> answer = JooqUtils.intoMap(
                    new JuserRecord()
                            .setId(u.getId())
                            .setCreatedAt(u.getCreatedAt())
                            .setFirstName(u.getFirstName())
                            .setIsActivated(u.isActivated())
                            .setIsDisabled(u.isDisabled())
                            .setIsRoleAdmin(u.isRoleAdmin())
                            .setLastLoginAt(u.getLastLoginAt())
                            .setLastName(u.getLastName())
                            .setMail(u.getMail())
                            .setPassword(u.getPassword())
            );

            if (!u.hasId()) {
                answer.remove(JUSER.ID);
            }

            return answer;
        }
    }


}

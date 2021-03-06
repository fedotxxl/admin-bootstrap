package com.onefactor.dsp.domain;

import io.thedocs.soyuz.db.jooq.crud.FieldWithOrder;
import io.thedocs.soyuz.db.jooq.crud.JooqListRequestI;
import io.thedocs.soyuz.to;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Объект для получения списка пользователей
 */
@NoArgsConstructor
@Getter
public class UserListRequest implements JooqListRequestI {

    private FieldWithOrder order;
    private int page;
    private UserFilterBody filter = new UserFilterBody();

    @Override
    public List<FieldWithOrder> getOrder() {
        if (order == null) {
            return to.list();
        } else if ("name".equalsIgnoreCase(order.getField())) {
            return to.list(
                    new FieldWithOrder("first_name", order.getOrder()),
                    new FieldWithOrder("last_name", order.getOrder())
            );
        } else {
            return to.list(order);
        }
    }

    @Override
    public int getItemsPerPage() {
        return 30;
    }

    public void setFilter(UserFilterBody filter) {
        this.filter = (filter == null) ? new UserFilterBody() : filter;
    }
}

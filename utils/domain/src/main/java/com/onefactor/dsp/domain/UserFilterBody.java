package com.onefactor.dsp.domain;

import io.thedocs.soyuz.is;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Параметры фильтрации пользователей
 */
@NoArgsConstructor
@Getter
public class UserFilterBody {
    private List<CommonFilterItem.UniqueId> items;

    public boolean hasItems() {
        return is.t(items);
    }

    public boolean hasItems(CommonFilterItem.Type type) {
        return is.t(getItemIds(type));
    }

    @Nullable
    public Collection<Integer> getItemIds(CommonFilterItem.Type type) {
        if (items == null) {
            return null;
        } else {
            return items.stream().filter(i -> i.getType() == type).map(i -> i.getIdAsInteger()).collect(Collectors.toList());
        }
    }
}

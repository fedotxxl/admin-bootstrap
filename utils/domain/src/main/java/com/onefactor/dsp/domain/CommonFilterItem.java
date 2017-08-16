package com.onefactor.dsp.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import io.thedocs.soyuz.to;
import lombok.*;
import org.apache.commons.codec.binary.Base64;

/**
 * Created on 29.06.17.
 */
@Getter
@EqualsAndHashCode(of = {"id", "type"})
public class CommonFilterItem {

    private String id;
    private Type type;
    private String label;

    public CommonFilterItem(int id, Type type, String label) {
        this(to.s(id), type, label);
    }

    public CommonFilterItem(String id, Type type, String label) {
        this.id = id;
        this.type = type;
        this.label = label;

        if (type.isBase64Convertable()) {
            this.id = Base64.encodeBase64URLSafeString(this.id.getBytes());
        }
    }

    public static CommonFilterItem name(String name) {
        return new CommonFilterItem(name, Type.NAME, name);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class UniqueId {
        private Type type;
        private String id;

        @JsonIgnore
        public Integer getIdAsInteger() {
            return to.Integer(id);
        }

        @JsonCreator
        @SneakyThrows
        public static UniqueId fromString(String id) {
            int pos = id.indexOf("_");

            if (pos > 0) {
                Type type = Type.myValueOf(id.substring(0, pos));
                String i = id.substring(pos + 1);

                if (i != null && type != null) {
                    if (type.isBase64Convertable()) {
                        i = new String(Base64.decodeBase64(i.getBytes("UTF-8")), "UTF-8");
                    }

                    return new UniqueId(type, i);
                }
            }

            throw new IllegalStateException("Unable to convert " + id + " to unique id");
        }

    }

    @Getter
    @AllArgsConstructor
    public enum Type {
        NAME("name", true), MAIL("mail", false), USER("user", false);

        private String id;
        private boolean isBase64Convertable;

        @JsonValue
        public String getId() {
            return id;
        }

        @JsonCreator
        public static Type myValueOf(String id) {
            return to.stream(values()).filter(v -> v.id.equalsIgnoreCase(id)).findFirst().orElse(null);
        }
    }
}

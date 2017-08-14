package com.onefactor.dsp.auth;

/**
 * Интерфейс - имплементирующий класс может инкодить пароль
 */
public interface AppPasswordEncoder {
    String encodePassword(String password);
}

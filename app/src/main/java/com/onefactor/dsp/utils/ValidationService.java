package com.onefactor.dsp.utils;

import io.thedocs.soyuz.is;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

/**
 * Сервис валидации данных
 */
@Service
public class ValidationService {

    private static final EmailValidator EMAIL_VALIDATOR = EmailValidator.getInstance();

    private static final int PASSWORD_MAX_LENGTH = 16;
    private static final Pattern PASSWORD_VALIDATE = Pattern.compile("^[0-9a-zA-Z\\.\\-_]*$");

    public boolean password(String value) {
        return (is.tt(value) &&
                value.length() <= PASSWORD_MAX_LENGTH &&
                PASSWORD_VALIDATE.matcher(value).matches());
    }

    public boolean mail(String mail) {
        return is.tt(mail) && EMAIL_VALIDATOR.isValid(mail);
    }

}

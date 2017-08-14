package com.onefactor.dsp.utils;

import io.belov.soyuz.err.Err;

/**
 * Created on 20.07.17.
 */
public enum InputViolation implements Err.Code {

    auth_error_accountIsBlocked,
    auth_error_invalidCredentials;

    private String code;

    InputViolation() {
        String code = toString();

        if (code.charAt(0) != code.charAt(0)) {
            code = code.toLowerCase();
        }

        this.code = code.replace("_", ".");
    }

    public String getErrCode() {
        return code;
    }
}

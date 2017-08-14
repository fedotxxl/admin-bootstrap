/*
 * RestResponse
 * Copyright (c) 2012 Cybervision. All rights reserved.
 */
package com.onefactor.dsp.domain;

import io.belov.soyuz.err.Err;
import io.belov.soyuz.err.Errors;

/**
 * Ответ REST API в случае возможных запланированных проблем обработки запроса
 */
public class RestResponse {

    private Errors errors = Errors.ok();

    private Object response;

    public RestResponse() {
    }

    public RestResponse(Object response) {
        this.response = response;
    }

    public RestResponse(Errors errors) {
        this.errors = errors;
    }

    public void addError(String field, Err.Code code) {
        errors.add(Err.field(field).code(code).build());
    }

    public Errors getErrors() {
        return errors;
    }

    public boolean isOk() {
        return !errors.hasErrors();
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(Object response) {
        this.response = response;
    }

    public void add(Err error) {
        errors.add(error);
    }
}

/*
 * RedirectRestResponse
 * Copyright (c) 2012 Cybervision. All rights reserved.
 */
package com.onefactor.dsp.domain;

/**
 * Ответ REST - 302
 */
public class RedirectRestResponse extends RestResponse {

    private String url;

    public RedirectRestResponse(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}

/*
 * Copyright (c) 2015 General Electric Company. All rights reserved.
 *
 * The copyright to the computer software herein is the property of
 * General Electric Company. The software may be used and/or copied only
 * with the written permission of General Electric Company or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */

package com.lvmama.vst.hhs.common.web;

import org.springframework.http.HttpStatus;

/**
 * Controllers implementing the restful api of the acs, should throw this kind of exception which is handled by the
 * error handler to generate a json error response payload.
 *
 * @author Luocheng Tang
 */
@SuppressWarnings({ "javadoc", "nls" })
public class RestApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private HttpStatus httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
    private String appErrorCode = "FAILURE";

    public RestApiException() {
    }

    public RestApiException(final String message) {
        super(message);
    }

    public RestApiException(final Throwable cause) {
        super(cause);
    }

    public RestApiException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public RestApiException(final String message, final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public RestApiException(final HttpStatus httpStatusCode, final String message) {
        super(message);
        this.httpStatusCode = httpStatusCode;
    }

    public RestApiException(final HttpStatus httpStatusCode, final String appErrorCode, final String message) {
        super(message);
        this.httpStatusCode = httpStatusCode;
        this.appErrorCode = appErrorCode;
    }

    public RestApiException(final HttpStatus httpStatusCode, final Throwable cause) {
        super(cause);
        this.httpStatusCode = httpStatusCode;
    }

    public RestApiException(final HttpStatus httpStatusCode, final String message, final Throwable cause) {
        super(message, cause);
        this.httpStatusCode = httpStatusCode;
    }

    public HttpStatus getHttpStatusCode() {
        return this.httpStatusCode;
    }

    public void setHttpStatusCode(final HttpStatus httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    /**
     * @return the appErrorCode
     */
    public String getAppErrorCode() {
        return this.appErrorCode;
    }

    /**
     * @param appErrorCode
     *            the appErrorCode to set
     */
    public void setAppErrorCode(final String appErrorCode) {
        this.appErrorCode = appErrorCode;
    }

}

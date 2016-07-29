/*
 * Copyright (c) 2015 General Electric Company. All rights reserved.
 *
 * The copyright to the computer software herein is the property of
 * General Electric Company. The software may be used and/or copied only
 * with the written permission of General Electric Company or in accordance
 * with the terms and conditions stipulated in the agreement/contract
 * under which the software has been supplied.
 */

package com.lvmama.vst.hhs.common.exception;

/**
 *
 * @author Luocheng Tang
 */
@SuppressWarnings("javadoc")
public class UntrustedIssuerException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public UntrustedIssuerException(final String message, final Throwable cause, final boolean enableSuppression,
            final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UntrustedIssuerException(final Throwable cause) {
        super(cause);
    }

    public UntrustedIssuerException() {
        super();
    }

    public UntrustedIssuerException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UntrustedIssuerException(final String message) {
        super(message);
    }

}

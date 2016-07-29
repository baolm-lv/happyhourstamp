package com.lvmama.vst.hhs.common.web;

/**
 * Used to provide error payload with code and message.
 *
 * @author Luocheng Tang
 *
 */
@SuppressWarnings({ "nls", "javadoc" })
public class RestApiErrorResponse {

    private String errorCode = "FAILED";
    private String errorMessage = "Operation Failed";

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(final String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

}

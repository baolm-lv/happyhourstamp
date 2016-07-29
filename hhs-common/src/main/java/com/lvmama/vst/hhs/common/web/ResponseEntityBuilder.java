package com.lvmama.vst.hhs.common.web;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Convenient ResponseEntity Builder methods to minimize boiler plate code.
 *
 * @author Luocheng Tang
 */
public final class ResponseEntityBuilder {

    private ResponseEntityBuilder() {
    }

    /**
     * Creates a typed ResponseEntity with HTTP status code 201 with no location.
     *
     * @return The corresponding ResponseEntity
     */
    public static <T> ResponseEntity<T> created() {
        return created(null, false);
    }

    /**
     * Creates a typed ResponseEntity with HTTP status code 201 with a given location.
     *
     * @param location
     *            The location of the created resource
     * @return The corresponding ResponseEntity
     */
    public static <T> ResponseEntity<T> created(final String location) {
        // Resource creation by default return 201 "created"
        return created(location, false);
    }

    /**
     * Creates a typed ResponseEntity with HTTP status code 201/204 with a given location.
     *
     * @param location
     *            The location of the created resource
     * @param noContent
     *            false means updated resource which returns 204, true means created resource which returns 201
     * @return The corresponding ResponseEntity
     */
    public static <T> ResponseEntity<T> created(final String location, final boolean noContent) {
        HttpStatus status = noContent ? HttpStatus.NO_CONTENT : HttpStatus.CREATED;

        if (location != null) {

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(location));
            return new ResponseEntity<T>(headers, status);
        }
        return new ResponseEntity<T>(status);
    }

    public static <T> ResponseEntity<T> created(final boolean noContent, final String uriTemplate,
            final String... keyValues) {
        URI resourceUri = UriTemplateUtils.expand(uriTemplate, keyValues);
        return created(resourceUri.getPath(), noContent);
    }

    /**
     * Creates a typed ResponseEntity with HTTP status code 200 with no response payload.
     *
     * @return The corresponding ResponseEntity
     */
    public static <T> ResponseEntity<T> ok() {
        return ok(null);
    }

    /**
     * Creates a typed ResponseEntity with HTTP status code 200 with a given response payload.
     *
     * @param response
     *            The response payload
     * @return The corresponding ResponseEntity
     */
    public static <T> ResponseEntity<T> ok(final T response) {
        if (response != null) {
            return new ResponseEntity<T>(response, HttpStatus.OK);
        }
        return new ResponseEntity<T>(HttpStatus.OK);
    }

    /**
     * Creates a typed ResponseEntity with HTTP status code 204 with no response payload.
     *
     * @return The corresponding ResponseEntity
     */
    public static ResponseEntity<Void> noContent() {
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    /**
     * Creates a typed ResponseEntity with HTTP status code 200 with no response payload.
     *
     * @return The corresponding ResponseEntity
     */
    public static <T> ResponseEntity<T> notFound() {
        return new ResponseEntity<T>(HttpStatus.NOT_FOUND);
    }

    /**
     * Response entity usually to reflect semantically invalid input data. Creates a typed ResponseEntity with HTTP
     * status code 422 with no response payload.
     *
     * @return The corresponding ResponseEntity
     */
    public static ResponseEntity<Void> unprocessable() {
        return new ResponseEntity<Void>(HttpStatus.UNPROCESSABLE_ENTITY);
    }
}

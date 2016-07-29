package com.lvmama.vst.hhs.common.web;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.util.UriTemplate;

/**
 * @author Luocheng Tang
 */
@SuppressWarnings("nls")
public final class UriTemplateUtils {

    private UriTemplateUtils() {
        // Prevents instantiation.
    }

    /**
     * Generates an instance of the URI according to the template given by uriTemplate, by expanding the variables
     * with the values provided by keyValues.
     *
     * @param uriTemplate
     *            The URI template
     * @param keyValues
     *            Dynamic list of string of the form "key:value" 
     * @return The corresponding URI instance
     */
    public static URI expand(final String uriTemplate, final String... keyValues) {

        UriTemplate template = new UriTemplate(uriTemplate);
        Map<String, String> uriVariables = new HashMap<>();  

        for (String kv : keyValues) {
            String[] keyValue = kv.split(":");
            uriVariables.put(keyValue[0], keyValue[1]);
        }
        return template.expand(uriVariables);
    }

    public static boolean isCanonicalMatch(final String uriTemplateDef, final String resourceUri) {
        String canonicalResourceURI = URI.create(resourceUri).normalize().toString();
        UriTemplate uriTemplate = new UriTemplate(appendTrailingSlash(uriTemplateDef));
        return uriTemplate.matches(appendTrailingSlash(canonicalResourceURI));
    }

    public static String appendTrailingSlash(final String s) {
        if (!s.endsWith("/")) {
            return new StringBuilder(s).append("/").toString();
        }
        return s;
    }

}

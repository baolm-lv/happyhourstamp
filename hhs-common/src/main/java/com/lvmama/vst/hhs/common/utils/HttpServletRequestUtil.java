package com.lvmama.vst.hhs.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HttpServletRequestUtil {

    private HttpServletRequestUtil() {
        // Prevents instantiation.
    }

    /**
     * @return empty string if requestHostname and baseDomain are identical, null if domain is not a sub-string of
     *         requestHostname
     */

    public static String getSubdomain(final String requestHostname, final String baseDomain) {

        if (requestHostname.equals(baseDomain)) {
            return "";
        }

        String regexPattern = "^(.*?)\\." + Pattern.quote(baseDomain) + "$";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(requestHostname);
        if (!matcher.matches()) {
            // There is no zone scope for this request. Return null
            return null;
        }

        String subdomain = matcher.group(1);

        return subdomain;
    }
}

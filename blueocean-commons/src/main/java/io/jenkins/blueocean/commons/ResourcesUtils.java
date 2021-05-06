package io.jenkins.blueocean.commons;


import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ResourcesUtils {
    public ResourcesUtils() {
        // no op
    }

    /**
     * use {@link #toString(URL, Charset)} using the default charset {@link StandardCharsets#UTF_8}
     * @see #toString(URL, Charset)
     */
    public static String toString(URL resource)
        throws IOException
    {
        return toString(resource, StandardCharsets.UTF_8);
    }

    /**
     *
     * @param resource the URL resource
     * @param charset the charset to use
     * @return turn the  URL resource into a String using the given Charset
     * @throws IOException if any issue reading the resource
     */
    public static String toString(URL resource, Charset charset)
        throws IOException
    {
        return IOUtils.toString(resource, charset);
    }

}

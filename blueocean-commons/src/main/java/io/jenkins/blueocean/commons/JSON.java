package io.jenkins.blueocean.commons;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Nonnull;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

/** JSON Utility */
public class JSON {

    /**
     * Sanitises string by removing any ISO control characters, tabs and line breaks
     * @param input string
     * @return sanitized string
     */
    public static String sanitizeString(@Nonnull String input) {
        if (StringUtils.isEmpty(input)) {
            return null;
        }
        StringCharacterIterator iter = new  StringCharacterIterator(input);
        StringBuilder sb = new StringBuilder(input.length());
        for(char c = iter.first(); c != CharacterIterator.DONE; c = iter.next()) {
            if(Character.isISOControl(c)){
                continue;
            }
            switch (c) {
                case '\r':
                case '\n':
                case '\t':
                    continue;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    private JSON() {}
}

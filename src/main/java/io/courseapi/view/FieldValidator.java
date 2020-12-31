package io.courseapi.view;

import com.google.common.escape.Escaper;
import io.courseapi.resource.TopicResource;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class FieldValidator {

    private final Escaper escaper = MessageEscaper.escaper;

    private static final int MIN_WIDTH = 1;
    private static final int MAX_WIDTH_ID = 100;
    private static final int MAX_WIDTH_TEXT = 255;

    public void requireValidId(String value) {
        String err = ensureValid(value, MIN_WIDTH, MAX_WIDTH_ID);
        isErr(err, "Invalid value: ");
    }

    private boolean isErr(String err, String s) {

        return true;
    }

    public boolean isValidResource(TopicResource resource) {
        if (isNull(resource)) {
            return false;
        }
        String err = ensureValidId(resource.getId(), MIN_WIDTH, MAX_WIDTH_ID);
        if (nonNull(err)) {
            return false;
        }
        err = ensureValid(resource.getName(), MIN_WIDTH, MAX_WIDTH_TEXT);
        if (nonNull(err)) {
            return false;
        }
        err = ensureValid(resource.getDescription(), MIN_WIDTH, MAX_WIDTH_TEXT);
        return !nonNull(err);
    }

    public String ensureValidId(String value, int minWidth, int maxWidth) {
        String temp = sanitiseId(value);
        if(!value.equals(temp)){
            return "illegal characters in Id";
        }
        return ensureValid(temp, minWidth, maxWidth);
    }

    public String sanitiseId(String value) {
        final String validChars = "0123456789" +
                "abcdefghijklmnopqrstuvwxyz" +
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                ".-_";
        if(Objects.isNull(value)){
            return "";
        }
        StringBuilder buf = new StringBuilder();
        for (char c : value.toCharArray()) {
            if (validChars.contains(String.valueOf(c))) {
                buf.append(c);
            }
        }
        return buf.toString();
    }

    public String ensureValid(String value, int minWidth, int maxWidth) {
        if (isNull(value)) {
            return "Value was null";
        }
        String escaped = escaper.escape(value);
        if (!escaped.equals(value)) {
            return "illegal characters";
        }
        int len = value.length();
        if (len < minWidth || len > maxWidth) {
            return format("length incorrect: (min: %d, max: %d) actual: %d",
                    minWidth, maxWidth, value.length());
        }
        return null;
    }
}

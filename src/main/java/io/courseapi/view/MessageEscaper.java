package io.courseapi.view;

import com.google.common.escape.Escaper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class MessageEscaper extends Escaper {

    public static final MessageEscaper escaper = new MessageEscaper()
            .addEscape("\"", "&quot;")
            // Note: "&apos;" is not defined in HTML 4.01.
            .addEscape("'", "&#39;")
            .addEscape("&", "&amp;")
            .addEscape("<", "&lt;")
            .addEscape(">", "&gt;")
            .addEscape("{", "&#7b;")
            .addEscape("}", "&#7d;")
            .addEscape("/", "&#2f;")
            .addEscape("\\", "&#5c;");

    private Map<String, String> fragments = new HashMap<>();

    private MessageEscaper addEscape(String frag, String replacement) {
        fragments.putIfAbsent(frag, replacement);
        return this;
    }

    @Override
    public String escape(String value) {
        if (Objects.isNull(value)) {
            return value;
        }
        return replaceAll(value);
    }

    private String replaceAll(String value) {
        final int maxLoops = 10;
        String temp = value;
        for (Map.Entry<String, String> entry : fragments.entrySet()) {
            for (int i = 0; i < maxLoops && temp.contains(entry.getKey()); i++) {
                temp = temp.replace(entry.getKey(), entry.getValue());
            }
        }
        return temp;
    }

    public String escapeException(Throwable t) {
        String message = t.getMessage();
        if (Objects.isNull(message)) {
            return t.getClass().getSimpleName();
        }
        return escapeString(message);
    }

    public String escapeString(String s) {
        if (Objects.isNull(s)) {
            return "";
        }
        return escaper.escape(s);
    }
}

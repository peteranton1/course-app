package io.courseapi.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class IdGenerator {

    public static final String GEN_CHARS = "0123456789" +
            "abcdefghijklmnopqrstuvwxyz";
    private Random random;

    public IdGenerator(@Autowired Random random) {
        this.random = random;
    }

    public String generateId(String prefix, int width) {
        StringBuilder buf = new StringBuilder();
        buf.append(prefix);
        for (int i = prefix.length(); i < width; i++) {
            buf.append(GEN_CHARS.charAt(random.nextInt(GEN_CHARS.length())));
        }
        return buf.toString();
    }
}

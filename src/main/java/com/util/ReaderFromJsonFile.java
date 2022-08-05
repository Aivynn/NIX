package com.util;

import com.Command.Reader;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class ReaderFromJsonFile extends Reader {
    private static final Pattern PATTERN_LEVEL = Pattern.compile("\\\"(.*)\\\"([A-z0-9-:$]+)");


    public HashMap<String, String> parserToProduct() throws IOException, URISyntaxException {

        URL reader = this.getClass().getClassLoader().getResource("phone.json");
        HashMap<String, String> map = new HashMap<>();
        try (Stream<String> br = Files.lines(Path.of(reader.toURI()))) {
            br.forEach(line -> {
                boolean flag = false;
                int i = 0;
                String result = getLevelFromLine(line).trim();
                if (!result.equals("empty")) {
                    while (!flag) {
                        if (result.charAt(i) == 58) {
                            map.put(result.substring(0, i - 1), result.substring(i + 1).trim());
                            flag = true;
                        }
                        i++;
                    }
                }
            });
        }
        return map;
    }

    private static String getLevelFromLine(String line) {
        final Matcher matcher = PATTERN_LEVEL.matcher(line);
        if (matcher.find()) {
            return matcher.group(1) + matcher.group(2);
        }
        return "empty";
    }
}

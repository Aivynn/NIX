package com.util;

import com.Command.Reader;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class ReadFromXMLFIle extends Reader {
    private static final Pattern PATTERN_LEVEL = Pattern.compile("[> \\:]+[A-Z,A-z,0-9,-,:]+");

    public HashMap<String, String> parserToProduct() throws URISyntaxException {
        URL reader = this.getClass().getClassLoader().getResource("phone.xml");
        HashMap<String, String> map = new HashMap<>();
        try (Stream<String> br = Files.lines(Path.of(reader.toURI()))) {
            br.skip(2).forEach(line -> {
                int i = 0;
                boolean flag = false;
                    String result = getLevelFromLine(line);
                    if(!result.equals("empty")) {
                    while (!flag) {
                        if (result.charAt(i) == 32) {
                            map.put(result.substring(0, i), (result.substring(i + 1)));
                            flag = true;
                        }
                        i++;
                    }
                    }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return map;
    }

    private static String getLevelFromLine(String line) {
        boolean flag = false;
        int i = 0;
        String key = "";
        String value;
        int start = 0;
        int end = 0;
        boolean inner = false;
        final Matcher matcher = PATTERN_LEVEL.matcher(line);
        if (matcher.find()) {
            while (!flag) {
                if (line.charAt(i) == 60) {
                    if (inner) {
                        end = i;
                        flag = true;
                    } else {
                        start = i + 1;
                    }
                }
                if (line.charAt(i) == 62) {
                    end = i;
                }
                if (start != 0 && end != 0 && !inner) {
                    key = line.substring(start, end);
                    start = i + 1;
                    end = 0;
                    inner = true;
                }
                i++;
            }
        } else {
            return "empty";
        }
        value = line.substring(start, end);
        return key + " " + value;
    }

}

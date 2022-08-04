package com.Command;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReaderFromJsonFile extends Reader {
    private static final Pattern PATTERN_LEVEL = Pattern.compile("\\\"(.*)\\\"([A-z0-9-:$]+)");


    public HashMap<String, String> parserToProduct() throws IOException {

        HashMap<String, String> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/model/phone.json"))) {
            String line;
            while ((line = br.readLine()) != null) {
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
            }
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

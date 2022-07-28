package com.Command;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadFromXMLFIle extends Reader {
    private static final Pattern PATTERN_LEVEL = Pattern.compile("[> \\:]+[A-Z,A-z,0-9,-,:]+");

    public HashMap<String, String> parserToProduct() {
        HashMap<String, String> map = new HashMap<>();
        boolean flag = false;
        int ind = 0;
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/model/phone.xml"))) {
            String line;
            while ((line = br.readLine()) != null) {
                ind++;
                if (ind > 2) {
                    String result = getLevelFromLine(line);
                    if (result.equals("empty")) {
                        continue;
                    }
                    while (!flag) {
                        if (result.charAt(i) == 32) {
                            map.put(result.substring(0, i), (result.substring(i + 1)));
                            flag = true;
                        }
                        i++;
                    }
                    flag = false;
                    i = 0;
                }
            }
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

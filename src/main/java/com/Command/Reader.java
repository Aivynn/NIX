package com.Command;

import java.io.IOException;
import java.util.HashMap;

public abstract class Reader {
    public abstract HashMap<String, String> parserToProduct() throws IOException;
}

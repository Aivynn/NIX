package com.Command;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

public interface Command {
    void execute() throws IOException, URISyntaxException;
    Scanner SCANNER = new Scanner(System.in);

}

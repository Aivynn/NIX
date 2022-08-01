package com;

import com.Command.Commands;
import com.Command.ConsoleMenu;

import java.io.IOException;



public class Main {

    public static void main(String[] args) throws IOException {
        final Commands[] values = Commands.values();
        boolean exit;

        do {
            exit = ConsoleMenu.menu(values);
        } while (!exit);
    }

}

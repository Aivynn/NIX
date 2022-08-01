package com;

import com.Command.Command;
import com.Command.Commands;

import java.io.IOException;

import static com.Command.Command.SCANNER;


public class Main {

    public static void main(String[] args) throws IOException {
        final Commands[] values = Commands.values();
        boolean exit;

        do {
            exit = userAction(values);
        } while (!exit);
    }

    private static boolean userAction(final Commands[] values) throws IOException {
        int userCommand = -1;
        do {
            for (int i = 0; i < values.length; i++) {
                System.out.printf("%d) %s%n", i, values[i].getName());
            }
            int input = SCANNER.nextInt();
            if (input >= 0 && input < values.length) {
                userCommand = input;
            }
        } while (userCommand == -1);
        final Command command;
        command = values[userCommand].getCommand();
        if (command == null) {
            return true;
        } else {
            command.execute();
            return false;
        }
    }
}

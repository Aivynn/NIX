package com.Command;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.Command.Command.SCANNER;

public class StartMenu {

    public static void simpleMenu(Commands[] values) throws IOException, URISyntaxException {
        boolean exit;
        do {
            exit = userMenu(values);
        }while (!exit);
    }

    private static boolean userMenu(Commands[] values) throws IOException, URISyntaxException {
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

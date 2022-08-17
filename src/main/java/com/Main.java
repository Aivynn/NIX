package com;

import com.Command.Command;
import com.Command.Commands;
import com.util.Annotations;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

import static com.Command.Command.SCANNER;


public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, NoSuchFieldException {
        Annotations test = new Annotations();
        test.repositories();
        test.services();
        test.autowiredFields();
        final Commands[] values = Commands.values();
        boolean exit;

        do {
            exit = userAction(values);
        } while (!exit);
    }

    private static boolean userAction(final Commands[] values) throws IOException, URISyntaxException {
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

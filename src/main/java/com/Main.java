package com;

import com.Command.Commands;
import com.Command.StartMenu;
import com.util.Annotations;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;



public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, NoSuchFieldException, ClassNotFoundException {
        Annotations.repositories();
        Annotations.autowiredFields();
        StartMenu.simpleMenu(Commands.values());
    }
}

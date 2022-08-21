package com;

import com.Command.Command;
import com.Command.Commands;
import com.Command.StartMenu;
import com.model.Manufacturer;
import com.model.Notebook;
import com.service.NotebookService;
import com.service.PhoneService;
import com.service.SmartwatchService;
import com.util.Annotations;
import com.util.Autowired;
import com.util.UserInputUtil;

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

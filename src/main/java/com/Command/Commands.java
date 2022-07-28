package com.Command;

import lombok.Getter;

@Getter
public enum Commands {

    CREATE("Create product", new Create()),
    UPDATE("Update product", new Update()),
    PRINT("Print products", new Print()),
    DELETE("Delete products", new Delete()),
    READ_FROM_FILE("Create object from XML/JSON", new CreateObject()),
    EXIT("Exit", null);

    private final String name;
    private final Command command;

    Commands(String name, Command command) {
        this.name = name;
        this.command = command;
    }
}

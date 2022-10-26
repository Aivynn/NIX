package com.config;

import org.flywaydb.core.Flyway;


public class FlywayConfig {

    private static final String URL = "jdbc:postgresql://localhost:5432/homework";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";
    private static final String LOCATION = "db/migration";

    private FlywayConfig() {
    }

    public static Flyway configureFlyway() {
        return Flyway.configure()
                .dataSource(URL, USER, PASSWORD)
                .baselineOnMigrate(true)
                .locations(LOCATION)
                .load();
    }
}
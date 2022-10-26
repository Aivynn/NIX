package com;

import com.config.FlywayConfig;
import org.flywaydb.core.Flyway;

public class FlywayMain {
    public static void main(String[] args) {
        Flyway flyway = FlywayConfig.configureFlyway();
        flyway.migrate();
    }
}

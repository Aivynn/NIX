package com.util;

import com.config.JDBCConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableCreator {

    private static final Connection CONNECTION = JDBCConfig.getConnection();

    public static void tableChecker() {
        String[] tables = new String[6];
        tables[0] = "CREATE SCHEMA IF NOT EXISTS homeworks";
        tables[1] = "CREATE TABLE IF NOT EXISTS homeworks.\"Smartwatch\"\n" +
                "(\n" +
                "    id character varying COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                "    model character varying COLLATE pg_catalog.\"default\",\n" +
                "    manufacturer character varying COLLATE pg_catalog.\"default\",\n" +
                "    count bigint,\n" +
                "    price double precision,\n" +
                "    \"time\" timestamp without time zone,\n" +
                "    title character varying COLLATE pg_catalog.\"default\",\n" +
                "    CONSTRAINT \"Smartwatch_pkey\" PRIMARY KEY (id)\n" +
                ")\n" +
                "\n" +
                "TABLESPACE pg_default;\n" +
                "\n" +
                "ALTER TABLE IF EXISTS homeworks.\"Smartwatch\"\n" +
                "    OWNER to postgres;";
        tables[2] = "CREATE TABLE IF NOT EXISTS homeworks.\"Phone\"\n" +
                "(\n" +
                "    id character varying COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                "    model character varying COLLATE pg_catalog.\"default\",\n" +
                "    manufacturer character varying COLLATE pg_catalog.\"default\",\n" +
                "    count bigint,\n" +
                "    price double precision,\n" +
                "    operationsystemversion bigint,\n" +
                "    operationsystemname character varying COLLATE pg_catalog.\"default\",\n" +
                "    \"time\" timestamp without time zone,\n" +
                "    title character varying COLLATE pg_catalog.\"default\",\n" +
                "    CONSTRAINT \"Phone_pkey\" PRIMARY KEY (id)\n" +
                ")\n" +
                "\n" +
                "TABLESPACE pg_default;\n" +
                "\n" +
                "ALTER TABLE IF EXISTS homeworks.\"Phone\"\n" +
                "    OWNER to postgres;";
        tables[3] = "CREATE TABLE IF NOT EXISTS homeworks.\"Notebook\"\n" +
                "(\n" +
                "    id character varying COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                "    model character varying COLLATE pg_catalog.\"default\",\n" +
                "    manufacturer character varying COLLATE pg_catalog.\"default\",\n" +
                "    count bigint,\n" +
                "    price double precision,\n" +
                "    \"time\" timestamp without time zone,\n" +
                "    title character varying COLLATE pg_catalog.\"default\",\n" +
                "    CONSTRAINT \"Notebook_pkey\" PRIMARY KEY (id)\n" +
                ")\n" +
                "\n" +
                "TABLESPACE pg_default;\n" +
                "\n" +
                "ALTER TABLE IF EXISTS homeworks.\"Notebook\"\n" +
                "    OWNER to postgres;";
        tables[4] = "CREATE TABLE IF NOT EXISTS homeworks.\"Invoice\"\n" +
                "(\n" +
                "    id character varying COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                "    sum double precision,\n" +
                "    \"time\" timestamp without time zone,\n" +
                "    CONSTRAINT \"Invoice_pkey\" PRIMARY KEY (id)\n" +
                ")\n" +
                "\n" +
                "TABLESPACE pg_default;\n" +
                "\n" +
                "ALTER TABLE IF EXISTS homeworks.\"Invoice\"\n" +
                "    OWNER to postgres;";
        tables[5] = "CREATE TABLE IF NOT EXISTS homeworks.products\n" +
                "(\n" +
                "    id character varying COLLATE pg_catalog.\"default\" NOT NULL,\n" +
                "    invoiceid character varying COLLATE pg_catalog.\"default\",\n" +
                "    productid character varying COLLATE pg_catalog.\"default\",\n" +
                "    producttype character varying COLLATE pg_catalog.\"default\",\n" +
                "    \"time\" timestamp without time zone,\n" +
                "    CONSTRAINT products_pkey PRIMARY KEY (id),\n" +
                "    CONSTRAINT invoice FOREIGN KEY (invoiceid)\n" +
                "        REFERENCES homeworks.\"Invoice\" (id) MATCH SIMPLE\n" +
                "        ON UPDATE NO ACTION\n" +
                "        ON DELETE NO ACTION\n" +
                ")\n" +
                "\n" +
                "TABLESPACE pg_default;\n" +
                "\n" +
                "ALTER TABLE IF EXISTS homeworks.products\n" +
                "    OWNER to postgres;\n" +
                "-- Index: fki_S\n" +
                "\n" +
                "-- DROP INDEX IF EXISTS homeworks.\"fki_S\";\n" +
                "\n" +
                "CREATE INDEX IF NOT EXISTS \"fki_S\"\n" +
                "    ON homeworks.products USING btree\n" +
                "    (id COLLATE pg_catalog.\"default\" ASC NULLS LAST)\n" +
                "    TABLESPACE pg_default;\n" +
                "-- Index: fki_n\n" +
                "\n" +
                "-- DROP INDEX IF EXISTS homeworks.fki_n;\n" +
                "\n" +
                "CREATE INDEX IF NOT EXISTS fki_n\n" +
                "    ON homeworks.products USING btree\n" +
                "    (id COLLATE pg_catalog.\"default\" ASC NULLS LAST)\n" +
                "    TABLESPACE pg_default;\n" +
                "-- Index: fki_product\n" +
                "\n" +
                "-- DROP INDEX IF EXISTS homeworks.fki_product;\n" +
                "\n" +
                "CREATE INDEX IF NOT EXISTS fki_product\n" +
                "    ON homeworks.products USING btree\n" +
                "    (productid COLLATE pg_catalog.\"default\" ASC NULLS LAST)\n" +
                "    TABLESPACE pg_default;\n" +
                "-- Index: fki_productNotebook\n" +
                "\n" +
                "-- DROP INDEX IF EXISTS homeworks.\"fki_productNotebook\";\n" +
                "\n" +
                "CREATE INDEX IF NOT EXISTS \"fki_productNotebook\"\n" +
                "    ON homeworks.products USING btree\n" +
                "    (productid COLLATE pg_catalog.\"default\" ASC NULLS LAST)\n" +
                "    TABLESPACE pg_default;\n" +
                "-- Index: fki_productPhone\n" +
                "\n" +
                "-- DROP INDEX IF EXISTS homeworks.\"fki_productPhone\";\n" +
                "\n" +
                "CREATE INDEX IF NOT EXISTS \"fki_productPhone\"\n" +
                "    ON homeworks.products USING btree\n" +
                "    (productid COLLATE pg_catalog.\"default\" ASC NULLS LAST)\n" +
                "    TABLESPACE pg_default;\n" +
                "-- Index: fki_productSmartwatch\n" +
                "\n" +
                "-- DROP INDEX IF EXISTS homeworks.\"fki_productSmartwatch\";\n" +
                "\n" +
                "CREATE INDEX IF NOT EXISTS \"fki_productSmartwatch\"\n" +
                "    ON homeworks.products USING btree\n" +
                "    (productid COLLATE pg_catalog.\"default\" ASC NULLS LAST)\n" +
                "    TABLESPACE pg_default;";
        for (String sql : tables) {
            try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
                statement.execute();
            } catch (SQLException e) {
                throw new RuntimeException();
            }
        }
    }
}

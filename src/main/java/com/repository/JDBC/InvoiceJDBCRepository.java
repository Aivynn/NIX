package com.repository.JDBC;

import com.config.JDBCConfig;
import com.model.*;
import lombok.SneakyThrows;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import static com.model.ProductType.*;


public class InvoiceJDBCRepository {

    private List<Product> products = new ArrayList<>();

    private static final Connection CONNECTION = JDBCConfig.getConnection();


    public void save(Invoice invoice) throws SQLException {
        CONNECTION.setAutoCommit(false);
        String sql = "INSERT INTO \"homeworks\".\"Invoice\" (id,sum, time) VALUES (?, ?, ?)";
        String insertIntoInvoice = "INSERT INTO \"homeworks\".\"products\" (id, invoiceId, productId, producttype,time) VALUES (?, ?, ?, ?,?)";
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            setObjectFields(statement, invoice);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<? extends Product> products = invoice.getProducts();
        try (final PreparedStatement statement = CONNECTION.prepareStatement(insertIntoInvoice)) {
            for (Product product : products) {
                setObjectFields(statement, invoice, product);
                statement.addBatch();
            }
            statement.executeBatch();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        CONNECTION.commit();
    }


    public void update(String id) {
        String sql = """
                UPDATE homeworks."Invoice"
                SET time = ?
                WHERE id = ?
                """;
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            statement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            statement.setString(2, id);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public HashMap<String,Double> GroupBySum() {
        HashMap<String,Double> invoicesWithSamePrice = new HashMap<String, Double>();
        String sql = """
                SELECT count(id) as count, id,sum from homeworks."Invoice"
                GROUP by id
                """;
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                System.out.println(new InvoiceData(resultSet.getString("id"),resultSet.getDouble("sum"),resultSet.getInt("count")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return invoicesWithSamePrice;
    }


    public Invoice findbyId(String id) {
        List<Product> invoiceProducts = new ArrayList<>();
        Double sum = 0.0;
        LocalDateTime time = null;
        String invoiceId = "";
        String sql = """
                SELECT
                                homeworks.products.producttype as product_type,
                                homeworks.products.invoiceid as Invoice_id,
                                
                                homeworks."Phone".id as Phone_id,
                                homeworks."Phone".title as Phone_title,
                                homeworks."Phone".model as Phone_model,
                                homeworks."Phone".count as Phone_count,
                                homeworks."Phone".price as Phone_price,
                                homeworks."Phone".manufacturer as Phone_manufacturer,
                                homeworks."Phone".operationsystemversion as operationsystemversion,
                                homeworks."Phone".operationsystemname as operationsystemname,
                                homeworks."Phone".time as Phone_time,
                               
                                 
                                homeworks."Notebook".id as Notebook_id,
                                 homeworks."Notebook".title as Notebook_title,
                                homeworks."Notebook".count as Notebook_count,
                                 homeworks."Notebook".price as Notebook_price,
                                homeworks."Notebook".manufacturer as Notebook_manufacturer,
                                homeworks."Notebook".model as Notebook_model,
                                 homeworks."Notebook".time as Notebook_time,
                                
                                homeworks."Smartwatch".manufacturer as Smartwatch_manufacturer,
                                homeworks."Smartwatch".model as Smartwatch_model,
                                homeworks."Smartwatch".id as Smartwatch_id,
                                homeworks."Smartwatch".time as Smartwatch_time,
                                homeworks."Smartwatch".title as Smartwatch_title,
                                homeworks."Smartwatch".price as Smartwatch_price,
                                homeworks."Smartwatch".count as Smartwatch_count,
                                
                                homeworks."Invoice".sum as Invoice_sum,
                                homeworks."Invoice".id as Invoice_id,
                                homeworks."Invoice".time as Invoice_time
                                from (homeworks.products
                                LEFT JOIN homeworks."Invoice" on "Invoice".id = products.invoiceid
                                LEFT JOIN homeworks."Phone" on "Phone".id = products.productid
                                LEFT JOIN homeworks."Notebook" on "Notebook".id = products.productid
                				LEFT JOIN homeworks."Smartwatch" on "Smartwatch".id = products.productid)
                WHERE homeworks.products.invoiceid=?
                """;
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            statement.setString(1, id);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                time = resultSet.getTimestamp("invoice_time").toLocalDateTime();
                sum = resultSet.getDouble("invoice_sum");
                invoiceId = resultSet.getString("invoice_id");
                invoiceProducts = receiveFields(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return new Invoice.InvoiceBuilder()
                .sum(sum)
                .time(time)
                .products(invoiceProducts)
                .id(invoiceId)
                .build();
    }

    public List<Invoice> findBySum(double sum) {
        Set<String> ids = new HashSet<>();
        List<Invoice> invoices = new ArrayList<>();
        String sql = """
                SELECT
                                homeworks.products.producttype as product_type,
                                homeworks.products.invoiceid as Invoice_id,
                                
                                homeworks."Phone".id as Phone_id,
                                homeworks."Phone".title as Phone_title,
                                homeworks."Phone".model as Phone_model,
                                homeworks."Phone".count as Phone_count,
                                homeworks."Phone".price as Phone_price,
                                homeworks."Phone".manufacturer as Phone_manufacturer,
                                homeworks."Phone".operationsystemversion as operationsystemversion,
                                homeworks."Phone".operationsystemname as operationsystemname,
                                homeworks."Phone".time as Phone_time,
                               
                                 
                                homeworks."Notebook".id as Notebook_id,
                                 homeworks."Notebook".title as Notebook_title,
                                homeworks."Notebook".count as Notebook_count,
                                 homeworks."Notebook".price as Notebook_price,
                                homeworks."Notebook".manufacturer as Notebook_manufacturer,
                                homeworks."Notebook".model as Notebook_model,
                                 homeworks."Notebook".time as Notebook_time,
                                
                                homeworks."Smartwatch".manufacturer as Smartwatch_manufacturer,
                                homeworks."Smartwatch".model as Smartwatch_model,
                                homeworks."Smartwatch".id as Smartwatch_id,
                                homeworks."Smartwatch".time as Smartwatch_time,
                                homeworks."Smartwatch".title as Smartwatch_title,
                                homeworks."Smartwatch".price as Smartwatch_price,
                                homeworks."Smartwatch".count as Smartwatch_count,
                                
                                homeworks."Invoice".sum as Invoice_sum,
                                homeworks."Invoice".id as Invoice_id,
                                homeworks."Invoice".time as Invoice_time
                                from (homeworks.products
                                LEFT JOIN homeworks."Invoice" on "Invoice".id = products.invoiceid
                                LEFT JOIN homeworks."Phone" on "Phone".id = products.productid
                                LEFT JOIN homeworks."Notebook" on "Notebook".id = products.productid
                				LEFT JOIN homeworks."Smartwatch" on "Smartwatch".id = products.productid)
                				
                                WHERE homeworks."Invoice".sum > ?
                """;
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            statement.setDouble(1, sum);
            final ResultSet resultSet = statement.executeQuery();
            boolean flag = true;
            boolean firstCircle = true;
            List<Product> invoiceProducts = new ArrayList<>();
            while (resultSet.next()) {
                String id = resultSet.getString("invoice_id");
                if (!(ids.contains(id))) {
                    ids.add(id);
                    if (!firstCircle) {
                        flag = false;
                    }
                    firstCircle = false;
                }
                if (!flag) {
                    Invoice invoice = new Invoice.InvoiceBuilder()
                            .sum(resultSet.getDouble("invoice_sum"))
                            .id(resultSet.getString("invoice_id"))
                            .time(resultSet.getTimestamp("invoice_time").toLocalDateTime())
                            .products(invoiceProducts)
                            .build();
                    invoices.add(invoice);
                    invoiceProducts.clear();
                    flag = true;
                }
                ProductType type = ProductType.valueOf(resultSet.getString("product_type"));
                switch (type) {
                    case PHONE -> invoiceProducts.add(createPhone(resultSet));
                    case NOTEBOOK -> invoiceProducts.add(createNotebook(resultSet));
                    case SMARTWATCH -> invoiceProducts.add(createSmartwatch(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return invoices;
    }

    public int countRows() {
        String sql = "select count(id) from homeworks.\"Invoice\"";
        int count = 0;
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            final ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            count = resultSet.getInt("count");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;

    }

    @SneakyThrows
    private void setObjectFields(PreparedStatement statement, Invoice invoice) {
        statement.setString(1, invoice.getId());
        statement.setDouble(2, invoice.getSum());
        statement.setTimestamp(3, Timestamp.valueOf(invoice.getTime()));

    }

    @SneakyThrows
    private List<String> receiveIdFields(ResultSet resultSet) {
        List<String> invoiceIds = new ArrayList<>();
        while (resultSet.next()) {
            invoiceIds.add(resultSet.getString("id"));
        }
        return invoiceIds;
    }


    @SneakyThrows
    private List<Product> receiveFields(ResultSet resultSet) {
        List<Product> products = new ArrayList<>();
        while (resultSet.next()) {
            ProductType type = ProductType.valueOf(resultSet.getString("product_type"));
            switch (type) {
                case PHONE -> products.add(createPhone(resultSet));
                case NOTEBOOK -> products.add(createNotebook(resultSet));
                case SMARTWATCH -> products.add(createSmartwatch(resultSet));
            }
        }
        return products;

    }

    @SneakyThrows
    private <T extends Product> void setObjectFields(final PreparedStatement statement, final Invoice invoice, T product) {
        statement.setString(1, String.valueOf(UUID.randomUUID()));
        statement.setString(2, invoice.getId());
        statement.setString(3, product.getId());
        statement.setString(4, product.getType().toString());
        statement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
    }

    private Smartwatch createSmartwatch(ResultSet resultSet) throws SQLException {
        String model = resultSet.getString("Smartwatch_model");
        Manufacturer manufacturer = Manufacturer.valueOf(resultSet.getString("Smartwatch_manufacturer"));
        LocalDateTime date = resultSet.getTimestamp("smartwatch_time").toLocalDateTime();

        return new Smartwatch.SmartwatchBuilder(resultSet.getDouble("smartwatch_price"), manufacturer, date)
                .model(model)
                .title(resultSet.getString("smartwatch_title"))
                .count(resultSet.getInt("smartwatch_count"))
                .build();

    }

    private Phone createPhone(ResultSet resultSet) throws SQLException {


        Phone phone = new Phone("Phone_title",
                resultSet.getInt("Phone_count"),
                resultSet.getDouble("Phone_price"),
                resultSet.getString("Phone_model"),
                Manufacturer.valueOf(resultSet.getString("Phone_manufacturer")),
                List.of("asdsadsad", "asdsadsad"),
                new OperationSystem(resultSet.getInt("operationsystemversion"), resultSet.getString("operationsystemname")),
                resultSet.getTimestamp("phone_time").toLocalDateTime());
        return phone;
    }

    private Notebook createNotebook(ResultSet resultSet) throws SQLException {
        String model = resultSet.getString("Notebook_model");
        Manufacturer manufacturer = Manufacturer.valueOf(resultSet.getString("Notebook_manufacturer"));
        Notebook notebook = new Notebook(
                resultSet.getString("notebook_title"),
                resultSet.getInt("notebook_count"),
                resultSet.getDouble("notebook_price"),
                resultSet.getString("notebook_model"),
                Manufacturer.valueOf(resultSet.getString("Notebook_manufacturer"))
        );
        notebook.setDate(resultSet.getTimestamp("notebook_time").toLocalDateTime());
        return notebook;
    }
}


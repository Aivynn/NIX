package com.repository.JDBC;

import com.config.JDBCConfig;
import com.model.Manufacturer;
import com.model.Phone;
import com.model.Smartwatch;
import com.repository.CrudRepository;
import lombok.SneakyThrows;
import org.apache.commons.lang3.EnumUtils;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class SmartwatchJDBCRepository implements CrudRepository<Smartwatch> {

    private final Random random = new Random();
    private static final Connection CONNECTION = JDBCConfig.getConnection();

    @Override
    public void save(Smartwatch smartwatch) {

        String sql = "INSERT INTO \"homeworks\".\"Smartwatch\" (id,title,count,price, model, manufacturer) VALUES (?, ?, ?,?,?,?,?)";
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            setObjectFields(statement, smartwatch);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @SneakyThrows
    private void setObjectFields(final PreparedStatement statement, final Smartwatch phone) {
        statement.setString(1, phone.getId());
        statement.setString(2, phone.getTitle());
        statement.setInt(3, phone.getCount());
        statement.setDouble(4, phone.getPrice());
        statement.setString(5, phone.getModel());
        statement.setString(6, phone.getManufacturer().name());
        statement.setTimestamp(7, Timestamp.valueOf(phone.getDate()));
    }

    @SneakyThrows
    private Smartwatch setFieldsToObject(final ResultSet resultSet) throws SQLException {
        final String model = resultSet.getString("model");
        final Manufacturer manufacturer = EnumUtils.getEnum(Manufacturer.class, resultSet.getString("manufacturer"),
                Manufacturer.NONE);
        final Smartwatch smartwatch = new Smartwatch.SmartwatchBuilder((double) random.nextInt(1000),manufacturer, LocalDateTime.now())
                .title(resultSet.getString("title"))
                .count(resultSet.getInt("count"))
                .model(model)
                .date(resultSet.getTimestamp("date").toLocalDateTime())
                .build();
        smartwatch.setId(resultSet.getString("id"));
        return smartwatch;
    }

    @Override

    public void saveAll(List<Smartwatch> notebooks) {
        String sql = "INSERT INTO \"homeworks\".\"Smartwatch\" (id,title,count,price, model, manufacturer) VALUES (?, ?, ?,?,?,?,?)";

        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            CONNECTION.setAutoCommit(false);
            for (Smartwatch phone : notebooks) {
                setObjectFields(statement, phone);
                statement.addBatch();
            }
            statement.executeBatch();
            CONNECTION.commit();
            CONNECTION.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Smartwatch product) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM \"homeworks\".\"Smartwatch\" WHERE id = ?";
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            statement.setString(1, id);
            return statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Smartwatch> getAll() {
        final List<Smartwatch> result = new ArrayList<>();
        try (final Statement statement = CONNECTION.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM \"homeworks\".\"Smartwatch\"");
            while (resultSet.next()) {
                result.add(setFieldsToObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public Optional<Smartwatch> findById(String id) {
        String sql = "SELECT * FROM \"homeworks\".\"Smartwatch\" WHERE id = ?";
        Optional<Smartwatch> phone = Optional.empty();

        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            statement.setString(1, id);
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                phone = Optional.of(setFieldsToObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return phone;
    }

    public List<Smartwatch> getAllWithLimit(int limit) {
        String sql = "SELECT * FROM \"homeworks\".\"Smartwatch\" ORDER BY time ASC LIMIT ?";
        List<Smartwatch> result = new ArrayList<>();
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            statement.setInt(1,limit);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(setFieldsToObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public Optional<Smartwatch> findByTitle(String id) {
        return Optional.empty();
    }
}

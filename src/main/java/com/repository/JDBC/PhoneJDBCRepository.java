package com.repository.JDBC;

import com.config.JDBCConfig;
import com.model.Manufacturer;
import com.model.OperationSystem;
import com.model.Phone;
import com.util.Singleton;
import lombok.SneakyThrows;
import org.apache.commons.lang3.EnumUtils;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class PhoneJDBCRepository{

    private static final Connection CONNECTION = JDBCConfig.getConnection();

    public void save(Phone phone) {

        String sql = "INSERT INTO \"homeworks\".\"Phone\" (id,title,count,price, operationsystemversion,operationsystemname, model, manufacturer,time) VALUES (?, ?, ?,?,?,?,?,?,?)";
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            setObjectFields(statement, phone);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @SneakyThrows
    private void setObjectFields(final PreparedStatement statement, final Phone phone) {
        statement.setString(1, phone.getId());
        statement.setString(2, phone.getTitle());
        statement.setInt(3, phone.getCount());
        statement.setDouble(4, phone.getPrice());
        statement.setInt(5, phone.getOperationSystem().getVersion());
        statement.setString(6, phone.getOperationSystem().getDesignation());
        statement.setString(7, phone.getModel());
        statement.setString(8, phone.getManufacturer().name());
        statement.setTimestamp(9, Timestamp.valueOf(phone.getDate()));

    }
    @SneakyThrows
    private Phone setFieldsToObject(final ResultSet resultSet) throws SQLException {
        final String model = resultSet.getString("model");
        final Manufacturer manufacturer = EnumUtils.getEnum(Manufacturer.class, resultSet.getString("manufacturer"),
                Manufacturer.NONE);
        final Phone phone = new Phone("",
                resultSet.getInt("count"),
                resultSet.getDouble("price"),
                model,
                manufacturer,
                List.of("none"),
        new OperationSystem(resultSet.getInt(("operationsystemversion")),resultSet.getString("operationsystemname")),
                resultSet.getTimestamp("time").toLocalDateTime());
        phone.setId(resultSet.getString("id"));
        return phone;
    }


    public void saveAll(List<Phone> phones) {
        String sql = "INSERT INTO \"homeworks\".\"Phone\" (id,title,count,price, operationsystemversion,operationsystemname, model, manufacturer) VALUES (?, ?, ?,?,?,?,?,?,?)";

        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            CONNECTION.setAutoCommit(false);
            for (Phone phone : phones) {
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

    public boolean update(Phone product) {
        return false;
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM \"homeworks\".\"Phone\" WHERE id = ?";
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            statement.setString(1, id);
            return statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Phone> getAll() {
        final List<Phone> result = new ArrayList<>();
        try (final Statement statement = CONNECTION.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM \"homeworks\".\"Phone\"");
            while (resultSet.next()) {
                result.add(setFieldsToObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    public List<Phone> getAllWithLimit(int limit) {
        String sql = "SELECT * FROM \"homeworks\".\"Phone\" ORDER BY time ASC LIMIT ?";
        List<Phone> result = new ArrayList<>();
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


    public Optional<Phone> findById(String id) {
        String sql = "SELECT * FROM \"homeworks\".\"Phone\" WHERE id = ?";
        Optional<Phone> phone = Optional.empty();

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

    public Optional<Phone> findByTitle(String id) {
        return Optional.empty();
    }
}

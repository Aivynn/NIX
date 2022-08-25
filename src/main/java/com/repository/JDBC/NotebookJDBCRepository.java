package com.repository.JDBC;

import com.config.JDBCConfig;
import com.model.Manufacturer;
import com.model.Notebook;
import com.model.Phone;
import com.repository.CrudRepository;
import com.util.Singleton;
import lombok.SneakyThrows;
import org.apache.commons.lang3.EnumUtils;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Singleton
public class NotebookJDBCRepository implements CrudRepository<Notebook> {
    private static final Connection CONNECTION = JDBCConfig.getConnection();

    @Override
    public void save(Notebook notebook) {

        String sql = "INSERT INTO \"homeworks\".\"Notebook\" (id, title, count, price, model, manufacturer, time) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            setObjectFields(statement, notebook);
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<Notebook> getAllWithLimit(int limit) {
        String sql = "SELECT * FROM \"homeworks\".\"Notebook\" ORDER BY time ASC LIMIT ?";
        List<Notebook> result = new ArrayList<>();
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            statement.setInt(1, limit);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(setFieldsToObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    @SneakyThrows
    private void setObjectFields(final PreparedStatement statement, final Notebook phone) {
        statement.setString(1, phone.getId());
        statement.setString(2, phone.getTitle());
        statement.setInt(3, phone.getCount());
        statement.setDouble(4, phone.getPrice());
        statement.setString(5, phone.getModel());
        statement.setString(6, phone.getManufacturer().name());
        statement.setTimestamp(7, Timestamp.valueOf(phone.getDate()));
    }

    @SneakyThrows
    private Notebook setFieldsToObject(final ResultSet resultSet) throws SQLException {
        final String model = resultSet.getString("model");
        final Manufacturer manufacturer = EnumUtils.getEnum(Manufacturer.class, resultSet.getString("manufacturer"),
                Manufacturer.NONE);
        final Notebook notebook = new Notebook(resultSet.getString("title"),
                resultSet.getInt("count"),
                resultSet.getDouble("price"),
                model,
                manufacturer);
        notebook.setDate(resultSet.getTimestamp("time").toLocalDateTime());
        notebook.setId(resultSet.getString("id"));
        return notebook;
    }

    @Override

    public void saveAll(List<Notebook> notebooks) {
        String sql = "INSERT INTO \"homeworks\".\"Notebook\" (id, model, manufacturer) VALUES (?, ?, ?)";

        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            CONNECTION.setAutoCommit(false);
            for (Notebook phone : notebooks) {
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
    public boolean update(Notebook product) {
        return false;
    }

    @Override
    public boolean delete(String id) {
        String sql = "DELETE FROM \"homeworks\".\"Notebook\" WHERE id = ?";
        try (final PreparedStatement statement = CONNECTION.prepareStatement(sql)) {
            statement.setString(1, id);
            return statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Notebook> getAll() {
        final List<Notebook> result = new ArrayList<>();
        try (final Statement statement = CONNECTION.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM \"homeworks\".\"Notebook\"");
            while (resultSet.next()) {
                result.add(setFieldsToObject(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public Optional<Notebook> findById(String id) {
        String sql = "SELECT * FROM \"homeworks\".\"Notebook\" WHERE id = ?";
        Optional<Notebook> phone = Optional.empty();

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

    @Override
    public Optional<Notebook> findByTitle(String id) {
        return Optional.empty();
    }
}

package org.prgrms.kdt;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.nio.ByteBuffer;
import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcCustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final String SELECT_BY_NAME_SQL = "select * from customers WHERE name = ?";
    private final String SELECT_ALL_SQL = "select * from customers";
    private final String INSERT_SQL = "INSERT INTO customers(customer_id, name, email) VALUES (UUID_TO_BIN(?), ?, ?)";
    private final String DELETE_ALL_SQL = "DELETE FROM customers";
    private final String UPDATE_BY_SQL = "UPDATE customers SET name = ? WHERE customer_id = UUID_TO_BIN(?)";

    public List<String> findNames(String name) {

        List<String> names = new ArrayList<>();

        try (
                //AutoCloser를 지원하기 때문에 이렇게 작성해도 된다.
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "Stop@s9194761");
                var statement = connection.prepareStatement(SELECT_BY_NAME_SQL);
        ) {
            statement.setString(1, name);
            logger.info("statement -> {}", statement);
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var customerName = resultSet.getString("name");
                    var customer_Id = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                    var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                    names.add(customerName);
                }
            }

        } catch (SQLException throwables) {
            logger.error("Got error while closing connection", throwables);
        }
        return names;
    }


    public List<String> findAllNames() {
        List<String> names = new ArrayList<>();
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "Stop@s9194761");
                var statement = connection.prepareStatement(SELECT_ALL_SQL);
                var resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                var customerName = resultSet.getString("name");
                var customer_Id = toUUID(resultSet.getBytes("customer_id"));
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                names.add(customerName);
            }
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection", throwables);
        }
        return names;
    }

    public List<UUID> findAllIds() {
        List<UUID> uuids = new ArrayList<>();
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "Stop@s9194761");
                var statement = connection.prepareStatement(SELECT_ALL_SQL);
                var resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                var customerName = resultSet.getString("name");
                var customer_Id = toUUID(resultSet.getBytes("customer_id"));
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                uuids.add(customer_Id);
            }
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection", throwables);
        }
        return uuids;
    }

    public int insertCustomer(UUID customerId, String name, String email) {
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "Stop@s9194761");
                var statement = connection.prepareStatement(INSERT_SQL);
        ) {
            statement.setBytes(1, customerId.toString().getBytes());
            statement.setString(2, name);
            statement.setString(3, email);
            return statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection", throwables);
        }
        return 0;
    }

    public int deleteAllCustomers() {
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "Stop@s9194761");
                var statement = connection.prepareStatement(DELETE_ALL_SQL);
        ) {
            return statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection", throwables);
        }
        return 0;
    }

    public int updateCustomerName(UUID customerId, String name) {
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "Stop@s9194761");
                var statement = connection.prepareStatement(UPDATE_BY_SQL);
        ) {
            statement.setString(1, name);
            statement.setBytes(2, customerId.toString().getBytes());
            return statement.executeUpdate();
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection", throwables);
        }
        return 0;
    }


    static UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    public static void main(String[] args) throws SQLException {
        var customerRepository = new JdbcCustomerRepository();

        var count = customerRepository.deleteAllCustomers();
        logger.info("deleted count -> {}", count);

        var customerId = UUID.randomUUID();
        logger.info("created customerId -> {}", customerId);
        customerRepository.insertCustomer(customerId, "new-user", "new-user@gmail.com");
        customerRepository.findAllIds().forEach(v -> logger.info("Found customerId : {}", v));
    }
}

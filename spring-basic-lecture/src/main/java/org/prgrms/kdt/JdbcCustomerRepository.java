package org.prgrms.kdt;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.sql.*;
import java.util.UUID;

public class JdbcCustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);

    public static void main(String[] args) throws SQLException{
        /*//닫아야 하기에 위로 빼줌.
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            //Test이고 원래 code상에 패스워드 절대 들어가면 안됨
            connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root","Stop@s9194761");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from customers");
            while(resultSet.next()){
             var name = resultSet.getString("name");
             var customer_Id = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
             logger.info("customer id -> {}, name -> {}",customer_Id,name);
            }
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection", throwables);
        } finally {
            try{
                // 닫아주는 부분이 중요하다.
                if(connection != null) connection.close();
                if(statement != null) statement.close();
                if(resultSet != null) resultSet.close();
            } catch(SQLException exception){
                logger.error("Got error while closing connection", exception);
            }
        } */



        try (
            //AutoCloser를 지원하기 때문에 이렇게 작성해도 된다.
            var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root","Stop@s9194761");
            var statement = connection.createStatement();
            var resultSet = statement.executeQuery("select * from customers");

        ) {
            while (resultSet.next()) {
                var name = resultSet.getString("name");
                var customer_Id = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                logger.info("customer id -> {}, name -> {}", customer_Id, name);
            }
        } catch (SQLException throwables) {
            logger.error("Got error while closing connection", throwables);
        }

    }
}

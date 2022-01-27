package com.geekbrains.cloud.jan.db;

// import com.geekbrains.cloud.jan.client.User;
import com.geekbrains.cloud.jan.common.Configs;
import com.geekbrains.cloud.jan.common.Constant;
import com.geekbrains.cloud.jan.server.Handler;

import java.sql.*;

public class DatabaseHandler extends Configs {
    Connection dbConnection;


    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    public void signUpUser(Handler handler) {   //User user
        String insert = "INSERT INTO " + Constant.USER_TABLE + " (" + Constant.USERS_LOGIN + "," + Constant.USERS_PASSWORD + "," +
                Constant.USERS_NAME + "," + Constant.USERS_SURNAME + "," + Constant.USERS_GENDER + "," + Constant.USERS_COUNTRY + "," +
                Constant.USERS_CITY + ")" + "VALUES(?,?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, handler.getLogin());    //user
            preparedStatement.setString(2, handler.getPassword());
            preparedStatement.setString(3, handler.getName());
            preparedStatement.setString(4, handler.getSurName());
            preparedStatement.setString(5, handler.getGender());
            preparedStatement.setString(6, handler.getCountry());
            preparedStatement.setString(7, handler.getCity());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getUser(Handler handler){ // User user
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + Constant.USER_TABLE + " WHERE " + Constant.USERS_LOGIN + "=? AND " + Constant.USERS_PASSWORD + "=?";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
            preparedStatement.setString(1, handler.getLogin()); // user
            preparedStatement.setString(2, handler.getPassword());

            resultSet = preparedStatement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }
}

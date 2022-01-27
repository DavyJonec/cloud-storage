package com.geekbrains.cloud.jan.controllers;

// import com.geekbrains.cloud.jan.client.User;

import com.geekbrains.cloud.jan.db.DatabaseHandler;
import com.geekbrains.cloud.jan.server.Handler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController {@FXML
private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button authButton;

    @FXML
    private Text notAllFields;

    @FXML
    private RadioButton buttonFemale;

    @FXML
    private RadioButton buttonMale;

    @FXML
    private ToggleGroup gender;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private TextField signUpCity;

    @FXML
    private TextField signUpCountry;

    @FXML
    private TextField signUpName;

    @FXML
    private TextField signUpSurname;

    @FXML
    void initialize() {
        authButton.setOnAction(actionEvent -> {
            signUpNewUser();
        });
    }

    private void signUpNewUser() {
        Controller controller = new Controller();
        DatabaseHandler dbHandler = new DatabaseHandler();

        String login = login_field.getText();
        String password = password_field.getText();
        String name = signUpName.getText();
        String surName = signUpSurname.getText();
        String gender = "";
        String country = signUpCountry.getText();
        String city = signUpCity.getText();

        if (buttonMale.isSelected())
            gender = "Male";
        else
            gender = "Female";

        if (login.isEmpty() || password.isEmpty() || name.isEmpty() || surName.isEmpty() || country.isEmpty() || city.isEmpty()) {
            notAllFields.setVisible(true);
        } else {
            Handler handler = new Handler(login, password, name, surName, gender, country, city);  //User user = new User
            dbHandler.signUpUser(handler);  //user
            controller.openNewScene("/com/geekbrains/cloud/jan/fileController.fxml", authButton);
        }

    }

}
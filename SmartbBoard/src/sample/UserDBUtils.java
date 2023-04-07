package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;


public class UserDBUtils {

    public static void signUpUser(ActionEvent event, String username, String password, String firstname, String lastname, String image) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;
        try {
            connection = DatabaseConnection.getConnection();
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users where username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("User already exists!You cannot use this username!");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO users (username,password,firstname,lastname,image) VALUES(?, ?, ?, ?,?) ");
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.setString(3, firstname);
                psInsert.setString(4, lastname);
                psInsert.setString(5, image);
                psInsert.executeUpdate();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {

            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (psCheckUserExists != null) {
                try {
                    psCheckUserExists.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void updateUser(ActionEvent event, String username, String firstname, String lastname, String image) {
        Connection connection = null;
        PreparedStatement psInsert = null;

        try {
            connection = DatabaseConnection.getConnection();
            psInsert = connection.prepareStatement("UPDATE users SET firstname = ?, lastname = ?, image = ? WHERE username = ?");
            psInsert.setString(1, firstname);
            psInsert.setString(2, lastname);
            psInsert.setString(3, image);
            psInsert.setString(4, username);
            psInsert.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {

            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void logInUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * From users where username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Incorrect Username or Password!");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    if (retrievedPassword.equals(password)) {

                        String firstname = resultSet.getString("firstname");

                        String lastname = resultSet.getString("lastname");

                        String image = resultSet.getString("image");

                        User user = new User(username, password, firstname, lastname, image);
                        LoginUserHolder.setCurrent(user);
                        //open SmartBoard
                        Parent home_page_parent = FXMLLoader.load(UserDBUtils.class.getResource("SmartBoard.fxml"));
                        Scene home_page_scene = new Scene(home_page_parent);
                        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        app_stage.setTitle("SmartBoard");
                        app_stage.setScene(home_page_scene);
                        app_stage.show();


                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Incorrect Username or Password!");
                        alert.show();

                    }
                }
            }


        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }


    }

}


package sample;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    @FXML
    private Button closeBut;


    @FXML
    private Button signInBut;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;




// for login

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        signInBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                UserDBUtils.logInUser(event,usernameField.getText(),passwordField.getText());
            }
        });


    }

    @FXML  //Close event
    void close(ActionEvent event) {
        Stage stage = (Stage)closeBut.getScene().getWindow();
        stage.close();
        Platform.exit();
    }



    @FXML //Create User for register
    void createUser(ActionEvent event) throws IOException{
        Parent register_page_parent = FXMLLoader.load(getClass().getResource("register.fxml"));
        Scene register_page_scene = new Scene(register_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setTitle("Create a user");
        app_stage.setScene(register_page_scene);
        app_stage.show();
    }


    @FXML
    void signIn(ActionEvent event) {

    }



}


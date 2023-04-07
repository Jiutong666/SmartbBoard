package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {


    @FXML
    private TextField usernameField1;

    @FXML
    private PasswordField passwordField1;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;


    @FXML
    private Button registerBut;


    @FXML
    private ImageView uploadImage;

    private String filePath;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        registerBut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                UserDBUtils.signUpUser(event,usernameField1.getText(),passwordField1.getText(),firstNameField.getText(),lastNameField.getText(), filePath);


            }
        });

    }


    @FXML
    void close(ActionEvent event) throws IOException {
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setTitle("Log in to SmartBoard");
        app_stage.setScene(home_page_scene);
        app_stage.show();
    }


    @FXML
    void register(ActionEvent event) {
    }

    @FXML
    void createPicture(ActionEvent event) {

        Stage stage =new Stage();
        FileChooser fileChooser =new FileChooser();

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image type","*.jpg","*.png", "*.jpeg"));
        File file = fileChooser.showOpenDialog(stage);
        filePath = file.getAbsolutePath();

        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(file !=null){
            Image myImage = new Image(inputStream);
            uploadImage.setImage(myImage);
        }else{
            System.out.println("choose image");
        }

    }




}



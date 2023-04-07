package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BoardUserController implements Initializable{


    @FXML
    private Label userLabel;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private ImageView uploadImage;

    private String filePath;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User current = LoginUserHolder.getCurrent();

        filePath = current.getImage();
        firstNameField.setText(current.getFirstname());
        lastNameField.setText(current.getLastname());
        userLabel.setText(current.getUsername());

        if (filePath != null) {
            try {
                uploadImage.setImage(new Image(new FileInputStream(filePath)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }


    @FXML
    void createPicture(ActionEvent event) {

        Stage stage =new Stage();
        FileChooser fileChooser =new FileChooser();
        //Get picture
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("image Type","*.jpg","*.png", "*.jpeg"));
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

        }

        //Display the image in imageView

    }


    @FXML
    void updateUser(ActionEvent event) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        User current = LoginUserHolder.getCurrent();
        current.setFirstname(firstName);
        current.setLastname(lastName);
        current.setImage(filePath);

        LoginUserHolder.setCurrent(current);
        UserDBUtils.updateUser(event, current.getUsername(), firstName, lastName, filePath);
        close(event);

    }


    @FXML
    void close(ActionEvent event){
        Parent home_page_parent = null;
        try {
            home_page_parent = FXMLLoader.load(UserDBUtils.class.getResource("SmartBoard.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setTitle("SmartBoard");
        app_stage.setScene(home_page_scene);
        app_stage.show();
    }


}

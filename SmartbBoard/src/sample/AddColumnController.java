package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AddColumnController  {

    @FXML
    private TextField nameField;

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


    @FXML
    void addColumn(ActionEvent event) {
        String text = nameField.getText();
        if (text == null || text.trim().length() <= 0) {
            return;
        }

        ColumnDBUtils.addColumn( text, SmartBoardController.currentName);
        close(event);
    }

}

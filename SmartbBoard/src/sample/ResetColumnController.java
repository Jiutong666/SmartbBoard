package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class ResetColumnController implements Initializable {


    public static List<String> columns;

    public static String current;

    public static String task;

    @FXML
    private ChoiceBox resetbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (columns != null && !columns.isEmpty()) {
            for (String column : columns) {
                resetbox.getItems().add(column);
            }
        }

    }


    @FXML
    public void resetColumn(ActionEvent event){
        String choiced = null;
        for (int i = 0; i < columns.size(); i++) {
            if ( resetbox.getSelectionModel().isSelected(i)) {
                choiced = columns.get(i);
                break;
            }
        }
        if (choiced != null && !choiced.trim().equals("") && !choiced.equals(current)) {
            TaskDBUtils.updateTask(task, choiced);
        }



        Parent home_page_parent = null;
        try {
            home_page_parent = FXMLLoader.load(getClass().getResource("SmartBoard.fxml"));
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

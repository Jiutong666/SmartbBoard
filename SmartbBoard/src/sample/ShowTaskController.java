package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ShowTaskController implements Initializable {

    public static Task task;

    public static List<Item> items;

    @FXML
    private Label track;

    @FXML
    private CheckBox completed;

    @FXML
    private DatePicker picker;

    @FXML
    private Label dueDate;

    @FXML
    private Label showDueDate;

    @FXML
    private Label closeDue;

    @FXML
    private Label showCheck;

    @FXML
    private Label checklist;

    @FXML
    private Label closeCheck;

    @FXML
    private ProgressBar psbar;

    @FXML
    private Label checkitem;


    @FXML
    private Label psstate;

    @FXML
    private VBox itembox;


    @FXML
    private TextField taskField;

    @FXML
    private TextArea descField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (task != null) {
            showDueDate();
            picker.getEditor().setText(task.getDuedate());
            taskField.setText(task.getTaskname());
            descField.setText(task.getDescription());
            completed.setSelected(task.getState().equals("1"));
        }

        if (items != null && items.size() > 0) {
            List<Item> item1 = items.stream().filter(x -> x.getTaskname().equals(task.getTaskname())).collect(Collectors.toList());
            showCheck();
            for (Item item : item1) {
                showItem(item);
            }
        }
    }

    @FXML
    void showDueDate() {
        showDueDate.setVisible(false);
        dueDate.setVisible(true);
        picker.setVisible(true);
        closeDue.setVisible(true);
        track.setVisible(true);
        completed.setVisible(true);
    }

    @FXML
    void closeDue() {
        showDueDate.setVisible(true);
        dueDate.setVisible(false);
        picker.setVisible(false);
        closeDue.setVisible(false);
        track.setVisible(false);
        completed.setVisible(false);
    }


    @FXML
    void showCheck() {
        checklist.setVisible(true);
        closeCheck.setVisible(true);
        showCheck.setVisible(false);
        psbar.setVisible(true);
        checkitem.setVisible(true);
        psstate.setVisible(true);
        itembox.setVisible(true);
    }

    @FXML
    void closeCheck() {
        checklist.setVisible(false);
        closeCheck.setVisible(false);
        showCheck.setVisible(true);
        psbar.setVisible(false);
        checkitem.setVisible(false);
        psstate.setVisible(false);
        itembox.setVisible(false);
    }


    private void showItem(Item input) {
        ObservableList<Node> children = itembox.getChildren();
        AnchorPane pane = new AnchorPane();
        pane.setPrefHeight(200);
        pane.setPrefWidth(200);
        CheckBox box = new CheckBox(input.getItemname());
        box.setSelected(input.getState().equals("1"));
        box.setLayoutX(19);
        box.setLayoutY(31);
        box.setMnemonicParsing(false);
        pane.getChildren().add(box);

        Label edit = new Label("Edit");
        edit.setLayoutX(178);
        edit.setLayoutY(31);
        edit.setUnderline(true);
        edit.setTextFill(Paint.valueOf("#2381c9"));
        edit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TextInputDialog alert = new TextInputDialog();
                alert.setTitle("input item");
                Optional<String> optional = alert.showAndWait();
                if (optional.isPresent()) {
                    TaskDBUtils.updateItem2(optional.get(),box.getText());
                    box.setText(optional.get());
                }
            }
        });
        pane.getChildren().add(edit);

        Label delete = new Label("Delete");
        delete.setLayoutX(238);
        delete.setLayoutY(31);
        delete.setUnderline(true);
        delete.setTextFill(Paint.valueOf("#2381c9"));
        delete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                TaskDBUtils.delItem(box.getText());
                children.remove(pane);

            }
        });
        pane.getChildren().add(delete);
        children.add(pane);
    }




    @FXML
    void updateTask(ActionEvent event) {

        String name = taskField.getText();
        String text = descField.getText();
        String time = picker.getEditor().getText();
        String state = completed.isSelected()? "1" : "0";
        if (time == null || time.trim().equals("")) {
            return;
        }
        TaskDBUtils.updateTask(name, state,  text, time);


        ObservableList<Node> children = itembox.getChildren();
        if (children.size() > 0) {
            for (Node child : children) {
                AnchorPane pane = (AnchorPane) child;
                for (Node paneChild : pane.getChildren()) {
                    if (paneChild instanceof CheckBox) {
                        CheckBox checkBox = (CheckBox) paneChild;
                        TaskDBUtils.updateItem(checkBox.getText(), checkBox.isSelected()?"1":"0");
                    }
                }
            }
        }

        close(event);

    }




    @FXML
    void close(ActionEvent event) {
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

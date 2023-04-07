package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;

import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.URL;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;



public class SmartBoardController implements Initializable {

    @FXML
    private TabPane tabPan;

    public static String currentName;

    @FXML
    private ScrollPane scpanel;

    @FXML
    private HBox hbox;

    private String filePath;
    @FXML
    private Label nameField;

    @FXML
    private Label quote;

    @FXML
    private ImageView uploadImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initProject();
        getInformation();
        setQuote();
    }


    //Choose three quotes at random
    public void setQuote(){
        ArrayList<String> quotes= new ArrayList();
        quotes.add("“It’s only after you’ve stepped outside your comfort zone that you begin to change, grow, and transform.”― Roy T. Bennett");
        quotes.add("Success is not how high you have climbed, but how you make a positive difference to the world.― Roy T. Bennett");
        quotes.add("Nothing in the world is ever completely wrong. Even a stopped clock is right twice a day.”― Paulo Coelho");

        int index = (int) (Math.random()* quotes.size());
        String q = quotes.get(index);
        quote.setText(q);

    }


    private void initProject() {
        //interval
        hbox.setSpacing(20);

        List<Project> list = ProjectDBUtils.selectProject();
        if (!list.isEmpty()) {
            boolean isdefault = false;
            for (Project project : list) {
                String s = project.getName();
                if (project.getDefaults().equals("1")) {
                    s = s + "(*)";
                    isdefault = true;
                }
                Tab tab = new Tab(s);
                tabPan.getTabs().add(tab);
            }

            ObservableList<Tab> tabs = tabPan.getTabs();

            if (isdefault) {
                for (Tab tab : tabs) {
                    if (tab.getText().contains("(*)")) {
                        currentName = tab.getText().replace("(*)", "");
                        tabPan.getSelectionModel().select(tab);
                    }
                }
            } else {
                tabPan.getSelectionModel().select(0);
                currentName = tabPan.getTabs().get(0).getText();
            }


            tabPan.setVisible(true);

            tabPan.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    String selected = getSelected();
                    initColumn(selected);
                }
            });
        }
        List<String> strings = ColumnDBUtils.selectColumn(currentName);
        if (!strings.isEmpty()) {
            scpanel.setVisible(true);
            initColumn(currentName);
        }
    }

    private void initColumn(String projectName) {
        if (projectName.contains("(*)")) {
            projectName = projectName.replace("(*)", "");
        }

        List<String> columns = ColumnDBUtils.selectColumn(projectName);
        ObservableList children = hbox.getChildren();
        children.clear();

        if (!columns.isEmpty()) {

            for (String column : columns) {
                // Create vbox
                VBox vBox = new VBox();
                vBox.setPrefWidth(249);
                vBox.setPrefHeight(515);
                vBox.getChildren().add(showColumn(column));

                // The query task
                List<Task> tasks = TaskDBUtils.selectTasks(column);
                if (!tasks.isEmpty()) {
                    for (Task task : tasks) {
                        ObservableList boxChildren = vBox.getChildren();
                        showTask(boxChildren, task, column, columns);
                    }
                }

                //interval
                vBox.setSpacing(20);
                children.add(vBox);
            }

        }
    }


    private AnchorPane showTask(ObservableList<Node> boxChildren, Task task, String column_name, List<String> columns) {

        //  <AnchorPane prefHeight="143.0" prefWidth="249.0">
        //                                    <children>
        //                                        <Label layoutX="156.0" layoutY="21.0" text="Edit" textFill="#2279d7"
        //                                               underline="true"/>
        //                                        <Label layoutX="203.0" layoutY="21.0" text="Delete" textFill="#4481d0"
        //                                               underline="true"/>
        //                                        <Label layoutX="26.0" layoutY="55.0" text="this is a single task"/>
        //                                        <Label layoutX="26.0" layoutY="98.0" text="2021-07-12"/>
        //                                        <Label layoutX="167.0" layoutY="98.0" text="items: 0/2"/>
        //                                    </children>
        //                                </AnchorPane>
        AnchorPane column = new AnchorPane();

        //Set the font
        column.setBackground(new Background(new BackgroundFill(Paint.valueOf("#ffffff"), CornerRadii.EMPTY, Insets.EMPTY)));
        //Design framework
        column.setBorder(new Border(new BorderStroke(Paint.valueOf("#4481d0"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));


        // Create label for edit task
        Label edit = new Label("Edit");
        edit.setLayoutX(156);
        edit.setTextFill(Paint.valueOf("#4481d0"));
        edit.setLayoutY(21);
        edit.setUnderline(true);
        final List<Item> items = TaskDBUtils.selectItems();

        edit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ShowTaskController.task = task;
                ShowTaskController.items = items;
                Parent show_page_parent = null;
                try {
                    show_page_parent = FXMLLoader.load(getClass().getResource("ShowTask.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene show_page_scene = new Scene(show_page_parent);
                Stage show_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                show_stage.setTitle("Add new task");
                show_stage.setScene(show_page_scene);
                show_stage.show();
            }
        });
        column.getChildren().add(edit);


        // Create label delete for delete task
        Label delete = new Label("Delete");
        delete.setLayoutX(203);
        delete.setTextFill(Paint.valueOf("#4481d0"));
        delete.setLayoutY(21);
        delete.setUnderline(true);
        delete.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                boxChildren.remove(column);
                // 删除任务
                TaskDBUtils.delTask(task.getTaskname());
            }
        });
        column.getChildren().add(delete);



        // create label reset for move task to another column
        Label reset = new Label("Reset column");
        reset.setLayoutX(203);
        reset.setTextFill(Paint.valueOf("#4481d0"));
        reset.setLayoutY(41);
        reset.setUnderline(true);
        reset.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ResetColumnController.task = task.getTaskname();
                ResetColumnController.current = column_name;
                ResetColumnController.columns = columns;
                Parent reset_page_parent = null;
                try {
                    reset_page_parent = FXMLLoader.load(getClass().getResource("ResetColumn.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene reset_page_scene = new Scene(reset_page_parent);
                Stage reset_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                reset_stage.setTitle("Reset Column");
                // app_stage.hide(); //optional
                reset_stage.setScene(reset_page_scene);
                reset_stage.show();
            }
        });
        column.getChildren().add(reset);



        //Show Task name
        Label name = new Label(task.getTaskname());
        name.setLayoutX(26);
        name.setLayoutY(55);
        column.getChildren().add(name);



        //Show Task date
        Label time = new Label(task.getDuedate());
        time.setLayoutX(26);
        time.setLayoutY(98);
        column.getChildren().add(time);
        Label item = new Label();



        boolean ssc = false;
        List<Item> items1 = items.stream().filter(x -> x.getTaskname().equals(task.getTaskname())).collect(Collectors.toList());

        if (items1.size() > 0) {
            long count = items1.stream().filter(x -> x.getState().equals("1")).count();
            item.setText(count + "/" + items1.size());
            ssc = (int) count == items1.size();
        }
        //For date
        LocalDate localDate = LocalDate.now();
        String now = localDate.format(DateTimeFormatter.ISO_DATE);
        String[] split = now.split("-");
        int a1 = Integer.parseInt(split[0]) * 10000 + Integer.parseInt(split[1]) * 100 + Integer.parseInt(split[2]);
        String[] split1 = task.getDuedate().split("/");
        int a2 = Integer.parseInt(split1[2]) * 10000 + Integer.parseInt(split1[1]) * 100 + Integer.parseInt(split1[0]);
        int res = a2 - a1;
        String state = task.getState();
        boolean wc = state.equals("1") || ssc;

        //For colour

        // Task Done. Turn green
        if (res >= 0 && wc) {
            if (items1.size() > 0) {
                item.setBackground(new Background(new BackgroundFill(Paint.valueOf("#00FF7F"), CornerRadii.EMPTY, Insets.EMPTY)));
            }
            time.setBackground(new Background(new BackgroundFill(Paint.valueOf("#00FF7F"), CornerRadii.EMPTY, Insets.EMPTY)));
        } else if (res < 0 && !wc) {
            // Task timeout incomplete, turn red
            if (items1.size() > 0) {
                item.setBackground(new Background(new BackgroundFill(Paint.valueOf("#FF7F50"), CornerRadii.EMPTY, Insets.EMPTY)));

            }
            time.setBackground(new Background(new BackgroundFill(Paint.valueOf("#FF7F50"), CornerRadii.EMPTY, Insets.EMPTY)));
        } else if (res > 0 && !wc && res == 1 ) {
            // Within 48 hours of expiration date, turn yellow
            if (items1.size() > 0) {
                item.setBackground(new Background(new BackgroundFill(Paint.valueOf("#FFFF00"), CornerRadii.EMPTY, Insets.EMPTY)));
            }
            time.setBackground(new Background(new BackgroundFill(Paint.valueOf("#FFFF00"), CornerRadii.EMPTY, Insets.EMPTY)));
        }
        else if (res ==0 ) {
            // Within 24 hours of expiration date, turn yellow
            if (items1.size() > 0) {
                item.setBackground(new Background(new BackgroundFill(Paint.valueOf("#FFFF00"), CornerRadii.EMPTY, Insets.EMPTY)));
            }
            time.setBackground(new Background(new BackgroundFill(Paint.valueOf("#FFFF00"), CornerRadii.EMPTY, Insets.EMPTY)));
        }

        item.setLayoutX(167);
        item.setLayoutY(98);
        column.getChildren().add(item);

     //   NodeDragUtil.addNodeDragListener(hbox, column);
        boxChildren.add(column);
        return column;
    }




    private AnchorPane showColumn(String columnName) {
        // <AnchorPane prefHeight="65.0" prefWidth="244.0">
        //                                    <children>
        //                                        <ProgressBar layoutX="14.0" layoutY="14.0" prefHeight="37.0" prefWidth="200.0"
        //                                                     progress="0.0"/>
        //                                        <Button layoutX="142.0" layoutY="20.0" mnemonicParsing="false" text="Add task"/>
        //                                        <Label layoutX="30.0" layoutY="24.0" text="java"/>
        //                                    </children>
        //                                </AnchorPane>


        //set column bar and column location
        AnchorPane column = new AnchorPane();
        ProgressBar bar = new ProgressBar();
        bar.setLayoutX(14);
        bar.setLayoutY(14);
        bar.setPrefHeight(37);
        bar.setPrefWidth(200);
        bar.setProgress(0);
        column.getChildren().add(bar);


        //Create SplitMenubutton for add task
        SplitMenuButton button = new SplitMenuButton();
        button.setText("Add task");
        button.setLayoutX(123);
        button.setLayoutY(20);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AddTaskController.taskName = columnName;
                // 点击 add task 触发
                Parent addTask_page_parent = null;
                try {
                    addTask_page_parent = FXMLLoader.load(getClass().getResource("AddTask.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene addTask_page_scene = new Scene(addTask_page_parent);
                Stage addTask_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                addTask_stage.setTitle("Add new task");
                addTask_stage.setScene(addTask_page_scene);
                addTask_stage.show();
            }
        });
        column.getChildren().add(button);

        //Create MenuItem for delete Column
        MenuItem item =new MenuItem("delete Column");
        button.getItems().addAll(item);
        item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AddTaskController.taskName = columnName;
                ColumnDBUtils.delCol(columnName);

                Parent home_page_parent = null;
                try {
                    home_page_parent = FXMLLoader.load(getClass().getResource("SmartBoard.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene home_page_scene = new Scene(home_page_parent);
                Stage app_stage = Main.stage;
                app_stage.setTitle("SmartBoard");
                app_stage.setScene(home_page_scene);
                app_stage.show();
            }
        });


        MenuItem item2 =new MenuItem("Rename Column");
        button.getItems().addAll(item2);


        //Create MenuItem for rename Column
        item2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String text = getSelected();
                currentName = text;
               RenameColumnController.colName = columnName;

                Parent rename_page_parent = null;
                try {
                    rename_page_parent = FXMLLoader.load(getClass().getResource("RenameColumn.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene home_page_scene = new Scene(rename_page_parent);
                Stage rename_stage = Main.stage;
                rename_stage.setTitle("Rename");
                rename_stage.setScene(home_page_scene);
                rename_stage.show();
            }
        });

        //add Column name on board
        Label label = new Label(columnName);
        label.setLayoutX(30);
        label.setLayoutY(24);
        column.getChildren().add(label);
        return column;
    }


    @FXML
    void logOut(ActionEvent event) throws IOException {
        currentName = null;
        Parent login_page_parent = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene login_page_scene = new Scene(login_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setTitle("Log in to SmartBoard");
        app_stage.setScene(login_page_scene);
        app_stage.show();
    }

    @FXML
    void profile(ActionEvent event) {
        Parent profile_page_parent = null;
        try {
            profile_page_parent = FXMLLoader.load(getClass().getResource("BoardUserInfo.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene profile_page_scene = new Scene(profile_page_parent);
        Stage profile_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        profile_stage.setTitle("Edit profile");
        profile_stage.setScene(profile_page_scene);
        profile_stage.show();
    }


    @FXML
    void addProject(ActionEvent event) {
        Parent add_page_parent = null;
        try {
            add_page_parent = FXMLLoader.load(getClass().getResource("AddProject.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene add_page_scene = new Scene(add_page_parent);
        Stage add_stage = Main.stage;
        add_stage.setTitle("Create a new Project");
        add_stage.setScene(add_page_scene);
        add_stage.show();
    }


    @FXML
    void addColumn() {
        String text = getSelected();
        currentName = text;

        Parent addC_page_parent = null;
        try {
            addC_page_parent = FXMLLoader.load(getClass().getResource("AddColumn.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene addC_page_scene = new Scene(addC_page_parent);
        Stage addC_stage = Main.stage;
        addC_stage.setTitle("Add new column to " + text);
        addC_stage.setScene(addC_page_scene);
        addC_stage.show();
    }


    @FXML
    void delete(){
        String text = getSelected();
        text = text.replace("(*)", "");
        ProjectDBUtils.delete(text);
        ColumnDBUtils.delCol2(text);
        tabPan.getTabs().clear();

        List<Project> list = ProjectDBUtils.selectProject();

        boolean isdefault = false;
        for (Project project : list) {
            String s = project.getName();
            if (project.getDefaults().equals("1")) {
                s = s + "(*)";
                isdefault = true;
            }
            Tab tab = new Tab(s);
            tabPan.getTabs().add(tab);
        }
        ObservableList<Tab> tabs = tabPan.getTabs();
        if (isdefault) {
            for (Tab tab : tabs) {
                if (tab.getText().contains("(*)")) {
                    currentName = tab.getText().replace("(*)", "");
                    tabPan.getSelectionModel().select(tab);
                }
            }
        } else {
            if (!tabPan.getTabs().isEmpty()) {
                tabPan.getSelectionModel().select(0);
                currentName = tabPan.getTabs().get(0).getText();
            }
            hbox.getChildren().clear();
        }


        //Refresh
        Parent home_page_parent = null;
        try {
            home_page_parent = FXMLLoader.load(getClass().getResource("SmartBoard.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = Main.stage;
        app_stage.setTitle("SmartBoard" );
        app_stage.setScene(home_page_scene);
        app_stage.show();

    }



    @FXML
    void unsetDefault() {
        String text = getSelected();
        text = text.replace("(*)", "");
        ProjectDBUtils.unsetDefault(text);
        tabPan.getTabs().clear();
        List<Project> list = ProjectDBUtils.selectProject();
        Tab select = null;
        for (Project p : list) {
            String s = p.getName();
            Tab tab = new Tab(s);
            if (s.equals(text)) {
                select = tab;
            }
            tabPan.getTabs().add(tab);
        }
        tabPan.getSelectionModel().select(select);
    }

    @FXML
    void setDefault() {
        String text = getSelected();
        ProjectDBUtils.setDefault(text);
        tabPan.getTabs().clear();
        List<Project> list = ProjectDBUtils.selectProject();
        for (Project p : list) {
            String s = p.getName();
            if (text.equals(s)) {
                s = s + "(*)";
            }
            Tab tab = new Tab(s);
            tabPan.getTabs().add(tab);
        }
        ObservableList<Tab> tabs = tabPan.getTabs();
        for (Tab tab : tabs) {
            if (tab.getText().contains("(*)")) {
                tabPan.getSelectionModel().select(tab);
            }
        }

    }


    private String getSelected() {
        ObservableList<Tab> tabs = tabPan.getTabs();
        int size = tabs.size();
        int selected = -1;
        for (int i = 0; i < size; i++) {
            if (tabPan.getSelectionModel().isSelected(i)) {
                selected = i;
            }
        }
        return tabs.get(selected).getText();
    }



    private void getInformation(){
        User current = LoginUserHolder.getCurrent();

        filePath = current.getImage();
        nameField.setText(current.getFirstname()+" "+current.getLastname());

        if (filePath != null) {
            try {
                uploadImage.setImage(new Image(new FileInputStream(filePath)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }



    @FXML
    void reName(ActionEvent event) {
        String text = getSelected();
        currentName = text;

        Parent reName_page_parent = null;
        try {
            reName_page_parent = FXMLLoader.load(getClass().getResource("RenameProject.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene home_page_scene = new Scene(reName_page_parent);
        Stage reName_stage = Main.stage;
        reName_stage.setTitle("Rename");
        reName_stage.setScene(home_page_scene);
        reName_stage.show();
    }






}

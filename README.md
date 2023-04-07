## Assignment 2

This is an README file SmartbBoard.

I am using the code edited by IntelliJ IDEA,sqlite3,SceneBuilder.


## Table of Contents

- [Background](#background)
- [Install](#install)
- [Edit the view](#Edit the view)
- [Edit the database](#Edit database)
- [Usage](#usage)
- [How to use Program ](#How to use Program )
- [Author](#author)


## Background
Smart Board is a desktop-based Kanban-style application for managing personal work.
It can application to enable teams to manage any sized collaborative projects from start to finish.
  
  
## Install
This project uses javafx,sqlite3,SceneBuilder. Go check them out if you don't have them locally installed.


## Edit the view
There are a lot of fxml file in sample package, you can edit by SceneBuilder.


## Edit the database
I use sqlite3, you can see ass2.db, it contains columns,items,projects,tasks,users table.



## Usage
This is only a sample package,you can click it, and find main class then click it, you can run it now.

      public static Stage stage;
      @Override
      public void start(Stage primaryStage) throws Exception{
          stage = primaryStage;
          Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
          primaryStage.setTitle("Log in to SmartBoard");
          primaryStage.setScene(new Scene(root, 430, 330));
          primaryStage.setResizable(false);
          primaryStage.show();   
    }
       public static void main(String[] args) {
          launch(args);  
    }
 
 
## How to use Program 
You should click "on create a new user" button before you Sign in
In "create a new user" page you can type your username,password,name and picture, by the way ,you can't create same username.
Then you can type correct username and password and Sign in now 
--------------------------------------------------
After you SignIn you can see the empty smartboard,you can create proeject and columns. 
Then you can use columns bar to create some tasks,and change task information.
The other function is you can click profie button to change some information of users.



## Yuncong Ji






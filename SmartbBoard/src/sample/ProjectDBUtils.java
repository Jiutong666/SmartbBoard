package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProjectDBUtils {

    public static void addProject(ActionEvent event, String name, String defaults,String username) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        try {
            connection = DatabaseConnection.getConnection();

            psInsert = connection.prepareStatement("INSERT INTO projects (`name`,`default`,'users') VALUES(?, ?, ?) ");
            psInsert.setString(1, name);
            psInsert.setString(2, defaults);
            psInsert.setString(3, username);
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


    public static void unsetDefault( String name) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        try {
            connection = DatabaseConnection.getConnection();

            psInsert = connection.prepareStatement("UPDATE projects SET `default` = 0 WHERE `name` = ?");
            psInsert.setString(1, name);
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


    public static void setDefault( String name) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        try {
            connection = DatabaseConnection.getConnection();

            psInsert = connection.prepareStatement("UPDATE projects SET `default` = 1 WHERE `name` = ?");
            psInsert.setString(1, name);
            psInsert.executeUpdate();

            psInsert = connection.prepareStatement("UPDATE projects SET `default` = 0 WHERE `name` != ?");
            psInsert.setString(1, name);
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


    public static List<Project> selectProject() {
        List<Project> names = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            //改users
            connection = DatabaseConnection.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * From projects where users = ?");
            preparedStatement.setString(1, LoginUserHolder.getCurrent().getUsername());

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String aDefault = resultSet.getString("default");
                names.add(new Project(name, aDefault));
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
        return names;

    }



    public static void delete(String name) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        try {
            connection = DatabaseConnection.getConnection();

            psInsert = connection.prepareStatement("delete from projects  WHERE `name` = ?");
            psInsert.setString(1, name);
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




    public static void renameProject(String newName,String name) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        try {
            connection = DatabaseConnection.getConnection();

            psInsert = connection.prepareStatement("UPDATE projects SET `name` = ?  WHERE `name` = ?");
            psInsert.setString(1, newName);
            psInsert.setString(2, name);
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




}


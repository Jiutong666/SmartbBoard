package sample;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskDBUtils {


    public static void updateTask( String name, String state, String desc, String duedate) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        try {
            connection = DatabaseConnection.getConnection();

            psInsert = connection.prepareStatement("update  tasks set `description` = ?,  `state` = ?, `duedate` = ? where `taskname` = ?");
            psInsert.setString(1, desc);
            psInsert.setString(2, state);
            psInsert.setString(3, duedate);
            psInsert.setString(4, name);
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

    public static void updateTask(String taskname, String column) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        try {
            connection = DatabaseConnection.getConnection();

            psInsert = connection.prepareStatement("update tasks set `columns` = ? where taskname = ?");
            psInsert.setString(1, column);
            psInsert.setString(2, taskname);
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

    public static void addTask( String name, String date, int bar, String desc, String col, String state) {
        Connection connection = null;
        PreparedStatement psInsert = null;

        try {
            connection = DatabaseConnection.getConnection();

            psInsert = connection.prepareStatement("INSERT INTO tasks (`taskname`, `duedate`, `progressbar`, `columns`, `description`, `state`) VALUES(?, ?, ?, ?, ?, ?) ");
            psInsert.setString(1, name);
            psInsert.setString(2, date);
            psInsert.setString(3, bar + "");
            psInsert.setString(4, col);
            psInsert.setString(5, desc);
            psInsert.setString(6, state);
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


    public static void addItem( String name, String taskname, String state) {
        Connection connection = null;
        PreparedStatement psInsert = null;

        try {
            connection = DatabaseConnection.getConnection();

            psInsert = connection.prepareStatement("INSERT INTO items (`item_name`, `task_name`, `state`) VALUES(?, ?, ?) ");
            psInsert.setString(1, name);
            psInsert.setString(2, taskname);
            psInsert.setString(3, state);
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


    public static void updateItem( String name, String state) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        try {
            connection = DatabaseConnection.getConnection();

            psInsert = connection.prepareStatement("UPDATE items set `state` = ? where `item_name` = ? ");
            psInsert.setString(1, state);
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

    public static void updateItem2( String name, String name2) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        try {
            connection = DatabaseConnection.getConnection();

            psInsert = connection.prepareStatement("UPDATE items set `item_name` = ? where `item_name` = ? ");
            psInsert.setString(1, name);
            psInsert.setString(2, name2);
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


    public static void delItem( String name) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        try {
            connection = DatabaseConnection.getConnection();

            psInsert = connection.prepareStatement("delete from items where `item_name` = ?");
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



    public static void delTask( String name) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        try {
            connection = DatabaseConnection.getConnection();

            psInsert = connection.prepareStatement("delete from tasks where `taskname` = ?");
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



    public static List<Task> selectTasks(String columns) {
        List<Task> tasks = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * From tasks where columns = ?");
            preparedStatement.setString(1, columns);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("taskname");
                String duedate = resultSet.getString("duedate");
                String progressbar = resultSet.getString("progressbar");
                String description = resultSet.getString("description");
                String state = resultSet.getString("state");
                tasks.add(new Task(columns, name, duedate, progressbar, description, state));
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
        return tasks;

    }


    public static List<Item> selectItems() {
        List<Item> tasks = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * From items");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("item_name");
                String taskname = resultSet.getString("task_name");
                String state = resultSet.getString("state");
                tasks.add(new Item(name, state, taskname));
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
        return tasks;

    }


    public static void updateTask2(String taskname, String column) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        try {
            connection = DatabaseConnection.getConnection();

            psInsert = connection.prepareStatement("update tasks set `columns` = ? where columns = ?");
            psInsert.setString(1, column);
            psInsert.setString(2, taskname);
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


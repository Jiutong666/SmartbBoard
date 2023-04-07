package sample;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ColumnDBUtils {

    public static void addColumn( String name, String project) {
        Connection connection = null;
        PreparedStatement psInsert = null;

        try {
            connection = DatabaseConnection.getConnection();

            psInsert = connection.prepareStatement("INSERT INTO columns (`column_name`, `projects`) VALUES(?, ?) ");
            psInsert.setString(1, name);
            psInsert.setString(2, project);
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



    public static List<String> selectColumn(String project) {
        List<String> names = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * From columns where projects = ?");
            preparedStatement.setString(1, project);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("column_name");
                names.add(name);
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



    public static void renameCol(String newName,String name) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        try {
            connection = DatabaseConnection.getConnection();
            psInsert = connection.prepareStatement("UPDATE columns SET `column_name` = ?  WHERE `column_name` = ?");
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


    public static void delCol( String name) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        try {
            connection = DatabaseConnection.getConnection();

            psInsert = connection.prepareStatement("delete from columns where `column_name` = ?");
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




    public static void delCol2( String name) {
        Connection connection = null;
        PreparedStatement psInsert = null;

        try {
            connection = DatabaseConnection.getConnection();

            psInsert = connection.prepareStatement("delete from columns where `projects` = ?");
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



    public static void updateCol(String newName,String name) {
        Connection connection = null;
        PreparedStatement psInsert = null;

        try {
            connection = DatabaseConnection.getConnection();
            psInsert = connection.prepareStatement("UPDATE columns SET `projects` = ?  WHERE `projects` = ?");
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


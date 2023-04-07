package Test;

import org.junit.Test;
import sample.BoardUserController;
import sample.DatabaseConnection;
import sample.SmartBoardController;

import java.sql.SQLException;

public class SmartBoardTest {
    @Test
    public void SmartBoardControllerTest(){
        SmartBoardController smartBoardController = new SmartBoardController();
        smartBoardController.setQuote();

    }

    @Test
    public void DatabaseConnectionTest() throws SQLException {
        DatabaseConnection databaseConnection= new DatabaseConnection();
        databaseConnection.getConnection();


    }
    


}

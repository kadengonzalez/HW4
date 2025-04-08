package databasePart1;
//Original Code By Professor Lynn Robert Carter.
import javafx.application.Application;
import javafx.stage.Stage;
import java.sql.SQLException;

import databasePart1.DatabaseHelper;

/**
 * 
 */
public class StartCSE360 extends Application {

	private static final DatabaseHelper databaseHelper = new DatabaseHelper();
	
	/**
	 * Start of the program.
	 * @param args
	 */
	public static void main( String[] args )
	{
		 launch(args);
	}
	
	@Override
	/**
	 * This launches the GUI for the program. 
	 * Directs user to FirstPage or SetupLoginSelectionPage. 
	 * @param primaryStage
	 */
    public void start(Stage primaryStage) {
        try {
            databaseHelper.connectToDatabase(); // Connect to the database
            if (databaseHelper.isDatabaseEmpty()) {
            	
            	new FirstPage(databaseHelper).show(primaryStage);
            } else {
            	new SetupLoginSelectionPage(databaseHelper).show(primaryStage);
                
            }
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        }
    }
	

}

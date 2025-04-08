package databasePart1; 
//Original Code By Professor Lynn Robert Carter. Original has been Editied by Bracken Peterson Jan 2025
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import databasePart1.*;

/**
 * The SetupLoginSelectionPage class allows users to choose between setting up a new account
 * or logging into an existing account. It provides two buttons for navigation to the respective pages.
 */
public class SetupLoginSelectionPage {
	
    private final DatabaseHelper databaseHelper;

    public SetupLoginSelectionPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    /**
     * This creates the functionality and UI required for allowing the user to 
     * login to an account or set up their account. 
     * 
     * @param primaryStage This allows the new class to be displayed without creating a new Stage. 
     */
    public void show(Stage primaryStage) {
        
    	// Buttons to select Login / Setup options that redirect to respective pages
        Button setupButton = new Button("SetUp");
        Button loginButton = new Button("Login");
        setupButton.setId("fancy-login");
        loginButton.setId("fancy-login");
        
        //button actions
        setupButton.setOnAction(a -> {
            new SetupAccountPage(databaseHelper).show(primaryStage);
        });
        loginButton.setOnAction(a -> {
        	new UserLoginPage(databaseHelper).show(primaryStage);
        });

        VBox layout = new VBox(10);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");
        layout.getChildren().addAll(setupButton, loginButton);
        Scene scene = new Scene(layout, 800, 400);
        scene.getStylesheets().add(getClass().getResource("/databasePart1/style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Account Setup");
        primaryStage.show();
    }
}

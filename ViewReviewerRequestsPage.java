package databasePart1;

import java.sql.SQLException;

import databasePart1.DatabaseHelper;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/*
 * Creates the UI and functionality for allowing the Instructor to see the 
 * list of reviewer role requests, and allows them to approve or deny the request. 
 */
public class ViewReviewerRequestsPage{
	
	private final DatabaseHelper databaseHelper;

	/**
	 * Constructor for the ViewReviewerRequestsPage.
	 * 
	 * @param databaseHelper This passes the databaseHelper to the page to let it access the database. 
	 */
    public ViewReviewerRequestsPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
    
    
    /**
     * Sets up the page to display the list of students requesting to be a reviewer.
     * Allows Instructor to grant or deny the request through the UI. 
     * 
     * @param primaryStage Passes the current stage being used.
     * @param user Passes the current user object.
     */
    public void show (Stage primaryStage, Stage newStage) {

    	VBox pageVerticleBox = new VBox();
	    

		// Create Heading Label
	    Label heading = new Label("Welcome to the requests page! Please approve or deny any request");


		// Create VBox for ScrollPane
		VBox contentVBox = new VBox();


		// Get pending requests
		ArrayList<String> userNameList = new ArrayList<>();
		userNameList = databaseHelper.getPending();

		// Add each name and components
		for(String name : userNameList){
			HBox tempHBox = createHBox(name, primaryStage, newStage);
			contentVBox.getChildren().add(tempHBox);

			Line tempLine = createLine();
			contentVBox.getChildren().add(tempLine);
		}
		contentVBox.setSpacing(5);
		
		// Create a scrollpane
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(contentVBox);

		
		
		// Configure Page VBox
		pageVerticleBox.setVgrow(scrollPane, Priority.ALWAYS); // make scrollpane take up rest of page
		pageVerticleBox.getChildren().addAll(heading, scrollPane); // add heading and scrollpane
	    

		// COnfigure scene 
	    Scene deleteUserScene = new Scene(pageVerticleBox, 600, 600);
	    newStage.setScene(deleteUserScene);
	    newStage.setTitle("Reviewer Role Request Page");
	    newStage.show();
    }
    
    
    /**
     * Displays userName for the student requesting and provides Instructor buttons
     * to grant or deny the request. 
     * 
     * @param user Passes the current user object.
     * @param primaryStage Passes the current stage being used.
     * @param newStage This is used to refresh the page when necessary. 
     * @return HBox This will contain all buttons and labels for the userName. 
     */
    public HBox createHBox (String userName, Stage primaryStage, Stage newStage) {
    	HBox newHBox = new HBox(10);
		newHBox.setStyle("-fx-padding: 0 0 0 10;");


		Label nameLabel = new Label(userName);

		Button admitButton = new Button("\u2713");
    	admitButton.setStyle("-fx-background-color: #90EE90;");

		admitButton.setOnAction(a-> {
			databaseHelper.decisionMade(userName, true);
			try {
				databaseHelper.setUserRoles(userName, "isReviewer", true);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			databaseHelper.addNewReviewer(userName);

			ViewReviewerRequestsPage viewRequestsPage = new ViewReviewerRequestsPage(databaseHelper);
			viewRequestsPage.show(primaryStage, newStage);
		});
    	
		Button reviewButton = new Button("See Questions and Answers");
		reviewButton.setOnAction(b-> {
 			ListUserPostsPage list = new ListUserPostsPage(databaseHelper);
 			list.show(userName);
 		});
		
		Button denyButton = new Button("\u274C");
		denyButton.setStyle("-fx-background-color: #FFB6B6;");

		denyButton.setOnAction(b-> {
			databaseHelper.decisionMade(userName, false);
			ViewReviewerRequestsPage viewRequestsPage = new ViewReviewerRequestsPage(databaseHelper);
			viewRequestsPage.show(primaryStage, newStage);
		});


		newHBox.getChildren().addAll(nameLabel, admitButton, denyButton, reviewButton);
    	
    	return newHBox;
    }
    
    /**
     * Creates and returns a Line Object that spans almost the entire Stage. 
     * 
     * @return Line Returns a line.
     */
    public Line createLine() {
    	Line line = new Line();

    	line.setStrokeWidth(2);
    	line.setStartX(10);
		line.setEndX(590);
		line.setStyle("-fx-stroke: black;");

    	return line;
    }
			
    
}
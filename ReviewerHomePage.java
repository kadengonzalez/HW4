package databasePart1;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import databasePart1.*;

/**
 * Responsible for allowing reviewer to carry out duties. 
 * Creates functionality and UI.
 */
public class ReviewerHomePage {

	private final DatabaseHelper databaseHelper;

	/**
	 * Constructor for the ReviewerHomePage.
	 * 
	 * @param databaseHelper This passes the databaseHelper to the page to let it access the database. 
	 */
    public ReviewerHomePage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
	
    /**
     * Create functionality and UI for all Reviewer needs. 
     * This includes: logging out, accessing conversations,
     * and getting to list reviews page.
     * 
     * @param primaryStage Passes the current stage being used.
     * @param user Passes the current user object.
     */
	public void show(Stage primaryStage, User user) {
		DisplayUtilities displayUtilities = new DisplayUtilities();
		
		//Set up page Layout
		VBox layout = new VBox(10);
		layout.setId("standard-page-vbox-layout"); //Configure CSS
		
		// Create hello label
		Label helloLabel = displayUtilities.createLabel("Hello Reviewer", 200, 100);
		
		
		// Create logout button
		Button logoutButton = new Button("Logout");
		
		logoutButton.setOnAction( a-> {
			UserLoginPage userLoginPage = new UserLoginPage(databaseHelper); 
			userLoginPage.show(primaryStage);
		});


		Button conversationButton = new Button("Open Conversations:");
		conversationButton.setOnAction(d->{
			ConversationListPage conversationPage = new ConversationListPage(databaseHelper);
			conversationPage.show(user);
		});
		
		Button viewMessagesButton = new Button("View Private Messages");
		viewMessagesButton.setOnAction(e -> new ConversationListPage(databaseHelper).show(user));
		
		
		Button createMessageButton = new Button("Create Private Message");
		createMessageButton.setOnAction(e -> new CreatePrivateMessage(databaseHelper, user).show(primaryStage));

		Button reviewListPage = new Button("View List of Reviews");
		reviewListPage.setOnAction(c->{
			ListReviewsPage reviewsPage = new ListReviewsPage(databaseHelper);
			reviewsPage.show(user.getUserName());
		});

		
		
		
		// Add all elements to page
		layout.getChildren().addAll(helloLabel, logoutButton, conversationButton, reviewListPage, viewMessagesButton, createMessageButton);
		
		
		// Configure and set window visible
		Scene scene = new Scene(layout, 800, 400);
		scene.getStylesheets().add(getClass().getResource("/databasePart1/style.css").toExternalForm());

		primaryStage.setScene(scene);
		primaryStage.setTitle("Reviewer Home Page");
		primaryStage.show();
	}
}

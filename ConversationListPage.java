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
import java.util.ArrayList;



/* IMPORTANT PLEASE READ !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * Add button to StudentHomePage to send them to this page.
 * Class will list all possible conversations (CONRAD decides whats allowed).
 * Button will send them to PrivateMessagePage with user and reviewer information
 */
/**
 * Responsible for displaying all current PM's that are open and allowing user to navigate
 * to each conversation. 
 */
public class ConversationListPage {
	
	private final DatabaseHelper databaseHelper;

	/**
	 * Constructor for the ConversationListPage.
	 * 
	 * @param databaseHelper This passes the databaseHelper to the page to let it access the database. 
	 */
    public ConversationListPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
    
	
    /**
     * Displays all open conversations that the user has.
     * 
     * @param user Passes the current user object.
     */
	public void show (User user) {
		// Vbox to store components for the entire page
		VBox primaryVBox = new VBox();
		
		
		// Label for the page ***********************************************************************************
		Label headingLabel = new Label("Conversations:");
		headingLabel.setStyle("-fx-font-size: 24px"); // USE THIS TO EDIT TEXT SIZE OF THE HEADING
		
		
		// Create List of Conversations *************************************************************************
		VBox boxForScrollPane = new VBox();
		
		
		ArrayList<String> conversationsList = databaseHelper.getAllConversations(user);    
		
		
		if(conversationsList.size() == 0) { // if there are no conversations then display "(No conversations)"
			HBox tempHBox = new HBox();
			
			Label tempLabel = new Label("(No conversations.)");
			
			tempHBox.getChildren().addAll(tempLabel);
			boxForScrollPane.getChildren().addAll(tempHBox);
			
		} else { // if there are conversations display name and button link
			boxForScrollPane = createScrollPaneContent(boxForScrollPane, conversationsList, user);
		}
		
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(boxForScrollPane);
		
		
		
		// Add components to the VBox
		primaryVBox.getChildren().addAll(headingLabel, scrollPane);
		primaryVBox.setVgrow(scrollPane,  Priority.ALWAYS);
		
		// Set up the new page and display
		Stage newStage = new Stage();
		Scene conversationListPage = new Scene(primaryVBox, 600, 600);
		newStage.setScene(conversationListPage);
		newStage.setTitle("Conversation List Page");
		newStage.show();
		
	}
    
    /**
     * Nicely display every name in ArrayList<String> with a button to go to 
     * PM page with selected user. 
     * 
     * @param boxForScrollPane VBox for where all the components are to be stored. 
     * @param conversationsList String list of all userName's that have a PM with current user. 
     * @param user Current user.
     * @return VBox This contains all the HBox's created and will be used for the ScrollPane. 
     */
	public VBox createScrollPaneContent(VBox boxForScrollPane, ArrayList<String> conversationsList, User user) { 
		for(String name : conversationsList) {
			// Create HBox
			HBox contentBox = new HBox();
			
			
			// Create label to display name
			Label nameLabel = new Label();
			nameLabel.setText(name); 
			
			
			// Create button to get to PM window
			Button privateMessageButton = new Button("Click to message");
			
			privateMessageButton.setOnAction(a->{
				PrivateMessagePage PMPage = new PrivateMessagePage(databaseHelper); // Reference if troubled
				PMPage.show(user, name);
			});
			
			
			// Add everything into HBox
			contentBox.getChildren().addAll(nameLabel, privateMessageButton);
			
			
			// Add Hbox to VBox
			boxForScrollPane.getChildren().add(contentBox);
		}
		
		// Return VBox
		return boxForScrollPane;
	}
	
	
	
}
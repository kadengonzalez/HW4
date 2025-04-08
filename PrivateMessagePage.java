package databasePart1;

import java.sql.SQLException;
import java.text.SimpleDateFormat; 
import java.util.Date; 

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
import javafx.scene.layout.Region;


/**
 * The PrivateMessagesPage class allows us to see a full conversation between two users 
 */
/**
 * <h1>Private Message Page Class</h1>
 * @author CSE360 Monday 11
 * @version 1.0
 * @since 2025-03-30
 */


public class PrivateMessagePage {
	
	private final DatabaseHelper databaseHelper;

	/**
	 * Constructor for creating PrivateMessagePage with only the database helper.
	 * Used when user context is passed during method calls instead of constructor.
	 *
	 * @param databaseHelper The database helper to manage data interactions.
	 */

    public PrivateMessagePage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
    

	/** Displays the private message conversation given a user and a reviewer. 
	 * Calls multiple functions including getConnectionID and newMessage from the database in the function 
	 * @param user
	 * @param reviewerName
	 */
	
     public void show (User user, String reviewerName) {
		// Vbox to store components for the entire page
		VBox primaryVBox = new VBox();
		
		
		// Label for the page ***********************************************************************************
		Label headingLabel = new Label();
		headingLabel.setStyle("-fx-font-size: 24px"); // USE THIS TO EDIT TEXT SIZE OF THE HEADING
		headingLabel.setText(reviewerName); 
		
		
		// Create ScrollPane ***********************************************************************************
		VBox boxForScrollPane = new VBox();
		int connectionID = databaseHelper.getConnectionID(user.getUserName(), reviewerName);
		
		ArrayList<PrivateMessage> messagesList = databaseHelper.loadAllMessages(connectionID);
		
		boxForScrollPane = formatConversations(boxForScrollPane, messagesList);
		
		ScrollPane scrollPane = new ScrollPane();
		
		scrollPane.setContent(boxForScrollPane);
		
		
		
		// Sending Message Functionality ************************************************************************
		HBox newMessageBox = new HBox();
		
		TextField textField = new TextField();
		
		Button send = new Button("Send");
		send.setOnAction(a->{
			String inputText = textField.getText(); // Collect message
			String sender = user.getUserName();
			System.out.println("Here is the sender: " + sender);
			
			PrivateMessage pm = new PrivateMessage(user.getUserName(), reviewerName, connectionID, inputText, sender);
			Boolean worked = databaseHelper.newMessage(pm);
			if (worked) {
				PrivateMessagePage pmPage = new PrivateMessagePage(databaseHelper);
				pmPage.show(user, reviewerName); 
			}
		});
		
		
		newMessageBox.getChildren().addAll(textField, send);
		
		// Add components to the VBox
		primaryVBox.getChildren().addAll(headingLabel, scrollPane, newMessageBox);
		
		// Set up the new page and display
		Stage messageStage = new Stage();
		Scene messageScene = new Scene(primaryVBox, 600, 600);
		messageStage.setScene(messageScene);
		messageStage.setTitle("Private Message(s)");
		messageStage.show();
		
	}
	
	
	
	/** Formats the PMS using VBox, Hbox and the array list of messages in a conversation. 
	 * @param boxForScrollPane
	 * @param messagesList
	 * @return VBox  ; for every message in the messagesList
	 */
	public VBox formatConversations(VBox boxForScrollPane, ArrayList<PrivateMessage> messagesList) { // ADD CORRECT DATA TYPE
		for (PrivateMessage message : messagesList) {
			HBox contentHBox = new HBox();
			
			Label senderLabel = new Label();
			senderLabel.setText(message.getSender()); 
			
			
			Label messageText = new Label();
			messageText.setText(message.getMessage()); 
			
			
			Label timeLabel = new Label();
			String time = formatTime((message.getTime())); 
			timeLabel.setText(time); // Type cast int to STring
			
			
			Region spacer1 = new Region();
			Region spacer2 = new Region();
			
			
			// Add all to HBox
			contentHBox.setHgrow(spacer1,  Priority.ALWAYS);
			contentHBox.setHgrow(spacer2, Priority.ALWAYS);
			contentHBox.getChildren().addAll(senderLabel, spacer1, messageText, spacer2, timeLabel);
			
			
			// Add to VBox
			boxForScrollPane.getChildren().add(contentHBox);
		}
		return boxForScrollPane;
	}
	
	/** A way to format the time in the chats
	 * @param time 
	 * @return String in the format MM/dd/yyyy HH:mm
	 */
	public String formatTime(int time) {
		
		Date date = new Date((long) time *1000);
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		
		return sdf.format(date);
	}
	
    
}
	
	
	

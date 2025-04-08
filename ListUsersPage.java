package databasePart1;

import java.util.LinkedList;

import databasePart1.DatabaseHelper;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Contains functionality and creates UI for displaying all users and 
 * their information. 
 */
public class ListUsersPage {
	
	private final DatabaseHelper databaseHelper;

	
	/**
	 * Constructor for the ListUsersPage.
	 * 
	 * @param databaseHelper This passes the databaseHelper to the page to let it access the database. 
	 */
    public ListUsersPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
	
    
    /**
     * Creates UI and displays all users in system along with
     * their account information. 
     * 
     * @param primaryStage Passes the current stage being used.
     */
	public void show(Stage primaryStage) {
		// Create new layout
		Stage newStage = new Stage();
		Pane pane = new Pane();
		DisplayUtilities displayUtilities = new DisplayUtilities();
		
		// Get information for every user
		//String allUserInfo = databaseHelper.getAllUsers(); // database will return correctly formated information
		LinkedList<User> usersList = databaseHelper.getAllUsers();
		
		VBox layout = new VBox(5);
		int size = usersList.size();
		System.out.println("Size: " + size + '\n');
		for(int i=0; i<size; i++) {
			//Set up a new HBox for Each User
			HBox hbox = new HBox(10); //Main
			HBox sub1 = new HBox();
			HBox sub2 = new HBox();
			HBox sub3 = new HBox();
			HBox sub4 = new HBox();
			
			//User Information Labels: Title and Texts
			Label nameLabel = new Label("Name: ");
			Label name = new Label(usersList.get(i).getName());
			Label userNameLabel = new Label("Username: ");
			Label userName = new Label(usersList.get(i).getUserName());
			Label emailLabel = new Label("Email: ");
			Label email = new Label(usersList.get(i).getEmail());
			Label rolesLabel = new Label("Roles:");
			Label roles = new Label();
			
			//Add Roles to the Text Field
			if(usersList.get(i).getIsAdmin()) {
				roles.setText(roles.getText() + " Admin");
			} 
			if(usersList.get(i).getIsUser()) {
				roles.setText(roles.getText() + " User");
			}
			if(usersList.get(i).getIsStaff()) {
				roles.setText(roles.getText() + " Staff");
			}
			if(usersList.get(i).getIsInstructor()) {		
				roles.setText(roles.getText() + " Instructor");
			}
			if(usersList.get(i).getIsReviewer()) {
				roles.setText(roles.getText() + " Reviewer");
			}
			if(usersList.get(i).getIsStudent()) {
				roles.setText(roles.getText() + " Student");
			}
			
			//Set a CSS label for The Titles
			nameLabel.setId("label-title");
			userNameLabel.setId("label-title");
			emailLabel.setId("label-title");
			rolesLabel.setId("label-title");
			
			//Set CSS Label for the Text
			name.setId("label-text");
			userName.setId("label-text");
			email.setId("label-text");
			roles.setId("label-text");
			
			//Add elements to the Layouts
			sub1.getChildren().addAll(nameLabel, name);
			sub2.getChildren().addAll(userNameLabel, userName);
			sub3.getChildren().addAll(emailLabel, email);
			sub4.getChildren().addAll(rolesLabel, roles);
			hbox.getChildren().addAll(sub1, sub2, sub3, sub4);
			layout.getChildren().add(hbox);
		}
		
		
		ScrollPane scrollPane = new ScrollPane(layout);//store the vbox in here
		scrollPane.setMinWidth(750);
		
		
		// Create and configure area to display information
		//TextArea textArea = displayUtilities.createTextArea(allUserInfo, true, false, 10, 10, 720, 370);
		
		
		// Add elements to layout
		//pane.getChildren().add(scrollPane);
		
		
		// Configure and set window visible
		Scene scene = new Scene(scrollPane, 750, 400);
		scene.getStylesheets().add(getClass().getResource("/databasePart1/style.css").toExternalForm());

		newStage.setScene(scene);;
		newStage.setTitle("UpdateRolesPage");
		newStage.show();
	}
}
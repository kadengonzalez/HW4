package databasePart1;

import java.sql.SQLException;

import databasePart1.DatabaseHelper;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * The ReviewAnswerPage class allows us to see the UI for reviewing an answer. Very similar to answer question page.  
 */
/**
 * <h1>Review Answer Page Class</h1>
 * @author CSE360 Monday 11
 * @version 1.0
 * @since 2025-03-15
 */
public class ReviewAnswerPage {
	
	private final DatabaseHelper databaseHelper;
	
	/**Constructor for the Review Answer Page 
	 * @param databaseHelper
	 */
	public ReviewAnswerPage(DatabaseHelper databaseHelper) {
		this.databaseHelper = databaseHelper;
	}
	
	
	//
	/**Main show for Creating an Review of a Question
	 * @param primaryStage
	 * @param user
	 * @param question
	 */
	public void show(Stage primaryStage, User user, Question question) {
		VBox vbox = new VBox(10);
		vbox.setStyle("-fx-alignment: center; -fx-padding: 20;");
		
		//Set up some elements
		Label title = new Label("Review Answer");
		Label questionText = new Label(question.getQuestionText());
		questionText.setWrapText(true);
		TextField reviewText = new TextField();
		CheckBox hideUserName = new CheckBox("Post Without Username Attached");
		
		//Set some label text
		reviewText.setPromptText("Review Text");
		
		//Text for an error label
    	Label errorLabel = new Label();
    	errorLabel.setWrapText(true);
    	errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		
		//The Add answer button
		Button addReview = new Button("Add Review");
		addReview.setOnAction( a-> {   
			//check validation
			if(reviewText.getText().length() > 3000) {
				errorLabel.setText("Review Text Must be less than 3001 characters");
			}
			else {
				//create new review object
				Answer review = new Answer(user.getUserName(), reviewText.getText(), question.getId(), hideUserName.isSelected(), 0, 0);
				review.setIsReview(); 

				//Find the Users highest Role and set it in the review, for Ranking
				if(databaseHelper.getIsAdmin("cse360users", review.getUserName(), "")){
					review.setHighestRole("admin");
				} else if (databaseHelper.getIsStaff("cse360users", review.getUserName(), "")) {
					review.setHighestRole("staff");
				} else if (databaseHelper.getIsInstructor("cse360users", review.getUserName(), "")) {
					review.setHighestRole("instructor");
				} else if (databaseHelper.getIsReviewer("cse360users", review.getUserName(), "")) {
					review.setHighestRole("reviewer");
				} else if (databaseHelper.getIsStudent("cse360users", review.getUserName(), "")) {
					review.setHighestRole("student");
				} else if (databaseHelper.getIsUser("cse360users", review.getUserName(), "")) {
					review.setHighestRole("user");
				}
				String bool;
				if(review.getIsReview()){
					bool = "true";
				} else{
					bool = "false";
				}
				System.out.println("This Review: \""+review.getAnswerText() + "\" will be added to the database as "+bool );
				//send the new review to the database
				databaseHelper.addNewAnswer(review);
                //databaseHelper.setIsReview(review, true);
				//load back the discussions page
				ViewDiscussions viewDiscussions = new ViewDiscussions(databaseHelper); 
				viewDiscussions.show(primaryStage, user, question, false);
			}
		});
		
		//add everything to the vbox
		vbox.getChildren().addAll(title, questionText, reviewText, hideUserName, addReview, errorLabel);
		
		//set the scene and stage
		Scene scene = new Scene(vbox, 750, 400);
		primaryStage.setScene(scene);;
		primaryStage.setTitle("Review Answer Page");
		primaryStage.show();
	}
	
	
	//
	/**This one is similar to the above but allows the user to edit current Review information, and it updates in on the database side
	 * @param primaryStage
	 * @param user
	 * @param question
	 * @param review
	 */
	public void showUpdateReview(Stage primaryStage, User user, Question question, Answer review) {
		VBox vbox = new VBox(10);
		vbox.setStyle("-fx-alignment: center; -fx-padding: 20;");
		
		//Set up some labels
		Label title = new Label("Review Answer:");
		Label questionText = new Label(question.getQuestionText());
		TextField reviewText = new TextField();
		CheckBox hideUserName = new CheckBox("Post Without Username Attached");
		
		//Set the current review information to make it editable for the user
		reviewText.setText(review.getAnswerText());
		//Set the checkbox with current info
		if(review.getHideUserName()) {
			hideUserName.setSelected(true);
		}
		else {hideUserName.setSelected(false);}
		
		//Text for an error label
    	Label errorLabel = new Label();
    	errorLabel.setWrapText(true);
    	errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		
		//Update the Review  in the database
		Button updateReview = new Button("Update Review");
		updateReview.setOnAction( a-> {   
			//check validation
			if(reviewText.getText().length() > 3000) {
				errorLabel.setText("Review Text Must be less than 3001 characters");
			}
			else {
				review.setAnswerText(reviewText.getText());
				review.setHideUserName(hideUserName.isSelected());
			
				try {
					databaseHelper.updateAnswer(review);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//go back to discussion page
				ViewDiscussions viewDiscussions = new ViewDiscussions(databaseHelper); 
				viewDiscussions.show(primaryStage, user, question, false);
			}
		});
		
		//add elemetns to vbox
		vbox.getChildren().addAll(title, questionText, reviewText, hideUserName, updateReview, errorLabel);
		
		//set the stage
		Scene scene = new Scene(vbox, 750, 400);
		primaryStage.setScene(scene);;
		primaryStage.setTitle("Update Review Answer Page");
		primaryStage.show();
	}
	
	//
	/**This Function for replying to an answer
	 * @param primaryStage
	 * @param user
	 * @param question
	 * @param answer
	 */
	public void showReply(Stage primaryStage, User user, Question question, Answer answer) {
		//passing in the current question on the page, and the answer they clicked review on 
		VBox vbox = new VBox(10);
		vbox.setStyle("-fx-alignment: center; -fx-padding: 20;");
		
		//Set up some elements
		Label title = new Label("Review Answer:");
		Label questionText = new Label("Question: " + question.getQuestionText());
		Label answerTextToReview = new Label("Answer to review: " + answer.getAnswerText());
		questionText.setWrapText(true);
		answerTextToReview.setWrapText(true);
		TextField reviewText = new TextField();
		CheckBox hideUserName = new CheckBox("Post Without Username Attached");
		
		//Set some label text
		reviewText.setPromptText("Reply Text");
		
		//Text for an error label
    	Label errorLabel = new Label();
    	errorLabel.setWrapText(true);
    	errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		
		//The Add Review button
		Button addReview = new Button("Add Review");
		addReview.setOnAction( a-> {   
			//check validation
			if(reviewText.getText().length() > 3000) {
				errorLabel.setText("Review Text Must be less than 3001 characters");
			}
			else {
				
				//Make sure the sub replies don't go over level 5
				int newLevel = (answer.getLevel() + 1);
				
				//create new Review object
				Answer reply = new Answer(user.getUserName(), reviewText.getText(), question.getId(), hideUserName.isSelected(), answer.getId(), newLevel );
				reply.setIsReview(); 


				//Find the Users highest Role and set it in the answer, for Ranking
				if(databaseHelper.getIsAdmin("cse360users", reply.getUserName(), "")){
					reply.setHighestRole("admin");
				} else if (databaseHelper.getIsStaff("cse360users", reply.getUserName(), "")) {
					reply.setHighestRole("staff");
				} else if (databaseHelper.getIsInstructor("cse360users", reply.getUserName(), "")) {
					reply.setHighestRole("instructor");
				} else if (databaseHelper.getIsReviewer("cse360users", reply.getUserName(), "")) {
					reply.setHighestRole("reviewer");
				} else if (databaseHelper.getIsStudent("cse360users", reply.getUserName(), "")) {
					reply.setHighestRole("student");
				} else if (databaseHelper.getIsUser("cse360users", reply.getUserName(), "")) {
					reply.setHighestRole("user");
				}else{
					System.out.println("did not set role");
				}
				
				//send the new answer to the database
				databaseHelper.addNewAnswer(reply);
                //databaseHelper.setIsReview(reply, true);
				//load back the discussions page
				ViewDiscussions viewDiscussions = new ViewDiscussions(databaseHelper); 
				viewDiscussions.show(primaryStage, user, question, false);
			}
		});
		
		//add everything to the vbox
		vbox.getChildren().addAll(title, questionText, answerTextToReview, reviewText, hideUserName, addReview, errorLabel);
		
		//set the scene and stage
		Scene scene = new Scene(vbox, 750, 400);
		primaryStage.setScene(scene);;
		primaryStage.setTitle("Review Answer Page");
		primaryStage.show();
	}	
	
	
	
	
}
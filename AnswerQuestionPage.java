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
 * The AnswerQuestionPage class allows us to see the UI for answering a question 
 */
/**
 * <h1>Answer Question Page Class</h1>
 * @author CSE360 Monday 11
 * @version 1.0
 * @since 2025-02-25
 */

public class AnswerQuestionPage {

	private final DatabaseHelper databaseHelper;
	
	/**Constructor for the AnswerQuestionPage
	 * @param databaseHelper
	 */
	public AnswerQuestionPage(DatabaseHelper databaseHelper) {
		this.databaseHelper = databaseHelper;
	}
	
	
	//Main show for Creating an Answer
	/** Main show for creating an Answer. Passes the question and user so we can use see them and update them in the database. 
	 * @param primaryStage
	 * @param user
	 * @param question
	 */
	public void show(Stage primaryStage, User user, Question question) {
		VBox vbox = new VBox(10);
		vbox.setStyle("-fx-alignment: center; -fx-padding: 20;");
		
		//Set up some elements
		Label title = new Label("Answer Question:");
		Label questionText = new Label(question.getQuestionText());
		questionText.setWrapText(true);
		TextField answerText = new TextField();
		CheckBox hideUserName = new CheckBox("Post Without Username Attached");
		
		//Set some label text
		answerText.setPromptText("Answer Text");
		
		//Text for an error label
    	Label errorLabel = new Label();
    	errorLabel.setWrapText(true);
    	errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		
		//The Add answer button
		Button addAnswer = new Button("Add Answer");
		addAnswer.setOnAction( a-> {   
			//check validation
			if(answerText.getText().length() > 3000) {
				errorLabel.setText("Answer Text Must be less than 3001 characters");
			}
			else {
				//create new answer object
				Answer answer = new Answer(user.getUserName(), answerText.getText(), question.getId(), hideUserName.isSelected(), 0, 0);
				
				//Find the Users highest Role and set it in the answer, for Ranking
				if(databaseHelper.getIsAdmin("cse360users", answer.getUserName(), "")){
					answer.setHighestRole("admin");
				} else if (databaseHelper.getIsStaff("cse360users", answer.getUserName(), "")) {
					answer.setHighestRole("staff");
				} else if (databaseHelper.getIsInstructor("cse360users", answer.getUserName(), "")) {
					answer.setHighestRole("instructor");
				} else if (databaseHelper.getIsReviewer("cse360users", answer.getUserName(), "")) {
					answer.setHighestRole("reviewer");
				} else if (databaseHelper.getIsStudent("cse360users", answer.getUserName(), "")) {
					answer.setHighestRole("student");
				} else if (databaseHelper.getIsUser("cse360users", answer.getUserName(), "")) {
					answer.setHighestRole("user");
				}
				
				
				//send the new answer to the database
				databaseHelper.addNewAnswer(answer);
				//load back the discussions page
				ViewDiscussions viewDiscussions = new ViewDiscussions(databaseHelper); 
				viewDiscussions.show(primaryStage, user, question, false);
			}
		});
		
		//add everything to the vbox
		vbox.getChildren().addAll(title, questionText, answerText, hideUserName, addAnswer, errorLabel);
		
		//set the scene and stage
		Scene scene = new Scene(vbox, 750, 400);
		primaryStage.setScene(scene);;
		primaryStage.setTitle("Answer Question Page");
		primaryStage.show();
	}
	
	
	//
	/**This one is similar to the above function but allows the user to edit current answer information, and it updates in on the database side
	 * @param primaryStage
	 * @param user
	 * @param question
	 * @param answer
	 */
	public void showUpdateAnswer(Stage primaryStage, User user, Question question, Answer answer) {
		VBox vbox = new VBox(10);
		vbox.setStyle("-fx-alignment: center; -fx-padding: 20;");
		
		//Set up some labels
		Label title = new Label("Answer Question:");
		Label questionText = new Label(question.getQuestionText());
		TextField answerText = new TextField();
		CheckBox hideUserName = new CheckBox("Post Without Username Attached");
		
		//Set the current answer information to make it editable for the user
		answerText.setText(answer.getAnswerText());
		//Set the checkbox with current info
		if(answer.getHideUserName()) {
			hideUserName.setSelected(true);
		}
		else {hideUserName.setSelected(false);}
		
		//Text for an error label
    	Label errorLabel = new Label();
    	errorLabel.setWrapText(true);
    	errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		
		//Update the answer in the database
		Button updateAnswer = new Button("Update Answer");
		updateAnswer.setOnAction( a-> {   
			//check validation
			if(answerText.getText().length() > 3000) {
				errorLabel.setText("Answer Text Must be less than 3001 characters");
			}
			else {
				answer.setAnswerText(answerText.getText());
				answer.setHideUserName(hideUserName.isSelected());
			
				try {
					databaseHelper.updateAnswer(answer);
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
		vbox.getChildren().addAll(title, questionText, answerText, hideUserName, updateAnswer, errorLabel);
		
		//set the stage
		Scene scene = new Scene(vbox, 750, 400);
		primaryStage.setScene(scene);;
		primaryStage.setTitle("Update Answer Question Page");
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
		//passing in the current question on the page, and the answer they clicked reply on
		VBox vbox = new VBox(10);
		vbox.setStyle("-fx-alignment: center; -fx-padding: 20;");
		
		//Set up some elements
		Label title = new Label("Answer Question:");
		Label questionText = new Label("Question: " + question.getQuestionText());
		Label answerTextToReply = new Label("Answer to reply to: " + answer.getAnswerText());
		questionText.setWrapText(true);
		answerTextToReply.setWrapText(true);
		TextField answerText = new TextField();
		CheckBox hideUserName = new CheckBox("Post Without Username Attached");
		
		//Set some label text
		answerText.setPromptText("Reply Text");
		
		//Text for an error label
    	Label errorLabel = new Label();
    	errorLabel.setWrapText(true);
    	errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		
		//The Add answer button
		Button addAnswer = new Button("Add Reply");
		addAnswer.setOnAction( a-> {   
			//check validation
			if(answerText.getText().length() > 3000) {
				errorLabel.setText("Reply Text Must be less than 3001 characters");
			}
			else {
				
				//Make sure the sub replies don't go over level 5
				int newLevel = (answer.getLevel() + 1);
				
				//create new answer object
				Answer reply = new Answer(user.getUserName(), answerText.getText(), question.getId(), hideUserName.isSelected(), answer.getId(), newLevel);
				
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
				//load back the discussions page
				ViewDiscussions viewDiscussions = new ViewDiscussions(databaseHelper); 
				viewDiscussions.show(primaryStage, user, question, false);
			}
		});
		
		//add everything to the vbox
		vbox.getChildren().addAll(title, questionText, answerTextToReply, answerText, hideUserName, addAnswer, errorLabel);
		
		//set the scene and stage
		Scene scene = new Scene(vbox, 750, 400);
		primaryStage.setScene(scene);;
		primaryStage.setTitle("Answer Question Page");
		primaryStage.show();
	}	
	
	
	
	
}
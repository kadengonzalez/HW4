package databasePart1;
import java.sql.SQLException;

import databasePart1.DatabaseHelper;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * This class is responsible for allowing the user to create a new question on the 
 * discussion page and allowing them to update questions. 
 */
public class NewQuestion {
		
	private final DatabaseHelper databaseHelper;
	public QuestionsList questionsList;
	
	public Boolean hwBool = false; 
    public Boolean conceptBool = false; 
    public Boolean examBool = false; 
    public Boolean generalBool = false; 
    public Boolean socialBool = false; 

    // The Tag you want to search 
    public String tag = ""; 

	public NewQuestion(DatabaseHelper databaseHelper) {
		this.databaseHelper = databaseHelper;
	}
		
	
	//This is the main show for generating a new question object
	/**
	 * This allows a user to create a new Question on the discussion page.
	 * This creates the UI and the functionality. 
	 * 
	 * @param primaryStage Used to update the current Stage being displayed. 
	 * @param user Current user. 
	 */
	public void show(Stage primaryStage, User user) {
		VBox vbox = new VBox();
		vbox.setStyle("-fx-alignment: center; -fx-padding: 20;");

		//set the elements up with pretext
		Label questionTitleLabel = new Label("Question Title");
		questionTitleLabel.setVisible(true);
			
		TextField questionTitle = new TextField();
		questionTitle.setPromptText("Title");
			
		Label questionTextLabel = new Label("Question");

		TextField questionText = new TextField();
		questionText.setPromptText("Question Text");
			
		CheckBox hideUserName = new CheckBox("Post Without Username Attached");
		
		//Text for an error label
    	Label errorLabel = new Label();
    	errorLabel.setWrapText(true);
    	errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
    	
    	 Boolean[] listTags = {hwBool, conceptBool, examBool, generalBool, socialBool};

         // A Button for Homework
         Button homework = new Button("homeworkTag"); 
     	// A Button for Concept
     	Button concept = new Button("conceptTag"); 
 		// A Button for Exam
 		Button exam = new Button("examTag");
 		// A Button for General
 		Button general = new Button ("generalTag");
 		// A Button for Social
 		Button social = new Button ("socialTag");
 		
 		homework.setOnAction( a-> {
             setButtonGreen(homework);
             setMultipleGrey(concept, exam, general, social);
             hwBool = true; 
             setFalse(listTags, hwBool);
             tag = "homeworkTag";
 		});

 		concept.setOnAction( a-> {
 			setButtonGreen(concept);
             setMultipleGrey(homework, exam, general, social);
             conceptBool = true; 
             setFalse(listTags, conceptBool);
             tag = "conceptTag";
 		});
 		
 		exam.setOnAction(a -> {
             setButtonGreen(exam);
             setMultipleGrey(concept, homework, general, social);
             examBool = true; 
             setFalse(listTags, examBool);
             tag = "examTag";
 	    	
 	    });
 		general.setOnAction( a-> {
             setButtonGreen(general);
             setMultipleGrey(concept, exam, homework, social);
             generalBool = true; 
             setFalse(listTags, generalBool);
             tag = "generalTag";
 		});
 		
 		
 		social.setOnAction( a-> {
 			setButtonGreen(social);
             setMultipleGrey(concept, exam, general, homework);
             socialBool = true; 
             setFalse(listTags, socialBool);
             tag = "socialTag";
 		});
		
		// Set up add question button to load the question object to the database
		Button addQuestion = new Button("Add Question");
		addQuestion.setOnAction( a-> {   
			if(questionTitle.getText().length() > 50) {
				errorLabel.setText("Question Title Must be less than 51 characters");
			}
			else if(questionText.getText().length() > 2000) {
				errorLabel.setText("Question Text Must be less than 2001 characters");
			}
			else {
				Question question = new Question(questionTitle.getText(), questionText.getText(), user.getUserName(), false, false, 0, hideUserName.isSelected(), 0 );
				databaseHelper.addNewQuestion(question);
				// Gets ID and updates the bools
				int id = databaseHelper.getQuestionID(questionText.getText()); 
				
				System.out.println("Question ID is: " + id);
				
				try {
					databaseHelper.updateTags(id, hwBool, conceptBool, examBool, generalBool, socialBool); 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			
				
				ViewDiscussions viewDiscussions = new ViewDiscussions(databaseHelper); 
				viewDiscussions.show(primaryStage, user, question, false);
			}
		});
			
		//Add elements to vbox
		vbox.getChildren().addAll(questionTitleLabel, questionTitle, questionTextLabel, questionText, hideUserName, addQuestion, errorLabel, homework, concept, general, social, exam);

			//set the stage
		Scene scene = new Scene(vbox, 750, 400);
		scene.getStylesheets().add(getClass().getResource("/databasePart1/style.css").toExternalForm());

		primaryStage.setScene(scene);;
		primaryStage.setTitle("New Question");
		primaryStage.show();
			
	}
	
	//This is for updating current question information
	/**
	 * This displays the updated question information for the user. 
	 * 
	 * @param primaryStage Used to update the current Stage being displayed. 
	 * @param user Current user.
	 * @param question Question object that is being used. 
	 */
	
	public void showUpdate(Stage primaryStage, User user, Question question) {
		VBox vbox = new VBox();
		vbox.setStyle("-fx-alignment: center; -fx-padding: 20;");

		//create the elemnts, but fill it with the current question information
		Label questionTitleLabel = new Label("Question Title");
		questionTitleLabel.setVisible(true);
			
		TextField questionTitle = new TextField();
		questionTitle.setText(question.getTitle());
			
		Label questionTextLabel = new Label("Question");

		TextField questionText = new TextField();
		questionText.setText(question.getQuestionText());
			
		CheckBox hideUserName = new CheckBox("Post Without Username Attached");
		if(question.getHideUserName()) {
			hideUserName.setSelected(true);
		}
		else {hideUserName.setSelected(false);}
		
		//Text for an error label
    	Label errorLabel = new Label();
    	errorLabel.setWrapText(true);
    	errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
		
		//this is the update question button which send the question(with it's update info) to the DB
		Button updateQuestion = new Button("Update Question");
		updateQuestion.setOnAction( a-> {   
			if(questionTitle.getText().length() > 50) {
				errorLabel.setText("Question Title Must be less than 50 characters");
			}
			else if(questionText.getText().length() > 2000) {
				errorLabel.setText("Question Text Must be less than 2000 characters");
			}
			else {
				question.setTitleText(questionTitle.getText());
				question.setQuestionText(questionText.getText());
				question.setHideUserName(hideUserName.isSelected());
				try {
					databaseHelper.updateQuestion(question);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			
				//sends us back to the discussion page
				ViewDiscussions viewDiscussions = new ViewDiscussions(databaseHelper); 
				viewDiscussions.show(primaryStage, user, question, false);
			}
		});
			
		//add elements
		vbox.getChildren().addAll(questionTitleLabel, questionTitle, questionTextLabel, questionText, hideUserName, updateQuestion, errorLabel);

			//set the stage
		Scene scene = new Scene(vbox, 750, 400);
		scene.getStylesheets().add(getClass().getResource("/databasePart1/style.css").toExternalForm());

		primaryStage.setScene(scene);;
		primaryStage.setTitle("Update Question");
		primaryStage.show();
			
	}
	
	/**
	 * Sets the background color of the button to grey. 
	 * 
	 * @param button This is the object that will be updated. 
	 */
    public void setButtonGrey(Button button) {
        button.setStyle("-fx-background-color: #D3D3D3;");
    }

    /**
     * Sets 4 buttons to have grey background. 
     * 
     * @param b1 First button. 
     * @param b2 Second button. 
     * @param b3 Third button. 
     * @param b4 Fourth button. 
     */
    public void setMultipleGrey(Button b1,Button b2,Button b3,Button b4) {
        b1.setStyle("-fx-background-color: #D3D3D3;");
        b2.setStyle("-fx-background-color: #D3D3D3;");
        b3.setStyle("-fx-background-color: #D3D3D3;");
        b4.setStyle("-fx-background-color: #D3D3D3;");
    }

    /**
     * Sets the background of a button to green. 
     * 
     * @param button Button to be updated. 
     */
    public void setButtonGreen(Button button) {
        button.setStyle("-fx-background-color: #90EE90;");
    }

    /**
     * Takes an Array of booleans and sets each one to false
     * unless it equals the king boolean.  
     * 
     * @param bools Array of booleans. 
     * @param king Compares each boolean in Array to this boolean. 
     */
    public void setFalse(Boolean[] bools, boolean king){
        for(int i = 0; i < bools.length; i++){
            if(bools[i] != king){
                bools[i] = false; 
            }
        }
    }
}

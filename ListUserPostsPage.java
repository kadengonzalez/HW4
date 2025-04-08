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

/**
 * Responsible for retrieving and displaying all questions and answers that have been created by a user.
 */
public class ListUserPostsPage {
    
    private final DatabaseHelper databaseHelper;

    /**
	 * Constructor for the ReviewerHomePage.
	 * 
	 * @param databaseHelper This passes the databaseHelper to the page to let it access the database. 
	 */
    public ListUserPostsPage(DatabaseHelper databaseHelper){
        this.databaseHelper = databaseHelper;
    }

    /**
     * Collects all the questions and answers of the user and displays them.
     * 
     * @param userName This is the name of the user to pull information on. 
     */
    public void show (String userName){
        VBox pageVerticalBox = new VBox();

        // Creating Heading for page
        Label heading = new Label();
        heading.setText(userName + " Post's");
        heading.setStyle("-fx-font-style: 20px");
        
        
        // Collect information from database
        QuestionsList questionList = databaseHelper.loadQuestionsByUsername(userName);
        AnswersList answerList = databaseHelper.loadAnswerByUsername(userName);

      

        // VBox to store all the posts
        VBox contentVBox = addContent(questionList, answerList);


        // Create scrollpane and add VBox content
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(contentVBox);



        // Configure Page VBox
        pageVerticalBox.getChildren().addAll(heading, scrollPane);

        // Configure scene
        Scene userPostScene = new Scene(pageVerticalBox, 600, 600);
        Stage newStage = new Stage();
        newStage.setScene(userPostScene);
        newStage.setTitle("List User Posts Page");
        newStage.show();
    }

    /**
     * Loops through both lists and adds everything to a VBox that will
     * contain all components and information. 
     * 
     * @param questionList This will contain a list of Questions. 
     * @param answerList This will contain a list of Answers.
     * @return VBox The VBox is going to contain all components. 
     */
    public VBox addContent(QuestionsList questionList, AnswersList answerList){
    	VBox contentVBox = new VBox();
    	
    	if(questionList.getSize() == 0) {
    		System.out.println("HEY. THIS AIN'T WORKING. (QUESTIONSLIST)");
    	}
    	if(answerList.getSize() == 0) {
    		System.out.println("HEY. THIS AIN'T WORKING. (AnswersLIST)");
    	}
    	
    	
        for(int i = 0; i < questionList.getSize(); i++){
        	Question currentQuestion = questionList.getQuestionFromIndex(i);
            HBox contentBox = new HBox();

            Label textLabel = new Label(currentQuestion.getQuestionText());

            contentBox.getChildren().addAll(textLabel);

            contentVBox.getChildren().add(contentBox);
            System.out.println("Adding Question to Display: " + i);
        }

        for(int i = 0; i < answerList.getSize(); i++){
        	Answer currentAnswer = answerList.getAnswerFromIndex(i);
            HBox contentBox = new HBox();

            Label textLabel = new Label(currentAnswer.getAnswerText());

            contentBox.getChildren().addAll(textLabel);

            contentVBox.getChildren().add(contentBox);
            System.out.println("Adding Answer to Display: " + i);
        }

        return contentVBox;
    }


}
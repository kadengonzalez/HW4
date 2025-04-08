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
 * Will display all reviews in the system of a given reviewer. 
 */
public class ListReviewsPage {
    
    private final DatabaseHelper databaseHelper;

    /**
	 * Constructor for the ListReviewsPage.
	 * 
	 * @param databaseHelper This passes the databaseHelper to the page to let it access the database. 
	 */
    public ListReviewsPage(DatabaseHelper databaseHelper){
        this.databaseHelper = databaseHelper;
    }

    /**
     * Create UI to display all reviews posted by a specified reviewer. 
     * 
     * @param userName Takes the name of a reviewer. 
     */
    public void show (String userName){
        VBox pageVerticalBox = new VBox();
    
        // Creating Heading for page
        Label heading = new Label();
        heading.setText(userName + "Reviews");
        heading.setStyle("-fx-font-style: 20px");


        // Get list of the users Reviews
        AnswersList reviewList = databaseHelper.loadReviewsByUsername(userName);

      
        // VBox to store all the posts
        VBox contentVBox = addContent(reviewList);


        // Create scrollpane and add VBox content
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(contentVBox);



        // Configure Page VBox
        pageVerticalBox.getChildren().addAll(heading, scrollPane);


        // Configure scene
        Scene userPostScene = new Scene(pageVerticalBox, 600, 600);
        Stage newStage = new Stage();
        newStage.setScene(userPostScene);
        newStage.setTitle("List Reviews Page");
        newStage.show();
    }

    /**
     * Displays all the reviews given an AnswersList Object. 
     * 
     * @param reviewList
     * @return VBox Contains all UI components for neatly displaying information. 
     */
    public VBox addContent(AnswersList reviewList){
        VBox contentVBox = new VBox();

       
        for(int i = 0; i < reviewList.getSize(); i++){
        	Answer currentReview = reviewList.getAnswerFromIndex(i);
        	HBox contentBox = new HBox();

                Label textLabel = new Label(currentReview.getAnswerText());

                contentBox.getChildren().addAll(textLabel);

                contentVBox.getChildren().add(contentBox);
                System.out.println("Adding Review to Display: " + i);
           
        }

        return contentVBox;
    }


}

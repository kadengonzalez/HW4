package databasePart1;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import java.sql.SQLException;
import databasePart1.*;
import javafx.scene.layout.Priority;
import java.util.ArrayList;
import javafx.scene.layout.Region;


/**
 * Creates the page for allowing students to create reviews of reviewers. 
 * Includes assigning scores and selecting if they are trusted. 
 */
public class ReviewerRatingPage {

    private final DatabaseHelper databaseHelper;

    /**
	 * Constructor for the ReviewerRatingPage.
	 * 
	 * @param databaseHelper This passes the databaseHelper to the page to let it access the database. 
	 */
    public ReviewerRatingPage(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
    
    /**
     * Responsible for creating the page for allowing students to create reviews for reviewers 
     * including giving them a score (increasing/decreasing) and selecting whether they are 
     * trusted or not. Calls necessary functions. 
     * 
     * @param primaryStage Passes the current stage being used.
     * @param user Passes the current user object.
     */
    public void show(Stage primaryStage, User user){
        // ************************************************** Create Page Layout ******************************************************************
    	DisplayUtilities displayUtilities = new DisplayUtilities();
        // Create page layout
        Stage newStage = new Stage();
        VBox mainVBox = new VBox(10);
        
        //Back button to return to student homepage
        Button back = new Button("Back");
        back.setOnAction( b-> {
        	new StudentHomePage(databaseHelper).show(primaryStage, user);
        });
        


        // ******************************************** Create the TOP half of the Page ***********************************************************
        // VBox for all TOP components
        VBox topVBox = new VBox(); 
        
        //Vbox for Top Banner
        VBox veryTop = new VBox();
        // Heading Label
        Label topLabel = new Label("Reviewers that are reviewed: "); 
        veryTop.getChildren().addAll(back, topLabel);

        // Creating ScrollPane
        VBox topContentBox;
        if(databaseHelper.ratingListCount(user.getUserName()) > 0) {
        	ArrayList<ReviewerRating> ratingList = databaseHelper.loadRatingList(user.getUserName(), databaseHelper);
        	topContentBox = setUpReviewedVBox(ratingList, primaryStage, user);
        } else {
        	topContentBox = new VBox();  
            Label noneMessage = new Label("Rate your First Reviewer!");
            topContentBox.getChildren().add(noneMessage);
        }
        

        ScrollPane topScrollPane = new ScrollPane(); // ScrollPane for showing information
        topScrollPane.setContent(topContentBox);

        VBox.setVgrow(topScrollPane, Priority.ALWAYS);
        topVBox.getChildren().addAll(veryTop, topScrollPane);





        // ****************************************** Create the BOTTOM half of the Page **********************************************************
        VBox bottomVBox = new VBox();

        Label bottomLabel = new Label("Reviewers not reviewed: ");

    
        VBox bottomContentBox;
        ArrayList<String> unreviewedList = databaseHelper.notYetRatedList(user.getUserName(), databaseHelper);
        if(unreviewedList.size() > 0){
           bottomContentBox = setUpNotReviewedVBox(unreviewedList, primaryStage, user, databaseHelper);
        } else {
            bottomContentBox = new VBox();  
            Label noneMessage = new Label("All Reviewers Currently have a Rating");
            bottomContentBox.getChildren().add(noneMessage);
        }              

        // bottomContentBox.getChildren().addAll(tempLabel4, tempLabel5, tempLabel6);

        // Creating ScrollPane
        ScrollPane bottomScrollPane = new ScrollPane();
        bottomScrollPane.setContent(bottomContentBox);

        VBox.setVgrow(bottomScrollPane, Priority.ALWAYS);
        bottomVBox.getChildren().addAll(bottomLabel, bottomScrollPane);



        // ************************************************ Finish Configuring Page ****************************************************************

        // Add elements to mainVBox
        VBox.setVgrow(topVBox, Priority.ALWAYS);
        VBox.setVgrow(bottomVBox, Priority.ALWAYS);
        
        Line partingLine = createLine(mainVBox);
        
        mainVBox.getChildren().addAll(topVBox, partingLine, bottomVBox);

        // Display and configure page
        Scene ratingScene = new Scene(mainVBox, 800, 600);
        //ratingScene.getStylesheets().add(getClass().getResource("/databasePart1/style.css").toExternalForm());
        primaryStage.setScene(ratingScene);
        primaryStage.setTitle("Reviewer Rating Page");
        primaryStage.show();

    }

    /**
     * Refreshes the current page to correctly reflect any changes. 
     * 
     * @param primaryStage Passes the current stage being used.
     * @param user Passes the current user object.
     */
    public void refreshPage(Stage primaryStage, User user){
        ReviewerRatingPage reviewerRatingPage = new ReviewerRatingPage(databaseHelper);
        reviewerRatingPage.show(primaryStage, user);
    }

    
    /** 
     * Puts all userName's inside ratingList into VBox. Contains necessary components
     * to allow user to increase/decrease reviewers score and select whether they 
     * are trusted. Performs this for every reviewer name in list. 
     * 
     * @param ratingList List of of strings containing reviewer userNams.
     * @param primaryStage Passes the current stage being used.
     * @param user Passes the current user object.
     * @return VBox This will contain all components for each userName in ratingList.
    */
    public VBox setUpReviewedVBox(ArrayList<ReviewerRating> ratingList, Stage primaryStage, User user) {
        DisplayUtilities displayUtilities = new DisplayUtilities();


    	VBox tempVBox = new VBox();

        for (ReviewerRating reviewerRating : ratingList){ // fix arraylist to have actual java
            HBox mainHBox = new HBox(10);

            // Name Components
            Label nameLabel = new Label(reviewerRating.getReviewerName());

            Region spacer1 = new Region();

            // Score Components

            Label score = new Label("Score: " + reviewerRating.getScore());

            Button increaseScore = new Button("\u2191");
            increaseScore.setUserData(reviewerRating);
            increaseScore.setOnAction(a->{
                ReviewerRating tempReview = (ReviewerRating) increaseScore.getUserData();
                if(tempReview.getScore() < 10){
                    tempReview.updateScore(1, user.getUserName());
                    refreshPage(primaryStage, user);
                }
            });

            Button decreaseScore = new Button("\u2193");
            decreaseScore.setUserData(reviewerRating);
            decreaseScore.setOnAction(b->{
                ReviewerRating tempReview = (ReviewerRating) decreaseScore.getUserData();
                if(tempReview.getScore() > 0){
                    tempReview.updateScore(-1, user.getUserName());
                    refreshPage(primaryStage, user);
                }
            });            

            Region spacer2 = new Region();

            // Trusted Components
            Label trustedLabel = new Label("Trusted?");

            Button isTrustedButton = new Button("Yes");
            isTrustedButton.setUserData(reviewerRating);
            isTrustedButton.setOnAction(c->{
                ReviewerRating tempReview = (ReviewerRating) isTrustedButton.getUserData();
                if(!tempReview.getIsTrusted()){
                	tempReview.updateTrusted(true, user.getUserName());
                    refreshPage(primaryStage, user);
                }
                // set Boolean TRUE
            });

            Button isNotTrustedButton = new Button("No");
            isNotTrustedButton.setUserData(reviewerRating);
            isNotTrustedButton.setOnAction(d->{
                ReviewerRating tempReview = (ReviewerRating) isNotTrustedButton.getUserData();
                if(tempReview.getIsTrusted()){
                	tempReview.updateTrusted(false, user.getUserName());
                    refreshPage(primaryStage, user);
                }
                // set Boolean FALSE
            });
            if(reviewerRating.getIsTrusted()){
                displayUtilities.setButtonGreen(isTrustedButton);
            } else {
                displayUtilities.setButtonRed(isNotTrustedButton);
            }


            // Add all components
            mainHBox.getChildren().addAll(nameLabel, spacer1, increaseScore, decreaseScore, score, spacer2, trustedLabel, isTrustedButton, isNotTrustedButton);

            tempVBox.getChildren().add(mainHBox);
        }
    	
    	return tempVBox;
    }
    
    // Not having a review yet
    /**
     * Creates and returns a VBox containing each userName inside ratingList and 
     * allows user to create a review for a reviewer that does NOT have a review yet. 
     * 
     * @param ratingList List of of strings containing reviewer userNams.
     * @param primaryStage Passes the current stage being used.
     * @param user Passes the current user object.
     * @param databaseHelper Allows function to access the database. 
     * @return VBox This will contain all components for each userName in ratingList.
     */
    public VBox setUpNotReviewedVBox(ArrayList<String> ratingList, Stage primaryStage, User user, DatabaseHelper databaseHelper){
    	DisplayUtilities displayUtilities = new DisplayUtilities();

        VBox tempVBox = new VBox();

        for (String reviewerName : ratingList){
            // Name Components
            HBox mainHBox = new HBox(10);

            Label nameLabel = new Label(reviewerName);

            Region spacer1 = new Region();

            
            
            // Enter Button
            Button enterButton = new Button("Click to move to Reviewable Section");
            enterButton.setUserData(reviewerName);
            enterButton.setOnAction(e->{
                String newReviewerName = (String) enterButton.getUserData();
                ReviewerRating newReviewerRating = new ReviewerRating(newReviewerName, 5, false, databaseHelper);
                databaseHelper.addNewRating(user.getUserName(), newReviewerRating);
                ReviewerRatingPage reviewerRatingPage =  new ReviewerRatingPage(databaseHelper);
                reviewerRatingPage.show(primaryStage, user);
                // USE currentScore and isTrusted to create the Review and add it to the database
            });

            // Add everything
            mainHBox.getChildren().addAll(nameLabel, spacer1, enterButton);

            tempVBox.getChildren().add(mainHBox);
        }
    	
    	return tempVBox;

    }

    /**
     * Creats a line spanning entire length of given VBox. 
     * 
     * @param mainVBox Used to determine length of line. 
     * @return Line Line object will be correctly formated with color and length. 
     */
    public Line createLine(VBox mainVBox) {
    	Line line = new Line();

    	line.setStrokeWidth(2);
    	line.setStartX(0);
        line.endXProperty().bind(mainVBox.widthProperty());
        line.setStartY(0);
        line.setEndY(0);
		line.setStyle("-fx-stroke: black;");

    	return line;
    }
    
    
}
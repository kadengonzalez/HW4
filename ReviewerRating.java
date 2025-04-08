package databasePart1;
import java.sql.SQLException;
import databasePart1.DatabaseHelper;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Creates a system to assign a rating to each reviewer.
 */
public class ReviewerRating {


	private String reviewerName;
	private int score;
	private Boolean isTrusted;
	
	private final DatabaseHelper databaseHelper;

	public ReviewerRating(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    /**
     * Constructor to initialize a new ReviewerRating
     *
     * @param reviewerName String containing the reviewerName. 
     * @param score Integer containing reviewers score. 
     * @param isTrusted Boolean containing whether the user is trusted.
     * @param databaseHelper DatabaseHelper object setting the database. 
     */
	public ReviewerRating(String reviewerName, int score, Boolean isTrusted, DatabaseHelper databaseHelper){
		this.reviewerName = reviewerName;
		this.score = score;
		this.isTrusted = isTrusted;
		this.databaseHelper = databaseHelper;
	}
	
	// ********************************** Getters **********************************

    /**
     * Returns the username of the reviewer as a String. 
     * @return Returns a String. 
     */
    public String getReviewerName() { return this.reviewerName; }

    /**
     * Returns the reviewer's score as an int. 
     * @return Returns an int.
     */
    public int getScore() { return this.score; }

    /**
     * Returns whether a reviewer is trusted as a boolean. 
     * @return Returns a boolean. 
     */
    public Boolean getIsTrusted() { return this.isTrusted; }

	// ********************************** Setters **********************************
    	/**
     	* Sets a Reviewer's name. 
     	* @param name Requires a string containing the reviewer's name.
     	*/
    public void setReviewerName(String name) { this.reviewerName = name; }

	/**
     	* Sets if a reviewer's score. 
     	* @param score Requires an int containing the score value.
     	*/
    public void setScore(int score) { this.score = score; }

	/**
     	* Sets if a reviewer is trusted. 
     	* @param bool Requires a boolean containing the isTrusted value.
     	*/
    public void setIsTrusted(Boolean bool) { this.isTrusted = bool; }
	
       /**
     	* Updates the current score number. 
     	* @param num Requires an int containing the score to add.
        * @param userName Requires a string with the reviewers name.
     	*/
	public void updateScore(int num, String userName){
		setScore(getScore() + num);
		databaseHelper.updateRatingScore(userName, getReviewerName(), getScore());
	}

	/**
     	* Updates if a reviewer is trusted. 
     	* @param bool Requires a boolean containing the new isTrusted value.
        * @param userName Requires a string with the reviewers name.
     	*/
	public void updateTrusted(Boolean bool, String userName){
		setIsTrusted(bool);
		databaseHelper.updateIsTrusted(userName, getReviewerName(), getIsTrusted());
	}
}

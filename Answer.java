package databasePart1;

/**
 * Creates the Answer class that contains all necessary data types for Answer Objects to function.
 * Contains setters and getters for necessary variables. 
 */
public class Answer {
	
    private int id; // The ID will help with Creation, Reading, Updating, and Deleting an Answer
	private int questionLinkID;
	private int answerLinkID; // This will be used to create subanswers and replies
	private int level = -1; // This will store if the answer is a reply to a question or an answer (or any subset of an answer)

    private String userName;
    private String answerText;
	private String highestRole; // This is for sorting the answers based on rank
	
	private Boolean hideUserName; // Make an Answer anonymous

    private Boolean isReview; // Signifies if an Answer is a review or not 
	
    
    /**
     * Constructor to initialize a new Answer object
     *
     * @param userName String containing the userName. 
     * @param answerText String containing the answer text. 
     * @param questionLinkID Int containing the questionLinkID.
     * @param hideUserName Boolean for whether it should be anonymous. 
     * @param answerLinkID Int containing the answerLinkID.
     * @param level Int containing the level for nesting level.
     */
    public Answer(String userName, String answerText, int questionLinkID, Boolean hideUserName, int answerLinkID, int level) {
        this.userName = userName;
        this.answerText = answerText;
        this.questionLinkID = questionLinkID;
        this.hideUserName = hideUserName;
        this.answerLinkID = answerLinkID;
        this.level = level;
        isReview = false; 
    }
    
    
    /**
     * Constructor for creating of an answer FOR AFTER it is already in the database,
     * this is for taking it out of the database and creating the answer objects from the stored info.
     * 
     * @param id Id number as an int. 
     * @param userName String containing the userName. 
     * @param answerText String containing the answer text. 
     * @param questionLinkID Int containing the questionLinkID.
     * @param hideUserName Boolean for whether it should be anonymous. 
     * @param answerLinkID Int containing the answerLinkID.
     * @param level Int containing the level for 
     * @param highestRole String containing the highestRole of user creating Answer. 
     */
    public Answer(int id, String userName, String answerText, int questionLinkID, Boolean hideUserName, int answerLinkID, int level, String highestRole) {
    	this.id = id;
        this.userName = userName;
        this.answerText = answerText;
        this.questionLinkID = questionLinkID;
        this.hideUserName = hideUserName;
        this.answerLinkID = answerLinkID;
        this.level = level;
        this.highestRole = highestRole;
        isReview = false; 
    }
    
    // ********************************** Getters **********************************
    /**
     * Returns the id number as an int. 
     * @return Returns an int.
     */
    public int getId() { return this.id; }
    
    /**
     * Returns the questionLinkID as an int. 
     * @return Returns an int. 
     */
    public int getQuestionLinkID() { return this.questionLinkID; }
    /**
     * Returns the answerLinkID as an int. 
     * @return Returns an int. 
     */
    public int getAnswerLinkID() { return this.answerLinkID; }
    /**
     * Returns the level number as an int. 
     * @return Returns an int. 
     */
    public int getLevel() { return this.level; }

    /**
     * Returns the username of the answer as a String. 
     * @return Returns a String. 
     */
    public String getUserName() { return this.userName; }
    /**
     * Returns the answerText as a String. 
     * @return Returns a String. 
     */
    public String getAnswerText() { return this.answerText; }  
    /**
     * Returns the highestRole as a String. 
     * @return Returns a String. 
     */
    public String getHighestRole() { return this.highestRole; } 
    
    /**
     * Returns whether the Answer is anonymous (hide username)
     * @return Returns a boolean. 
     */
    public Boolean getHideUserName() { return this.hideUserName; }
    /**
     * Returns whether the Answer is a review or not.
     * @return Returns a boolean. 
     */
    public Boolean getIsReview() { return this.isReview; }

    
    // ********************************** Setters **********************************
    /**
     * Updates the current ID number. 
     * @param id Requires an int containing the Id.
     */
    public void setQuestionLinkID(int id) { this.questionLinkID = id; }
    /**
     * Updates answerLinkID. 
     * @param id Requires an int containing the Id.
     */
    public void setAnswerLinkID(int id) { this.answerLinkID = id; }
    /**
     * Updates the level of answer.
     * @param level Requires an int containing the level number. 
     */
    public void setLevel(int level) { this.level = level; }
    
    /**
     * Updates the answerText.
     * @param text Requires String containing text. 
     */
    public void setAnswerText(String text) { this.answerText = text; }
    /**
     * Updates the highest role. 
     * @param text Requires String containing text. 
     */
    public void setHighestRole(String text) { this.highestRole = text; }
    
    /**
     * Updates whether the userName should be hidden (anonymous) 
     * @param bool Requires boolean value. 
     */
    public void setHideUserName(Boolean bool) { this.hideUserName = bool; }
    
    /**
     * Sets the answer to be a review. 
     */
    public void setIsReview() { this.isReview = true; } 
}

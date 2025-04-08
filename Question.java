package databasePart1;

/**
 * Creates the Question class that contains all necessary data types for Question objects to function.
 * Contains setters and getters for all necessary variables. 
 */

public class Question {
		
	//Id will be set from Server side, when it is inserted into the table
    private int id; //THis will help with Creation, Reading, Updateing, BUT especially Deleting
    private String title;
    private String questionText;
    private String userName;
    private Boolean resolved;
    private Boolean isSubQuestion;
    private int parentQuestionLinkId;
    private Boolean hideUserName; //make question anonomous
    private int preferredAnswerId = 0; //Someone like an admin can select an answer to put here

    
    /**
     * Constructor to initialize a new Question object
     *
     * @param title String containing the title.
     * @param question String containing the question.
     * @param userName String containing the userName. 
     * @param resolved Boolean that states whether a question is resolved or not.
     * @param isSubQuestion Boolean for whether the question is a sub question.
     * @param parentQuestionLinkID Integer containing the parents QuestionLinkID.
     * @param hideUserName Boolean for whether it should be anonymous. 
     * @param preferredAnswerId Integer with the preferred answers ID.
     */
    public Question(String title, String question, String userName, Boolean resolved, Boolean isSubQuestion, int parentQuestionLinkId, Boolean hideUserName, int preferredAnswerId) {
        this.userName = userName;
        this.title = title;
        this.questionText = question;
        this.hideUserName = hideUserName;
        this.preferredAnswerId = 0;
        this.resolved = resolved;
        this.isSubQuestion = isSubQuestion;
        this.parentQuestionLinkId = parentQuestionLinkId;
        this.preferredAnswerId = preferredAnswerId;
    } 
   
    /**
     * Constructor for creating of a question FOR AFTER it is already in the database,
     * this is for taking it out of the database and creating the question objects from the stored info.
     *
     * @param id Integer with the question's id number.
     * @param title String containing the title.
     * @param question String containing the question.
     * @param userName String containing the userName. 
     * @param resolved Boolean that states whether a question is resolved or not.
     * @param isSubQuestion Boolean for whether the question is a sub question.
     * @param parentQuestionLinkID Integer containing the parents QuestionLinkID.
     * @param hideUserName Boolean for whether it should be anonymous. 
     * @param preferredAnswerId Integer with the preferred answers ID.
     */
    public Question(int id, String title, String question, String userName, Boolean resolved, Boolean isSubQuestion, int parentQuestionLinkId, Boolean hideUserName, int preferredAnswerId) {
        this.id = id;
    	this.userName = userName;
        this.title = title;
        this.questionText = question;
        this.hideUserName = hideUserName;
        this.preferredAnswerId = 0;
        this.resolved = resolved;
        this.isSubQuestion = isSubQuestion;
        this.parentQuestionLinkId = parentQuestionLinkId;
        this.preferredAnswerId = preferredAnswerId;
    } 
    
    // ********************************** Getters **********************************
    /**
     * Returns the id number as an int. 
     * @return Returns an int.
     */
    public int getId() { return this.id; }

    /**
     * Returns the title of the question. 
     * @return Returns a String. 
     */
    public String getTitle() { return this.title; }

    /**
     * Returns the question as a String. 
     * @return Returns a String. 
     */
    public String getQuestionText() { return this.questionText; }

    /**
     * Returns the userName of the question as a String. 
     * @return Returns a String. 
     */
    public String getUserName() { return this.userName; }

    /**
     * Returns whether a question has been resolved or not, as a boolean. 
     * @return Returns a boolean. 
     */
    public Boolean getResolved() { return this.resolved; } 

    /**
     * Returns whether an question is a sub-question or not, as a boolean. 
     * @return Returns a boolean. 
     */
    public Boolean getIsSubQuestion() { 
    	if(this.isSubQuestion == null) {
    		return false;
    	} else { 
    		return this.isSubQuestion;
    	}
    }
	
    /**
     * Returns the parent question's ID as an int. 
     * @return Returns an int.
     */
    public int getParentQuestionLinkId() {return this.parentQuestionLinkId; }

    /**
     * Returns whether the question is anonymous (hide username)
     * @return Returns a boolean. 
     */
    public Boolean getHideUserName() { return this.hideUserName; }

    /**
     * Returns the preferred answer's ID as an int. 
     * @return Returns an int.
     */
    public int getPreferredAnswerId() { 
    	return this.preferredAnswerId;
    }

    
    // ********************************** Setters **********************************
    /**
     * Updates whether the userName should be hidden (anonymous) 
     * @param bool Requires boolean value. 
     */
    public void setHideUserName(Boolean bool) { this.hideUserName = bool; }

    /**
     * Updates the preferred answer to a question. 
     * @param answerId Requires int value containing the answer's ID. 
     */
    public void setPreferredAnswerId(int answerId) { this.preferredAnswerId = answerId;}

    /**
     * Updates the parent Question. 
     * @param parentQuestionID Requires int value with the parent question's ID. 
     */
    public void setParentQuestionLinkId(int parentQuestionID) { this.parentQuestionLinkId = parentQuestionID;}

    /**
     * Updates whether the question has been resolved. 
     * @param bool Requires boolean value. 
     */
    public void setResolved(Boolean bool) { this.resolved = bool; }

    /**
     * Updates whether the question is a sub-question. 
     * @param bool Requires boolean value. 
     */
    public void setIsSubQuestion(Boolean bool) { this.isSubQuestion = bool; }

    /**
     * Updates the question text.
     * @param text Requires String containing text. 
     */
    public void setQuestionText(String text) { this.questionText = text; }
	
    /**
     * Updates the question's title.
     * @param text Requires String containing text. 
     */
    public void setTitleText(String text) { this.title = text; }
    
    
}

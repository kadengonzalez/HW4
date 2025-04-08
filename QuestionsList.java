package databasePart1;

import java.util.LinkedList;

import databasePart1.DatabaseHelper;

/**
 * Creates QuestionsList class and all needed variables. 
 * Creates needed functionality to store list of Questions. 
 */
public class QuestionsList {
	
	public DatabaseHelper databaseHelper;
	
    public LinkedList<Question> questionsList;
    
    
    /**
     * Constructor that creates an instance of QuestionsList. 
     */
    public QuestionsList() {
    	this.questionsList = new LinkedList<Question>();
    }
    
    
    /**
     * Returns an Question Object corresponding to given Question ID. 
     * 
     * @param id Int containing the ID of Question. 
     * @return Returns the corresponding Question Object. 
     */
    public Question getQuestionFromId(int id) { 
    	// Loop through QuestionsList
    	for(int i=0; i<questionsList.size(); i++) {
    		// Check each index if it matches ID
    		if(questionsList.get(i).getId() == id) {
    			// If Question has matching ID return it
    			return questionsList.get(i);
    		}
    	}
    	// Return null if Question Object was not found
		return null;
    }
    
    
    /**
     * Returns the size of the QuestionsList.
     * @return int
     */
    public int getSize() {
    	// Check if list is empty
    	if (questionsList == null) { 
    		// Return 0 if empty
    		return 0; 
    	} else { 
    		// If list not empty, return the size
    		return this.questionsList.size();
    	}
    }
    
    
    /**
     * This function allows a question object to be added to the QuestionsList object.
     * Primarily used For when the database loads Questions back in to a QuestionsList 
     * and then sends that info to the ViewDiscussions page.
     * 
     * @param question Takes a Question Object. 
     */
    public void loadQuestion(Question question) {
    	questionsList.add(question);
    }
    
    
    /**
     * Returns a Question Object given an index.
     * 
     * @param index Integer index number.
     * @return Returns Question Object. 
     */
    public Question getQuestionFromIndex(int index) {
    	// Loop through QuestionsList
    	if (questionsList == null) {
    		// If empty, return null
    		return null;
    	} else {
    		// If list not empty, return the index
    		return this.questionsList.get(index);
    	}
    }
    
    
    /**
     * Adds a question to QuestionsList and the Database.
     * 
     * @param databaseHelper2 DatabaseHelper object creating the database to store the questions.
     * @param question Question object containing the new question. 
     */
    public void addQuestionBothPlaces(DatabaseHelper databaseHelper2, Question question) {
    	// Add Question to QuestionsList
    	questionsList.addLast(question);
    	
    	// Add Question to the Database
    	databaseHelper2.addNewQuestion(question);
    }
    

    /**
     * Adds a sub-question to another question.
     * 
     * @param newQuestion Question object containing the new question. 
     */

    public void addSubQuestion(Question newQuestion) {
    	// Add Question to QuestionsList
    	questionsList.addLast(newQuestion);
    	
    	// Add Question to the Database
    	databaseHelper.addNewQuestion(newQuestion);
    }
    
    
    // Clear the entire list
    /**
     * Returns boolean as to whether list was cleared. 
     * NOT a destructor. 
     * 
     * @return Boolean value. 
     */
    public boolean deleteList() {
    	// Ensure list is not empty
    	if(questionsList.size() > 0) {
    		// Clear if not empty
    		questionsList.clear();
    		// Return ersult to user
    		return true;
    	} else {
    		// Return false if it was empty or failed to clear list
    		return false;
    	}
	}
    
}

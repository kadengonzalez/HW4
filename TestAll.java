package databasePart1;

import static org.junit.Assert.*;

//RUN WITH THE DATABASE RESETTING EACH TIME (meaning run with line 41 un commented)

import java.sql.SQLException;

import databasePart1.*;
import org.junit.Test;

public class TestAll {

	private static final DatabaseHelper databaseHelper = new DatabaseHelper();
	public int index = 0;

    public TestAll() {
    	try {
			databaseHelper.connectToDatabase();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Connect to the database
    }
    
    //TEST CRUD FOR QUESTIONS==================================================================
    @Test
    public void createQuestion() {
    	Question question = new Question("Test Question", "Question" , "RandomUserName", false, false, 0, false, 0 );
    	//Test if Question was Created
    	assertTrue("Question was not added to Database", databaseHelper.addNewQuestion(question));
    }
    
    @Test
    public void readQuestion() throws InterruptedException {
    	//Load new question, and read it from the database
    	Question question2 = new Question("Test Question2", "Question2" , "RandomUserName", false, false, 0, false, 0 );
    	databaseHelper.addNewQuestion(question2);
    	QuestionsList questionsList = databaseHelper.loadQuestionsList();
    	assertTrue("Question2 from Database was not readable", questionsList.getQuestionFromIndex(0).getQuestionText().equals("Question2"));
    }
    @Test
    public void updateQuestion() throws SQLException {
    	//Test if we can change the questionText from Question33 to Question3
    	Question question3 = new Question("Test Question3", "Question33" , "RandomUserName", false, false, 0, false, 0 );
    	databaseHelper.addNewQuestion(question3);
    	//pull the list of questions from the database
    	QuestionsList questionsList = databaseHelper.loadQuestionsList();
    	Question questionUpdated = questionsList.getQuestionFromIndex(0);
    	//Updating the QuestionText
    	questionUpdated.setQuestionText("Question3");
    	assertTrue("Question3 text was not updated", databaseHelper.updateQuestion(questionUpdated));
    	
    }
    @Test
    public void deleteQuestion() throws SQLException {
    	Question question4 = new Question(1,"Test Question4", "Question4" , "RandomUserName", false, false, 0, false, 0 );
    	databaseHelper.addNewQuestion(question4);
    	assertTrue("Question4 was not deleted", databaseHelper.deleteQuesiton(1));
    	
    }
    @Test
    public void searchForQuestion() throws InterruptedException {
    	for(int i=0; i<10; i++) {
    		//Load a new random question into the database with a set incrementing index number
    		Question question = new Question("Test Question4", String.valueOf(i), "RandomUserName", false, false, 0, false, 0 );
    		databaseHelper.addNewQuestion(question);
    		Thread.sleep(200);
    	}
    	Thread.sleep(300);
    	//pull the questionslist from the database
    	QuestionsList questionsList = databaseHelper.loadQuestionsList();
    	//Search for many questions
    	for(int i=0; i<10;i++) {
    		boolean test = false;
    		//if the index of this for loop matches the index value in the question obj, it's good
    		if(questionsList.getQuestionFromIndex(i).getQuestionText().equals(String.valueOf(i))) {
    			test = true;
    		}
    		assertTrue("Question "+i+" was not found", test);
    	}
    }
    
    //TEST CRUD FOR ANSWERS ==============================================================================
    @Test
    public void createAnswer() {
    	//create answer
    	Answer answer = new Answer("username", "Answer", 0, false, 0, 0);
    	//Test if Answer was Created
    	assertTrue("Answer was not added to Database", databaseHelper.addNewAnswer(answer));
    }
    
    @Test
    public void readAnswer() throws InterruptedException {
    	//Load new answer, and read it from the database
    	Answer answer = new Answer("username", "AnswerRead", 0, false, 0, 0);
    	databaseHelper.addNewAnswer(answer);
    	AnswersList answersList = databaseHelper.loadAnswersList();
    	assertTrue("Answer from Database was not readable", answersList.getAnswerFromIndex(0).getAnswerText().equals("AnswerRead"));
    }
    @Test
    public void updateAnswer() throws SQLException {
    	//Test if we can change the AnswerText from AnswerUPDATE to answer
    	Answer answer = new Answer("username", "AnswerUPDATE", 0, false, 0, 0);
    	databaseHelper.addNewAnswer(answer);
    	//pull the list of answers from the database
    	AnswersList answersList = databaseHelper.loadAnswersList();
    	Answer answerUpdated = answersList.getAnswerFromIndex(0);
    	//Updating the AnswerText
    	answerUpdated.setAnswerText("answer");
    	assertTrue("Answer text was not updated", databaseHelper.updateAnswer(answerUpdated));
    	
    }
    @Test
    public void deleteAnswer() throws SQLException {
    	Answer answer = new Answer(1, "username", "AnswerDelete", 0, false, 0, 0, "admin");
    	databaseHelper.addNewAnswer(answer);
    	assertTrue("Answer was not deleted", databaseHelper.deleteAnswer(1));
    }
    @Test
    public void searchForAnswer() throws InterruptedException {
    	for(int i=0; i<10; i++) {
    		Answer answer = new Answer("username", String.valueOf(i), 0, false, 0, 0);
    		databaseHelper.addNewAnswer(answer);
    		Thread.sleep(100);
    	}
    	Thread.sleep(300);
    	AnswersList answersList = databaseHelper.loadAnswersList();
    	//Search for many of the answers in the answersList from the database
    	for(int i=0; i<10;i++) {
    		boolean test = false;
    		if(answersList.getAnswerFromIndex(i).getAnswerText().equals(String.valueOf(i))) {
    			test = true;
    		}
    		assertTrue("Answer "+i+" was not found", test);
    	}
    }
    
    //CRUD FOR QuestionsList========================================================================
    @Test
    public void createQuestionsList() throws InterruptedException {
    	for(int i=0; i<2; i++) {
    		//Load a new random question into the database with a set incrementing index number
    		Question question = new Question("Test Question4", String.valueOf(i), "RandomUserName", false, false, 0, false, 0 );
    		databaseHelper.addNewQuestion(question);
    		Thread.sleep(100);
    	}
    	Thread.sleep(100);
    	
    	//create a new questions List to compare to what the database returns
    	QuestionsList questionsList = new QuestionsList();
    	boolean test = false;
    	//see if they are both the same class
    	if(questionsList.getClass() == databaseHelper.loadQuestionsList().getClass()) {
    		test = true;
    	}
    	assertTrue("QuestionsList is not created", test);

    }
    @Test
    public void readQuestionsList() throws InterruptedException {
    	for(int i=0; i<2; i++) {
    		//Load a new random answer into the database with a set incrementing index number
    		Answer answer = new Answer("username", String.valueOf(i), 0, false, 0, 0);
    		databaseHelper.addNewAnswer(answer);
    		Thread.sleep(100);
    	}
    	
    	//pull a new QuestionsList
    	QuestionsList questionsList = databaseHelper.loadQuestionsList();
    	for(int i=0; i<questionsList.getSize(); i++) {
    		boolean test = false;
    		if(questionsList.getQuestionFromIndex(i).getQuestionText().equals(String.valueOf(i))) {
    			test = true;
    		}
    		assertTrue("Question "+i+"Not Read", test);
    	}
    }
    @Test
    public void updateQuestionsList() {
    	Question question = new Question("Test Question4", "Epic", "RandomUserName", false, false, 0, false, 0 );
    	QuestionsList questionsList = new QuestionsList();
    	questionsList.loadQuestion(question);
    	assertTrue("This questionsList was not updated", (questionsList.getSize()>0));
    	
    }
    @Test
    public void deleteQuestionsList() {
    	Question question = new Question("Test Question4", "Epic", "RandomUserName", false, false, 0, false, 0 );
    	QuestionsList questionsList = new QuestionsList();
    	questionsList.loadQuestion(question);
    	assertTrue("This questionsList was cleared", questionsList.deleteList());
    	
    }
    @Test
    public void searchForQuestionsList() throws InterruptedException {
    	QuestionsList questionsList = new QuestionsList();
    	for(int i=0; i<10; i++) {
    		//Load a new random question into the database with a set incrementing index number
    		if(i != 7) {
    			Question question = new Question("Test Question4", String.valueOf(i), "RandomUserName", false, false, 0, false, 0 );
    			questionsList.loadQuestion(question);
    		}
    		else {
    			Question question = new Question("Test Question4","Special Question", "RandomUserName", false, false, 0, false, 0 );
    			questionsList.loadQuestion(question);
    		}
    		Thread.sleep(100);
    	}
    	
    	boolean found = false;
    	for(int i=0; i<questionsList.getSize();i++) {
    		//Search for the Special Question we loaded earlier
    		if(questionsList.getQuestionFromIndex(i).getQuestionText().equals("Special Question")) {
    			found = true;
    		}
    	}
    	assertTrue("Special Question was not Found", found);
    }
    
    //CRUD FOR ANSWERSLIST====================================================================================
    @Test
    public void createAnswersList() throws InterruptedException {
    	for(int i=0; i<2; i++) {
    		//Load a new random answer into the database with a set incrementing index number
    		Answer answer = new Answer("username", String.valueOf(i), 0, false, 0, 0);
    		databaseHelper.addNewAnswer(answer);
    		Thread.sleep(100);
    	}
    	Thread.sleep(100);
    	
    	//create a new Answers List to compare to what the database returns
    	AnswersList answersList = new AnswersList();
    	boolean test = false;
    	//see if they are botht the same class
    	if(answersList.getClass() == databaseHelper.loadAnswersList().getClass()) {
    		test = true;
    	}
    	assertTrue("AnswersList is not created", test);
    }
    @Test
    public void readAnswersList() throws InterruptedException {
    	for(int i=0; i<2; i++) {
    		//Load a new random answer into the database with a set incrementing index number
    		Answer answer = new Answer("username", String.valueOf(i), 0, false, 0, 0);
    		databaseHelper.addNewAnswer(answer);
    		Thread.sleep(100);
    	}
    	
    	//pull a new AnswersList
    	AnswersList answersList = databaseHelper.loadAnswersList();
    	for(int i=0; i<answersList.getSize(); i++) {
    		boolean test = false;
    		if(answersList.getAnswerFromIndex(i).getAnswerText().equals(String.valueOf(i))) {
    			test = true;
    		}
    		assertTrue("Answer "+i+"Not Read", test);
    	}
    }
    @Test
    public void updateAnswersList() {
    	Answer answer = new Answer("username", "answers", 0, false, 0, 0);
    	AnswersList answersList = new AnswersList();
    	answersList.loadAnswer(answer);
    	//check to see if we can update the answerslist by adding something to it.
    	assertTrue("This answersList was not updated", (answersList.getSize()>0));
    }
    @Test
    public void deleteAnswersList() {
    	Answer answer = new Answer("username", "Updatethis", 0, false, 0, 0);
    	AnswersList answersList = new AnswersList();
    	answersList.loadAnswer(answer);
    	//check if we can delete/clear the answerslist
    	assertTrue("This answersList was cleared", answersList.deleteList());
    }
    @Test
    public void searchForAnswersList() throws InterruptedException {
    	AnswersList answersList = new AnswersList();
    	for(int i=0; i<10; i++) {
    		//Load a new random answer into the database with a set incrementing index number
    		if(i != 7) {
    			Answer answer = new Answer("username", String.valueOf(i), 0, false, 0, 0);
    			answersList.loadAnswer(answer);
    		}
    		else {
    			Answer answer = new Answer("username", "Special Answer", 0, false, 0, 0);
    			answersList.loadAnswer(answer);
    		}
    		Thread.sleep(100);
    	}
    	
    	boolean found = false;
    	for(int i=0; i<answersList.getSize();i++) {
    		//Search for the Special Question we loaded earlier
    		if(answersList.getAnswerFromIndex(i).getAnswerText().equals("Special Answer")) {
    			found = true;
    		}
    	}
    	assertTrue("Special Answer was not Found", found);
    }
    @Test
    public void addToDatabase() throws InterruptedException{
    	//generate a bunch of questions
    	for(int i=0; i<100; i++) {
    		//Load a new random question into the database with a set incrementing index number
    		Question question = new Question("Test Question " + String.valueOf(i), "This is the Question Text to be honest?" + String.valueOf(i), "RandomUserName", false, false, 0, false, 0 );
    		databaseHelper.addNewQuestion(question);
    		Thread.sleep(100);
    		int id = databaseHelper.getQuestionID("This is the Question Text to be honest?" + String.valueOf(i)); 
			
			try {
				if(i<10) {
					databaseHelper.updateTags(id, true, false, false, false, false); 
				}else if (i > 10 && i < 20) {
					databaseHelper.updateTags(id, false, true, false, false, false); 
				}else if (i > 20 && i < 30) {
					databaseHelper.updateTags(id, false, false, true, false, false); 
				}else if (i > 30 && i < 40) {
					databaseHelper.updateTags(id, false, false, false, true, false); 
				}else if (i > 40) {
					databaseHelper.updateTags(id, false, false, false, false, true); 
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	//put my default admin account info in
    	User user1 = new User("admin", "Pass1!!!", "Bracken", "Admin", true, true, true, true, true, true, "epic@gmail.com");
    	try {
			databaseHelper.register(user1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//generate a bunch of users
    	for(int i=0; i<100; i++) {
    		User user = new User("user" + String.valueOf(i), "Pass1!!!", "davis" + String.valueOf(i), "user", false, true, false, false, false, true, "tacobell@gmail.com");
    		try {
				databaseHelper.register(user);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
    	}
    	
    }
}

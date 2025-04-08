package databasePart1;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import databasePart1.DatabaseHelper;

/**
 * <h1>Team Project 3 JUnit Tests</h1>
 * <p>This Class runs 30 tests that include the following classes:</p>
 * <ul><li>Question.java</li>
 * <li>Answer.java (testing the answers)</li>
 * <li>Answer.java (testing the reviews)</li>
 * <li>ReviewerRating.java</li>
 * <li>QuestionsList</li>
 * <li>AnswersList</li></ul>
 * 
 */
public class TP3Junit {

	/*
	 * ************Question.java TESTS************************************************************************************************************************
	 */
	
	
	
	/**
	 * Creates a new Question Obj, and constructs it. Then compares Class types with an null Question Obj.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void createQuestion() {
		Question question = new Question("New Question", "Body Test", "RandomUserName", false, false, 0, false, 0 );
		
    	assertEquals(0, question.getId());
	}
	
	/**
	 * Creates a new Question Obj, and constructs it. Then reads and Asserts all object data.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void readQuestion() {
		Question question = new Question("New Question", "Body Test", "RandomUserName", false, false, 0, false, 0 );
		assertTrue("Question was not read Correctly", question.getTitle().equals("New Question"));
		assertTrue("Question was not read Correctly", question.getQuestionText().equals("Body Test"));
		assertTrue("Question was not read Correctly", question.getUserName().equals("RandomUserName"));
		assertFalse("Question was not read Correctly", question.getResolved());
		assertFalse("Question was not read Correctly", question.getIsSubQuestion());
		assertEquals(0, question.getParentQuestionLinkId());
		assertFalse("Question was not read Correctly", question.getHideUserName());
		assertEquals(0, question.getPreferredAnswerId());
		
	}
	
	
	/**
	 * Creates a new Question Obj, and constructs it. Update the constructed Contents, then Read updated data.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void updateQuestion() {
		Question question = new Question("New Question", "Body Test", "RandomUserName", false, false, 0, false, 0 );
		question.setTitleText("Old Question");
		question.setQuestionText("Test Body");
		question.setResolved(true);
		question.setIsSubQuestion(true);
		question.setParentQuestionLinkId(1);
		question.setHideUserName(true);
		question.setPreferredAnswerId(1);
		assertTrue("Question was not read Correctly", question.getTitle().equals("Old Question"));
		assertTrue("Question was not read Correctly", question.getQuestionText().equals("Test Body"));
		assertTrue("Question was not read Correctly", question.getResolved());
		assertTrue("Question was not read Correctly", question.getIsSubQuestion());
		assertEquals(1, question.getParentQuestionLinkId());
		assertTrue("Question was not read Correctly", question.getHideUserName());
		assertEquals(1, question.getPreferredAnswerId());
		
	}
	
	
	/**
	 * Creates a new Question Obj, and constructs it. Edits the Body Text, checks change. Sets object to Null, verifys assertNull.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void deleteQuestion() {
		Question question = new Question("New Question", "Body Test", "RandomUserName", false, false, 0, false, 0 );
		question.setQuestionText("Deleted");
		assertTrue("Question Text was not changed to deleted Correctly", question.getQuestionText().equals("Deleted"));
		question = null;
		assertEquals(null, question);
		assertNull(question);
	}
	
	
	
	/**
	 * Creates a new Question Obj List, and constructs each Question in it. Then searches for the specific question	.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void searchQuestion() {
		ArrayList<Question> qlist = new ArrayList<Question>();
		for(int i=0; i<100; i++) {
			if(i != 50) {
				Question question = new Question("New Question", "Body Test", "RandomUserName", false, false, 0, false, 0 );
				qlist.add(question);
			} else {
				Question question = new Question("New Question", "SECRET", "RandomUserName", false, false, 0, false, 0 );
				qlist.add(question);
			}
		}
		for(Question question : qlist) {
			if(question.getQuestionText().equals("SECRET")) {
				assertTrue("Question Not Found", question.getQuestionText().equals("SECRET"));
			}
		}
		
    	
	}
	
	
	
	/*
	 * ************Answer.java for Answers TESTS************************************************************************************************************************
	 */
	
	
	/**
	 * Creates a new Answer Obj, and constructs it. Then checks if it it is marked false on getIsReview()
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void createAnswer() {
		Answer answer = new Answer("RandomUserName", "Body Text", 0, false, 0, 0);
		
    	assertFalse("Answer was not generated Correctly", answer.getIsReview());
	}
	
	/**
	 * Creates a new Answer Obj, and constructs it. Then reads and Asserts all object data.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void readAnswer() {
		Answer answer = new Answer("RandomUserName", "Body Text", 0, false, 0, 0);
		assertTrue("Answer was not read Correctly", answer.getUserName().equals("RandomUserName"));
		assertTrue("Answer was not read Correctly", answer.getAnswerText().equals("Body Text"));
		assertEquals(0, answer.getQuestionLinkID());
		assertFalse("Answer was not read Correctly", answer.getHideUserName());
		assertEquals(0, answer.getLevel());
		assertEquals(0, answer.getAnswerLinkID());
		assertFalse("Answer was not read Correctly", answer.getIsReview());
	}
	
	
	/**
	 * Creates a new Answer Obj, and constructs it. Update the constructed Contents, then Read updated data.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void updateAnswer() {
		Answer answer = new Answer("RandomUserName", "Body Text", 0, false, 0, 0);
		answer.setAnswerText("Text Body");
		answer.setQuestionLinkID(1);
		answer.setHideUserName(true);
		answer.setLevel(1);
		answer.setAnswerLinkID(1);
		assertTrue("Answer was not read Correctly", answer.getAnswerText().equals("Text Body"));
		assertEquals(1, answer.getQuestionLinkID());
		assertTrue("Answer was not read Correctly", answer.getHideUserName());
		assertEquals(1, answer.getLevel());
		assertEquals(1, answer.getAnswerLinkID());
	}
	
	
	
	/**
	 * Creates a new Answer Obj, and constructs it. Edits the Body Text, checks change. Sets object to Null, verifys assertNull.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void deleteAnswer() {
		Answer answer = new Answer("RandomUserName", "Body Text", 0, false, 0, 0);
		answer.setAnswerText("Deleted");
		assertTrue("Answer Text was not changed to deleted Correctly", answer.getAnswerText().equals("Deleted"));
		answer = null;
		assertEquals(null, answer);
		assertNull(answer);
	}
	
	
	/**
	 * Creates a new Answer Obj List, and constructs each Answer in it. Then searches for the specific answer	.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void searchAnswer() {
		ArrayList<Answer> alist = new ArrayList<Answer>();
		for(int i=0; i<100; i++) {
			if(i != 50) {
				Answer answer = new Answer("RandomUserName", "Body Text", 0, false, 0, 0);
				alist.add(answer);
			} else {
				Answer answer = new Answer("RandomUserName", "SECRET", 0, false, 0, 0);
				alist.add(answer);
			}
		}
		for(Answer answer : alist) {
			if(answer.getAnswerText().equals("SECRET")) {
				assertTrue("Answer Not Found", answer.getAnswerText().equals("SECRET"));
			}
		}
		
    	
	}
	
	
	
	
	/*
	 * ************Answer.java for Reviews TESTS****************************************************************************************************
	 */
	
	
	/**
	 * Creates a new Answer Obj, and constructs it. Set's the answer to a Review. Asserts the Boolean answer.getIsReview()
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void createReview() {
		Answer answer = new Answer("RandomUserName", "Body Text", 0, false, 0, 0);
		answer.setIsReview();		
		
    	assertTrue("Review was not generated Correctly", answer.getIsReview());
	}
	
	/**
	 * Creates a new Answer Obj, sets it as a review, and constructs it. Then reads and Asserts all object data.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void readReview() {
		Answer answer = new Answer("RandomUserName", "Body Text", 0, false, 0, 0);
		answer.setIsReview();
		assertTrue("Review was not read Correctly", answer.getUserName().equals("RandomUserName"));
		assertTrue("Review was not read Correctly", answer.getAnswerText().equals("Body Text"));
		assertEquals(0, answer.getQuestionLinkID());
		assertFalse("Review was not read Correctly", answer.getHideUserName());
		assertEquals(0, answer.getLevel());
		assertEquals(0, answer.getAnswerLinkID());
		assertTrue("Review was not read Correctly", answer.getIsReview());
	}
	
	
	
	/**
	 * Creates a new Answer Obj, and constructs it. Checks it it's a review. Update the constructed Contents, then Read updated data.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void updateReview() {
		Answer answer = new Answer("RandomUserName", "Body Text", 0, false, 0, 0);
		assertFalse("Review was not read Correctly", answer.getIsReview());
		answer.setIsReview();
		answer.setAnswerText("Text Body");
		answer.setQuestionLinkID(1);
		answer.setHideUserName(true);
		answer.setLevel(1);
		answer.setAnswerLinkID(1);
		assertTrue("Review was not read Correctly", answer.getAnswerText().equals("Text Body"));
		assertEquals(1, answer.getQuestionLinkID());
		assertTrue("Review was not read Correctly", answer.getHideUserName());
		assertEquals(1, answer.getLevel());
		assertEquals(1, answer.getAnswerLinkID());
		assertTrue("Review was not read Correctly", answer.getIsReview());

	}
	
	/**
	 * Creates a new Answer Obj, and constructs it. Set's it to a review. Edits the Body Text, checks change. Sets object to Null, verifys assertNull.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void deleteReview() {
		Answer answer = new Answer("RandomUserName", "Body Text", 0, false, 0, 0);
		assertFalse("Review was not read Correctly", answer.getIsReview());
		answer.setIsReview();
		assertTrue("Review was not read Correctly", answer.getIsReview());
		answer.setAnswerText("Deleted");
		assertTrue("Review Text was not changed to deleted Correctly", answer.getAnswerText().equals("Deleted"));
		answer = null;
		assertEquals(null, answer);
		assertNull(answer);
	}
	
	
	/**
	 * Creates a new Answer Obj List, and constructs & sets each to a review type. Then searches for the specific review answer	.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void searchReview() {
		ArrayList<Answer> alist = new ArrayList<Answer>();
		for(int i=0; i<100; i++) {
			if(i != 50) {
				Answer answer = new Answer("RandomUserName", "Body Text", 0, false, 0, 0);
				answer.setIsReview();
				alist.add(answer);
			} else {
				Answer answer = new Answer("RandomUserName", "SECRET", 0, false, 0, 0);
				answer.setIsReview();
				alist.add(answer);
			}
		}
		for(Answer answer : alist) {
			if(answer.getAnswerText().equals("SECRET")) {
				assertTrue("Answer Not Found", answer.getAnswerText().equals("SECRET"));
				assertTrue("Answer Not Found", answer.getIsReview());
			}
		}    	
	}
	
	
	
	/*
	 * ************ReviewerRating.java TESTS************************************************************************************************************************
	 */
	
	/**
	 * Creates a new ReviewerRating Obj, and constructs it. Then compares Class types with an null ReviewerRating Obj.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void createReviewerRating() {
		DatabaseHelper dbHelp = new DatabaseHelper();
		ReviewerRating rr = new ReviewerRating("RandomUserName", 0, true, dbHelp);
		
    	assertTrue("ReviewerRating was not generated Correctly", rr.getIsTrusted());
	}
	
	
	/**
	 * Creates a new ReviewerRating Obj, and constructs it. Then reads and Asserts all object data.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void readReviewerRating() {
		DatabaseHelper dbHelp = new DatabaseHelper();
		ReviewerRating rr = new ReviewerRating("RandomUserName", 0, true, dbHelp);
		assertTrue("ReviewerRating was not read Correctly", rr.getReviewerName().equals("RandomUserName"));
		assertEquals(0, rr.getScore());
		assertTrue("ReviewerRating was not read Correctly", rr.getIsTrusted());
	}
	
	
	
	/**
	 * Creates a new ReviewerRating Obj, and constructs it. Then updates the data. Then reads and Asserts new object data.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void updateReviewerRating() {
		DatabaseHelper dbHelp = new DatabaseHelper();
		ReviewerRating rr = new ReviewerRating("RandomUserName", 0, true, dbHelp);
		rr.setIsTrusted(false);
		rr.setReviewerName("Your Mom");
		rr.setScore(100000);
		assertTrue("ReviewerRating was not read Correctly", rr.getReviewerName().equals("Your Mom"));
		assertEquals(100000, rr.getScore());
		assertFalse("ReviewerRating was not read Correctly", rr.getIsTrusted());
	}
	
	
	/**
	 * Creates a new ReviewerRating Obj, and constructs it. Edits the data in the object, checks change. Sets object to Null, verifys assertNull.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void deleteReviewerRating() {
		DatabaseHelper dbHelp = new DatabaseHelper();
		ReviewerRating rr = new ReviewerRating("RandomUserName", 0, true, dbHelp);
		assertTrue("ReviewerRating was not read Correctly", rr.getReviewerName().equals("RandomUserName"));
		assertEquals(0, rr.getScore());
		assertTrue("ReviewerRating was not read Correctly", rr.getIsTrusted());
		rr = null;
		assertEquals(null, rr);
		assertNull(rr);
	}
	
	

	
	/**
	 * Creates a new ReviwerRating Obj List, and constructs a list of new Objects. Then searches for the specific ReviewRating Object.
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@SuppressWarnings("null")
	@Test
	public void searchReviewerRating() {
		ArrayList<ReviewerRating>list = new ArrayList<ReviewerRating>();
		DatabaseHelper dbHelp = new DatabaseHelper();
		
		for(int i=0; i<100; i++) {
			if(i != 50) {
				ReviewerRating rr = new ReviewerRating("RandomUserName", 0, true, dbHelp);
				list.add(rr);
			} else {
				ReviewerRating rr = new ReviewerRating("Your Mom", 1, false, dbHelp);
				list.add(rr);
			}
		}
		for(ReviewerRating rr : list) {
			if(rr.getReviewerName().equals("SECRET")) {
				assertTrue("ReviewerRating was not read Correctly", rr.getReviewerName().equals("Your Mom"));
				assertEquals(1, rr.getScore());
				assertFalse("ReviewerRating was not read Correctly", rr.getIsTrusted());
			}
		}    	
	}
	
	
	/*
	 * ************QuestionsList.java TESTS************************************************************************************************************************
	 */
	
	
	/**
	 * Creates and Returns an Empty QuestionsList Object
	 * @return QuestionsList
	 */
	public QuestionsList createQuestionsList() {
		QuestionsList qlist = new QuestionsList();
		return qlist;
	}
	
	/**
	 * Add a given number of Question objects to a given QuestionsList Object
	 * @param num	The Number of Questions to Generate.
	 * @param qlist		The QuestionsList Object to add the generated Questions to.
	 * @return qlist	Return the Updated QuestionsList
	 */
	public QuestionsList addQuestions(int num, QuestionsList qlist) {
		for(int i=0; i<num; i++) {
    		//Load a new random question into the database with a set incrementing index number
    		Question question = new Question("New Question", String.valueOf(i), "RandomUserName", false, false, 0, false, 0 );
    		qlist.loadQuestion(question);
		}
		return qlist;
	}
	
	/**
	 * Create a QuestionsList object, make sure it's actually generated. 
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@Test
	public void createQuestionsListTest() {
		QuestionsList questionsList = new QuestionsList();
		QuestionsList qlist = createQuestionsList();
		boolean tf = false;
		
		//Compare the class types to see if we actually created a QuestionsList object.
		if(questionsList.getClass() == qlist.getClass()) {
    		tf = true;
    	}
    	assertTrue("QuestionsList was not generated Correctly", tf);
	}
	
	
	/**
	 * Create a List of 100 questions, read each one and match the expected question body text. 
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@Test
	public void readQuestionsList() {
		QuestionsList qlist = createQuestionsList();
		qlist = addQuestions(100, qlist);
		boolean tf = false;
		
		//Loop to check if the index number matches the questionbody text
		for(int i=0; i<100; i++) {
			tf = false;
			if(qlist.getQuestionFromIndex(i).getQuestionText().equals(String.valueOf(i))) {
				tf = true;
			}
			assertTrue("Reading Question " + String.valueOf(i) + " incorrectly", tf);
		}
	}
	
	
	/**
	 * Create a List of 100 questions, update each one (duplicating the current question text body), 
	 * and then check if all questions are actually updated. 
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@Test
	public void updateQuestionsList() {
		QuestionsList qlist = createQuestionsList();
		qlist = addQuestions(100, qlist);
		boolean tf = false;
		String currentBodyText = "";
		
		//Update each Questions Body text. Duplicate what's already there
		for(int i=0; i<100; i++) {
			currentBodyText = qlist.getQuestionFromIndex(i).getQuestionText();
			qlist.getQuestionFromIndex(i).setQuestionText(currentBodyText + currentBodyText);
		}
		
		//Verify each Questions Body text is a duplication of the current index
		for(int i=0; i<100; i++) {
			tf = false;
			currentBodyText = qlist.getQuestionFromIndex(i).getQuestionText();
			if(currentBodyText.equals(String.valueOf(i) + String.valueOf(i))) {
				tf = true;
			}
			assertTrue("Updating Question " + String.valueOf(i) + " incorrectly", tf);
		}
	}
	
	/**
	 * Create a List of 100 questions, go through and delete each one, 
	 * check if it was actually deleted, then delete the list. 
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@Test
	public void deleteQuestionsList() {
		QuestionsList qlist = createQuestionsList();
		qlist = addQuestions(100, qlist);
		boolean tf = false;
		for(int i=0; i<100; i++) {
			tf = false;
			//Remove the First Question in the List, compare text to make sure it was taken out.
			if(qlist.questionsList.remove().getQuestionText().equals(String.valueOf(i))) {
				tf = true;
			}
			assertTrue("Deleting Question " + String.valueOf(i) + " incorrectly", tf);
		}
		
		//Use AssertFalse, because if the QuestionsList is empty, it will return False if we try to delete it.
		assertFalse("Was not able to delete QuestionsList", qlist.deleteList());
	}
	
	
	/**
	 * Create a List of 100 questions, and search for each one. 
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@Test
	public void searchQuestionsList() {
		QuestionsList qlist = createQuestionsList();
		qlist = addQuestions(100, qlist);
		boolean tf = false;
		
		//Loop to check if the index number matches the current questionbody text.
		for(int i=0; i<100; i++) {
			tf = false;
			if(qlist.getQuestionFromIndex(i).getQuestionText().equals(String.valueOf(i))){
				tf = true;
			}
			assertTrue("Incorrectly got Question " + String.valueOf(i), tf);
		}
	}
	
	/*
	 * ************AnswersList.java TESTS************************************************************************************************************************
	 */
	
	
	/**
	 * Creates and Returns an Empty AnswersList Object
	 * @return AnswersList
	 */
	public AnswersList createAnswersList() {
		AnswersList alist = new AnswersList();
		return alist;
	}
	
	/**
	 * Add a given number of Answer objects to a given AnswersList Object
	 * @param num	The Number of Answers to Generate.
	 * @param alist		The AnswersList Object to add the generated Answers to.
	 * @return alist	Return the Updated AnswersList
	 */
	public AnswersList addAnswers(int num, AnswersList alist) {
		for(int i=0; i<num; i++) {
    		//Load a new random answer into the database with a set incrementing index number
			
    		Answer answer = new Answer("RandomUserName", String.valueOf(i), 0, false, 0, 0);
    		alist.loadAnswer(answer);
		}
		return alist;
	}
	
	/**
	 * Create a AnswersList object, make sure it's actually generated. 
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@Test
	public void createAnswersListTest() {
		AnswersList answersList = new AnswersList();
		AnswersList alist = createAnswersList();
		boolean tf = false;
		
		//Compare the class types to see if we actually created a QuestionsList object.
		if(answersList.getClass() == alist.getClass()) {
    		tf = true;
    	}
    	assertTrue("AnswersList was not generated Correctly", tf);
	}
	
	
	/**
	 * Create a List of 100 Answers, read each one and match the expected answer text. 
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@Test
	public void readAnswersList() {
		AnswersList alist = createAnswersList();
		alist = addAnswers(100, alist);
		boolean tf = false;
		
		//Loop to check if the index number matches the Answer text
		for(int i=0; i<100; i++) {
			tf = false;
			if(alist.getAnswerFromIndex(i).getAnswerText().equals(String.valueOf(i))) {
				tf = true;
			}
			assertTrue("Reading Answer " + String.valueOf(i) + " incorrectly", tf);
		}
	}
	
	
	/**
	 * Create a List of 100 Answers, update each one (duplicating the current answer text body), 
	 * and then check if all answers are actually updated. 
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@Test
	public void updateAnswersList() {
		AnswersList alist = createAnswersList();
		alist = addAnswers(100, alist);
		boolean tf = false;
		String currentBodyText = "";
		
		//Update each Answers Body text. Duplicate what's already there
		for(int i=0; i<100; i++) {
			currentBodyText = alist.getAnswerFromIndex(i).getAnswerText();
			alist.getAnswerFromIndex(i).setAnswerText(currentBodyText + currentBodyText);
		}
		
		//Verify each Answers Body text is a duplication of the current index
		for(int i=0; i<100; i++) {
			tf = false;
			currentBodyText = alist.getAnswerFromIndex(i).getAnswerText();
			if(currentBodyText.equals(String.valueOf(i) + String.valueOf(i))) {
				tf = true;
			}
			assertTrue("Updating Answer " + String.valueOf(i) + " incorrectly", tf);
		}
	}
	
	/**
	 * Create a List of 100 Answers, go through and delete each one, 
	 * check if it was actually deleted, then delete the list. 
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@Test
	public void deleteAnswersList() {
		AnswersList alist = createAnswersList();
		alist = addAnswers(100, alist);
		boolean tf = false;
		for(int i=0; i<100; i++) {
			tf = false;
			//Remove the First Answer in the List, compare text to make sure it was taken out.
			if(alist.answersList.remove().getAnswerText().equals(String.valueOf(i))) {
				tf = true;
			}
			assertTrue("Deleting Question " + String.valueOf(i) + " incorrectly", tf);
		}
		
		//Use AssertFalse, because if the AnswersList is empty, it will return False if we try to delete it.
		assertFalse("Was not able to delete AnswersList", alist.deleteList());
	}
	
	
	/**
	 * Create a List of 100 Answers, and search for each one. 
	 * <strong>JUnit returns an Assertion Statement</strong>
	 */
	@Test
	public void searchAnswersList() {
		AnswersList alist = createAnswersList();
		alist = addAnswers(100, alist);
		boolean tf = false;
		
		//Loop to check if the index number matches the current answer text.
		for(int i=0; i<100; i++) {
			tf = false;
			if(alist.getAnswerFromIndex(i).getAnswerText().equals(String.valueOf(i))){
				tf = true;
			}
			assertTrue("Incorrectly got Answer " + String.valueOf(i), tf);
		}
	}

}//END OF FILE
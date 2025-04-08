package databasePart1;

import java.sql.SQLException;
import java.util.LinkedList;

import databasePart1.DatabaseHelper;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// "This little guy imports himself"
import databasePart1.SearchDiscussions;


/**
 * The Search Discussions class creates the page to search new discussions. 
 */
/**
 * <h1>Search Discussions Class</h1>
 * @author CSE360 Monday 11
 * @version 1.0
 * @since 2025-03-05
 */
public class SearchDiscussions {
	
	private final DatabaseHelper databaseHelper;
	//public QuestionsList questionsList;
	public Question currentQuestion;
	public Answer currentAnswer;
	//public AnswersList answersList;
	public VBox allAnswers = new VBox(1);
	public Label preferredAnswerText = new Label();
	public Label resolvedText = new Label("");
	//Tag Members
	public Boolean hwBool = false; 
    public Boolean conceptBool = false; 
    public Boolean examBool = false; 
    public Boolean generalBool = false; 
    public Boolean socialBool = false; 
	public String tag = ""; 

    /** Constructor for the SearchDiscussions page
     * @param databaseHelper
     */
    public SearchDiscussions(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    /** Constructor for the SD page, but with a tag to search by tag. 
     * @param databaseHelper
     * @param tag
     */
    public SearchDiscussions(DatabaseHelper databaseHelper, String tag) {
    	this.databaseHelper = databaseHelper;
    	this.tag = tag; 
    }

 
    /** the main display function for the SD class. 
     * @param primaryStage
     * @param user
     */
    public void show(Stage primaryStage, User user) {
    	System.out.println("New Tag (looosers) " + tag);
    	
    	//Split up the page.
    	BorderPane layout = new BorderPane(); //Has Different Prebuilt Sections
    	
    	//The main 2 parts of the Page
    	VBox listOfAllQuestions = new VBox(5); //to store the List of Questions, LEFT SIDE
    	VBox questionContent = new VBox(5); //to store content of Currently Selected QUestion, RIGHT SIDE
    	
    	//Text for an error label
    	Label errorLabel = new Label();
    	errorLabel.setWrapText(true);
    	errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
    	
    	//A button for the User to use to logout
    	Button logout = new Button("Logout"); 
		logout.setOnAction( a-> {
				UserLoginPage userLoginPage = new UserLoginPage(databaseHelper); 
				userLoginPage.show(primaryStage);
		});
		
		// making a list 
        Boolean[] listTags = {hwBool, conceptBool, examBool, generalBool, socialBool};

        // A Button for Homework
        Button homework = new Button("Homework Tag"); 
    	// A Button for Concept
    	Button concept = new Button("Concept Tag"); 
		// A Button for Exam
		Button exam = new Button("Exam Tag");
		// A Button for General
		Button general = new Button ("General Tag");
		// A Button for Social
		Button social = new Button ("Social Tag");
		//Search Button 
		Button search = new Button ("Search");
		
		homework.setOnAction( a-> {
            setButtonGreen(homework);
            setMultipleGrey(concept, exam, general, social);
            hwBool = true; 
            setFalse(listTags, hwBool);
            this.tag = "Homework";
		});

		concept.setOnAction( a-> {
			setButtonGreen(concept);
            setMultipleGrey(homework, exam, general, social);
            conceptBool = true; 
            setFalse(listTags, conceptBool);
            this.tag= "conceptTag";
		});
		
		exam.setOnAction(a -> {
            setButtonGreen(exam);
            setMultipleGrey(concept, homework, general, social);
            examBool = true; 
            setFalse(listTags, examBool);
            this.tag = "Exam";
	    	
	    });
		general.setOnAction( a-> {
            setButtonGreen(general);
            setMultipleGrey(concept, exam, homework, social);
            generalBool = true; 
            setFalse(listTags, generalBool);
            this.tag= "General";
		});
		
		social.setOnAction( a-> {
			setButtonGreen(social);
            setMultipleGrey(concept, exam, general, homework);
            socialBool = true; 
            setFalse(listTags, socialBool);
            this.tag = "Social";
		});
		
		Button refresh = new Button ("Refresh");
		refresh.setOnAction( a-> {
			SearchDiscussions searchDisc = new SearchDiscussions(databaseHelper);
			searchDisc.show(primaryStage, user);
		});

		Button back = new Button("Back");
		back.setOnAction( a-> {
			ViewDiscussions viewDiscussions = new ViewDiscussions(databaseHelper);
			Question questionNull = new Question(0, "null", "null", "null", false, false, 0, false, 0);//a blank question to load through the ViewDiscussion Page, used to save 424 lines of code
			viewDiscussions.show(primaryStage, user, questionNull, false);
		});
		
		
		//this vbox is for the top section of the border pane
		VBox topSection = new VBox(5);
		//The Top Box to Store the Buttons
		HBox topHBox = new HBox(10);
		//sets the top vbox as the top element
    	layout.setTop(topSection); //Storing this hbox in the Page Layout (BorderPane)
    	
    	
    	//Labels to Update when we click on a question, these are the defaults
    	Label idLabel = new Label("Id:");
    	idLabel.setStyle("-fx-font-weight: bold;");
    	Label idNumber = new Label("");
    	
    	Label tagLabel = new Label("Tag:");
    	tagLabel.setStyle("-fx-font-weight: bold;");
    	Label questionTag = new Label("");
  
    	Label questionTitleLabel = new Label("");
    	questionTitleLabel.setStyle("-fx-font-weight: bold;");
    	Label spaceLabel1 = new Label("\t"); //Tab Label for spacing use
    	Label spaceLabel2 = new Label("\t"); //^^
    	Label spaceLabel3 = new Label("\t"); //^^
    	Label spaceLabel4 = new Label("\t"); //^^
    	Label userNameLabel = new Label("Username: ");
    	userNameLabel.setStyle("-fx-font-weight: bold;");
    	Label userNameText = new Label("");
    	Label resolvedLabel = new Label("Resolved:");
    	resolvedLabel.setStyle("-fx-font-weight: bold;");
    	//Set to Gloabl for recursive buttons Label resolvedText = new Label("");
    	resolvedText.setStyle("-fx-font-weight: bold;");
    	Label questionText = new Label();
    	questionText.setStyle("-fx-font-weight: bold;");
    	Label preferredAnswerLabelTitle = new Label("The Preferred Answer: ");
    	preferredAnswerLabelTitle.setMinWidth(130);
    	preferredAnswerLabelTitle.setStyle("-fx-font-weight: bold;");
    	// Turned this guy into a global Label preferredAnswerText = new Label();
    	preferredAnswerText.setWrapText(true);
    	Label answersLabelTitle = new Label("Answers:");
    	answersLabelTitle.setStyle("-fx-font-weight: bold;");
    	
    	//To Store the Basic Info about a particular Question
    	VBox questionContentTitle = new VBox();
    	HBox info1 = new HBox();
    	HBox info2 = new HBox();
    	HBox info3 = new HBox();
    	HBox info4 = new HBox();
    	HBox info5 = new HBox();
    	HBox info6 = new HBox();
    	HBox spacer = new HBox();

    	//add the family together
    	info1.getChildren().addAll(idLabel, idNumber, spaceLabel1, userNameLabel, userNameText, spaceLabel3, resolvedLabel, resolvedText, spaceLabel4, tagLabel, questionTag);
    	spacer.getChildren().add(spaceLabel2);
    	info2.getChildren().addAll(questionTitleLabel);
    	info3.getChildren().addAll();
    	//info4.getChildren().addAll(resolvedLabel, resolvedText);
    	info5.getChildren().addAll(preferredAnswerLabelTitle, preferredAnswerText);
    	//info6.getChildren().addAll();

    	
    	//Vertical box for looking at all the answers
    	//VBox allAnswers = new VBox(1);  //moved to public
    	
    	//This is the content of the right side of the border pane which carries all of the answers and question data
    	questionContentTitle.getChildren().addAll(info1, spacer, info2, info3, info5, info6);

    	//Get the updated Questions List and AnswersList from the Database
    	
    	
    	QuestionsList questionsList = databaseHelper.loadQuestionsListTags(tag);
    	AnswersList answersList = databaseHelper.loadAnswersList();
    	
    	System.out.println("Size of QuestionList " + questionsList.getSize());
    	
    	int size = questionsList.getSize();
    	listOfAllQuestions.getChildren().clear(); //reset for each loading
    	
    	//This little guy will dynamically make buttons on the left side of the screen as we have questions in the database
    	if(size > 0) {
    		for (int i = 0; i < size; i++) {  //Creates new buttons 
    			
    			//The creation of a new button
				Button newButton = new Button (questionsList.getQuestionFromIndex(i).getTitle()); 
				
				//Setting a question Object into each button
				newButton.setUserData(questionsList.getQuestionFromIndex(i));						
				newButton.setOnAction( a-> { 														
					//the Action of each button is realative to the question object inside
					//remake the Question Object on Click
					Question question = (Question) newButton.getUserData(); 
					currentQuestion = question;
					
					//Set all of the respective Labels
					idNumber.setText(String.valueOf(currentQuestion.getId()));
					
					questionTag.setText(databaseHelper.getTag(currentQuestion.getId()));
					
					questionTitleLabel.setText("Title: " + currentQuestion.getTitle());
					if(!currentQuestion.getHideUserName()) {userNameText.setText(currentQuestion.getUserName());}
					else {userNameText.setText("Anonymous"); }
					
					//Update the Resolved Text Label, YES OR NO in Green and RED
					if(currentQuestion.getResolved()) { 
						resolvedText.setText(" Yes");
						resolvedText.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
					}
					else { 
						resolvedText.setText(" No"); 
						resolvedText.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
					}
					if(currentQuestion.getPreferredAnswerId() != 0) {
						if(answersList.getAnswerFromId(currentQuestion.getPreferredAnswerId()) == null){
							preferredAnswerText.setText("");
						}
						else {
							preferredAnswerText.setText(answersList.getAnswerFromId(currentQuestion.getPreferredAnswerId()).getAnswerText());
						}
					}
					questionText.setText("Question: " + currentQuestion.getQuestionText());
					questionText.setWrapText(true);			


					//Load Associated Answers into Right side 
					int size2 = answersList.getSize();
					allAnswers.getChildren().clear();
					String [] rolesList = {"admin", "staff", "instructor", "reviewer", "student", "user"};
					
					// Ensure that the list of answers is not empty
					if(size2 > 0) {
						
						// Loop through possible roles and add each answer associated with that role
						// This will ensure that answers from higher roles is added first
						for (int k = 0; k < rolesList.length; k++){

							// loop through answerList
							for (int j = 0; j < size2; j++) { 

								//Check Question ID, Check if it's a Level 0 Answer, Check if it's highest role matches the RolesList[k]
								if((answersList.getAnswerFromIndex(j).getQuestionLinkID() == currentQuestion.getId()) && (answersList.getAnswerFromIndex(j).getHighestRole().equals(rolesList[k]))  && (answersList.getAnswerFromIndex(j).getLevel() == 0)) { // update this line
									
									//Create a new Hbox for each answer, including a button and a label
									HBox newHBox = new HBox(5);
									VBox newVBox = new VBox();
									
									//Dynamix element creation
									Button setAsPreferredAnswer = new Button ("Preferred Answer");
									setAsPreferredAnswer.setStyle("-fx-font-size: 8px;");
									setAsPreferredAnswer.setPrefHeight(12);
									
									//Storing the answer obj, in each preferred ans. button, for future reference
									setAsPreferredAnswer.setUserData(answersList.getAnswerFromIndex(j));
								
									//Set Button Clicks
									setAsPreferredAnswer.setOnAction( b-> {
										//remake the Answer Object on Click
										Answer answerObj = (Answer) setAsPreferredAnswer.getUserData(); 
										currentAnswer = answerObj;
										currentQuestion.setPreferredAnswerId(currentAnswer.getId());
										currentQuestion.setResolved(true);
										updateResolvedLabelText(currentQuestion.getResolved());
										//update the preffered answer ID number in the database too
										try {
											databaseHelper.updateQuestion(currentQuestion);
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										preferredAnswerText.setText(currentAnswer.getAnswerText());
										//Reload the page
										
									});
									
									//If you are not the creator of the question, then you don't get to See the preffered answer button
									if(!answersList.getAnswerFromIndex(j).getUserName().equals(user.getUserName())) {
										setAsPreferredAnswer.setVisible(false);
									}
									

									//edit and delete buttons set up
									Button editAnswer = new Button ("Edit");
									editAnswer.setStyle("-fx-font-size: 8px;");
									editAnswer.setPrefHeight(12);
									Button deleteAnswer = new Button ("Delete");
									deleteAnswer.setStyle("-fx-text-fill: red; -fx-font-size: 8px;");
									deleteAnswer.setPrefHeight(12);
									Button replyAnswer = new Button ("Reply");
									replyAnswer.setStyle("-fx-text-fill: black; -fx-font-size: 8px;");
									replyAnswer.setPrefHeight(12);
									
									//give each button an answer obj for future reference
									editAnswer.setUserData(answersList.getAnswerFromIndex(j));
									deleteAnswer.setUserData(answersList.getAnswerFromIndex(j));
									replyAnswer.setUserData(answersList.getAnswerFromIndex(j));
									
									//on clicks for edit and delete, and reply
									editAnswer.setOnAction( b-> {
										Answer answerObj = (Answer) editAnswer.getUserData(); //remake the Answer Object on Click
										currentAnswer = answerObj;
										
										//Make sure only the answer creator is the only one able to edit their answer
										if(currentAnswer.getUserName().equals(user.getUserName())) {
											//currentQuestion.setPreferredAnswerId(currentAnswer.getId());
											AnswerQuestionPage answerQuestionPage = new AnswerQuestionPage(databaseHelper);
											answerQuestionPage.showUpdateAnswer(primaryStage, user, currentQuestion, currentAnswer);
										}
									});

									deleteAnswer.setOnAction( b-> {
										Answer answerObj = (Answer) deleteAnswer.getUserData(); //remake the Answer Object on Click
										currentAnswer = answerObj;
										
										if(currentAnswer.getUserName().equals(user.getUserName()) || user.getIsAdmin()) {
											try {
												databaseHelper.deleteAnswer(currentAnswer.getId());
											} catch (SQLException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											//recalls the stage, to refresh 
											ViewDiscussions viewDiscussions = new ViewDiscussions(databaseHelper); 
											viewDiscussions.show(primaryStage, user, currentQuestion, false);
										}
									});
									
									replyAnswer.setOnAction( b-> {
										Answer answerObj = (Answer) editAnswer.getUserData(); //remake the Answer Object on Click
										currentAnswer = answerObj;

										AnswerQuestionPage answerQuestionPage = new AnswerQuestionPage(databaseHelper);
										answerQuestionPage.showReply(primaryStage, user, currentQuestion, currentAnswer);
									});
									
									
									
									
									//Set the Username, and make sure it can be anonymous
									Label usersname = new Label();
									usersname.setStyle("-fx-font-weight: bold;");
									usersname.setUserData(answersList.getAnswerFromIndex(j));
									
									//Answer newAnswer = (Answer) usersname.getUserData();
									if(answersList.getAnswerFromIndex(j).getHideUserName()) { 
										usersname.setText("User: Anonymous");
									} 
									else {
										usersname.setText("User: " + answersList.getAnswerFromIndex(j).getUserName()); 
										
									}
									newHBox.getChildren().add(usersname);
									//add to hbox
									newHBox.getChildren().addAll(setAsPreferredAnswer, editAnswer, deleteAnswer, replyAnswer);
									
									//The actual Answer Text
									Label answer = new Label(answersList.getAnswerFromIndex(j).getAnswerText());
									
									setAsPreferredAnswer.setMinWidth(30);
									
									//make sure the answer doesnt go off the page, and stays in view
									answer.setWrapText(true);
									
									//add the remaining elements
									newVBox.getChildren().addAll(newHBox, answer);
									newVBox.setStyle("-fx-border-color: grey");
									allAnswers.getChildren().add(newVBox);

									//Recursive Add Replies Function
									//For Adding all Subrepplies in an orderly manner.

									showReplies(answersList.getAnswerFromIndex(j), answersList, primaryStage, user);

								}
							}
						
						}
						
					}
				});
				
				//Add the question Buttons to the Stage basically.
				listOfAllQuestions.getChildren().add(newButton);
			}
		//END dynamic stage set up
        } 
        
    	//Update Question Button
    	Button updateCurrentQuestion = new Button ("Update Question");
    	updateCurrentQuestion.setOnAction( a-> {
    		if(currentQuestion != null) {
    			if(currentQuestion.getUserName().equals(user.getUserName())) {
    				NewQuestion updateQuestion = new NewQuestion(databaseHelper); 
    				updateQuestion.showUpdate(primaryStage, user, currentQuestion);
    			}
    			else {
    				errorLabel.setText("You are not the Author of this Question");
    			}
    		} else {errorLabel.setText("Please Select A Question to Update");} 
 
    	});	
    		
    		
    	//Deletes the current question with Specific Admin Rights
        Button deleteCurrentQuestion = new Button ("Delete Question");
        deleteCurrentQuestion.setOnAction( a-> {
        	if(currentQuestion != null) {
        		if(currentQuestion.getUserName().equals(user.getUserName()) || user.getIsAdmin() ) {
        			try {
        				databaseHelper.deleteAnswersLinked(currentQuestion.getId());
        				databaseHelper.deleteQuesiton(currentQuestion.getId());
        			} catch (SQLException e) {
        				e.printStackTrace();
        			}     			
        			//recalls the stage, to refresh question Building
        			ViewDiscussions viewDiscussions = new ViewDiscussions(databaseHelper); 
        			Question questionNull = new Question(0, "null", "null", "null", false, false, 0, false, 0);//a blank question to load through the ViewDiscussion Page, used to save 424 lines of code
        			viewDiscussions.show(primaryStage, user, questionNull, false);
        		}
        		else { errorLabel.setText("You are not the Author of this Question");}
        		
        	} else {errorLabel.setText("Please Select A Question to Delete");}
        });	
    	
    	//Answer Question Button
    	Button answerQuestion = new Button ("Answer Question");
    			answerQuestion.setOnAction( a-> {
    				if(currentQuestion != null) {
    					AnswerQuestionPage answerQuestionPage = new AnswerQuestionPage(databaseHelper); 
    					answerQuestionPage.show(primaryStage, user, currentQuestion);
    				} else { errorLabel.setText("Please Select A Question to Answer");}
    				
    			});	 
    			
    	// Search Questions Button
		search.setOnAction( a-> {
			System.out.println(tag);
			SearchDiscussions searchDisc = new SearchDiscussions(databaseHelper, tag);
			searchDisc.show(primaryStage, user);
		});
		
    	//Putting the Buttons inside of the HBOX
    	topHBox.getChildren().addAll(logout, homework, concept, exam, general, social,  search, back);
    	topSection.getChildren().addAll(topHBox, errorLabel);
    	questionContent.getChildren().addAll(questionContentTitle, questionText,answersLabelTitle, allAnswers);
    	
    	//Setting up the reset of the page
    	ScrollPane scrollPaneLeft = new ScrollPane(listOfAllQuestions);//Question Titles
    	ScrollPane scrollPaneRight = new ScrollPane(questionContent); //Reading Currently selected questoin and answers
    	
    	//seting some widths of the page
    	scrollPaneLeft.setPrefWidth(200);
    	scrollPaneRight.setFitToWidth(true);
    	
    	//set up the elements orientation
    	layout.setLeft(scrollPaneLeft);
    	layout.setCenter(scrollPaneRight);
	    layout.setStyle("-fx-alignment: center; -fx-padding: 20;");
	    
	    Scene searchDiscussionScene = new Scene(layout, 800, 400);
		searchDiscussionScene.getStylesheets().add(getClass().getResource("/databasePart1/style.css").toExternalForm());

	    // Set the scene to primary stage
	    primaryStage.setScene(searchDiscussionScene);
	    primaryStage.setTitle("Search Discussion Page");
	    
	    primaryStage.show();
    }
    
    
	//
	/**This cool little guy will recursively display each sub reply, and it will make sure the sub replies sub replies are taken care of etc.
	 * @param parentAnswer
	 * @param answersList
	 * @param primaryStage
	 * @param user
	 */
	public void showReplies(Answer parentAnswer, AnswersList answersList, Stage primaryStage, User user){
		System.out.println("Creating Reply Chain");
		// Ensure answer list isn't blank
		int size = answersList.getSize();
		if(size > 0){
			// Loop through all the answers in answersList
			for (int j=0; j < size; j++){
				// if correct level (one greater than parent) && has parentID that equals the parentAnswer ID
				// then you display the sub answers
				if ((answersList.getAnswerFromIndex(j).getLevel() == (parentAnswer.getLevel() + 1)) && (answersList.getAnswerFromIndex(j).getAnswerLinkID() == parentAnswer.getId())){
					
					//New Label for Spacing
					Label spacingLabel = new Label("");
					
					//Add tab spacing to the label text for each level of reply it is
					for(int k = 0; k < answersList.getAnswerFromIndex(j).getLevel(); k++){
						spacingLabel.setText(spacingLabel.getText() + "\t");
						if(k > 5) {
							break;
						}
					}
					//this new hbox will hold the whole reply Answer
					HBox replyBox = new HBox();
					
					//Create a new Hbox for each answer, including a button and a label
					HBox newHBox = new HBox(5); 
					VBox newVBox = new VBox();
					
					//Dynamix element creation
					Button setAsPreferredAnswer = new Button ("Preferred Answer");
					setAsPreferredAnswer.setStyle("-fx-font-size: 8px;");
					setAsPreferredAnswer.setPrefHeight(12);
					
					//Storing the answer obj, in each preferred ans. button, for future reference
					setAsPreferredAnswer.setUserData(answersList.getAnswerFromIndex(j));
				
					//Set Button Clicks
					setAsPreferredAnswer.setOnAction( b-> {
						//remake the Answer Object on Click
						Answer answerObj = (Answer) setAsPreferredAnswer.getUserData(); 
						currentAnswer = answerObj;
						currentQuestion.setPreferredAnswerId(currentAnswer.getId());
						currentQuestion.setResolved(true);
						updateResolvedLabelText(currentQuestion.getResolved());
						//update the preffered answer ID number in the database too
						try {
							databaseHelper.updateQuestion(currentQuestion);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						preferredAnswerText.setText(currentAnswer.getAnswerText());
					});
					
					//If you are not the creator of the question, then you don't get to See the preffered answer button
					if(!answersList.getAnswerFromIndex(j).getUserName().equals(user.getUserName())) {
						setAsPreferredAnswer.setVisible(false);
					}
					
					//edit and delete buttons set up
					Button editAnswer = new Button ("Edit");
					editAnswer.setStyle("-fx-font-size: 8px;");
					editAnswer.setPrefHeight(12);
					Button deleteAnswer = new Button ("Delete");
					deleteAnswer.setStyle("-fx-text-fill: red; -fx-font-size: 8px;");
					deleteAnswer.setPrefHeight(12);
					Button replyAnswer = new Button ("Reply");
					replyAnswer.setStyle("-fx-text-fill: black; -fx-font-size: 8px;");
					replyAnswer.setPrefHeight(12);
					
					//give each button an answer obj for future reference
					editAnswer.setUserData(answersList.getAnswerFromIndex(j));
					deleteAnswer.setUserData(answersList.getAnswerFromIndex(j));
					replyAnswer.setUserData(answersList.getAnswerFromIndex(j));
					
					//on clicks for edit and delete, and reply
					editAnswer.setOnAction( b-> {
						Answer answerObj = (Answer) editAnswer.getUserData(); //remake the Answer Object on Click
						currentAnswer = answerObj;
						
						//Make sure only the answer creator is the only one able to edit their answer
						if(currentAnswer.getUserName().equals(user.getUserName())) {
							//currentQuestion.setPreferredAnswerId(currentAnswer.getId());
							AnswerQuestionPage answerQuestionPage = new AnswerQuestionPage(databaseHelper);
							answerQuestionPage.showUpdateAnswer(primaryStage, user, currentQuestion, currentAnswer);
						}
					});
					deleteAnswer.setOnAction( b-> {
						Answer answerObj = (Answer) deleteAnswer.getUserData(); //remake the Answer Object on Click
						currentAnswer = answerObj;
						
						if(currentAnswer.getUserName().equals(user.getUserName()) || user.getIsAdmin()) {
							try {
								databaseHelper.deleteAnswer(currentAnswer.getId());
							} catch (SQLException e) {
								e.printStackTrace();
							}
							//recalls the stage, to refresh 
							ViewDiscussions viewDiscussions = new ViewDiscussions(databaseHelper); 
							viewDiscussions.show(primaryStage, user, currentQuestion, false);
						}
					});
					replyAnswer.setOnAction( b-> {
						Answer answerObj = (Answer) editAnswer.getUserData(); //remake the Answer Object on Click
						currentAnswer = answerObj;

						AnswerQuestionPage answerQuestionPage = new AnswerQuestionPage(databaseHelper);
						answerQuestionPage.showReply(primaryStage, user, currentQuestion, currentAnswer);
					});					
					
					//Set the Username, and make sure it can be anonymous
					Label usersname = new Label();
					usersname.setStyle("-fx-font-weight: bold;");
					usersname.setUserData(answersList.getAnswerFromIndex(j));
					
					//Answer newAnswer = (Answer) usersname.getUserData();
					if(answersList.getAnswerFromIndex(j).getHideUserName()) { 
						usersname.setText("User: Anonymous");
					} 
					else {
						usersname.setText("User: " + answersList.getAnswerFromIndex(j).getUserName()); 
						
					}
					newHBox.getChildren().add(usersname);
					//add to hbox
					newHBox.getChildren().addAll(setAsPreferredAnswer, editAnswer, deleteAnswer, replyAnswer);
					
					//The actual Answer Text
					Label answer = new Label(answersList.getAnswerFromIndex(j).getAnswerText());
					
					setAsPreferredAnswer.setMinWidth(30);
					
					//make sure the answer doesnt go off the page, and stays in view
					answer.setWrapText(true);
					
					//add the remaining elements
					newVBox.getChildren().addAll(newHBox, answer);
					newHBox.setMinWidth(400);
					newVBox.setMaxWidth(newHBox.getWidth());
					newVBox.setStyle("-fx-border-color: grey");
					replyBox.getChildren().addAll(spacingLabel, newVBox);
					allAnswers.getChildren().add(replyBox);
					
					//Recursivley call the reply tree
					showReplies(answersList.getAnswerFromIndex(j), answersList, primaryStage, user);
				}
			}
		}
	}
	
	/** updates the "resolved" label text
	 * @param isResolved
	 */
	public void updateResolvedLabelText(boolean isResolved) {
		//Update the Resolved Text Label, YES OR NO in Green and RED
		if(isResolved) { 
			resolvedText.setText(" Yes");
			resolvedText.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
		}
		else { 
			resolvedText.setText(" No"); 
			resolvedText.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
		}
	}
	
/** for loading in a particular question 
 * @param primaryStage
 * @param user
 * @param loadThisQuestion
 */
public void show(Stage primaryStage, User user, Question loadThisQuestion) {
    	
    	
    	//Split up the page.
    	BorderPane layout = new BorderPane(); //Has Different Prebuilt Sections
    	
    	//The main 2 parts of the Page
    	VBox listOfAllQuestions = new VBox(5); //to store the List of Questions, LEFT SIDE
    	VBox questionContent = new VBox(5); //to store content of Currently Selected QUestion, RIGHT SIDE
    	
    	//Text for an error label
    	Label errorLabel = new Label();
    	errorLabel.setWrapText(true);
    	errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");
    	
    	//A button for the User to use to logout
    	Button logout = new Button("Logout"); 
		logout.setOnAction( a-> {
				UserLoginPage userLoginPage = new UserLoginPage(databaseHelper); 
				userLoginPage.show(primaryStage);
		});
		
		//Back to User Homepage
		Button userButton = new Button("Switch Roles");
		userButton.setOnAction(a -> {
	    	new WelcomeLoginPage(databaseHelper).show(primaryStage, user);
	    });
		
		//A Button to Launch the New Question Page
		Button newQuestion = new Button ("New Question");
		newQuestion.setOnAction( a-> {
			NewQuestion newQuestionPage = new NewQuestion(databaseHelper);
			newQuestionPage.show(primaryStage, user);
		});
		
		//A refresh Button for Fun
		Button refresh = new Button ("Refresh");
		refresh.setOnAction( a-> {
			ViewDiscussions viewDiscussions = new ViewDiscussions(databaseHelper); 
			viewDiscussions.show(primaryStage, user, currentQuestion, false);
		});
		
			
		
				
		//this vbox is for the top section of the border pane
		VBox topSection = new VBox(5);
		//The Top Box to Store the Buttons
		HBox topHBox = new HBox(10);
		//sets the top vbox as the top element
    	layout.setTop(topSection); //Storing this hbox in the Page Layout (BorderPane)
    	
    	
    	//Labels to Update when we click on a question, these are the defaults
    	Label idLabel = new Label("Id:");
    	idLabel.setStyle("-fx-font-weight: bold;");
    	Label idNumber = new Label("");
    	Label questionTitleLabel = new Label("");
    	questionTitleLabel.setStyle("-fx-font-weight: bold;");
    	Label spaceLabel1 = new Label("\t"); //Tab Label for spacing use
    	Label spaceLabel2 = new Label("\t"); //^^
    	Label spaceLabel3 = new Label("\t"); //^^
    	Label spaceLabel4 = new Label("\t"); //^^
    	Label userNameLabel = new Label("Username: ");
    	userNameLabel.setStyle("-fx-font-weight: bold;");
    	Label userNameText = new Label("");
    	Label resolvedLabel = new Label("Resolved:");
    	resolvedLabel.setStyle("-fx-font-weight: bold;");
    	//Set to Gloabl for recursive buttons Label resolvedText = new Label("");
    	resolvedText.setStyle("-fx-font-weight: bold;");
    	Label questionText = new Label();
    	questionText.setStyle("-fx-font-weight: bold;");
    	Label preferredAnswerLabelTitle = new Label("The Preferred Answer: ");
    	preferredAnswerLabelTitle.setMinWidth(130);
    	preferredAnswerLabelTitle.setStyle("-fx-font-weight: bold;");
    	// Turned this guy into a global Label preferredAnswerText = new Label();
    	preferredAnswerText.setWrapText(true);
    	Label answersLabelTitle = new Label("Answers:");
    	answersLabelTitle.setStyle("-fx-font-weight: bold;");
    	
		
    	
    	//To Store the Basic Info about a particular Question
    	VBox questionContentTitle = new VBox();
    	HBox info1 = new HBox();
    	HBox info2 = new HBox();
    	HBox info3 = new HBox();
    	HBox info4 = new HBox();
    	HBox info5 = new HBox();
    	HBox info6 = new HBox();
    	HBox spacer = new HBox();

    	//add the family together
    	info1.getChildren().addAll(idLabel, idNumber, spaceLabel1, userNameLabel, userNameText, spaceLabel3, resolvedLabel, resolvedText, spaceLabel4);
    	spacer.getChildren().add(spaceLabel2);
    	info2.getChildren().addAll(questionTitleLabel);
    	info3.getChildren().addAll();
    	//info4.getChildren().addAll(resolvedLabel, resolvedText);
    	info5.getChildren().addAll(preferredAnswerLabelTitle, preferredAnswerText);
    	//info6.getChildren().addAll();

    	
    	//Vertical box for looking at all the answers
    	//VBox allAnswers = new VBox(1);  //moved to public
    	
    	//This is the content of the right side of the border pane which carries all of the answers and question data
    	questionContentTitle.getChildren().addAll(info1, spacer, info2, info3, info5, info6);

    	//Get the updated Questions List and AnswersList from the Database
    	QuestionsList questionsList = databaseHelper.loadQuestionsList();
    	AnswersList answersList = databaseHelper.loadAnswersList();
    	
    	
    	
    	int size = questionsList.getSize();
    	listOfAllQuestions.getChildren().clear(); //reset for each loading
    	
    	//This little guy will dynamically make buttons on the left side of the screen as we have questions in the database
    	if(size > 0) {
    		for (int i = 0; i < size; i++) {  
    			
    			//The creation of a new button
				Button newButton = new Button (questionsList.getQuestionFromIndex(i).getTitle()); 
				
				//Setting a question Object into each button
				newButton.setUserData(questionsList.getQuestionFromIndex(i));						
				newButton.setOnAction( a-> { 														
					//the Action of each button is realative to the question object inside
					//remake the Question Object on Click
					Question question = (Question) newButton.getUserData(); 
					currentQuestion = question;
					
					//Set all of the respective Labels
					idNumber.setText(String.valueOf(currentQuestion.getId()));
					
					questionTitleLabel.setText("Title: " + currentQuestion.getTitle());
					if(!currentQuestion.getHideUserName()) {userNameText.setText(currentQuestion.getUserName());}
					else {userNameText.setText("Anonymous"); }
					
					//Update the Resolved Text Label, YES OR NO in Green and RED
					if(currentQuestion.getResolved()) { 
						resolvedText.setText(" Yes");
						resolvedText.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
					}
					else { 
						resolvedText.setText(" No"); 
						resolvedText.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
					}
					if(currentQuestion.getPreferredAnswerId() != 0) {
						if(answersList.getAnswerFromId(currentQuestion.getPreferredAnswerId()) == null){
							preferredAnswerText.setText("");
						}
						else {
							preferredAnswerText.setText(answersList.getAnswerFromId(currentQuestion.getPreferredAnswerId()).getAnswerText());
						}
					}
					questionText.setText("Question: " + currentQuestion.getQuestionText());
					questionText.setWrapText(true);			


					//Load Associated Answers into Right side 
					int size2 = answersList.getSize();
					allAnswers.getChildren().clear();
					String [] rolesList = {"admin", "staff", "instructor", "reviewer", "student", "user"};
					
					// Ensure that the list of answers is not empty
					if(size2 > 0) {
						
						// Loop through possible roles and add each answer associated with that role
						// This will ensure that answers from higher roles is added first
						for (int k = 0; k < rolesList.length; k++){

							// loop through answerList
							for (int j = 0; j < size2; j++) { 

								//Check Question ID, Check if it's a Level 0 Answer, Check if it's highest role matches the RolesList[k]
								if((answersList.getAnswerFromIndex(j).getQuestionLinkID() == currentQuestion.getId()) && (answersList.getAnswerFromIndex(j).getHighestRole().equals(rolesList[k]))  && (answersList.getAnswerFromIndex(j).getLevel() == 0)) { // update this line
									
									//Create a new Hbox for each answer, including a button and a label
									HBox newHBox = new HBox(5);
									VBox newVBox = new VBox();
									
									//Dynamix element creation
									Button setAsPreferredAnswer = new Button ("Preferred Answer");
									setAsPreferredAnswer.setStyle("-fx-font-size: 8px;");
									setAsPreferredAnswer.setPrefHeight(12);
									
									//Storing the answer obj, in each preferred ans. button, for future reference
									setAsPreferredAnswer.setUserData(answersList.getAnswerFromIndex(j));
								
									//Set Button Clicks
									setAsPreferredAnswer.setOnAction( b-> {
										//remake the Answer Object on Click
										Answer answerObj = (Answer) setAsPreferredAnswer.getUserData(); 
										currentAnswer = answerObj;
										currentQuestion.setPreferredAnswerId(currentAnswer.getId());
										currentQuestion.setResolved(true);
										updateResolvedLabelText(currentQuestion.getResolved());
										//update the preffered answer ID number in the database too
										try {
											databaseHelper.updateQuestion(currentQuestion);
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										preferredAnswerText.setText(currentAnswer.getAnswerText());
										//Reload the page
										
									});
									
									//If you are not the creator of the question, then you don't get to See the preffered answer button
									if(!answersList.getAnswerFromIndex(j).getUserName().equals(user.getUserName())) {
										setAsPreferredAnswer.setVisible(false);
									}
									

									//edit and delete buttons set up
									Button editAnswer = new Button ("Edit");
									editAnswer.setStyle("-fx-font-size: 8px;");
									editAnswer.setPrefHeight(12);
									Button deleteAnswer = new Button ("Delete");
									deleteAnswer.setStyle("-fx-text-fill: red; -fx-font-size: 8px;");
									deleteAnswer.setPrefHeight(12);
									Button replyAnswer = new Button ("Reply");
									replyAnswer.setStyle("-fx-text-fill: black; -fx-font-size: 8px;");
									replyAnswer.setPrefHeight(12);
									
									//give each button an answer obj for future reference
									editAnswer.setUserData(answersList.getAnswerFromIndex(j));
									deleteAnswer.setUserData(answersList.getAnswerFromIndex(j));
									replyAnswer.setUserData(answersList.getAnswerFromIndex(j));
									
									//on clicks for edit and delete, and reply
									editAnswer.setOnAction( b-> {
										Answer answerObj = (Answer) editAnswer.getUserData(); //remake the Answer Object on Click
										currentAnswer = answerObj;
										
										//Make sure only the answer creator is the only one able to edit their answer
										if(currentAnswer.getUserName().equals(user.getUserName())) {
											//currentQuestion.setPreferredAnswerId(currentAnswer.getId());
											AnswerQuestionPage answerQuestionPage = new AnswerQuestionPage(databaseHelper);
											answerQuestionPage.showUpdateAnswer(primaryStage, user, currentQuestion, currentAnswer);
										}
									});

									deleteAnswer.setOnAction( b-> {
										Answer answerObj = (Answer) deleteAnswer.getUserData(); //remake the Answer Object on Click
										currentAnswer = answerObj;
										
										if(currentAnswer.getUserName().equals(user.getUserName()) || user.getIsAdmin()) {
											try {
												databaseHelper.deleteAnswer(currentAnswer.getId());
											} catch (SQLException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											//recalls the stage, to refresh 
											ViewDiscussions viewDiscussions = new ViewDiscussions(databaseHelper); 
											viewDiscussions.show(primaryStage, user, currentQuestion, false);
										}
									});
									
									replyAnswer.setOnAction( b-> {
										Answer answerObj = (Answer) editAnswer.getUserData(); //remake the Answer Object on Click
										currentAnswer = answerObj;

										AnswerQuestionPage answerQuestionPage = new AnswerQuestionPage(databaseHelper);
										answerQuestionPage.showReply(primaryStage, user, currentQuestion, currentAnswer);
									});
									
									
									
									
									//Set the Username, and make sure it can be anonymous
									Label usersname = new Label();
									usersname.setStyle("-fx-font-weight: bold;");
									usersname.setUserData(answersList.getAnswerFromIndex(j));
									
									//Answer newAnswer = (Answer) usersname.getUserData();
									if(answersList.getAnswerFromIndex(j).getHideUserName()) { 
										usersname.setText("User: Anonymous");
									} 
									else {
										usersname.setText("User: " + answersList.getAnswerFromIndex(j).getUserName()); 
										
									}
									newHBox.getChildren().add(usersname);
									//add to hbox
									newHBox.getChildren().addAll(setAsPreferredAnswer, editAnswer, deleteAnswer, replyAnswer);
									
									//The actual Answer Text
									Label answer = new Label(answersList.getAnswerFromIndex(j).getAnswerText());
									
									setAsPreferredAnswer.setMinWidth(30);
									
									//make sure the answer doesnt go off the page, and stays in view
									answer.setWrapText(true);
									
									//add the remaining elements
									newVBox.getChildren().addAll(newHBox, answer);
									newVBox.setStyle("-fx-border-color: grey");
									allAnswers.getChildren().add(newVBox);

									//Recursive Add Replies Function
									//For Adding all Subrepplies in an orderly manner.

									showReplies(answersList.getAnswerFromIndex(j), answersList, primaryStage, user);

								}
							}
						
						}
						
					}
				});
				
				
				//Add the question Buttons to the Stage basically.
				listOfAllQuestions.getChildren().add(newButton);
				if(questionsList.getQuestionFromIndex(i).getId() == loadThisQuestion.getId()) {
					newButton.fire();
				}
			}
		//END dynamic stage set up
        } 
        
    	//Update Question Button
    	Button updateCurrentQuestion = new Button ("Update Question");
    	updateCurrentQuestion.setOnAction( a-> {
    		if(currentQuestion != null) {
    			if(currentQuestion.getUserName().equals(user.getUserName())) {
    				NewQuestion updateQuestion = new NewQuestion(databaseHelper); 
    				updateQuestion.showUpdate(primaryStage, user, currentQuestion);
    			}
    			else {
    				errorLabel.setText("You are not the Author of this Question");
    			}
    		} else {errorLabel.setText("Please Select A Question to Update");} 
 
    	});	
    		
    		
    	//Deletes the current question with Specific Admin Rights
        Button deleteCurrentQuestion = new Button ("Delete Question");
        deleteCurrentQuestion.setOnAction( a-> {
        	if(currentQuestion != null) {
        		if(currentQuestion.getUserName().equals(user.getUserName()) || user.getIsAdmin() ) {
        			try {
        				databaseHelper.deleteAnswersLinked(currentQuestion.getId());
        				databaseHelper.deleteQuesiton(currentQuestion.getId());
        			} catch (SQLException e) {
        				e.printStackTrace();
        			}     			
        			//recalls the stage, to refresh question Building
        			ViewDiscussions viewDiscussions = new ViewDiscussions(databaseHelper); 
        			Question questionNull = new Question(0, "null", "null", "null", false, false, 0, false, 0);//a blank question to load through the ViewDiscussion Page, used to save 424 lines of code
        			viewDiscussions.show(primaryStage, user, questionNull, false);
        		}
        		else { errorLabel.setText("You are not the Author of this Question");}
        		
        	} else {errorLabel.setText("Please Select A Question to Delete");}
        });	
    	
    	//Answer Question Button
    	Button answerQuestion = new Button ("Answer Question");
    			answerQuestion.setOnAction( a-> {
    				if(currentQuestion != null) {
    					AnswerQuestionPage answerQuestionPage = new AnswerQuestionPage(databaseHelper); 
    					answerQuestionPage.show(primaryStage, user, currentQuestion);
    				} else { errorLabel.setText("Please Select A Question to Answer");}
    				
    			});	 
    			
    	//Putting the Buttons inside of the HBOX
    	topHBox.getChildren().addAll(logout, userButton, newQuestion, refresh, answerQuestion, updateCurrentQuestion, deleteCurrentQuestion);
    	topSection.getChildren().addAll(topHBox, errorLabel);
    	questionContent.getChildren().addAll(questionContentTitle, questionText,answersLabelTitle, allAnswers);
    	
    	//Setting up the reset of the page
    	ScrollPane scrollPaneLeft = new ScrollPane(listOfAllQuestions);//Question Titles
    	ScrollPane scrollPaneRight = new ScrollPane(questionContent); //Reading Currently selected questoin and answers
    	
    	//seting some widths of the page
    	scrollPaneLeft.setPrefWidth(200);
    	scrollPaneRight.setFitToWidth(true);
    	
    	//set up the elements orientation
    	layout.setLeft(scrollPaneLeft);
    	layout.setCenter(scrollPaneRight);
	    layout.setStyle("-fx-alignment: center; -fx-padding: 20;");
	    
	    Scene searchDiscussionScene = new Scene(layout, 800, 400);

	    // Set the scene to primary stage
	    primaryStage.setScene(searchDiscussionScene);
	    primaryStage.setTitle("Search Discussion Page");
	    
	    primaryStage.show();
    }

	/**sets a button grey
	 * @param button
	 */
	public void setButtonGrey(Button button) {
	    button.setStyle("-fx-background-color: #D3D3D3;");
	}
	
	/** sets multiple buttons gray 
	 * @param b1
	 * @param b2
	 * @param b3
	 * @param b4
	 */
	public void setMultipleGrey(Button b1,Button b2,Button b3,Button b4) {
	    b1.setStyle("-fx-background-color: #D3D3D3;");
	    b2.setStyle("-fx-background-color: #D3D3D3;");
	    b3.setStyle("-fx-background-color: #D3D3D3;");
	    b4.setStyle("-fx-background-color: #D3D3D3;");
	}
	
	/** sets a button green 
	 * @param button
	 */
	public void setButtonGreen(Button button) {
	    button.setStyle("-fx-background-color: #90EE90;");
	}
	
	/** sets a tag true out of a list of false booleans
	 * @param bools
	 * @param king
	 */
	public void setFalse(Boolean[] bools, boolean king){
	    for(int i = 0; i < bools.length; i++){
	        if(bools[i] != king){
	            bools[i] = false; 
	        }
	    }
	}
		
		
 
}
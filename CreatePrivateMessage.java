package databasePart1;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <h1>CreatePrivateMessage Class</h1>
 * <p>
 * This class allows users to create a private message with allowed recipients based on their role:
 * - Staff: Can message Instructors and other Staff
 * - Students, Reviewers, Instructors: Can message Staff only
 * </p>
 * 
 * @author CSE360 Monday 11
 * @version 1.1
 * @since 2025-04-05
 */
public class CreatePrivateMessage {

    private final DatabaseHelper databaseHelper;
    private final User user;

    /**
     * Constructor for CreatePrivateMessage
     * 
     * @param databaseHelper-The database helper instance
     * @param user The current logged-in user
     */
    public CreatePrivateMessage(DatabaseHelper databaseHelper, User user) {
        this.databaseHelper = databaseHelper;
        this.user = user;
    }

    /**
     * This displays the Create Private Message page, which allows users to select a recipient and start a conversation
     * 
     * @param primaryStage The main application stage
     */
    public void show(Stage primaryStage) {
        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-alignment: center; -fx-padding: 20;");

        Label label = new Label("Select User to Message:");

        ChoiceBox<String> recipientChoiceBox = new ChoiceBox<>();

        // Load all users from database
        List<User> users = databaseHelper.getAllUsers();

        // Filter based on sender's role
        if (user.getRole().equalsIgnoreCase("Staff")) {
            // Staff can message Instructors and other Staff
            users = users.stream()
                    .filter(u -> u.getRole().equalsIgnoreCase("Instructor") || u.getRole().equalsIgnoreCase("Staff"))
                    .collect(Collectors.toList());
        } else if (user.getRole().equalsIgnoreCase("Student") || user.getRole().equalsIgnoreCase("Reviewer") || user.getRole().equalsIgnoreCase("Instructor")) {
            // Students, Reviewers, Instructors can only message Staff
            users = users.stream()
                    .filter(u -> u.getRole().equalsIgnoreCase("Staff"))
                    .collect(Collectors.toList());
        }

        // Populate the choice box
        for (User u : users) {
            recipientChoiceBox.getItems().add(u.getUserName());
        }

        Button nextButton = new Button("Next");
        nextButton.setOnAction(e -> {
            String selectedRecipient = recipientChoiceBox.getValue();
            if (selectedRecipient != null) {
                PrivateMessagePage privateMessagePage = new PrivateMessagePage(databaseHelper);
                privateMessagePage.show(user, selectedRecipient);
            }
        });

        vbox.getChildren().addAll(label, recipientChoiceBox, nextButton);

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Create Private Message");
        primaryStage.show();
    }
}

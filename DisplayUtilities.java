package databasePart1;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import databasePart1.*;

/**
 * This class creates functions that help with creating and setting properties
 * of commonly used JavaFX components. This allows any class to access these 
 * functions and not have to reproduce them in multiple files. 
 */
@SuppressWarnings("unused")
public class DisplayUtilities{
	
	// *************************************************************************************************
	// The following are for creating Button and setting properties for Button
	// *************************************************************************************************
	/**
	 * Takes a text and an x and y coordinate and returns a button with 
	 * these specified characteristics. 
	 * 
	 * @param text This is the text the button will display.
	 * @param x This will be the x coordinate. 
	 * @param y This will be the y coordinate. 
	 * @return Button This will return a Button Object. 
	 */
	public Button createButton (String text, int x, int y) {
		Button temp = new Button();
		temp.setText(text);
		temp.setLayoutX(x);
		temp.setLayoutY(y);
		return temp;
	}
	
	/**
	 * Updates the Button Object to have a background of grey.
	 * @param button Takes a Button Object.
	 */
	public void setButtonGrey(Button button) {
		button.setStyle("-fx-background-color: #D3D3D3;");
	}
	
	/**
	 * Updates the Button Object to have a background of green.
	 * @param button Takes a Button Object.
	 */
	public void setButtonGreen(Button button) {
		button.setStyle("-fx-background-color: #90EE90;");
	}
	
	/**
	 * Updates the Button Object to have a background of red.
	 * @param button Takes a Button Object.
	 */
	public void setButtonRed(Button button) {
		button.setStyle("-fx-background-color: #FFCCCB;");
	}
	
	/**
	 * Updates the Button Object to have specified x and y coordinates. 
	 * @param button Takes Button Object. 
	 * @param xVal	Takes x coordinate. 
	 * @param yVal Takes y cordinate. 
	 */
	public void setButtonLocation(Button button, double xVal, double yVal) {
		button.setLayoutX(xVal);
		button.setLayoutY(yVal);
	}
	
	
	
	// *************************************************************************************************
	// The following are for creating Labels
	// *************************************************************************************************
	/**
	 * Returns a Label with the specified text and at the specified coordinates (x, y).
	 * @param text This is the text that will be displayed in label. 
	 * @param x This is the x coordinate. 
	 * @param y This is the y coordinate. 
	 * @return Label This returns a label with specified characteristics. 
	 */
	public Label createLabel (String text, int x, int y) {
		Label temp = new Label();
		temp.setText(text);
		temp.setLayoutX(x);
		temp.setLayoutY(y);
		return temp;
	}
	
	
	// *************************************************************************************************
	// The following are for creating TextFIELD's
	// *************************************************************************************************
	/**
	 * Returns a TextField with the specified coordinates (x, y).
	 * @param x This is the x coordinate. 
	 * @param y This is the y coordinate. 
	 * @return TextField This returns a TextField with specified characteristics. 
	 */
	public TextField createTextField(int x, int y) {
		TextField temp = new TextField();
		temp.setLayoutX(x);
		temp.setLayoutY(y);
		return temp;
	}
	
	
	// *************************************************************************************************
	// The following are for creating TextAREA's
	// *************************************************************************************************
	/**
	 * Returns a TextArea with the specified characteristics as defined in the parameters. 
	 * @param text This is the text that will be displayed. 
	 * @param wrap This is a boolean that determines whether text will be wrapped. 
	 * @param edit This is a boolean that determines whether the TextArea will be editable by user. 
	 * @param xLocation This is the x coordinate. 
	 * @param yLocation This is the y coordinate. 
	 * @param xPrefSize This sets the x preferred size. 
	 * @param yPrefSize This sets the y preferred size. 
	 * @return TextArea THis returns a TextArea with specified characteristics. 
	 */
	public TextArea createTextArea (String text, Boolean wrap, Boolean edit, int xLocation, int yLocation, int xPrefSize, int yPrefSize) {
		TextArea temp = new TextArea();
		temp.setText(text);
		temp.setWrapText(wrap);
		temp.setEditable(edit);	
		temp.setLayoutX(xLocation);
		temp.setLayoutY(yLocation);
		temp.setPrefSize(xPrefSize, yPrefSize);
		return temp;
	}
	
	
}

package databasePart1;

public class PrivateMessage {
	
/**
 * The PrivateMessages class represents Private Message between two users (Student and Review)
 * It contains the PM's details such as userNames, connectionID, message, time and sender. 
 */
/**
 * <h1>Private Message Class</h1>
 * @author CSE360 Monday 11
 * @version 1.0
 * @since 2025-03-30
 */

    private String stuUsername; 
    private String revUsername; 
    private int connectionID;
    private String message; 
    private int time; 
    private String sender; 

    // Constructor 1 (given sender)
    /**First constructor to make a private message object using the following parameters: 
     * @param stuUsername
     * @param revUsername
     * @param connectionID
     * @param message
     * @param time
     * @param sender
     */
    public PrivateMessage(String stuUsername, String revUsername, int connectionID, String message, int time, String sender){
        this.stuUsername = stuUsername; 
        this.revUsername = revUsername; 
        this.connectionID = connectionID; 
        this.message = message; 
        this.time = time; 
        this.sender = sender; 
    }
    

    // Constructor 2 (Default sender to "Amy Stake")
    /**Second constructor to make a private message object using the previous parameters, but defaults the sender to "Amy Stake" 
     * @param stuUsername
     * @param revUsername
     * @param connectionID
     * @param message
     * @param time
     */
    public PrivateMessage(String stuUsername, String revUsername, int connectionID, String message, int time){
        this.stuUsername = stuUsername; 
        this.revUsername = revUsername; 
        this.connectionID = connectionID; 
        this.message = message; 
        this.time = time; 
        this.sender = "Amy Stake"; 
    }

    // Constructor 3 (Default time to 0)
    /** Third constructor to make a private message object using the previous parameters, but defaults the time to 0 
     * @param stuUsername
     * @param revUsername
     * @param connectionID
     * @param message
     * @param sender
     */
    public PrivateMessage(String stuUsername, String revUsername, int connectionID, String message, String sender){
        this.stuUsername = stuUsername; 
        this.revUsername = revUsername; 
        this.connectionID = connectionID; 
        this.message = message; 
        this.time = 0; 
        this.sender = sender; 
    }

    // Constructor 4 (Default sender to "Amy Stake" + Default time to 0)
    /** Fourth constructor to make a private message object using the previous parameters, but defaults the sender to "Amy Stake" and default time to 0 
     * @param stuUsername
     * @param revUsername
     * @param connectionID
     * @param message
     */
    public PrivateMessage(String stuUsername, String revUsername, int connectionID, String message){
        this.stuUsername = stuUsername; 
        this.revUsername = revUsername; 
        this.connectionID = connectionID; 
        this.message = message; 
        this.time = 0; 
        this.sender = "Amy Stake"; 
    }

    // Getter Methods for each Instance Variable 
    /**Returns the Student Username
     * @return String student Username
     */
    public String getStuUsername() {return stuUsername;}
    
    /**Returns the Reviewer Username
     * @return String Reviewer Username
     */
    public String getRevUsername() {return revUsername;}
    
    /**Returns the connection id of a pm 
     * @return int connectionID
     */
    public int getConnectionID() {return connectionID;}
    
    /**Returns a private message's text 
     * @return String message
     */
    public String getMessage() {return message;}
    
    /**Returns the time Username
     * @return int time
     */
    public int getTime() {return time;}
    
    /**Returns the sender username
     * @return String sender
     */
    public String getSender() {return sender;}
    


    // Setter Methods for each Instance Variable 
    /**Sets the Student Username
     * @param String username
     */
    public void setStuUsername(String username) {stuUsername = username;}
    
    
    /** sets the reviewer username
     * @param username
     */
    public void setRevUsername(String username) {revUsername = username;}
    
    
    /** sets the connection id 
     * @param id
     */
    public void  setConnectionID(int id) {connectionID = id;}
    
    
    /** sets the message 
     * @param message
     */
    public void setMessage(String message) { this.message = message;}
    
    
    /** sets the time 
     * @param time
     */
    public void setTime(int time ) { this.time = time;}
    
    
    /** sets the sender
     * @param username
     */
    public void setSender(String username) {sender = username;}



}

package databasePart1;
//Original Code By Professor Lynn Robert Carter. Original has been Editied by Bracken Peterson Jan 2025
/**
 * The User class represents a user entity in the system.
 * It contains the user's details such as userName, password, and role.
 */
public class User {
    private String userName;
    private String password;
    private String name;  //changed  
    private String email; //changed 
    private String role;  //Not Really used, but still needed to function.
    private Boolean isAdmin = false;
    private Boolean isUser = false;
    private Boolean isStaff = false;
    private Boolean isInstructor = false;
    private Boolean isReviewer = false;
    private Boolean isStudent = false;


    /**
     * Constructor to initialize a new User object with userName, password, and role.
     * @param userName This will be the userName.
     * @param password This will be the password. 
     * @param name This will be the name. 
     * @param email This will be the email for user. 
     * @param role This will be what the role is set to. 
     */
    public User( String userName, String password, String name, String email, String role) {
        this.userName = userName;
        this.password = password;
        this.name = name; 
        this.email = email; 
        this.role = role;
    }
    
    /**
     * A constructor for building reConstructing users on the Database
     * @param name This will be the name. 
     * @param userName This will be the userName.
     * @param email This will be the email for user. 
     * @param isAdmin Boolean if user is admin. 
     * @param isUser Boolean if user is user. 
     * @param isInstructor Boolean if user is instructor. 
     * @param isStaff Boolean if user is staff. 
     * @param isReviewer Boolean if user is reviewer. 
     * @param isStudent Boolean if user is student. 
     */
    public User(String name, String userName, String email, Boolean isAdmin, Boolean isUser, Boolean isInstructor, Boolean isStaff, Boolean isReviewer, Boolean isStudent) {
    	this.name = name; 
    	this.userName = userName;
    	this.email = email; 
        this.isAdmin = isAdmin;
        this.isUser = isUser;
        this.isInstructor = isInstructor;
        this.isStaff = isStaff;
        this.isReviewer = isReviewer;
        this.isStudent = isStudent;
    }
    
    /**
     * Constructor to initialize a new Complete User object with userName, password, and roles.
     * @param userName This will be the userName.
     * @param password This will be the password. 
     * @param name This will be the name. 
     * @param role This will set the role. 
     * @param isAdmin Boolean if user is admin. 
     * @param isUser Boolean if user is user. 
     * @param isInstructor Boolean if user is instructor. 
     * @param isStaff Boolean if user is staff. 
     * @param isReviewer Boolean if user is reviewer. 
     * @param isStudent Boolean if user is student. 
     * @param email This will be the email for user. 
     */
    public User( String userName, String password, String name,  String role, Boolean isAdmin, Boolean isUser, Boolean isInstructor, Boolean isStaff, Boolean isReviewer, Boolean isStudent, String email) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.role = role;
    	this.email = email; 
        this.isAdmin = isAdmin;
        this.isUser = isUser;
        this.isInstructor = isInstructor;
        this.isStaff = isStaff;
        this.isReviewer = isReviewer;
        this.isStudent = isStudent;
    }
    
    /**
     * Sets the role of the user. 
     * @param role This is what the role will be set to. 
     */
    public void setRole(String role) {
    	this.role=role;
    }
    
    // *********************************** Get basic info **************************************
    /**
     * This will return userName as a String. 
     * @return userName 
     */
    public String getUserName() { return userName; }
    /**
     * This will return password as a String. 
     * @return password 
     */
    public String getPassword() { return password; }
    /**
     * This will return name as a String. 
     * @return name 
     */
    public String getName() { return name; }   
    /**
     * This will return email as a String. 
     * @return email 
     */
    public String getEmail() {return email; }   
    /**
     * This will return role as a String. 
     * @return String role 
     */
    public String getRole() { return role; }    
    
    
    //Getters for Role Types
    /**
     * Returns whether user is admin.
     * @return boolean
     */
    public Boolean getIsAdmin() { return isAdmin; }
    /**
     * Returns whether user is user.
     * @return boolean
     */
    public Boolean getIsUser() { return isUser; }
    /**
     * Returns whether user is staff.
     * @return boolean
     */
    public Boolean getIsStaff() { return isStaff; }
    /**
     * Returns whether user is instructor.
     * @return boolean
     */
    public Boolean getIsInstructor() { return isInstructor; }
    /**
     * Returns whether user is reviewer.
     * @return boolean
     */
    public Boolean getIsReviewer() { return isReviewer; }
    /**
     * Returns whether user is student.
     * @return boolean
     */
    public Boolean getIsStudent() { return isStudent; }
    
    
    // ******************** Set all is Role Actions *******************************************
    /**
     * This will set the admin access to the given boolean value. 
     * @param bool Sets whether admin access is granted. 
     */
    public void setIsAdmin(Boolean bool) { isAdmin = bool; }
    /**
     * This will set the user access to the given boolean value. 
     * @param bool Sets whether user access is granted. 
     */
    public void setIsUser(Boolean bool) { isUser = bool; }
    /**
     * This will set the staff access to the given boolean value. 
     * @param bool Sets whether staff access is granted. 
     */
    public void setIsStaff(Boolean bool) { isStaff = bool; }
    /**
     * This will set the instructor access to the given boolean value. 
     * @param bool Sets whether instructor access is granted. 
     */
    public void setIsInstructor(Boolean bool) { isInstructor = bool; }
    /**
     * This will set the reviewer access to the given boolean value. 
     * @param bool Sets whether reviewer access is granted. 
     */
    public void setIsReviewer(Boolean bool) { isReviewer = bool; }
    /**
     * This will set the student access to the given boolean value. 
     * @param bool Sets whether student access is granted. 
     */
    public void setIsStudent(Boolean bool) { isStudent = bool; }
    
    
}

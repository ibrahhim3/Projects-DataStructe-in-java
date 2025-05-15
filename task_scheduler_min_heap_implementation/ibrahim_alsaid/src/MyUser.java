/**
 * Each user has an ID and a priority level.
 */
class MyUser {
    Integer id;
    Integer priority;
    
    /**
     * Constructor to create a new user with specified ID and priority
     * Time Complexity: O(1)
     * 
     * @param id the user s ID
     * @param priority the user s priority (lower values indicate higher priority)
     */
    public MyUser(Integer id, Integer priority) {
        this.id = id;
        this.priority = priority;
    }
    
    /**
     * Gets the user's ID.
     * Time Complexity: O(1)
     * 
     * @return the user's ID
     */
    public Integer getID() {
        return this.id;
    }
    
    /**
     * Gets the user's priority.
     * Time Complexity: O(1)
     * 
     * @return the user's priority
     */
    public Integer getPriority() {
        return this.priority;
    }
}
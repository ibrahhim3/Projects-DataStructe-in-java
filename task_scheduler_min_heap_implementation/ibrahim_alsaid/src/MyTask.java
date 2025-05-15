/**
 * Tasks are compared based on user priority and task ID.
 */
class MyTask implements Comparable<MyTask> {
    MyUser user;
    Integer id;
    
    /**
     * Time Complexity: O(1)
     * 
     * @param user the user who issued the task
     * @param id the task's ID
     */
    public MyTask(MyUser user, Integer id) {
        this.user = user;
        this.id = id;
    }
    
    /**
     * Time Complexity: O(1)
     * 
     * @return a string in the format "Task <id> User <userID>"
     */
    @Override
    public String toString() {
        return "Task " + id + " User " + user.getID();
    }
    
    /**
     * Compares this task with another task based on priority.
     * Time Complexity: O(1)
     * @param other the task to compare with
     * @return negative if this task has higher priority, positive if lower priority, zero if equal
     */
    @Override
    public int compareTo(MyTask other) {
        // Compare based on user priority (lower value means higher priority)
        int priorityComparison = this.user.getPriority().compareTo(other.user.getPriority());
        
        // If priorities are equal, compare based on task ID (lower ID means higher priority)
        if (priorityComparison == 0) {
            return this.id.compareTo(other.id);
        }
        
        return priorityComparison;
    }
}
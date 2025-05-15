import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * TaskManager reads user configuration from a file, accepts tasks from users,
 * and executes them according to their priorities.
 */
public class TaskManager {
    private ArrayList<MyUser> users;
    private MyPriorityQueue<MyTask> taskQueue;
    private int taskIdCounter;
    
    /**
     * Constructor to initialize the TaskManager.
     * Time Complexity: O(1)
     */
    public TaskManager() {
        users = new ArrayList<>();
        taskQueue = new MyHeap<>();
        taskIdCounter = 0;
    }
    
    /**
     * Loads user configuration from a file.
     * Time Complexity: O(n) 
     * 
     * @param configFilePath the path to the configuration file
     * @throws IOException if there's an error reading the file
     */
    public void loadUserConfiguration(String configFilePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(configFilePath))) {
            String line;
            int userId = 0;
            
            while ((line = reader.readLine()) != null) {
                
                if (line.contains("//")) {
                    line = line.substring(0, line.indexOf("//")).trim();
                }
                
                
                if (line.isEmpty()) {
                    continue;
                }
                
                try {
                    int priority = Integer.parseInt(line.trim());
                    users.add(new MyUser(userId, priority));
                    userId++;
                } catch (NumberFormatException e) {
                    System.err.println("Invalid priority value in configuration file: " + line);
                }
            }
        }
    }
    
    /**
     * Processes user inputs for tasks and executes them.
     * Time Complexity: O(n log n)
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String input;
        
        while (scanner.hasNextLine()) {
            input = scanner.nextLine().trim();
            
            if (input.isEmpty()) {
                continue;
            }
            
            if (input.equalsIgnoreCase("execute")) {
                executeTasks();
                break;
            } else {
                try {
                    int userId = Integer.parseInt(input.split("//")[0].trim());
                    addTask(userId);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid user ID: " + input);
                } catch (IndexOutOfBoundsException e) {
                    System.err.println("User ID does not exist: " + input);
                }
            }
        }
        
        scanner.close();
    }
    
    /**
     * Adds a task to the queue.
     * Time Complexity: O(log n) 
     * 
     * @param userId the ID of the user issuing the task
     * @throws IndexOutOfBoundsException if the user ID does not exist
     */
    private void addTask(int userId) throws IndexOutOfBoundsException {
        if (userId < 0 || userId >= users.size()) {
            throw new IndexOutOfBoundsException();
        }
        
        MyUser user = users.get(userId);
        MyTask task = new MyTask(user, taskIdCounter);
        taskQueue.add(task);
        taskIdCounter++;
    }
    
    /**
     * Executes all tasks in the queue according to their priorities.
     * Time Complexity: O(n log n)
     */
    private void executeTasks() {
        while (!taskQueue.isEmpty()) {
            MyTask task = taskQueue.poll();
            System.out.println(task);
        }
    }
    
    /**
     * Main method to run the TaskManager
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java TaskManager <config_file_path>");
            return;
        }
        
        String configFilePath = args[0];
        TaskManager manager = new TaskManager();
        
        try {
            manager.loadUserConfiguration(configFilePath);
            manager.run();
        } catch (IOException e) {
            System.err.println("Error reading configuration file: " + e.getMessage());
        }
    }
}
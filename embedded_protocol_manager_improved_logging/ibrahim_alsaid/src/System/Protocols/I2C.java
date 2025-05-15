package System.Protocols;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

/**
 * implementation of the I2C protocol
 * 
 * @author ibrahim al said
 */
public class I2C implements Protocol {
    private Stack<String> logEntries;
    private String logFile;

    /**
     * Constructs a new I2C protocol instance
     * @param portID The ID of the port
     * @param logDir The directory for log files
     */
    public I2C(int portID, String logDir) {
        this.logFile = logDir + File.separator + getProtocolName() + "_" + portID + ".log";
        this.logEntries = new Stack<>();
        // Add the first log entry
        logEntries.push("Port Opened.");
    }

    /**
     * Gets the name of the protocol
     * @return Protocol name which is "I2C"
     */
    @Override
    public String getProtocolName() {
        return "I2C";
    }
    
   

    /**
     * Reads data from the port
     * @return Fixed data "24.00"
     */
    @Override
    public String read() {
        logEntries.push("Reading.");
        return "24.00"; // fixed value
    }

    /**
     * write data to the port
     * @param data Data to write
     */
    @Override
    public void write(String data) {
        logEntries.push("Writing \"" + data + "\".");
    }

    /**
     * close the port and writes log entries to file in reverse order
     */
    @Override
    public void close() {
        try {
            // create directory if it doesnt exist
            File logDir = new File(logFile).getParentFile();
            if (logDir != null && !logDir.exists()) {
                logDir.mkdirs();
            }
            // write logs to file logs will be in reverse order
            // because we are using a stack with push and pop
            FileWriter writer = new FileWriter(logFile);
            while (!logEntries.isEmpty()) {
                writer.write(logEntries.pop() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}
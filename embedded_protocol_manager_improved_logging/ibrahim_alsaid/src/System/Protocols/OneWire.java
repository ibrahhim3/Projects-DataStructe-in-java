package System.Protocols;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

/**
 * implementation of the OneWire protocol
 */
public class OneWire implements Protocol {
    private Stack<String> logEntries;
    private String logFile;

    /**
     * Constructs a new OneWire protocol instance
     * @param portID the ID of the port
     * @param logDir the directory for log files
     */
    public OneWire(int portID, String logDir) {
        this.logFile = logDir + File.separator + getProtocolName() + "_" + portID + ".log";
        this.logEntries = new Stack<>();
        // To add the first log entry
        logEntries.push("Port Opened.");
    }

    @Override
    public String getProtocolName() {
        return "OneWire";
    }
    
   

    @Override
    public String read() {
        logEntries.push("Reading.");
        return "24.00"; // Fixed value
    }

    @Override
    public void write(String data) {
        logEntries.push("Writing \"" + data + "\".");
    }

    @Override
    public void close() {
        try {
            // create directory if it doesnt exist
            File logDir = new File(logFile).getParentFile();
            if (logDir != null && !logDir.exists()) {
                logDir.mkdirs();
            }
            
            // Write logs to file
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
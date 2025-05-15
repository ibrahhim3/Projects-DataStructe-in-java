package System.Protocols;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

/**
 * implementation of the UART protocol
 */
public class UART implements Protocol {
    private Stack<String> logEntries;
    private String logFile;

    /**
     * Constructs a new UART protocol instance
     * @param portID The ID of the port
     * @param logDir The directory for log files
     */
    public UART(int portID, String logDir) {
        this.logFile = logDir + File.separator + getProtocolName() + "_" + portID + ".log";
        this.logEntries = new Stack<>();
        logEntries.push("Port Opened.");
    }

    @Override
    public String getProtocolName() {
        return "UART";
    }
    
  
    @Override
    public String read() {
        logEntries.push("Reading.");
        return "24.00"; 
    }

    @Override
    public void write(String data) {
        logEntries.push("Writing \"" + data + "\".");
    }

    @Override
    public void close() {
        try {
            File logDir = new File(logFile).getParentFile();
            if (logDir != null && !logDir.exists()) {
                logDir.mkdirs();
            }
            
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
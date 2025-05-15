package System.Protocols;



/**
 * interface for communication protocols
 * 
 * @author ibrahim al said
 */
public interface Protocol {
    /**
     * gets the name of the protocol
     * @return Protocol name
     */
    String getProtocolName();
    
    /**
     * reads data from the port
     * @return data read
     */
    String read();
    
    /**
     * write data to the port
     * @param data data to write
     */
    void write(String data);
    
    /**
     * close the port and write log entries to file
     */
    void close();
    
   
}
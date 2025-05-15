package System.Protocols;

public interface Protocol {
   
    String getProtocolName();

    String read();
    
    void write(String data);
}

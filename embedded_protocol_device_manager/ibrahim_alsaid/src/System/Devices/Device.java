package System.Devices;
import System.Protocols.Protocol;

public abstract class Device {
    protected Protocol protocol;
    protected State state;
    
   
    public Device(Protocol protocol) {
        this.protocol = protocol;
        this.state = State.OFF;
    }
    
    public abstract void turnON();
    
    public abstract void turnOFF();
    
    public abstract String getName();
    
    public abstract String getDevType();
    
    
    public State getState() {
        return state;
    }
    
   
    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }
    
    public Protocol getProtocol() {
        return protocol;
    }
}

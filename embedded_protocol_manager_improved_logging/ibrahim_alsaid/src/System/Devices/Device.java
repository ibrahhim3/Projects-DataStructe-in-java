package System.Devices;

import System.Protocols.Protocol;

/**
 * Abstract base class for all hardware devices
 * Provides common functionality and properties
 * 
 * @author Ibrahim Al Said
 */
public abstract class Device {

    protected Protocol protocol;
    
    /**
     * Current state of the device 
     */
    protected State state;
    
    /**
     * Device ID within its category
     */
    protected int devID;

    /**
     * Constructs a new device
     * @param devID Device ID within its category
     * @param protocol Communication protocol for the device
     */
    public Device(int devID, Protocol protocol) {
        this.devID = devID;
        this.protocol = protocol;
        this.state = State.OFF;  // Default state is OFF
    }
    
    /**
     * Turns on the device
     */
    public abstract void turnON();
    
    /**
     * Turns off the device
     */
    public abstract void turnOFF();
    
    /**
     * Gets the name of the device
     * @return Device name
     */
    public abstract String getName();
    
    /**
     * Gets the type of the device
     * @return Device type
     */
    public abstract String getDevType();
    
    /**
     * Gets the ID of the device within its category
     * @return Device ID
     */
    public abstract int getDevID();
    
    /**
     * Gets the current state of the device
     * @return Current state 
     */
    public State getState() {
        return state;
    }
    
    
    /**
     * Gets the communication protocol used by the device
     * @return Current protocol
     */
    public Protocol getProtocol() {
        return protocol;
    }
    
}
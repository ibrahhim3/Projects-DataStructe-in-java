package System.Devices.Displays;

import System.Devices.Device;
import System.Devices.State;
import System.Protocols.Protocol;

/**
 * Abstract base class for display devices
 * 
 * @author Ibrahim Al Said
 */
public abstract class Display extends Device {
    
    /**
     * Constructs a new display device
     * @param devID Device ID
     * @param protocol Communication protocol
     */
    public Display(int devID, Protocol protocol) {
        super(devID, protocol);
    }
    
    /**
     * Prints data to the display
     * @param data Data to be printed
     */
    public void printData(String data) {
        if (getState() == State.ON) {
            protocol.write(data);
            System.out.println(getName() + ": Printing \"" + data + "\".");
        } else {
            System.err.println("Device is not active.");
            System.err.println("Command failed.");
        }
    }
    
    @Override
    public String getDevType() {
        return "Display";
    }
}
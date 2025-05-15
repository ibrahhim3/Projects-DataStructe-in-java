package System.Devices.Displays;

import System.Devices.State;
import System.Protocols.I2C;
import System.Protocols.Protocol;

/**
 * Implementation of an LCD display device
 * Uses I2C protocol for communication
 * 
 * @author Ibrahim Al Said
 */
public class LCD extends Display {
    
    /**
     * Constructs a new LCD display
     * @param devID Device ID
     * @param protocol Communication protocol (must be I2C)
     * @throws IllegalArgumentException if protocol is not I2C
     */
    public LCD(int devID, Protocol protocol) {
        super(devID, protocol);
        if (!(protocol instanceof I2C)) {
            throw new IllegalArgumentException("LCD is only compatible with I2C protocol");
        }
    }
    
    /**
     * Turns on the LCD display
     */
    @Override
    public void turnON() {
        if (state == State.OFF) {
            System.out.println(getName() + ": Turning ON.");
            protocol.write("turnON");
            state = State.ON;
        }
    }
    
    /**
     * Turns off the LCD display
     */
    @Override
    public void turnOFF() {
        if (state == State.ON) {
            System.out.println(getName() + ": Turning OFF.");
            protocol.write("turnOFF");
            state = State.OFF;
        }
    }
    
    /**
     * Gets the name of the device
     * @return Device name "LCD"
     */
    @Override
    public String getName() {
        return "LCD";
    }

    /**
     * Gets the device ID
     * @return Device ID
     */
    @Override
    public int getDevID() {
        return devID;  // Return the device ID inherited from the Device class
    }
}
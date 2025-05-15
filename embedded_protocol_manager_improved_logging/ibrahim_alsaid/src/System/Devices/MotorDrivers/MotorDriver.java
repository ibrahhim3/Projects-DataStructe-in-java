package System.Devices.MotorDrivers;

import System.Devices.Device;
import System.Devices.State;
import System.Protocols.Protocol;

/**
 * Abstract base class for motor driver devices
 * 
 * @author Ibrahim Al Said
 */
public abstract class MotorDriver extends Device {

    /**
     * Constructs a new motor driver
     * @param devID Device ID
     * @param protocol Communication protocol
     */
    public MotorDriver(int devID, Protocol protocol) {
        super(devID, protocol);
    }

    /**
     * Sets the motor speed
     * @param speed Motor speed value
     */
    public void setMotorSpeed(int speed) {
        if (getState() == State.ON) {
            protocol.write(String.valueOf(speed));
            System.out.println(getName() + ": Setting speed to " + speed + ".");
        } else {
            System.err.println("Device is not active.");
            System.err.println("Command failed.");
        }
    }

    @Override
    public String getDevType() {
        return "MotorDriver";
    }
}
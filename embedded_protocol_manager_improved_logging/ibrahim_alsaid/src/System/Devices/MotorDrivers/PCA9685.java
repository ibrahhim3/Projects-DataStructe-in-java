package System.Devices.MotorDrivers;

import System.Devices.State;
import System.Protocols.I2C;
import System.Protocols.Protocol;

public class PCA9685 extends MotorDriver {

    public PCA9685(int devID, Protocol protocol) {
        super(devID, protocol);  // Pass devID and protocol to the MotorDriver constructor
        if (!(protocol instanceof I2C)) {
            throw new IllegalArgumentException("PCA9685 is only compatible with I2C protocol");
        }
    }

    @Override
    public void turnON() {
        if (getState() == State.OFF) {  // Use getState() from Device class
            System.out.println(getName() + ": Turning ON.");
            getProtocol().write("turnON");  // Use getProtocol() from Device class
            state = State.ON;
        }
    }

    @Override
    public void turnOFF() {
        if (getState() == State.ON) {
            System.out.println(getName() + ": Turning OFF.");
            getProtocol().write("turnOFF");  // Use getProtocol() from Device class
            state = State.OFF;
        }
    }

    @Override
    public String getName() {
        return "PCA9685";
    }

    @Override
    public int getDevID() {
        return devID;  // Return the devID inherited from Device class
    }
}

package System.Sensors.IMUSensors;

import System.Devices.State;
import System.Protocols.I2C;
import System.Protocols.Protocol;

public class MPU6050 extends IMUSensor {
    
    public MPU6050(int devID, Protocol protocol) {
        super(devID, protocol);  // Pass devID and protocol to the IMUSensor constructor
        if (!(protocol instanceof I2C)) {
            throw new IllegalArgumentException("MPU6050 is only compatible with I2C protocol");
        }
    }

    @Override
    public void turnON() {
        if (getState() == State.OFF) {  // Use getState() inherited from Device class
            System.out.println(getName() + ": Turning ON.");
            getProtocol().write("turnON");  // Use getProtocol() inherited from Device class
            state = State.ON;
        }
    }

    @Override
    public void turnOFF() {
        if (getState() == State.ON) {
            System.out.println(getName() + ": Turning OFF.");
            getProtocol().write("turnOFF");  // Use getProtocol() inherited from Device class
            state = State.OFF;
        }
    }

    @Override
    public String getName() {
        return "MPU6050";
    }

    @Override
    public int getDevID() {
        return devID;  // Return the devID inherited from Device class
    }
}

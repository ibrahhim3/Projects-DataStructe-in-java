package System.Sensors.IMUSensors;

import System.Devices.State;
import System.Protocols.I2C;
import System.Protocols.Protocol;


public class MPU6050 extends IMUSensor {
    
   
    public MPU6050(Protocol protocol) {
        super(protocol);
        if (!(protocol instanceof I2C)) {
            throw new IllegalArgumentException("MPU6050 is only compatible with I2C protocol");
        }
    }
    
    @Override
    public void turnON() {
        if (state == State.OFF) {
            System.out.println(getName() + ": Turning ON");
            protocol.write("turnON");
            state = State.ON;
        }
    }
    
    @Override
    public void turnOFF() {
        if (state == State.ON) {
            System.out.println(getName() + ": Turning OFF");
            protocol.write("turnOFF");
            state = State.OFF;
        }
    }
    
    @Override
    public String getName() {
        return "MPU6050";
    }
}
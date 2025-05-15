package System.Devices.Displays;

import System.Devices.State;
import System.Protocols.I2C;
import System.Protocols.Protocol;


public class LCD extends Display {
    
    public LCD(Protocol protocol) {
        super(protocol);
        if (!(protocol instanceof I2C)) {
            throw new IllegalArgumentException("LCD is only compatible with I2C protocol");
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
        return "LCD";
    }
}
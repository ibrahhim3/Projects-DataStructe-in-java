package System.Sensors.IMUSensors;

import System.Devices.State;
import System.Protocols.Protocol;
import System.Protocols.SPI;
import System.Protocols.UART;


public class GY951 extends IMUSensor {
    
    
    public GY951(Protocol protocol) {
        super(protocol);
        if (!(protocol instanceof SPI || protocol instanceof UART)) {
            throw new IllegalArgumentException("GY951 is only compatible with SPI or UART protocols");
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
        return "GY951";
    }
}
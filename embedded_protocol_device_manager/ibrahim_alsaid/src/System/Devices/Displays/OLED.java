package System.Devices.Displays;

import System.Devices.State;
import System.Protocols.Protocol;
import System.Protocols.SPI;

public class OLED extends Display {
    
   
    public OLED(Protocol protocol) {
        super(protocol);
        if (!(protocol instanceof SPI)) {
            throw new IllegalArgumentException("OLED is only compatible with SPI protocol");
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
        return "OLED";
    }
}
package System.Devices.WirelessIOs;

import System.Devices.State;
import System.Protocols.Protocol;
import System.Protocols.UART;

public class Bluetooth extends WirelessIO {
    
   
    public Bluetooth(Protocol protocol) {
        super(protocol);
        if (!(protocol instanceof UART)) {
            throw new IllegalArgumentException("Bluetooth is only compatible with UART protocol");
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
        return "Bluetooth";
    }
}
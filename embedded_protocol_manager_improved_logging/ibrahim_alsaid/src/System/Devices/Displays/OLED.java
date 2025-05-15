package System.Devices.Displays;

import System.Devices.State;
import System.Protocols.Protocol;
import System.Protocols.SPI;

public class OLED extends Display {

    public OLED(int devID, Protocol protocol) {
        super(devID, protocol);  // Pass both devID and protocol to the Display constructor
        if (!(protocol instanceof SPI)) {
            throw new IllegalArgumentException("OLED is only compatible with SPI protocol");
        }
    }

    @Override
    public void turnON() {
        if (getState() == State.OFF) {  // Use getState() inherited from Device class
            System.out.println(getName() + ": Turning ON.");
            getProtocol().write("turnON");  // Use getProtocol() inherited from Device class
            state = State.ON;        }
    }

    @Override
    public void turnOFF() {
        if (getState() == State.ON) {
            System.out.println(getName() + ": Turning OFF.");
            getProtocol().write("turnOFF");  // Use getProtocol() inherited from Device class
            state = State.OFF;        }
    }

    @Override
    public String getName() {
        return "OLED";
    }

    @Override
    public int getDevID() {
        return devID;  // Return the device ID inherited from Device class
    }
}

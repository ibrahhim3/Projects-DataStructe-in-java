package System.Sensors.IMUSensors;

import System.Devices.State;
import System.Protocols.Protocol;
import System.Protocols.SPI;
import System.Protocols.UART;

public class GY951 extends IMUSensor {

    public GY951(int devID, Protocol protocol) {
        super(devID, protocol);  // Pass devID and protocol to the IMUSensor constructor
        if (!(protocol instanceof SPI || protocol instanceof UART)) {
            throw new IllegalArgumentException("GY951 is only compatible with SPI or UART protocols");
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
        return "GY951";
    }

    @Override
    public int getDevID() {
        return devID;  // Return the devID inherited from Device class
    }
}

package System.Devices.WirelessIOs;

import System.Devices.State;
import System.Protocols.Protocol;
import System.Protocols.UART;

public class Bluetooth extends WirelessIO {

    public Bluetooth(int devID, Protocol protocol) {
        super(devID, protocol);  // Pass devID and protocol to the WirelessIO constructor
        if (!(protocol instanceof UART)) {
            throw new IllegalArgumentException("Bluetooth is only compatible with UART protocol");
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
        return "Bluetooth";
    }

    @Override
    public int getDevID() {
        return devID;  // Return the device ID inherited from Device class
    }
}

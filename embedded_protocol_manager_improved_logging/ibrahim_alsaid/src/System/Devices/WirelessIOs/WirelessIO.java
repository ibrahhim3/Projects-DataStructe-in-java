package System.Devices.WirelessIOs;

import System.Devices.Device;
import System.Devices.State;
import System.Protocols.Protocol;

/**
 * Abstract base class for wireless I/O devices
 * 
 * @author Ibrahim Al Said
 */
public abstract class WirelessIO extends Device {

    /**
     * Constructs a new wireless I/O device
     * @param devID Device ID
     * @param protocol Communication protocol
     */
    public WirelessIO(int devID, Protocol protocol) {
        super(devID, protocol);
    }

    /**
     * Sends data via the wireless device
     * @param data Data to be sent
     */
    public void sendData(String data) {
        if (getState() == State.ON) {
            // Pass only the raw data to protocol.write
            protocol.write(data);
            System.out.println(getName() + ": Sending \"" + data + "\".");
        } else {
            System.err.println("Device is not active.");
            System.err.println("Command failed.");
        }
    }

    /**
     * Receives data from the wireless device
     * @return Fixed data string "Some Data"
     */
    public String recvData() {
        if (getState() == State.ON) {
            String data = "Some Data"; 
            protocol.read();
            System.out.println(getName() + ": Received \"" + data + "\".");
            return data;
        } else {
            System.err.println("Device is not active.");
            System.err.println("Command failed.");
            return null;
        }
    }

    @Override
    public String getDevType() {
        return "WirelessIO";
    }
}
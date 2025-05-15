package System.Devices.WirelessIOs;
import System.Devices.Device;
import System.Devices.State;
import System.Protocols.Protocol;


public abstract class WirelessIO extends Device {
    
    
    public WirelessIO(Protocol protocol) {
        super(protocol);
    }
    
   
    public void sendData(String data) {
        if (getState() == State.ON) {
            protocol.write(data);
        } else {
            System.out.println("Device is not active.");
            System.out.println("Command failed.");
        }
    }
   
    public String recvData() {
        if (getState() == State.ON) {
            return protocol.read();
        } else {
            System.out.println("Device is not active.");
            System.out.println("Command failed.");
            return null;
        }
    }
    
    @Override
    public String getDevType() {
        return "WirelessIO";
    }
}


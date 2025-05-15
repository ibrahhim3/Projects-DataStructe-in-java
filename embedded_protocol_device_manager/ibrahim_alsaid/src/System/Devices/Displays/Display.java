package System.Devices.Displays;

import System.Devices.Device;
import System.Devices.State;
import System.Protocols.Protocol;

public abstract class Display extends Device {
    
   
    public Display(Protocol protocol) {
        super(protocol);
    }
    
    
    public void printData(String data) {
        if (getState() == State.ON) {
            protocol.write(data);
        } else {
            System.out.println("Device is not active.");
            System.out.println("Command failed.");
        }
    }
    
    @Override
    public String getDevType() {
        return "Display";
    }
}

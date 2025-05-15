package System.Devices.MotorDrivers;
import System.Devices.Device;
import System.Devices.State;
import System.Protocols.Protocol;

public abstract class MotorDriver extends Device {
    
    
    public MotorDriver(Protocol protocol) {
        super(protocol);
    }
    
    
    public void setMotorSpeed(int speed) {
        if (getState() == State.ON) {
            protocol.write("Speed: " + speed);
        } else {
            System.out.println("Device is not active.");
            System.out.println("Command failed.");
        }
    }
    
    @Override
    public String getDevType() {
        return "MotorDriver";
    }
}

package System.Devices.MotorDrivers;
import System.Devices.State;
import System.Protocols.Protocol;
import System.Protocols.SPI;

public class SparkFunMD extends MotorDriver {
    
    public SparkFunMD(Protocol protocol) {
        super(protocol);
        if (!(protocol instanceof SPI)) {
            throw new IllegalArgumentException("SparkFunMD is only compatible with SPI protocol");
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
        return "SparkFunMD";
    }
}
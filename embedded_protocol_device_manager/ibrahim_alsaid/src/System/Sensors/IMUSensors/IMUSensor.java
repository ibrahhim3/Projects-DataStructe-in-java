package System.Sensors.IMUSensors;
import System.Protocols.Protocol;
import System.Sensors.Sensor;


public abstract class IMUSensor extends Sensor {
    
    public IMUSensor(Protocol protocol) {
        super(protocol);
    }
    
    
    public Float getAccel() {
        protocol.read();
        return (float) (Math.random() * 20 - 10); // random acceleration (-10,10)
    }
    
   
    public Float getRot() {
        protocol.read();
        return (float) (Math.random() * 360); // random rotation (0-360)
    }
    
    @Override
    public String getSensType() {
        return "IMUSensor";
    }
    
    @Override
    public String data2String() {
        return String.format("Accel: %.2f, Rot: %.2f", getAccel(), getRot());
    }
}

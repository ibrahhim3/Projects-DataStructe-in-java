package System.Sensors;
import System.Devices.Device;
import System.Protocols.Protocol;



public abstract class Sensor extends Device {
    
    
    public Sensor(Protocol protocol) {
        super(protocol);
    }
      
    public abstract String getSensType();
    
    public abstract String data2String();
    
    @Override
    public String getDevType() {
        return getSensType() + " Sensor";
    }
}

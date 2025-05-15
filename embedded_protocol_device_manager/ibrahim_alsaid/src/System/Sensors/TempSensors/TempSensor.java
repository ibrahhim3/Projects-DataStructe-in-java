package System.Sensors.TempSensors;
import System.Protocols.Protocol;
import System.Sensors.Sensor;

public abstract class TempSensor extends Sensor {
    
    public TempSensor(Protocol protocol) {
        super(protocol);
    }
    
    public Float getTemp() {
        protocol.read();
        return (float) (Math.random() * 50); // random temperature (0-50)
    }
    
    @Override
    public String getSensType() {
        return "TempSensor";
    }
    
    @Override
    public String data2String() {
        return String.format("Temp: %.2f", getTemp());
    }
}



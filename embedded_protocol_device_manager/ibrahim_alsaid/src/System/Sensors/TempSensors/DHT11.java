package System.Sensors.TempSensors;

import System.Devices.State;
import System.Protocols.OneWire;
import System.Protocols.Protocol;


public class DHT11 extends TempSensor {
    
    
    public DHT11(Protocol protocol) {
        super(protocol);
        if (!(protocol instanceof OneWire)) {
            throw new IllegalArgumentException("DHT11 is only compatible with OneWire protocol");
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
        return "DHT11";
    }
}


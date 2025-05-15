package System.Sensors.TempSensors;

import System.Devices.State;
import System.Protocols.I2C;
import System.Protocols.Protocol;
import System.Protocols.SPI;


public class BME280 extends TempSensor {
    
    
    public BME280(Protocol protocol) {
        super(protocol);
        if (!(protocol instanceof I2C || protocol instanceof SPI)) {
            throw new IllegalArgumentException("BME280 is only compatible with I2C or SPI protocols");
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
        return "BME280";
    }
}

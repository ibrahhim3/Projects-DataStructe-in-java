package System.Sensors.TempSensors;

import System.Devices.State;
import System.Protocols.I2C;
import System.Protocols.Protocol;
import System.Protocols.SPI;

public class BME280 extends TempSensor {

    // Constructor now accepts both devID and protocol
    public BME280(int devID, Protocol protocol) {
        super(devID, protocol);  // Pass devID and protocol to the TempSensor constructor
        if (!(protocol instanceof I2C || protocol instanceof SPI)) {
            throw new IllegalArgumentException("BME280 is only compatible with I2C or SPI protocols");
        }
    }

    @Override
    public void turnON() {
        if (getState() == State.OFF) {  // Use getState() inherited from Device class
            System.out.println(getName() + ": Turning ON.");
            getProtocol().write("turnON");  // Use getProtocol() inherited from Device class
            state = State.ON;
        }
    }

    @Override
    public void turnOFF() {
        if (getState() == State.ON) {
            System.out.println(getName() + ": Turning OFF.");
            getProtocol().write("turnOFF");  // Use getProtocol() inherited from Device class
            state = State.OFF;
        }
    }

    @Override
    public String getName() {
        return "BME280";
    }

    @Override
    public int getDevID() {
        return devID;  // Return the devID inherited from Device class
    }
}

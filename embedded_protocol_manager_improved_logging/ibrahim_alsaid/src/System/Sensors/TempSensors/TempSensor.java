package System.Sensors.TempSensors;

import System.Protocols.Protocol;
import System.Sensors.Sensor;

/**
 * Abstract base class for temperature sensors
 * 
 * @author Ibrahim Al Said
 */
public abstract class TempSensor extends Sensor {

    /**
     * Constructs a new temperature sensor
     * @param devID Device ID
     * @param protocol Communication protocol
     */
    public TempSensor(int devID, Protocol protocol) {
        super(devID, protocol);
    }

    /**
     * Gets the temperature reading
     * @return Fixed temperature value (24.00)
     */
    public Float getTemp() {
        protocol.read();
        return 24.00f; // Fixed value
    }

    @Override
    public String getSensType() {
        return "TempSensor";
    }

    @Override
    public String data2String() {
        return String.format("Temperature: %.2fC", getTemp());
    }
}
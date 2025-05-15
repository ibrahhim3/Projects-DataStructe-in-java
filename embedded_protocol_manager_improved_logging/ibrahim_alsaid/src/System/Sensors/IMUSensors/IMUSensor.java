package System.Sensors.IMUSensors;

import System.Protocols.Protocol;
import System.Sensors.Sensor;

/**
 * Abstract base class for IMU sensors
 * 
 * @author Ibrahim Al Said
 */
public abstract class IMUSensor extends Sensor {

    /**
     * Constructs a new IMU sensor
     * @param devID Device ID
     * @param protocol Communication protocol
     */
    public IMUSensor(int devID, Protocol protocol) {
        super(devID, protocol);
    }

    /**
     * Gets the acceleration reading
     * @return Fixed acceleration value (1.00)
     */
    public Float getAccel() {
        protocol.read();
        return 1.00f; // Fixed value 
    }

    /**
     * Gets the rotation reading
     * @return Fixed rotation value (0.50)
     */
    public Float getRot() {
        protocol.read();
        return 0.50f; // Fixed value
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
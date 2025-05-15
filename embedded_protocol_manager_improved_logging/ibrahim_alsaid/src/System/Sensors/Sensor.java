package System.Sensors;

import System.Devices.Device;
import System.Protocols.Protocol;

public abstract class Sensor extends Device {

    public Sensor(int devID, Protocol protocol) {
        super(devID, protocol);  // Call the constructor of Device
    }
    
    // Abstract method to get the sensor type 
    public abstract String getSensType();
    
    // Abstract method for sensor-specific data as a string 
    public abstract String data2String();
    
    // Override getDevType to include the specific sensor type 
    @Override
    public String getDevType() {
        return getSensType() + " Sensor";  // Combine sensor type and Sensor to form the full type
    }
    
    // Override getDevID to return the device ID from the parent class Device
    @Override
    public int getDevID() {
        return devID;  // Return the devID inherited from the Device class
    }
}

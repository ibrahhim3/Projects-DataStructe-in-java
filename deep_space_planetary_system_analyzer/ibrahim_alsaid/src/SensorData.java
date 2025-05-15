/**
 * SensorData.java
 * Stores environmental measurements from celestial objects
 */
public class SensorData {
    private double temperature; // in Kelvin
    private double pressure;    // in Pascals
    private double humidity;    // percentage (0-100)
    private double radiation;   // in Sieverts

    /**
     * Constructs a SensorData object with specified measurements
     * 
     * @param temperature The temperature in Kelvin
     * @param pressure The pressure in Pascals
     * @param humidity  percentage (0-100)
     * @param radiation 
     */
    public SensorData(double temperature, double pressure, double humidity, double radiation) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.radiation = radiation;
    }

    /**
     * 
     * @return The temperature in Kelvin
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * 
     * @return The pressure in Pascals
     */
    public double getPressure() {
        return pressure;
    }

    /**
     * 
     * @return The humidity percentage (0-100)
     */
    public double getHumidity() {
        return humidity;
    }

    /**
     * 
     * @return The radiation in Sieverts
     */
    public double getRadiation() {
        return radiation;
    }

    /**
     * 
     * @return Formatted string with all sensor measurements and proper units
     */
    @Override
    public String toString() {
        return String.format("%.2f Kelvin, %.2f Pascals, %.2f%%, %.4f Sieverts", 
                temperature, pressure, humidity, radiation);
    }
}
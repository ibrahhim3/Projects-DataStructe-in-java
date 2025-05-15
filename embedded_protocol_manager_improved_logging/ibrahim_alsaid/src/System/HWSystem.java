// AI helped me to remove the Downcasting (I had it in hw1), and get some ideas and advices for the complexity 
// like direct indexing through ArrayList lookups
// And AI helped me to write the javadoc notion or syntax to generate it

package System;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Queue;
import java.util.Scanner;

import System.Devices.Device;
import System.Devices.State;
import System.Devices.Displays.Display;
import System.Devices.Displays.LCD;
import System.Devices.Displays.OLED;
import System.Devices.MotorDrivers.MotorDriver;
import System.Devices.MotorDrivers.PCA9685;
import System.Devices.MotorDrivers.SparkFunMD;
import System.Devices.WirelessIOs.Bluetooth;
import System.Devices.WirelessIOs.Wifi;
import System.Devices.WirelessIOs.WirelessIO;
import System.Protocols.I2C;
import System.Protocols.OneWire;
import System.Protocols.Protocol;
import System.Protocols.SPI;
import System.Protocols.UART;
import System.Sensors.Sensor;
import System.Sensors.IMUSensors.GY951;
import System.Sensors.IMUSensors.MPU6050;
import System.Sensors.TempSensors.BME280;
import System.Sensors.TempSensors.DHT11;

/**
 * Main hardware system class that manages all devices and ports
 * 
 * @author Ibrahim Al Said
 */
public class HWSystem {
    private ArrayList<Protocol> ports;
    private LinkedList<Device> devices; //LinkedList
    
    // direct access by port ID O(1) access
    private ArrayList<Device> portToDevice; 
    
    // direct access by device ID O(1) access
    private ArrayList<Sensor> sensorsByID;
    private ArrayList<Display> displaysByID;
    private ArrayList<WirelessIO> wirelessByID;
    private ArrayList<MotorDriver> motorDriversByID;
    
    // Useing a Queue for command tracking
    private Queue<String> commandLog;

    private int maxSensors;
    private int maxDisplays;
    private int maxWirelessIO;
    private int maxMotorDrivers;
    
    private int sensorCount;
    private int displayCount;
    private int wirelessCount;
    private int motorDriverCount;
    
    private String logDir;
    
    /**
     * Constructs a new HWSystem
     * @param configFile Path to the configuration file
     * @param logDir Directory for log files
     */
    public HWSystem(String configFile, String logDir) {
        this.logDir = logDir;
        ports = new ArrayList<>();
        devices = new LinkedList<>(); 
        commandLog = new LinkedList<>(); 

        loadConfiguration(configFile);
        
        // initialize direct lookup collections
        portToDevice = new ArrayList<>(ports.size());
        for (int i = 0; i < ports.size(); i++) {
            portToDevice.add(null); // Initialize with nulls
        }
        
        sensorsByID = new ArrayList<>(maxSensors);
        displaysByID = new ArrayList<>(maxDisplays);
        wirelessByID = new ArrayList<>(maxWirelessIO);
        motorDriversByID = new ArrayList<>(maxMotorDrivers);
        
        // im initializing with nulls to allow direct indexing
        for (int i = 0; i < maxSensors; i++) {
            sensorsByID.add(null);
        }
        
        for (int i = 0; i < maxDisplays; i++) {
            displaysByID.add(null);
        }
        
        for (int i = 0; i < maxWirelessIO; i++) {
            wirelessByID.add(null);
        }
        
        for (int i = 0; i < maxMotorDrivers; i++) {
            motorDriversByID.add(null);
        }
        
        sensorCount = 0;
        displayCount = 0;
        wirelessCount = 0;
        motorDriverCount = 0;
    }
    
    /**
     * loading the system configuration from a file
     * @param configFile Path to the configuration file
     */
    private void loadConfiguration(String configFile) {
        try {
            File file = new File(configFile);
            Scanner scanner = new Scanner(file);
            
            String portConfig = scanner.nextLine().split(":")[1].trim(); 
            String[] portTypes = portConfig.split(",");
              
            for (int i = 0; i < portTypes.length; i++) {
                String portType = portTypes[i].trim();
                switch (portType) {  
                    case "I2C":
                        ports.add(new I2C(i, logDir));
                        break;
                    case "SPI":
                        ports.add(new SPI(i, logDir));
                        break;
                    case "OneWire":
                        ports.add(new OneWire(i, logDir));
                        break;
                    case "UART":
                        ports.add(new UART(i, logDir));
                        break;
                    default:
                        System.err.println("Unknown protocol type: " + portType);
                }
            }
            
            maxSensors = Integer.parseInt(scanner.nextLine().split(":")[1].trim());
            maxDisplays = Integer.parseInt(scanner.nextLine().split(":")[1].trim());
            maxWirelessIO = Integer.parseInt(scanner.nextLine().split(":")[1].trim());
            maxMotorDrivers = Integer.parseInt(scanner.nextLine().split(":")[1].trim());
            
            scanner.close();
        } catch (Exception e) {
            System.err.println("Error loading configuration: " + e.getMessage());
        }
    }
    
    /**
     * Loging a command for tracking purposes
     * @param command Command to log
     */
    private void logCommand(String command) {
        commandLog.add(command);
    }
    
    /**
     * Closes all port logs
     */
    public void closePorts() {
        Iterator<Protocol> it = ports.iterator();
        while (it.hasNext()) {
            Protocol protocol = it.next();
            protocol.close();
        }
    }
    
    /**
     * Adding a device to the system
     * @param deviceName Name of the device
     * @param portID ID of the port to connect to
     * @param devID ID for the device
     */
    public void addDevice(String deviceName, int portID, int devID) {
        // Check if port ID is valid
        if (portID < 0 || portID >= ports.size()) {
            System.err.println("Invalid port ID: " + portID);
            return;
        }
        
        // Check if port is already occupied - O(1) check
        if (portToDevice.get(portID) != null) {
            System.err.println("Port " + portID + " is already occupied");
            return;
        }
        
        Protocol protocol = ports.get(portID);
        
        try {
            // Createing a device based on name
            switch (deviceName) {
                case "DHT11":
                    if (sensorCount >= maxSensors) {
                        System.err.println("Maximum number of sensors reached");
                        return;
                    }
                    if (devID < 0 || devID >= maxSensors) {
                        System.err.println("Invalid sensor ID: " + devID);
                        return;
                    }
                    // O(1) check if device ID is in use
                    if (sensorsByID.get(devID) != null) {
                        System.err.println("Sensor ID " + devID + " is already in use");
                        return;
                    }
                    DHT11 dht11 = new DHT11(devID, protocol);
                    devices.add(dht11);
                    sensorsByID.set(devID, dht11); // O(1) 
                    portToDevice.set(portID, dht11); // O(1) 
                    sensorCount++;
                    break;
                    
                case "BME280":
                    if (sensorCount >= maxSensors) {
                        System.err.println("Maximum number of sensors reached");
                        return;
                    }
                    if (devID < 0 || devID >= maxSensors) {
                        System.err.println("Invalid sensor ID: " + devID);
                        return;
                    }
                    if (sensorsByID.get(devID) != null) {
                        System.err.println("Sensor ID " + devID + " is already in use");
                        return;
                    }
                    BME280 bme280 = new BME280(devID, protocol);
                    devices.add(bme280);
                    sensorsByID.set(devID, bme280);
                    portToDevice.set(portID, bme280);
                    sensorCount++;
                    break;
                    
                case "MPU6050":
                    if (sensorCount >= maxSensors) {
                        System.err.println("Maximum number of sensors reached");
                        return;
                    }
                    if (devID < 0 || devID >= maxSensors) {
                        System.err.println("Invalid sensor ID: " + devID);
                        return;
                    }
                    if (sensorsByID.get(devID) != null) {
                        System.err.println("Sensor ID " + devID + " is already in use");
                        return;
                    }
                    MPU6050 mpu6050 = new MPU6050(devID, protocol);
                    devices.add(mpu6050);
                    sensorsByID.set(devID, mpu6050);
                    portToDevice.set(portID, mpu6050);
                    sensorCount++;
                    break;
                    
                case "GY951":
                    if (sensorCount >= maxSensors) {
                        System.err.println("Maximum number of sensors reached");
                        return;
                    }
                    if (devID < 0 || devID >= maxSensors) {
                        System.err.println("Invalid sensor ID: " + devID);
                        return;
                    }
                    if (sensorsByID.get(devID) != null) {
                        System.err.println("Sensor ID " + devID + " is already in use");
                        return;
                    }
                    GY951 gy951 = new GY951(devID, protocol);
                    devices.add(gy951);
                    sensorsByID.set(devID, gy951);
                    portToDevice.set(portID, gy951);
                    sensorCount++;
                    break;
                    
                case "LCD":
                    if (displayCount >= maxDisplays) {
                        System.err.println("Maximum number of displays reached");
                        return;
                    }
                    if (devID < 0 || devID >= maxDisplays) {
                        System.err.println("Invalid display ID: " + devID);
                        return;
                    }
                    if (displaysByID.get(devID) != null) {
                        System.err.println("Display ID " + devID + " is already in use");
                        return;
                    }
                    LCD lcd = new LCD(devID, protocol);
                    devices.add(lcd);
                    displaysByID.set(devID, lcd);
                    portToDevice.set(portID, lcd);
                    displayCount++;
                    break;
                    
                case "OLED":
                    if (displayCount >= maxDisplays) {
                        System.err.println("Maximum number of displays reached");
                        return;
                    }
                    if (devID < 0 || devID >= maxDisplays) {
                        System.err.println("Invalid display ID: " + devID);
                        return;
                    }
                    if (displaysByID.get(devID) != null) {
                        System.err.println("Display ID " + devID + " is already in use");
                        return;
                    }
                    OLED oled = new OLED(devID, protocol);
                    devices.add(oled);
                    displaysByID.set(devID, oled);
                    portToDevice.set(portID, oled);
                    displayCount++;
                    break;
                    
                case "Bluetooth":
                    if (wirelessCount >= maxWirelessIO) {
                        System.err.println("Maximum number of wireless adapters reached");
                        return;
                    }
                    if (devID < 0 || devID >= maxWirelessIO) {
                        System.err.println("Invalid wireless adapter ID: " + devID);
                        return;
                    }
                    if (wirelessByID.get(devID) != null) {
                        System.err.println("Wireless adapter ID " + devID + " is already in use");
                        return;
                    }
                    Bluetooth bluetooth = new Bluetooth(devID, protocol);
                    devices.add(bluetooth);
                    wirelessByID.set(devID, bluetooth);
                    portToDevice.set(portID, bluetooth);
                    wirelessCount++;
                    break;
                    
                case "Wifi":
                    if (wirelessCount >= maxWirelessIO) {
                        System.err.println("Maximum number of wireless adapters reached");
                        return;
                    }
                    if (devID < 0 || devID >= maxWirelessIO) {
                        System.err.println("Invalid wireless adapter ID: " + devID);
                        return;
                    }
                    if (wirelessByID.get(devID) != null) {
                        System.err.println("Wireless adapter ID " + devID + " is already in use");
                        return;
                    }
                    Wifi wifi = new Wifi(devID, protocol);
                    devices.add(wifi);
                    wirelessByID.set(devID, wifi);
                    portToDevice.set(portID, wifi);
                    wirelessCount++;
                    break;
                    
                case "PCA9685":
                    if (motorDriverCount >= maxMotorDrivers) {
                        System.err.println("Maximum number of motor drivers reached");
                        return;
                    }
                    if (devID < 0 || devID >= maxMotorDrivers) {
                        System.err.println("Invalid motor driver ID: " + devID);
                        return;
                    }
                    if (motorDriversByID.get(devID) != null) {
                        System.err.println("Motor driver ID " + devID + " is already in use");
                        return;
                    }
                    PCA9685 pca9685 = new PCA9685(devID, protocol);
                    devices.add(pca9685);
                    motorDriversByID.set(devID, pca9685);
                    portToDevice.set(portID, pca9685);
                    motorDriverCount++;
                    break;
                    
                case "SparkFunMD":
                    if (motorDriverCount >= maxMotorDrivers) {
                        System.err.println("Maximum number of motor drivers reached");
                        return;
                    }
                    if (devID < 0 || devID >= maxMotorDrivers) {
                        System.err.println("Invalid motor driver ID: " + devID);
                        return;
                    }
                    if (motorDriversByID.get(devID) != null) {
                        System.err.println("Motor driver ID " + devID + " is already in use");
                        return;
                    }
                    SparkFunMD sparkFunMD = new SparkFunMD(devID, protocol);
                    devices.add(sparkFunMD);
                    motorDriversByID.set(devID, sparkFunMD);
                    portToDevice.set(portID, sparkFunMD);
                    motorDriverCount++;
                    break;
                    
                default:
                    System.err.println("Unknown device: " + deviceName);
                    return;
            }
            
            System.out.println("Device added.");
            
            // log the command
            logCommand("Added " + deviceName + " to port " + portID);
            
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * Remove device from the system
     * @param portID ID of the port the device is connected to
     */
    public void removeDevice(int portID) {
        // Check if port ID is valid
        if (portID < 0 || portID >= ports.size()) {
            System.err.println("Invalid port ID: " + portID);
            return;
        }
    
        // Check if port ID is occupied O(1) 
        Device deviceToRemove = portToDevice.get(portID);
        if (deviceToRemove == null) {
            System.err.println("Port " + portID + " is not occupied");
            return;
        }
    
        // If device is active (ON), we cant remove it
        if (deviceToRemove.getState() == State.ON) {
            System.err.println("Device is active.");
            System.err.println("Command failed.");
            return;
        }
    
        // Remove from devices
        Iterator<Device> deviceIter = devices.iterator();
        while (deviceIter.hasNext()) {
            Device device = deviceIter.next();
            if (device == deviceToRemove) {
                deviceIter.remove();
                break;
            }
        }
        
        // Remove from appropriate collection O(1)
        if (deviceToRemove instanceof Sensor) {
            sensorsByID.set(deviceToRemove.getDevID(), null);
            sensorCount--;
        } else if (deviceToRemove instanceof Display) {
            displaysByID.set(deviceToRemove.getDevID(), null);
            displayCount--;
        } else if (deviceToRemove instanceof WirelessIO) {
            wirelessByID.set(deviceToRemove.getDevID(), null);
            wirelessCount--;
        } else if (deviceToRemove instanceof MotorDriver) {
            motorDriversByID.set(deviceToRemove.getDevID(), null);
            motorDriverCount--;
        }
        
        // Mark port as unoccupied O(1)
        portToDevice.set(portID, null);
    
        System.out.println("Device removed.");
        
        // Log the command
        logCommand("Removed device from port " + portID);
    }
    
    /**
     * Turns on a device
     * @param portID ID of the port the device is connected to
     */
    public void turnDeviceON(int portID) {
        // Check if port ID is valid
        if (portID < 0 || portID >= ports.size()) {
            System.err.println("Invalid port ID: " + portID);
            return;
        }
    
        // Check if port ID is occupied O(1) 
        Device deviceToTurnOn = portToDevice.get(portID);
        if (deviceToTurnOn == null) {
            System.err.println("Port " + portID + " is not occupied");
            return;
        }
    
        // Turn on the device
        deviceToTurnOn.turnON();
        
        // Log the command
        logCommand("Turned ON device at port " + portID);
    }
    
    /**
     * Turns off a device
     * @param portID ID of the port the device is connected to
     */
    public void turnDeviceOFF(int portID) {
        // Check if port ID is valid
        if (portID < 0 || portID >= ports.size()) {
            System.err.println("Invalid port ID: " + portID);
            return;
        }
    
        // Check if port ID is occupied O(1) 
        Device deviceToTurnOff = portToDevice.get(portID);
        if (deviceToTurnOff == null) {
            System.err.println("Port " + portID + " is not occupied");
            return;
        }
    
        // Turn off the device
        deviceToTurnOff.turnOFF();
        
        // Log the command
        logCommand("Turned OFF device at port " + portID);
    }
    
    /**
     * Lists all ports and their status O(n) 
     */
    public void listPorts() {
        System.out.println("list of ports:");
        
        Iterator<Protocol> it = ports.iterator();
        int i = 0;
        while (it.hasNext()) {
            Protocol protocol = it.next();
            Device device = portToDevice.get(i); // O(1) access
            
            StringBuilder sb = new StringBuilder();
            sb.append(i).append(" ").append(protocol.getProtocolName()).append(" ");
            
            if (device != null) {
                sb.append("occupied ").append(device.getName()).append(" ")
                  .append(device.getDevType()).append(" ")
                  .append(device.getDevID()).append(" ")
                  .append(device.getState());
            } else {
                sb.append("empty");
            }
            
            System.out.println(sb.toString());
            i++;
        }
        
        // Log the command
        logCommand("Listed ports");
    }
    
    /**
     * Lists all devices of a specific type O(n)
     * @param deviceType Type of devices to list
     */
    public void listDevices(String deviceType) {
        System.out.println("list of " + deviceType + "s:");
        
        Iterator<Device> it = devices.iterator();
        int count = 0;
        
        while (it.hasNext()) {
            Device device = it.next();
            
            if (device.getDevType().contains(deviceType)) {
                // Find which port this device is on
                int portID = -1;
                for (int i = 0; i < portToDevice.size(); i++) {
                    if (portToDevice.get(i) == device) {
                        portID = i;
                        break;
                    }
                }
                
                if (portID != -1) {
                    Protocol protocol = ports.get(portID);
                    System.out.println(device.getName() + " " + count + " " + 
                                      portID + " " + protocol.getProtocolName());
                    count++;
                }
            }
        }
        
        // Log the command
        logCommand("Listed " + deviceType + " devices");
    }
    
    /**
     * Reads data from a sensor O(1)
     * @param devID ID of the sensor to read from
     */
    public void readSensor(int devID) {
        // O(1) lookup
        Sensor sensor = (devID >= 0 && devID < sensorsByID.size()) ? sensorsByID.get(devID) : null;

        if (sensor == null) {
            System.err.println("No sensor found with ID " + devID);
            return;
        }

        if (sensor.getState() == State.OFF) {
            System.err.println("Device is not active");
            return;
        }

        System.out.println(sensor.getName() + ": " + sensor.getDevType() + ": " + sensor.data2String());
        
        // Log the command
        logCommand("Read sensor " + devID);
    }

    /**
     * Prints data to a display O(1)
     * @param devID ID of the display to print to
     * @param data Data to print
     */
    public void printDisplay(int devID, String data) {
        // O(1) lookup
        Display display = (devID >= 0 && devID < displaysByID.size()) ? displaysByID.get(devID) : null;

        if (display == null) {
            System.err.println("No display found with ID " + devID);
            return;
        }

        if (display.getState() == State.OFF) {
            System.err.println("Device is not active");
            return;
        }

        display.printData(data);
        
        // Log the command
        logCommand("Printed to display " + devID);
    }

    /**
     * Reads data from a wireless device O(1)
     * @param devID ID of the wireless device to read from
     */
    public void readWireless(int devID) {
        // O(1) lookup
        WirelessIO wireless = (devID >= 0 && devID < wirelessByID.size()) ? wirelessByID.get(devID) : null;

        if (wireless == null) {
            System.err.println("No WirelessIO found with ID " + devID);
            return;
        }

        if (wireless.getState() == State.OFF) {
            System.err.println("Device is not active");
            return;
        }

        wireless.recvData();
        
        // Log the command
        logCommand("Read from wireless device " + devID);
    }

    /**
     * Writes data to a wireless device O(1)
     * @param devID ID of the wireless device to write to
     * @param data Data to write
     */
    public void writeWireless(int devID, String data) {
        // O(1) lookup
        WirelessIO wireless = (devID >= 0 && devID < wirelessByID.size()) ? wirelessByID.get(devID) : null;

        if (wireless == null) {
            System.err.println("No WirelessIO found with ID " + devID);
            return;
        }

        if (wireless.getState() == State.OFF) {
            System.err.println("Device is not active");
            return;
        }

        wireless.sendData(data);
        
        // Log the command
        logCommand("Wrote to wireless device " + devID);
    }

    /**
     * Sets the speed of a motor O(1)
     * @param devID ID of the motor driver
     * @param speed Speed to set
     */
    public void setMotorSpeed(int devID, int speed) {
        // O(1) lookup
        MotorDriver motor = (devID >= 0 && devID < motorDriversByID.size()) ? motorDriversByID.get(devID) : null;

        if (motor == null) {
            System.err.println("No MotorDriver found with ID " + devID);
            return;
        }

        if (motor.getState() == State.OFF) {
            System.err.println("Device is not active");
            return;
        }

        motor.setMotorSpeed(speed);
        
        // Log the command
        logCommand("Set motor speed for device " + devID);
    }
}
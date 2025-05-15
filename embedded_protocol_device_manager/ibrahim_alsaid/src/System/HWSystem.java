package System;

import java.io.File;
import java.util.ArrayList;
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

public class HWSystem {
    private ArrayList<Protocol> ports;
    private ArrayList<Device> devices;
    private ArrayList<Integer> occupiedPorts;
    
    private int maxSensors;
    private int maxDisplays;
    private int maxWirelessIO;
    private int maxMotorDrivers;
    
    private int sensorCount;
    private int displayCount;
    private int wirelessCount;
    private int motorDriverCount;
    
   
    public HWSystem(String configFile) {
        ports = new ArrayList<>();
        devices = new ArrayList<>();
        occupiedPorts = new ArrayList<>();
        
        sensorCount = 0;
        displayCount = 0;
        wirelessCount = 0;
        motorDriverCount = 0;
        
        loadConfiguration(configFile);
    }
    
    private void loadConfiguration(String configFile) {
        try {
            File file = new File(configFile);
            Scanner scanner = new Scanner(file);
            
            //configuration and handling all casas
            String portConfig = scanner.nextLine().split(":")[1].trim(); 
            String[] portTypes = portConfig.split(",");
              // triming each protocol type
            for (String portType : portTypes) {
                switch (portType.trim()) {  
                    case "I2C":
                        ports.add(new I2C());
                        break;
                    case "SPI":
                        ports.add(new SPI());
                        break;
                    case "OneWire":
                        ports.add(new OneWire());
                        break;
                    case "UART":
                        ports.add(new UART());
                        break;
                    default:
                        System.out.println("Unknown protocol type: " + portType);
                }
            }
            
            //the devices limits
            maxSensors = Integer.parseInt(scanner.nextLine().split(":")[1].trim());
            maxDisplays = Integer.parseInt(scanner.nextLine().split(":")[1].trim());
            maxWirelessIO = Integer.parseInt(scanner.nextLine().split(":")[1].trim());
            maxMotorDrivers = Integer.parseInt(scanner.nextLine().split(":")[1].trim());
            
            scanner.close();
        } catch (Exception e) {
            System.out.println("error loading configuration: " + e.getMessage());
        }
    }
    
    public void addDevice(String deviceName, int portID, int devID) {
        // if statement to check if port ID is valid or not
        if (portID < 0 || portID >= ports.size()) {
            System.out.println("invalid port ID: " + portID);
            return;
        }
        
        // if statement to check if port is already occupied or not
        if (occupiedPorts.contains(portID)) {
            System.out.println("Port " + portID + " is already occupied");
            return;
        }
        
        Protocol protocol = ports.get(portID);
        Device device = null;
        
        try {
            // creating a device based on the name
            switch (deviceName) {
                case "DHT11":
                    if (sensorCount >= maxSensors) {
                        System.out.println("Maximum number of sensors reached");
                        return;
                    }
                    if (devID < 0 || devID >= maxSensors) {
                        System.out.println("invalid sensor ID: " + devID);
                        return;
                    }
                    if (isDuplicateDevID("Sensor", devID)) {
                        System.out.println("Sensor ID " + devID + " is already in use");
                        return;
                    }
                    device = new DHT11(protocol);
                    sensorCount++;
                    break;
                    
                case "BME280":
                    if (sensorCount >= maxSensors) {
                        System.out.println("Maximum number of sensors reached");
                        return;
                    }
                    if (devID < 0 || devID >= maxSensors) {
                        System.out.println("invalid sensor ID: " + devID);
                        return;
                    }
                    if (isDuplicateDevID("Sensor", devID)) {
                        System.out.println("Sensor ID " + devID + " is already in use");
                        return;
                    }
                    device = new BME280(protocol);
                    sensorCount++;
                    break;
                    
                case "MPU6050":
                    if (sensorCount >= maxSensors) {
                        System.out.println("Maximum number of sensors reached");
                        return;
                    }
                    if (devID < 0 || devID >= maxSensors) {
                        System.out.println("invalid sensor ID: " + devID);
                        return;
                    }
                    if (isDuplicateDevID("Sensor", devID)) {
                        System.out.println("Sensor ID " + devID + " is already in use");
                        return;
                    }
                    device = new MPU6050(protocol);
                    sensorCount++;
                    break;
                    
                case "GY951":
                    if (sensorCount >= maxSensors) {
                        System.out.println("Maximum number of sensors reached");
                        return;
                    }
                    if (devID < 0 || devID >= maxSensors) {
                        System.out.println("invalid sensor ID: " + devID);
                        return;
                    }
                    if (isDuplicateDevID("Sensor", devID)) {
                        System.out.println("Sensor ID " + devID + " is already in use");
                        return;
                    }
                    device = new GY951(protocol);
                    sensorCount++;
                    break;
                    
                case "LCD":
                    if (displayCount >= maxDisplays) {
                        System.out.println("Maximum number of displays reached");
                        return;
                    }
                    if (devID < 0 || devID >= maxDisplays) {
                        System.out.println("invalid display ID: " + devID);
                        return;
                    }
                    if (isDuplicateDevID("Display", devID)) {
                        System.out.println("Display ID " + devID + " is already in use");
                        return;
                    }
                    device = new LCD(protocol);
                    displayCount++;
                    break;
                    
                case "OLED":
                    if (displayCount >= maxDisplays) {
                        System.out.println("Maximum number of displays reached");
                        return;
                    }
                    if (devID < 0 || devID >= maxDisplays) {
                        System.out.println("invalid display ID: " + devID);
                        return;
                    }
                    if (isDuplicateDevID("Display", devID)) {
                        System.out.println("Display ID " + devID + " is already in use");
                        return;
                    }
                    device = new OLED(protocol);
                    displayCount++;
                    break;
                    
                case "Bluetooth":
                    if (wirelessCount >= maxWirelessIO) {
                        System.out.println("Maximum number of wireless adapters reached");
                        return;
                    }
                    if (devID < 0 || devID >= maxWirelessIO) {
                        System.out.println("invalid wireless adapter ID: " + devID);
                        return;
                    }
                    if (isDuplicateDevID("WirelessIO", devID)) {
                        System.out.println("Wireless adapter ID " + devID + " is already in use");
                        return;
                    }
                    device = new Bluetooth(protocol);
                    wirelessCount++;
                    break;
                    
                case "Wifi":
                    if (wirelessCount >= maxWirelessIO) {
                        System.out.println("Maximum number of wireless adapters reached");
                        return;
                    }
                    if (devID < 0 || devID >= maxWirelessIO) {
                        System.out.println("invalid wireless adapter ID: " + devID);
                        return;
                    }
                    if (isDuplicateDevID("WirelessIO", devID)) {
                        System.out.println("Wireless adapter ID " + devID + " is already in use");
                        return;
                    }
                    device = new Wifi(protocol);
                    wirelessCount++;
                    break;
                    
                case "PCA9685":
                    if (motorDriverCount >= maxMotorDrivers) {
                        System.out.println("Maximum number of motor drivers reached");
                        return;
                    }
                    if (devID < 0 || devID >= maxMotorDrivers) {
                        System.out.println("invalid motor driver ID: " + devID);
                        return;
                    }
                    if (isDuplicateDevID("MotorDriver", devID)) {
                        System.out.println("Motor driver ID " + devID + " is already in use");
                        return;
                    }
                    device = new PCA9685(protocol);
                    motorDriverCount++;
                    break;
                    
                case "SparkFunMD":
                    if (motorDriverCount >= maxMotorDrivers) {
                        System.out.println("Maximum number of motor drivers reached");
                        return;
                    }
                    if (devID < 0 || devID >= maxMotorDrivers) {
                        System.out.println("invalid motor driver ID: " + devID);
                        return;
                    }
                    if (isDuplicateDevID("MotorDriver", devID)) {
                        System.out.println("Motor driver ID " + devID + " is already in use");
                        return;
                    }
                    device = new SparkFunMD(protocol);
                    motorDriverCount++;
                    break;
                    
                default:
                    System.out.println("Unknown device: " + deviceName);
                    return;
            }
            
            // mark the device as occupied and store it
            devices.add(device);
            occupiedPorts.add(portID);
            
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    
  
    private boolean isDuplicateDevID(String deviceType, int devID) {
        int count = 0;
        for (Device device : devices) {
            if (device.getDevType().contains(deviceType)) {
                if (count == devID) {
                    return true;
                }
                count++;
            }
        }
        return false;
    }
    
    
    public void removeDevice(int portID) {
        // if statement to check if port ID is valid or not
        if (portID < 0 || portID >= ports.size()) {
            System.out.println("invalid port ID: " + portID);
            return;
        }
        
        // if statement to check if port ID is occupied or not
        if (!occupiedPorts.contains(portID)) {
            System.out.println("Port " + portID + " is not occupied");
            return;
        }
        
        //finding the device the connected to the port
        Device deviceToRemove = null;
        int deviceIndex = -1;
        
        for (int i = 0; i < devices.size(); i++) {
            Device device = devices.get(i);
            if (occupiedPorts.indexOf(portID) == i) {
                deviceToRemove = device;
                deviceIndex = i;
                break;
            }
        }
        
        //device is active
        if (deviceToRemove != null && deviceToRemove.getState() == State.ON) {
            System.out.println("Device is active.");
            System.out.println("Command failed.");
            return;
        }
        
        // to remove the device
        if (deviceIndex != -1) {
            Device device = devices.remove(deviceIndex);
            occupiedPorts.remove(Integer.valueOf(portID));
            
            // Decrementing the counters
            if (device instanceof Sensor) {
                sensorCount--;
            } else if (device instanceof Display) {
                displayCount--;
            } else if (device instanceof WirelessIO) {
                wirelessCount--;
            } else if (device instanceof MotorDriver) {
                motorDriverCount--;
            }
        }
    }
   
    public void turnDeviceON(int portID) {
        // if statement to check if port ID is valid or not
        if (portID < 0 || portID >= ports.size()) {
            System.out.println("invalid port ID: " + portID);
            return;
        }
        
        // if statement to check if port ID is occupied
        if (!occupiedPorts.contains(portID)) {
            System.out.println("Port " + portID + " is not occupied");
            return;
        }
        
        //finding the device the connected to the port
        for (int i = 0; i < devices.size(); i++) {
            if (occupiedPorts.indexOf(portID) == i) {
                Device device = devices.get(i);
                device.turnON();
                break;
            }
        }
    }
    
    
    public void turnDeviceOFF(int portID) {
        // if statement to check if port ID is valid or not
        if (portID < 0 || portID >= ports.size()) {
            System.out.println("invalid port ID: " + portID);
            return;
        }
        
        // if statement to check if port ID is occupied
        if (!occupiedPorts.contains(portID)) {
            System.out.println("Port " + portID + " is not occupied");
            return;
        }
        
        //finding the device the connected to the port
        for (int i = 0; i < devices.size(); i++) {
            if (occupiedPorts.indexOf(portID) == i) {
                Device device = devices.get(i);
                device.turnOFF();
                break;
            }
        }
    }
    
    
    public void listPorts() {
        System.out.println("list of ports:");
        
        for (int i = 0; i < ports.size(); i++) {
            Protocol protocol = ports.get(i);
            boolean isOccupied = occupiedPorts.contains(i);
            
            StringBuilder sb = new StringBuilder();
            sb.append(i).append(" ").append(protocol.getProtocolName()).append(" ");
            
            if (isOccupied) {
                int deviceIndex = occupiedPorts.indexOf(i);
                if (deviceIndex < devices.size()) {
                    Device device = devices.get(deviceIndex);
                    sb.append("occupied ").append(device.getName()).append(" ")
                      .append(device.getDevType()).append(" ")
                      .append(getDeviceID(device)).append(" ")
                      .append(device.getState());
                }
            } else {
                sb.append("empty");
            }
            
            System.out.println(sb.toString());
        }
    }
    
   
    public void listDevices(String deviceType) {
        System.out.println("list of " + deviceType + "s:");
        
        int count = 0;
        for (int i = 0; i < devices.size(); i++) {
            Device device = devices.get(i);
            
            if (device.getDevType().contains(deviceType)) {
                int portID = occupiedPorts.get(i);
                Protocol protocol = ports.get(portID);
                
                System.out.println(device.getName() + " " + count + " " + 
                                  portID + " " + protocol.getProtocolName());
                count++;
            }
        }
    }
    
    
    private int getDeviceID(Device device) {
        String deviceType = "";
        if (device instanceof Sensor) {
            deviceType = "Sensor";
        } else if (device instanceof Display) {
            deviceType = "Display";
        } else if (device instanceof WirelessIO) {
            deviceType = "WirelessIO";
        } else if (device instanceof MotorDriver) {
            deviceType = "MotorDriver";
        }
        
        int count = 0;
        for (Device d : devices) {
            if (d.getDevType().contains(deviceType)) {
                if (d == device) {
                    return count;
                }
                count++;
            }
        }
        
        return -1;
    }
    
    
    public void readSensor(int devID) {
        Device device = getDeviceByTypeAndID("Sensor", devID);
        
        if (device == null) {
            System.out.println("Sensor with ID " + devID + " not found");
            return;
        }
        
        if (device.getState() == State.OFF) {
            System.out.println("Device is not active.");
            System.out.println("Command failed.");
            return;
        }
        
        if (device instanceof Sensor) {
            Sensor sensor = (Sensor) device;
            System.out.println(sensor.getName() + " " + sensor.getDevType() + ": " + sensor.data2String());
        } else {
            System.out.println("Device is not a sensor");
        }
    }
    
    
    public void printDisplay(int devID, String data) {
        Device device = getDeviceByTypeAndID("Display", devID);
        
        if (device == null) {
            System.out.println("Display with ID " + devID + " not found");
            return;
        }
        
        if (device.getState() == State.OFF) {
            System.out.println("Device is not active.");
            System.out.println("Command failed.");
            return;
        }
        
        if (device instanceof Display) {
            Display display = (Display) device;
            display.printData(data);
        } else {
            System.out.println("Device is not a display");
        }
    }
    
   
    public void readWireless(int devID) {
        Device device = getDeviceByTypeAndID("WirelessIO", devID);
        
        if (device == null) {
            System.out.println("Wireless adapter with ID " + devID + " not found");
            return;
        }
        
        if (device.getState() == State.OFF) {
            System.out.println("Device is not active.");
            System.out.println("Command failed.");
            return;
        }
        
        if (device instanceof WirelessIO) {
            WirelessIO wireless = (WirelessIO) device;
            String data = wireless.recvData();
            if (data != null) {
                System.out.println(data);
            }
        } else {
            System.out.println("Device is not a wireless adapter");
        }
    }
    
 
    public void writeWireless(int devID, String data) {
        Device device = getDeviceByTypeAndID("WirelessIO", devID);
        
        if (device == null) {
            System.out.println("Wireless adapter with ID " + devID + " not found");
            return;
        }
        
        if (device.getState() == State.OFF) {
            System.out.println("Device is not active.");
            System.out.println("Command failed.");
            return;
        }
        
        if (device instanceof WirelessIO) {
            WirelessIO wireless = (WirelessIO) device;
            wireless.sendData(data);
        } else {
            System.out.println("Device is not a wireless adapter");
        }
    }
    
   
    public void setMotorSpeed(int devID, int speed) {
        Device device = getDeviceByTypeAndID("MotorDriver", devID);
        
        if (device == null) {
            System.out.println("Motor driver with ID " + devID + " not found");
            return;
        }
        
        if (device.getState() == State.OFF) {
            System.out.println("Device is not active.");
            System.out.println("Command failed.");
            return;
        }
        
        if (device instanceof MotorDriver) {
            MotorDriver motor = (MotorDriver) device;
            motor.setMotorSpeed(speed);
        } else {
            System.out.println("Device is not a motor driver");
        }
    }
    
    
    private Device getDeviceByTypeAndID(String deviceType, int devID) {
        int count = 0;
        for (Device device : devices) {
            if (device.getDevType().contains(deviceType)) {
                if (count == devID) {
                    return device;
                }
                count++;
            }
        }
        return null;
    }
}
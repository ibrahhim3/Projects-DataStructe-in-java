package Main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import System.HWSystem;

/**
 * Main class for the hardware system 
 * Handles input/output and command processing
 * 
 * @author Ibrahim Al Said
 */
public class Main {
    /**
     * Main method, entry point of the program
     * 
     * @param args Command line arguments: configFile logDir
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java Main.Main <configFile> <logDir>");
            System.exit(1);
        }
        
        String configFile = args[0];
        String logDir = args[1];
        
        // Initializing HWSystem with config file and log directory
        HWSystem system = new HWSystem(configFile, logDir);
        Scanner scanner = new Scanner(System.in);
        
        // Store commands in an ArrayList
        ArrayList<String> commands = new ArrayList<>();
        
        // Read commands until exit is received
        String input;
        while (true) {
            input = scanner.nextLine().trim();
            commands.add(input);
            
            if (input.equals("exit")) {
                break;
            }
        }
        
        // Process all stored commands using an iterator
        Iterator<String> cmdIterator = commands.iterator();
        while (cmdIterator.hasNext()) {
            input = cmdIterator.next();
            String[] tokens = input.split("\\s+");
            
            if (tokens.length == 0) {
                continue;
            }
            
            String command = tokens[0];
            
            try {
                switch (command) {
                    case "turnON":
                        if (tokens.length != 2) {
                            System.err.println("Invalid command format. Usage: turnON <portID>");
                            break;
                        }
                        int portID = Integer.parseInt(tokens[1]);
                        system.turnDeviceON(portID);
                        break;
                        
                    case "turnOFF":
                        if (tokens.length != 2) {
                            System.err.println("Invalid command format. Usage: turnOFF <portID>");
                            break;
                        }
                        portID = Integer.parseInt(tokens[1]);
                        system.turnDeviceOFF(portID);
                        break;
                        
                    case "addDev":
                        if (tokens.length != 4) {
                            System.err.println("Invalid command format. Usage: addDev <devName> <portID> <devID>");
                            break;
                        }
                        String deviceName = tokens[1];
                        portID = Integer.parseInt(tokens[2]);
                        int devID = Integer.parseInt(tokens[3]);
                        system.addDevice(deviceName, portID, devID);
                        break;
                        
                    case "rmDev":
                        if (tokens.length != 2) {
                            System.err.println("Invalid command format. Usage: rmDev <portID>");
                            break;
                        }
                        portID = Integer.parseInt(tokens[1]);
                        system.removeDevice(portID);
                        break;
                        
                    case "list":
                        if (tokens.length != 2) {
                            System.err.println("Invalid command format. Usage: list ports|<devType>");
                            break;
                        }
                        if (tokens[1].equals("ports")) {
                            system.listPorts();
                        } else {
                            system.listDevices(tokens[1]);
                        }
                        break;
                        
                    case "readSensor":
                        if (tokens.length != 2) {
                            System.err.println("Invalid command format. Usage: readSensor <devID>");
                            break;
                        }
                        devID = Integer.parseInt(tokens[1]);
                        system.readSensor(devID);
                        break;
                        
                    case "printDisplay":
                        if (tokens.length < 3) {
                            System.err.println("Invalid command format. Usage: printDisplay <devID> <String>");
                            break;
                        }
                        devID = Integer.parseInt(tokens[1]);
                        
                        // Combine remaining tokens for the string to print
                        StringBuilder data = new StringBuilder();
                        for (int i = 2; i < tokens.length; i++) {
                            if (i > 2) {
                                data.append(" ");
                            }
                            data.append(tokens[i]);
                        }
                        
                        system.printDisplay(devID, data.toString());
                        break;
                        
                    case "readWireless":
                        if (tokens.length != 2) {
                            System.err.println("Invalid command format. Usage: readWireless <devID>");
                            break;
                        }
                        devID = Integer.parseInt(tokens[1]);
                        system.readWireless(devID);
                        break;
                        
                    case "writeWireless":
                        if (tokens.length < 3) {
                            System.err.println("Invalid command format. Usage: writeWireless <devID> <String>");
                            break;
                        }
                        devID = Integer.parseInt(tokens[1]);
                        
                        // Combine remaining tokens for the string to send
                        data = new StringBuilder();
                        for (int i = 2; i < tokens.length; i++) {
                            if (i > 2) {
                                data.append(" ");
                            }
                            data.append(tokens[i]);
                        }
                        
                        system.writeWireless(devID, data.toString());
                        break;
                        
                    case "setMotorSpeed":
                        if (tokens.length != 3) {
                            System.err.println("Invalid command format. Usage: setMotorSpeed <devID> <integer>");
                            break;
                        }
                        devID = Integer.parseInt(tokens[1]);
                        int speed = Integer.parseInt(tokens[2]);
                        system.setMotorSpeed(devID, speed);
                        break;
                        
                    case "exit":
                        System.out.println("Exiting ...");
                        break;
                        
                    default:
                        System.err.println("Unknown command: " + command);
                        break;
                }
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        
        // Close all port logs after executing all commands
        system.closePorts();
        
        scanner.close();
    }
}
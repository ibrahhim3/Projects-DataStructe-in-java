package Main;
import java.util.Scanner;
import System.HWSystem;

public class Main {
    public static void main(String[] args) {
        String configFile = "C:\\Users\\we\\OneDrive\\Desktop\\ibrahim_alsaid\\hw1\\src\\config.txt";
        if (args.length > 0) {
            configFile = args[0];
        }
        
        HWSystem system = new HWSystem(configFile);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        System.out.println("The system has been started, type 'exit' to quit");
        
        while (running) {
            System.out.print("Command: ");
            String input = scanner.nextLine().trim();
            String[] tokens = input.split("\\s+");
            
            if (tokens.length == 0) {
                continue;
            }
            
            String command = tokens[0];
            
            try {
                switch (command) {
                    case "turnON":
                        if (tokens.length != 2) {
                            System.out.println("invalid command format. Usage: turnON <portID>");
                            break;
                        }
                        int portID = Integer.parseInt(tokens[1]);
                        system.turnDeviceON(portID);
                        break;
                        
                    case "turnOFF":
                        if (tokens.length != 2) {
                            System.out.println("invalid command format. Usage: turnOFF <portID>");
                            break;
                        }
                        portID = Integer.parseInt(tokens[1]);
                        system.turnDeviceOFF(portID);
                        break;
                        
                    case "addDev":
                        if (tokens.length != 4) {
                            System.out.println("invalid command format. Usage: addDev <devName> <portID> <devID>");
                            break;
                        }
                        String deviceName = tokens[1];
                        portID = Integer.parseInt(tokens[2]);
                        int devID = Integer.parseInt(tokens[3]);
                        system.addDevice(deviceName, portID, devID);
                        break;
                        
                    case "rmDev":
                        if (tokens.length != 2) {
                            System.out.println("invalid command format. Usage: rmDev <portID>");
                            break;
                        }
                        portID = Integer.parseInt(tokens[1]);
                        system.removeDevice(portID);
                        break;
                        
                    case "list":
                        if (tokens.length != 2) {
                            System.out.println("invalid command format. Usage: list ports|<devType>");
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
                            System.out.println("invalid command format. Usage: readSensor <devID>");
                            break;
                        }
                        devID = Integer.parseInt(tokens[1]);
                        system.readSensor(devID);
                        break;
                        
                    case "printDisplay":
                        if (tokens.length < 3) {
                            System.out.println("invalid command format. Usage: printDisplay <devID> <String>");
                            break;
                        }
                        devID = Integer.parseInt(tokens[1]);
                        
                        // to handle string with spaces
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
                            System.out.println("invalid command format. Usage: readWireless <devID>");
                            break;
                        }
                        devID = Integer.parseInt(tokens[1]);
                        system.readWireless(devID);
                        break;
                        
                    case "writeWireless":
                        if (tokens.length < 3) {
                            System.out.println("invalid command format. Usage: writeWireless <devID> <String>");
                            break;
                        }
                        devID = Integer.parseInt(tokens[1]);
                        
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
                            System.out.println("invalid command format. Usage: setMotorSpeed <devID> <integer>");
                            break;
                        }
                        devID = Integer.parseInt(tokens[1]);
                        int speed = Integer.parseInt(tokens[2]);
                        system.setMotorSpeed(devID, speed);
                        break;
                        
                    case "exit":
                        running = false;
                        break;
                        
                    default:
                        System.out.println("unknown command: " + command);
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("invalid number format: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("error: " + e.getMessage());
            }
        }
        
        System.out.println("exiting system...");
        scanner.close();
    }
}
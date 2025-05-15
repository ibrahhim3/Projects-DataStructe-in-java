import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * Main.java
 */

public class Main {
    private static PlanetarySystem planetarySystem = new PlanetarySystem();
    private static Scanner scanner = new Scanner(System.in);

    /**
     *entry point of the program
     */
    public static void main(String[] args) {
        System.out.println("Welcome to the Deep Space Planetary System :)");
        printCommandList(); // Show commands at beginning
        
        boolean running = true;
        while (running) {
            System.out.print("\n> ");
            String input = scanner.nextLine().trim();
            
            try {
                processCommand(input);
            } catch (Exception e) {
                System.out.println("Error processing command: " + e.getMessage());
                
            }
        }
    }
    
    /**
     * Prints the list of available commands
     */
    private static void printCommandList() {
        System.out.println("\nCommands:");
        System.out.println("1. create planetSystem 'starName' 'temperature' 'pressure' 'humidity' 'radiation'");
        System.out.println("2. addPlanet 'planetName' 'parentName' 'temperature' 'pressure' 'humidity' 'radiation'");
        System.out.println("3. addSatellite 'satelliteName' 'parentName' 'temperature' 'pressure' 'humidity' 'radiation'");
        System.out.println("4. findRadiationAnomalies 'threshold'");
        System.out.println("5. getPathTo 'nodeName'");
        System.out.println("6. printMissionReport");
        System.out.println("7. printMissionReport 'nodeName'");
        System.out.println("8. exit");
    }

    /**
     * Processes a user command
     * 
     * @param command The command to process
     */
    private static void processCommand(String command) {
        String[] parts = command.split("\\s+", 2);
        
        if (parts.length == 0) {
            return;
        }
        
        String cmd = parts[0].toLowerCase();
        String args = parts.length > 1 ? parts[1] : "";
        
        switch (cmd) {
            case "create":
                handleCreateCommand(args);
                break;
            case "addplanet":
                handleAddPlanetCommand(args);
                break;
            case "addsatellite":
                handleAddSatelliteCommand(args);
                break;
            case "findradiationanomalies":
                handleFindRadiationAnomaliesCommand(args);
                break;
            case "getpathto":
                handleGetPathToCommand(args);
                break;
            case "printmissionreport":
                handlePrintMissionReportCommand(args);
                break;
            case "exit":
                System.out.println("Exiting program...");
                System.exit(0);
                break;
            default:
                System.out.println("Unknown command: " + cmd);
        }
    }

    /**
     * Handles the create planetSystem command
     * 
     * @param args The command arguments
     */
    private static void handleCreateCommand(String args) {
        String[] parts = args.trim().split("\\s+");
        
        if (parts.length == 0 || !parts[0].equalsIgnoreCase("planetsystem")) {
            System.out.println("Invalid command. Use: create planetSystem 'starName' 'temperature' 'pressure' 'humidity' 'radiation'");
            return;
        }
        
        // Skip the "planetSystem" part and process the remaining parameters
        if (parts.length != 6) {
            System.out.println("Invalid number of parameters. Expected: 'starName' 'temperature' 'pressure' 'humidity' 'radiation'");
            
            return;
        }
        
        String starName = parts[1];
        
        try {
            double temperature = Double.parseDouble(parts[2]);
            double pressure = Double.parseDouble(parts[3]);
            double humidity = Double.parseDouble(parts[4]);
            double radiation = Double.parseDouble(parts[5]);
            
            boolean success = planetarySystem.createPlanetarySystem(starName, temperature, pressure, humidity, radiation);
            
            if (success) {
                System.out.println("Planetary system created with star: " + starName);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
            
        }
    }

    /**
     * Handles the addPlanet command
     * 
     * @param args The command arguments
     */
    private static void handleAddPlanetCommand(String args) {
        String[] parameters = args.trim().split("\\s+");
        
        if (parameters.length != 6) {
            System.out.println("Invalid number of parameters. Expected: 'planetName' 'parentName' 'temperature' 'pressure' 'humidity' 'radiation'");
            
            return;
        }
        
        String planetName = parameters[0];
        String parentName = parameters[1];
        
        try {
            double temperature = Double.parseDouble(parameters[2]);
            double pressure = Double.parseDouble(parameters[3]);
            double humidity = Double.parseDouble(parameters[4]);
            double radiation = Double.parseDouble(parameters[5]);
            
            boolean success = planetarySystem.addPlanet(planetName, parentName, temperature, pressure, humidity, radiation);
            
            if (success) {
                System.out.println("Planet added: " + planetName);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
            
        }
    }

    /**
     * Handles the addSatellite command
     * 
     * @param args The command arguments
     */
    private static void handleAddSatelliteCommand(String args) {
        String[] parameters = args.trim().split("\\s+");
        
        if (parameters.length != 6) {
            System.out.println("Invalid number of parameters. Expected: 'satelliteName' 'parentName' 'temperature' 'pressure' 'humidity' 'radiation'");
            
            return;
        }
        
        String satelliteName = parameters[0];
        String parentName = parameters[1];
        
        try {
            double temperature = Double.parseDouble(parameters[2]);
            double pressure = Double.parseDouble(parameters[3]);
            double humidity = Double.parseDouble(parameters[4]);
            double radiation = Double.parseDouble(parameters[5]);
            
            boolean success = planetarySystem.addSatellite(satelliteName, parentName, temperature, pressure, humidity, radiation);
            
            if (success) {
                System.out.println("Satellite added: " + satelliteName);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
            
        }
    }

    /**
     * Handles the findRadiationAnomalies command
     * 
     * @param args The command arguments
     */
    private static void handleFindRadiationAnomaliesCommand(String args) {
        String[] parameters = args.trim().split("\\s+");
        
        if (parameters.length != 1) {
            System.out.println("Invalid number of parameters. Expected: 'threshold'");
            
            return;
        }
        
        try {
            double threshold = Double.parseDouble(parameters[0]);
            
            List<CelestialNode> anomalies = planetarySystem.findRadiationAnomalies(threshold);
            
            System.out.println("Radiation anomalies (threshold: " + threshold + " Sieverts):");
            if (anomalies.isEmpty()) {
                System.out.println("No anomalies found.");
            } else {
                for (CelestialNode node : anomalies) {
                    System.out.println("- " + node.getName() + " (" + node.getType() + "): " 
                                    + node.getSensorData().getRadiation() + " Sieverts");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        }
    }

    /**
     * Handles the getPathTo command
     * 
     * @param args The command arguments
     */
    private static void handleGetPathToCommand(String args) {
        String nodeName = args.trim();
        
        if (nodeName.isEmpty()) {
            System.out.println("Invalid command. Use: getPathTo 'nodeName'");
            return;
        }
        
        Stack<String> path = planetarySystem.getPathTo(nodeName);
        
        if (path != null) {
            System.out.println("Path to " + nodeName + ":");
            
            // Create a list from the stack to print in correct order
            List<String> orderedPath = new ArrayList<>();
            while (!path.isEmpty()) {
                orderedPath.add(0, path.pop());
            }
            
            System.out.println(String.join(" -> ", orderedPath));
        }
    }

    /**
     * Handles the printMissionReport command
     * 
     * @param args The command arguments
     */
    private static void handlePrintMissionReportCommand(String args) {
        String nodeName = args.trim();
        
        if (nodeName.isEmpty()) {
            planetarySystem.printMissionReport(null);
        } else {
            planetarySystem.printMissionReport(nodeName);
        }
    }
}
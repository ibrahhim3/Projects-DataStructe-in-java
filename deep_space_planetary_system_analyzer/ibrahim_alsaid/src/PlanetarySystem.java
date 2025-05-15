import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * PlanetarySystem.java
 * Implements the tree structure for the planetary system
 */


public class PlanetarySystem {
    private CelestialNode root;

    /**
     * Constructs an empty planetary system
     */
    public PlanetarySystem() {
        this.root = null;
    }

    /**
     * Creates a new planetary system with a star as the root
     * 
     * @param starName The name of the star
     * @param temperature The temperature of the star in Kelvin
     * @param pressure The pressure of the star in Pascals
     * @param humidity Should be 0 for stars
     * @param radiation The radiation of the star in Sieverts
     * @return True if successful, false otherwise
     */
    public boolean createPlanetarySystem(String starName, double temperature, 
                                         double pressure, double humidity, double radiation) {
        if (root != null) {
            System.out.println("Error: Planetary system already exists!");
            return false;
        }
        
        if (humidity != 0) {
            System.out.println("Error: Stars do not have humidity. It must be 0.");
            return false;
        }
        
        SensorData starData = new SensorData(temperature, pressure, humidity, radiation);
        root = new CelestialNode(starName, "Star", starData);
        return true;
    }

/**
 * Adds a planet to the planetary system with a chain structure
 * 
 * @param planetName The name of the planet
 * @param parentName The name of the parent celestial object
 * @param temperature The temperature of the planet in Kelvin
 * @param pressure The pressure of the planet in Pascals
 * @param humidity The humidity of the planet as a percentage
 * @param radiation The radiation of the planet in Sieverts
 * @return True if successful, false otherwise
 */
public boolean addPlanet(String planetName, String parentName, double temperature, 
                         double pressure, double humidity, double radiation) {
    if (root == null) {
        System.out.println("Error: Planetary system not created yet!");
        return false;
    }
    
    CelestialNode parent = findNode(root, parentName);
    if (parent == null) {
        System.out.println("Error: Parent '" + parentName + "' not found!");
        return false;
    }
    

       // Ensure the parent is either a planet or a star
       if (!(parent.getType().equals("Planet") || parent.getType().equals("Star"))) {
        System.out.println("Error: The parent must be either a planet or a star!");
        return false;
       }


    // Check if a node with this name already exists
    if (findNode(root, planetName) != null) {
        System.out.println("Error: A celestial object named '" + planetName + "' already exists!");
        return false;
    }
    
    // Check if the parent already has a planet child
    boolean hasChildPlanet = false;
    for (CelestialNode child : parent.getChildren()) {
        if (child.getType().equals("Planet")) {
            hasChildPlanet = true;
            break;
        }
    }
    
    if (hasChildPlanet) {
        System.out.println("Error: '" + parentName + "' already has a child planet! A celestial object can have at most one child planet.");
        return false;
    }
    
    SensorData planetData = new SensorData(temperature, pressure, humidity, radiation);
    CelestialNode planet = new CelestialNode(planetName, "Planet", planetData);
    parent.addChild(planet);
    return true;
}
   

/**
     * Adds a satellite (moon) to the planetary system
     * 
     * @param satelliteName The name of the satellite
     * @param parentName The name of the parent planet
     * @param temperature The temperature of the satellite in Kelvin
     * @param pressure The pressure of the satellite in Pascals
     * @param humidity The humidity of the satellite as a percentage
     * @param radiation The radiation of the satellite in Sieverts
     * @return True if successful, false otherwise
     */
    public boolean addSatellite(String satelliteName, String parentName, double temperature, 
                              double pressure, double humidity, double radiation) {
        if (root == null) {
            System.out.println("Error: Planetary system not created yet!");
            return false;
        }
        
        CelestialNode parent = findNode(root, parentName);
        if (parent == null) {
            System.out.println("Error: Parent '" + parentName + "' not found!");
            return false;
        }
        
        if (!parent.getType().equals("Planet")) {
            System.out.println("Error: Satellites can only be added to planets!");
            return false;
        }
        
        // Check if a node with this name already exists
        if (findNode(root, satelliteName) != null) {
            System.out.println("Error: A celestial object named '" + satelliteName + "' already exists!");
            return false;
        }
        
        SensorData satelliteData = new SensorData(temperature, pressure, humidity, radiation);
        CelestialNode satellite = new CelestialNode(satelliteName, "Moon", satelliteData);
        parent.addChild(satellite);
        return true;
    }

    /**
     * Finds nodes with radiation levels exceeding a given threshold
     * 
     * @param threshold The radiation threshold in Sieverts
     * @return List of nodes exceeding the radiation threshold
     */
    public List<CelestialNode> findRadiationAnomalies(double threshold) {
        if (root == null) {
            System.out.println("Error: Planetary system not created yet!");
            return new ArrayList<>();
        }
        
        List<CelestialNode> anomalies = new ArrayList<>();
        findRadiationAnomaliesRecursive(root, threshold, anomalies);
        return anomalies;
    }

    /**
     * Helper method to recursively find nodes with radiation anomalies
     * 
     * @param node The current node being checked
     * @param threshold The radiation threshold
     * @param anomalies The list of nodes with anomalies
     */
    private void findRadiationAnomaliesRecursive(CelestialNode node, double threshold, 
                                               List<CelestialNode> anomalies) {
        if (node.getSensorData().getRadiation() > threshold) {
            anomalies.add(node);
        }
        
        for (CelestialNode child : node.getChildren()) {
            findRadiationAnomaliesRecursive(child, threshold, anomalies);
        }
    }

    /**
     * Gets the path from the root to a specific node
     * 
     * @param nodeName The name of the target node
     * @return A stack representing the path from root to the target node, or null if not found
     */
    public Stack<String> getPathTo(String nodeName) {
        if (root == null) {
            System.out.println("Error: Planetary system not created yet!");
            return null;
        }
        
        Stack<String> path = new Stack<>();
        if (getPathToRecursive(root, nodeName, path)) {
            return path;
        } else {
            System.out.println("Error: Node '" + nodeName + "' not found!");
            return null;
        }
    }

    /**
     * Helper method to recursively find the path to a node
     * 
     * @param node The current node being checked
     * @param nodeName The name of the target node
     * @param path The stack to store the path
     * @return True if the path is found, false otherwise
     */
    private boolean getPathToRecursive(CelestialNode node, String nodeName, Stack<String> path) {
        path.push(node.getName());
        
        if (node.getName().equals(nodeName)) {
            return true;
        }
        
        for (CelestialNode child : node.getChildren()) {
            if (getPathToRecursive(child, nodeName, path)) {
                return true;
            }
        }
        
        path.pop();
        return false;
    }

    /**
     * Prints a comprehensive mission report for the entire planetary system or a specific node
     * 
     * @param nodeName Optional name of a specific node, or null for the entire system
     */
    public void printMissionReport(String nodeName) {
        if (root == null) {
            System.out.println("Error: Planetary system not created yet!");
            return;
        }
        
        if (nodeName == null) {
            // Print the entire tree
            System.out.println("Complete Mission Report:");
            printMissionReportRecursive(root, 0);
        } else {
            // Print just the specified node
            CelestialNode node = findNode(root, nodeName);
            if (node == null) {
                System.out.println("Error: Node '" + nodeName + "' not found!");
                return;
            }
            
            System.out.println("Mission Report for " + nodeName + ":");
            SensorData data = node.getSensorData();
            System.out.println("Type: " + node.getType());
            System.out.println("Sensor Data: " + data.toString());
        }
    }

    /**
     * Helper method to recursively print the mission report
     * 
     * @param node The current node being printed
     * @param depth The depth in the tree for indentation
     */
    private void printMissionReportRecursive(CelestialNode node, int depth) {
        StringBuilder indent = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            indent.append("  ");
        }
        
        SensorData data = node.getSensorData();
        System.out.println(indent + "- " + node.getName() + " (" + node.getType() + "): " +
                           String.format("%.2f Kelvin, %.2f Pascals, %.2f%%, %.4f Sieverts", 
                           data.getTemperature(), data.getPressure(), data.getHumidity(), data.getRadiation()));
        
        for (CelestialNode child : node.getChildren()) {
            printMissionReportRecursive(child, depth + 1);
        }
    }

    /**
     * Helper method to find a node by name in the tree
     * 
     * @param node The current node being checked
     * @param name The name of the node to find
     * @return The found node or null if not found
     */
    private CelestialNode findNode(CelestialNode node, String name) {
        if (node.getName().equals(name)) {
            return node;
        }
        
        for (CelestialNode child : node.getChildren()) {
            CelestialNode found = findNode(child, name);
            if (found != null) {
                return found;
            }
        }
        
        return null;
    }
}
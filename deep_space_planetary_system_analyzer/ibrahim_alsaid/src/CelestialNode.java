import java.util.ArrayList;
import java.util.List;

/**
 * CelestialNode.java
 * Represents a node in the planetary system tree
 */


public class CelestialNode {
    private String name;
    private String type;
    private SensorData sensorData;
    private List<CelestialNode> children;

    /**
     * 
     * @param name The name of the celestial object
     * @param type (Star, Planet, Moon)
     * @param sensorData The sensor data associated with this celestial object
     */
    public CelestialNode(String name, String type, SensorData sensorData) {
        this.name = name;
        this.type = type;
        this.sensorData = sensorData;
        this.children = new ArrayList<>();
    }

    /**
     * 
     * @return The name of the celestial object
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @return The type of the celestial object (Star, Planet, Moon)
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @return The sensor data object
     */
    public SensorData getSensorData() {
        return sensorData;
    }

    /**
     * 
     * @return List of child celestial nodes
     */
    public List<CelestialNode> getChildren() {
        return children;
    }

    /**
     * Adds a child node to this celestial object
     * 
     * @param child The child node to be added
     */
    public void addChild(CelestialNode child) {
        children.add(child);
    }

    /**
     * Finds a child node by name
     * 
     * @param name The name of the child node to find
     * @return The found child node or null if not found
     */
    public CelestialNode findChildByName(String name) {
        for (CelestialNode child : children) {
            if (child.getName().equals(name)) {
                return child;
            }
        }
        return null;
    }
}
package puzzles.common.solver;

import java.util.*;

/**
 * A Solver class that uses BFS to solve the clock puzzle and water puzzle
 * using Configurations of respective classes (Clock and Water).
 *
 * @author Ishan Shah
 */
public class Solver {

    /** A field to keep track of all the configs generated */
    private int totalConfigs;

    /** A field to keep track of all the unique configs generated */
    private int uniqueConfigs;

    /** Predecessor map to find the shortest path and avoid cycles */
    private final HashMap<Configuration, Configuration> predMap;

    /** Queue for the BFS algorithm */
    private final Queue<Configuration> queue;

    /** The shortest path found */
    private final ArrayList<Configuration> path;

    /** The configuration */
    private Configuration config;


    /**
     * Get the total of configurations
     *
     * @return total of configurations
     */
    public int getTotalConfigs() {
        return totalConfigs;
    }

    /**
     * Get the total of unique configurations
     *
     * @return total of unique configurations
     */
    public int getUniqueConfigs() {
        return uniqueConfigs;
    }

    /**
     * Constructor for the Solver class
     * Initializes variables to default values
     *
     * @param config Configuration of the puzzle
     */
    public Solver(Configuration config) {
        this.totalConfigs = 1;
        this.uniqueConfigs = 1;
        this.predMap = new HashMap<>();
        this.queue = new LinkedList<>();
        this.path = new ArrayList<>();
        this.config = config;
    }

    /**
     * Solve function that attempts to solve the puzzle using BFS
     *
     * @return Optional of the collection of Configurations as the path or empty.
     */
    public Optional<ArrayList<Configuration>> solve() {
        predMap.put(config, null);
        queue.add(config);

        while (!queue.isEmpty()) {
            config = queue.remove();
            if (config.isGoal()) {
                return Optional.of(constructPath(predMap));
            } else {
                Collection<Configuration> successors = config.getSuccessors();
                for (Configuration n : successors) {
                    if (!predMap.containsKey(n)) {
                        predMap.put(n, config);
                        queue.add(n);
                        uniqueConfigs++;
                    }
                    totalConfigs++;
                }
            }
        }
        return Optional.empty();
    }

    /**
     * Builds the path from the predecessor map
     *
     * @param map predecessor map of Configurations
     * @return path as a collection of Configurations
     */
    private ArrayList<Configuration> constructPath(HashMap<Configuration, Configuration> map) {
        while (map.get(config) != null) {
            path.add(0, config);
            config = map.get(config);
        }
        path.add(0, config);
        return path;
    }

}

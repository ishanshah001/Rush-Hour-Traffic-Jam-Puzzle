package puzzles.common.solver;

import java.util.Collection;

/**
 * Configuration interface to get successors and check if the config is at the goal
 *
 * @author Ishan Shah
 */
public interface Configuration {
    /**
     * Finds the successors of a current Configuration
     *
     * @return a collection of Configurations
     */
    Collection<Configuration> getSuccessors();

    /**
     * Checks if the current Configuration is the goal
     *
     * @return true if reached the goal else false
     */
    boolean isGoal();
}

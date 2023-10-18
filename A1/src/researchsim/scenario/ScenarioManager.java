package researchsim.scenario;

import java.util.*;

/**
 * A singleton class which manages all the scenarios items
 */
public class ScenarioManager {

    /** Static variable reference of scenarioInstance */
    private static ScenarioManager scenarioInstance = null;

    /** The current scenario being selected */
    private Scenario currentScenario;

    /** A linked hash map to store all scenarios in insertion order */
    private LinkedHashMap<String, Scenario> scenarioMap = new LinkedHashMap<String, Scenario>();

    /**
     * Private constructor restricted to this class itself
     */
    private ScenarioManager() {
        currentScenario = null;
    }

    /**
     * Return the singleton instance of the scenario manager
     * @return singletonInstance The instance of scenario manager
     */
    public static ScenarioManager getInstance() {
        if (scenarioInstance == null) {
            scenarioInstance = new ScenarioManager();
        }
        return scenarioInstance;
    }

    /**
     * Return the current scenario from the manager
     * @return currentScenario The current scenario or null if none has been set
     */
    public Scenario getScenario() {
        return currentScenario;
    }

    /**
     * Set the current scenario from the manager
     * @param scenarioName The name of the scenario to set
     */
    public void setScenario(String scenarioName) {
        currentScenario = scenarioMap.get(scenarioName);
    }

    /**
     * Get all the loaded scenarios in a map
     * The items appear in the map is in insertion order
     * @return scenarioMapCopy All the scenarios that have been loaded
     */
    public Map<String, Scenario> getLoadedScenarios() {
        LinkedHashMap<String, Scenario> scenarioMapCopy = new LinkedHashMap<String, Scenario>();

        //A deep copy version of scenarioMap
        scenarioMapCopy.putAll(scenarioMap);
        return scenarioMapCopy;
    }

    /**
     * Register a scenario with the manager
     * Setting the added scenario as the "active" scenario
     * @param scenario A scenario to register with the manager
     */
    public void addScenario(Scenario scenario) {
        scenarioMap.put(scenario.getName(), scenario);
        setScenario(scenario.getName());
    }

    /**
     * Reset the singleton by clearing all recorded scenarios
     */
    public void reset() {
        scenarioMap.clear();
    }
}

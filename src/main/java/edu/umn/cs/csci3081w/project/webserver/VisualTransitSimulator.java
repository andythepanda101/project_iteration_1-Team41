package edu.umn.cs.csci3081w.project.webserver;

import edu.umn.cs.csci3081w.project.model.Bus;
import edu.umn.cs.csci3081w.project.model.Counter;
import edu.umn.cs.csci3081w.project.model.Line;
import edu.umn.cs.csci3081w.project.model.Route;
import edu.umn.cs.csci3081w.project.model.StorageFacility;
import edu.umn.cs.csci3081w.project.model.Train;
import edu.umn.cs.csci3081w.project.model.Vehicle;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for VTS, responsible for running the simulation.
 */
public class VisualTransitSimulator {

  private static boolean LOGGING = false;
  private int numTimeSteps = 0;
  private int simulationTimeElapsed = 0;
  private Counter counter;
  private List<Route> routes;
  private List<Vehicle> activeVehicles;
  private List<Vehicle> completedTripVehicles;
  private List<Integer> vehicleStartTimings;
  private List<Integer> timeSinceLastVehicle;
  private StorageFacility storageFacility;

  /**
   * Constructor for Simulation.
   *
   * @param configFile file containing the simulation configuration
   */
  public VisualTransitSimulator(String configFile) {
    this.counter = new Counter();
    ConfigManager configManager = new ConfigManager();
    configManager.readConfig(counter, configFile);
    this.routes = configManager.getRoutes();
    this.storageFacility = configManager.getStorageFacility();
    this.activeVehicles = new ArrayList<Vehicle>();
    this.completedTripVehicles = new ArrayList<Vehicle>();
    this.vehicleStartTimings = new ArrayList<Integer>();
    this.timeSinceLastVehicle = new ArrayList<Integer>();
    if (VisualTransitSimulator.LOGGING) {
      System.out.println("////Simulation Routes////");
      for (int i = 0; i < routes.size(); i++) {
        routes.get(i).report(System.out);
      }
    }
  }

  /**
   * Starts the simulation.
   *
   * @param vehicleStartTimings start timings of bus
   * @param numTimeSteps        number of time steps
   */
  public void start(List<Integer> vehicleStartTimings, int numTimeSteps) {
    this.vehicleStartTimings = vehicleStartTimings;
    this.numTimeSteps = numTimeSteps;
    for (int i = 0; i < vehicleStartTimings.size(); i++) {
      this.timeSinceLastVehicle.add(i, 0);
    }
    simulationTimeElapsed = 0;
  }

  /**
   * Updates the simulation at each step.
   */
  public void update() {
    simulationTimeElapsed++;
    if (simulationTimeElapsed > numTimeSteps) {
      return;
    }
    System.out.println("~~~~The simulation time is now at time step "
        + simulationTimeElapsed + "~~~~");
    // generate vehicles
    for (int i = 0; i < timeSinceLastVehicle.size(); i++) {
      if (timeSinceLastVehicle.get(i) <= 0) {
        Route outbound = routes.get(2 * i);
        Route inbound = routes.get(2 * i + 1);
        if (outbound.getLineType().equals(Route.BUS_LINE)
            && inbound.getLineType().equals(Route.BUS_LINE)) {
          // checks if the bus is available according to storage facility
          if (storageFacility.isBusAvailable()) {
            activeVehicles
                .add(new Bus(counter.getBusIdCounterAndIncrement(),
                    new Line(outbound.shallowCopy(), inbound.shallowCopy()),
                    Bus.CAPACITY, Bus.SPEED));
            storageFacility.decrementBuses();
          }
          timeSinceLastVehicle.set(i, vehicleStartTimings.get(i));
          timeSinceLastVehicle.set(i, timeSinceLastVehicle.get(i) - 1);
        } else if (outbound.getLineType().equals(Route.TRAIN_LINE)
            && inbound.getLineType().equals(Route.TRAIN_LINE)) {
          // checks if the train is available according to storage facility
          if (storageFacility.isTrainAvailable()) {
            activeVehicles
                .add(new Train(counter.getTrainIdCounterAndIncrement(),
                    new Line(outbound.shallowCopy(), inbound.shallowCopy()),
                    Train.CAPACITY, Train.SPEED));
            storageFacility.decrementTrains();
          }
          timeSinceLastVehicle.set(i, vehicleStartTimings.get(i));
          timeSinceLastVehicle.set(i, timeSinceLastVehicle.get(i) - 1);
        }
      } else {
        timeSinceLastVehicle.set(i, timeSinceLastVehicle.get(i) - 1);
      }
    }
    // update vehicles
    for (int i = activeVehicles.size() - 1; i >= 0; i--) {
      Vehicle currVehicle = activeVehicles.get(i);
      currVehicle.update();
      if (currVehicle.isTripComplete()) {
        Vehicle completedTripVehicle = activeVehicles.remove(i);
        completedTripVehicles.add(completedTripVehicle);
        if (completedTripVehicle instanceof Bus) {
          storageFacility.incrementBuses();
        } else if (completedTripVehicle instanceof Train) {
          storageFacility.incrementTrains();
        }
      } else {
        if (VisualTransitSimulator.LOGGING) {
          currVehicle.report(System.out);
        }
      }
    }
    // update routes
    for (int i = 0; i < routes.size(); i++) {
      Route currRoute = routes.get(i);
      currRoute.update();
      if (VisualTransitSimulator.LOGGING) {
        currRoute.report(System.out);
      }
    }
  }

  public List<Route> getRoutes() {
    return routes;
  }

  public List<Vehicle> getActiveVehicles() {
    return activeVehicles;
  }

  public StorageFacility getStorageFacility() {
    return storageFacility;
  }
}

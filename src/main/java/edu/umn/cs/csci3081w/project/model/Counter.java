package edu.umn.cs.csci3081w.project.model;

/**
 * A class that keeps count of the IDs in the simulation.
 */
public class Counter {

  public int routeIdCounter = 10;
  public int stopIdCounter = 100;
  public int busIdCounter = 1000;
  public int trainIdCounter = 2000;

  /**
   * Empty constructor for the Counter.
   */
  public Counter() {
  }

  public int getRouteIdCounterAndIncrement() {
    return routeIdCounter++;
  }

  public int getStopIdCounterAndIncrement() {
    return stopIdCounter++;
  }

  public int getBusIdCounterAndIncrement() {
    return busIdCounter++;
  }

  public int getTrainIdCounterAndIncrement() {
    return trainIdCounter++;
  }

}

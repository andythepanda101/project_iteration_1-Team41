package edu.umn.cs.csci3081w.project.model;

/**
 * A class that represents StorageFacility in the simulation.
 */
public class StorageFacility {
  private int busesNum;
  private int trainsNum;

  /**
   * Constructor for storage facility.
   * @param busesNum  number of buses available
   * @param trainsNum number of trains available
   */
  public StorageFacility(int busesNum, int trainsNum) {
    this.busesNum = busesNum;
    this.trainsNum = trainsNum;
  }

  public int getBusesNum() {
    return busesNum;
  }

  public int getTrainsNum() {
    return trainsNum;
  }

  public void setBusesNum(int busesNum) {
    this.busesNum = busesNum;
  }

  public void setTrainsNum(int trainsNum) {
    this.trainsNum = trainsNum;
  }

  /**
   * Check if there is a bus available in storage facility.
   * @return true if there at least 1 bus in the facility, false otherwise
   */
  public boolean isBusAvailable() {
    return busesNum > 0;
  }

  /**
   * Check if there is a train available in storage facility.
   * @return true if there at least 1 train in the facility, false otherwise
   */
  public boolean isTrainAvailable() {
    return trainsNum > 0;
  }

  /**
   * Decrements number of buses in the storage facility.
   */
  public void decrementBuses() {
    busesNum--;
  }

  /**
   * Decrements number of trains in the storage facility.
   */
  public void decrementTrains() {
    trainsNum--;
  }

  /**
   * Increments number of buses in the storage facility.
   */
  public void incrementBuses() {
    busesNum++;
  }

  /**
   * Increments number of trains in the storage facility.
   */
  public void incrementTrains() {
    trainsNum++;
  }

}

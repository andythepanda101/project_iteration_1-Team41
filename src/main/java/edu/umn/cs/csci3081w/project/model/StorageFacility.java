package edu.umn.cs.csci3081w.project.model;

public class StorageFacility {
  private int busesNum;
  private int trainsNum;

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

  public boolean isBusAvailable() {
    return busesNum > 0;
  }

  public boolean isTrainAvailable() {
    return trainsNum > 0;
  }

  public void decrementBuses() {
    busesNum--;
  }

  public void decrementTrains() {
    trainsNum--;
  }

  public void incrementBuses() {
    busesNum++;
  }

  public void incrementTrains() {
    trainsNum++;
  }

}

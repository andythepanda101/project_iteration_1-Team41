package edu.umn.cs.csci3081w.project.model;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class that represents a Vehicle in the simulation.
 */
public abstract class Vehicle {
  private int id;
  private int capacity;
  //the speed is in distance over a time unit
  private double speed;
  private PassengerLoader loader;
  private PassengerUnloader unloader;
  private List<Passenger> passengers;
  private String name;
  private Position position;


  /**
   * Constructor for a vehicle.
   *
   * @param id       vehicle identifier
   * @param capacity vehicle capacity
   * @param speed    vehicle speed
   * @param loader   passenger loader for vehicle
   * @param unloader passenger unloader for vehicle
   */
  public Vehicle(int id, int capacity, double speed, PassengerLoader loader,
                 PassengerUnloader unloader) {
    this.id = id;
    this.capacity = capacity;
    this.speed = speed;
    this.loader = loader;
    this.unloader = unloader;
    this.passengers = new ArrayList<Passenger>();
  }

  /**
   * Reports statistics for the vehicle.
   * @param out stream for printing
   */
  public abstract void report(PrintStream out);

  /**
   * Checking if the trip is done.
   * @return true if vehicle completed the trip, false otherwise
   *
   */
  public abstract boolean isTripComplete();

  /**
   * Loads the passengers onto a vehicle.
   * @param newPassenger a passenger to be loaded
   * @return number of loaded passengers
   */
  public abstract int loadPassenger(Passenger newPassenger);

  /**
   * Moves the vehicle.
   */
  public abstract void move();

  /**
   * Updates the vehicle.
   */
  public abstract void update();

  public int getId() {
    return id;
  }

  public int getCapacity() {
    return capacity;
  }

  public double getSpeed() {
    return speed;
  }

  public PassengerLoader getPassengerLoader() {
    return loader;
  }

  public PassengerUnloader getPassengerUnloader() {
    return unloader;
  }

  public List<Passenger> getPassengers() {
    return passengers;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Position getPosition() {
    return position;
  }

  public void setPosition(Position position) {
    this.position = position;
  }
}

package edu.umn.cs.csci3081w.project.model;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract class that sets up passenger generation.
 */
public abstract class PassengerGenerator {
  private List<Stop> stops;
  private List<Double> probabilities;

  /**
   * Constructor for Abstract class.
   *
   * @param stops         list of stops
   * @param probabilities list of probabilities
   */
  public PassengerGenerator(List<Stop> stops, List<Double> probabilities) {
    this.probabilities = new ArrayList<>();
    this.stops = new ArrayList<>();
    for (Stop s : stops) {
      this.stops.add(s);
    }
    for (Double probability : probabilities) {
      this.probabilities.add(probability);
    }
  }

  /**
   * Generates passengers.
   * @return number of generated passengers
   */
  public abstract int generatePassengers();

  public List<Stop> getStops() {
    return stops;
  }

  public List<Double> getProbabilities() {
    return probabilities;
  }

}
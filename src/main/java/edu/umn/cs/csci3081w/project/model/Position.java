package edu.umn.cs.csci3081w.project.model;

/**
 * A class that represents a location.
 */
public class Position {

  private double longitude;
  private double latitude;

  /**
   * Constructor for Position.
   * @param longitude east-west position
   * @param latitude  north-south position
   */
  public Position(double longitude, double latitude) {
    this.longitude = longitude;
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public double getLatitude() {
    return latitude;
  }

}

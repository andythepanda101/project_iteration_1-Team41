package edu.umn.cs.csci3081w.project.model;

/**
 * A wrapper class that contains outbound and inbound route for the vehicle.
 */
public class Line {
  private final Route outboundRoute;
  private final Route inboundRoute;

  /**
   * Constructor for a line.
   * @param outboundRoute first (outbound) route in the line
   * @param inboundRoute  second (inbound) route in the line
   */
  public Line(Route outboundRoute, Route inboundRoute) {
    this.outboundRoute = outboundRoute;
    this.inboundRoute = inboundRoute;
  }

  public Route getInboundRoute() {
    return inboundRoute;
  }

  public Route getOutboundRoute() {
    return outboundRoute;
  }

}

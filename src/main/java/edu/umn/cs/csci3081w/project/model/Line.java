package edu.umn.cs.csci3081w.project.model;

public class Line {
  private final Route outboundRoute;
  private final Route inboundRoute;

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

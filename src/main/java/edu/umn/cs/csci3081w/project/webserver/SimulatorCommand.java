package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonObject;

/**
 * An abstract class for simulation commands.
 */
public abstract class SimulatorCommand {
  /**
   * Accepts command for the simulation.
   * @param session current simulation session
   * @param command an appropriate command to be executed
   */
  public abstract void execute(WebServerSession session, JsonObject command);
}

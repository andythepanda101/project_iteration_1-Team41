package edu.umn.cs.csci3081w.project.webserver;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import edu.umn.cs.csci3081w.project.model.Bus;
import edu.umn.cs.csci3081w.project.model.Train;
import edu.umn.cs.csci3081w.project.model.Vehicle;
import java.util.List;

/**
 * A class that obtains vehicle commands for the simulation.
 */
public class GetVehiclesCommand extends SimulatorCommand {

  private VisualTransitSimulator simulator;

  /**
   * Constructor for vehicles commands.
   * @param simulator current simulation session
   */
  public GetVehiclesCommand(VisualTransitSimulator simulator) {
    this.simulator = simulator;
  }

  /**
   * Retrieves vehicles information from the simulation.
   *
   * @param session current simulation session
   * @param command the get vehicles command content
   */
  @Override
  public void execute(WebServerSession session, JsonObject command) {
    List<Vehicle> vehicles = simulator.getActiveVehicles();
    JsonObject data = new JsonObject();
    data.addProperty("command", "updateVehicles");
    JsonArray vehiclesArray = new JsonArray();
    for (int i = 0; i < vehicles.size(); i++) {
      Vehicle currVehicle = vehicles.get(i);
      JsonObject s = new JsonObject();
      s.addProperty("id", currVehicle.getId());
      s.addProperty("numPassengers", currVehicle.getPassengers().size());
      s.addProperty("capacity", currVehicle.getCapacity());
      String vehicleType = "";
      int co2Usage = 0;
      if (currVehicle instanceof Bus) {
        vehicleType = Bus.BUS_VEHICLE;
        s.addProperty("type", vehicleType);
        co2Usage = 2 + currVehicle.getPassengers().size() * 1;
      } else if (currVehicle instanceof Train) {
        vehicleType = Train.TRAIN_VEHICLE;
        s.addProperty("type", vehicleType);
        co2Usage = 5 + currVehicle.getPassengers().size() * 2;
      }
      s.addProperty("co2", co2Usage);
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("longitude", currVehicle.getPosition().getLongitude());
      jsonObject.addProperty("latitude", currVehicle.getPosition().getLatitude());
      s.add("position", jsonObject);
      vehiclesArray.add(s);
    }
    data.add("vehicles", vehiclesArray);
    session.sendJson(data);
  }

}

package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.umn.cs.csci3081w.project.webserver.VisualTransitSimulator;
import org.junit.jupiter.api.Test;

public class StorageFacilityTest {
  public VisualTransitSimulator createVisualTransitSimulator() {
    return new VisualTransitSimulator("src/main/resources/config.txt");
  }

  @Test
  public void testConstructorNormal() {
    VisualTransitSimulator simulator = createVisualTransitSimulator();
    StorageFacility storageFacility = simulator.getStorageFacility();
    assertEquals(10, storageFacility.getBusesNum());
    assertEquals(11, storageFacility.getTrainsNum());
  }

}
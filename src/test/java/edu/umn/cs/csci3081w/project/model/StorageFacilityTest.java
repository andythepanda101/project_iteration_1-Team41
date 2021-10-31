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

  /*
  Feature 3 was tested manually using debugging print() statements.
  If the feature works correctly, buses and trains should only be created during time steps
  1, 6, 11, ... and so on, in increments of 5.
  And, the buses/trains should only be created if there is one available in storage.
  Below is the recording of the simulation:

    session opened
    Time between vehicles for route  0: 5
    Time between vehicles for route  1: 5
    Number of time steps for simulation is: 50
    Starting simulation
    DEBUG: Initial storage for buses is 4
    DEBUG: Initial storage for trains is 3
    ~~~~The simulation time is now at time step 1~~~~
    DEBUG: Bus available and created.
    DEBUG: Current storage facility for buses is 3
    DEBUG: Train available and created.
    DEBUG: Current storage facility for trains 2
    ~~~~The simulation time is now at time step 2~~~~
    ~~~~The simulation time is now at time step 3~~~~
    ~~~~The simulation time is now at time step 4~~~~
    ~~~~The simulation time is now at time step 5~~~~
    ~~~~The simulation time is now at time step 6~~~~
    DEBUG: Bus available and created.
    DEBUG: Current storage facility for buses is 2
    DEBUG: Train available and created.
    DEBUG: Current storage facility for trains 1
    ~~~~The simulation time is now at time step 7~~~~
    ~~~~The simulation time is now at time step 8~~~~
    ~~~~The simulation time is now at time step 9~~~~
    ~~~~The simulation time is now at time step 10~~~~
    DEBUG: Train completed trip. Train available in storage facility
    DEBUG: Current storage facility for trains is 2
    ~~~~The simulation time is now at time step 11~~~~
    DEBUG: Bus available and created.
    DEBUG: Current storage facility for buses is 1
    DEBUG: Train available and created.
    DEBUG: Current storage facility for trains 1
    ~~~~The simulation time is now at time step 12~~~~
    ~~~~The simulation time is now at time step 13~~~~
    ~~~~The simulation time is now at time step 14~~~~
    ~~~~The simulation time is now at time step 15~~~~
    DEBUG: Train completed trip. Train available in storage facility
    DEBUG: Current storage facility for trains is 2
    ~~~~The simulation time is now at time step 16~~~~
    DEBUG: Bus available and created.
    DEBUG: Current storage facility for buses is 0
    DEBUG: Train available and created.
    DEBUG: Current storage facility for trains 1
    ~~~~The simulation time is now at time step 17~~~~
    ~~~~The simulation time is now at time step 18~~~~
    ~~~~The simulation time is now at time step 19~~~~
    ~~~~The simulation time is now at time step 20~~~~
    DEBUG: Train completed trip. Train available in storage facility
    DEBUG: Current storage facility for trains is 2
    ~~~~The simulation time is now at time step 21~~~~
    DEBUG: Buses full. Not created.
    DEBUG: Train available and created.
    DEBUG: Current storage facility for trains 1
    ~~~~The simulation time is now at time step 22~~~~
    ~~~~The simulation time is now at time step 23~~~~
    ~~~~The simulation time is now at time step 24~~~~
    ~~~~The simulation time is now at time step 25~~~~
    DEBUG: Train completed trip. Train available in storage facility
    DEBUG: Current storage facility for trains is 2
    ~~~~The simulation time is now at time step 26~~~~
    DEBUG: Buses full. Not created.
    DEBUG: Train available and created.
    DEBUG: Current storage facility for trains 1
    ~~~~The simulation time is now at time step 27~~~~
    ~~~~The simulation time is now at time step 28~~~~
    ~~~~The simulation time is now at time step 29~~~~
    ~~~~The simulation time is now at time step 30~~~~
    DEBUG: Train completed trip. Train available in storage facility
    DEBUG: Current storage facility for trains is 2
    ~~~~The simulation time is now at time step 31~~~~
    DEBUG: Buses full. Not created.
    DEBUG: Train available and created.
    DEBUG: Current storage facility for trains 1
    ~~~~The simulation time is now at time step 32~~~~
    ~~~~The simulation time is now at time step 33~~~~
    ~~~~The simulation time is now at time step 34~~~~
    ~~~~The simulation time is now at time step 35~~~~
    DEBUG: Train completed trip. Train available in storage facility
    DEBUG: Current storage facility for trains is 2
    ~~~~The simulation time is now at time step 36~~~~
    DEBUG: Buses full. Not created.
    DEBUG: Train available and created.
    DEBUG: Current storage facility for trains 1
    ~~~~The simulation time is now at time step 37~~~~
    ~~~~The simulation time is now at time step 38~~~~
    ~~~~The simulation time is now at time step 39~~~~
    ~~~~The simulation time is now at time step 40~~~~
    DEBUG: Train completed trip. Train available in storage facility
    DEBUG: Current storage facility for trains is 2
    ~~~~The simulation time is now at time step 41~~~~
    DEBUG: Buses full. Not created.
    DEBUG: Train available and created.
    DEBUG: Current storage facility for trains 1
    ~~~~The simulation time is now at time step 42~~~~
    ~~~~The simulation time is now at time step 43~~~~
    DEBUG: Bus completed trip. Bus available in storage facility
    DEBUG: Current storage facility for buses is 1
    ~~~~The simulation time is now at time step 44~~~~
    ~~~~The simulation time is now at time step 45~~~~
    DEBUG: Train completed trip. Train available in storage facility
    DEBUG: Current storage facility for trains is 2
    ~~~~The simulation time is now at time step 46~~~~
    DEBUG: Bus available and created.
    DEBUG: Current storage facility for buses is 0
    DEBUG: Train available and created.
    DEBUG: Current storage facility for trains 1
    ~~~~The simulation time is now at time step 47~~~~
    ~~~~The simulation time is now at time step 48~~~~
    DEBUG: Bus completed trip. Bus available in storage facility
    DEBUG: Current storage facility for buses is 1
    ~~~~The simulation time is now at time step 49~~~~
    ~~~~The simulation time is now at time step 50~~~~
    DEBUG: Train completed trip. Train available in storage facility
    DEBUG: Current storage facility for trains is 2
    session closed
   */

}
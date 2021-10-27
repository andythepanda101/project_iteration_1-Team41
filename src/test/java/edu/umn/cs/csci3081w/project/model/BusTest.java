package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class BusTest {

  /**
   * Setup operations before each test runs.
   */
  @BeforeEach
  public void setUp() {
    PassengerFactory.DETERMINISTIC = true;
    PassengerFactory.DETERMINISTIC_NAMES_COUNT = 0;
    PassengerFactory.DETERMINISTIC_DESTINATION_COUNT = 0;
    RandomPassengerGenerator.DETERMINISTIC = true;
  }

  /**
   * Create a bus with outgoing and incoming routes and three stops per route.
   */
  public Bus createBus() {
    Stop stop1 = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop stop2 = new Stop(1, "test stop 2", new Position(-93.235071, 44.973580));
    Stop stop3 = new Stop(2, "test stop 2", new Position(-93.226632, 44.975392));
    List<Stop> stopsOut = new ArrayList<Stop>();
    stopsOut.add(stop1);
    stopsOut.add(stop2);
    stopsOut.add(stop3);
    List<Double> distancesOut = new ArrayList<Double>();
    distancesOut.add(0.9712663713083954);
    distancesOut.add(0.961379387775189);
    List<Double> probabilitiesOut = new ArrayList<Double>();
    probabilitiesOut.add(.15);
    probabilitiesOut.add(0.3);
    probabilitiesOut.add(.0);
    PassengerGenerator generatorOut = new RandomPassengerGenerator(stopsOut, probabilitiesOut);
    Route testRouteOut = new Route(10, "testLine", "BUS", "testRouteOut",
        stopsOut, distancesOut, generatorOut);
    testRouteOut.generateNewPassengers();
    List<Stop> stopsIn = new ArrayList<>();
    stopsIn.add(stop3);
    stopsIn.add(stop2);
    stopsIn.add(stop1);
    List<Double> distancesIn = new ArrayList<>();
    distancesIn.add(0.961379387775189);
    distancesIn.add(0.9712663713083954);
    List<Double> probabilitiesIn = new ArrayList<>();
    probabilitiesIn.add(.025);
    probabilitiesIn.add(0.3);
    probabilitiesIn.add(.0);
    PassengerGenerator generatorIn = new RandomPassengerGenerator(stopsIn, probabilitiesIn);
    Route testRouteIn = new Route(11, "testLine", "BUS", "testRouteIn",
        stopsIn, distancesIn, generatorIn);
    testRouteIn.generateNewPassengers();
    return new Bus(0, testRouteOut, testRouteIn, 5, 1);
  }

  /**
   * Testing state after using constructor.
   */
  @Test
  public void testBusConstructor() {
    Bus bus = createBus();
    assertEquals(0, bus.getId());
    assertEquals(5, bus.getCapacity());
    assertEquals(1, bus.getSpeed());
    assertEquals(0, bus.getPassengers().size());
    assertEquals("testRouteOut0", bus.getName());
    assertEquals("test stop 1", bus.getNextStop().getName());
    assertEquals(44.972392, bus.getNextStop().getPosition().getLatitude());
    assertEquals(-93.243774, bus.getNextStop().getPosition().getLongitude());
  }

  /**
   * Testing reporting functionality of route. Ensuring that all other reports show correctly too.
   */
  @Test
  public void testBusReport() {
    try {
      Bus bus = createBus();
      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      bus.report(testStream);
      outputStream.flush();
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();
      String strToCompare =
          "####Bus Info Start####" + System.lineSeparator()
              + "ID: 0" + System.lineSeparator()
              + "Name: testRouteOut0" + System.lineSeparator()
              + "Speed: 1.0" + System.lineSeparator()
              + "Capacity: 5" + System.lineSeparator()
              + "Position: 44.972392,-93.243774" + System.lineSeparator()
              + "Distance to next stop: 0.0" + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num of passengers: 0" + System.lineSeparator()
              + "****Passengers Info End****" + System.lineSeparator()
              + "####Bus Info End####" + System.lineSeparator();
      assertEquals(data, strToCompare);
    } catch (IOException ioe) {
      fail();
    }
  }

  /**
   *  Testing the state after the bus moves on its route.
   */
  @Test
  public void testBusMove() {
    Bus bus = createBus();
    bus.move();

    // checks if we picked up a passenger at the stop.
    assertEquals(1, bus.getPassengers().size());

    // checks if it correctly updated to the next stop.
    assertEquals("test stop 2", bus.getNextStop().getName());

    // checks if positions were updated correctly.
    assertEquals(44.973580, bus.getNextStop().getPosition().getLatitude());
    assertEquals(-93.235071, bus.getNextStop().getPosition().getLongitude());
  }

  /**
   *  Testing the state after the bus updates its location on the route.
   */
  @Test
  public void testBusUpdate() {
    Bus bus = createBus();
    bus.update();

    // checks if we picked up a passenger at the stop.
    assertEquals(1, bus.getPassengers().size());

    // checks if it correctly updated to the next stop.
    assertEquals("test stop 2", bus.getNextStop().getName());

    // checks if positions were updated correctly.
    assertEquals(44.973580, bus.getNextStop().getPosition().getLatitude());
    assertEquals(-93.235071, bus.getNextStop().getPosition().getLongitude());
  }

  /**
   *  Testing if bus correctly checks whether or not the trip was completed.
   */
  @Test
  public void testBusIsTripComplete() {
    Bus bus = createBus();
    assertEquals(false, bus.isTripComplete());
    for (int i = 0; i < 6; i++) {
      bus.update();
    }
    assertEquals(true, bus.isTripComplete());
  }

  /**
   * Testing if bus utilizes passenger loader correctly.
   */
  @Test
  public void testBusLoadPassenger() {
    Bus bus = createBus();
    Passenger passenger = new Passenger(0, "OriginalName");
    int loadedPassengers = bus.loadPassenger(passenger);
    assertEquals(1, loadedPassengers);
    assertEquals(1, bus.getPassengers().size());
  }

  /**
   * Testing if bus utilizes passenger loader correctly when the bus is full.
   */
  @Test
  public void testBusLoadPassengerFull() {
    Bus bus = createBus();
    Passenger passenger = new Passenger(0, "OriginalName");
    Passenger passenger2 = new Passenger(1, "OriginalName2");
    Passenger passenger3 = new Passenger(2, "OriginalName3");
    Passenger passenger4 = new Passenger(3, "OriginalName4");
    Passenger passenger5 = new Passenger(4, "OriginalName5");
    bus.loadPassenger(passenger);
    bus.loadPassenger(passenger2);
    bus.loadPassenger(passenger3);
    bus.loadPassenger(passenger4);
    bus.loadPassenger(passenger5);

    assertEquals(5, bus.getPassengers().size());

    Passenger passenger6 = new Passenger(5, "OriginalName6");
    bus.loadPassenger(passenger6);
    assertEquals(5, bus.getPassengers().size());
  }

}

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

public class TrainTest {

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
   * Create a train with outgoing and incoming routes and three stops per route.
   */
  public Train createTrain() {
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
    Route testRouteOut = new Route(10, "testLine", "TRAIN", "testRouteOut",
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
    Route testRouteIn = new Route(11, "testLine", "TRAIN", "testRouteIn",
        stopsIn, distancesIn, generatorIn);
    testRouteIn.generateNewPassengers();
    return new Train(0, testRouteOut, testRouteIn, 5, 1);
  }

  /**
   * Testing state after using constructor.
   */
  @Test
  public void testTrainConstructor() {
    Train train = createTrain();
    assertEquals(0, train.getId());
    assertEquals(5, train.getCapacity());
    assertEquals(1, train.getSpeed());
    assertEquals(0, train.getPassengers().size());
    assertEquals("testRouteOut0", train.getName());
    assertEquals("test stop 1", train.getNextStop().getName());
    assertEquals(44.972392, train.getNextStop().getPosition().getLatitude());
    assertEquals(-93.243774, train.getNextStop().getPosition().getLongitude());
  }

  /**
   * Testing reporting functionality of route. Ensuring that all other reports show correctly too.
   */
  @Test
  public void testBusReport() {
    try {
      Train train = createTrain();
      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      train.report(testStream);
      outputStream.flush();
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();
      String strToCompare =
          "####Train Info Start####" + System.lineSeparator()
              + "ID: 0" + System.lineSeparator()
              + "Name: testRouteOut0" + System.lineSeparator()
              + "Speed: 1.0" + System.lineSeparator()
              + "Capacity: 5" + System.lineSeparator()
              + "Position: 44.972392,-93.243774" + System.lineSeparator()
              + "Distance to next stop: 0.0" + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num of passengers: 0" + System.lineSeparator()
              + "****Passengers Info End****" + System.lineSeparator()
              + "####Train Info End####" + System.lineSeparator();
      assertEquals(data, strToCompare);
    } catch (IOException ioe) {
      fail();
    }
  }

  /**
   *  Testing the state after the train moves on its route.
   */
  @Test
  public void testTrainMove() {
    Train train = createTrain();
    train.move();

    // checks if we picked up a passenger at the stop.
    assertEquals(1, train.getPassengers().size());

    // checks if it correctly updated to the next stop.
    assertEquals("test stop 2", train.getNextStop().getName());

    // checks if positions were updated correctly.
    assertEquals(44.973580, train.getNextStop().getPosition().getLatitude());
    assertEquals(-93.235071, train.getNextStop().getPosition().getLongitude());
  }

  /**
   *  Testing the state after the train updates its location on the route.
   */
  @Test
  public void testTrainUpdate() {
    Train train = createTrain();
    train.update();

    // checks if we picked up a passenger at the stop.
    assertEquals(1, train.getPassengers().size());

    // checks if it correctly updated to the next stop.
    assertEquals("test stop 2", train.getNextStop().getName());

    // checks if positions were updated correctly.
    assertEquals(44.973580, train.getNextStop().getPosition().getLatitude());
    assertEquals(-93.235071, train.getNextStop().getPosition().getLongitude());
  }

  /**
   *  Testing if train correctly checks whether or not the trip was completed.
   */
  @Test
  public void testTrainIsTripComplete() {
    Train train = createTrain();
    assertEquals(false, train.isTripComplete());
    for (int i = 0; i < 6; i++) {
      train.update();
    }
    assertEquals(true, train.isTripComplete());
  }

  /**
   * Testing if train utilizes passenger loader correctly.
   */
  @Test
  public void testTrainLoadPassenger() {
    Train train = createTrain();
    Passenger passenger = new Passenger(0, "OriginalName");
    int loadedPassengers = train.loadPassenger(passenger);
    assertEquals(1, loadedPassengers);
    assertEquals(1, train.getPassengers().size());
  }

  /**
   * Testing if train utilizes passenger loader correctly when the train is full.
   */
  @Test
  public void testTrainLoadPassengerFull() {
    Train train = createTrain();
    Passenger passenger = new Passenger(0, "OriginalName");
    Passenger passenger2 = new Passenger(1, "OriginalName2");
    Passenger passenger3 = new Passenger(2, "OriginalName3");
    Passenger passenger4 = new Passenger(3, "OriginalName4");
    Passenger passenger5 = new Passenger(4, "OriginalName5");
    train.loadPassenger(passenger);
    train.loadPassenger(passenger2);
    train.loadPassenger(passenger3);
    train.loadPassenger(passenger4);
    train.loadPassenger(passenger5);

    assertEquals(5, train.getPassengers().size());

    Passenger passenger6 = new Passenger(5, "OriginalName6");
    train.loadPassenger(passenger6);
    assertEquals(5, train.getPassengers().size());
  }

}
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

public class RouteTest {

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
   * Create a route with three stops.
   */
  public Route createRoute() {
    Stop stop1 = new Stop(0, "test stop 1", new Position(-93.243774, 44.972392));
    Stop stop2 = new Stop(1, "test stop 2", new Position(-93.235071, 44.973580));
    Stop stop3 = new Stop(2, "test stop 3", new Position(-93.226632, 44.975392));
    List<Stop> stops = new ArrayList<Stop>();
    stops.add(stop1);
    stops.add(stop2);
    stops.add(stop3);
    List<Double> distances = new ArrayList<Double>();
    distances.add(0.9712663713083954);
    distances.add(0.961379387775189);
    List<Double> probabilities = new ArrayList<Double>();
    probabilities.add(.15);
    probabilities.add(0.3);
    probabilities.add(.0);
    PassengerGenerator generator = new RandomPassengerGenerator(stops, probabilities);
    Route testRoute = new Route(10, "testLine", "BUS", "testRoute",
        stops, distances, generator);

    return testRoute;
  }

  /**
   * Testing state after using constructor.
   */
  @Test
  public void testRouteConstructor() {
    Route route = createRoute();
    assertEquals(10, route.getId());
    assertEquals("testLine", route.getLineName());
    assertEquals("BUS", route.getLineType());
    assertEquals("testRoute", route.getName());
    assertEquals(3, route.getStops().size());
    assertEquals(0, route.getNextStopDistance());
    assertEquals(0, route.getNextStopIndex());
    assertEquals("test stop 1", route.getNextStop().getName());
  }

  /**
   * Testing state after creating a shallow copy of the route.
   * The stops for the original and the copy should be the same, since the stops are shared.
   */
  @Test
  public void testRouteShallowCopy() {
    Route originalRoute = createRoute();
    Route shallowCopy = originalRoute.shallowCopy();
    for (int i = 0; i < 3; i++) {
      assertEquals(originalRoute.getStops().get(i), shallowCopy.getStops().get(i));
    }
  }

  /**
   * Testing if updating the route correctly adds passengers to the stops.
   */
  @Test
  public void testRouteUpdate() {
    Route route = createRoute();
    route.update();
    int stop1Passengers = route.getStops().get(0).getPassengers().size();
    int stop2Passengers = route.getStops().get(1).getPassengers().size();
    int stop3Passengers = route.getStops().get(2).getPassengers().size();
    assertEquals(1, stop1Passengers);
    assertEquals(1, stop2Passengers);
    assertEquals(0, stop3Passengers);
  }

  /**
   * Testing reporting functionality of route. Ensuring that all other reports show correctly too.
   */
  @Test
  public void testRouteReport() {
    try {
      Route route = createRoute();
      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      route.report(testStream);
      outputStream.flush();
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();
      String strToCompare =
          "####Route Info Start####" + System.lineSeparator()
              + "ID: 10" + System.lineSeparator()
              + "Line name: testLine" + System.lineSeparator()
              + "Line type: BUS" + System.lineSeparator()
              + "Name: testRoute" + System.lineSeparator()
              + "Num stops: 3" + System.lineSeparator()
              + "****Stops Info Start****" + System.lineSeparator()
              + "++++Next Stop Info Start++++" + System.lineSeparator()
              + "####Stop Info Start####" + System.lineSeparator()
              + "ID: 0" + System.lineSeparator()
              + "Name: test stop 1" + System.lineSeparator()
              + "Position: 44.972392,-93.243774" + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num passengers waiting: 0" + System.lineSeparator()
              + "****Passengers Info End****" + System.lineSeparator()
              + "####Stop Info End####" + System.lineSeparator()
              + "++++Next Stop Info End++++" + System.lineSeparator()
              + "####Stop Info Start####" + System.lineSeparator()
              + "ID: 1" + System.lineSeparator()
              + "Name: test stop 2" + System.lineSeparator()
              + "Position: 44.97358,-93.235071" + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num passengers waiting: 0" + System.lineSeparator()
              + "****Passengers Info End****" + System.lineSeparator()
              + "####Stop Info End####" + System.lineSeparator()
              + "####Stop Info Start####" + System.lineSeparator()
              + "ID: 2" + System.lineSeparator()
              + "Name: test stop 3" + System.lineSeparator()
              + "Position: 44.975392,-93.226632" + System.lineSeparator()
              + "****Passengers Info Start****" + System.lineSeparator()
              + "Num passengers waiting: 0" + System.lineSeparator()
              + "****Passengers Info End****" + System.lineSeparator()
              + "####Stop Info End####" + System.lineSeparator()
              + "****Stops Info End****" + System.lineSeparator()
              + "####Route Info End####" + System.lineSeparator();
      assertEquals(data, strToCompare);
    } catch (IOException ioe) {
      fail();
    }
  }

  /**
   * Testing if the next stop updates correctly.
   */
  @Test
  public void testRouteNextStop() {
    Route route = createRoute();
    Stop stop = route.getNextStop();
    assertEquals("test stop 1", stop.getName());
    route.nextStop();
    stop = route.getNextStop();
    assertEquals("test stop 2", stop.getName());
    route.nextStop();
    stop = route.getNextStop();
    assertEquals("test stop 3", stop.getName());
    route.nextStop();
    stop = route.getNextStop();
    assertEquals("test stop 3", stop.getName());
  }

  /**
   * Testing if the previous stop is calculated correctly.
   */
  @Test
  public void testRoutePrevStop() {
    Route route = createRoute();
    Stop stop = route.prevStop();
    assertEquals("test stop 1", stop.getName());
    route.nextStop();
    stop = route.prevStop();
    assertEquals("test stop 1", stop.getName());
    route.nextStop();
    stop = route.prevStop();
    assertEquals("test stop 2", stop.getName());
    route.nextStop();
    stop = route.prevStop();
    assertEquals("test stop 3", stop.getName());
  }

  /**
   * Testing if the distance to the next stop is received correctly.
   */
  @Test
  public void testRouteNextStopDistance() {
    Route route = createRoute();
    assertEquals(0.0, route.getNextStopDistance());
    route.nextStop();
    assertEquals(0.9712663713083954, route.getNextStopDistance());
    route.nextStop();
    assertEquals(0.961379387775189, route.getNextStopDistance());
  }

  /**
   * Testing if method correctly calls generator and returns correct number of passengers.
   */
  @Test
  public void testRouteGenerateNewPassengers() {
    Route route = createRoute();
    assertEquals(2, route.generateNewPassengers());
  }

  /**
   * Testing if we get end of the route correctly.
   */
  @Test
  public void testRouteIsAtEnd() {
    Route route = createRoute();
    assertEquals(false, route.isAtEnd());
    route.nextStop();
    route.nextStop();
    route.nextStop();
    assertEquals(true, route.isAtEnd());
  }

}

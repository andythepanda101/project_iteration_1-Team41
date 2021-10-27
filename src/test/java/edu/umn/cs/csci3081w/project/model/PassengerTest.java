package edu.umn.cs.csci3081w.project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

public class PassengerTest {

  /**
   * Testing state after using constructor.
   */
  @Test
  public void testPassengerConstructor() {
    Passenger passenger = new Passenger(0, "OriginalName");
    assertEquals(0, passenger.getDestination());
    assertEquals(false, passenger.isOnVehicle());
  }

  /**
   *  Testing if pasUpdate() updates the correct values for waitAtStop.
   *  If it updates the correct value, it should NOT change timeOnVehicle.
   */
  @Test
  public void testPasUpdate() {
    Passenger passenger = new Passenger(0, "OriginalName");
    passenger.pasUpdate();
    assertEquals(false, passenger.isOnVehicle());
  }

  /**
   *  Testing the passenger correctly shows if they are on the vehicle or not.
   */
  @Test
  public void testPassengerOnVehicle() {
    Passenger passenger = new Passenger(0, "OriginalName");
    assertEquals(false, passenger.isOnVehicle());
    passenger.setOnVehicle();
    assertEquals(true, passenger.isOnVehicle());
  }

  /**
   * Testing reporting functionality of the passenger, if they spent no time waiting/on vehicle.
   */
  @Test
  public void testPassengerReportNoWaiting() {
    try {
      Passenger passenger = new Passenger(0, "OriginalName");
      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      passenger.report(testStream);
      outputStream.flush();
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();
      String strToCompare =
          "####Passenger Info Start####" + System.lineSeparator()
              + "Name: OriginalName" + System.lineSeparator()
              + "Destination: 0" + System.lineSeparator()
              + "Wait at stop: 0" + System.lineSeparator()
              + "Time on vehicle: 0" + System.lineSeparator()
              + "####Passenger Info End####" + System.lineSeparator();
      assertEquals(data, strToCompare);
    } catch (IOException ioe) {
      fail();
    }
  }

  /**
   * Testing reporting functionality of the passenger, if they did spend time waiting/on vehicle.
   */
  @Test
  public void testPassengerReportWaiting() {
    try {
      Passenger passenger = new Passenger(0, "OriginalName");
      passenger.pasUpdate();
      passenger.pasUpdate();
      passenger.setOnVehicle();
      passenger.pasUpdate();
      passenger.pasUpdate();
      final Charset charset = StandardCharsets.UTF_8;
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream testStream = new PrintStream(outputStream, true, charset.name());
      passenger.report(testStream);
      outputStream.flush();
      String data = new String(outputStream.toByteArray(), charset);
      testStream.close();
      outputStream.close();
      String strToCompare =
          "####Passenger Info Start####" + System.lineSeparator()
              + "Name: OriginalName" + System.lineSeparator()
              + "Destination: 0" + System.lineSeparator()
              + "Wait at stop: 2" + System.lineSeparator()
              + "Time on vehicle: 3" + System.lineSeparator()
              + "####Passenger Info End####" + System.lineSeparator();
      assertEquals(data, strToCompare);
    } catch (IOException ioe) {
      fail();
    }
  }
}

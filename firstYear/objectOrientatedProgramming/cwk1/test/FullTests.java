// Correctness testing for COMP1721 Coursework 1 (Full Solution)

import java.io.FileNotFoundException;
import java.util.InputMismatchException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class FullTests {

  private static WeatherStation station;

  private WeatherRecord r1, r2, r3, r4;

  @BeforeClass
  public static void globalSetup() throws FileNotFoundException {
    station = new WeatherStation("../data/sheffield.txt");
  }

  @Before
  public void perTestSetup() {
    r1 = new WeatherRecord("2016  1   8.0   3.0  3  84.8   40.3");
    r2 = new WeatherRecord("2015  1   8.0   3.0  3  84.8   40.3");
    r3 = new WeatherRecord("2016  2   8.0   3.0  3  84.8   40.3");
    r4 = new WeatherRecord("1995  7  23.8  14.1  0  22.5  229.1");
  }

  // WeatherRecord class

  @Test
  public void equality() {
    WeatherRecord r = new WeatherRecord("2016  1  8.0  3.0  3  84.8  40.3");

    assertThat(r1.equals(r1), is(true));
    assertThat(r1.equals(r), is(true));
    assertThat(r1.equals(r2), is(false));
    assertThat(r1.equals(r3), is(false));
    assertThat(r1.equals(r4), is(false));
  }

  @Test
  public void hashing() {
    WeatherRecord r = new WeatherRecord("2016  1  8.0  3.0  3  84.8  40.3");

    assertThat(r1.hashCode() == r.hashCode(), is(true));
    assertThat(r1.hashCode() == r2.hashCode(), is(false));
    assertThat(r1.hashCode() == r3.hashCode(), is(false));
    assertThat(r1.hashCode() == r4.hashCode(), is(false));
  }

  @Test
  public void comparison() {
    assertThat(r1.compareTo(r2), greaterThan(0));
    assertThat(r2.compareTo(r1), lessThan(0));
    assertThat(r1.compareTo(r3), lessThan(0));
    assertThat(r3.compareTo(r1), greaterThan(0));
  }

  // WeatherStation class

  @Test
  public void wettestYear() {
    assertThat(station.findWettestYear(), is(2012));
  }

  @Test
  public void driestYear() {
    assertThat(station.findDriestYear(), is(1975));
  }

  @Test
  public void totalRainfall() {
    assertThat(station.totalRainfall(2012), closeTo(1146.4, 0.0001));
    assertThat(station.totalRainfall(1975), closeTo(559.3, 0.0001));
  }
}

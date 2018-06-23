// Correctness testing for COMP1721 Coursework 1 (Basic Solution)

import java.io.FileNotFoundException;
import java.util.InputMismatchException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class BasicTests {

  private static WeatherStation station;

  private WeatherRecord r1, r2, r3;

  @BeforeClass
  public static void globalSetup() throws FileNotFoundException {
    station = new WeatherStation("../data/sheffield.txt");
  }

  @Before
  public void perTestSetup() {
    r1 = new WeatherRecord("2016   1   8.0   3.0  3  84.8   40.3");
    r2 = new WeatherRecord("1995   7  23.8  14.1  0  22.5  229.1");
    r3 = new WeatherRecord("1977  12   7.5   3.6  1  82.5   30.7");
  }

  // WeatherRecord class

  @Test
  public void yearMonth() {
    assertThat(r1.getYear(), is(2016));
    assertThat(r2.getYear(), is(1995));

    assertThat(r1.getMonth(), is(1));
    assertThat(r2.getMonth(), is(7));
    assertThat(r3.getMonth(), is(12));
  }

  @Test
  public void monthName() {
    assertThat(r1.getMonthName(), is("January"));
    assertThat(r2.getMonthName(), is("July"));
    assertThat(r3.getMonthName(), is("December"));
  }

  @Test
  public void temperatures() {
    assertThat(r1.getMaxTemp(), closeTo(8.0, 0.000001));
    assertThat(r2.getMaxTemp(), closeTo(23.8, 0.000001));
    assertThat(r1.getMinTemp(), closeTo(3.0, 0.000001));
    assertThat(r2.getMinTemp(), closeTo(14.1, 0.000001));
  }

  @Test
  public void frostDays() {
    assertThat(r1.getFrostDays(), is(3));
    assertThat(r2.getFrostDays(), is(0));
    assertThat(r3.getFrostDays(), is(1));
  }

  @Test
  public void rain() {
    assertThat(r1.getRainfall(), closeTo(84.8, 0.000001));
    assertThat(r2.getRainfall(), closeTo(22.5, 0.000001));
  }

  @Test
  public void sunHours() {
    assertThat(r1.getSunHours(), closeTo(40.3, 0.000001));
    assertThat(r2.getSunHours(), closeTo(229.1, 0.000001));
  }

  @Test(expected=InputMismatchException.class)
  public void tooFewValues() {
    new WeatherRecord("2016  1  8.0  3.0  3  84.8");
  }

  @Test(expected=InputMismatchException.class)
  public void tooManyValues() {
    new WeatherRecord("2016  1  8.0  3.0  3  84.8  40.3  40.3");
  }

  // WeatherStation class

  @Test(expected=FileNotFoundException.class)
  public void missingFile() throws FileNotFoundException {
    new WeatherStation("missing.txt");
  }

  @Test
  public void name() {
    assertThat(station.getName(), is("Sheffield"));
  }

  @Test
  public void location() {
    Location loc = station.getLocation();

    assertThat(loc.getEasting(), is(433900));
    assertThat(loc.getNorthing(), is(387200));
    assertThat(loc.getLatitude(), closeTo(53.381, 0.000001));
    assertThat(loc.getLongitude(), closeTo(-1.490, 0.000001));
    assertThat(loc.getHeight(), is(131));
  }

  @Test
  public void recordCount() {
    assertThat(station.getRecordCount(), is(1044));
  }

  @Test
  public void firstRecord() {
    WeatherRecord r = station.getRecord(0);

    assertThat(r.getYear(), is(1930));
    assertThat(r.getMonth(), is(1));
    assertThat(r.getMaxTemp(), closeTo(8.1, 0.000001));
    assertThat(r.getMinTemp(), closeTo(2.4, 0.000001));
    assertThat(r.getFrostDays(), is(6));
    assertThat(r.getRainfall(), closeTo(120.5, 0.000001));
    assertThat(r.getSunHours(), closeTo(54.2, 0.000001));
  }

  @Test
  public void lastRecord() {
    WeatherRecord r = station.getRecord(1043);

    assertThat(r.getYear(), is(2016));
    assertThat(r.getMonth(), is(12));
    assertThat(r.getMaxTemp(), closeTo(9.3, 0.000001));
    assertThat(r.getMinTemp(), closeTo(4.1, 0.000001));
    assertThat(r.getFrostDays(), is(3));
    assertThat(r.getRainfall(), closeTo(31.8, 0.000001));
    assertThat(r.getSunHours(), closeTo(57.9, 0.000001));
  }

  @Test
  public void sunniestMonth() {
    WeatherRecord sunniest = station.findSunniestMonth();

    assertThat(sunniest.getYear(), is(2006));
    assertThat(sunniest.getMonth(), is(7));
  }

  @Test
  public void meanMaxTemp() {
    assertThat(station.meanMaxTemp(8), closeTo(20.1954, 0.0001));
  }
}

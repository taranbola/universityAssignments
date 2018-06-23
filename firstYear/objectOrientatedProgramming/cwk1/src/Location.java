import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents the location of a Met Office weather station.
 *
 * @author Nick Efford
 */
public class Location {

  private static final Pattern PATTERN = Pattern.compile(
      "^Location\\s+(\\d+)E\\s+(\\d+)N,"
    + "\\s+Lat\\s+(-?\\d+\\.\\d+)\\s+Lon\\s+(-?\\d+\\.\\d+),"
    + "\\s+(\\d+)\\s+metres\\s+amsl$"
  );

  private double longitude;
  private double latitude;
  private int easting;
  private int northing;
  private int height;

  /**
   * Creates a Location object from the data supplied in a Met Office
   * weather station dataset.
   *
   * <p>An example of the required format is shown here:</p>
   * <pre>
   * Location 433900E 387200N, Lat 53.381 Lon -1.490, 131 metres amsl
   * </pre>
   *
   * @param line Line of text containing location data
   */
  public Location(String line) {
    Matcher matcher = PATTERN.matcher(line);
    if (matcher.matches()) {
      easting = Integer.parseInt(matcher.group(1));
      northing = Integer.parseInt(matcher.group(2));
      latitude = Double.parseDouble(matcher.group(3));
      longitude = Double.parseDouble(matcher.group(4));
      height = Integer.parseInt(matcher.group(5));
    }
    else {
      throw new InputMismatchException("invalid location format");
    }
  }

  /**
   * @return Longitude of this location, in degrees
   */
  public double getLongitude() {
    return longitude;
  }

  /**
   * @return Latitude of this location, in degrees
   */
  public double getLatitude() {
    return latitude;
  }

  /**
   * @return Easting of this location
   */
  public int getEasting() {
    return easting;
  }

  /**
   * @return Northing of this location
   */
  public int getNorthing() {
    return northing;
  }

  /**
   * @return Height of this location above mean sea level, in metres
   */
  public int getHeight() {
    return height;
  }

  /**
   * Generates a string containing this location's easting,
   * northing and height above sea level.
   *
   * @return String representation of this location
   */
  @Override
  public String toString() {
    return String.format("(%dE %dN, %d metres)", easting, northing, height);
  }
}

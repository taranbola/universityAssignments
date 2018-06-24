/**
 * LocalTimeDeserializer.java
 */

package sample;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.time.LocalTime;

/**
 * Used for deserialising LocalTime objects
 *
 * @author Mitchell Gladstone
 */
public class LocalTimeDeserializer extends StdDeserializer<LocalTime> {

  protected LocalTimeDeserializer() {
    super(LocalTime.class);
  }

  @Override
  public LocalTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
    return LocalTime.parse(parser.readValueAs(String.class));
  }
}

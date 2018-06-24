/**
 * LocalTimeSerializer.java
 */

package sample;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Used for serialising LocalTime objects
 *
 * @author Mitchell Gladstone
 */
public class LocalTimeSerializer extends StdSerializer<LocalTime> {

  public LocalTimeSerializer() {
    super(LocalTime.class);
  }

  @Override
  public void serialize(LocalTime value, JsonGenerator generator, SerializerProvider provider) throws IOException {
    generator.writeString(value.format(DateTimeFormatter.ISO_LOCAL_TIME));
  }
}

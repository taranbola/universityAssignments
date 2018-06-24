/**
 * Resource.java
 */

package sample;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.HashMap;

/**
 * Class used for object mapping
 * @author Mitchell Gladstone
 */
public abstract class Resource {

  protected static HttpClient client;

  static {
    Resource.client = new HttpClient("localhost", 5000);
  }

  protected Object getMap(String json, Class klass){
    try {
      Object result = new ObjectMapper().readValue(json, klass);
      return result;
    } catch (Exception e) {
      System.err.println(e);
    }
    return null;
  }
}

/**
 * HttpClient.java
 */

package sample;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.io.OutputStream;
import java.net.URL;

/**
 * Class for establishing connection between api
 *
 * @author Mitchell Gladstone
 */
public class HttpClient {

  private String host;

  private int port;

  private String api_endpoint;

  public HttpClient(String host, int port){
    super();
    this.host = host;
    this.port = port;
    this.api_endpoint = "api";
  }

  public HttpClient(String host, int port, String api_endpoint){
    super();
    this.host = host;
    this.port = port;
    this.api_endpoint = api_endpoint;
  }

  private HttpURLConnection makeConenction(String endpoint) throws Exception {

    URL url = new URL("http://" + this.host + ":" + this.port+"/" + this.api_endpoint + "/"+endpoint);

    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestProperty("Accept", "application/json");
    return conn;
  }

  public String get(String endpoint) throws Exception {
    HttpURLConnection conn = makeConenction(endpoint);
    conn.setRequestMethod("GET");

    return this.request(conn);
  }

  public String put(String endpoint, String input) throws Exception {
    HttpURLConnection conn = makeConenction(endpoint);
    conn.setRequestMethod("PUT");
    conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "application/json");

    OutputStream os = conn.getOutputStream();
    os.write(input.getBytes());
    os.flush();

    return this.request(conn);
  }

  public String post(String endpoint, String input) throws Exception {
    HttpURLConnection conn = makeConenction(endpoint);
    conn.setRequestMethod("POST");
    conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "application/json");

    OutputStream os = conn.getOutputStream();
    os.write(input.getBytes());
    os.flush();

    return this.request(conn);
  }


  public String delete(String endpoint) throws Exception {
    HttpURLConnection conn = makeConenction(endpoint);
    conn.setRequestMethod("DELETE");
    return this.request(conn);
  }

  private String request(HttpURLConnection conn) throws Exception {
    try {


      BufferedReader br = new BufferedReader(new InputStreamReader(
        (conn.getInputStream())));

      String output;
      StringBuilder builder = new StringBuilder();
      while ((output = br.readLine()) != null) {
        builder.append(output);
      }

      conn.disconnect();
      return builder.toString();

    } catch (MalformedURLException e) {

    e.printStackTrace();

    } catch (IOException e) {

    e.printStackTrace();

    }
    return null;
  }

	public static void main(String[] args) throws Exception {
    HttpClient hc = new HttpClient("localhost", 5000);
    System.out.println(hc.get("screen"));

    hc.delete("screen/5");

    System.out.println("\n\n\n\n");

    System.out.println(hc.get("screen"));
	}

}

package comp3911.cwk2;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.PreparedStatement;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

@SuppressWarnings("serial")
public class AppServlet extends HttpServlet {

  private static final String CONNECTION_URL = "jdbc:sqlite:db.sqlite3";
  private static final String AUTH_QUERY = "select * from user where username= ? and password= ?";
  private static final String SEARCH_QUERY = "select * from patient where surname like ?";
  private static final Key aesSecretKey = new SecretKeySpec("compsci123456789".getBytes(), "AES");

  private final Configuration fm = new Configuration(Configuration.VERSION_2_3_28);
  private Connection database;

  @Override
  public void init() throws ServletException {
    configureTemplateEngine();
    connectToDatabase();
  }

  private void configureTemplateEngine() throws ServletException {
    try {
      fm.setDirectoryForTemplateLoading(new File("./templates"));
      fm.setDefaultEncoding("UTF-8");
      fm.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
      fm.setLogTemplateExceptions(false);
      fm.setWrapUncheckedExceptions(true);
    }
    catch (IOException error) {
      throw new ServletException(error.getMessage());
    }
  }

  private void connectToDatabase() throws ServletException {
    try {
      database = DriverManager.getConnection(CONNECTION_URL);
    }
    catch (SQLException error) {
      throw new ServletException(error.getMessage());
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
   throws ServletException, IOException {
    try {
      Template template = fm.getTemplate("login.html");
      template.process(null, response.getWriter());
      response.setContentType("text/html");
      response.setStatus(HttpServletResponse.SC_OK);
    }
    catch (TemplateException error) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Edited the dopost function to hash the passwords entered.
   * Uses SHA-256 to hash the password entered and that is sent as
   * a query to the SQL database to check it's values.
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
   throws ServletException, IOException {
     // Get form parameters
    String hashtext = "";
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String surname = request.getParameter("surname");
    try{
      MessageDigest encryptdigest = MessageDigest.getInstance("SHA-256");
      byte[] passwordhash = encryptdigest.digest(password.getBytes(StandardCharsets.UTF_8));
      BigInteger passint = new BigInteger(1, passwordhash);
      hashtext = passint.toString(16);
    }
    catch (NoSuchAlgorithmException e) {
      System.out.println(e);
    }

    try {
      if (authenticated(username, hashtext)) {
        // Get search results and merge with template
        Map<String, Object> model = new HashMap<>();
        model.put("records", searchResults(surname));
        Template template = fm.getTemplate("details.html");
        template.process(model, response.getWriter());
      }
      else {
        Template template = fm.getTemplate("invalid.html");
        template.process(null, response.getWriter());
      }
      response.setContentType("text/html");
      response.setStatus(HttpServletResponse.SC_OK);
    }
    catch (Exception error) {
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Modified it so that it takes in parameterised queries, which is better
   * than manually filtering out the values entered. This prevents SQL
   * injection attacks.
   */
  private boolean authenticated(String username, String password) throws SQLException {
    String query = String.format(AUTH_QUERY, username, password);
    try (PreparedStatement stmt = database.prepareStatement(AUTH_QUERY)) {
      stmt.setString(1,encrypt(username));
      stmt.setString(2,password);
      ResultSet results = stmt.executeQuery();
      return results.next();
    }
  }

  /**
   * Ammended it so that it queries an encrypted surname instead of just
   * the plain text they entered. It also decrypts the columns in the records,
   * which is then displayed on the website. The surname query is ammended to
   * use parameterised queries like the password and username.
   */
  private List<Record> searchResults(String surname) throws SQLException {
      List<Record> records = new ArrayList<>();
      String query = String.format(SEARCH_QUERY, surname);
      try (PreparedStatement stmt = database.prepareStatement(SEARCH_QUERY)) {
        stmt.setString(1,encrypt(surname));
        ResultSet results = stmt.executeQuery();
        while (results.next()) {
          Record rec = new Record();
          rec.setSurname(decrypt(results.getString(2)));
          rec.setForename(decrypt(results.getString(3)));
          rec.setAddress(decrypt(results.getString(4)));
          rec.setDateOfBirth(decrypt(results.getString(5)));
          rec.setDoctorId(results.getString(6));
          rec.setDiagnosis(decrypt(results.getString(7)));
          records.add(rec);
        }
      }
      return records;
  }

  /**
   * This will encrypt a given string in AES.
   * @param  String plain The string to be decrypted.
   * @return String encrypted The encrypted string.
   */
  public String encrypt(String plain){
    try{
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, aesSecretKey);
        byte[] bytePlain = cipher.doFinal(plain.getBytes());
        String encrypted = Base64.getEncoder().encodeToString(bytePlain);
        return encrypted;
    }
    catch(Exception e){
        e.printStackTrace();
    }
    return null;
  }

  /**
   * This will decrypt a given string in AES.
   * @param  String encrypted The string to be decrypted.
   * @return String decrypted The decrypted string.
   */
  private String decrypt(String encrypted){
    try{
        Cipher cipher = Cipher.getInstance("AES");
        byte[] byteEncrypted = Base64.getDecoder().decode(encrypted);
        cipher.init(Cipher.DECRYPT_MODE, aesSecretKey);
        String decrypted = new String(cipher.doFinal(byteEncrypted));
        return decrypted;
    }
    catch(Exception e){
        e.printStackTrace();
    }
    return null;
  }

}

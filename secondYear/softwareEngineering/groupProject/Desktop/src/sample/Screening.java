/**
 * Screening.java
 */

package sample;
import java.time.LocalTime ;
import java.time.LocalDate ;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Class for representing a screening.
 *
 * @author Mitchell Gladstone
 */
public class Screening {
  /** ID number of the screening. */
  private int screening_id ;
  /** ID number of the film. */
  private int film_id;

  @JsonDeserialize(using = LocalTimeDeserializer.class)
  @JsonSerialize(using = LocalTimeSerializer.class)
  private LocalTime screening_time ;

  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  private LocalDate screening_date ;

  /** ID number of the screen. */
  private int screen_id;

  /**
   * [JsonProperty description]
   * @param "screen" [description]
   */
  @JsonProperty("screen")
  private void unpackScreenFromNextedObject(Map<String, String> screen) {
     this.screen_id = Integer.parseInt(screen.get("screen_id"));
  }

  /**
   * Default Screening constructor.
   */
	public Screening() {
		super();
	}

	/**
	 * Screening constructor.
	 * @param screening_id   ID number of screening.
	 * @param film_id        ID number of film.
	 * @param screening_time [description]
	 * @param screening_date [description]
	 * @param screen_id      ID number of screen.
	 */
	public Screening(int screening_id, int film_id, LocalTime screening_time, LocalDate screening_date, int screen_id) {
		super();
		this.screening_id = screening_id;
		this.film_id = film_id;
		this.screening_time = screening_time;
		this.screening_date = screening_date;
		this.screen_id = screen_id;
	}

	/**
	 * @return ID number of screening.
	 */
	public int getScreening_id() {
		return screening_id;
	}

	/**
	 * Sets new value of this.screening_id.
	 * @param screening_id ID number of screening.
	 */
	public void setScreening_id(int screening_id) {
    if(screening_id < 0) {
      throw new IllegalArgumentException("ID must NOT be negative.");
    } else {
		  this.screening_id = screening_id;
    }
	}

	/**
	 * @return ID number of film.
	 */
	public int getFilm_id() {
		return film_id;
	}

	/**
	 * Sets new value of this.film_id.
	 * @param film_id ID number of film.
	 */
	public void setFilm_id(int film_id) {
    if(film_id < 0) {
      throw new IllegalArgumentException("ID must NOT be negative.");
    } else {
		  this.film_id = film_id;
    }
	}

	/**
	 * @return [description]
	 */
	public LocalTime getScreening_time() {
		return screening_time;
	}

	/**
	 * Sets new value of this.screening_time.
	 * @param screening_time [description]
	 */
	public void setScreening_time(LocalTime screening_time) {
		this.screening_time = screening_time;
	}

	/**
	 * @return [description]
	 */
	public LocalDate getScreening_date() {
		return screening_date;
	}

	/**
	 * Sets new value of this.screening_date.
	 * @param screening_date [description]
	 */
	public void setScreening_date(LocalDate screening_date) {
    LocalDate current = LocalDate.now();
		 this.screening_date = screening_date;
	}

	/**
	 * @return ID number of screen.
	 */
	public int getScreen_id() {
		return screen_id;
	}

	/**
	 * Sets new value of this.screen_id.
	 * @param screen_id ID number of screen.
	 */
	public void setScreen_id(int screen_id) {
    if(screen_id < 0) {
      throw new IllegalArgumentException("ID must NOT be negative.");
    } else {
		  this.screen_id = screen_id;
    }
	}

	/**
	 * @return String representation of Screening for printing.
	 */
	@Override
	public String toString() {
		return "Screening [screening_id=" + screening_id + ", film_id=" + film_id + ", screening_time=" + screening_time + ", screening_date=" + screening_date + ", screen_id=" + screen_id + "]";
	}
}

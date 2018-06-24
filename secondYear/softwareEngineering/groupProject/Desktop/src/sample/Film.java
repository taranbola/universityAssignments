/**
 * Film.java
 */

package sample;

/**
 * Class for storing all infromation regarding films, provides methods to fetch
 * specific film properties. 
 *
 * @author Dan Aves and Mitchell Gladstone
 */
public class Film {
  /** ID number of the film. */
  private int film_id;
  /** Name of the film. */
  private String film_name;
  /** Description of the film. */
  private String film_description;
  /** Runtime of the film. */
  private int film_runtime;
  /** Director of the film. */
  private String film_director;
  /** Age rating of the film. */
  private String film_age_rating;

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
	 * @return Name of film.
	 */
	public String getFilm_name() {
		return film_name;
	}

	/**
	 * Sets new value of this.film_name.
	 * @param film_name Name of film.
	 */
	public void setFilm_name(String film_name) {
    if(film_name == null || film_name == "") {
      throw new IllegalArgumentException("Film must have a name.");
    } else {
      this.film_name = film_name;
    }
	}

	/**
	 * @return Description of film.
	 */
	public String getFilm_description() {
		return film_description;
	}

	/**
	 * Sets new value of this.film_description.
	 * @param film_description Description of film.
	 */
	public void setFilm_description(String film_description) {
    if(film_description == null || film_description == "") {
      throw new IllegalArgumentException("Film must have a description.");
    } else {
		  this.film_description = film_description;
    }
	}

	/**
	 * @return Runtime of film.
	 */
	public int getFilm_runtime() {
		return film_runtime;
	}

	/**
	 * Sets new value of this.film_runtime.
	 * @param film_runtime Runtime of film.
	 */
	public void setFilm_runtime(int film_runtime) {
    if(film_runtime < 0) {
      throw new IllegalArgumentException("Runtime must NOT be negative.");
    } else {
		  this.film_runtime = film_runtime;
    }
	}

	/**
	 * @return Director of film.
	 */
	public String getFilm_director() {
		return film_director;
	}

	/**
	 * Sets new value of this.film_director.
	 * @param film_director Director of film.
	 */
	public void setFilm_director(String film_director) {
    if(film_director == null || film_director == "") {
      throw new IllegalArgumentException("Film must have a director.");
    } else {
		  this.film_director = film_director;
    }
	}

	/**
	 * @return Age rating of film.
	 */
	public String getFilm_age_rating() {
		return film_age_rating;
	}

	/**
	 * Sets new value of this.film_age_rating.
	 * @param film_age_rating Age rating of film.
	 */
	public void setFilm_age_rating(String film_age_rating) {
    if(film_age_rating == null || film_age_rating == "") {
      throw new IllegalArgumentException("Film must have an age rating.");
    } else {
		  this.film_age_rating = film_age_rating;
    }
	}

	/**
	 * @return String representation of Film for printing.
	 */
	@Override
	public String toString() {
		return "Film [film_id=" + film_id + ", film_name=" + film_name + ", film_description=" + film_description + ", film_runtime=" + film_runtime + ", film_director=" + film_director + ", film_age_rating=" + film_age_rating + "]";
	}
}

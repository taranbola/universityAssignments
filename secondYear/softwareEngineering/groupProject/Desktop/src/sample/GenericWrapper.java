/**
 * GenericWrapper.java
 */

package sample;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Generic wrapper is used for object serialisation/deserialisation.
 *
 * @author Mitchell Gladstone
 */
public class GenericWrapper<T> {

  private List<T> objects;

  private int num_results;

  private int total_pages;

  private int page;

	/**
	* Returns value of objects
	* @return
	*/
	public List<T> getObjects() {
		return objects;
	}

	/**
	* Sets new value of objects
	* @param objects.
	*/
	public void setObjects(List<T> objects) {
		this.objects = objects;
	}

	/**
	* Returns value of num_results
	* @return
	*/
	public int getNum_results() {
		return num_results;
	}

	/**
	* Sets new value of num_results
	* @param num_results
	*/
	public void setNum_results(int num_results) {
    if(num_results < 0) {
      throw new IllegalArgumentException("Must NOT be negative.");
    } else {
		  this.num_results = num_results;
    }
	}

	/**
	* Returns value of total_pages
	* @return
	*/
	public int getTotal_pages() {
		return total_pages;
	}

	/**
	* Sets new value of total_pages
	* @param total_pages
	*/
	public void setTotal_pages(int total_pages) {
    if(total_pages < 0) {
      throw new IllegalArgumentException("Must NOT be negative.");
    } else {
		  this.total_pages = total_pages;
    }
	}

	/**
	* Returns value of page
	* @return
	*/
	public int getPage() {
		return page;
	}

	/**
	* Sets new value of page
	* @param page
	*/
	public void setPage(int page) {
    if(page < 0) {
      throw new IllegalArgumentException("Must NOT be negative.");
    } else {
		  this.page = page;
    }
	}
}

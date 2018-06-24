package comp2931.cwk1;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Unit Tests for the Date Class
 */
public class DateTest {
  private Date test1 = new Date(1997,1,2);

   @Test
   public void testToString() {
     assertThat(new Date(2013,12,12).toString(), is("2013-12-12"));
     assertThat(new Date(2014,05,11).toString(), is("2014-05-11"));
   }

   @Test(expected=IllegalArgumentException.class)
   public void yearToLow() {
     new Date(-1,1,1);
   }

   @Test(expected=IllegalArgumentException.class)
   public void monthToLow() {
     new Date(0,-1,1);
   }

   @Test(expected=IllegalArgumentException.class)
   public void monthToHigh() {
     new Date(0,13,1);
   }

   @Test(expected=IllegalArgumentException.class)
   public void daysToLow() {
     new Date(0,1,-1);
   }

   @Test(expected=IllegalArgumentException.class)
   public void daysToHigh() {
     new Date(0,1,32);
   }

   @Test(expected=IllegalArgumentException.class)
   public void leapYearInvalid() {
     new Date(2001,2,29);
   }

   @Test(expected=IllegalArgumentException.class)
   public void leapYearInvalid2() {
     new Date(2017,2,29);
   }

   @Test
   public void leapYearValid() {
     new Date(2000,2,29);
     new Date(1996,2,29);
   }

   @Test
   public void equality() {
     assertTrue(test1.equals(test1));
     assertTrue(test1.equals(new Date(1997,1,2)));
     assertFalse(test1.equals(new Date(13,1,1)));
     assertFalse(test1.equals(new Date(12,1,1)));
     assertFalse(test1.equals(new Date(12,12,21)));
     assertFalse(test1.equals("12-01-02"));
   }

   @Test
   public void testDayOfYear() {
     assertThat(new Date(2013,1,1).getDayOfYear(), is(1));
     assertThat(new Date(2013,12,31).getDayOfYear(), is(365));
     assertThat(new Date(2013,1,16).getDayOfYear(), is(16));
     assertThat(new Date(2012,12,31).getDayOfYear(), is(366));
   }

   @Test
    public void currentDay() {
      DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate local = LocalDate.now();
      assertThat(new Date().toString(), is(dateFormat.format(local)));
    }
}

public enum Month {
  JANUARY,
  FEBRUARY,
  MARCH,
  APRIL,
  MAY,
  JUNE,
  JULY,
  AUGUST,                   //Constants for each month
  SEPTEMBER,
  OCTOBER,
  NOVEMBER,
  DECEMBER;

  @Override
  public String toString() {
    return name().substring(0, 1)         //outputs these strings in lowercase
         + name().substring(1).toLowerCase();
  }

}

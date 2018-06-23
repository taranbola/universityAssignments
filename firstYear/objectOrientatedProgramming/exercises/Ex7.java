class Rectangle{
  private double width;
  private double height;

  public Rectangle(){
    width = 1;                            //method to initliase the class..
    height = 1;                           //with default values of 1
  };

  public Rectangle(double w, double h){
    width = w;                            //allows this to be replaced with
    height = h;                           //some parameters
  };

  public double getWidth(){return width;}

  public double getHeight(){return height;}

  public double getArea(){
    double area = 0;
    area = width*height;
    return area;
  }
  public double getPerimeter(){
    double perimeter = 0;
    perimeter = (width*2) + (height*2);
    return perimeter;
  }
}

public class Ex7{
    public static void main(String[] args) {
      Rectangle rectangle1 = new Rectangle(4,40);    //object that has a set
      System.out.println("Rectangle 1:");            // of parameters
      System.out.println("Width = " + rectangle1.getWidth());
      System.out.println("Height = " + rectangle1.getHeight());
      System.out.println("Area = " + rectangle1.getArea());
      System.out.println("Perimeter = " + rectangle1.getPerimeter());

      Rectangle rectangle2 = new Rectangle(3.5,35.9); //object 2 that has a set
      System.out.println("\nRectangle 2:");           //of parameters
      System.out.println("Width = " + rectangle2.getWidth());
      System.out.println("Height = " + rectangle2.getHeight());
      System.out.println("Area = " + rectangle2.getArea());
      System.out.println("Perimeter = " + rectangle2.getPerimeter());

  }
}

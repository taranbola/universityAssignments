import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import java.io.FileNotFoundException;


public class WeatherPlot extends Application {
  public static WeatherStation station;
    @Override public void start(Stage stage) {
        stage.setTitle("Max Temperature vs Sun Hours");

        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Sun Hours");
        yAxis.setLabel("Max Temperature");

        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<Number,Number>(xAxis,yAxis);

        lineChart.setTitle("Sun Hours VS Max Temperature for " + station.getName() + " for " + station.getRecordCount() + " Months");

        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Each Month of " + station.getName());

        //populating the series with data
        for(int value = 0; value < station.getRecordCount(); value++){
          WeatherRecord weatherRecord7 = station.getRecord(value);
          double xValue = weatherRecord7.getSunHours();
          double yValue = weatherRecord7.getMaxTemp();
          series.getData().add(new XYChart.Data(xValue,yValue));
          }

        Scene scene  = new Scene(lineChart,2000,800);
        lineChart.getData().add(series);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws FileNotFoundException {
        station = new WeatherStation(args[0]);
        launch(args);
    }
}

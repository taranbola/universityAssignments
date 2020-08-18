package com.tutorial.jaxrs.calc;

import java.util.List;
/**
 * This is the cost model used in Stuttgart.
 * http://www.callabike-interaktiv.de/en?
 */
public class StuttgartModel implements costModels{
    private double endcost = 0.0;
    private static final double average = 15.5;
    private static final double minutecharge = 0.10;

    /**
     * Calculates the endcost for the Stuttgart cost model.
     * @param paths The data used to help calculate the cost
     */
    StuttgartModel(List<Newnode> paths) {
        for (Newnode each: paths) {
            double end = getEndcost();
            double time = each.getStartdist()/average;

            double charge = 0.0;
            double temp = time*60;
            double tempdailycharge = 0.0;
            while (temp != 0.0){
                double loopcharge;
                if(temp <= 1440) {
                    if (temp <= 60.0) {
                        loopcharge = temp * minutecharge;
                        if (loopcharge < 4.00) {
                            charge += loopcharge;
                        } else {
                            charge += 4.00;
                        }
                        temp = 0.0;
                    }
                    if (temp > 60.0) {
                        temp -= 60.0;
                        charge += 4.00;
                        tempdailycharge += 4.00;
                    }
                    if(tempdailycharge > 16.00){
                        temp = 0.0;
                        charge -= tempdailycharge;
                        charge += 16.00;
                    }
                }
                else{
                    temp -= 1440;
                    charge += 16.0;
                }
            }

            end += charge;
            setEndcost(end);
        }

    }

    public double getEndcost() {
        return endcost;
    }

    public void setEndcost(double endcost) {
        this.endcost = endcost;
    }
}

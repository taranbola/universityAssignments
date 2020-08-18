package com.tutorial.jaxrs.calc;

import java.util.List;

/**
 * Cost model based on the amount of time the paths
 * are and the charge_per_time is the amount in pounds
 * used to charge the cyclist.
 */
public class DefaultTimeModel implements costModels {
    private double endcost = 0.0;
    private static final double average = 15.5;

    /**
     * This calculates the endcost based on the time.
     * @param paths Data used to calculate the cost.
     * @param charge_per_time The amount that will be charged per minute
     */
    DefaultTimeModel(List<Newnode> paths, double charge_per_time) {
        for (Newnode each: paths) {
            double end = getEndcost();
            double time = each.getStartdist()/average;
            double charge = 0.0;

            double time_in_mins = time * 60;
            double time_charge = Math.round(time_in_mins) * charge_per_time;
            charge += time_charge;


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

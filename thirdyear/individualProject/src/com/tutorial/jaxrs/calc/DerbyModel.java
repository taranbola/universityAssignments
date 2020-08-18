package com.tutorial.jaxrs.calc;

import java.util.List;

/**
 * This is based on the costmodel in Derby
 * https://www.ebikesderby.com/
 */
class DerbyModel implements costModels{
    private double endcost = 0.0;
    private static final double average = 15.5;
    private static final double starting_charge = 1.0;

    /**
     * Calculates the cost model of Derby
     * @param paths Used to calculate the endcost
     */
    DerbyModel(List<Newnode> paths) {
        for (Newnode each: paths) {
            double end = getEndcost();
            double time = each.getStartdist()/average;
            double charge = 0.0;

            charge += starting_charge;

            double time_in_mins = time * 60;
            double time_charge = Math.round(time_in_mins) * 0.03;
            charge += time_charge;

            if (charge > 12.00) {
                charge = 12.00;
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

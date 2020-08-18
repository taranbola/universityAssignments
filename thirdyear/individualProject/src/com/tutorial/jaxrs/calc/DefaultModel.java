package com.tutorial.jaxrs.calc;

import java.util.List;

/**
 * The cost model is based on the amount of distance
 * a user rides. The charge is based on the number of
 * km a user rides.
 */
public class DefaultModel implements costModels{
    private double endcost = 0.0;

    /**
     * Calculates the endcost depending on the users distance
     * @param paths The data used to calculate cost.
     * @param charge_per_distance The charge per km for the user
     */
    DefaultModel(List<Newnode> paths, double charge_per_distance) {
        for (Newnode each: paths) {
            double end = getEndcost();
            double temp = each.getStartdist() * charge_per_distance;
            end += temp;
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

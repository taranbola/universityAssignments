package com.tutorial.jaxrs.calc;

import java.util.List;

/**
 * This is the London cost model where the system is based on
 * https://www.visitlondon.com/traveller-information/getting-around-london/london-cycle-hire-scheme
 */
public class LondonModel implements costModels{
    private double endcost = 0.0;
    private static final double average = 15.5;
    private static final double starting_charge = 2.0;

    /**
     * Calculates the endcost of the London model.
     * @param paths List of paths to be used to calculate cost.
     */
    LondonModel(List<Newnode> paths) {
        for (Newnode each: paths) {
            double end = getEndcost();
            double time = each.getStartdist()/average;
            double charge = 0.0;

            if(time > 0.5) {
                charge += starting_charge;
            }

            if (time > 24) {
                double temp = time - 24;
                double extra_charge = temp * 2;
                charge += extra_charge;
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

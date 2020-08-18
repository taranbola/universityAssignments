package com.tutorial.jaxrs.calc;

import java.util.List;

/**
 * This is the cost model used a lot in china.
 * https://www.travelchinacheaper.com/china-bike-share-travelers-guide
 */
public class ChinaModel implements costModels {
    private double endcost = 0.0;
    private static final double average = 15.5;
    private static final double halfhourcharge = 0.12;

    /**
     * Calculates the endcost for the China cost model.
     * @param paths The data used to help calculate the cost
     */
    ChinaModel(List<Newnode> paths) {
        for (Newnode each: paths) {
            double end = getEndcost();
            double time = each.getStartdist()/average;

            double charge = 0.0;
            double temp = (time*2) * halfhourcharge;
            charge += temp;


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

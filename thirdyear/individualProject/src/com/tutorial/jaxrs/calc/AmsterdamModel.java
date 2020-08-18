package com.tutorial.jaxrs.calc;

import java.util.List;

/**
 * This is the cost model used in Amsterdam.
 * http://www.yellowbike.nl/tour/bike-rental/
 */
public class AmsterdamModel implements costModels{
    private double endcost = 0.0;
    private static final double average = 15.5;
    private static final double halfhourcharge = 0.12;

    /**
     * Calculates the endcost for the Amsterdam cost model.
     * @param paths The data used to help calculate the cost
     */
    public AmsterdamModel(List<Newnode> paths) {
        for (Newnode each: paths) {
            double end = getEndcost();
            double time = each.getStartdist()/average;

            double charge = 0.0;
            double temp = (time*60) * halfhourcharge;
            if(temp <= 1440){
                charge = 12.00;
            }
            else if (temp <= 2880){
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

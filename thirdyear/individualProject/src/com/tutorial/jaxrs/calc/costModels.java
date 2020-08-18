package com.tutorial.jaxrs.calc;

/**
 * Interface implemented by cost models.
 * Getters and Setters the same in every cost model
 */
interface costModels {

    double endcost = 0.0;

    /**
     * Getter for the endcost of a cost model
     * @return Will return endcost of a model
     */
    double getEndcost();

    /**
     * Setter for the endcost of a cost model
     * @param endcost The endcost to set it too
     */
    void setEndcost(double endcost);
}

package com.tutorial.jaxrs.calc;
import java.util.ArrayList;

/**
 * This class represents a node which is part of a greater
 * graph network of nodes.
 */
class Newnode {
    private String label;
    private final ArrayList<Newnode> adjList = new ArrayList<>();
    private final ArrayList<Double> cost = new ArrayList<>();
    private double startdist;
    private double goalDist;
    private Newnode parent;
    private double lon;
    private double lat;

    /**
     * Getter for the unique name of the node
     * @return The name of the node
     */
    public String getLabel() {
        return label;
    }

    /**
     * Setter for the unique name of the node
     * @param label The name of the node to be set to.
     */
    void setLabel(String label) {
        this.label = label;
    }

    /**
     * Getter for to get a specific node neighbour
     * @return A specific node next to it.
     */
    Newnode getAdjList(int i) {
        return adjList.get(i);
    }

    /**
     * Setter to add a node neighbour
     * @param nodeBeingAdded The node next to it being added to the list
     */
    void setAdjList(Newnode nodeBeingAdded) {
        this.adjList.add(nodeBeingAdded);
    }

    /**
     * Function to check if adjList is empty
     * @return true/false depending on if it is empty.
     */
    boolean adjListIsEmpty(){
        return this.adjList.isEmpty();
    }

    /**
     * Finds the size of the adjList
     * @return The size of adjList
     */
    int adjListSize(){
        return this.adjList.size();
    }
    /**
     * Getter for cost of a specific neighbour
     * @return The cost of a specific neighbour
     */
    double getCost(int i) {
        return cost.get(i);
    }

    /**
     * Setter for a specific cost to be added.
     * @param cost Value to be added to the list of costs
     */
    void setCost(double cost) {
        this.cost.add(cost);
    }

    /**
     * Getter for the distance from the starting node
     * @return The distance from the starting node
     */
    double getStartdist() {
        return startdist;
    }

    /**
     * Setter for the distance from the starting node
     * @param startdist The actual length from the starting node.
     */
    void setStartdist(double startdist) {
        this.startdist = startdist;
    }

    /**
     * Getter for the  heuristic distance from the goalnode (Maybe 0.0)
     * @return The heuristic distance from the goalnode
     */
    double getGoalDist() {
        return goalDist;
    }

    /**
     * Setter for the heuristic distance from the goalnode.
     * @param goalDist The value to set the goaldist
     */
    void setGoalDist(double goalDist) {
        this.goalDist = goalDist;
    }

    /**
     * Getter for the parent node
     * @return Will return the parent of the current node (might be null)
     */
    public Newnode getParent() {
        return parent;
    }

    /**
     * Setter for the parent node
     * @param parent Will set the parent node
     */
    void setParent(Newnode parent) {
        this.parent = parent;
    }

    /**
     * Getter for the variable lon
     * @return Will return the longitude
     */
    double getLon() {
        return lon;
    }

    /**
     * Setter for the variable lon
     * @param lon Will set the longitude value
     */
    void setLon(double lon) {
        this.lon = lon;
    }

    /**
     * Getter for the variable lat
     * @return Will return the latitude
     */
    double getLat() {
        return lat;
    }

    /**
     * Setter for the variable lat.
     * @param lat Will set the latitude value
     */
    void setLat(double lat) {
        this.lat = lat;
    }
}

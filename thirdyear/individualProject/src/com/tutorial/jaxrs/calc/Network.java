package com.tutorial.jaxrs.calc;

import info.pavie.basicosmparser.controller.OSMParser;
import info.pavie.basicosmparser.model.Element;
import info.pavie.basicosmparser.model.Node;
import info.pavie.basicosmparser.model.Way;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class will represent a network of nodes,
 * which can then be used to call the routing algorithm.
 */
class Network {

    private final List<Node> arrayting = new ArrayList<>();
    private final List<Way> wayArrayTing = new ArrayList<>();
    private final Map<String, Newnode> mapofNodes = new HashMap<>();

    /**
     * Will construct a network of nodes from a map file, to be used in Routing.java
     * @param filename The name of the file to be parsed and used to
     *                 create the network.
     */
    Network(String filename){
        OSMParser p = new OSMParser();						//Initialization of the parser
        File osmFile = new File(filename);	//Create a file object for your OSM XML file

        String[] highallowed = {"trunk", "primary","secondary","tertiary","unclassified","residential","service","motorway_link","trunk_link","" +
                "primary_link","secondary_link","tertiary_link","living_street","pedestrian","track","escape","road","footway","path","cycleway"};
        try {
            Map<String,Element> result = p.parse(osmFile);		//Parse OSM data, and put result in a Map object

            for (String key : result.keySet()){
                char letter1 = key.charAt(0);
                if(Character.toString(letter1).equals("W")) {
                    Element ind = result.get(key);
                    for (int i = 0; i < highallowed.length; i++){
                        if ((ind.getTags().containsValue(highallowed[i]) & ind.getTags().containsKey("highway")  ) || ind.getTags().containsKey("cycleway")) {
                            Way myBike = (Way)ind;
                            setWayArrayTing(myBike);
                            List<Node> hello = myBike.getNodes();
                            for (Node each : hello) {
                                setArrayting(each);
                                Newnode a = new Newnode();
                                a.setLabel(each.getId());
                                a.setLat(each.getLat());
                                a.setLon(each.getLon());
                                setMapofNodes(each.getId(),a);
                            }
                        }
                    }
                }
            }


        } catch (IOException | SAXException e) {
            e.printStackTrace();								//Input/output errors management
        }
        System.out.println("Network Constructer Completed");
    }

    /**
     * Getter for the variable ArrayTing
     * @return The variable arrayTing
     */
    private List<Node> getArrayting() {
        return arrayting;
    }

    /**
     * Getter for the variable getWayArrayTing
     * @return The variable wayArrayTing
     */
    private List<Way> getWayArrayTing() {
        return wayArrayTing;
    }

    /**
     * Getter for the MapOfNodes variable
     * @return The variable MapOfNodes
     */
     private Map<String, Newnode> getMapofNodes() {
        return mapofNodes;
    }

    /**
     * Setter for the ArrayTing variable
     * @param toBeAdded The node to be added to the ArrayTing list
     */
    private void setArrayting(Node toBeAdded) {
        this.arrayting.add(toBeAdded);
    }

    /**
     * Setter for the WayArrayTing variable
     * @param toBeAdded The Way to be added to the WayArrayTing list
     */
    private void setWayArrayTing(Way toBeAdded) {
        this.wayArrayTing.add(toBeAdded);
    }

    /**
     * Setter for the MapOfNodes variable
     * @param each The String to be added to a mapOfLists Entry
     * @param toBeAdded The Newnode to be added to a mapOfLists Entry
     */
    private void setMapofNodes(String each , Newnode toBeAdded) {
        this.mapofNodes.put(each,toBeAdded);
    }

    /**
     * This function will run the astar routing class a certain number of paths.
     * @param number The number of paths that need to be created
     * @return A list of goal nodes, that contain a path in each one.
     */
    List<Newnode> astar(int number){
        List<Newnode> listofpaths = new ArrayList<>();
        Routing run = new Routing(getArrayting(),getWayArrayTing(),getMapofNodes());
        for (int i = 0; i < number; i++) {
            Newnode al = run.algo();
            listofpaths.add(al);
            System.out.println("Path #" + (i+1) + " found");
        }
        return listofpaths;
    }

}

package com.tutorial.jaxrs.calc;

import info.pavie.basicosmparser.model.Node;
import info.pavie.basicosmparser.model.Way;
import java.util.*;
import static com.tutorial.jaxrs.calc.Haversine.haversine;

/**
 * This class is used to construct a path from 2 nodes which will
 * make use of an A* algorithm.
 */
 class Routing {
    private List<Node> arrayting;
    private Map<String, Newnode> mapOfNodes;

    /**
     * Function to generate the roads needed for the constructor
     * @param ar This is used to have a list of all the nodes.
     * @param wayArrayTing This is used to construct the different cost of each node in a way.
     * @param mon This is a map of nodes which are used as a graph of nodes.
     */
    Routing(List<Node> ar, List<Way> wayArrayTing, Map<String, Newnode> mon) {
        setArrayting(ar);
        setMapOfNodes(mon);

        for (Way each : wayArrayTing) {
            List<Node> listOfN = each.getNodes();
            for (int c =0; c < (listOfN.size()-1); c++){
                Node a = listOfN.get(c);
                Node b = listOfN.get(c+1);
                Newnode a1 = getMapOfNodes().get(a.getId());
                Newnode b2 = getMapOfNodes().get(b.getId());
                a1.setAdjList(b2);
                a1.setCost(haversine(a.getLat(),a.getLon(),b.getLat(),b.getLon()));
            }
            //adds the cost and adjList for each node and way <Done Once>
        }

        System.out.println("Routing Constructor Completed");
    }

    /**
     * Getter for the ArrayTing variable
     * @return Returns this private variable to be used.
     */
    private List<Node> getArrayting() {
        return arrayting;
    }

    /**
     * Setter for the ArrayTing variable
     * @param arrayting This is the variable that will become arrayting.
     */
    private void setArrayting(List<Node> arrayting) {
        this.arrayting = arrayting;
    }

    /**
     * Getter for the mapOfNodes variable
     * @return Returns this private variable to be used.
     */
    private Map<String, Newnode> getMapOfNodes() {
        return mapOfNodes;
    }

    /**
     * Setter for the mapOfNodes variable
     * @param mapOfNodes This is the variable that will become mapOfNodes.
     */
    private void setMapOfNodes(Map<String, Newnode> mapOfNodes) {
        this.mapOfNodes = mapOfNodes;
    }

    /**
     * Will generate a random node from mapofNodes. Used in algo()
     * @return a random node on the map.
     */
    private Node random(){
        Random generator = new Random();
        Node[] hello = getArrayting().toArray(new Node[0]);
        Node randomValue = hello[generator.nextInt(hello.length)];
        Newnode rand = getMapOfNodes().get(randomValue.getId());
        if(rand.adjListIsEmpty()){
            return random();
        }
        else{
            return randomValue;
        }
    }

    /**
     * This is a routing algorithm which uses A* to allow you to create
     * a route between 2 points.
     * @return An instance of newnode which contains the path, from start to finish
     * with its self and parent node.
     */
    Newnode algo(){
        Newnode returnnode = null;
        Node randomValue1 = random();
        Node randomValue2 = random();

        // A* algorithm
        List<Newnode> open_list = new ArrayList<>();
        Newnode n = getMapOfNodes().get(randomValue1.getId());
        n.setStartdist(0.0);
        n.setGoalDist(haversine(n.getLat(),n.getLon(),randomValue2.getLat(),randomValue2.getLon()));
        n.setParent(null);
        open_list.add(n);

        Newnode goalnode = getMapOfNodes().get(randomValue2.getId());
        List<Newnode> closed_list = new ArrayList<>();

        while(open_list.size() != 0){
            open_list.sort(Comparator.comparingDouble(c -> c.getStartdist()+c.getGoalDist()));
            Newnode m = open_list.get(0);

            if(m.equals(goalnode)){
                returnnode = m;
            }

            open_list.remove(m);
            closed_list.add(m);

            for (int i = 0; i < m.adjListSize(); i++) {
                Newnode each = m.getAdjList(i);

                if(closed_list.contains(each)){
                    continue;
                }

                double cost = m.getCost(i) + m.getStartdist();
                if(open_list.contains(each) && (cost < n.getStartdist())) {
                    open_list.remove(each);
                }

                if(closed_list.contains(each) && (cost < n.getStartdist())) {
                    closed_list.remove(each);
                }

                if(!open_list.contains(each) && !closed_list.contains(each)) {
                    each.setStartdist(cost);
                    each.setGoalDist(haversine(each.getLat(),each.getLon(),randomValue2.getLat(),randomValue2.getLon()));
                    each.setParent(m);
                    open_list.add(each);
                }

            }
        }
        if(null == returnnode){
            returnnode = algo();
        }
        return returnnode;
    }

}

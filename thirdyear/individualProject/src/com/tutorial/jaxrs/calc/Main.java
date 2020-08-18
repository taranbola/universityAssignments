package com.tutorial.jaxrs.calc;

import java.text.NumberFormat;
import java.util.List;

import static jdk.nashorn.internal.objects.Global.Infinity;

/**
 * The main function needed to run the program and the simulation.
 */
@SuppressWarnings("Duplicates")
class Main {

    /**
     * This is the main function used to run the simulation.
     * @param args !!!!!CURRENTLY UNUSED!!!!!
     */
    public static void main(String[] args) {
        int pathNumber = 10;
        long startTime = System.currentTimeMillis();
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        Network net = new Network("map.osm");
        List<Newnode> paths = net.astar(pathNumber);

        DefaultModel def = new DefaultModel(paths,1.0);     //£1 charge per km
        DefaultTimeModel deftime = new DefaultTimeModel(paths,1.0); //£1 charge per min
        LondonModel london = new LondonModel(paths);
        ChinaModel china = new ChinaModel(paths);
        DerbyModel derby = new DerbyModel(paths);
        StuttgartModel stuttgart = new StuttgartModel(paths);
        AmsterdamModel amsterdam = new AmsterdamModel(paths);

        System.out.println();
        System.out.println("The Default Distance model End Cost: " + formatter.format(def.getEndcost()));
        System.out.println("The Default Time model End Cost: " + formatter.format(deftime.getEndcost()));
        System.out.println("The London model End Cost: " + formatter.format(london.getEndcost()));
        System.out.println("The China model End Cost: " + formatter.format(china.getEndcost()));
        System.out.println("The Derby model End Cost: " + formatter.format(derby.getEndcost()));
        System.out.println("The Stuttgart Model End Cost: " + formatter.format(stuttgart.getEndcost()));
        System.out.println("The Amsterdam Model End Cost: " + formatter.format(amsterdam.getEndcost()));

        infogain(paths,pathNumber);

        long timeElapsed = System.currentTimeMillis() - startTime;
        System.out.println("\nTime taken to run program: " + timeElapsed);
    }

    /**
     * This will calculate the info gain of the data and check the attribute of whether it
     * is similar to a walking journey or cycling journey.
     * @param paths The data to check the data source
     * @param pathNumber The number of paths there are.
     */
    private static void infogain(List<Newnode> paths, int pathNumber){

        //These are the average walking and cycle distance for a journey in km
        final double walkingdistance = 1.0;
        final double cycledistance = 5.4;

        //These are the average walking and cycle journey times in mins
        final double walkingtime = 15.0;
        final double cycletime = 18.;

        //Calculating the entropy of the data.
        double walkcounter = 0.0;
        double cyclecounter = 0.0;

        double cyclespeed = 15.5;

        double walktimecounter1 = 0.0;
        double cycletimecounter1 =0.0;
        double walktimecounter2 = 0.0;
        double cycletimecounter2 =0.0;

        for (Newnode each : paths) {
            double check = each.getStartdist();
            double time = (check/cyclespeed)*60;
            if (Math.abs(walkingdistance - check) <= Math.abs(cycledistance - check)) {
                walkcounter += 1.0;
                if(Math.abs((walkingtime) - time) <= Math.abs(cycletime - time)){
                    walktimecounter1 += 1.0;
                }
                else{
                    cycletimecounter1 += 1.0;
                }

            } else //noinspection Duplicates
            {
                cyclecounter += 1.0;
                if(Math.abs((walkingtime) - time) <= Math.abs(cycletime - time)){
                    walktimecounter2 += 1.0;
                }
                else{
                    cycletimecounter2 += 1.0;
                }
            }
        }

        double walkingchildlog1 = Math.log( walktimecounter1/ walkcounter) / Math.log(2);
        double cyclingchildlog1 = Math.log( walktimecounter2/ walkcounter) / Math.log(2);
        double walkingchildlog2 = Math.log( cycletimecounter1/ cyclecounter) / Math.log(2);
        double cyclingchildlog2 = Math.log( cycletimecounter2/ cyclecounter) / Math.log(2);

        double walkchildentropy =  -((walktimecounter1/walkcounter)*walkingchildlog1)-((cycletimecounter1/walkcounter)*walkingchildlog2);
        double cyclechildentropy =  -((walktimecounter2/cyclecounter)*cyclingchildlog1)-((cycletimecounter2/cyclecounter)*cyclingchildlog2);

        if(walktimecounter1 == 0.0) walkchildentropy = 1.0;
        if(cycletimecounter1 == 0.0) walkchildentropy = 0.0;

        if(walktimecounter2 == 0.0) cyclechildentropy = 1.0;
        if(cycletimecounter2 == 0.0) cyclechildentropy = 0.0;

        double weighted_average = ((walkcounter/pathNumber)*walkchildentropy) + ((cyclecounter/pathNumber)*cyclechildentropy);

        double walkinglog = Math.log(walkcounter / pathNumber) / Math.log(2);
        double cyclinglog = Math.log(cyclecounter / pathNumber) / Math.log(2);
        double entropy = -((walkcounter/pathNumber)*(walkinglog))-((cyclecounter/pathNumber)*(cyclinglog));

        if(walkcounter == 0.0) entropy = 1.0;
        if(cyclecounter == 0.0) entropy = 0.0;

        if ((entropy-weighted_average) == Infinity) {
            System.out.println("\nThe information gain needs more paths in order to complete ");
        }
        else if ((entropy-weighted_average) < 0) {
            System.out.println("\nThe information gain needs more paths in order to complete ");
        }
        else{
            System.out.println("\nThe information gain is " + (entropy - weighted_average));
        }
    }
}

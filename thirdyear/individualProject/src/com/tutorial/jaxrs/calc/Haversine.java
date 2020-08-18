package com.tutorial.jaxrs.calc;

/**
 * Will calculate the distance between 2 points in km.
 */
class Haversine {
    private static final double R = 6372.8; // In km radius of earth

    /**
     * USed to calculate the distance between 2 points.
     * @param lat1 The latitude of point 1.
     * @param lon1 The Longitude of point 1
     * @param lat2 The latitude of point 2
     * @param lon2 The longitude of point 2
     * @return The distance between the 2 points
     */
    static double haversine(double lat1, double lon1, double lat2, double lon2) {

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);


        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c;
    }
}

package fr.iut.ocr;

import java.util.ArrayList;

/**
 * Created by theo on 31/03/17.
 */
public class Utils {

    private static double distEucli(ArrayList<Specification> vect1, ArrayList<Specification> vect2){
        double dist = 0;

        if(vect1.size() == vect2.size()){
            for (int i = 0; i < vect1.size(); i++) {
                dist += Math.pow(vect1.get(i).compareTo(vect2.get(i)), 2);
            }
            dist = Math.sqrt(dist);
        }
        else{
            System.out.println("Not the same size.");
            dist = Double.MAX_VALUE;
        }

        return dist;
    }

    //returns the index of the lowestDistance
    public static int lowestDistance(ArrayList<Specification> element, ArrayList<ArrayList<Specification>> references, int except){
        int index = -1;
        double dist = Double.MAX_VALUE;

        for (int i = 0; i < references.size(); i++) {
            if(i != except) {
                double tmp_dist = distEucli(element, references.get(i));

                if (tmp_dist < dist) {
                    index = i;
                    dist = tmp_dist;
                }
            }
        }

        return index;
    }

    //Returns the average distance
    public static double averageDistance(ArrayList<Specification> element, ArrayList<ArrayList<Specification>> references) {

        if(references.size() == 0)
            return Double.MAX_VALUE;

        double dist = 0;

        for(ArrayList<Specification> ref : references) {
            if(ref != element) //enleve la comparaison entre element et lui meme dans references
                dist += distEucli(element, ref);
        }

        return dist / (references.size()-1); //-1 afin d'enlever la comparaison entre element et lui meme dans references
    }
}

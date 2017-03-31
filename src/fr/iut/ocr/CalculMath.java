package fr.iut.ocr;

import java.util.ArrayList;

/**
 * Created by theo on 31/03/17.
 */
public class CalculMath {

    private static double distEucli(ArrayList<Double> vect1, ArrayList<Double> vect2){
        double dist = 0;

        if(vect1.size() == vect2.size()){
            for (int i = 0; i < vect1.size(); i++) {
                dist += Math.pow(vect1.get(i) - vect2.get(i), 2);
            }
            dist = Math.sqrt(dist);
        }
        else{
            System.out.println("Not the same size.");
        }

        return dist;
    }

    public static int PPV(ArrayList<Double> vect, ArrayList<ArrayList<Double>> tabVect, int except){
        int index = -1;
        double dist = 10;

        for (int i = 0; i < tabVect.size(); i++) {
            if(i != except){
                if(distEucli(vect, tabVect.get(i)) < dist){
                    index = i;
                    dist = distEucli(vect, tabVect.get(i));
                }
            }
        }

        return index;
    }
}

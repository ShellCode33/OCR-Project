package fr.iut.tests;

import fr.iut.ocr.Utils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by theo on 31/03/17.
 */
public class UtilsTest {

    @Test
    public void testPPV(){
        ArrayList<Double> tab0 = new ArrayList<>();
        ArrayList<Double> tab1 = new ArrayList<>();
        ArrayList<Double> tab2 = new ArrayList<>();
        ArrayList<Double> tab3 = new ArrayList<>();
        tab0.add(1.0);
        tab1.add(-1.0);
        tab2.add(1.0);
        tab3.add(0.0);
        ArrayList<ArrayList<Double>> myList = new ArrayList<>();
        myList.add(tab1);
        myList.add(tab2);
        myList.add(tab3);

        assertEquals(Utils.PPV(tab0, myList, -1), 1);
    }
}

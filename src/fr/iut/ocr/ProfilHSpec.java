package fr.iut.ocr;

import ij.process.ImageProcessor;

import java.util.ArrayList;

/**
 * Created by shellcode on 4/4/17.
 */
public class ProfilHSpec extends Specification {

    int horizontal[];

    public ProfilHSpec(ImageProcessor processor) {
        super(processor);
    }

    @Override
    public void compute() {
        byte[] pixels = (byte[]) processor.getPixels();
        int height = processor.getHeight();
        int width = processor.getWidth();

        horizontal = new int[height];

        //profil horizontal
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(pixels[i * width + j] < threshold_to_be_recognized_as_black)
                    horizontal[i]++;
            }
        }
    }

    @Override
    public double getValue() {
        return 0;
    }

    @Override
    double compareTo(Specification specification) {

        if(!(specification instanceof ProfilHSpec))
            return Double.MAX_VALUE;

        ProfilHSpec profilHSpec = (ProfilHSpec) specification;

        double dist = 0;

        for (int i = 0; i < horizontal.length; i++) {
            dist += Math.pow(horizontal[i] - profilHSpec.horizontal[i], 2);
        }

        return Math.sqrt(dist);
    }
}

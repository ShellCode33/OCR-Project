package fr.iut.ocr;

import ij.process.ImageProcessor;

/**
 * Created by shellcode on 4/4/17.
 */
public class ProfilVSpec extends Specification {
    private int[] vertical;

    public ProfilVSpec(ImageProcessor processor) {
        super(processor);
    }

    @Override
    void compute() {
        int width = processor.getWidth();
        byte[] pixels = (byte[]) processor.getPixels();

        vertical = new int[width];

        //profil vertical
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < processor.getHeight(); j++) {
                if(pixels[j * width + i ] < threshold_to_be_recognized_as_black)
                    vertical[i]++;
            }
        }
    }

    @Override
    double getValue() {
        return 0;
    }

    @Override
    double compareTo(Specification specification) {

        if(!(specification instanceof ProfilVSpec))
            return Double.MAX_VALUE;

        ProfilVSpec profilVSpec = (ProfilVSpec) specification;

        double dist = 0;

        for (int i = 0; i < vertical.length; i++) {
            dist += Math.pow(vertical[i] - profilVSpec.vertical[i], 2);
        }

        return Math.sqrt(dist);
    }
}

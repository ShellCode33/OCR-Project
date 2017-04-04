package fr.iut.ocr;

import ij.process.ImageProcessor;

/**
 * Created by shellcode on 4/4/17.
 */
public class IsoSpec extends Specification {

    private double surface = 0, perimeter = 0;

    public IsoSpec(ImageProcessor processor) {
        super(processor);
    }

    @Override
    public void compute() {
        for(int i = 0; i < processor.getWidth(); i++)
            for(int j = 0; j < processor.getHeight(); j++)
                if(processor.getPixel(i, j) < threshold_to_be_recognized_as_white)
                    surface++;

        for(int i = 0; i < processor.getWidth(); i++) {
            for (int j = 0; j < processor.getHeight(); j++) {
                if (processor.getPixel(i, j) < threshold_to_be_recognized_as_black) {
                    //On regarde tous les voisins de ce pixel, et s'il y a un voisin blanc on inrémente perimeter
                    int i_begin = i > 0 ? i-1 : 0;
                    int j_begin = j > 0 ? j-1 : 0;
                    int i_end = i < processor.getWidth()-1 ? i+1 : processor.getWidth()-1;
                    int j_end = j < processor.getHeight()-1 ? j+1 : processor.getHeight()-1;

                    boolean white_found = false;

                    for(int k = i_begin; k < i_end && !white_found; k++) {
                        for(int l = j_begin; l < j_end && !white_found; l++) {
                            if((k != i || l != j) && processor.getPixel(k, l) > threshold_to_be_recognized_as_white) {
                                perimeter++;
                                white_found = true;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public double getValue() {
        return perimeter / (4 * Math.PI * surface);
    }

    @Override
    double compareTo(Specification specification) {
        return getValue() - specification.getValue();
    }
}

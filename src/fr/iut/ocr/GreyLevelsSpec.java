package fr.iut.ocr;

import ij.process.ImageProcessor;

/**
 * Created by shellcode on 4/4/17.
 */
public class GreyLevelsSpec extends Specification {

    private double sum;

    public GreyLevelsSpec(ImageProcessor processor) {
        super(processor);
    }

    @Override
    public void compute() {
        byte[] pixels = (byte[]) processor.getPixels();
        int height = processor.getHeight();
        int width = processor.getWidth();

        sum = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                sum += pixels[j * width + i] & 0xff;
            }
        }
    }

    @Override
    public double getValue() {
        return sum / (processor.getWidth() * processor.getHeight());
    }

    @Override
    double compareTo(Specification specification) {
        return getValue() - specification.getValue();
    }
}

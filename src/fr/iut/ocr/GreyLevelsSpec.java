package fr.iut.ocr;

import ij.process.ImageProcessor;

/**
 * Created by shellcode on 4/4/17.
 */
public class GreyLevelsSpec extends Specification {

    private double result;

    public GreyLevelsSpec(ImageProcessor processor) {
        super(processor);
    }

    @Override
    public void compute() {
        byte[] pixels = (byte[]) processor.getPixels();
        int height = processor.getHeight();
        int width = processor.getWidth();

        double sum = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                sum += pixels[j * width + i] & 0xff;
            }
        }

        result = sum / (processor.getWidth() * processor.getHeight());
    }

    @Override
    public double getValue() {
        return result;
    }

    @Override
    double compareTo(Specification specification) {
        return getValue() - specification.getValue();
    }
}

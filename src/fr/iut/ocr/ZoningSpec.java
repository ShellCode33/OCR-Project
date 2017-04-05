package fr.iut.ocr;

import ij.process.ImageProcessor;

/**
 * Created by theo on 05/04/17.
 */
public class ZoningSpec extends Specification {

    private final int gridSize = 9;
    double mean[] = new double[gridSize];
    private ImageProcessor imageProcessor;

    public ZoningSpec(ImageProcessor processor) {
        super(processor);
        imageProcessor = processor;
    }

    @Override
    void compute() {
        int width = (int) (imageProcessor.getWidth() / Math.sqrt(gridSize));
        int height = (int) (imageProcessor.getHeight() / Math.sqrt(gridSize));
        for (int i = 0; i < gridSize; i++) {
            int x = (int) ((i % Math.sqrt(gridSize)) * width);
            int y = (int) ((i / Math.sqrt(gridSize)) * height);

            imageProcessor.setRoi(x,y, width, height);
            ImageProcessor cropped = imageProcessor.crop();
            GreyLevelsSpec greyLevelsSpec = new GreyLevelsSpec(cropped);
            greyLevelsSpec.compute();
            mean[i] = greyLevelsSpec.getValue();
        }
    }

    @Override
    double getValue() {
        return 0;
    }

    @Override
    double compareTo(Specification specification) {

        if(!(specification instanceof ZoningSpec))
            return Double.MAX_VALUE;

        ZoningSpec zoningSpec = (ZoningSpec) specification;

        double dist = 0;

        for (int i = 0; i < mean.length; i++) {
            dist += Math.pow(mean[i] - zoningSpec.mean[i], 2);
        }

        return Math.sqrt(dist);
    }
}

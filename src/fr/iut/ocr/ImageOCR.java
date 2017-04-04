package fr.iut.ocr;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;

import java.util.ArrayList;

/**
 * Created by shellcode on 3/31/17.
 */
public class ImageOCR {
    private ImagePlus img;
    private ImageProcessor processor;
    private char expected_value;
    private ArrayList<Specification> specifications;

    public ImageOCR(String filename, char expected_value) {
        img = new ImagePlus(filename);
        this.expected_value = expected_value;
        init();
    }

    public ImageOCR(ImagePlus imagePlus) {
        img = imagePlus;
        init();
    }

    private void init() {
        new ImageConverter(img).convertToGray8();
        processor = img.getProcessor();
        specifications = new ArrayList<>();
        resize(20, 20);
    }

    private void resize(int width , int height) {
        processor.setInterpolate(true);
        processor = processor.resize(width, height);
        img.setProcessor (null, processor) ;
    }

    public char getExpectedValue() {
        return expected_value;
    }

    public void addSpecification(Specification specification) {
        specifications.add(specification);
        specification.compute();
    }

    public ArrayList<Specification> getSpecifications() {
        return specifications;
    }

    public ImageProcessor getProcessor() {
        return processor;
    }
}

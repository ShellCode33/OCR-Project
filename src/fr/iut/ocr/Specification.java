package fr.iut.ocr;

import ij.process.ImageProcessor;

/**
 * Created by shellcode on 4/4/17.
 */
public abstract class Specification {

    protected final static int threshold_to_be_recognized_as_black = 50;
    protected final static int threshold_to_be_recognized_as_white = 255 - threshold_to_be_recognized_as_black;
    protected ImageProcessor processor;

    public Specification(ImageProcessor processor) {
        this.processor = processor;
    }

    abstract void compute();
    abstract double getValue();
    abstract double compareTo(Specification specification);
}

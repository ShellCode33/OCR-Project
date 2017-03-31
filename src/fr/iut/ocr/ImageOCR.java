package fr.iut.ocr;

import ij.ImagePlus;

import java.util.ArrayList;

/**
 * Created by shellcode on 3/31/17.
 */
public class ImageOCR {
    private ImagePlus img;
    private char expected_value;
    private ArrayList<Specification> specifications;

    public ImageOCR(String filename, char expected_value) {
        img = new ImagePlus(filename);
        this.expected_value = expected_value;
    }

    public char getExpectedValue() {
        return expected_value;
    }

    public char getFoundValue() {
        return (char)((int)'a' + (int)(Math.random() * 26));
    }
}

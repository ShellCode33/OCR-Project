package fr.iut.ocr;

import ij.ImagePlus;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by shellcode on 3/31/17.
 */
public class ImageOCR {
    private ImagePlus img;
    private String expected_value;
    private ArrayList<Specification> specifications;

    public ImageOCR(String filename, String expected_value) {
        img = new ImagePlus(filename);
    }
}

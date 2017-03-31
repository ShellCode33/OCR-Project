package fr.iut.ocr;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;

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


    public double averageImage(ImageProcessor ip){
        byte[] pixels = (byte[]) ip.getPixels(); // Notez le cast en byte ()
        int height = ip.getHeight();
        int width = ip.getWidth();

        int sum = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                sum += pixels[j * width + i] & 0xff;
            }
        }

        sum = sum / (width*height);

        return sum;
    }
}

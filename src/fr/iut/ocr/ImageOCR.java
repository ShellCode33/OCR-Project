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
    private char expected_value;
    private ArrayList<Double> specifications;

    public ImageOCR(String filename, char expected_value) {
        img = new ImagePlus(filename);
        new ImageConverter(img).convertToGray8();
        this.expected_value = expected_value;
        specifications = new ArrayList<>();
        resize(20, 20);
    }

    public ImageOCR(ImagePlus imagePlus) {
        img = imagePlus;
        new ImageConverter(img).convertToGray8();
        specifications = new ArrayList<>();
        resize(20, 20);
    }

    private void resize(int larg , int haut)
    {
        ImageProcessor ip2 = img.getProcessor();
        ip2.setInterpolate(true);
        ip2 = ip2.resize(larg, haut);
        img.setProcessor (null, ip2) ;
    }

    public char getExpectedValue() {
        return expected_value;
    }

    public void addGreyLevelsSpec() {
        specifications.clear();
        specifications.add(getGreyAverage());
    }

    public ArrayList<Double> getSpecifications() {
        return specifications;
    }

    public double getGreyAverage() {
        ImageProcessor ip = img.getProcessor();
        byte[] pixels = (byte[]) ip.getPixels();
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

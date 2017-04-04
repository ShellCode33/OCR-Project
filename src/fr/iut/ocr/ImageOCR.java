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
    private ArrayList<Double> specifications;

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

    public ArrayList<Double> getSpecifications() {
        return specifications;
    }

    public void addGreyLevelsSpec() {
        byte[] pixels = (byte[]) processor.getPixels();
        int height = processor.getHeight();
        int width = processor.getWidth();

        double sum = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                sum += pixels[j * width + i] & 0xff;
            }
        }

        sum = sum / (width*height);

        specifications.add(sum);
    }

    public void addProfilSpec() {
        byte[] pixels = (byte[]) processor.getPixels();
        int height = processor.getHeight();
        int width = processor.getWidth();

        int count[] = new int[width];

        //profil vertical
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(pixels[j * width + i ] > 0)
                    count[i]++;
            }
        }

        for (int i = 0; i < count.length; i++) {
            specifications.add((double) count[i]);
        }

        count = new int[height];

        //profil horizontal
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(pixels[i * width + j] > 0)
                    count[i]++;
            }
        }

        for (int i = 0; i < count.length; i++) {
            specifications.add((double) count[i]);
        }
    }
}

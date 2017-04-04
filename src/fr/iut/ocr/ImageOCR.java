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

    private void resize(int larg , int haut) {
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

    public void setFeatureProfilH(){
        ImageProcessor ip = img.getProcessor();
        byte[] pixels = (byte[]) ip.getPixels();
        int height = ip.getHeight();
        int width = ip.getWidth();

        int count[] = new int[width];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(pixels[j * width + i] > 0) // c'est comme ca qu'on test qu'un pixel est noir ?
                    count[i]++;
            }
        }

        for (int i = 0; i < count.length; i++) {
            specifications.add((double) count[i]);
        }

    }

    public void setFeatureProfilV(){
        ImageProcessor ip = img.getProcessor();
        byte[] pixels = (byte[]) ip.getPixels();
        int height = ip.getHeight();
        int width = ip.getWidth();

        int count[] = new int[height];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(pixels[i * width + j] > 0) // c'est comme ca qu'on test qu'un pixel est noir ?
                    count[i]++;
            }
        }

        for (int i = 0; i < count.length; i++) {
            specifications.add((double) count[i]);
        }
    }

}

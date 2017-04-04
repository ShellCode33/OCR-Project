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
    private final static int threshold_to_be_recognized_as_black = 50;
    private final static int threshold_to_be_recognized_as_white = 255 - threshold_to_be_recognized_as_black;

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
                if(pixels[j * width + i ] < threshold_to_be_recognized_as_black)
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
                if(pixels[i * width + j] < threshold_to_be_recognized_as_black)
                    count[i]++;
            }
        }

        for (int i = 0; i < count.length; i++) {
            specifications.add((double) count[i]);
        }
    }

    public void addIsoSpec() {
        double surface = 0, perimeter = 0;

        for(int i = 0; i < processor.getWidth(); i++)
            for(int j = 0; j < processor.getHeight(); j++)
                if(processor.getPixel(i, j) < threshold_to_be_recognized_as_white)
                    surface++;

        for(int i = 0; i < processor.getWidth(); i++) {
            for (int j = 0; j < processor.getHeight(); j++) {
                if (processor.getPixel(i, j) < threshold_to_be_recognized_as_black) {
                    //On regarde tous les voisins de ce pixel, et s'il y a un voisin blanc on inrémente perimeter
                    int i_begin = i > 0 ? i-1 : 0;
                    int j_begin = j > 0 ? j-1 : 0;
                    int i_end = i < processor.getWidth()-1 ? i+1 : processor.getWidth()-1;
                    int j_end = j < processor.getHeight()-1 ? j+1 : processor.getHeight()-1;

                    boolean white_found = false;

                    for(int k = i_begin; k < i_end && !white_found; k++) {
                        for(int l = j_begin; l < j_end && !white_found; l++) {
                            if((k != i || l != j) && processor.getPixel(k, l) > threshold_to_be_recognized_as_white) {
                                perimeter++;
                                white_found = true;
                            }
                        }
                    }
                }
            }
        }

        System.out.println(perimeter / (4 * Math.PI * surface));
        specifications.add(perimeter / (4 * Math.PI * surface));
    }
}

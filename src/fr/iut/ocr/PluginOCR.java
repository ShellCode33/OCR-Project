package fr.iut.ocr;

import ij.ImagePlus;
import ij.io.DirectoryChooser;
import ij.plugin.PlugIn;
import ij.process.ImageConverter;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by shellcode on 3/31/17.
 */
public class PluginOCR implements PlugIn {

    private ArrayList<ImageOCR> images = new ArrayList<>();

    @Override
    public void run(String s) {
        DirectoryChooser dirChooser = new DirectoryChooser("Select the references directory");
        String choosen_dir = dirChooser.getDirectory();

        if(choosen_dir != null)
            createImageList(choosen_dir);
    }


    private void createImageList(String path) {
        File[] files = new File(path).listFiles();

        if(files != null) {
            for (int i = 0; i < files.length; i++) {
                String filePath = files[i].getAbsolutePath();
                System.out.println("Processing : " + filePath);
                ImagePlus tmp = new ImagePlus(filePath);
                new ImageConverter(tmp).convertToGray8();
                images.add(new ImageOCR(filePath, files[i].getName().charAt(0)));
            }

            logOCR("/tmp");
        }
    }

    public void logOCR(String pathOut) {
        File file = new File(pathOut);

        if(file.isDirectory()) {
            file = new File(pathOut + "/logs");
        }


    }
}

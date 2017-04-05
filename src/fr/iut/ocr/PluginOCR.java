package fr.iut.ocr;

import ij.ImagePlus;
import ij.WindowManager;
import ij.io.DirectoryChooser;
import ij.measure.ResultsTable;
import ij.plugin.PlugIn;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by shellcode on 3/31/17.
 */
public class PluginOCR implements PlugIn {

    private static ArrayList<ImageOCR> references = new ArrayList<>();
    private ImageOCR imageToAnalyse;

    @Override
    public void run(String s) {
        if(s.equals("load_references")) {
            DirectoryChooser dirChooser = new DirectoryChooser("Select the references directory");
            String choosen_dir = dirChooser.getDirectory();

            if (choosen_dir != null)
                createImageList(choosen_dir);
        }

        else if(s.equals("confuse_matrix")) {
            if(references.size() == 0) {
                System.out.println("Load references before");
                return;
            }

            logOCR();
        }

        else if(s.equals("custom_image")) {
            if(references.size() == 0) {
                System.out.println("Load references before");
                return;
            }

            ImagePlus imp = WindowManager.getCurrentImage();

            if(imp == null) {
                System.out.println("Open an image before");
                return;
            }

            System.out.println("Analyzing " + imp.getTitle());

            imageToAnalyse = new ImageOCR(imp);
            imageToAnalyse.addSpecification(new GreyLevelsSpec(imp.getProcessor()));
            imageToAnalyse.addSpecification(new ProfilHSpec(imp.getProcessor()));
            imageToAnalyse.addSpecification(new ProfilVSpec(imp.getProcessor()));
            imageToAnalyse.addSpecification(new IsoSpec(imp.getProcessor()));
            imageToAnalyse.addSpecification(new ZoningSpec(imp.getProcessor()));
        }
    }

    private int[][] buildConfuseMatrix() {

        int count = (int) Math.sqrt(references.size());
        int confusion[][] = new int[count][count];


        ArrayList<ArrayList<Specification>> specsReferences = new ArrayList<>();
        ArrayList<ArrayList<Specification>> [] specsReferencesSorted = new ArrayList[count]; //zeros are grouped together, same for 2, 3, 4, ...

        for(int j = 0; j < count; j++)
            specsReferencesSorted[j] = new ArrayList<>(); //Initialise le tableau

        for (ImageOCR imageOCR : references) {
            specsReferences.add(imageOCR.getSpecifications());
            specsReferencesSorted[Integer.parseInt(""+imageOCR.getExpectedValue())].add(imageOCR.getSpecifications()); //On rassemble les specifications de references par chiffre
        }

        for(int i = 0; i < references.size(); i++) {
            int best_match = -1;
            double lowest_dist = Double.MAX_VALUE;

            for(int j = 0; j < specsReferencesSorted.length; j++) {

                double tmp_dist = Utils.averageDistance(references.get(i).getSpecifications(), specsReferencesSorted[j]);

                if(lowest_dist > tmp_dist) {
                    lowest_dist = tmp_dist;
                    best_match = j;
                }
            }

            //best_match = Integer.parseInt("" + references.get(Utils.lowestDistance(references.get(i).getSpecifications(), specsReferences, i)).getExpectedValue()); //ancien système de classification
            int expected = Integer.parseInt("" + references.get(i).getExpectedValue());
            confusion[expected][best_match]++;
        }

        return confusion;
    }

    private void createImageList(String path) {
        references.clear();
        File[] files = new File(path).listFiles();

        if(files != null) {
            for (int i = 0; i < files.length; i++) {
                String filePath = files[i].getAbsolutePath();
                System.out.println("Processing : " + filePath);
                ImageOCR newImg = new ImageOCR(filePath, files[i].getName().charAt(0));
                newImg.addSpecification(new GreyLevelsSpec(newImg.getProcessor()));
                newImg.addSpecification(new ProfilHSpec(newImg.getProcessor()));
                newImg.addSpecification(new ProfilVSpec(newImg.getProcessor()));
                newImg.addSpecification(new IsoSpec(newImg.getProcessor()));
                newImg.addSpecification(new ZoningSpec(newImg.getProcessor()));
                references.add(newImg);
            }

            System.out.println("" + references.size() + " images processed");
        }
    }

    public void logOCR() {

        try{
            PrintWriter writer = new PrintWriter("/tmp/logs", "UTF-8");
            writer.println(new Date() + "\n");

            for(int i = 0; i < 10; i++)
                writer.print("\t" + i);

            writer.println("\n----------------------------------------------------------------------------------");

            int count = (int) Math.sqrt(references.size());
            int confusion[][] = buildConfuseMatrix();
            displayConfuseMatrix(confusion);

            for(int i = 0; i < count; i++) {

                writer.print(("" + i) + "|");

                for (int j = 0; j < count; j++) {
                    writer.print("\t" + confusion[i][j]);
                }

                writer.println();
            }

            writer.println("----------------------------------------------------------------------------------\n");



            writer.println("Success rate : " + getSuccessRate(confusion) + "%");

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayConfuseMatrix(int[][] confusion) {
        ResultsTable resultsTable = new ResultsTable();
        resultsTable.showRowNumbers(false);

        for(int i = 0; i < confusion.length; i++) {
            resultsTable.incrementCounter();
            resultsTable.addValue("label", i);

            for(int j = 0; j < confusion[i].length; j++) {
                resultsTable.addValue(Integer.toString(j), confusion[i][j]);
            }

            resultsTable.addValue("success", "" + (int)((double)confusion[i][i] / confusion.length * 100) + "%");
        }

        resultsTable.incrementCounter();

        resultsTable.addValue("label", "");
        for(int i = 0; i < confusion.length-1; i++)
            resultsTable.addValue(Integer.toString(i), "");

        resultsTable.addValue(Integer.toString(confusion.length-1), "Moy:");
        resultsTable.addValue("success", Integer.toString(getSuccessRate(confusion)) + "%");
        resultsTable.show("Confuse Matrix");
    }

    private int getSuccessRate(int [][] confusion) {
        double sum = 0;

        for(int i = 0; i < confusion.length; i++) {
            sum += confusion[i][i];
        }

        return (int)(sum / references.size() * 100);
    }
}

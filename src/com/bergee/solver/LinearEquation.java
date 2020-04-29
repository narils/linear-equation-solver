package com.bergee.solver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class LinearEquation {
    private final String inputFile, outputFile;
    private boolean verbose;
    private Matrix matrix;

    public LinearEquation(String inputFile, String outputFile, boolean verbose) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.verbose = verbose;

        initialize();
    }

    private void initialize() {
        File file = new File(inputFile);

        try (Scanner sc = new Scanner(file)) {
            int size = sc.nextInt();
            String remainder = sc.nextLine().replaceAll("(^\\s+)|(\\s+$)", "");
            int equations = remainder.equals("") ? size : Integer.parseInt(remainder);
            matrix = new Matrix(size, equations, verbose);

            for (int equationIdx = 0; equationIdx < equations; equationIdx++) {
                String[] numberList = new String[size + 1];
                for (int numberIdx = 0; numberIdx < size + 1; numberIdx++) {
                    numberList[numberIdx] = sc.next();
                }
                matrix.addRow(numberList);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void solve() {
        System.out.println(matrix + "\n\n");
        matrix.solve(verbose);
        writeToFile();
    }

    private void writeToFile() {
        try (FileWriter writer = new FileWriter(outputFile)) {
            String result = matrix.getResultString();
            System.out.println();
            System.out.println(result);
            writer.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


package com.bergee.solver;

public class Main {
    public static void main(String[] args) {
        String inputFile = null;
        String outputFile = null;
        boolean verbose = false;

        for (int i = 0; i < args.length; i++) {
            String currentArg = args[i];

            if (currentArg.equals("-in") && args.length - 1 > i) {
                inputFile = args[++i];
            } else if (currentArg.equals("-out") && args.length - 1 > i) {
                outputFile = args[++i];
            } else if (currentArg.equals("-v")) {
                verbose = true;
            }
        }

        if (inputFile == null) {
            System.out.println("Missing input file -in parameter");
        }
        if (outputFile == null) {
            System.out.println("Missing output file -out parameter");
        }

        LinearEquation equation = new LinearEquation(inputFile, outputFile, verbose);
        equation.solve();
    }
}
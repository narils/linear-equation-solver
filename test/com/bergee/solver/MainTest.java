package com.bergee.solver;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class MainTest {
    static final String NO_SOLUTION = "No solutions";
    static final String testFolder = "test/com/bergee/solver/testfiles/";
    static final String inputFile = testFolder + "input.txt";
    static final String outputFile = testFolder + "output.txt";

    @BeforeEach
    void setUp() {
        File input = new File(inputFile);
        if (input.exists() && !input.delete()) {
            System.out.println("Failed at deleting input file");
        }
    }

    @AfterEach
    void tearDown() {
        setUp();
        File output = new File(outputFile);
        if (output.exists() && !output.delete()) {
            System.out.println("Failed at deleting output file");
        }
    }

    void copyToTest(String filePath) throws IOException {
        Files.copy(Paths.get(testFolder + filePath), Paths.get(inputFile));
    }

    void callMain() {
        String[] args = {"-in", inputFile, "-out", outputFile};
        Main.main(args);
    }

    List<Complex> parseOutputToComplex() throws IOException {
        return Files.lines(Paths.get(outputFile))
                .map(Complex::parseComplexNumber)
                .collect(Collectors.toList());
    }

    void e2eTest(String testFile) throws IOException {
        copyToTest(testFile);
        callMain();
    }

    @Test
    void test01() throws IOException {
        e2eTest("in01.txt");
        Complex[] solution = {new Complex(1, 0), new Complex(2, 0), new Complex(3, 0)};
        List<Complex> answer = parseOutputToComplex();
        Assertions.assertArrayEquals(solution, answer.toArray());
    }

    @Test
    void test02() throws IOException {
        e2eTest("in02.txt");
        byte[] content = Files.readAllBytes(Paths.get(outputFile));
        String answer = new String(content);
        Assertions.assertEquals(NO_SOLUTION, answer);
    }

    @Test
    void test03() throws IOException {
        e2eTest("in03.txt");
        Complex[] solution = {
                new Complex(0.54282178),
                new Complex(-2.3922826),
                new Complex(1.57892693),
                new Complex(-1.36789478),
                new Complex(0.64330307),
                new Complex(-1.75310327),
                new Complex(-0.04324128),
                new Complex(-0.75034754),
                new Complex(-0.82448656),
                new Complex(-0.45616658),
                new Complex(-1.21629582),
                new Complex(0.30930226),
                new Complex(-0.11050352),
                new Complex(1.1717445),
                new Complex(-0.58729493),
                new Complex(-1.39331184),
                new Complex(1.1229329),
                new Complex(3.06934541),
                new Complex(1.19948577),
                new Complex(1.53988394)
        };
        List<Complex> answer = parseOutputToComplex();
        Assertions.assertArrayEquals(solution, answer.toArray());
    }

    @Test
    void test04() throws IOException {
        e2eTest("in04.txt");
        Complex[] solution = {
                Complex.parseComplexNumber("-i"),
                Complex.parseComplexNumber("-i")
        };
        List<Complex> answer = parseOutputToComplex();
        Assertions.assertArrayEquals(solution, answer.toArray());
    }

    @Test
    void test05() throws IOException {
        e2eTest("in05.txt");
        Complex[] solution = {
                Complex.parseComplexNumber("-0.08790367+0.1686043i"),
                Complex.parseComplexNumber("-0.07066656-0.08768536i"),
                Complex.parseComplexNumber("0.69869688+0.87260635i"),
        };
        List<Complex> answer = parseOutputToComplex();
        Assertions.assertArrayEquals(solution, answer.toArray());
    }
}
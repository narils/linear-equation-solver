package com.bergee.solver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ComplexTest {

    @Test
    void isImaginary() {
        Complex c1 = new Complex(0, 1);
        Complex c2 = new Complex(1, 0);

        assertTrue(c1.isImaginary());
        assertFalse(c2.isImaginary());
    }

    @Test
    void isReal() {
        Complex c1 = new Complex(1, 0);
        Complex c2 = new Complex(0, 1);

        assertTrue(c1.isReal());
        assertFalse(c2.isReal());
    }

    @Test
    void toRoundedString() {
        Complex c1 = new Complex(
                new Rational(65536, 458),
                new Rational(3, -124));

        String result = "143.09170306-0.02419355i";
        Assertions.assertEquals(result, c1.toRoundedString());
    }

    @Test
    void parseComplexNumber() {
        String stringTestData = "1\n" +
                "-1\n" +
                "1.23\n" +
                "-1.23\n" +
                "1+i\n" +
                "1-i\n" +
                "1.23+i\n" +
                "-1.23-i\n" +
                "1.23+2345i\n" +
                "-1.23-23.45i\n";

        Complex[] solution = {
                new Complex(1),
                new Complex(-1),
                new Complex(1.23),
                new Complex(-1.23),
                new Complex(1, 1),
                new Complex(1, -1),
                new Complex(1.23, 1),
                new Complex(-1.23, -1),
                new Complex(1.23, 2345),
                new Complex(-1.23, -23.45)
        };

        Assertions.assertArrayEquals(solution, Stream
                .of(stringTestData.split("\\n"))
                .map(Complex::parseComplexNumber).toArray());
    }

    @Test // TODO
    void negate() {
    }

    @Test
    void add() {
    }

    @Test
    void subtract() {
    }

    @Test
    void multiply() {
    }

    @Test
    void divide() {
    }
}
package com.bergee.solver;

import java.math.BigInteger;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Complex extends Number {
    public final static Complex ONE = new Complex(1, 0);
    public final static Complex ZERO = new Complex(0, 0);
    private final Rational real, imaginary;

    public Complex(Rational real) {

        this(real, new Rational(0));
    }

    public Complex(Rational real, Rational imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public Complex(int real, int imaginary) {
        this(new Rational(real), new Rational(imaginary));
    }

    public Complex(double real) {
        this(new Rational(real), new Rational(0));
    }

    public Complex(double real, double imaginary) {
        this(new Rational(real), new Rational(imaginary));
    }

    public Complex(String real, String imaginary) {
        this(new Rational(real), new Rational(imaginary));
    }

    static Complex parseComplexNumber(String s) {
        // https://stackoverflow.com/questions/50425322/c-regex-for-reading-complex-numbers
        Pattern pattern = Pattern.compile("^(?=[iI.\\d+-])([+-]?(?:\\d+(?:\\.\\d*)?|\\.\\d+)(?:[eE][+-]?\\d+)?(?![iI.\\d]))?([+-]?(?:(?:\\d+(?:\\.\\d*)?|\\.\\d+)(?:[eE][+-]?\\d+)?)?[iI])?$");
        Matcher matcher = pattern.matcher(s);

        boolean result = matcher.matches();

        String realString = matcher.group(1);
        String imaginaryString = matcher.group(2);
        String real = realString == null ? "0" : realString;
        String imaginary;


        if (imaginaryString == null) {
            imaginary = "0";
        } else if (imaginaryString.equals("+i") || imaginaryString.equals("i")) {
            imaginary = "1";
        } else if (imaginaryString.equals("-i")) {
            imaginary = "-1";
        } else {
            imaginary = imaginaryString.substring(0, imaginaryString.indexOf('i'));
        }

        return new Complex(real, imaginary);
    }

    public Rational getReal() {
        return real;
    }

    public Rational getImaginary() {
        return imaginary;
    }

    public boolean isImaginary() {
        return imaginary.value() != 0.0;
    }

    public boolean isReal() {
        return imaginary.value() == 0.0;
    }

    public Complex negate() {
        return new Complex(real.negate(), imaginary.negate());
    }

    public Complex add(Complex num) {
        return new Complex(real.add(num.getReal()), imaginary.add(num.getImaginary()));
    }

    public Complex subtract(Complex num) {
        return new Complex(real.subtract(num.getReal()), imaginary.subtract(num.getImaginary()));
    }

    public Complex multiply(Complex num) {
        if (isReal() && num.isReal()) {
            return new Complex(num.real.multiply(real));
        }

        // (a + bi) * (c + di) =
        // (ac - bd) + (ad + bc)i

        Rational a = real;
        Rational b = imaginary;
        Rational c = num.getReal();
        Rational d = num.getImaginary();

        Rational newReal = a.multiply(c).subtract(b.multiply(d));
        Rational newImaginary = a.multiply(d).add(b.multiply(c));
        return new Complex(newReal, newImaginary);
    }

    public Complex divide(Complex num) {
        if (isReal() && num.isReal()) {
            return new Complex(real.divide(num.real));
        }

        // a + bi / c + di =
        // real part: (ac + bd) / c^2 + d^2
        // imaginary: i * (bc - ad) / c^2 + d^2

        Rational a = real;
        Rational b = imaginary;

        Rational c = num.getReal();
        Rational d = num.getImaginary();

        Rational denominator = c.multiply(c).add(d.multiply(d));

        Rational newReal = a.multiply(c).add(b.multiply(d)).divide(denominator);
        Rational newImaginary = b.multiply(c).subtract(a.multiply(d)).divide(denominator);
        return new Complex(newReal, newImaginary);
    }

    @Override
    public String toString() {
        String realString = real.value() == 0.0 ? "" : "" + real;
        String imaginaryString;

        boolean preciseImaginary = imaginary.getDenominator().compareTo(BigInteger.valueOf(1)) == 0;

        if (imaginary.value() == -1.0) {
            imaginaryString = "-i";
        } else if (imaginary.value() == 1.0) {
            imaginaryString = "i";
        } else if (preciseImaginary) {
            imaginaryString = "" + imaginary + "i";
        } else {
            imaginaryString = "(" + imaginary + ")i";
        }

        if (imaginary.value() == 0.0) {
            return "" + real;
        } else if (real.value() == 0.0) {
            return imaginaryString;
        } else if (imaginary.value() > 0.0) {
            return realString + "+" + imaginaryString;
        }
        return realString + imaginaryString;
    }

    public String toRoundedString() {
        double realValue = real.value();
        double imaginaryValue = imaginary.value();

        String realString = realValue == 0.0 ? "" : "" + realValue;
        String imaginaryString;


        if (imaginaryValue == -1.0) {
            imaginaryString = "-i";
        } else if (imaginaryValue == 1.0) {
            imaginaryString = "i";
        } else {
            imaginaryString = "" + imaginaryValue + "i";
        }

        if (imaginaryValue == 0.0) {
            return "" + realValue;
        } else if (realValue == 0.0) {
            return imaginaryString;
        } else if (imaginaryValue > 0.0) {
            return realString + "+" + imaginaryString;
        }
        return realString + imaginaryString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complex number = (Complex) o;
        return real.equals(number.getReal()) &&
                imaginary.equals(number.getImaginary());
    }

    @Override
    public int hashCode() {
        return Objects.hash(real, imaginary);
    }

    @Override
    public int intValue() {
        return (int) doubleValue();
    }

    @Override
    public long longValue() {
        return (long) doubleValue();
    }

    @Override
    public float floatValue() {
        return (float) doubleValue();
    }

    @Override
    public double doubleValue() {
        if (isReal()) {
            return real.value();
        }
        return Math.sqrt(real.multiply(real).add(imaginary.multiply(imaginary)).value());
    }
}

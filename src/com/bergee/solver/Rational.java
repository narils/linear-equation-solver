package com.bergee.solver;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Objects;

class Rational {
    private BigInteger numerator, denominator;

    public Rational(int numerator) {
        this(numerator, 1);
    }

    public Rational(int numerator, int denominator) {
        this(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
    }

    public Rational(BigInteger numerator) {
        this(numerator, BigInteger.valueOf(1));
    }

    public Rational(BigInteger numerator, BigInteger denominator) {
        if (denominator.compareTo(BigInteger.valueOf(0)) == 0) {
            throw new IllegalArgumentException("Denominator can't be zero");
        } else if (numerator.compareTo(BigInteger.valueOf(0)) == 0) {
            this.numerator = BigInteger.valueOf(0);
            this.denominator = BigInteger.valueOf(1);
        } else if (denominator.compareTo(BigInteger.valueOf(0)) < 0) {
            this.numerator = numerator.negate();
            this.denominator = denominator.negate();
        } else {
            this.numerator = numerator;
            this.denominator = denominator;
        }

        reduce();
    }

    public Rational(double d) {
        this("" + d);
    }

    public Rational(String s) {
        double d = Double.parseDouble(s);
        int digitsDec = s.length() - 1 - s.indexOf('.');
        BigInteger denom = BigInteger.valueOf(1);
        for (int i = 0; i < digitsDec; i++) {
            d *= 10;
            denom = denom.multiply(BigInteger.valueOf(10));
        }

        this.numerator = BigInteger.valueOf((int) Math.round(d));
        this.denominator = denom;
        reduce();
    }

    public BigInteger getNumerator() {
        return numerator;
    }

    public BigInteger getDenominator() {
        return denominator;
    }

    private void reduce() {
        BigInteger gcd = numerator.gcd(denominator);
        numerator = numerator.divide(gcd);
        denominator = denominator.divide(gcd);
    }

    public Rational add(Rational r) {
        BigInteger n = numerator.multiply(r.getDenominator()).add((r.getNumerator().multiply(denominator)));
        BigInteger d = denominator.multiply(r.getDenominator());
        return new Rational(n, d);
    }

    public Rational subtract(Rational r) {
        BigInteger n = numerator.multiply(r.getDenominator()).subtract(r.getNumerator().multiply(denominator));
        BigInteger d = denominator.multiply(r.getDenominator());
        return new Rational(n, d);
    }

    public Rational multiply(Rational r) {
        BigInteger n = numerator.multiply(r.getNumerator());
        BigInteger d = denominator.multiply(r.getDenominator());
        return new Rational(n, d);
    }

    public Rational divide(Rational r) {
        BigInteger n = numerator.multiply(r.getDenominator());
        BigInteger d = denominator.multiply(r.getNumerator());
        return new Rational(n, d);
    }

    public Rational negate() {
        return new Rational(numerator.negate(), denominator);
    }

    public double value() {
        return getBigDecimalValue().doubleValue();
    }

    public BigDecimal getBigDecimalValue() {
        BigDecimal num = new BigDecimal(numerator);
        BigDecimal den = new BigDecimal(denominator);

        return num.divide(den, 8, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        if (denominator.compareTo(BigInteger.valueOf(1)) == 0) {
            return "" + numerator;
        }
        return "" + numerator + "/" + denominator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rational fraction = (Rational) o;
        return numerator.equals(fraction.numerator) &&
                denominator.equals(fraction.denominator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numerator, denominator);
    }

}

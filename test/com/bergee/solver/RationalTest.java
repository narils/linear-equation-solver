package com.bergee.solver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RationalTest {

    @Test
    void add() {
        Rational r1 = new Rational("0.125");
        Rational r2 = new Rational("0.875");
        Assertions.assertEquals(1.0, r1.add(r2).value());

        r1 = new Rational(-3);
        r2 = new Rational(-3, -2);
        assertEquals(-1.5, r1.add(r2).value());
    }

    @Test
    void subtract() {
        Rational r1 = new Rational("0.125");
        Rational r2 = new Rational(1);
        Assertions.assertEquals(-0.875, r1.subtract(r2).value());

        r1 = new Rational(-3);
        r2 = new Rational(-3, -2);
        assertEquals(-4.5, r1.subtract(r2).value());
    }

    @Test
    void multiply() {
        Rational r1 = new Rational(8.2);
        Rational r2;
        Rational ans = new Rational(1681, 25);

        r1 = r1.multiply(r1);
        Assertions.assertEquals(67.24, r1.value());
        Assertions.assertEquals(ans, r1);

        r1 = new Rational(new BigInteger("4294967296"));
        r2 = new Rational(new BigInteger("18446744073709551616"));
        ans = new Rational(new BigInteger("79228162514264337593543950336"));

        r1 = r1.multiply(r2);
        Assertions.assertEquals(ans, r1);
    }

    @Test
    void divide() {
        Rational r1 = new Rational("14.876449");
        Rational r2 = new Rational("5.72561");
        Rational ans = new Rational(14876449, 5725610);

        r1 = r1.divide(r2);
        BigDecimal bd = new BigDecimal("2.598229533621745106634926234933919704625358695405380387417");
        bd = bd.setScale(8, RoundingMode.HALF_UP);

        Assertions.assertEquals(bd, r1.getBigDecimalValue());
        Assertions.assertEquals(ans, r1);

        r1 = new Rational(new BigInteger("4294967296"));
        r2 = new Rational(new BigInteger("18446744073709551616"));
        ans = new Rational(BigInteger.valueOf(1), new BigInteger("4294967296"));

        r1 = r1.divide(r2);
        Assertions.assertEquals(ans, r1);
    }

    @Test
    void negate() {
        Rational r1 = new Rational(1337);
        r1 = r1.negate();

        Assertions.assertEquals(-1337.0, r1.value());
        Assertions.assertEquals(1337.0, r1.negate().value());
    }
}
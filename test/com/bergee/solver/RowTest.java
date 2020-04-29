package com.bergee.solver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RowTest {

    @Test
    void isZeroInPosition() {
        String input = "1 0 1";
        Row r = new Row(input.split(" "));
        Assertions.assertFalse(r.isZeroInPosition(0));
        Assertions.assertTrue(r.isZeroInPosition(1));
        Assertions.assertFalse(r.isZeroInPosition(2));
    }

    @Test
    void add() {
        String row1Input = "1 2 3";
        String row2Input = "1 1 1";
        String answerString = "3 4 5";
        Row row1 = new Row(row1Input.split(" "));
        Row row2 = new Row(row2Input.split(" "));
        Complex factor = new Complex(2, 0);
        Row answerRow = new Row(answerString.split(" "));

        row1.add(row2, factor);
        Assertions.assertEquals(answerRow, row1);

        row1Input = "2 4 8 15";
        answerString = "4 8 16 30";
        Complex factorOne = factor.divide(factor);

        row1 = new Row(row1Input.split(" "));
        row2 = new Row(row1Input.split(" "));
        answerRow = new Row(answerString.split(" "));

        row1.add(row2, factorOne);
        Assertions.assertEquals(answerRow, row1);

    }

    @Test
    void divide() {
        String row1Input = "4 16 64 256";
        String answerString = "2 8 32 128";
        Complex factor = new Complex(2);

        Row row1 = new Row(row1Input.split(" "));
        Row answerRow = new Row(answerString.split(" "));

        row1.divide(factor);
        Assertions.assertEquals(answerRow, row1);
    }
}
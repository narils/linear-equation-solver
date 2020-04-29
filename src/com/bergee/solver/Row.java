package com.bergee.solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Row {
    List<Complex> numbers;
    private String name;

    public Row(String[] rowArray, int rowNumber) {
        setRowNumber(rowNumber);
        numbers = Arrays.stream(rowArray)
                .map(Complex::parseComplexNumber)
                .collect(Collectors.toList());
    }

    public Row(String[] rowArray) {
        this(rowArray, 1);
    }

    public String getName() {
        return name;
    }

    public void setRowNumber(int rowNumber) {
        name = "R" + rowNumber;
    }

    public boolean isZeroInPosition(int i) {
        Complex num = numbers.get(i);
        return num.equals(Complex.ZERO);
    }

    void add(Row r, Complex coefficient) {
        add(r, coefficient, false);
    }

    void add(Row r, Complex coefficient, boolean verbose) {
        if (verbose) {
            System.out.println("" + coefficient + " * " + r.getName() + " + " + name + " -> " + name);
        }
        for (int i = 0; i < numbers.size(); i++) {
            Complex myValue = numbers.get(i);
            Complex otherValue = r.numbers.get(i);


            otherValue = otherValue.multiply(coefficient);
            myValue = myValue.add(otherValue);

            numbers.set(i, myValue);
        }
    }

    void subtract(Row r, Complex coefficient, boolean verbose) {
        add(r, coefficient.negate(), verbose);
    }

    void divide(Complex number) {
        divide(number, false);
    }

    void divide(Complex number, boolean verbose) {
        if (verbose) {
            System.out.println(name + " / " + number + " -> " + name);
        }

        for (int i = 0; i < numbers.size(); i++) {
            Complex n = numbers.get(i);
            n = n.divide(number);
            numbers.set(i, n);
        }
    }

    @Override
    public String toString() {
        return numbers.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Row row = (Row) o;
        return name.equals(row.name) &&
                numbers.equals(row.numbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, numbers);
    }
}


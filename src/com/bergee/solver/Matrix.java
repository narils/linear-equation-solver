package com.bergee.solver;

import java.util.ArrayList;

class Matrix {
    private final int size, equations;
    ArrayList<Row> rows;

    Matrix(int size, int equations, boolean verbose) {
        this.size = size;
        this.equations = equations;
        rows = new ArrayList<>(size);
    }

    void addRow(String[] rowArray) {
        if (rows.size() >= equations) {
            throw new IllegalArgumentException("Matrix already at proper size");
        }
        Row newRow = new Row(rowArray, rows.size() + 1);
        rows.add(newRow);
    }

    @Override
    public String toString() {
        return rows.toString();
    }

    private void swapRows(int idxA, int idxB) {
        Row newA = rows.get(idxB);
        Row newB = rows.get(idxA);
        newA.setRowNumber(idxA);
        newB.setRowNumber(idxB);
        rows.set(idxA, newA);
        rows.set(idxB, newB);
    }

    /**
     * @return number significant equations. Returns -1 if matrix contains inconsistent equations. (no solutions)
     */
    private int getSignificantEquations() {
        int significantEquations = 0;
        outer:
        for (Row row : rows) {
            for (int idx = 0; idx < size; idx++) {
                if (!row.isZeroInPosition(idx)) {
                    significantEquations++;
                    continue outer;
                }
            }
            // No solution found
            if (!row.isZeroInPosition(row.numbers.size() - 1)) {
                return -1;
            }
        }
        return significantEquations;
    }

    public String getResultString() {
        int significantEquations = getSignificantEquations();
        if (significantEquations == -1 || significantEquations > size) {
            return "No solutions";
        } else if (significantEquations < size) {
            return "Infinitely many solutions";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            Complex result = rows.get(i).numbers.get(size);
            sb.append(result.toRoundedString()).append("\n");
        }
        return sb.toString();
    }

    void solve(boolean verbose) {
        prepareRowEchelon(verbose);
        rowEchelonForm(verbose);
        reduceRowEchelonForm(verbose);
    }

    /**
     * Make sure that every row has a non-zero entry in pivot positions
     */
    private void prepareRowEchelon(boolean verbose) {
        outer:
        for (int rowIdx = 0; rowIdx < size; rowIdx++) {
            if (rowIdx >= equations) break;
            Row currentRow = rows.get(rowIdx);

            if (currentRow.isZeroInPosition(rowIdx)) {
                for (int nextRowIdx = 0; nextRowIdx < equations; nextRowIdx++) {
                    if (rowIdx == nextRowIdx) continue;

                    Row nextRow = rows.get(nextRowIdx);

                    if (nextRowIdx == size - 1 || !currentRow.isZeroInPosition(nextRowIdx) && !nextRow.isZeroInPosition(rowIdx)) {
                        swapRows(rowIdx, nextRowIdx);
                        continue outer;
                    }
                }
            }
        }
    }

    /**
     * Make leading 1 in each pivot position.
     * Subtract lower rows with this new value to create a Matrix in Row Echelon Form
     */
    private void rowEchelonForm(boolean verbose) {
        for (int rowIdx = 0; rowIdx < size; rowIdx++) {
            if (rowIdx >= equations) break;
            Row currentRow = rows.get(rowIdx);

            Complex pivotNumber = currentRow.numbers.get(rowIdx);
            if (pivotNumber.equals(Complex.ZERO)) continue;
            else if (!pivotNumber.equals(Complex.ONE)) {
                currentRow.divide(pivotNumber, verbose);
            }

            for (int nextRowIdx = rowIdx + 1; nextRowIdx < equations; nextRowIdx++) {
                Row nextRow = rows.get(nextRowIdx);
                Complex coefficient = nextRow.numbers.get(rowIdx);

                if (!coefficient.equals(Complex.ZERO)) {
                    nextRow.subtract(currentRow, coefficient, verbose);
                }
            }
        }
    }

    /**
     * Backtrack from Echelon form to find a general solution
     */
    private void reduceRowEchelonForm(boolean verbose) {
        for (int rowIdx = size - 1; rowIdx >= 0; rowIdx--) {
            if (rowIdx >= equations) break;
            Row currentRow = rows.get(rowIdx);

            for (int nextRowIdx = rowIdx - 1; nextRowIdx >= 0; nextRowIdx--) {
                Row nextRow = rows.get(nextRowIdx);
                Complex coefficient = nextRow.numbers.get(rowIdx);

                if (!coefficient.equals(Complex.ZERO)) {
                    nextRow.subtract(currentRow, coefficient, verbose);
                }
            }
        }
    }

}

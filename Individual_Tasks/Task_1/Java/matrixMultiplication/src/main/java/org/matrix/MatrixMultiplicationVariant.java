package org.matrix;

public class MatrixMultiplicationVariant {
    public double[][] execute(double[][] a, double[][] b) {
        int aRows = a.length;
        int aColumns = a[0].length;
        int bRows = b.length;
        int bColumns = b[0].length;
        if (aColumns != bRows) {
            throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
        }
        double[][] result = new double[aRows][bColumns];
        // Initialize the result matrix with 0s
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bColumns; j++) {
                result[i][j] = 0.0;
            }
        }
        // Multiply the two matrices
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bColumns; j++) {
                for (int k = 0; k < aColumns; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }
}

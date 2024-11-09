package org.bd;

public class MatrixMultiplicationBlocked implements MatrixMultiplication {

    private static final int BLOCK_SIZE = 2;

    @Override
    public double[][] matrixMultiplication(double[][] matrixA, double[][] matrixB) {
        int n = matrixA.length;
        double[][] result = new double[n][n];

        for (int i = 0; i < n; i += BLOCK_SIZE) {
            for (int j = 0; j < n; j += BLOCK_SIZE) {
                for (int k = 0; k < n; k += BLOCK_SIZE) {
                    multiplyBlock(matrixA, matrixB, result, i, j, k);
                }
            }
        }
        return result;
    }

    private void multiplyBlock(double[][] matrixA, double[][] matrixB, double[][] result, int row, int col, int common) {
        int n = matrixA.length;

        for (int i = row; i < Math.min(row + BLOCK_SIZE, n); i++) {
            for (int j = col; j < Math.min(col + BLOCK_SIZE, n); j++) {
                double sum = 0;
                for (int k = common; k < Math.min(common + BLOCK_SIZE, n); k++) {
                    sum += matrixA[i][k] * matrixB[k][j];
                }
                result[i][j] += sum;
            }
        }
    }
}

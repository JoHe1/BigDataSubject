package org.bd;

import java.util.HashMap;
import java.util.Map;

public class MatrixMultiplicationSparsing implements MatrixMultiplication {

    @Override
    public double[][] matrixMultiplication(double[][] matrixA, double[][] matrixB) {
        int n = matrixA.length;
        double[][] result = new double[n][n];

        // Convert matrices to sparse format with integer-based keys
        Map<Integer, Map<Integer, Double>> sparseA = toSparse(matrixA);
        Map<Integer, Map<Integer, Double>> sparseB = toSparse(matrixB);

        // Multiply only non-zero elements
        for (Map.Entry<Integer, Map<Integer, Double>> rowEntryA : sparseA.entrySet()) {
            int rowA = rowEntryA.getKey();
            Map<Integer, Double> rowValuesA = rowEntryA.getValue();

            for (Map.Entry<Integer, Double> entryA : rowValuesA.entrySet()) {
                int colA = entryA.getKey();
                double valueA = entryA.getValue();

                // Only process if colA exists in sparseB as a row
                if (sparseB.containsKey(colA)) {
                    Map<Integer, Double> rowValuesB = sparseB.get(colA);
                    for (Map.Entry<Integer, Double> entryB : rowValuesB.entrySet()) {
                        int colB = entryB.getKey();
                        double valueB = entryB.getValue();
                        result[rowA][colB] += valueA * valueB;
                    }
                }
            }
        }
        return result;
    }

    // Helper method to convert a matrix to sparse representation with integer-based keys
    private Map<Integer, Map<Integer, Double>> toSparse(double[][] matrix) {
        Map<Integer, Map<Integer, Double>> sparseMatrix = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            Map<Integer, Double> rowMap = new HashMap<>();
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] != 0) {
                    rowMap.put(j, matrix[i][j]);
                }
            }
            if (!rowMap.isEmpty()) {
                sparseMatrix.put(i, rowMap);
            }
        }
        return sparseMatrix;
    }
}

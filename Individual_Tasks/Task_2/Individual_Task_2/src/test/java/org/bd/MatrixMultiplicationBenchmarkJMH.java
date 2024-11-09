package org.bd;

import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
@Fork(1)
@State(Scope.Thread)
public class MatrixMultiplicationBenchmarkJMH {

    private static final int MATRIX_SIZE = 1000;
    private static final double ZERO_PROBABILITY = 0.60;
    private static final double[][] matrixA = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE, ZERO_PROBABILITY);
    private static final double[][] matrixB = generateRandomMatrix(MATRIX_SIZE, MATRIX_SIZE, ZERO_PROBABILITY);

    private static final MatrixMultiplication baseMultiplication = new MatrixMultiplicationBase();
    private static final MatrixMultiplication sparseMultiplication = new MatrixMultiplicationSparsing();
    private static final MatrixMultiplication blockedMultiplication = new MatrixMultiplicationBlocked();

    private long beforeUsedMemory;
    private long startTime;

    @Setup(Level.Trial)
    public void setupTotalMeasurement() {
        System.gc();
        beforeUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        startTime = System.nanoTime();
    }

    @TearDown(Level.Trial)
    public void tearDownTotalMeasurement() {
        long endTime = System.nanoTime();
        long afterUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        long totalTime = endTime - startTime;
        long memoryUsed = afterUsedMemory - beforeUsedMemory;

        System.out.println("Total time for all iterations: " + totalTime / 1_000_000.0 + " ms");
        System.out.println("Total memory used: " + memoryUsed + " bytes");
    }

    @Benchmark
    public void testBaseMultiplication() {
        baseMultiplication.matrixMultiplication(matrixA, matrixB);
    }

    @Benchmark
    public void testSparseMultiplication() {
        sparseMultiplication.matrixMultiplication(matrixA, matrixB);
    }

    @Benchmark
    public void testBlockedMultiplication() {
        blockedMultiplication.matrixMultiplication(matrixA, matrixB);
    }


    private static double[][] generateRandomMatrix(int rows, int cols, double zeroProbability) {
        double[][] matrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (Math.random() < zeroProbability) {
                    matrix[i][j] = 0.0;
                } else {
                    matrix[i][j] = Math.random();
                }
            }
        }
        return matrix;
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}

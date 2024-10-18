package org.matrix;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime) // Measure the average time taken for the operation
@OutputTimeUnit(TimeUnit.NANOSECONDS) // Output the time in milliseconds
@State(Scope.Thread) // Define the state shared between benchmark invocations
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS) // Warm-up phase with 3 iterations
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS) // Measure 3 iterations for the actual benchmark

public class MatrixMultiplicationBenchmarking {
    @Param({"3", "10", "100"}) // Define matrix sizes for benchmarking
    public int matrixSize;

    private double[][] matrixA;
    private double[][] matrixB;
    private MatrixMultiplicationVariant matrixMultiplicationVariant;

    @Setup // Prepare the data before benchmarking
    public void setup() {
        matrixA = generateRandomMatrix(matrixSize);
        matrixB = generateRandomMatrix(matrixSize);
        System.out.println("Benchmarking matrix multiplication for matrices of size " + matrixSize + "x" + matrixSize);
        matrixMultiplicationVariant = new MatrixMultiplicationVariant();
    }

    @Benchmark // Benchmark the matrix multiplication operation using the generated matrices
    public double[][] benchmarkMatrixMultiplication() {
        return matrixMultiplicationVariant.execute(matrixA, matrixB);
    }

    // Generate a random matrix of the specified size
    private double[][] generateRandomMatrix(int size) {
        double[][] matrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = Math.random() * 100;
            }
        }
        return matrix;
    }
}

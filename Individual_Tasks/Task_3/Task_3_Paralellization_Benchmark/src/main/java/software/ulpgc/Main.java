package software.ulpgc;

import java.util.Random;

public class Main {
    static int n = 2048; // Matrix size
    static double[][] matrixA;
    static double[][] matrixB;

    public static void main(String[] args) {
        // Generate random matrices
        matrixA = generateRandomMatrix(n, n);
        matrixB = generateRandomMatrix(n, n);

        // Sequential execution (Base)
        System.out.println("\n--- Base (Sequential) ---");
        MatrixMultiplicationBase base = new MatrixMultiplicationBase(matrixA, matrixB);
        long baseTime = base.execute();

        // Parallel execution
        System.out.println("\n--- Parallel (Multi-threaded) ---");
        MatrixMultiplicationParallel parallel = new MatrixMultiplicationParallel(matrixA, matrixB, 8);
        long parallelTime = parallel.execute();
        double speedupParallel = (double) baseTime / parallelTime;
        System.out.printf("Speedup (Parallel): %.2f\n", speedupParallel);
        System.out.printf("Efficiency (Parallel): %.2f\n", speedupParallel / 8);

        // Vectorized execution
        System.out.println("\n--- Vectorized ---");
        MatrixMultiplicationVectorized vectorized = new MatrixMultiplicationVectorized(matrixA, matrixB);
        long vectorizedTime = vectorized.execute();
        double speedupVectorized = (double) baseTime / vectorizedTime;
        System.out.printf("Speedup (Vectorized): %.2f\n", speedupVectorized);
    }

    public static double[][] generateRandomMatrix(int rows, int cols) {
        Random random = new Random();
        double[][] matrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextDouble();
            }
        }
        return matrix;
    }
}

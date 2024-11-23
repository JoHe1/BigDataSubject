package software.ulpgc;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class MatrixMultiplicationVectorized {
    private final RealMatrix a;
    private final RealMatrix b;

    public MatrixMultiplicationVectorized(double[][] a, double[][] b) {
        this.a = MatrixUtils.createRealMatrix(a);
        this.b = MatrixUtils.createRealMatrix(b);
    }

    public long execute() {
        Runtime runtime = Runtime.getRuntime();
        System.gc(); // Clean up memory
        long initialMemory = runtime.totalMemory() - runtime.freeMemory();

        long start = System.currentTimeMillis();
        RealMatrix result = a.multiply(b); // Vectorized operation
        long stop = System.currentTimeMillis();

        long finalMemory = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsedBytes = finalMemory - initialMemory;
        System.out.printf("Memory used (MB): %.6f\n", memoryUsedBytes / (1024.0 * 1024.0));
        System.out.printf("Execution time (s): %.3f\n", (stop - start) * 1e-3);

        return stop - start;
    }
}

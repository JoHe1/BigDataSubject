package software.ulpgc;

public class MatrixMultiplicationParallel {
    private final double[][] a;
    private final double[][] b;
    private final double[][] c;
    private final int numThreads;

    public MatrixMultiplicationParallel(double[][] a, double[][] b, int numThreads) {
        this.a = a;
        this.b = b;
        this.c = new double[a.length][b[0].length];
        this.numThreads = numThreads;
    }

    public long execute() {
        Runtime runtime = Runtime.getRuntime();
        System.gc(); // Clean up memory
        long initialMemory = runtime.totalMemory() - runtime.freeMemory();

        Thread[] threads = new Thread[numThreads];
        int rowsPerThread = a.length / numThreads;

        long start = System.currentTimeMillis();
        for (int t = 0; t < numThreads; t++) {
            final int startRow = t * rowsPerThread;
            final int endRow = (t == numThreads - 1) ? a.length : startRow + rowsPerThread;
            threads[t] = new Thread(() -> {
                for (int i = startRow; i < endRow; i++) {
                    for (int j = 0; j < b[0].length; j++) {
                        for (int k = 0; k < b.length; k++) {
                            c[i][j] += a[i][k] * b[k][j];
                        }
                    }
                }
            });
            threads[t].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long stop = System.currentTimeMillis();

        long finalMemory = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsedBytes = finalMemory - initialMemory;
        System.out.printf("Memory used (MB): %.6f\n", memoryUsedBytes / (1024.0 * 1024.0));
        System.out.printf("Execution time (s): %.3f\n", (stop - start) * 1e-3);

        return stop - start;
    }
}

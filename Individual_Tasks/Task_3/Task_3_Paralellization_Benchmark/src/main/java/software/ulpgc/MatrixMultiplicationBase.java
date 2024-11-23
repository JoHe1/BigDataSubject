package software.ulpgc;

public class MatrixMultiplicationBase {
	private final double[][] a;
	private final double[][] b;
	private final double[][] c;

	public MatrixMultiplicationBase(double[][] a, double[][] b) {
		this.a = a;
		this.b = b;
		this.c = new double[a.length][b[0].length];
	}

	public long execute() {
		Runtime runtime = Runtime.getRuntime();
		System.gc(); // Clean up memory
		long initialMemory = runtime.totalMemory() - runtime.freeMemory();

		long start = System.currentTimeMillis();
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < b[0].length; j++) {
				for (int k = 0; k < b.length; k++) {
					c[i][j] += a[i][k] * b[k][j];
				}
			}
		}
		long stop = System.currentTimeMillis();

		long finalMemory = runtime.totalMemory() - runtime.freeMemory();
		long memoryUsedBytes = finalMemory - initialMemory;
		System.out.printf("Memory used (MB): %.6f\n", memoryUsedBytes / (1024.0 * 1024.0));
		System.out.printf("Execution time (s): %.3f\n", (stop - start) * 1e-3);

		return stop - start; // Returns time in milliseconds
	}
}

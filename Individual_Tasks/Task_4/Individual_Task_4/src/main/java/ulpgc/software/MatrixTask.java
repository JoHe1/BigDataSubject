package ulpgc.software;

import java.io.Serializable;
import java.util.concurrent.Callable;

public class MatrixTask implements Callable<int[]>, Serializable {
    private final int[] rowA;
    private final int[][] matrixB;

    public MatrixTask(int[] rowA, int[][] matrixB) {
        this.rowA = rowA;
        this.matrixB = matrixB;
    }

    @Override
    public int[] call() {
        int colsB = matrixB[0].length;
        int[] resultRow = new int[colsB];

        // Calculate the partial product
        for (int j = 0; j < colsB; j++) {
            for (int k = 0; k < rowA.length; k++) {
                resultRow[j] += rowA[k] * matrixB[k][j];
            }
        }

        // Print the partial result on the auxiliary node
        System.out.printf("Node %s processed row with result: %s%n",
                Thread.currentThread().getName(), java.util.Arrays.toString(resultRow));
        return resultRow;
    }
}

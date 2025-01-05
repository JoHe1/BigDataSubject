package ulpgc.software;

import com.hazelcast.cluster.Member;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Future;

public class DistributedMatrixMultiplication {

    public static void main(String[] args) throws Exception {
        // Start Hazelcast
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance();

        // Wait for all auxiliary nodes to be ready
        System.out.println("Waiting for auxiliary nodes...");
        waitForCluster(hazelcastInstance, 3); // Waiting for 3 nodes (including the main one)

        // Generate 4x4 matrices for testing
        int[][] matrixA = generateMatrix(20);
        int[][] matrixB = generateMatrix(20);

        // Print the generated matrices
        System.out.println("Matrix A:");
        printMatrix(matrixA);
        System.out.println("Matrix B:");
        printMatrix(matrixB);

        // Get all cluster members, excluding the main node
        Set<Member> members = hazelcastInstance.getCluster().getMembers();
        List<Member> memberList = new ArrayList<>(members);
        memberList.remove(hazelcastInstance.getCluster().getLocalMember());

        // Use a distributed execution service
        IExecutorService executorService = hazelcastInstance.getExecutorService("matrixExecutor");

        // List to collect task results
        List<Future<int[]>> futures = new ArrayList<>();

        // Assign rows of matrix A to the cluster nodes
        for (int i = 0; i < matrixA.length; i++) {
            Member targetMember = memberList.get(i % memberList.size()); // Distribute evenly
            System.out.printf("Assigning row %d to node: %s%n", i, targetMember);
            Future<int[]> future = executorService.submitToMember(
                    new MatrixTask(matrixA[i], matrixB), targetMember);
            futures.add(future);
        }

        // Combine results into the final matrix
        int[][] resultMatrix = new int[matrixA.length][matrixB[0].length];
        for (int i = 0; i < futures.size(); i++) {
            resultMatrix[i] = futures.get(i).get();
        }

        // Print the resulting matrix
        System.out.println("Result Matrix:");
        printMatrix(resultMatrix);

        // Shutdown Hazelcast
        hazelcastInstance.shutdown();
    }

    /**
     * Generates a square matrix with integers between 0 and 10.
     * @param size Size of the matrix (number of rows and columns).
     * @return Generated square matrix.
     */
    private static int[][] generateMatrix(int size) {
        Random random = new Random();
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                matrix[i][j] = random.nextInt(11); // Numbers between 0 and 10
            }
        }
        return matrix;
    }

    /**
     * Prints a matrix to the console.
     * @param matrix Matrix to be printed.
     */
    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    /**
     * Waits until the number of nodes in the cluster matches the expected count.
     */
    private static void waitForCluster(HazelcastInstance hazelcastInstance, int expectedMembers) throws InterruptedException {
        while (hazelcastInstance.getCluster().getMembers().size() < expectedMembers) {
            Thread.sleep(1000); // Wait 1 second before checking again
        }
        System.out.println("All auxiliary nodes are ready.");
    }
}

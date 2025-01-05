# **Distributed Matrix Multiplication Using Hazelcast**

## **University Details**
- **University Name**: University of Las Palmas de Gran Canaria
- **Course**: 3rd Year - Grade in Engineer and Data Science
- **Instructor**: Jose Juan Hernandez Cabrera
- **Semester**: 1st Semester
- **Date Submitted**: 5th January 2025

---

## **Table of Contents**
1. [Introduction](#introduction)
2. [Objective](#objective)
3. [System Architecture](#system-architecture)
4. [Code Explanation](#code-explanation)
5. [Results](#results)
6. [Prerequisites](#prerequisites)
7. [How to Run](#how-to-run)
8. [References](#references)

---

## **Introduction**
This project demonstrates distributed matrix multiplication using **Hazelcast** for workload distribution across multiple nodes. It is designed to showcase parallelism, efficient task allocation, and the simplicity of implementing distributed systems in Java.

---

## **Objective**
The primary goals of this project are:
1. Perform matrix multiplication in a distributed environment.
2. Distribute computation across nodes to optimize performance.
3. Learn the basics of Hazelcast and its capabilities in distributed systems.

---

## **System Architecture**
### **Orchestrator Node**
- Responsible for:
   - Generating matrices for multiplication.
   - Assigning matrix rows to auxiliary nodes for computation.
   - Aggregating the computed results into the final matrix.

### **Auxiliary Nodes**
- Compute specific rows of the resulting matrix.
- Return the computed results to the orchestrator node.

---

## **Code Explanation**
### **Main Program (`DistributedMatrixMultiplication`)**
- **Matrix Generation**:
   - Generates two square matrices of size `NxN` with random integers between 0 and 10 using the `generateMatrix` function.
- **Task Distribution**:
   - Distributes rows of Matrix A to auxiliary nodes for computation.
- **Result Aggregation**:
   - Combines results from all nodes to produce the final matrix.
- **Key Functions**:
   1. `generateMatrix(int size)` - Generates a random square matrix.
   2. `printMatrix(int[][] matrix)` - Prints a matrix to the console.
   3. `waitForCluster(HazelcastInstance instance, int members)` - Waits for auxiliary nodes to connect.

### **Auxiliary Task (`MatrixTask`)**
- **Task Responsibility**:
   - Each auxiliary node computes one row of the result matrix.
- **Logic**:
   - Computes partial results by multiplying the assigned row of Matrix A with all columns of Matrix B.
- **Output**:
   - Prints the partial computation for debugging purposes.

---

## **Results**
The results of the matrix multiplication are displayed on the orchestrator node after all auxiliary nodes have completed their tasks. The final matrix is printed to the console for verification.

---

## **Prerequisites**
1. Java 8 or higher installed.
2. **Docker** and **Docker Compose** installed.
3. Basic knowledge of Hazelcast and distributed systems.

---

## **How to Run**
1. Clone the repository to your local machine.
2. Open a terminal and navigate to the project directory.
3. Run the following command to package the application:
   ```bash
   mvn clean package
   ```
4. Start the Hazelcast cluster using Docker Compose:
   ```bash
   docker-compose build
   docker-compose up
    ```
5. Open Docker Desktop to view the running containers.

---

## **References**
1. [Hazelcast Documentation](https://docs.hazelcast.com/)
2. [Docker Documentation](https://docs.docker.com/)
3. [Docker Desktop](https://www.docker.com/products/docker-desktop)

---

## **Contributors**
- Jorge Lorenzo Lorenzo

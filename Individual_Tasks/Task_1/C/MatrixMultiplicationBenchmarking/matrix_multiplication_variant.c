#include <stdio.h>
#include <stdlib.h>
#include <time.h>


void initialise_matrix(int **matrix, int n) {
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            matrix[i][j] = rand() % 10; 
        }
    }
}


void print_matrix(int **matrix, int n) {
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            printf("%d ", matrix[i][j]);
        }
        printf("\n");
    }
}


void multiply_matrix(int **A, int **B, int **result, int n) {
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            result[i][j] = 0; 
            for (int k = 0; k < n; k++) {
                result[i][j] += A[i][k] * B[k][j];
            }
        }
    }
}


void free_matrix(int **matrix, int n) {
    for (int i = 0; i < n; i++) {
        free(matrix[i]);
    }
    free(matrix);
}

// Principal function
int main(int argc, char *argv[]) {
    if (argc != 2) {
        printf("Uso: %s <tamaño de la matrix>\n", argv[0]);
        return 1;
    }

    int n = atoi(argv[1]); 

    
    int **A = (int **)malloc(n * sizeof(int *));
    int **B = (int **)malloc(n * sizeof(int *));
    int **result = (int **)malloc(n * sizeof(int *));
    for (int i = 0; i < n; i++) {
        A[i] = (int *)malloc(n * sizeof(int));
        B[i] = (int *)malloc(n * sizeof(int));
        result[i] = (int *)malloc(n * sizeof(int));
    }

    srand(time(NULL)); 

    initialise_matrix(A, n); 
    initialise_matrix(B, n); 

    printf("matrix A:\n");
    print_matrix(A, n); 

    printf("\nmatrix B:\n");
    print_matrix(B, n); 

    printf("\nMultiplicando matrices de tamaño %dx%d...\n", n, n);

    multiply_matrix(A, B, result, n);

    printf("\nmatrix result:\n");
    print_matrix(result, n); 

    
    free_matrix(A, n);
    free_matrix(B, n);
    free_matrix(result, n);

    return 0;
}
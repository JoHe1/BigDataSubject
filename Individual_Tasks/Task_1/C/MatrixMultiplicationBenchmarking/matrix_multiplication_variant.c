#include <stdio.h>
#include <stdlib.h>
#include <time.h>


void inicializar_matriz(int **matriz, int n) {
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            matriz[i][j] = rand() % 10; 
        }
    }
}


void imprimir_matriz(int **matriz, int n) {
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            printf("%d ", matriz[i][j]);
        }
        printf("\n");
    }
}


void multiplicar_matrices(int **A, int **B, int **resultado, int n) {
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            resultado[i][j] = 0; 
            for (int k = 0; k < n; k++) {
                resultado[i][j] += A[i][k] * B[k][j];
            }
        }
    }
}


void liberar_matriz(int **matriz, int n) {
    for (int i = 0; i < n; i++) {
        free(matriz[i]);
    }
    free(matriz);
}

// Principal function
int main(int argc, char *argv[]) {
    if (argc != 2) {
        printf("Uso: %s <tamaño de la matriz>\n", argv[0]);
        return 1;
    }

    int n = atoi(argv[1]); 

    
    int **A = (int **)malloc(n * sizeof(int *));
    int **B = (int **)malloc(n * sizeof(int *));
    int **resultado = (int **)malloc(n * sizeof(int *));
    for (int i = 0; i < n; i++) {
        A[i] = (int *)malloc(n * sizeof(int));
        B[i] = (int *)malloc(n * sizeof(int));
        resultado[i] = (int *)malloc(n * sizeof(int));
    }

    srand(time(NULL)); 

    inicializar_matriz(A, n); 
    inicializar_matriz(B, n); 

    printf("Matriz A:\n");
    imprimir_matriz(A, n); 

    printf("\nMatriz B:\n");
    imprimir_matriz(B, n); 

    printf("\nMultiplicando matrices de tamaño %dx%d...\n", n, n);

    multiplicar_matrices(A, B, resultado, n);

    printf("\nMatriz Resultado:\n");
    imprimir_matriz(resultado, n); 

    
    liberar_matriz(A, n);
    liberar_matriz(B, n);
    liberar_matriz(resultado, n);

    return 0;
}
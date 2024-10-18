import sys
import os

sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))) # 

from src.matrix_operations import matrix_multiply_base
import numpy as np
import pytest
from memory_profiler import memory_usage # Library to measure memory usage
import psutil # Library to measure CPU usage



@pytest.mark.parametrize("size", [3, 10, 100]) # Sizes of the matrices to test
def test_matrix_multiply_base(benchmark,size):
    matrixA = np.random.rand(size, size).tolist()
    matrixB = np.random.rand(size, size).tolist()

    cpu_percent_before = psutil.cpu_percent(interval=None)  # First CPU usage measurement

    # Wrapper function to measure memory
    def wrapper():
        return matrix_multiply_base(matrixA, matrixB)
    
    mem_usage = memory_usage(wrapper) # Measure memory usage

    result = benchmark(matrix_multiply_base, matrixA, matrixB) # Run the benchmark on the multiplication function

    cpu_percent_after = psutil.cpu_percent(interval=None)  # Final CPU usage measurement

    print(f"Matrix size: {size}x{size}")
    print(f"Memory used: {max(mem_usage):.2f} MiB")
    print(f"CPU value initial: {cpu_percent_before}%, after: {cpu_percent_after}%")

# The option -s is to print the output of the print statements
# pytest .\benchmarking\test_benchmarking_base.py --benchmark-only -s
# Travelling Salesman Problem

## Solution created for Efficient Algorithms Programming college class.

### Algorithms implemented
- Exact algorithms
  - Branch and Bound (Least cost)
  - Brute Force
- Approximation algorithm
  - Simulated Annealing
    - parameters: starting and final temperature, temperature reduction coefficient, stop condition (time in seconds)
- Metaheuristic algorithm:
  - Genetic algorithm
    - parameters: population size, crossover and mutation coefficient, crossover method (one point and two point), mutation method (one pair or two pair swap), stop condition (time in seconds)

### Input
- File with a number of cities and a matrix of distances.
\
4 \
0 1 1 1 \
1 0 1 1 \
1 1 0 1 \
1 1 1 0

### Output
- Path
- Path length
- Execution time

### Dependencies
- Apache Commons Collections 4.4 (for exact algorithms)

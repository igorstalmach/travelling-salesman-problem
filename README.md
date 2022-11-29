# Travelling Salesman Problem

## Solution created for Efficient Algorithms Programming college class.

### Algorithms implemented
- Exact algorithms
  - Branch and Bound (Least cost)
  - Bruteforce
- Probabilistic algorithms
  - Simulated Annealing
    - with customizable parameters: starting temperature, final temperature, temperature reduction coefficient, stop condition (in seconds)

### Input
- File with a number of cities and a matrix of distances.
\
4 \
0 1 1 1 \
1 0 1 1 \
1 1 0 1 \
1 1 1 0

##### Output
- Path
- Path length

##### Dependencies
- Apache Commons Collections 4.4 (for exact algorithms)
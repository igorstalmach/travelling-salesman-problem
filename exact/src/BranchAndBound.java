import java.util.*;

public class BranchAndBound {

    // A method used to find a vertex with the smallest value.
    private Vertex findSmallestVertex(LinkedList<Vertex> allVertices) {
        Vertex smallestVertex = null;
        int minValue = Integer.MAX_VALUE;

        for (int i = allVertices.size() - 1; i >= 0; i--) {
            if(allVertices.get(i).getValue() < minValue) {
                minValue = allVertices.get(i).getValue();
                smallestVertex = allVertices.get(i);
            }
        }
        return smallestVertex;
    }

    // A method used to find all previous vertices.
    private void getVisitedVertices(Vertex vertex, LinkedList<Vertex> visitedVertices) {
        while (vertex != null) {
            visitedVertices.add(vertex);
            vertex = vertex.prevVertex;
        }
    }

    // A method used to find the final path.
    private void createPath(LinkedList<Vertex> visitedVertices, LinkedList<Integer> path) {
        for (Vertex vertex : visitedVertices) {
            path.add(vertex.getKey());
        }
    }

    // Main method of the algorithm.
    public void BaB(String filePath) {
        // Reading from a file.
        System.out.println("Info: Reading from file");
        ArrayList<ArrayList<Integer>> cities = ReadFromFile.readFile(filePath);
        int cityNumber = cities.get(0).get(0);
        cities.remove(0);

        // Matrix formatting and reduction.
        cities = MatrixManipulation.createMatrix(cities);
        ReductionResult reductionResult = MatrixManipulation.reduceMatrix(cities);
        cities = reductionResult.matrix;
        int lowerBoundary = reductionResult.getTotal();
        int currUpperBoundary = lowerBoundary;
        int upperBoundary = Integer.MAX_VALUE;

        // A list storing numbers of all vertices.
        // LinkedList, as this object is more efficient for additions and deletions.
        LinkedList<Integer> allNodeKeys = new LinkedList<>();
        for (int i = 1; i < cityNumber; i++) {
            allNodeKeys.add(i);
        }

        // Initialization of: the variable of the current parent, the temporary parent needed in the loop,
        // the vertex with the smallest value and the current shortest route.
        Vertex parentVertex = new Vertex(0, Integer.MAX_VALUE);
        Vertex tempParentVertex = null;
        Vertex smallestVertex = null;
        int currShortestPath = Integer.MAX_VALUE;
        // Final path
        LinkedList<Integer> path = new LinkedList<>();
        path.add(0);
        // Adding a previously reduced matrix to a starting vertex.
        parentVertex.setMatrix(cities);
        // Creating a list storing all calculated vertices.
        LinkedList<Vertex> allVertices = new LinkedList<>();
        allVertices.add(parentVertex);

        System.out.println("Info: Started branch and bound");
        // Main loop of the algorithm.
        while(!allNodeKeys.isEmpty()) {
            // Checking all children of the selected parent.
            for (int childNode : allNodeKeys) {
                ReductionResult tempReductionResult;
                Vertex vertex = new Vertex(childNode, 0);
                // Calculates the value of a vertex according to the formula G(prev, next) + y + y'
                tempReductionResult = MatrixManipulation.reduceMatrix(MatrixManipulation.createNodeMatrix(parentVertex.matrix, parentVertex.getKey(), childNode));
                tempReductionResult.addToTotal(parentVertex.matrix.get(parentVertex.getKey()).get(childNode));
                tempReductionResult.addToTotal(currUpperBoundary);
                // Adding a new reduced matrix to the vertex object.
                vertex.setMatrix(tempReductionResult.matrix);
                // Update vertex value.
                vertex.setValue(tempReductionResult.total);
                // Updating the vertex's parent.
                vertex.prevVertex = parentVertex;
                // Adding a vertex to the list of all calculated vertices.
                allVertices.add(vertex);
                // If the value of a given vertex is less than current, that is, it is a hypothetical next vertex,
                // it is stored along with the entire vertex.
                if(vertex.getValue() < currShortestPath) {
                    currShortestPath = vertex.getValue();
                    tempParentVertex = vertex;
                }
            }
            // Final parent update.
            parentVertex = tempParentVertex;
            // Marking vertex as visited.
            parentVertex.setVisited(true);

            // If the value of the currently selected parent is greater than the upper limit,
            // then there is no point in continuing down this path.
            if (parentVertex.getValue() > upperBoundary) {
                allNodeKeys.clear();
            }

            // Removing the parent from the list of all possible vertices to be visited next.
            allNodeKeys.removeFirstOccurrence(parentVertex.getKey());
            currUpperBoundary = currShortestPath;
            currShortestPath = Integer.MAX_VALUE;

            // A condition that checks whether the algorithm should be ended.
            if (allNodeKeys.size() == 0) {
                // Update the upper limit only if it's smaller.
                if (currUpperBoundary < upperBoundary) {
                    upperBoundary = currUpperBoundary;
                    smallestVertex = allVertices.getLast();
                } else {
                    currUpperBoundary = upperBoundary;
                }

                // Removal of all vertices whose value is greater than the upper limit.
                for (int i = allVertices.size() - 1; i >= 0; i--) {
                    if (allVertices.get(i).getValue() > currUpperBoundary && allVertices.get(i).getValue() < Integer.MAX_VALUE) {
                        allVertices.remove(i);
                    }
                }

                // Checking if all other vertices have been visited,
                // if not - the loop is terminated and the program continues
                boolean flag = true;
                for (Vertex vertex : allVertices) {
                    if (!vertex.getVisited() && vertex.getKey() != 0) {
                        flag = false;
                        break;
                    }
                }

                // If all vertices have been visited, it means that the last vertex
                // is in the list of all vertices.
                // Finding visited vertices and creating a path.
                LinkedList<Vertex> visitedVertices = new LinkedList<>();
                if (flag) {
                    getVisitedVertices(smallestVertex, visitedVertices);
                    createPath(visitedVertices, path);
                    break;
                }

                // Removal of all visited vertices.
                for (int i = allVertices.size() - 1; i >= 0; i--) {
                    if (allVertices.get(i).getVisited()) {
                        allVertices.remove(i);
                    }
                }

                // Finding the smallest unvisited vertex and tracing its path.
                Vertex currSmallestVertex = findSmallestVertex(allVertices);
                getVisitedVertices(currSmallestVertex, visitedVertices);

                // Re-create a list storing the vertices to be visited.
                for (int i = 1; i < cityNumber; i++) {
                    allNodeKeys.add(i);
                }

                // Removing from the list of vertices to visit the vertices that are in the already discovered path.
                for (Vertex vertex : visitedVertices) {
                    if (allNodeKeys.contains(vertex.getKey())) {
                        allNodeKeys.removeFirstOccurrence(vertex.getKey());
                    }
                }

                // Update parent and upper limit.
                parentVertex = currSmallestVertex;
                parentVertex.setVisited(true);
                currUpperBoundary = currSmallestVertex.getValue();
            }
        }

        // Outputs correctly formatted results.
        for (int i = 0; i < path.size(); i++) {
            if (i == path.size() - 1) {
                System.out.println(path.get(i));
            } else {
                System.out.print(path.get(i) + " -> ");
            }
        }
        System.out.println(currUpperBoundary);
        System.out.println();
    }
}

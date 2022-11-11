import java.util.*;

public class BranchAndBound {

    // A method used to find a vertex with the smallest value.
    private Node findSmallestNode(LinkedList<Node> allNodes) {
        Node smallestNode = null;
        int minValue = Integer.MAX_VALUE;

        for (int i = allNodes.size() - 1; i >= 0; i--) {
            if(allNodes.get(i).getValue() < minValue) {
                minValue = allNodes.get(i).getValue();
                smallestNode = allNodes.get(i);
            }
        }
        return smallestNode;
    }

    // A method used to find all previous vertices.
    private void getVisitedNodes(Node node, LinkedList<Node> visitedNodes) {
        while (node != null) {
            visitedNodes.add(node);
            node = node.prevNode;
        }
    }

    // A method used to find the final path.
    private void createPath(LinkedList<Node> visitedNodes, LinkedList<Integer> path) {
        for (Node node : visitedNodes) {
            path.add(node.getKey());
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
        Node parentNode = new Node(0, Integer.MAX_VALUE);
        Node tempParentNode = null;
        Node smallestNode = null;
        int currShortestPath = Integer.MAX_VALUE;
        // Final path
        LinkedList<Integer> path = new LinkedList<>();
        path.add(0);
        // Adding a previously reduced matrix to a starting vertex.
        parentNode.addMatrix(cities);
        // Creating a list storing all calculated vertices.
        LinkedList<Node> allNodes = new LinkedList<>();
        allNodes.add(parentNode);

        System.out.println("Info: Started branch and bound");
        // Main loop of the algorithm.
        while(!allNodeKeys.isEmpty()) {
            // Checking all children of the selected parent.
            for (int childNode : allNodeKeys) {
                ReductionResult tempReductionResult;
                Node node = new Node(childNode, 0);
                // Calculates the value of a vertex according to the formula G(prev, next) + y + y'
                tempReductionResult = MatrixManipulation.reduceMatrix(MatrixManipulation.createNodeMatrix(parentNode.matrix, parentNode.getKey(), childNode));
                tempReductionResult.addToTotal(parentNode.matrix.get(parentNode.getKey()).get(childNode));
                tempReductionResult.addToTotal(currUpperBoundary);
                // Adding a new reduced matrix to the vertex object.
                node.addMatrix(tempReductionResult.matrix);
                // Update vertex value.
                node.setValue(tempReductionResult.total);
                // Updating the vertex's parent.
                node.prevNode = parentNode;
                // Adding a vertex to the list of all calculated vertices.
                allNodes.add(node);
                // If the value of a given vertex is less than current, that is, it is a hypothetical next vertex,
                // it is stored along with the entire vertex.
                if(node.getValue() < currShortestPath) {
                    currShortestPath = node.getValue();
                    tempParentNode = node;
                }
            }
            // Final parent update.
            parentNode = tempParentNode;
            // Marking vertex as visited.
            parentNode.setVisited(true);

            // If the value of the currently selected parent is greater than the upper limit,
            // then there is no point in continuing down this path.
            if (parentNode.getValue() > upperBoundary) {
                allNodeKeys.clear();
            }

            // Removing the parent from the list of all possible vertices to be visited next.
            allNodeKeys.removeFirstOccurrence(parentNode.getKey());
            currUpperBoundary = currShortestPath;
            currShortestPath = Integer.MAX_VALUE;

            // A condition that checks whether the algorithm should be ended.
            if (allNodeKeys.size() == 0) {
                // Update the upper limit only if it's smaller.
                if (currUpperBoundary < upperBoundary) {
                    upperBoundary = currUpperBoundary;
                    smallestNode = allNodes.getLast();
                } else {
                    currUpperBoundary = upperBoundary;
                }

                // Removal of all vertices whose value is greater than the upper limit.
                for (int i = allNodes.size() - 1; i >= 0; i--) {
                    if (allNodes.get(i).getValue() > currUpperBoundary && allNodes.get(i).getValue() < Integer.MAX_VALUE) {
                        allNodes.remove(i);
                    }
                }

                // Checking if all other vertices have been visited,
                // if not - the loop is terminated and the program continues
                boolean flag = true;
                for (Node node : allNodes) {
                    if (!node.getVisited() && node.getKey() != 0) {
                        flag = false;
                        break;
                    }
                }

                // If all vertices have been visited, it means that the last vertex
                // is in the list of all vertices.
                // Finding visited vertices and creating a path.
                LinkedList<Node> visitedNodes = new LinkedList<>();
                if (flag) {
                    getVisitedNodes(smallestNode, visitedNodes);
                    createPath(visitedNodes, path);
                    break;
                }

                // Removal of all visited vertices.
                for (int i = allNodes.size() - 1; i >= 0; i--) {
                    if (allNodes.get(i).getVisited()) {
                        allNodes.remove(i);
                    }
                }

                // Finding the smallest unvisited vertex and tracing its path.
                Node currSmallestNode = findSmallestNode(allNodes);
                getVisitedNodes(currSmallestNode, visitedNodes);

                // Re-create a list storing the vertices to be visited.
                for (int i = 1; i < cityNumber; i++) {
                    allNodeKeys.add(i);
                }

                // Removing from the list of vertices to visit the vertices that are in the already discovered path.
                for (Node node : visitedNodes) {
                    if (allNodeKeys.contains(node.getKey())) {
                        allNodeKeys.removeFirstOccurrence(node.getKey());
                    }
                }

                // Update parent and upper limit.
                parentNode = currSmallestNode;
                parentNode.setVisited(true);
                currUpperBoundary = currSmallestNode.getValue();
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

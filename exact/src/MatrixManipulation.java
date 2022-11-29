import java.util.ArrayList;
import java.util.LinkedList;

public class MatrixManipulation {
    // A method used to convert zeros on the diagonal into INF values.
    public static ArrayList<ArrayList<Integer>> createMatrix(ArrayList<ArrayList<Integer>> prevMatrix) {
        ArrayList<ArrayList<Integer>> newMatrix = new ArrayList<>();
        for (int i = 0; i < prevMatrix.size(); i++) {
            ArrayList<Integer> tempMatrix = new ArrayList<>();
            for (int j = 0; j < prevMatrix.get(i).size(); j++) {
                if (i == j) {
                    // The NULL value is added first, since the .set() method only modifies a value.
                    tempMatrix.add(j, null);
                    tempMatrix.set(j, Integer.MAX_VALUE);
                } else {
                    tempMatrix.add(j, null);
                    tempMatrix.set(j, prevMatrix.get(i).get(j));
                }
            }
            newMatrix.add(tempMatrix);
        }
        return newMatrix;
    }

    // A method used to reduce matrices.
    public static ReductionResult reduceMatrix(ArrayList<ArrayList<Integer>> tempMatrix) {
        ArrayList<ArrayList<Integer>> matrix = DeepCopy.copyArray(tempMatrix);

        // A variable that stores the sum of the smallest values in each row.
        int rowMinValue = 0;
        // A loop that performs calculations on rows.
        for (int i = 0; i < matrix.size(); i++) {
            int minValue = Integer.MAX_VALUE;
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (matrix.get(i).get(j) < minValue) {
                    minValue = matrix.get(i).get(j);
                }
            }
            // If the entire line consists of INF values, the minimum value of the line is set to 0.
            if (minValue == Integer.MAX_VALUE) {
                minValue = 0;
            } else {
                rowMinValue += minValue;
            }
            for (int j = 0; j < matrix.get(i).size(); j++) {
                // Bypassing unnecessary deletion of numbers from INF.
                if (matrix.get(i).get(j) == Integer.MAX_VALUE) {
                    continue;
                } else {
                    matrix.get(i).set(j, matrix.get(i).get(j) - minValue);
                }
            }
        }

        // A variable that stores the sum of the smallest values in each column.
        int columnMinValue = 0;
        // A loop that performs calculations on columns.
        for (int i = 0; i < matrix.size(); i++) {
            int minValue = Integer.MAX_VALUE;
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (matrix.get(j).get(i) < minValue) {
                    minValue = matrix.get(j).get(i);
                }
            }
            // If the entire line consists of INF values, the minimum value of the line is set to 0.
            if (minValue == Integer.MAX_VALUE) {
                minValue = 0;
            } else {
                columnMinValue += minValue;
            }
            for (int j = 0; j < matrix.get(i).size(); j++) {
                // Bypassing unnecessary deletion of numbers from INF.
                if (matrix.get(j).get(i) == Integer.MAX_VALUE) {
                    continue;
                } else {
                    matrix.get(j).set(i, matrix.get(j).get(i) - minValue);
                }
            }
        }

        // Create and return a helper object.
        return new ReductionResult(matrix, rowMinValue, columnMinValue);
    }

    // A method used to populate the selected row and column of a matrix with INF values.
    public static ArrayList<ArrayList<Integer>> createNodeMatrix(ArrayList<ArrayList<Integer>> tempMatrix, int row, int column) {
        ArrayList<ArrayList<Integer>> matrix = DeepCopy.copyArray(tempMatrix);
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if ((i == row) || (j == column)) {
                    matrix.get(i).set(j, Integer.MAX_VALUE);
                }
            }
        }
        return matrix;
    }

    // A method used to populate the selected row and column of a matrix with INF values multiple levels at once.
    public static ArrayList<ArrayList<Integer>> createNestedNodeMatrix(ArrayList<ArrayList<Integer>> tempMatrix, LinkedList<Integer> path, int parentVertex) {
        ArrayList<ArrayList<Integer>> matrix = DeepCopy.copyArray(tempMatrix);
        LinkedList<Integer> tempPath = DeepCopy.copyList(path);
        tempPath.add(parentVertex);
        for (int i = 0; i < path.size(); i++) {
            matrix = createNodeMatrix(matrix, tempPath.get(i), tempPath.get(i+1));
        }
        return matrix;
    }
}

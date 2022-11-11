import java.util.ArrayList;
import java.util.LinkedList;

public class MatrixManipulation {
    // Metoda służąca do zamiany zer na przekątnej na wartości INF.
    public static ArrayList<ArrayList<Integer>> createMatrix(ArrayList<ArrayList<Integer>> prevMatrix) {
        ArrayList<ArrayList<Integer>> newMatrix = new ArrayList<>();
        for (int i = 0; i < prevMatrix.size(); i++) {
            ArrayList<Integer> tempMatrix = new ArrayList<>();
            for (int j = 0; j < prevMatrix.get(i).size(); j++) {
                if (i == j) {
                    // Dodawana jest najpierw wartość NULL, ponieważ metoda .set() jedynie modyfikuje wartość.
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

    // Metoda służąca do redukcji macierzy.
    public static ReductionResult reduceMatrix(ArrayList<ArrayList<Integer>> tempMatrix) {
        ArrayList<ArrayList<Integer>> matrix = DeepCopy.copyArray(tempMatrix);

        // Zmienna przechowująca sumę najmniejszych wartości w każdym wierszu.
        int rowMinValue = 0;
        // Pętla wykonująca obliczenia na wierszach.
        for (int i = 0; i < matrix.size(); i++) {
            int minValue = Integer.MAX_VALUE;
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (matrix.get(i).get(j) < minValue) {
                    minValue = matrix.get(i).get(j);
                }
            }
            // Jeśli cały wiersz składa się z wartości INF, przyjmuje się że minimalna wartość wiersza wynosi 0.
            if (minValue == Integer.MAX_VALUE) {
                minValue = 0;
            } else {
                rowMinValue += minValue;
            }
            for (int j = 0; j < matrix.get(i).size(); j++) {
                // Ominięcie niepotrzebnego usuwania liczb od INF.
                if (matrix.get(i).get(j) == Integer.MAX_VALUE) {
                    continue;
                } else {
                    matrix.get(i).set(j, matrix.get(i).get(j) - minValue);
                }
            }
        }

        // Zmienna przechowująca sumę najmniejszych wartości w każdej kolumnie.
        int columnMinValue = 0;
        // Pętla wykonująca obliczenia na kolumnach.
        for (int i = 0; i < matrix.size(); i++) {
            int minValue = Integer.MAX_VALUE;
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (matrix.get(j).get(i) < minValue) {
                    minValue = matrix.get(j).get(i);
                }
            }
            // Jeśli cały wiersz składa się z wartości INF, przyjmuje się że minimalna wartość wiersza wynosi 0.
            if (minValue == Integer.MAX_VALUE) {
                minValue = 0;
            } else {
                columnMinValue += minValue;
            }
            for (int j = 0; j < matrix.get(i).size(); j++) {
                // Ominięcie niepotrzebnego usuwania liczb od INF.
                if (matrix.get(j).get(i) == Integer.MAX_VALUE) {
                    continue;
                } else {
                    matrix.get(j).set(i, matrix.get(j).get(i) - minValue);
                }
            }
        }

        // Utworzenie i zwrócenie obiektu pomocniczego.
        return new ReductionResult(matrix, rowMinValue, columnMinValue);
    }

    // Metoda służąca do zapełnienia wybranego wiersza i kolumny macierzy wartościami INF.
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

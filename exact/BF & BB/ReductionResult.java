import java.util.ArrayList;

// Auxiliary object used to store the results after matrix reduction.
class ReductionResult {
    ArrayList<ArrayList<Integer>> matrix;
    int rowMinValue;
    int columnMinValue;
    int total;

    ReductionResult(ArrayList<ArrayList<Integer>> matrix, int rowMinValue, int columnMinValue){
        this.matrix = matrix;
        this.rowMinValue = rowMinValue;
        this.columnMinValue = columnMinValue;
        this.total = rowMinValue + columnMinValue;
    }

    public int getTotal() {
        return total;
    }

    public void addToTotal(int number) {
        total += number;
    }
}
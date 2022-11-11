import java.util.*;

public class BranchAndBound {

    // Metoda służąca do znalezienia wierzchołka o najmniejszej wartości.
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

    // Metoda służąca do znalezienia wszystkich wcześniejszych wierzchołków.
    private void getVisitedNodes(Node node, LinkedList<Node> visitedNodes) {
        while (node != null) {
            visitedNodes.add(node);
            node = node.prevNode;
        }
    }

    // Metoda służąca do znalezienia ostatecznej ścieżki.
    private void createPath(LinkedList<Node> visitedNodes, LinkedList<Integer> path) {
        for (Node node : visitedNodes) {
            path.add(node.getKey());
        }
    }

    // Metoda główna algorytmu.
    public void BaB(String filePath) {
        // Odczytywanie z pliku.
        System.out.println("Info: Reading from file");
        ArrayList<ArrayList<Integer>> cities = ReadFromFile.readFile(filePath);
        int cityNumber = cities.get(0).get(0);
        cities.remove(0);

        // Formatowanie i redukcja macierzy.
        cities = MatrixManipulation.createMatrix(cities);
        ReductionResult reductionResult = MatrixManipulation.reduceMatrix(cities);
        cities = reductionResult.matrix;
        int lowerBoundary = reductionResult.getTotal();
        int currUpperBoundary = lowerBoundary;
        int upperBoundary = Integer.MAX_VALUE;

        // Lista przechowująca numery wszystkich wierzchołków.
        // LinkedList, ponieważ obiekt ten jest wydajniejszy przy dodawaniu i usuwaniu.
        LinkedList<Integer> allNodeKeys = new LinkedList<>();
        for (int i = 1; i < cityNumber; i++) {
            allNodeKeys.add(i);
        }

        // Inicjalizacja zmiennej obecnego rodzica, tymczasowego rodzica potrzebnego w pętli,
        // wierzchołka o najmniejszej wartości oraz obecnej najkrótszej trasy
        Node parentNode = new Node(0, Integer.MAX_VALUE);
        Node tempParentNode = null;
        Node smallestNode = null;
        int currShortestPath = Integer.MAX_VALUE;
        // Ostateczna ścieżka
        LinkedList<Integer> path = new LinkedList<>();
        path.add(0);
        // Dodanie wcześniej zredukowanej macierzy do rodzica wierzchołka.
        parentNode.addMatrix(cities);
        // Utworzenie listy przechowującej wszystkie obliczone wierzchołki.
        LinkedList<Node> allNodes = new LinkedList<>();
        allNodes.add(parentNode);

        System.out.println("Info: Started branch and bound");
        // Pętla główna algorytmu.
        while(!allNodeKeys.isEmpty()) {
            // Sprawdzanie wszystkich dzieci wybranego rodzica.
            for (int childNode : allNodeKeys) {
                ReductionResult tempReductionResult;
                Node node = new Node(childNode, 0);
                // Oblicza wartość wierzchołka wg wzoru G(prev, next) + y + y'
                tempReductionResult = MatrixManipulation.reduceMatrix(MatrixManipulation.createNodeMatrix(parentNode.matrix, parentNode.getKey(), childNode));
                tempReductionResult.addToTotal(parentNode.matrix.get(parentNode.getKey()).get(childNode));
                tempReductionResult.addToTotal(currUpperBoundary);
                // Dodanie nowej zredukowanej macierzy do obiektu wierzchołka.
                node.addMatrix(tempReductionResult.matrix);
                // Aktualizacja wartości wierzchołka.
                node.setValue(tempReductionResult.total);
                // Aktualizacja rodzica wierzchołka.
                node.prevNode = parentNode;
                // Dodanie wierzchołka do listy wszystkich obliczonych.
                allNodes.add(node);
                // Jeśli wartość danego wierzchołka jest mniejsza, tzn. jest on hipotetycznym następnym wierzchołkiem,
                // jest ona zapisywana wraz z całym wierzchołkiem.
                if(node.getValue() < currShortestPath) {
                    currShortestPath = node.getValue();
                    tempParentNode = node;
                }
            }
            // Ostateczna aktualizacja rodzica.
            parentNode = tempParentNode;
            // Oznaczenie wierzchołka odwiedzonego.
            parentNode.setVisited(true);

            // Jeśli wartość obecnie wybranego rodzica jest większa od górnej granicy,
            // to nie ma sensu kontynuować tej drogi.
            if (parentNode.getValue() > upperBoundary) {
                allNodeKeys.clear();
            }

            // Usunięcie rodzica z listy wszystkich możliwych do następnego odwiedzenia wierzchołków.
            allNodeKeys.removeFirstOccurrence(parentNode.getKey());
            currUpperBoundary = currShortestPath;
            currShortestPath = Integer.MAX_VALUE;

            // Warunek sprawdzający, czy algorytm należy zakończyć.
            if (allNodeKeys.size() == 0) {
                // Podmiana górnej granicy wyłącznie, gdy jest mniejsza.
                if (currUpperBoundary < upperBoundary) {
                    upperBoundary = currUpperBoundary;
                    smallestNode = allNodes.getLast();
                } else {
                    currUpperBoundary = upperBoundary;
                }

                // Usunięcie wszystkich wierzchołków, których wartość jest większa od górnej granicy.
                for (int i = allNodes.size() - 1; i >= 0; i--) {
                    if (allNodes.get(i).getValue() > currUpperBoundary && allNodes.get(i).getValue() < Integer.MAX_VALUE) {
                        allNodes.remove(i);
                    }
                }

                // Sprawdzenie czy wszystkie pozostałe wierzchołki zostały odwiedzone,
                // jeśli nie - pętla jest przerywana i program działa dalej
                boolean flag = true;
                for (Node node : allNodes) {
                    if (!node.getVisited() && node.getKey() != 0) {
                        flag = false;
                        break;
                    }
                }

                // Jeśli wszystkie wierzchołki zostały odwiedzone, oznacza to, że ostatni wierzchołek
                // w liście wszystkich wierzchołków jest ostateczny.
                // Odnalezienie odwiedzonych wierzchołków oraz utworzenie drogi.
                LinkedList<Node> visitedNodes = new LinkedList<>();
                if (flag) {
                    getVisitedNodes(smallestNode, visitedNodes);
                    createPath(visitedNodes, path);
                    break;
                }

                // Usunięcie wszystkich odwiedzonych wierzchołków.
                for (int i = allNodes.size() - 1; i >= 0; i--) {
                    if (allNodes.get(i).getVisited()) {
                        allNodes.remove(i);
                    }
                }

                // Odnalezienie najmniejszego nieodwiedzonego wierzchołka i odszukanie jego trasy.
                Node currSmallestNode = findSmallestNode(allNodes);
                getVisitedNodes(currSmallestNode, visitedNodes);

                // Ponowne utworzenie listy przechowującej wierzchołki do odwiedzenia.
                for (int i = 1; i < cityNumber; i++) {
                    allNodeKeys.add(i);
                }

                // Usunięcie z listy wierzchołków do odwiedzenia wierzchołków, które znajdują się w odkrytej już ścieżce.
                for (Node node : visitedNodes) {
                    if (allNodeKeys.contains(node.getKey())) {
                        allNodeKeys.removeFirstOccurrence(node.getKey());
                    }
                }

                // Aktualizacja rodzica oraz górnej granicy.
                parentNode = currSmallestNode;
                parentNode.setVisited(true);
                currUpperBoundary = currSmallestNode.getValue();
            }
        }

        // Wypisanie poprawnie sformatowanych wyników.
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

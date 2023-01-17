import java.util.LinkedList;
import java.util.Random;

public class Mutation {
    // A mutation that swaps one pair of genes.
    public static LinkedList<Integer> onePairSwapMutation(LinkedList<Integer> path) {
        LinkedList<Integer> newPath = DeepCopy.copyList(path);
        Random rand = new Random();

        int firstRandomIndex = rand.nextInt(path.size());
        int secondRandomIndex = rand.nextInt(path.size());

        int firstGene = newPath.get(firstRandomIndex);
        int secondGene = newPath.get(secondRandomIndex);

        newPath.set(firstRandomIndex, secondGene);
        newPath.set(secondRandomIndex, firstGene);

        return newPath;
    }

    // A mutation that swaps two pairs of genes.
    public static LinkedList<Integer> twoPairSwapMutation(LinkedList<Integer> path) {
        LinkedList<Integer> newPath = DeepCopy.copyList(path);
        Random rand = new Random();

        int firstRandomIndex = rand.nextInt(path.size());
        int secondRandomIndex = rand.nextInt(path.size());

        int firstGene = newPath.get(firstRandomIndex);
        int secondGene = newPath.get(secondRandomIndex);

        newPath.set(firstRandomIndex, secondGene);
        newPath.set(secondRandomIndex, firstGene);

        int thirdRandomIndex = rand.nextInt(path.size());
        int fourthRandomIndex = rand.nextInt(path.size());

        int thirdGene = newPath.get(thirdRandomIndex);
        int fourthGene = newPath.get(fourthRandomIndex);

        newPath.set(thirdRandomIndex, fourthGene);
        newPath.set(fourthRandomIndex, thirdGene);

        return newPath;
    }
}

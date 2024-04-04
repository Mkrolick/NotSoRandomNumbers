import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.io.FileWriter;
import java.io.IOException;


public class BruteForce {
    public static void main(String[] args) {
        Map<Long, List<Integer>> sequence = generateSequence();

        List<Map.Entry<Long, List<Integer>>> sortedEntries = sequence.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue, new ListComparator()))
                .collect(Collectors.toList());

        // Printing the sorted lists
        //for (Map.Entry<Integer, List<Integer>> entry : sortedEntries) {
        //    System.out.println("Key: " + entry.getKey() + " - Value: " + entry.getValue());
        //}

        // Save the printed values to a file
        try {
            FileWriter writer = new FileWriter("/Users/malcolmkrolick/Documents/GitHub/NotSoRandomNumbers/output.txt");
            for (Map.Entry<Long, List<Integer>> entry : sortedEntries) {
                writer.write("Key: " + entry.getKey() + " - Value: " + entry.getValue() + "\n");
            }
            writer.close();
            System.out.println("Values saved to file.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving the values to file.");
            e.printStackTrace();
        }


    }

    public static Map<Long, List<Integer>> generateSequence() {

        Map<Long, List<Integer>> bigSeq = new HashMap<>();

        long[] seedValues = new long[10000];
        for (int i = 0; i < 10000; i++) {
            seedValues[i] = i;
        }

        for (long seed : seedValues) {
            List<Integer> sequence = new ArrayList<>();

            Random random = new Random(seed);

            for (int i = 0; i < 7; i ++) {
                int randomNumber = random.nextInt();
                sequence.add(randomNumber);
            }

            List<Integer> sortedSequence = new ArrayList<>(sequence);
            Collections.sort(sortedSequence);

            for (int i = 0; i < sequence.size(); i++) {
                int index = sequence.indexOf(sortedSequence.get(i));


                
                sequence.set(index, i + 1);
            }
            
            boolean allPositive = sequence.stream().allMatch(value -> ((long) value) > 0);
            if (allPositive && !bigSeq.containsValue(sequence)) {
                bigSeq.put(seed, sequence);
            }

        }



        return bigSeq;
    }

    static class ListComparator implements Comparator<List<Integer>> {
        @Override
        public int compare(List<Integer> o1, List<Integer> o2) {
            int size = Math.min(o1.size(), o2.size());
            for (int i = 0; i < size; i++) {
                // Assuming the goal is to sort lists closer to the sequence 1,2,3..., comparing element by element
                int comparison = Long.compare(o1.get(i), o2.get(i));
                if (comparison != 0) {
                    return comparison;
                }
            }
            // If all compared elements are equal, shorter lists are considered "closer"
            return Long.compare(o1.size(), o2.size());
        }
    }
}
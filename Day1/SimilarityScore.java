package Day1;

import java.io.*;
import java.util.*;

public class SimilarityScore {
    public static void main(String[] args) {
        try {
            
            List<int[]> inputPairs = readInput("F:\\AdventofCode\\Day1\\input_1.txt");

            
            List<Integer> list1 = new ArrayList<>();
            List<Integer> list2 = new ArrayList<>();
            
            for (int[] pair : inputPairs) {
                list1.add(pair[0]);
                list2.add(pair[1]);
            }
            Map<Integer,Integer> apprenceMap=new HashMap<>();
            for (int num : list1) {
                int count = 0;
                for (int n : list2) {
                    if (num == n) {
                        count++;
                    }
                }
                apprenceMap.put(num, count * num);
            }
            int totalValue = 0;
            for (int value : apprenceMap.values()) {
                totalValue += value;
            }
            System.out.println(totalValue);


        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static List<int[]> readInput(String filename) throws IOException {
        List<int[]> pairs = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] numbers = line.split("\\s+");
                int[] pair = { Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]) };
                pairs.add(pair);
            }
        }
        return pairs;
    }
}

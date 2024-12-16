package Day1;

import java.io.*;
import java.util.*;

public class SumofDiffrence {
    public static void main(String[] args) {
        try {
            
            List<int[]> inputPairs = readInput("F:\\AdventofCode\\Day1\\input_1.txt");

            
            List<Integer> list1 = new ArrayList<>();
            List<Integer> list2 = new ArrayList<>();
            
            for (int[] pair : inputPairs) {
                list1.add(pair[0]);
                list2.add(pair[1]);
            }
            System.out.println("UnList 1 :"  + list1);
            Collections.sort(list1);
            Collections.sort(list2);
            System.out.println("List 1 :"  + list1);
            int totalDifference = 0;
            for (int i = 0; i < list1.size(); i++) {
                totalDifference += Math.abs(list1.get(i) - list2.get(i));
            }

            
            System.out.println("The total sum of differences is: " + totalDifference);

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
                int[] pair = {Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1])};
                pairs.add(pair);
            }
        }
        return pairs;
    }
}

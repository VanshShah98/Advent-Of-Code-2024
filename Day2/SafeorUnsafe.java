package Day2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SafeorUnsafe {
    public static int safecount = 0;

    public static void main(String[] args) {
        try {
            List<int[]> inputArrays = readInput("F:\\AdventofCode\\Day2\\input_2.txt");
            for (int i = 0; i < inputArrays.size(); i++) {
                int[] array = inputArrays.get(i);
                if (isSorted(array) && checkDifferenceCondition(array)) {
                    safecount++;
                }
            }
            System.out.println(safecount);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    public static boolean checkDifferenceCondition(int[] array) {
        boolean isSafe = false;
        for (int j = 0; j < array.length - 1; j++) {
            if (Math.abs(array[j] - array[j + 1]) >= 1 && Math.abs(array[j] - array[j + 1]) <= 3) {
                isSafe = true;
            } else {
                isSafe = false;
                break;
            }
        }
        return isSafe;
    }

    public static List<int[]> readInput(String filename) throws IOException {
        List<int[]> arrays = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {

                String[] numbers = line.split("\\s+");
                int[] array = Arrays.stream(numbers)
                        .mapToInt(Integer::parseInt)
                        .toArray();
                arrays.add(array);
            }
        }
        return arrays;
    }

    public static boolean isSorted(int[] arr) {
        if (arr.length < 2) {
            return true;
        }

        boolean ascending = true;
        boolean descending = true;

        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                ascending = false;
            }
            if (arr[i] < arr[i + 1]) {
                descending = false;
            }
        }

        return ascending || descending;
    }

}

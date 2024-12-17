package Day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CourptedMemoryII {
    public static void main(String[] args) {
        String filePath = "F:\\AdventofCode\\Day3\\input_2.txt"; // Path to your file
        try {
            // Read the content of the file
            String input = readFile(filePath);

            // Process the instructions and calculate the sum of valid multiplications
            int result = processString(input);

            // Output the result
            System.out.println("Sum of valid products: " + result);
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    // Read the file content into a string
    public static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
        }
        return content.toString();
    }

    // Process the string and calculate the sum of valid products
    public static int processString(String input) {
        boolean isMulEnabled = true; // Initially, multiplication is enabled
        int totalSum = 0;

        // Regular expression to match do(), don't(), and mul(x, y)
        Pattern pattern = Pattern.compile("(do\\(\\)|don't\\(\\)|mul\\((\\d+),(\\d+)\\))");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String instruction = matcher.group(1); // Full match
            String firstNum = matcher.group(2);    // First number in mul(x, y)
            String secondNum = matcher.group(3);   // Second number in mul(x, y)

            if ("do()".equals(instruction)) {
                isMulEnabled = true; // Enable multiplication
            } else if ("don't()".equals(instruction)) {
                isMulEnabled = false; // Disable multiplication
            } else if (instruction.startsWith("mul") && isMulEnabled) {
                // Perform multiplication if enabled
                int num1 = Integer.parseInt(firstNum);
                int num2 = Integer.parseInt(secondNum);
                totalSum += num1 * num2;
            }
        }

        return totalSum;
    }
}

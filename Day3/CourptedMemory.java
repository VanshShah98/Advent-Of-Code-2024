package Day3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CourptedMemory {
    public static void main(String[] args) {
        String filePath = "F:\\AdventofCode\\Day3\\input.txt"; // Update this with your file path

        try {
            // Read the content of the file into a string
            String input = readFile(filePath);

            // Process the string to extract and calculate valid multiplications
            List<Integer> results = processString(input);
            int sum = 0;
            for(int i=0;i<results.size();i++){
                sum+=results.get(i);
            }
            // Print the results
            System.out.println("Sum of valid multiplications: " + sum);

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
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
    public static List<Integer> processString(String input) {
        List<Integer> products = new ArrayList<>();
        Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
           
            int num1 = Integer.parseInt(matcher.group(1));
            int num2 = Integer.parseInt(matcher.group(2));

            
            products.add(num1 * num2);
        }

        return products;
    }
}

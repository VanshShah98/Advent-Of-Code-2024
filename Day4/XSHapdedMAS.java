package Day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class XSHapdedMAS {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("F:\\AdventofCode\\Day4\\input.txt"));

        StringBuilder gridContent = new StringBuilder();
        // String word = "XMAS";  // Word we're searching for in part 1
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) continue;
            gridContent.append(line).append("\n");
        }

        // Prepare grid and rowsArray
        String[] rowsArray = gridContent.toString().split("\n");
        int rows = rowsArray.length;
        int cols = rowsArray[0].length();

        char[][] grid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            grid[i] = rowsArray[i].toCharArray();
        }

        scanner.close();

        // Part 1: Find occurrences of "XMAS" and "SAMX"
        int part1Count = part1(grid);
        System.out.println("Part 1 result: " + part1Count);

        // Part 2: Find the cross pattern with 'A' as the center
        int part2Count = part2(grid);
        System.out.println("Part 2 result: " + part2Count);
    }

    // Part 1: Find occurrences of "XMAS" and "SAMX" in grid
    public static int part1(char[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        List<String> lines = new ArrayList<>();

        // Adding row-wise lines
        for (int i = 0; i < row; i++) {
            lines.add(new String(grid[i]));
        }

        // Adding column-wise lines
        for (int j = 0; j < col; j++) {
            StringBuilder column = new StringBuilder();
            for (int i = 0; i < row; i++) {
                column.append(grid[i][j]);
            }
            lines.add(column.toString());
        }

        // Adding diagonals
        Map<Integer, List<Character>> mainDiagonals = new HashMap<>();
        Map<Integer, List<Character>> antiDiagonals = new HashMap<>();
        findDiagonals(grid, mainDiagonals, antiDiagonals);

        // Adding diagonals as strings to lines
        for (List<Character> diag : mainDiagonals.values()) {
            lines.add(charListToString(diag));
        }
        for (List<Character> diag : antiDiagonals.values()) {
            lines.add(charListToString(diag));
        }

        // Count occurrences of "XMAS" and "SAMX"
        int count = 0;
        for (String line : lines) {
            count += countOccurrences(line, "XMAS");
            count += countOccurrences(line, "SAMX");
        }

        return count;
    }

    // Part 2: Find the cross pattern with 'A' as the center
    public static int part2(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        int count = 0;

        Set<Character> set = new HashSet<>();
        set.add('M');
        set.add('S');

        // Check for "A" being the center of the cross
        for (int r = 1; r < rows - 1; r++) {
            for (int c = 1; c < cols - 1; c++) {
                if (grid[r][c] == 'A') {
                    if (set.contains(grid[r - 1][c - 1]) && set.contains(grid[r + 1][c + 1]) &&
                        set.contains(grid[r - 1][c + 1]) && set.contains(grid[r + 1][c - 1])) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    // Helper method to count occurrences of the word in a line
    public static int countOccurrences(String line, String word) {
        int count = 0;
        int index = 0;
        while ((index = line.indexOf(word, index)) != -1) {
            count++;
            index++;
        }
        return count;
    }

    // Helper method to convert List<Character> to String
    public static String charListToString(List<Character> charList) {
        StringBuilder sb = new StringBuilder();
        for (Character c : charList) {
            sb.append(c);
        }
        return sb.toString();
    }

    // Find diagonals from top-left to bottom-right and top-right to bottom-left
    private static void findDiagonals(char[][] grid, Map<Integer, List<Character>> mainDiagonals, Map<Integer, List<Character>> antiDiagonals) {
        int rows = grid.length;
        int cols = grid[0].length;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                // Main diagonals (top-left to bottom-right)
                int keyMain = r - c;
                mainDiagonals.putIfAbsent(keyMain, new ArrayList<>());
                mainDiagonals.get(keyMain).add(grid[r][c]);

                // Anti-diagonals (top-right to bottom-left)
                int keyAnti = r + c;
                antiDiagonals.putIfAbsent(keyAnti, new ArrayList<>());
                antiDiagonals.get(keyAnti).add(grid[r][c]);
            }
        }
    }
}

package Day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SearchOfXMAS {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("F:\\AdventofCode\\Day4\\input.txt"));
        
        StringBuilder gridContent = new StringBuilder();
        String word = "XMAS";
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) continue;
            if (word == null) {
                word = line;  
            } else {
                gridContent.append(line).append("\n");
            }
        }

        
        String[] rowsArray = gridContent.toString().split("\n");
        int rows = rowsArray.length;
        int cols = rowsArray[0].length();
        
        char[][] grid = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            grid[i] = rowsArray[i].toCharArray();
        }

        
        scanner.close();

        
        System.out.println("Total occurrences of word: " + findWords(grid, word));
    }

    public static int findWords(char[][] board, String word) {
        int row = board.length;
        int col = board[0].length;
        int[][] directions = {
            {0, 1}, {0, -1}, {1, 0}, {-1, 0}, // Horizontal and Vertical
            {1, 1}, {-1, -1}, {1, -1}, {-1, 1} // Diagonal directions
        };
        int count = 0;

        for (int rows = 0; rows < row; rows++) {
            for (int cols = 0; cols < col; cols++) {
                for (int[] dir : directions) {
                    if (searchWord(board, rows, cols, word, dir[0], dir[1])) {
                        System.out.println("Found at: (" + rows + ", " + cols + ") in direction (" + dir[0] + ", " + dir[1] + ")");
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static boolean searchWord(char[][] board, int row, int col, String word, int dirX, int dirY) {
        int len = word.length();
        int rows = board.length;
        int cols = board[0].length;

        for (int i = 0; i < len; i++) {
            int newRow = row + i * dirX;
            int newCol = col + i * dirY;

            if (!inBounds(newRow, newCol, rows, cols) || board[newRow][newCol] != word.charAt(i)) {
                return false;
            }
        }
        return true;  // Return true when the word is found
    }

    public static boolean inBounds(int x, int y, int rows, int cols) {
        return x >= 0 && x < rows && y >= 0 && y < cols;
    }
}

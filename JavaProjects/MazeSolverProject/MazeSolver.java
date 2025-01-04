// The MazeSolver.java program reads-in a maze from a file (using the filename as the input), solves it and prints it out cleanly.
// Prints ERROR with a small explanation for various error cases.
// Has 2 classes - MazeSolver & Maze.
// MazeSolver has the main method and just reads and prints the solved maze with an input error check.
// Maze has the constructor - reads in the maze, solves it and prints it out. Contains multiple error checks including one for maze being unsolvable.
// Author: Arnav Khetan
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.util.Scanner;

public class MazeSolver {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("ERROR: No filename provided."); // Error if filename not provided. A system error will also be given if incorrect file name is entered
            return;
        }
        String fileName = args[0];
        Maze maze;

        try {
            maze = new Maze(fileName);
            maze.solve(); // Calling the solve method to solve the maze
            maze.print(); // Calling the print method to print the solved method (unless the maze is unsolvable)
        } catch (FileNotFoundException e) { // Catching error in 2 places but only printing the error statement once
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

class Maze {
    private String[] grid;
    private int rows;
    private int columns;
    private int startRow, startCol; // Creation of the variables required for the solving of the maze
    private boolean[][] visited;

    public Maze(String fileName) throws IOException {
        readMaze(fileName); // Reading in the file
    }

    private void readMaze(String fileName) throws IOException {
        try (Scanner scanner = new Scanner(new FileReader(fileName))) {
            int lineCount = 0;
            int Scount = 0;
            int Ecount = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (lineCount == 0) {
                    columns = line.length();
                } else if (line.length() != columns) { // Each line should have the same number of characters in order to be a rectangle
                    throw new IOException("ERROR: Maze is not a rectangle.");
                }

                for (char c : line.toCharArray()) {
                    if (c == 'S') {
                        Scount++;
                        startRow = lineCount;
                        startCol = line.indexOf('S');
                    }
                    if (c == 'E') {
                        Ecount++; // Keeping track of the number of 'E's read-in
                    }
                    if (c != 'S' && c != 'E' && c != 'O' && c != '-') {
                        throw new IOException("ERROR: Characters that are not allowed detected.");
                    }
                }
                lineCount++;
            }
            if (Scount > 1) throw new IOException("ERROR: Too many 'S'.");
            if (Ecount > 1) throw new IOException("ERROR: Too many 'E'.");
            if (Scount < 1) throw new IOException("ERROR: No 'S'."); // Error checks
            if (Ecount < 1) throw new IOException("ERROR: No 'E'.");

            rows = lineCount;
        } catch (FileNotFoundException e) {
        	System.out.println("ERROR: File not found - " + fileName + ".");    
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }

        // Creating a grid to better understand the dimensions of the maze
        grid = new String[rows];
        try (Scanner scanner = new Scanner(new FileReader(fileName))) {
            for (int i = 0; i < rows; i++) {
                grid[i] = scanner.nextLine();
            }
        }
    }

    public void solve() { 
        visited = new boolean[rows][columns];
        if (!findPath(startRow, startCol)) {
            System.out.println("ERROR: Maze cannot be solved."); // If there is no path from S to E, then the maze cannot be solved and this error message will be printed
            System.exit(0); 
        }
    }

    private boolean findPath(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= columns) return false;
        if (grid[row].charAt(col) == '-' || visited[row][col]) return false;
        if (grid[row].charAt(col) == 'E') return true; // Checking the boundary and path

        // Avoiding changing anything but 'O' to 'X'
        if (grid[row].charAt(col) == 'O') {
            markPath(row, col, 'X');
        }

        // Marking as visited (for double-backing and only using each junction once)
        visited[row][col] = true;

        // Right-hand rule: try moving in all directions
        int[][] directions = { {0, 1}, {1, 0}, {0, -1}, {-1, 0} }; // right, down, left, up
        for (int[] d : directions) {
            if (findPath(row + d[0], col + d[1])) return true;
        }

        // Backtracking if no path found from current character
        if (grid[row].charAt(col) == 'X') {
            markPath(row, col, 'O');
        }
        return false;
    }

    private void markPath(int row, int col, char marker) { // Marks the path
        char[] rowArray = grid[row].toCharArray();
        rowArray[col] = marker;
        grid[row] = new String(rowArray);
    }

    public void print() { // Print method
        for (int i = 0; i < grid.length; i++) {
            System.out.print(grid[i]);
            if (i < grid.length - 1) {
                System.out.println();
            }
        }
    }
}


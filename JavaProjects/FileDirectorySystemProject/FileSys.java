// The program creates a object-based file directory system based on the inputs and performs the corresponding commands.
// There is only one class - FileSys but loads of methods. Each command also has its own method. 
// Filename: FileSys.java
// Author: Arnav Khetan

import java.util.ArrayList; // Imports, this one is used for the files and directories
import java.util.Scanner; // To read in inputs

public class FileSys {

    // Represents files and directories
    static class FileNode {
        String name;
        boolean isDirectory;
        String content; // For files
        ArrayList<FileNode> children; // List of children (for directories)
        FileNode parent; // Reference to parent directory

        FileNode(String name, boolean isDirectory, FileNode parent) {
            this.name = name;
            this.isDirectory = isDirectory;
            this.content = isDirectory ? null : "";
            this.children = isDirectory ? new ArrayList<>() : null;
            this.parent = parent;
        }
    }

    private static FileNode root = new FileNode("/", true, null);
    private static FileNode currentDirectory = root;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("prompt> "); // Prompt required each time
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) continue; // If no command entered, its ignored and the program waits for the next command

            String[] parts = input.split("\\s+"); // Splitting the input into parts
            String command = parts[0];
            String argument = parts.length > 1 ? parts[1] : null;

            switch (command) { // Massive switch command to check for the commands and alternate between them based on what is sent. Includes error check
                case "mkdir":
                    if (argument != null) mkdir(argument);
                    else System.out.println("ERROR: Missing argument for 'mkdir'.");
                    break;
                case "create":
                    if (argument != null) create(argument, scanner);
                    else System.out.println("ERROR: Missing argument for 'create'.");
                    break;
                case "cat":
                    if (argument != null) cat(argument);
                    else System.out.println("ERROR: Missing argument for 'cat'.");
                    break;
                case "rm":
                    if (argument != null) rm(argument);
                    else System.out.println("ERROR: Missing argument for 'rm'.");
                    break;
                case "rmdir":
                    if (argument != null) rmdir(argument);
                    else System.out.println("ERROR: Missing argument for 'rmdir'.");
                    break;
                case "cd":
                    if (argument != null) cd(argument);
                    else System.out.println("ERROR: Missing argument for 'cd'.");
                    break;
                case "ls":
                    ls();
                    break; // Some of these require arguments, some don't
                case "du":
                    System.out.println(du(currentDirectory));
                    break;
                case "pwd":
                    pwd();
                    break;
                case "find":
                    if (argument != null) find(argument);
                    else System.out.println("ERROR: Missing argument for 'find'.");
                    break;
                case "exit":
                    scanner.close();
                    return;
                default:
                    System.out.println("ERROR: Invalid command."); // Error check for wrong command given as input
            }
        }
    }

    public static void mkdir(String name) {
        if (existsInCurrentDirectory(name)) {
            System.out.println("ERROR: File or directory with this name already exists."); // Directory creation
            return;
        }
        FileNode newDir = new FileNode(name, true, currentDirectory);
        currentDirectory.children.add(newDir);
    }

    public static void create(String name, Scanner scanner) {
        if (existsInCurrentDirectory(name)) { // File creation
            System.out.println("ERROR: File or directory with this name already exists.");
            return;
        }
        FileNode newFile = new FileNode(name, false, currentDirectory);
        currentDirectory.children.add(newFile);
        newFile.content = "";

        while (true) {
            String line = scanner.nextLine();
            if (line.contains("~")) { // Stops reading in input from keyboard after ~ is sent
                newFile.content += line.substring(0, line.indexOf("~"));
                break;
            }
            newFile.content += line + "\n";
        }
    }

    public static void cat(String name) {
        FileNode file = findInCurrentDirectory(name, false); // checks for file input, if not there then error
        if (file == null) {
            System.out.println("ERROR: File not found.");
        } else {
            System.out.println(file.content);
        }
    }

    public static void rm(String name) {
        for (int i = 0; i < currentDirectory.children.size(); i++) {
            FileNode child = currentDirectory.children.get(i); // Checks for a file in that same directory and if there then it deletes it otherwise file not found
            if (child.name.equals(name) && !child.isDirectory) {
                currentDirectory.children.remove(i);
                return;
            }
        }
        System.out.println("ERROR: File not found.");
    }

    public static void rmdir(String name) {
        for (int i = 0; i < currentDirectory.children.size(); i++) {
            FileNode child = currentDirectory.children.get(i);
            if (child.name.equals(name) && child.isDirectory) { // Same logic as rm but for directory
                currentDirectory.children.remove(i);
                return;
            }
        }
        System.out.println("ERROR: Directory not found.");
    }

    public static void cd(String path) {
        String[] components = path.split("/");
        FileNode temp = path.startsWith("/") ? root : currentDirectory;

        for (String component : components) {
            if (component.isEmpty()) continue;
            if (component.equals("..")) {
                temp = (temp.parent != null) ? temp.parent : temp; // cd will check for multiple paths in one argument and will always stop shifting paths at the last path entered
            } else {
                temp = findChild(temp, component, true);
                if (temp == null) {
                    System.out.println("ERROR: Directory not found.");
                    return;
                }
            }
        }
        currentDirectory = temp;
    }

    public static void ls() {
        // Sort children alphabetically by name
        currentDirectory.children.sort((a, b) -> a.name.compareToIgnoreCase(b.name)); // Need to print out the file/directory names through ls using alphabetical order

        // Print sorted contents with (*) for directories
        for (FileNode child : currentDirectory.children) {
            System.out.println(child.name + (child.isDirectory ? " (*)" : ""));
        }
    }

    public static int du(FileNode node) {
        if (!node.isDirectory) return node.content.length(); // Checks for the number of characters in files and prints them out. Depends on the path where this command is being entered
        int size = 0;
        for (FileNode child : node.children) {
            size += du(child);
        }
        return size;
    }

    public static void pwd() {
        String path = "";
        FileNode temp = currentDirectory;

        while (temp != null && temp != root) {
            path = "/" + temp.name + path;
            temp = temp.parent;
        }

        path = "/" + path; // Ensure a single `/` for root. Was getting an issue where it would send multiple / for the root directory when this and find was used (not directly related to find but find had the same error)
        System.out.println(path.replaceAll("//", "/"));
    }

    public static void find(String name) {
        findRecursive(currentDirectory, name, getAbsolutePath(currentDirectory));
    }

    private static void findRecursive(FileNode node, String name, String currentPath) {
        if (node.name.equals(name)) {
            System.out.println(currentPath.replaceAll("//", "/"));
        }

        if (node.isDirectory) {
            for (FileNode child : node.children) {
                String childPath = currentPath.equals("/") ? "/" + child.name : currentPath + "/" + child.name;
                findRecursive(child, name, childPath);
            }
        }
    }

    private static String getAbsolutePath(FileNode node) {
        String path = "";
        FileNode temp = node;

        while (temp != null && temp != root) { // Using this to get the full path of any file/directory since its required for the find command
            path = "/" + temp.name + path;
            temp = temp.parent;
        }

        return path.isEmpty() ? "/" : path; // Special case for root
    }

    private static boolean existsInCurrentDirectory(String name) {
        return findInCurrentDirectory(name, true) != null || findInCurrentDirectory(name, false) != null;
    }

    private static FileNode findInCurrentDirectory(String name, boolean isDirectory) {
        for (FileNode child : currentDirectory.children) {
            if (child.name.equals(name) && child.isDirectory == isDirectory) {
                return child;
            }
        }
        return null;
    }

    private static FileNode findChild(FileNode node, String name, boolean isDirectory) {
        for (FileNode child : node.children) {
            if (child.name.equals(name) && child.isDirectory == isDirectory) {
                return child;
            }
        }
        return null;
    }
}

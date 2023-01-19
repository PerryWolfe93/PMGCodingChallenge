import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner scanner;
    // Temporary storage for user-entered paths to be used for reading files
    static ArrayList<Path> paths = new ArrayList<>();
    // Temporary storage for data that has been read but not yet written
    static ArrayList<Data> dataSet = new ArrayList<>();
    // Placeholder for Path before it is sent to the paths ArrayList
    static Path pathHolder;

    public static void main(String[] args) {

        scanner = new Scanner(System.in);
        int choice;

        // Prompts user to enter CSV paths (minimum 2)
        System.out.println("Enter the first CSV file path");
        // Loops until a valid path is entered
        do {
            pathHolder = FileSystems.getDefault().getPath(scanner.nextLine());
        } while(invalidPath(pathHolder));

        System.out.println("Enter the second CSV file path");
        do {
            pathHolder = FileSystems.getDefault().getPath(scanner.nextLine());
        } while(invalidPath(pathHolder));

        // If more than 2 CSV files need to be merged, prompts user for more CSV files,
        // Else merges the files already specified
        do {
            System.out.println("1. Add another CSV file");
            System.out.println("2. Merge CSV files");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice) {
                case 1 -> addCSV();
                case 2 -> mergeCSV(paths);
            }
        } while(choice == 1);
    }

    // Check to see if the path is valid and is a .csv file
    public static boolean invalidPath(Path path) {
        if(!path.toFile().exists()) {
            System.out.println("Invalid path. File does not exist.");
            return true;
        } else if(!path.toFile().getName().contains(".csv")) {
            System.out.println("Invalid path. Please enter a path to a .csv file");
            return true;
        }
        paths.add(path);
        return false;
    }

    // Method used for prompting user for more CSV files
    public static void addCSV() {
        System.out.println("Enter the CSV file path");
        do {
            pathHolder = FileSystems.getDefault().getPath(scanner.nextLine());
        } while(invalidPath(pathHolder));
    }

    public static void mergeCSV(ArrayList<Path> paths) {

        String targetFileName;
        String targetDirectory;

        // Prompts user for file name and save location
        System.out.println("What name would you like to save this file as?");
        do {
            targetFileName = scanner.nextLine() + ".csv";
        } while (!isValidFilename(targetFileName));

        System.out.println("Where would you like to save the file?");
        do {
            pathHolder = FileSystems.getDefault().getPath(scanner.nextLine());
            targetDirectory = pathHolder + "\\";
        } while(!isValidDirectory(pathHolder));

        // Loops through all paths that user has specified, reading in data and writing it to a new CSV file
        for(Path path : paths) {

            // Try/Catch block to catch IOExceptions
            try {
                // Creates a file from the path and passes it to a file reader, skipping the first line
                File file = path.toFile();
                scanner = new Scanner(new BufferedReader(new FileReader(file)));
                scanner.nextLine();

                // While loop iterates through CSV file, splitting the data up and temporarily saving it as a Data object
                while(scanner.hasNextLine()) {

                    String[] line = scanner.nextLine().split(",");
                    Data data = new Data(line[0], line[1], file.getName());
                    dataSet.add(data);
                }

                // Creates a file writer with the specified save location and file name
                // Adds column names to the first line
                try {
                    BufferedWriter bw = new BufferedWriter(new FileWriter(targetDirectory + targetFileName, true));

                    if(path.equals(paths.get(0))) {
                        bw.write("email_hash,category,filename\n");
                    }

                    // For loop used to iterate through temporary array list while writing data to new CSV file
                    for(Data data : dataSet) {
                        bw.write(data.getHash() + "," + data.getCategory() + "," + data.getFilename() + "\n");
                    }

                    // Closes the writer and clears the array list
                    bw.close();
                    dataSet.clear();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Closes the scanner if it is not empty
                if(scanner != null) {
                    scanner.close();
                }
            }
        }
    }

    public static boolean isValidFilename(String targetFilename) {
        if(targetFilename.matches("^[A-za-z0-9.]{1,255}$*")) {
            return true;
        }
        System.out.println("Invalid filename. Please use only alphanumeric characters");
        return false;
    }

    public static boolean isValidDirectory(Path checkPath) {
        if(checkPath.toFile().isDirectory()) {
            return true;
        }
        System.out.println("Invalid directory. Please enter an existing directory path");
        return false;
    }
}

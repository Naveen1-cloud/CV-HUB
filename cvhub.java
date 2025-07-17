import java.io.*;
import java.nio.file.Paths;
import java.util.*;

class User {
  // Private fields to store the user's information
  private String username; // Stores the user's username
  private String email; // Stores the user's email address
  private String password; // Stores the user's password

  // Constructor to initialize the User object with given values
  User(String username, String email, String password) {
    this.username = username; // Assign the provided username to the username field
    this.email = email; // Assign the provided email to the email field
    this.password = password; // Assign the provided password to the password field
  }

  // Getter method for the username
  public String getUsername() {
    return username; // Returns the username value
  }

  // Getter method for the email
  public String getEmail() {
    return email; // Returns the email value
  }

  // Getter method for the password
  public String getPassword() {
    return password; // Returns the password value
  }

  // Setter method to change the username
  public void setUsername(String username) {
    this.username = username; // Sets the username field to the new value
  }

  // Setter method to change the email
  public void setEmail(String email) {
    this.email = email; // Sets the email field to the new value
  }

  // Setter method to change the password
  public void setPassword(String password) {
    this.password = password; // Sets the password field to the new value
  }

}

class Repository {
  private String name; // Name of the repository
  private Map<String, String> filePaths; // A map to store file paths and their content
  private List<String> commitHistory; // A list to track commit history

  // Constructor to initialize repository name, file map, and commit history
  public Repository(String name) {
    this.name = name; // Initialize repository name
    this.filePaths = new HashMap<>(); // Initialize the map for storing file paths and content
    this.commitHistory = new ArrayList<>(); // Initialize the commit history list
  }

  // Getter for the repository name
  public String getName() {
    return name; // Return the repository name
  }

  // Get the set of filenames stored in the repository
  public Set<String> getFileNames() {
    return filePaths.keySet(); // Return the keys of the map, which represent the filenames
  }

  // Add a file to the repository
  public void addFile(String filePath, String commitMessage) {
    try {
      // Create a File object from the provided file path
      File file = new File(filePath);
      if (file.exists() && file.isFile()) { // Check if the file exists and is a valid file
        // Read the content of the file
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
          String line;
          while ((line = reader.readLine()) != null) {
            content.append(line).append("\n"); // Append each line of the file
          }
        }
        // Store the file content in the filePaths map
        filePaths.put(filePath, content.toString());
        // Log the commit history
        commitHistory.add("Added file: " + filePath + " | Commit: " + commitMessage);
      } else {
        System.out.println("\n\t\t\t\t\t\t\t\t\t   File does not exist at the provided path."); // If file doesn't exist
      }
    } catch (IOException e) {
      System.out.println("Error reading the file: " + e.getMessage()); // Handle potential I/O exceptions
    }
  }

  // Remove a file from the repository by its filename
  public void removeFile(String fileName, String commitMessage) {
    // Search for the file by filename (not by full path)
    String filePathToRemove = null;
    for (String filePath : filePaths.keySet()) {
      if (filePath.endsWith(fileName)) { // Match by filename
        filePathToRemove = filePath; // Found the file path to remove
        break;
      }
    }

    if (filePathToRemove != null) {
      // Remove the file from the map using its full path
      filePaths.remove(filePathToRemove);
      // Log the commit history
      commitHistory.add("Removed file: " + filePathToRemove + " | Commit: " + commitMessage);
      System.out.println("File removed successfully.");
    } else {
      System.out.println("File not found in the repository."); // If the file isn't found
    }
  }

  // Get the commit history of the repository
  public List<String> getCommitHistory() {
    return commitHistory; // Return the list of commit history entries
  }

  // Open and view the content of a file by its filename
  public String openFile(String fileName) {
    for (String filePath : filePaths.keySet()) {
      if (filePath.endsWith(fileName)) { // Search for the file by filename
        try {
          // Read the content of the file at the stored path
          File file = new File(filePath);
          if (file.exists() && file.isFile()) { // Ensure the file exists
            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
              String line;
              while ((line = reader.readLine()) != null) {
                content.append(line).append("\n"); // Append the content of the file
              }
            }
            return content.toString(); // Return the file content as a string
          } else {
            return "File does not exist at the path: " + filePath; // If file doesn't exist
          }
        } catch (IOException e) {
          return "Error reading file: " + e.getMessage(); // Handle any I/O exceptions
        }
      }
    }
    return "File not found in repository."; // If the file isn't found in the repository
  }
}

public class CvHub {
  // Scanner object for user input
  static Scanner in = new Scanner(System.in);
  // The user object, which will hold user information
  static User user = null;
  // List of repositories
  static List<Repository> repositories = new ArrayList<>();

  // Color codes for text formatting in terminal
  public static final String RESET = "\u001B[0m"; // Reset color
  public static final String RED = "\u001B[31m"; // Red text
  public static final String GREEN = "\u001B[32m"; // Green text
  public static final String YELLOW = "\u001B[33m"; // Yellow text
  public static final String BLUE = "\u001B[34m"; // Blue text
  public static final String PURPLE = "\u001B[35m"; // Purple text
  public static final String CYAN = "\u001B[36m"; // Cyan text
  public static final String WHITE = "\u001B[37m"; // White text
  public static final String BLACK = "\u001B[30m";
  // Background Colors
  public static final String BG_RED = "\u001B[41m";
  public static final String BG_GREEN = "\u001B[42m";
  public static final String BG_YELLOW = "\u001B[43m";
  public static final String BG_BLUE = "\u001B[44m";
  public static final String BG_WHITE = "\u001B[47m";
  // Text Styles
  public static final String BOLD = "\u001B[1m";
  public static final String UNDERLINE = "\u001B[4m";

  public static void main(String[] args) {
    int total = 20;

    String green = "\u001B[32m";
    String reset = "\u001B[0m"; // Reset color

    for (int i = 0; i <= total; i++) {
      int percentage = (i * 100) / total;
      StringBuilder bar = new StringBuilder("\r\t\t\t\t\t\t\t\t\t[");

      // Build the progress bar
      for (int j = 0; j < total; j++) {
        if (j < i) {
          bar.append(green + "#" + reset); // Green colored progress
        } else {
          bar.append(" ");
        }
      }

      bar.append("] " + percentage + "%");
      System.out.print(bar.toString()); // Print the progress bar

      try {
        Thread.sleep(100); // Simulate task time
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    System.out.print("\r" + new String(new char[100]).replace('\0', ' ') + "\r");

    System.out.println("\n\t\t\t\t\t\t\t\t      _______________________");
    System.out.println("\t\t\t\t\t\t\t\t     |                       |");
    System.out.println("\t\t\t\t\t\t\t\t     |                       |");
    System.out.println("\t\t\t\t\t\t\t\t     |    CVHUB TERMINAL     |");
    System.out.println("\t\t\t\t\t\t\t\t     |   >_ Welcome to CVHUB |");
    System.out.println("\t\t\t\t\t\t\t\t     |                       |");
    System.out.println("\t\t\t\t\t\t\t\t     |_______________________|");

    System.out.println("\t\t\t\t\t\t\t\t            |" + BG_WHITE + "       " + RESET + "|");
    System.out.println("\t\t\t\t\t\t\t\t            |" + BG_WHITE + "       " + RESET + "|");
    System.out.println("\t\t\t\t\t\t\t\t            |" + BG_WHITE + "_______" + RESET + "|");

    System.out.println("\t\t\t\t\t\t\t\t    /=======================\\");
    System.out.println("\t\t\t\t\t\t\t\t   | [ ] [ ] [ ] [ ] [ ] [ ] |");
    System.out.println("\t\t\t\t\t\t\t\t   | [ ] [ ] [ ] [ ] [ ] [ ] |");
    System.out
        .println("\t\t\t\t\t\t\t\t   | [" + BG_WHITE + BLACK + "_________SPACE_______" + RESET + "] |");
    System.out.println("\t\t\t\t\t\t\t\t    \\=======================/\n");

    System.out.println("\t\t\t\t\t\t            " + RED + " ****   *     *   " + RESET + "*     *   *     *  ******");
    System.out.println("\t\t\t\t\t\t            " + RED + "*       *     *   " + RESET + "*     *   *     *  *     *");
    System.out.println("\t\t\t\t\t\t            " + RED + "*       *     *   " + RESET + "*     *   *     *  *     *");
    System.out.println("\t\t\t\t\t\t            " + RED + "*       *     *   " + RESET + "* * * *   *     *  ******");
    System.out.println("\t\t\t\t\t\t            " + RED + "*        *   *    " + RESET + "*     *   *     *  *     *");
    System.out.println("\t\t\t\t\t\t            " + RED + "*         * *     " + RESET + "*     *   *     *  *     *");
    System.out.println("\t\t\t\t\t\t            " + RED + " ****      *      " + RESET + "*     *     * *    ******");
    System.out.println();

    boolean running = true;

    while (running) {
      int choice = -1;

      while (choice < 1 || choice > 3) {
        System.out
            .println(
                "\n\t\t\t\t\t\t" + YELLOW + "            ------------ Select the option ------------" + RESET);
        System.out.println();
        System.out.println(CYAN + "\t\t\t\t\t\t\t\t\t   1. Join the Hub\n");
        System.out.println("\t\t\t\t\t\t\t\t\t   2. Enter the Hub\n");
        System.out.println("\t\t\t\t\t\t\t\t\t   3. Leave the Hub\n" + RESET);
        System.out.print("\t\t\t\t\t\t\t\t\t  Enter your choice: ");

        try {
          choice = Integer.parseInt(in.nextLine());
        } catch (NumberFormatException e) {
          System.out
              .println("\n\t\t\t\t\t\t\t" + RED + "Invalid input. Please enter a number between 1 and 3\n" + RESET);
          continue;
        }

        if (choice < 1 || choice > 3) {
          System.out.println("Invalid option. Please enter a number between 1 and 3.");
        }
      }

      switch (choice) {
        case 1:
          signup();
          break;
        case 2:
          signin();
          break;
        case 3:
          System.out.println();
          System.out.println(GREEN + "\t\t\t\t\t\t            ------------   Thank You!  ------------");

          System.out.println("\n\t\t\t\t\t\t            ----------- Committer: You  ------------");
          System.out.println("\n\t\t\t\t\t\t            ----------- Repository: CvHub ------------");
          System.out.println("\n\t\t\t\t\t\t             -> We truly appreciate your support!");
          System.out.println("\n\t\t\t\t\t\t  -> Keep committing, keep pushing, and keep building amazing" + RESET);
          running = false; // Exit the loop
          break;
      }
    }

  }

  public static void signup() {
    // Start signup process
    System.out.println(YELLOW + "\n\t\t\t\t\t\t            ------------   Signup process  ------------" + RESET);

    // Username input loop
    boolean runningU = true;
    String username = "";
    while (runningU) {
      System.out.print(RED + "\n\t\t\t\t\t\t            Enter username: " + RESET);
      username = in.nextLine();
      if (validUsername(username))
        runningU = false; // Valid username
      else
        System.out.println(BOLD + "\n\t\t\t\t\t\t\t                 " + BG_RED + "Enter a valid username:" + RESET);
    }

    // Email input loop
    boolean runningE = true;
    String email = "";
    while (runningE) {
      System.out.print(RED + "\n\t\t\t\t\t\t            Enter email: " + RESET);
      email = in.nextLine();
      if (validEmail(email))
        runningE = false; // Valid email
      else
        System.out.println(BOLD + "\t\t\t\t\t\t\t             " + BG_RED + "Please enter a valid email" + RESET);
    }

    // Password input loop
    boolean runningP = true;
    String password = "";
    while (runningP) {
      System.out.print(RED + "\n\t\t\t\t\t\t            Enter password: " + RESET);
      password = in.nextLine();
      if (validPassword(password))
        runningP = false; // Valid password
      else
        System.out.println("\n\t\t\t\t\t\t            " + BG_RED + "Please enter a valid password" + RESET);
    }

    // Store user and confirm signup
    user = new User(username, email, password);
    System.out.println(GREEN + "\n\t\t\t\t\t\t\t\t         Signup successful!" + RESET);
  }

  public static void signin() {
    // Start signin process
    System.out.println(GREEN+"\n\t\t\t\t\t\t            Signin process..."+RESET);

    // Check if user is registered
    if (user == null) {
      System.out.println(RED+"\n\t\t\t\t\t\t            No user found. Please sign up first."+RESET);
      return;
    }

    // Verify email
    System.out.print(YELLOW+"\n\t\t\t\t\t\t            Enter registered email: ");
    String email = in.next();
    if (email.equals(user.getEmail())) {
      // Verify password
      System.out.print("\n\t\t\t\t\t\t            Enter registered password: ");
      in.nextLine(); // Consume newline
      String password = in.nextLine();
      if (password.equals(user.getPassword())) {
        System.out.println("\n\t\t\t\t\t\t            Signin successful!"+RESET);
      } else {
        System.out.println(BG_RED+"\n\t\t\t\t\t\t            Incorrect password!");
        return;
      }
    } else {
      System.out.println("\n\t\t\t\t\t\t            No account found with this email."+RESET);
      return;
    }

    // Welcome the user and display options
    System.out.println(GREEN+"\n\t\t\t\t\t\t            Welcome, " + user.getUsername() + "!"+RESET);
    boolean running = true;
    while (running) {

      int choice = -1;
      while (choice < 1 || choice > 5) {

        System.out.println(YELLOW + "\n\t\t\t\t\t\t            --------------- Hub Menu --------------" + RESET);
        System.out.println();
        System.out.println(PURPLE + "\t\t\t\t\t\t\t\t\t   1. Create Repository\n");
        System.out.println("\n\t\t\t\t\t\t\t\t\t   2. View Repositories\n");
        System.out.println("\n\t\t\t\t\t\t\t\t\t   3. Delete Repository\n");
        System.out.println("\n\t\t\t\t\t\t\t\t\t   4. Account Settings\n");
        System.out.println("\n\t\t\t\t\t\t\t\t\t   5. Exit\n" + RESET);

        System.out.print(YELLOW + "\n\t\t\t\t\t\t            Please select an option (1-5): " + RESET);
        try {
          choice = in.nextInt();
          in.nextLine();
          if (choice < 1 || choice > 5) {
            System.out.println(
                BG_RED + "\n\t\t\t\t\t\t            Invalid choice! Please enter a number between 1 and 5" + RESET);
          }
        } catch (InputMismatchException e) {
          System.out.println(
              BG_RED + "\n\t\t\t\t\t\t            Invalid input! Please enter a valid number between 1 and 5" + RESET);
          in.nextLine();
        }
      }

      switch (choice) {
        case 1:
          createRepo();
          break;
        case 2:
          viewRepos();
          break;
        case 3:
          deleteRepository(repositories);
          break;
        case 4:
          accountSettings();
          break;
        case 5:
          running = false;
          break;
        default:
          System.out.println(BG_RED + "\n\t\t\t\t\t\t            Invalid choice! Try again." + RESET);
      }
    }
  }

  public static void createRepo() {
    // Start repository creation process
    System.out.println(PURPLE + "\n\t\t\t\t\t\t\t\t\t    Repository:" + RESET);

    // Loop until valid repository name is provided
    while (true) {
      System.out.println(PURPLE + "\n\t\t\t\t\t\t\t\t\t   Enter repository name: " + RESET);
      String repoName = in.nextLine();

      // Ensure the repository name is not empty
      if (repoName.isEmpty()) {
        System.out.println(BG_RED + "\n\t\t\t\t\t\t\t\t\t   Repository name cannot be empty. Try again." + RESET);
        continue;
      }

      // Check if repository already exists in memory
      if (repoExistsInMemory(repoName)) {
        System.out.println(PURPLE + "\n\t\t\t\t\t\t\t\t\t   Repository already exists. Try a different name.");
        continue;
      }

      // Add new repository to the list
      repositories.add(new Repository(repoName));

      System.out
          .println(PURPLE + "\t\t\t\t\t\t\t\t\t   Repository '" + YELLOW + repoName + RESET + "'" + PURPLE
              + "created successfully!" + RESET);
      break; // Exit the loop after successful creation
    }
  }

  private static boolean repoExistsInMemory(String repoName) {
    // Check if repository with the given name exists in the list
    for (Repository repo : repositories) {
      if (repo.getName().equalsIgnoreCase(repoName)) {
        return true; // Repository found
      }
    }
    return false; // Repository not found
  }

  public static void viewRepos() {
    // Display message to view repositories
    System.out.println(GREEN + "\n\t\t\t\t\t\t\t\t\t   View Repositories:" + RESET);

    // Check if repositories list is empty
    if (repositories.isEmpty()) {
      System.out.println(GREEN + "\n\t\t\t\t\t\t\t\t\t   No repositories found." + RESET);
    } else {
      // List all repositories
      for (int i = 0; i < repositories.size(); i++) {
        System.out.println(YELLOW+"\n\t\t\t\t\t\t\t\t\t   "+(i + 1) + ". " + repositories.get(i).getName()+RESET);
      }
      int repoIndex = -1; // Store user selection
      boolean validInput = false; // Flag to track valid input

      while (!validInput) { // Keep running until a valid input is entered
        try {
          System.out.print(GREEN + "\n\t\t\t\t\t\t\t\t\t   Select a repository by number: " + RESET);
          repoIndex = Integer.parseInt(in.nextLine().trim()) - 1; // Adjust for zero-based index

          if (repoIndex >= 0 && repoIndex < repositories.size()) {
            validInput = true; // Valid input, exit loop
            Repository selectedRepo = repositories.get(repoIndex);
            viewRepoActions(selectedRepo);
          } else {
            System.out.println(BG_RED
                + "\n\t\t\t\t\t\t\t\t\t   Invalid selection. Please enter a valid repository number." + RESET);
          }
        } catch (NumberFormatException e) {
          System.out.println(BG_RED + "\n\t\t\t\t\t\t\t\t\t   Invalid input. Please enter a number." + RESET);
        }
      }

    }
  }

  public static void deleteRepository(List<Repository> repositories) {
    // Show available repositories to delete
    System.out.println(PURPLE+"\n\t\t\t\t\t\t\t\t\t   Select a repository to delete:"+RESET);

    // Check if there are repositories to delete
    if (repositories.isEmpty()) {
      System.out.println(BG_RED+"\n\t\t\t\t\t\t\t\t\t   No repositories available to delete."+RESET);
      return;
    }

    // List repositories with index
    for (int i = 0; i < repositories.size(); i++) {
      System.out.println((i + 1) + ". " + repositories.get(i).getName());
    }

    int repoChoice = -1; // Store user selection
    boolean validInput = true; // Track if input is valid

    // Loop until valid repository selection
    while (validInput) {
      try {
        System.out.print("\n\t\t\t\t\t\t\t\t\t   Enter the repository number to delete: ");
        repoChoice = Integer.parseInt(in.nextLine()); // Parse input

        if (repoChoice >= 1 && repoChoice <= repositories.size()) {
          validInput = false; // Valid input, exit loop
        } else {
          System.out.println(BG_RED+"\n\t\t\t\t\t\t\t\t\t   Invalid selection. Please enter a valid repository number.");
        }
      } catch (NumberFormatException e) {
        System.out.println("\n\t\t\t\t\t\t\t\t\t   Invalid input. Please enter a number."+RESET);
      }
    }

    // Get selected repository and confirm deletion
    Repository selectedRepo = repositories.get(repoChoice - 1);
    String repoName = selectedRepo.getName();
    String confirmation = "";

    // Loop until valid confirmation (y/n)
    while (true) {
      System.out.print(RED+"\n\t\t\t\t\t\t\t\t\t   Are you sure you want to delete the repository '" + repoName + "'? (y/n): "+RESET);
      confirmation = in.nextLine().trim();

      if (confirmation.equalsIgnoreCase("y")) {
        repositories.remove(selectedRepo);
        System.out.println(YELLOW+"\n\t\t\t\t\t\t\t\t\t   Repository '" + repoName + "' deleted successfully."+RESET);
        break;
      } else if (confirmation.equalsIgnoreCase("n")) {
        System.out.println(YELLOW+"\n\t\t\t\t\t\t\t\t\t   Deletion canceled."+RESET);
        break;
      } else {
        System.out.println(BG_RED+"\n\t\t\t\t\t\t\t\t\t   Invalid input. Please enter 'y' for Yes or 'n' for No."+RESET);
      }
    }
  }

  // Display actions for a selected repository
  public static void viewRepoActions(Repository repo) {
    boolean repoActionRunning = true;

    while (repoActionRunning) {
      // Display available actions for the selected repository
      System.out.println(PURPLE+"\n\t\t\t\t\t\t\t\t\t   Select an action for repository '" + repo.getName() + "':");
      System.out.println("\n\t\t\t\t\t\t\t\t\t   1. Add File");
      System.out.println("\n\t\t\t\t\t\t\t\t\t   2. Remove File");
      System.out.println("\n\t\t\t\t\t\t\t\t\t   3. Open File");
      System.out.println("\n\t\t\t\t\t\t\t\t\t   4. View Commit History");
      System.out.println("\n\t\t\t\t\t\t\t\t\t   5. Back to Repository List"+RESET);

      int actionChoice = -1; // Initialize choice variable
      boolean validInput = false; // Track input validity

      // Loop until valid input is received
      while (!validInput) {
        try {
          System.out.println(YELLOW+"\n\t\t\t\t\t\t\t\t\t   Enter your choice: "+RESET);
          actionChoice = Integer.parseInt(in.nextLine()); // Read and parse input

          if (actionChoice >= 1 && actionChoice <= 5) {
            validInput = true; // Valid input, exit loop
          } else {
            System.out.println(BG_RED+"\t\t\t\t\t\t\t\t\t   Invalid option. Please enter a number between 1 and 5.");
          }
        } catch (NumberFormatException e) {
          System.out.println("\t\t\t\t\t\t\t\t\t   Invalid input. Please enter a number."+RESET);
        }
      }

      // Handle action based on user's input
      switch (actionChoice) {
        case 1:
          addFileToRepo(repo); // Add file to repository
          break;
        case 2:
          removeFileFromRepo(repo); // Remove file from repository
          break;
        case 3:
          openFileInRepo(repo); // Open file in repository
          break;
        case 4:
          viewCommitHistory(repo); // View commit history
          break;
        case 5:
          repoActionRunning = false; // Exit loop to go back to repository list
          break;
      }
    }
  }

  // Add a file to a repository
  public static void addFileToRepo(Repository repo) {
    // Prompt user to enter file path
    System.out.print(YELLOW+"\n\t\t\t\t\t\t\t\t\t   Enter the full file path to add: ");
    String filePath = in.nextLine();

    // Prompt user to enter commit message
    System.out.print("\n\t\t\t\t\t\t\t\t\t   Enter commit message: ");
    String commitMessage = in.nextLine();

    // Add file to repository with commit message
    repo.addFile(filePath, commitMessage);
    System.out.println("\n\t\t\t\t\t\t\t\t\t   File added successfully.");
  }

  public static boolean repoExists(String repoName) {
    // Check if the repositories file exists
    File file = new File("\n\t\t\t\t\t\t\t\t\t   repositories.txt"+RESET);
    if (!file.exists()) {
      return false;
    }

    // Read the file and check if repository name exists
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = 
      reader.readLine()) != null) {
        if (line.equalsIgnoreCase(repoName)) {
          return true; // Repository found
        }
      }
    } catch (IOException e) {
      System.out.println(BG_RED+"\n\t\t\t\t\t\t\t\t\t   Error reading repositories."+RESET);
    }

    return false; // Repository not found
  }

  // Remove a file from a repository
  public static void removeFileFromRepo(Repository repo) {
    // Display all available files in the repository
    System.out.println(GREEN+"\n\t\t\t\t\t\t\t\t\t   Available files in the repository:"+RESET);
    Set<String> fileNames = repo.getFileNames(); // Get all filenames from the repository

    if (fileNames.isEmpty()) {
      System.out.println(RED+"\n\t\t\t\t\t\t\t\t\t   No files available to remove."+RESET);
      return;
    }

    int index = 1;
    for (String fileName : fileNames) {
      System.out.println(index + ". " + fileName);
      index++;
    }

    int fileChoice = -1; // Initialize choice variable
    boolean validInput = false; // Track input validity

    // Loop until valid input is received
    while (!validInput) {
      try {
        System.out.print(PURPLE+"\n\t\t\t\t\t\t\t\t\t   Enter the file number to remove: "+RESET);
        fileChoice = Integer.parseInt(in.nextLine().trim());

        if (fileChoice >= 1 && fileChoice <= fileNames.size()) {
          validInput = true; // Valid input, exit loop
        } else {
          System.out.println(BG_RED+"\n\t\t\t\t\t\t\t\t\t   Invalid file selection. Please enter a number between 1 and " + fileNames.size() + ".");
        }
      } catch (NumberFormatException e) {
        System.out.println("\n\t\t\t\t\t\t\t\t\t   Invalid input. Please enter a valid number."+RESET);
      }
    }

    // Get the selected file name
    String selectedFileName = (String) fileNames.toArray()[fileChoice - 1];

    // Prompt for a commit message
    System.out.print(PURPLE+"\n\t\t\t\t\t\t\t\t\t   Enter commit message: "+RESET);
    String commitMessage = in.nextLine();

    // Remove the selected file from the repository
    repo.removeFile(selectedFileName, commitMessage);
    System.out.println(PURPLE+"\n\t\t\t\t\t\t\t\t\t   File '" + selectedFileName + "' removed successfully.");
  }

  public static void viewCommitHistory(Repository repo) {
    // Display commit history
    System.out.println("\n\t\t\t\t\t\t\t\t\t   Commit History:");
    for (String commit : repo.getCommitHistory()) { // Loop through commit history
      System.out.println(commit); // Print each commit
    }
  }

  // Open and view the content of a file in a repository
  public static void openFileInRepo(Repository repo) {
    // Display available files in the repository
    System.out.println("\n\t\t\t\t\t\t\t\t\t   Available files in repository '" + repo.getName() + "':"+RESET);
    Set<String> filePaths = repo.getFileNames(); // Get file paths

    // Check if repository has no files
    if (filePaths.isEmpty()) {
      System.out.println(BG_RED+"\n\t\t\t\t\t\t\t\t\t   No files available in this repository."+RESET);
      return;
    }

    // List files with indices
    int fileIndex = 1;
    for (String filePath : filePaths) {
      String fileName = Paths.get(filePath).getFileName().toString(); // Extract file name from path
      System.out.println(fileIndex + ". " + fileName);
      fileIndex++;
    }

    int fileChoice = -1; // Initialize choice variable
    boolean validInput = false; // Track input validity

    // Loop until valid input is received
    while (!validInput) {
      try {
        System.out.print(PURPLE+"\n\t\t\t\t\t\t\t\t\t   Enter the file number to open: "+RESET);
        fileChoice = Integer.parseInt(in.nextLine().trim());

        if (fileChoice >= 1 && fileChoice <= filePaths.size()) {
          validInput = true; // Valid input, exit loop
        } else {
          System.out.println(BG_RED+"\n\t\t\t\t\t\t\t\t\t   Invalid file selection. Please enter a number between 1 and " + filePaths.size() + ".");
        }
      } catch (NumberFormatException e) {
        System.out.println("\n\t\t\t\t\t\t\t\t\t   Invalid input. Please enter a valid number."+RESET);
      }
    }

    // Get the selected file path and name
    String selectedFilePath = (String) filePaths.toArray()[fileChoice - 1];
    String selectedFileName = Paths.get(selectedFilePath).getFileName().toString();

    // Open and display file content
    try {
      String content = repo.openFile(selectedFilePath);
      System.out.println(PURPLE+"\n\t\t\t\t\t\t\t\t\t   Content of the file '" + selectedFileName + "':\n"+RESET);
      System.out.println(content);
    } catch (Exception e) {
      System.out.println(BG_RED+"\n\t\t\t\t\t\t\t\t\t   Error opening file: " + e.getMessage()+RESET);
    }
  }

  public static void accountSettings() {
    while (true) { // Loop to keep showing the menu until the user exits
      // Display account settings options
      System.out.println(PURPLE+"\n\t\t\t\t\t\t\t\t\t   Account Settings:");
      System.out.println("\n\t\t\t\t\t\t\t\t\t   1. Update Username...");
      System.out.println("\n\t\t\t\t\t\t\t\t\t   2. Update Email...");
      System.out.println("\n\t\t\t\t\t\t\t\t\t   3. Update Password...");
      System.out.println("\n\t\t\t\t\t\t\t\t\t   4. Go back..."+RESET);

      int choice = -1; // Initialize choice variable
      boolean validInput = false; // Track input validity

      // Loop until valid input is received
      while (!validInput) {
        try {
          System.out.print(YELLOW+"\n\t\t\t\t\t\t\t\t\t   Enter your choice: "+RESET);
          choice = Integer.parseInt(in.nextLine().trim());

          if (choice >= 1 && choice <= 4) {
            validInput = true; // Valid input, exit loop
          } else {
            System.out.println(BG_RED+"\n\t\t\t\t\t\t\t\t\t   Invalid choice! Please enter a number between 1 and 4.");
          }
        } catch (NumberFormatException e) {
          System.out.println("\n\t\t\t\t\t\t\t\t\t   Invalid input! Please enter a valid number."+RESET);
        }
      }

      // Switch statement for handling different actions
      switch (choice) {
        case 1:
          updateUsername(); // Update username
          break;
        case 2:
          updateEmail(); // Update email
          break;
        case 3:
          updatePassword(); // Update password
          break;
        case 4:
          return; // Exit settings
        default:
          System.out.println(BG_RED+"\n\t\t\t\t\t\t\t\t\t   Invalid choice! Try again."+RESET); // This should never happen due to validation
      }
    }
  }

  public static void updateUsername() {
    System.out.println(YELLOW+"\n\t\t\t\t\t\t            ------------ Update Username ------------"+RESET);

    // Ensure user is logged in before allowing username update
    if (user == null) {
      System.out.println(RED+"\n\t\t\t\t\t\t\t\t\t   No user found. Please sign in first."+RESET);
      return; // Exit if no user is logged in
    }

    // Verify the current username by asking the user
    while (true) {
      System.out.print(PURPLE+"\n\t\t\t\t\t\t\t\t\t   Enter current username: "+RESET);
      String currentUsername = in.nextLine();

      // Check if entered current username matches the stored one
      if (currentUsername.equals(user.getUsername())) {
        break; // Proceed if username matches
      } else {
        System.out.println(BG_RED+"\n\t\t\t\t\t\t\t\t\t   Incorrect current username. Try again."+RESET);
      }
    }

    // Loop for entering a valid new username
    String newUsername;
    while (true) {
      System.out.print(PURPLE+"\n\t\t\t\t\t\t\t\t\t   Enter new username: "+RESET);
      newUsername = in.nextLine();

      // Ensure the new username is valid
      if (validUsername(newUsername)) {
        break; // Proceed if valid username is entered
      } else {
        System.out.println(BG_RED+"\n\t\t\t\t\t\t\t\t\t   Invalid username format. Try again."+RESET);
      }
    }

    // Set the new username
    user.setUsername(newUsername);
    System.out.println(GREEN+"\n\t\t\t\t\t\t\t\t\t   Username updated successfully!"+RESET); // Confirmation message
  }

  public static void updatePassword() {
    System.out.println(YELLOW+"\n\t\t\t\t\t\t            ------------ Update Password ------------"+RESET);

    // Ensure the user is logged in before updating the password
    if (user == null) {
      System.out.println(RED+"\n\t\t\t\t\t\t\t\t\t   No user found. Please sign in first."+RESET);
      return; // Exit if no user is logged in
    }

    // Loop to verify the current password
    while (true) {
      System.out.print(CYAN+"\n\t\t\t\t\t\t\t\t\t   Enter current password: "+RESET);
      String currentPassword = in.nextLine();

      // Check if entered password matches the stored one
      if (currentPassword.equals(user.getPassword())) {
        break; // Proceed if the password matches
      } else {
        System.out.println(BG_RED+"\n\t\t\t\t\t\t\t\t\t   Incorrect current password. Try again."+RESET);
      }
    }

    // Loop to validate and accept a new password
    String newPassword;
    while (true) {
      System.out.print(CYAN+"\n\t\t\t\t\t\t\t\t\t   Enter new password: "+RESET);
      newPassword = in.nextLine();

      // Ensure the new password is valid (via a predefined validation method)
      if (validPassword(newPassword)) {
        break; // Proceed if valid password is entered
      } else {
        System.out.println(BG_RED+"\n\t\t\t\t\t\t\t\t\t   Invalid password format. Try again."+RESET);
      }
    }

    // Update the user's password
    user.setPassword(newPassword);
    System.out.println(GREEN+"\n\t\t\t\t\t\t\t\t\t   Password updated successfully!"+RESET); // Success message
  }

  public static void updateEmail() {
    System.out.println(YELLOW+"\n\t\t\t\t\t\t            ------------ Update Email ------------"+RESET);

    // Ensure user is logged in before updating email
    if (user == null) {
      System.out.println(RED+"\n\t\t\t\t\t\t\t\t\t   No user found. Please sign in first."+RESET);
      return; // Exit if no user is logged in
    }

    // Loop to verify the current email
    while (true) {
      System.out.print(CYAN+"\n\t\t\t\t\t\t\t\t\t   Enter current email: "+RESET);
      String currentEmail = in.nextLine();

      // Check if entered email matches the stored one
      if (currentEmail.equals(user.getEmail())) {
        break; // Proceed if email matches
      } else {
        System.out.println(BG_RED+"\n\t\t\t\t\t\t\t\t\t   Incorrect current email. Try again."+RESET);
      }
    }

    // Loop to validate and accept a new email
    String newEmail;
    while (true) {
      System.out.print(CYAN+"\n\t\t\t\t\t\t\t\t\t   Enter new email: "+RESET);
      newEmail = in.nextLine();

      // Ensure the new email is valid (via a predefined validation method)
      if (validEmail(newEmail)) {
        break; // Proceed if valid email is entered
      } else {
        System.out.println(BG_RED+"\n\t\t\t\t\t\t\t\t\t   Invalid email format. Try again."+RESET);
      }
    }

    // Update the user's email
    user.setEmail(newEmail);
    System.out.println(GREEN+"\n\t\t\t\t\t\t\t\t\t   Email updated successfully!"+RESET); // Success message
  }

  public static boolean validEmail(String email) {

    // Check if the email is null or empty
    if (email == null || email.isEmpty()) {
      System.out.println("\n\n\t\t\t\t\t\t\t\t\t\t " + BG_RED + BOLD + "Email cannot be empty!" + RESET + "\n");
      return false; // Return false if empty
    }

    // Ensure the email ends with @gmail.com
    if (!email.endsWith("@gmail.com")) {
      System.out
          .println("\n\t\t\t\t\t\t\t         " + BG_RED + BOLD + "Email should end with @gmail.com" + RESET + "\n");
      return false; // Return false if not Gmail
    }

    // Ensure '@' is not the first character
    int atIndex = email.indexOf('@');
    if (atIndex < 1) {
      System.out.println("\n\t\t\t\t\t\t\t\t   " + BG_RED + BOLD + "'@' should not be at the start" + RESET + "\n");
      return false; // Return false if '@' is at the start
    }

    // Ensure there are no spaces in the email
    if (email.contains(" ")) {
      System.out.println("\n\t\t\t\t\t\t\t\t     " + BG_RED + BOLD + "No spaces allowed in email" + RESET + "\n");
      return false; // Return false if there are spaces
    }

    return true; // Return true if all conditions are met
  }

  public static boolean validPassword(String password) {

    // Check if password length is less than 8 characters
    if (password.length() < 8) {
      System.out.println(RED+"\n\t\t\t\t\t\t\t\t\t   Password should be at least 8 characters long.");
      return false; // Return false if password is too short
    }

    // Check if password contains at least one special symbol
    if (!containSpecialSymbols(password)) {
      System.out.println("\n\t\t\t\t\t\t\t\t\t   Password should contain at least one special character.");
      return false; // Return false if no special symbol
    }

    // Check if password contains at least one number
    if (!containNumbers(password)) {
      System.out.println("\n\t\t\t\t\t\t\t\t\t   Password should contain at least one number.");
      return false; // Return false if no number is found
    }

    // Check if password contains at least one uppercase letter
    if (!containUpperCase(password)) {
      System.out.println("\n\t\t\t\t\t\t\t\t\t   Password should contain at least one uppercase letter."+RESET);
      return false; // Return false if no uppercase letter
    }

    return true; // Return true if all conditions are satisfied
  }

  public static boolean containUpperCase(String str) {
    // Loop through each character in the string
    for (char c : str.toCharArray()) {
      // Check if the character is uppercase
      if (Character.isUpperCase(c)) {
        return true; // Return true if an uppercase letter is found
      }
    }
    return false; // Return false if no uppercase letter is found
  }

  public static boolean validUsername(String username) {
    // Check if the username is at least 5 characters long
    if (username.length() < 5) {
      System.out
          .println(
              BOLD + "\n\t\t\t\t\t\t\t    " + BG_RED + "Username should be at least 5 characters long." + RESET + "");
      return false;
    }

    // Check if the username contains special characters
    if (containSpecialSymbols(username)) {
      System.out.println("\n\t\t\t\t\t\t\t    " + BG_RED + "Username cannot contain special characters." + RESET + "");
      return false;
    }

    return true; // Valid username if it's long enough and doesn't have special characters
  }

  public static boolean containSpecialSymbols(String username) {
    // List of special characters to check against
    String specialCharacters = "!@#$%^&*()_+{}[]|\\:;\"'<>,.?/~`-=";
    boolean found = false;

    // Loop through each character in the username
    for (int i = 0; i < username.length(); i++) {
      // Check if the character exists in the specialCharacters string
      for (int j = 0; j < specialCharacters.length(); j++) {
        if (username.charAt(i) == specialCharacters.charAt(j)) {
          found = true;
          break;
        }
      }
    }
    return found; // Returns true if any special symbol is found
  }

  public static boolean containNumbers(String username) {
    boolean found = false;

    // Loop through each character in the username
    for (int i = 0; i < username.length(); i++) {
      char c = username.charAt(i);

      // Check if the character is a digit
      if (c >= '0' && c <= '9') {
        found = true; // A number is found
        break;
      }
    }

    return found; // Returns true if a number is found
  }

}

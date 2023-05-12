import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

public class PlayingMode {


    //User.csv attributes
    static ArrayList<Integer> listOfUsersNrCrt;
    static ArrayList<String> listOfPlayerNames = new ArrayList<>();
    static ArrayList<Integer> nrOfGames = new ArrayList<>();
    static DateFormat lastGamePlayed;
    static double totalTimePlayedPerUser;



    //GamesForUser.csv attributes
    static ArrayList<Integer> listOfGamesNrCrt;
    static ArrayList<String> gameID = new ArrayList<>();
    static ArrayList<Integer> gameScore = new ArrayList<>();
    static int countOfHints;
    static int countOfSteps;
    static List<String>initialMostEfficientPath;
    static ArrayList<String> myPath;

    static ArrayList<Double> listOfRuntimeOfAppPerGame;



    //Other necessary attributes
    static int numHints;
    static List<String>mostEfficientPath;
    static List<String> listOfWordsOfTheSameLength;
    static double start;
    static double endProcess;
    static  double  runtimeOfApp;
    static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    static Date obj;

    static void startGame(GraphBuilder.Graph<String> graph, List<String> list) throws Exception {
        System.out.println("<<Playing Mode>>");
        start = Instant.now().toEpochMilli();
        numHints = 3;
        countOfHints=0;
        myPath = new ArrayList<>();
        System.out.print("Username: ");
        Scanner input = new Scanner(System.in);
        String user ;
        while (true) {
            try {
                user = input.next();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid choice.");
                input.next(); // consume the invalid input
            }
        }
        System.out.println();
        listOfPlayerNames.add(user);
        System.out.print("Choose the number of letters for the words:: ");

        int numberOfLetters;
        while (true) {
            try {
                numberOfLetters = input.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid choice.");
                input.next(); // consume the invalid input
            }
        }
        listOfWordsOfTheSameLength = GameMethods.chooseTheWordsWithSpecificNumberOfLetters(list, numberOfLetters);
        String startWord;
        countOfSteps = 0;
        boolean exit = true;
        System.out.print("Choose a starting word from the dictionary:: ");
        startWord = input.next();
        do {
            String errorsForFirstWord = GameMethods.checkersForFirstWordString(list ,startWord, numberOfLetters);
            if(errorsForFirstWord == null){

                myPath.add(startWord);
                String endWord = GameMethods.getRandomWordFromList(listOfWordsOfTheSameLength);
                System.out.println("The last word will be '" + endWord + "'");
                //to be deleted later
                initialMostEfficientPath = PathFinder.findShortestPath(graph, startWord, endWord);
                while (initialMostEfficientPath.isEmpty()) {
                    System.out.println("No path found with the initial endWord. Getting a new endWord...");
                    endWord = GameMethods.getRandomWordFromList(listOfWordsOfTheSameLength);
                    System.out.println("The new end word is '" + endWord + "'");
                    initialMostEfficientPath = PathFinder.findShortestPath(graph, startWord, endWord);
                }
                mostEfficientPath = PathFinder.findShortestPath(graph, startWord, endWord);

                System.out.println("to delete later, but for now the best path is " + mostEfficientPath);
                System.out.println();
                String nextWord = " ";
                System.out.println("<<Playing Mode>>");
                System.out.println("Choose an option:");
                System.out.println("1. Enter next word");
                System.out.println("2. Get a hint");
                boolean firstIteration = true;
                while(!nextWord.equals(endWord)){
                    if (!firstIteration) {
                        System.out.println("<<Playing Mode>>");
                        System.out.println("Choose an option:");
                        System.out.println("1. Enter next word");
                        System.out.println("2. Get a hint");
                    } else {
                        firstIteration = false;
                    }
                    int choice ;
                    while (true) {
                        try {
                            System.out.print("Enter your choice--->");
                            choice = input.nextInt();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid choice.");
                            input.next(); // consume the invalid input
                        }
                    }
                    switch (choice) {
                        case 1 -> {
                            System.out.println("<<Playing Mode>>");
                            numHints = 3;
                            System.out.print("Next word:: ");
                            nextWord = input.next();
                            countOfSteps++;

                            while(true){
                                String error = GameMethods.checkersForNextWordString(list, startWord, nextWord, numberOfLetters);
                                if(error==null){
                                    if(!mostEfficientPath.get(1).equals(nextWord)){
                                        mostEfficientPath = PathFinder.findShortestPath(graph, nextWord, endWord);
                                        System.out.println(mostEfficientPath);//doar pt teste
                                    } else {
                                        mostEfficientPath.remove(0);
                                        System.out.println(mostEfficientPath);//doar pt teste
                                    }
                                    System.out.println("Word '" + nextWord + "' added to the path.");
                                    myPath.add(nextWord);
                                    startWord = nextWord;
                                    break;
                                }else{
                                    System.out.println("Error: " + error);
                                    System.out.println("Choose the next word again!");
                                    nextWord = input.next();
                                }
                            }
                        }
                        case 2 -> {
                            if (numHints > 0) {
                                //to make a repeatable method
                                if(numHints == 3){
                                    System.out.println("<<Playing Mode>>");
                                    countOfHints++;
                                    numHints--;
                                    countOfSteps++;
                                    System.out.println("You have 2 more hints left");
                                    String nextHint = mostEfficientPath.get(1);
                                    String hintForNextWord = Hints.hintNumberOne(startWord, nextHint);
                                    System.out.println("Letter that needs to be changed for " + startWord + " is " + hintForNextWord);
                                } else if (numHints == 2) {
                                    System.out.println("<<Playing Mode>>");
                                    countOfHints++;
                                    numHints--;
                                    countOfSteps++;
                                    System.out.println("You have 1 more hint left");
                                    String nextHint = mostEfficientPath.get(1);
                                    String hintForNextWord = Hints.hintNumberOne(startWord, nextHint);
                                    System.out.println("Letter that needs to be changed for " + startWord + " is " + hintForNextWord);
                                    Hints.hintNumberTwo(startWord, nextHint);
                                } else {
                                    System.out.println("<<Playing Mode>>");
                                    countOfHints++;
                                    numHints--;
                                    countOfSteps++;
                                    System.out.println("You have 0 hints left");
                                    String nextHint = mostEfficientPath.get(1);
                                    String hintForNextWord = Hints.hintNumberOne(startWord, nextHint);
                                    System.out.println("Letter that needs to be changed for " + startWord + " is " + hintForNextWord);
                                    Hints.hintNumberTwo(startWord, nextHint);
                                    String nextHint3 = Hints.hintNumberThree(startWord, nextHint);
                                    System.out.println("Next word is " + nextHint3);
                                }
                            } else {
                                System.out.println("No more hints allowed!");
                                System.out.println("to delete later, but for now the best path is " + mostEfficientPath);
                            }
                        }
                        default -> System.out.println("Invalid choice. Please try again.");
                    }
                }
                System.out.println("size of most efficient path "+ initialMostEfficientPath.size());
                System.out.println("size of my path "+ myPath.size());
                endProcess = Instant.now().toEpochMilli();
                runtimeOfApp = endProcess -start;
                System.out.println("took " + (runtimeOfApp) + " millis");
                System.out.println();
                System.out.println("Nr. of steps " + countOfSteps);
                System.out.println("Nr. of hints " + countOfHints);
                System.out.println();
                System.out.println("The most efficient path was : "+ initialMostEfficientPath );
                System.out.println("My chosen path was: "+ myPath);
                System.out.println();
                System.out.println("List of all the players that played on this instance were " + listOfPlayerNames);
                System.out.println();
                System.out.println("Game over at this date and time::");
                obj = new Date();
                System.out.println(dateFormat.format(obj));
                System.out.println("<<<GAME OVER>>>");
                writeToCSV("src/main/java/words_alpha3.csv");
                if(restartGame()){
                    return;
                } else {
                    exit = false;
                }
            } else {
                System.out.println("Errors: " + errorsForFirstWord);
                System.out.println("Give me a valid starting word!");
                startWord = input.next();
            }
        } while (exit);
    }
    public static void writeToCSV(String filePath) throws Exception {
        File file = new File(filePath);
        try{
            FileWriter outfile = new FileWriter(file);
            CSVWriter writer = new CSVWriter(outfile);
            writer.writeNext(new String[]{"runtimeOfApp", String.valueOf(runtimeOfApp)});
            writer.writeNext(new String[]{"countOfSteps", String.valueOf(countOfSteps)});
            writer.writeNext(new String[]{"countOfHints", String.valueOf(countOfHints)});
            ArrayList<List<String>> arr2 = new ArrayList<>();
            arr2.add(initialMostEfficientPath);
            writer.writeNext(new String[]{"mostEfficientPath", String.valueOf(arr2)});
            writer.writeNext(new String[]{"myPath", String.valueOf(myPath)});
            writer.writeNext(new String[]{"Users", String.valueOf(listOfPlayerNames)});
            writer.writeNext(new String[]{"Date & Time", String.valueOf(obj)});
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean restartGame()  {
        Scanner scan = new Scanner(System.in);
        while(true){
            System.out.println("Do you want to keep playing?YES/NO");
            String choice2 = scan.next().toUpperCase();
            switch (choice2) {
                case "YES" -> {
                    return true;
                }
                case "NO" -> {
                    System.exit(0);
                    return false;
                }
                default -> System.out.println("Incorrect input!!! Please re-enter choice from menu");
            }
        }
    }

    public static double getRuntimeOfApp(){
        return runtimeOfApp;
    }

    public static int getCountOfHints(){
        return countOfHints;
    }

    public static int getCountOfSteps(){
        return countOfSteps;
    }

    public static ArrayList<String> getListOfPlayerNames(){
        return listOfPlayerNames;
    }

    public static ArrayList<String> getMyPath(){
        return myPath;
    }

    public static List<String> getMostEfficientPath(){
        return mostEfficientPath;
    }

    public static List<String> getInitialMostEfficientPath(){
        return initialMostEfficientPath;
    }

}
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
        public static void main(String[] args) throws Exception {

        List<String> words = WordProcessor.readWordsFromFile("src/main/java/words_alpha.txt");

        Map<String, List<String>> wordsByWildcard = WordProcessor.groupWordsByWildcard(words);

        GraphBuilder.Graph<String> graph = GraphBuilder.buildGraphFromWildcardMap(wordsByWildcard);

        Scanner scan = new Scanner(System.in);

        while(true){
            System.out.println("<<Graph Puzzle>>");
            System.out.println("Choose a game mode::");
            System.out.println("1. Automatic Mode");
            System.out.println("2. Playing Mode");
            System.out.println("3. Exit");
            int choice = scan.nextInt();//accept user input
            switch (choice) {
                case 1 -> {
                    System.out.println("<<Automatic Mode>>");
                    System.out.println("Choose the starting word::");
                    String startWord = scan.next().toLowerCase();
                    System.out.println("Choose the ending word::");
                    String end = scan.next().toLowerCase();
                    List<String> path = PathFinder.findShortestPath(graph, startWord, end);
                    if (path.isEmpty()) {
                        System.out.println("Not found");
                    } else {
                        System.out.println("Path found: " + String.join(", ", path));
                    }
                    PlayingMode.restartGame();
                }
                case 2 -> {
                    PlayingMode.startGame(graph, words);
                }
                case 3 -> System.exit(0);
                default -> System.out.println("Incorrect input!!! Please re-enter choice from menu");
            }
        }
    }
}
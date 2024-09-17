package lastpencil;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean initialized = false;
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many pencils would you like to use:");
        int numPencils = getNumericInput(initialized);
        initialized = true;

        String player1;
        do  {
            System.out.println("Who will be the first (Franz, Karl): ");
            player1 = scanner.nextLine();
            if (!player1.equals("Franz") && !player1.equals("Karl")) {
                System.out.println("Choose between 'Franz' and 'Karl'");
            }
        }
        while (!player1.equals("Franz") && !player1.equals("Karl"));
        String currentPlayer = player1;
        String player2 = nextPlayer(player1);

        printPencils(numPencils);

        int turnCounter = 0;
        int currentTake;

        while (numPencils > 0) {
            currentPlayer = turnCounter % 2 == 0 ? player1 : player2;
            System.out.printf("%s's turn!\n", currentPlayer);
            if (currentPlayer.equals("Franz")) { // Human turn
                currentTake = getValidTake(initialized, numPencils);
            } else { //Bot Turn
                currentTake = isWinningPosition(numPencils) ?
                        winningTake(numPencils) : randomTake(numPencils);
                System.out.println(currentTake);
            }
            numPencils -= currentTake;
            printPencils(numPencils);
            turnCounter++;
        }
        System.out.printf("%s won!", nextPlayer(currentPlayer));
    }

    public static int getNumericInput(boolean initialized) {
        Scanner scanner = new Scanner(System.in);
        String input;
        int numericInput = 0;
        String stage1Msg1 = "The number of pencils should be positive";
        String stage1Msg2 = "The number of pencils should be numeric";
        String stage2Msg1 = "Possible values: '1', '2' or '3'";
        String stage2Msg2 = "Possible values: '1', '2' or '3'";
        String message1 = initialized ? stage2Msg1 : stage1Msg1;
        String message2 = initialized ? stage2Msg2 : stage1Msg2;
        do {
            input = scanner.nextLine();
            if (isNumeric(input)) {
                numericInput = Integer.parseInt(input);
                if (numericInput <= 0) {
                    System.out.println(message1);
                }
            } else {
                System.out.println(message2);
            }
        } while (numericInput <= 0 || !isNumeric(input));
        return numericInput;
    }

    public static int getValidTake(boolean initialized, int remaining) {
        int take;
        do {
            take = getNumericInput(initialized);
            if (take > 3) {
                System.out.println("Possible values: '1', '2' or '3'");
            }
            if (take > remaining) {
                System.out.println("Too many pencils were taken");
            }
        } while (take > 3 || take > remaining);
        return take;
    }

    public static String nextPlayer (String lastPlayer){
            return lastPlayer.equals("Franz") ? "Karl" : "Franz";
    }

    public static void printPencils (int numPencils){
            for (int i = 0; i < numPencils; i++) {
                System.out.print("|");
            }
            System.out.print("\n");
    }

    public static boolean isNumeric (String s){
            char[] chars = s.toCharArray();
            for (char c : chars) {
                if (!Character.isDigit(c)) {
                    return false;
                }
            }
            return true;
    }

    public static boolean isWinningPosition(int pencilsLeft) {
        if (pencilsLeft != 1) {
            return ((pencilsLeft - 1) % 4 != 0);
        } else {
            return false;
        }
    }

    public static int randomTake(int pencilsLeft) {
        Random rand = new Random();
        return pencilsLeft == 1 ? 1 : (rand.nextInt(2) + 1);
    }

    public static int winningTake(int pencilsLeft) {
        int take;
        if (pencilsLeft % 4 == 0) {
            take = 3;
        } else if ((pencilsLeft + 1) % 4 == 0) {
            take = 2;
        } else {
            take = 1;
        }
        return take;
    }
}

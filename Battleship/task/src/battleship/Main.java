package battleship;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Write your code here
        Scanner scanner = new Scanner(System.in);
        String input;

        GameBoard player1 = new GameBoard("Player 1");
        GameBoard player2 = new GameBoard("Player 2");

        initializeGame(scanner, player1);
        promptEnterKey();
        initializeGame(scanner, player2);
        promptEnterKey();

        boolean gameIsRunning = true;
        String winner;
        while (gameIsRunning) {
            if (!gameLoop(scanner, player1, player2)) {
                gameIsRunning = false;
                winner = player1.getPlayerName();
                break;
            };
            promptEnterKey();
            if (!gameLoop(scanner, player2, player1)) {
                gameIsRunning = false;
                winner = player2.getPlayerName();
                break;
            };
            promptEnterKey();
        }

        System.out.println("You sank the last ship. You won. Congratulations!");
    }

    private static boolean gameLoop(Scanner scanner, GameBoard playerOnTurn, GameBoard opponentPlayer) {
        opponentPlayer.hideShips();
        playerOnTurn.showShips();

        opponentPlayer.printGameBoard();
        System.out.println("---------------------");
        playerOnTurn.printGameBoard();

        String input;
        System.out.println(String.format("%s, it's your turn:", playerOnTurn.getPlayerName()));
        do {
            input = scanner.nextLine();
        } while (!playerOnTurn.isInputCorrect(input));
        String shotResult = opponentPlayer.takeShot(input);
        System.out.println(shotResult);

        if (!opponentPlayer.areShipFieldsLeft()) {
            return false;
        }
        return true;
    }

    private static void initializeGame(Scanner scanner, GameBoard player) {
        System.out.println(String.format("%s, place your ships on the game field", player.getPlayerName()));

        player.printGameBoard();

        String input;

        do {
            System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):");
            input = scanner.nextLine();
        } while (!player.positionShip("Aircraft Carrier", 5, input));

        player.printGameBoard();

        do {
            System.out.println("Enter the coordinates of the Battleship (4 cells):");
            input = scanner.nextLine();
        } while (!player.positionShip("Battleship", 4, input));

        player.printGameBoard();

        do {
            System.out.println("Enter the coordinates of the Submarine (3 cells):");
            input = scanner.nextLine();
        } while (!player.positionShip("Submarine", 3, input));

        player.printGameBoard();

        do {
            System.out.println("Enter the coordinates of the Cruiser (3 cells):");
            input = scanner.nextLine();
        } while (!player.positionShip("Cruiser", 3, input));

        player.printGameBoard();

        do {
            System.out.println("Enter the coordinates of the Destroyer (2 cells):");
            input = scanner.nextLine();
        } while (!player.positionShip("Destroyer", 2, input));

        player.printGameBoard();
    }

    public static void promptEnterKey() {
        System.out.println("Press Enter and pass the move to another player");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

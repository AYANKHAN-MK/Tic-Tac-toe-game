import java.util.Scanner;

public class TicTacToeAI {

    static final char PLAYER = 'X';
    static final char COMPUTER = 'O';
    static final char EMPTY = ' ';
    static char[] board = new char[9];

    public static void main(String[] args) {
        for (int i = 0; i < 9; i++) board[i] = EMPTY;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Tic-Tac-Toe Game");

        printBoard();

        while (true) {
            playerMove(scanner);
            printBoard();
            if (isWinner(PLAYER)) {
                System.out.println("You win!");
                break;
            }
            if (isBoardFull()) {
                System.out.println("It's a draw!");
                break;
            }

            int bestMove = findBestMove();
            board[bestMove] = COMPUTER;
            System.out.println("\nComputer chose: " + (bestMove + 1));
            printBoard();

            if (isWinner(COMPUTER)) {
                System.out.println("Computer wins!");
                break;
            }
            if (isBoardFull()) {
                System.out.println("It's a draw!");
                break;
            }
        }

        scanner.close();
    }

    static void printBoard() {
        System.out.println();
        for (int i = 0; i < 9; i += 3) {
            System.out.printf(" %c | %c | %c \n", board[i], board[i + 1], board[i + 2]);
            if (i < 6) System.out.println("---|---|---");
        }
        System.out.println();
    }

    static void playerMove(Scanner scanner) {
        int move;
        while (true) {
            System.out.print("Enter your move (1-9): ");
            move = scanner.nextInt() - 1;
            if (move >= 0 && move < 9 && board[move] == EMPTY) {
                board[move] = PLAYER;
                break;
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }
    }

    static boolean isWinner(char player) {
        int[][] winComb = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // columns
            {0, 4, 8}, {2, 4, 6}             // diagonals
        };

        for (int[] combo : winComb) {
            if (board[combo[0]] == player &&
                board[combo[1]] == player &&
                board[combo[2]] == player)
                return true;
        }
        return false;
    }

    static boolean isBoardFull() {
        for (char c : board) {
            if (c == EMPTY) return false;
        }
        return true;
    }

    static int minimax(boolean isMaximizing) {
        if (isWinner(COMPUTER)) return 1;
        if (isWinner(PLAYER)) return -1;
        if (isBoardFull()) return 0;

        if (isMaximizing) {
            int bestScore = -1000;
            for (int i = 0; i < 9; i++) {
                if (board[i] == EMPTY) {
                    board[i] = COMPUTER;
                    int score = minimax(false);
                    board[i] = EMPTY;
                    bestScore = Math.max(score, bestScore);
                }
            }
            return bestScore;
        } else {
            int bestScore = 1000;
            for (int i = 0; i < 9; i++) {
                if (board[i] == EMPTY) {
                    board[i] = PLAYER;
                    int score = minimax(true);
                    board[i] = EMPTY;
                    bestScore = Math.min(score, bestScore);
                }
            }
            return bestScore;
        }
    }

    static int findBestMove() {
        int bestScore = -1000;
        int bestMove = -1;
        for (int i = 0; i < 9; i++) {
            if (board[i] == EMPTY) {
                board[i] = COMPUTER;
                int score = minimax(false);
                board[i] = EMPTY;
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = i;
                }
            }
        }
        return bestMove;
    }
}

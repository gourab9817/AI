public class tictac {

    public static void main(String[] args) {
        char[][] board = {
                { 'O', 'O', 'X' },
                { 'X', ' ', ' ' },
                { ' ', 'O', 'X' }
        };

        printBoard(board);

        // Determine the optimal move
        int[] optimalMove = minimax(board, 'X');

        System.out.println("Optimal Move: Row " + (optimalMove[0] + 1) + ", Column " + (optimalMove[1] + 1));
    }

    private static void printBoard(char[][] board) {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }

    private static int[] minimax(char[][] board, char player) {
        int[] bestMove = new int[] { -1, -1 };
        int bestScore = Integer.MIN_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = player;
                    int score = minimaxHelper(board, 0, false);
                    board[i][j] = ' ';

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }

        return bestMove;
    }

    private static int minimaxHelper(char[][] board, int depth, boolean isMaximizing) {
        if (isGameOver(board)) {
            return evaluate(board, depth);
        }

        char currentPlayer = (isMaximizing) ? 'X' : 'O';
        int bestScore = (isMaximizing) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = currentPlayer;
                    int score = minimaxHelper(board, depth + 1, !isMaximizing);
                    board[i][j] = ' ';

                    if ((isMaximizing && score > bestScore) || (!isMaximizing && score < bestScore)) {
                        bestScore = score;
                    }
                }
            }
        }

        return bestScore;
    }

    private static boolean isGameOver(char[][] board) {
        return hasPlayerWon(board, 'X') || hasPlayerWon(board, 'O') || isBoardFull(board);
    }

    private static boolean hasPlayerWon(char[][] board, char player) {
        // Check rows, columns, and diagonals
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                    (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
                return true;
            }
        }

        return (board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
                (board[0][2] == player && board[1][1] == player && board[2][0] == player);
    }

    private static boolean isBoardFull(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private static int evaluate(char[][] board, int depth) {
        if (hasPlayerWon(board, 'O')) {
            return 10 - depth;
        } else if (hasPlayerWon(board, 'X')) {
            return depth - 10;
        } else {
            return 0;
        }
    }
}

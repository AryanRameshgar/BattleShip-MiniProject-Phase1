import java.util.Scanner;
import java.util.Random;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main
{
    static final int SIZE = 10;
    static char[][] player1Board = new char[SIZE][SIZE];
    static char[][] player2Board = new char[SIZE][SIZE];
    static char[][] player1_view = new char[SIZE][SIZE];
    static char[][] player2_view = new char[SIZE][SIZE];
    static int player1Remaining_S = 14;
    static int player2Remaining_S = 14;
    static int[] shipLengths = {5, 4, 3, 2};
    public static void main(String[] args)
    {
        initializeBoard(player1Board);
        initializeBoard(player2Board);
        initializeBoard(player1_view);
        initializeBoard(player2_view);

        placeShips(1);
        placeShips(2);
        playGame();
    }

    public static void initializeBoard(char[][] board)
    {
        for (int i = 0; i < SIZE; i++)
        {
            for (int j = 0; j < SIZE; j++)
            {
                board[i][j] = '~';
            }
        }
    }

    public static void placeShips(int player)
    {
        Random rand = new Random();
        char[][] currentBoard = (player == 1) ? player1Board : player2Board;
        int tedad_kashti_gharar_gerefte = 0;

        while (tedad_kashti_gharar_gerefte < 4)
        {
            int shipLength = shipLengths[tedad_kashti_gharar_gerefte];
            int row = rand.nextInt(SIZE);
            int col = rand.nextInt(SIZE);
            char direction =' ';
            int dir = (int)(Math.random() * 2);
            if(dir==0){direction = 'h';}
            if(dir==1){direction = 'v';}

            if (harketGhabaleGhabool(currentBoard, row, col, shipLength, direction))
            {
                placeShip(currentBoard, row, col, shipLength, direction);
                tedad_kashti_gharar_gerefte++;
            }
        }
    }

    public static boolean harketGhabaleGhabool(char[][] board, int row, int col, int length, char direction)
    {
        if (direction == 'h')
        {
            if (col + length > SIZE) return false;
            for (int i = 0; i < length; i++)
            {
                if (board[row][col + i] != '~') return false;
            }
        }
        else if (direction == 'v')
        {
            if (row + length > SIZE) return false;
            for (int i = 0; i < length; i++)
            {
                if (board[row + i][col] != '~') return false;
            }
        }
        return true;
    }

    public static void placeShip(char[][] board, int row, int col, int length, char direction)
    {
        if (direction == 'h')
        {
            for (int i = 0; i < length; i++)
            {
                board[row][col + i] = 'S';  // قرار دادن کشتی افقی
            }
        }
        else if (direction == 'v')
        {
            for (int i = 0; i < length; i++)
            {
                board[row + i][col] = 'S';  // قرار دادن کشتی عمودی
            }
        }
    }

    public static void Game_board(char[][] board)
    {
        System.out.print("     ");
        for (int i = 65 ; i< 75 ; i++)
        {
            System.out.print( (char)i + "   ");
        }
        System.out.println("   ");

        for (int i = 0 ; i <=9 ; i++)
        {
            System.out.print("   +");
            for(int k = 0 ; k <= 9 ; k++)
            {
                System.out.print("---+");
            }
            System.out.println();
            System.out.print( i + " " + " |");
            for(int j=0 ; j<10 ; j++)
            {
                System.out.print( " "+board[i][j] + " |");
            }
            System.out.print( " " + i );
            System.out.println();
        }
        System.out.print("   +");
        for(int k = 0 ; k < 10 ; k++)
        {
            System.out.print("---+");
        }
        System.out.println();
        System.out.print("     ");
        for( int i =0 ; i<10 ; i++)
        {
            System.out.print( (char)(i+65) + "   ");
        }
        System.out.println();
    }

    public static void attack(int player, int row, int col)
    {
        char[][] opponentBoard = (player == 1) ? player2Board : player1Board;
        char[][] opponentBoard_view = (player == 1) ? player2_view : player1_view;
        //  int remainingShips = (player == 1) ? player2Remaining_S : player1Remaining_S;

        if (opponentBoard[row][col] == 'S')
        {
            opponentBoard[row][col] = 'X';  //  نشان دهنده ضربه به کشتی است
            opponentBoard_view[row][col] = 'X';
            if (player == 1)
            {
                player2Remaining_S--;
            }
            else
            {
                player1Remaining_S--;
            }
            System.out.println("Hit!");
        }
        else if (opponentBoard[row][col] == '~')
        {
            opponentBoard[row][col] = 'O';//  نشان دهنده حمله به آب است
            opponentBoard_view[row][col] = 'O';
            System.out.println("Miss!");
        }
        else
        {
            System.out.println("You already attacked this position.");
        }
    }

    public static boolean isGameOver()
    {
        return player1Remaining_S == 0 || player2Remaining_S == 0;
    }

    public static void playGame()
    {
        Scanner input = new Scanner(System.in);
        boolean gameOver = false;
        int currentPlayer = 1;

        while (!gameOver)
        {
            System.out.println("Player " + currentPlayer + "'s turn");
            System.out.println("Opponent's board:");
            if (currentPlayer == 1)
            {
                Game_board(player2_view);
            }
            else
            {
                Game_board(player1_view);
            }

            System.out.println("Enter row (0-9): ");
            int row = input.nextInt();
            System.out.print("Enter column (A-J): ");
            String Col = input.next().toUpperCase();
            char sotoon= Col.charAt(0);
            int col = (int)sotoon - 65;

            if (row >= 0 && row < SIZE && col >= 0 && col < SIZE)
            {
                attack( currentPlayer, row , col);
                gameOver = isGameOver();
                currentPlayer = (currentPlayer == 1) ? 2 : 1;
            }
            else
            {
                System.out.println("Invalid move. Try again.");
            }
        }

        // اعلام برنده
        if (player1Remaining_S == 0)
        {
            System.out.println("Player 2 wins! All Player 1's ships have been destroyed.");
        }
        else
        {
            System.out.println("Player 1 wins! All Player 2's ships have been destroyed.");
        }

    }

}
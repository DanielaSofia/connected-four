import java.util.*;

class Board {

	public int lines = 6;
	public int columns = 7;
	public int[][] board = new int[6][7];

	// clean the initial matrix
	 
	public void BlankBoard(int[][] board) {

		for (int i = 0; i < lines; i++) {
			for (int j = 0; j < columns; j++) {
				board[i][j] = 0;
			}
		}
	}

	//Prints Board
	public void PrintBoard(int[][] board) {

		for (int i = 0; i < lines; i++) {
			for (int j = 0; j < columns; j++) {
				if (board[i][j] == 0)
					System.out.print("_  ");
				else
					System.out.print(board[i][j] + "  ");
			}
			System.out.println();
		}

		for (int i = 0; i < columns; i++)
			System.out.print("*  ");
		System.out.println();

		for (int i = 0; i < columns; i++)
			System.out.print(i + "  ");
		System.out.println();

	}

	//Check if move is valid
	public boolean CheckMove(int[][] board, int col) {
		if (col >= 0 && col < 7) {
			for (int i = lines - 1; i > 0; i--) {
				if (board[i][col] == 0) {
					return true;
				}
			}
		}
		return false;
	}

	//make move on the board
	public int[][] MakeMove(int[][] board, int col, int player) {

		for (int i = lines - 1; i > 0; i--) {
			if (board[i][col] == 0) {
				board[i][col] = player;
				break;
			}
		}
		return board;
	}

	//Check if anyone wins
	public int Win(int[][] board) {

		// Vertical
		int count = 0;
		int current = -1;
		int j = columns - 1;
		while (j >= 0) {

			for (int i = lines - 1; i > 0; i--) {

				if (current == -1) {
					count++;
					current = board[i][j];
					continue;
				}
				if (current != board[i][j]) {
					count = 1;
					current = board[i][j];
				} else if (current != 0)
					count++;

				if (count == 4)
					return current;
			}
			j--;
		}

		count = 0;
		current = -1;
		// Horizontal
		j = lines - 1;
		while (j >= 0) {
			for (int i = columns - 1; i > 0; i--) {

				if (current == -1) {
					count++;
					current = board[j][i];
					continue;
				}

				if (current != board[j][i]) {
					count = 1;
					current = board[j][i];
				} else if (current != 0)
					count++;

				if (count == 4)
					return current;
			}

			j--;
		}

		// Diagonal

		for (int i = 0; i < lines - 1; i++) {
			for (j = 0; j < columns - 1; j++) {

				if (board[i][j] == 0) {
					continue;
				}

				if (i + 3 < lines && j + 3 < columns) {
					if ((board[i][j] == board[i + 1][j + 1]) && (board[i + 1][j + 1] == board[i + 2][j + 2])
							&& (board[i + 2][j + 2] == board[i + 3][j + 3]))
						return board[i][j];

				}
				if (i + 3 < lines && j - 3 < columns) {
					if ((board[i][j] == board[i + 1][j - 1]) && (board[i + 1][j - 1] == board[i + 2][j - 2])
							&& (board[i + 2][j - 2] == board[i + 3][j - 3]))
						return board[i][j];
				}
			}
		}
		return 0;
	}

}

class Minimax {

	Board b = new Board();
	ArrayList<int[][]> Successors = new ArrayList<int[][]>();
	int[][] state = new int[b.lines][b.columns];
	int v = 0;
	int mv = 0;

	public void Minimax_Decision(int[][] board) {
		state = board;
		v = Max_Value(state);
		return /* action in Successors(state) with value V */;
	}

	public int Max_Value(int[][] current) {
		if (b.Win(current) == 0)
			return Utility(state);
		v = -9999;

		for (int[][] item : Successors) {
			v = Max(v, Min_Value(item));
		}
		return v;
	}

	public int Min_Value(int[][] state) {

		if (b.Win(current) == 0)
			return Utility(state);

		v = -9999;
		for (int[][] item : Successors) {
			v = Min(v, Max_Value(item));
		}
		return v;

	}

}

public class ConnectedFour {

	public static void main(String [] args){

		Scanner in = new Scanner(System.in);
		Board b ;
		int playfirst;
		double startTime;
		// selected column
		int scol=0;
		System.out.print("How do you want to play?\n MinMax(1) or Alfa-Beta(2)? \nOption: "); 
		int option = in.nextInt();
		if(option == 1) {
			
			// MinMax
			System.out.print("Who plays first? Computer(1) ou Human(2)\nOption: ");
			playfirst = in.nextInt();
			startTime = Math.pow(10,-9)*System.nanoTime();

			if(playfirst == 1) {
				// Computer Play
				b = new Board();
				b.BlankBoard(b.board);
				b.PrintBoard(b.board);
				System.out.println("Computer Play:");
				//chama funcao minimax
				if(b.Win(b.board) == 1){
					b.PrintBoard(b.board);
					System.out.println("Computer Wins !!");
					System.exit(0);
				}
				b.PrintBoard(b.board);

			}else {
				b = new Board();
				System.out.println("Human play!");
				scol=in.nextInt();

				while(!b.CheckMove(b.board,scol)){
					System.out.println("Human play valid!");
					scol=in.nextInt();
				}
				b.board=b.MakeMove(b.board,scol,2);
				if(b.Win(b.board) == 2){
					b.PrintBoard(b.board);
					System.out.println("Human Wins !!");
					System.exit(0);
				}
				b.PrintBoard(b.board);
			}

		}else {				
			// Alpha-Beta
			System.out.print("Who plays first? Computer(1) ou Human(2)\nOption: ");
			playfirst = in.nextInt();
			startTime = Math.pow(10,-9)*System.nanoTime();
	
			if(playfirst == 2) {
				// Computer Play
				b = new Board();
				b.BlankBoard(b.board);
				b.PrintBoard(b.board);
				System.out.println("Computer Play:");
				// Calls Alpha-Beta
				
				if(b.Win(b.board) == 1){
					b.PrintBoard(b.board);
					System.out.println("Computer Wins !!");
					System.exit(0);
				}
				b.PrintBoard(b.board);
	
			}else {
				
				b = new Board();
				System.out.println("Human play!");
				scol=in.nextInt();
				while(!b.CheckMove(b.board,scol)){
					System.out.println("Human play valid!");
					scol=in.nextInt();
				}
				b.board=b.MakeMove(b.board,scol,2);
				if(b.Win(b.board) == 2){
					b.PrintBoard(b.board);
					System.out.println("Human Wins !!");
					System.exit(0);
				}
				b.PrintBoard(b.board);
			}
		}


	}
			
}

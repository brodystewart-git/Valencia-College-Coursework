import java.util.Random;

public class ArraysAssignment {
	
	public static void main(String[] args) {
		SudokuBoard testBoard = new SudokuBoard(80);
		testBoard.printBoard(false);
		if (!testBoard.solve()) System.out.println("No solution.");
		testBoard.printBoard(true);
	}
}



class SudokuBoard {
	// [board 0-8 (left to right)] [position 0-8 (left to right)] 
	/* Each board should look like this:
	* 0,1,2		
	* 3,4,5
	* 6,7,8
	*/
	private int[][] board;
	
	public SudokuBoard(int numMax){ // This number determines how many numbers will be placed. Up to 81.
		this.board = new int[9][9];
		Random rand = new Random();
		int numCount = 0; 
		if (numMax > 81 || numMax < 0) numMax = 20; // just in case a user error occurs.
		
		while (numCount < numMax) {
			int num = rand.nextInt(9) + 1;
			int boardNum = rand.nextInt(9);
			int row = rand.nextInt(3);
			int column = rand.nextInt(3);
			int position = row * 3 + column;
			
			if(board[boardNum][position] == 0 && is_safe(board, boardNum, row, column, num)) {
				board[boardNum][position] = num;
				
				 if (is_solvable()){// Test entire board
					 numCount++;
				 }else board[boardNum][position] = 0;
			}
		}
	}
	
	public boolean is_safe(int[][] grid, int boardNum, int row, int column, int num) {
		int position = row * 3 + column; // position = row * 3 + column
	    int realRow = (boardNum / 3) * 3 + row; // Row in relation to entire board
	    int realColumn = (boardNum % 3) * 3 + column; // Column in relation to entire board
	    
	    for (int i = 0; i < 9; i++) { // Check single Sudoku block
	        if (grid[boardNum][i] == num && i != position) return false; 
	    }

	    for (int b = (realRow / 3) * 3; b < (realRow / 3) * 3 + 3; b++) { // Check Sudoku Row
	        for (int c = 0; c < 3; c++) {
	            int posToCheck = (realRow % 3) * 3 + c;
	            if (grid[b][posToCheck] == num && !(b == boardNum && posToCheck == position)) return false;
	        }
	    }

	    for (int b = (realColumn / 3); b < 9; b += 3) { // Check Sudoku Column
	        for (int r = 0; r < 3; r++) {
	            int posToCheck = r * 3 + (realColumn % 3);
	            if (grid[b][posToCheck] == num && !(b == boardNum && posToCheck == position)) return false;
	        }
	    }
	    return true;
	}
	
	public boolean is_solvable() {
		int[][] testBoard = new int[9][9];
				for (int i = 0; i < 9; i++)
					System.arraycopy(board[i], 0, testBoard[i], 0, 9);
		return solve(testBoard);
	}
	public boolean solve() {
		return solve(board);
	}
	
	public boolean solve(int[][] numArray) {
		for(int boardNum = 0; boardNum < 9; boardNum++) { // Loops through boards
			for(int position = 0; position < 9; position++) { // Loops through board positions
				if(numArray[boardNum][position] == 0) { // If no answer, do:
					int row = position / 3; // Get Row
					int column = position % 3; // Get Column
					for (int num = 1; num <=9; num++) {  
						if (is_safe(numArray, boardNum, row, column, num)) {
							numArray[boardNum][position] = num; // Solved and added to array
							if (solve(numArray)) {
								return true; // Solved.
							}
							numArray[boardNum][position] = 0;
						}
					} return false; // No numbers worked. Failed.
				}
			}
		} return true;
	}
	
	
	public void printBoard(boolean solved) {
		if(solved)System.out.println("--------Solved Board--------");
		if(!solved)System.out.println("--------Unsolved Board--------");
		  for (int r = 0; r < 9; r++) {
	            if (r > 0 && r % 3 == 0) {
	                System.out.println("-------------------------");
	            }
	            for (int c = 0; c < 9; c++) {
	                if (c % 3 == 0) System.out.print("| ");
	                
	                // Logic to map global (r, c) to your board[boardnum][position]
	                int boardNum = (r / 3) * 3 + (c / 3);
	                int pos = (r % 3) * 3 + (c % 3);
	                int val = board[boardNum][pos];
	                System.out.print(val + " ");
	            }
	            System.out.println("|");
	        }
	        System.out.println("-------------------------");
	}
}


public class CatSquare extends MammalSquare {


	//constructors
	public CatSquare(int row,int col, int player) {//Ask Ran
		super(row,col,player);
	}

	public CatSquare(int row,int col, int player, String name) {
		super(row,col,player,name);
	}
	//casing function
	public void keepPossibleRegular(Board board,String[] allRegular,int player){
		int lastRow = this.row, lastCol = this.col;
		checkBiasUpSteps(board, allRegular, player, lastRow, lastCol);
		checkStarightSteps(board, allRegular, player, lastRow, lastCol);
		chickBiasDownSteps(board, allRegular, player, lastRow, lastCol);

	}
	//checks catSquare options for bias moves at one side and inserts it to String array
	private void checkBiasUpSteps(Board board, String[] allRegular, int player, int lastRow, int lastCol) {
	
		if(inBound(lastRow+1*player)&&inBound(lastCol+1*player)) {//if the new place in bound
			if(board.getBoard()[lastRow+1*player][lastCol+1*player].toString().equals("*")||(board.getBoard()[lastRow+1*player][lastCol+1*player].toString().equals("-"))) //if the new place is empty
				setAndCount(board,"Regular", allRegular, lastRow+1*player,lastCol+1*player,lastRow,lastCol);//insert to the array of the options
		}
		if(inBound(lastRow+1*player)&&inBound(lastCol-1*player)) {
			if(board.getBoard()[lastRow+1*player][lastCol-1*player].toString().equals("*")||(board.getBoard()[lastRow+1*player][lastCol-1*player].toString().equals("-"))) //if the new place is empty
				setAndCount(board,"Regular", allRegular,lastRow+1*player,lastCol-1*player,lastRow,lastCol);
			
		}
	}
	//checks catSquare options for bias moves at the other side and inserts it to String array
	private void chickBiasDownSteps(Board board, String[] allRegular, int player, int lastRow, int lastCol) {
		if(inBound(lastRow-1*player)&&inBound(lastCol+1*player)) {
			if(board.getBoard()[lastRow-1*player][lastCol+1*player] .toString().equals("*")||(board.getBoard()[lastRow-1*player][lastCol+1*player].toString().equals("-"))) //if the new place is empty
				setAndCount(board,"Regular",allRegular, lastRow-1*player,lastCol+1*player,lastRow,lastCol);
		}
		if(inBound(lastRow-1*player)&&inBound(lastCol-1*player)) {
			if(board.getBoard()[lastRow-1*player][lastCol-1*player].toString().equals("*")||(board.getBoard()[lastRow-1*player][lastCol-1*player].toString().equals("-"))) //if the new place is empty
				setAndCount(board,"Regular", allRegular,lastRow-1*player,lastCol-1*player,lastRow,lastCol);
		}
	}
	//checks catSquare options for straight moves and inserts it to String array
	private void checkStarightSteps(Board board, String[] allRegular, int player, int lastRow, int lastCol) {

		if(inBound(lastRow+1*player)) {//if the new place in bound
			if(board.getBoard()[lastRow+1*player][lastCol].toString().equals("*")||(board.getBoard()[lastRow+1*player][lastCol].toString().equals("-"))) //if the new place is empty //if the new place is empty
				setAndCount(board,"Regular", allRegular, lastRow+1*player,lastCol,lastRow,lastCol);//insert to the array of the options
		}
		if(inBound(lastRow-1*player)) {//if the new place in bound
			if(board.getBoard()[lastRow-1*player][lastCol] .toString().equals("*")||(board.getBoard()[lastRow-1*player][lastCol].toString().equals("-"))) //if the new place is empty
				setAndCount(board,"Regular", allRegular, lastRow-1*player,lastCol,lastRow,lastCol);//insert to the array of the options
		}
		if(inBound(lastCol+1*player)) {//if the new place in bound
			if(board.getBoard()[lastRow][lastCol+1*player].toString().equals("*")||(board.getBoard()[lastRow][lastCol+1*player].toString().equals("-"))) //if the new place is empty
				setAndCount(board,"Regular", allRegular, lastRow,lastCol+1*player,lastRow,lastCol);//insert to the array of the options
		}
		if(inBound(lastCol-1*player)) {//if the new place in bound
			if(board.getBoard()[lastRow][lastCol-1*player].toString().equals("*")||(board.getBoard()[lastRow][lastCol-1*player].toString().equals("-")))		
				setAndCount(board,"Regular", allRegular, lastRow,lastCol-1*player,lastRow,lastCol);//insert to the array of the options
		}

	}
	//casing function
	public void keepPossibleEat(Board board,String[] allEat,int player){
		int lastRow = this.row, lastCol = this.col;
		//forward steps:
		checkFirstOptionEat(board, allEat, player, lastRow, lastCol);
		checkSecondOptionEat(board, allEat, player, lastRow, lastCol);
	}
	//checks CatSquare options for eat at one side and inserts it to String array
	private void checkFirstOptionEat	(Board board, String[] allEat, int player, int lastRow, int lastCol) {	
		if(inBound(lastRow+2*player)&&inBound(lastCol+2*player)) {//if the new place in bound
			if(board.getBoard()[lastRow+2*player][lastCol+2*player].toString().equals("*")) {//if the new place is empty
				if(board.getBoard()[lastRow+1*player][lastCol+1*player].getPlayer()==-1*player) {//if is there a rival soldier {//if is there a rival soldier
					setAndCount(board,"Eat",allEat, lastRow+2*player,lastCol+2*player,lastRow,lastCol);//insert to the array of the options
					board.setCanEat(true);
				}
			}
		}

		if(inBound(lastRow+2*player)&&inBound(lastCol-2*player)) {
			if(board.getBoard()[lastRow+2*player][lastCol-2*player].toString().equals("*")) {
				if(board.getBoard()[lastRow+1*player][lastCol-1*player].getPlayer()==-1*player) {//if is there a rival soldier {
					setAndCount(board,"Eat", allEat,lastRow+2*player,lastCol-2*player,lastRow,lastCol);
					board.setCanEat(true);
				} 
			}
		}
	}
	//checks CatSquare options for eat at the other side and inserts it to String array
	private void checkSecondOptionEat(Board board, String[] allEat, int player, int lastRow, int lastCol) {
		if(inBound(lastRow-2*player)&&inBound(lastCol-2*player)) {//if the new place in bound
			if(board.getBoard()[lastRow-2*player][lastCol-2*player].toString().equals("*")) {//if the new place is empty
				if(board.getBoard()[lastRow-1*player][lastCol-1*player].getPlayer()==-1*player) {//if is there a rival soldier {//if is there a rival soldier
					setAndCount(board,"Eat",allEat, lastRow-2*player,lastCol-2*player,lastRow,lastCol);//insert to the array of the options
					board.setCanEat(true);
				}
			}
		}

		if(inBound(lastRow-2*player)&&inBound(lastCol+2*player)) {
			if(board.getBoard()[lastRow-2*player][lastCol+2*player].toString().equals("*")) {
				if(board.getBoard()[lastRow-1*player][lastCol+1*player].getPlayer()==-1*player) {//if is there a rival soldier {
					setAndCount(board,"Eat", allEat,lastRow-2*player,lastCol+2*player,lastRow,lastCol);
					board.setCanEat(true);
				}
			}
		}
	}
//updates the board according to the last Square
	public void updateBoard(Board board, int newRow, int newCol) {
		board.setSquare(newRow, newCol, this);
		if((row % 2 == 0 && col % 2 == 0) || (row % 2 != 0 && col % 2 != 0))
			board.setSquare(row, col, new LineSquare(row,col,"-"));
		else
			board.setSquare(row, col, new StarSquare(row,col,"*"));

		this.row = newRow;
		this.col = newCol;
	}
//deletes the rival according to lastSquare
	public  void deleteRival(Board board, int newRow, int newCol) {
		int aveRow=(newRow + row) / 2;
		int aveCol=(newCol + col) / 2;

		if((aveRow % 2 == 0 && aveCol % 2 == 0) || (aveRow % 2 != 0 && aveCol % 2 != 0))
			board.setSquare(aveRow, aveCol, new LineSquare(aveRow,aveCol,"-"));
		else
			board.setSquare(aveRow, aveCol, new StarSquare(aveRow,aveCol,"*"));
		//when the delete occure mark that the eating happened
		board.setEatHappened(true);
	}
}






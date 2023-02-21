
public class MouseSquare extends MammalSquare{
	private int eatsCounter;
	//constructors
	public MouseSquare(int row,int col, int player) {
		super(row,col,player);
	}

	public MouseSquare(int row,int col, int player, String name) {
		super(row,col,player,name);
	}

	//checks MouseSquare options for move and inserts it to String array
	public void keepPossibleRegular(Board board,String[] allRegular,int player){
		int lastRow = this.row, lastCol = this.col;
		//forward steps:
		if(inBound(lastRow+2*player)&&inBound(lastCol+2*player)) {//if the new place in bound
			if(board.getBoard()[lastRow+1*player][lastCol+1*player].toString().equals("*"))
				setAndCount(board,"Regular", allRegular, lastRow+2*player,lastCol+2*player,lastRow,lastCol);//insert to the array of the options
		}
		if(inBound(lastRow+2*player)&&inBound(lastCol-2*player)) {
			if(board.getBoard()[lastRow+1*player][lastCol-1*player].toString().equals("*"))
				setAndCount(board,"Regular", allRegular,lastRow+2*player,lastCol-2*player,lastRow,lastCol);
		}
	}	


	//casing function
	public void keepPossibleEat(Board board,String[] allEat,int player){
		int lastRow = this.row, lastCol = this.col;
		//forward steps:
		checkFirstOptionEat(board, allEat, player, lastRow, lastCol);
		if(board.getIsEatHappened() &&eatsCounter<=12) { //in case its double eating or queen's move
			checkFirstOptionEat(board,allEat,player,lastRow,lastCol);
			checkSecondOptionEat(board, allEat, player, lastRow, lastCol);
	}
	}
	//checks MouseSquare options for eat at one side and inserts it to String array
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

	//checks MouseSquare options for eat at the other side and inserts it to String array
	private void checkSecondOptionEat(Board board, String[] allEat, int player, int lastRow, int lastCol) {
		//backwards steps:
		if(inBound(lastRow-2*player)&&inBound(lastCol+2*player)) {
			if(board.getBoard()[lastRow-2*player][lastCol+2*player].toString().equals("*")) {
				if(board.getBoard()[lastRow-1*player][lastCol+1*player].getPlayer()==-1*player) {//if is there a rival soldier) {
					setAndCount(board,"Eat",allEat, lastRow-2*player,lastCol+2*player,lastRow,lastCol);
					board.setCanEat(true);
				}
			}
		}

		if(inBound(lastRow-2*player)&&inBound(lastCol-2*player)) {
			if(board.getBoard()[lastRow-2*player][lastCol-2*player].toString().equals("*")) {
				if(board.getBoard()[lastRow-1*player][lastCol-1*player].getPlayer()==-1*player) {//if is there a rival soldier {
					setAndCount(board,"Eat", allEat,lastRow-2*player,lastCol-2*player,lastRow,lastCol);
					board.setCanEat(true);
				}
			}
		}
	}
	//runs over the general delteRival functions according to mouse eat Rules
	public  void deleteRival(Board board, int newRow, int newCol) {
		eatsCounter++;
		board.setEatHappened(true);
		return;
	}
	//replace regular player to queen according to the checkers rules
	public void replaceToQueen(Board board,int curRow,int curCol) {
		if(player==1 &&curRow==1) 
			board.setSquare(row, col, new MouseSquareQueen(curRow,curCol,1,"M1Q"));
		if(player==2&&curRow==8) 
			board.setSquare(row, col, new MouseSquareQueen(curRow,curCol,2,"M2Q"));
	}

}

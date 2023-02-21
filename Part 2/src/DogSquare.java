
public class DogSquare extends MammalSquare{


	//constructors
	public DogSquare(int row,int col, int player) {
		super(row,col,player);
	}

	public DogSquare(int row,int col, int player, String name) {
		super(row,col,player,name);
	}


	//casing function
	public  void keepPossibleEat(Board board,String[] allEat,int player){
		int lastRow = this.row, lastCol = this.col;
		//forward steps:
		checkFirstOptionEat(board, allEat, player, lastRow, lastCol);
		checkSecondOptionEat(board, allEat, player, lastRow, lastCol);

	}
	//checks DogSquare options for eat at one side and inserts it to String array
	private void checkFirstOptionEat(Board board, String[] allEat, int player, int lastRow, int lastCol) {
		if(inBound(lastRow+2*player)&&inBound(lastCol+2*player)) {//if the new place in bound
			if(board.getBoard()[lastRow+2*player][lastCol+2*player].toString().equals("*")) {//if the new place is empty

				if(board.getBoard()[lastRow+1*player][lastCol+1*player].getPlayer()==-1*player) {//if is there a rival soldier
					setAndCount(board,"Eat",allEat, lastRow+2*player,lastCol+2*player,lastRow,lastCol);//insert to the array of the options
					board.setCanEat(true);

				}

			}
		}
	}
	//checks DogSquare options for eat at the other side and inserts it to String array
	private void checkSecondOptionEat(Board board, String[] allEat, int player, int lastRow, int lastCol) {
		if(inBound(lastRow+2*player)&&inBound(lastCol-2*player)) {
			if(board.getBoard()[lastRow+2*player][lastCol-2*player].toString().equals("*")) {
				if(board.getBoard()[lastRow+1*player][lastCol-1*player].getPlayer()==-1*player) {//if is there a rival soldier {
					setAndCount(board,"Eat", allEat,lastRow+2*player,lastCol-2*player,lastRow,lastCol);
					board.setCanEat(true);
				}
			}
		}
	}

	//keeps DogSquare regular possible moves at String array
	public void keepPossibleRegular(Board board,String[] allRegular,int player){
		int lastRow = this.row, lastCol = this.col;
		//forward steps:
		if(inBound(lastRow+1*player)&&inBound(lastCol+1*player)) {//if the new place in bound
			if(board.getBoard()[lastRow+1*player][lastCol+1*player].toString().equals("*")) { //if the new place is empty
				setAndCount(board,"Regular", allRegular, lastRow+1*player,lastCol+1*player,lastRow,lastCol);//insert to the array of the options
			}
			if(inBound(lastRow+1*player)&&inBound(lastCol-1*player)) {
				if(board.getBoard()[lastRow+1*player][lastCol-1*player].toString().equals("*"))
					setAndCount(board,"Regular", allRegular,lastRow+1*player,lastCol-1*player,lastRow,lastCol);
			}

		}



	}

}
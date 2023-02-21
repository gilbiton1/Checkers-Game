
public class ElephantSquareQueen extends ElephantSquare{
	
	//constructors
	public ElephantSquareQueen(int row,int col,int player) {
		super(row,col,player);
	}
	public ElephantSquareQueen(int row,int col, int player, String name) {
		super(row,col,player,name);
	}
	//converts ElephantSquareQueen to string and returns its string
	public String toString() {
		return this.name;
	}
//checks ElephantQueenSquare options for move and inserts it to String array
	public void keepPossibleRegular(Board board,String[] allRegular,int player) {
		super.keepPossibleRegular(board, allRegular, player*-1);
		super.keepPossibleRegular(board, allRegular, player);
	}
	//checks ElephantQueenSquare options for eat and inserts it to String array
	public void keepPossibleEat(Board board,String[] allEat,int player){
		super.keepPossibleEat(board, allEat, player);
		super.keepPossibleEat(board, allEat, player*-1);
	}
	
	
}

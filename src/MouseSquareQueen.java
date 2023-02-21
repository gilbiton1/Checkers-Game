
public class MouseSquareQueen extends MouseSquare{
	//constructors
	public MouseSquareQueen(int row,int col,int player) {
		super(row,col,player);
	}
	public MouseSquareQueen(int row,int col, int player, String name) {
		super(row,col,player,name);
	}
	//converts MouseSquareQueen to string and returns its string
	public String toString() {
		return this.name;
	}
	//checks MouseQueenSquare options for move and inserts it to String array
	public void keepPossibleRegular(Board board,String[] allRegular,int player) {
		super.keepPossibleRegular(board, allRegular, player);
		super.keepPossibleRegular(board, allRegular, player*-1);
	}
	//checks MouseQueenSquare options for eat and inserts it to String array
	public void keepPossibleEat(Board board,String[] allEat,int player){
		super.keepPossibleEat(board, allEat, player);
		super.keepPossibleEat(board, allEat, player*-1);
	}
	
	
	
}

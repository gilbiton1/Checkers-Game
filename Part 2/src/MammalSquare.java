
public class MammalSquare {

	//helper variables
	protected int row;
	protected int col;
	protected int player;
	protected String name;
	protected boolean isFound;

	//constructor
	public MammalSquare(int row,int col, String name) {
		this.row=row;
		this.col=col;
		this.name=name;
	}
	//constructor
	public MammalSquare(int row,int col, int player) {
		this.row=row;
		this.col=col;
		this.player =player;
	}
	//constructor
	public MammalSquare(int row,int col, int player, String name) {
		this.row=row;
		this.col=col;
		this.player =player;
		this.name= name;
	}
	//getters
	public int getPlayer() {
		if(this.player==2)
			return 1;
		return -1;
	}
	public String getString(Board board,int row, int col) {
		return board.getBoard()[row][col].toString();
	}	

	//setters
	public void setPlayer(int player) {
		this.player=player;
	}
	public void setRow(int row) {
		this.row=row;
	}
	public void setCol(int col) { 
		this.col=col;
	}

	// possible regular moves counter
	protected void countRegular(Board board) {
		int temp=board.getCountRegular();
		board.setCountRegular(temp++);
	}
	//possible eat moves counter 
	protected void countEats(Board board) {
		int temp=board.getCountEats();
		board.setCountEats(temp++);
	}




	//keep the potential eating moves of one player
	protected void keepPossibleEat(Board board,String[] allOptions,int player){
	}

	protected void keepPossibleRegular(Board board,String[] allRegular,int player){
	}
	//returns specific mammalSquare string
	public String toString () {

		return this.name+this.player;
	}

	protected void canEat (String[][] board, String newPlace) {
	}

	//	insert an optional move to array up to type
	protected void setAndCount(Board board, String typeMove, String[] allOptions, int newRow , int newCol, int lastRow, int lastCol) {
		Integer num1 = newRow , num2 = newCol , num3 = lastRow , num4 = lastCol;
		String placeStr=num1.toString() + num2.toString()+ "-" + num3.toString() + num4.toString();//convert the coordinates from int to string

		isFound=false;//an indicate to find an '-' for knowing where insert the current string
		for (int i=0; i<allOptions.length&&!isFound; i++) {
			if(allOptions[i].compareTo("-")==0) {
				allOptions[i]=placeStr;//insert the string to array
				isFound=true;

				//check which counter to update:
				if(typeMove.compareTo("Regular")==0)
					board.setCountRegular(board.getCountRegular() + 1);

				if(typeMove.compareTo("Eat")==0)
					board.setCountEats(board.getCountEats() + 1);

			}
		}
	}
	//return if a number is in board boundaries
	protected boolean inBound(int x){
		if(x>0&&x<9)
			return true;
		return false;
	}


	//returns which player is playing now
	protected int whichPlayer(Board board,int row,int col) {
		if(board.getBoard()[row][col].toString().charAt(1)=='1') 
			return -1;
		return 1;
	}

	//updates board after move/eat
	protected void updateBoard(Board board, int newRow, int newCol) {
		board.setSquare(newRow, newCol, this);
		board.setSquare(row, col, new StarSquare(row,col,"*"));
		this.row = newRow;
		this.col=newCol;
	}
	//delete Rival after eat
	protected  void deleteRival(Board board, int newRow, int newCol) {
		int aveRow=(newRow + row) / 2;
		int aveCol=(newCol + col) / 2;

		board.setSquare(aveRow, aveCol, new StarSquare(aveRow,aveCol,"*"));
		board.setEatHappened(true);//when the delete occure mark that the eating happened
	}
//runs over by the mammalQueens
	protected void replaceToQueen(Board board,int curRow,int curCol) {
		return;
	}
}


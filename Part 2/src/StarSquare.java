
public class StarSquare extends MammalSquare {
	//constructor
	public StarSquare(int row,int col,String name) {
		super(row,col,name);
	}
	//converts StarSquare to string and returns its string
	public String toString() {
		return this.name;
	}
	//runs over the general function because StarSquare dosen't contains player
	public int whichPlayer(Board board,int row,int col) {
		return 0;
	}
	//runs over the general function because StarSquare dosen't contains player
	public int getPlayer() {
		return 0;
	}

}

import java.util.Random;
import java.util.Scanner;

public class Board {

	//helper variables
	private final static int size = 9,arrLength = 48;
	private MammalSquare [][] board;
	private int userChoice,player,countRegular,countEats,curRow,curCol,newRow,newCol;
	private boolean isEatHappened,isStepHappened,canEat,userWantToStop,isOptionFound;
	private String[] allEat;//keep all the eating options
	private String[] allRegular;// keep all the regular step options
	private String userMove;

	//restarts arrays
	public Board() {
		board=new MammalSquare[9][9];
		allEat=new String[arrLength];
		allRegular=new String[arrLength];
	}

	public MammalSquare[][] getBoard() {
		return this.board;
	}
	//setters
	public void setCountRegular(int countRegular) {
		this.countRegular=countRegular;
	}
	public void setCountEats(int countEats) {
		this.countEats=countEats;
	}
	public void setEatHappened(boolean isEatHappened) {
		this.isEatHappened = isEatHappened;
	}
	public void setSquare(int newRow, int newCol, MammalSquare square) {
		board[newRow][newCol] = square;
	}
	public void setCanEat(boolean canEat) {
		this.canEat = canEat;
	}

	//getters
	public int getCountRegular() {
		return this.countRegular;
	}
	public int getCountEats() {
		return this.countEats;
	}
	public boolean getIsEatHappened() {
		return isEatHappened;
	}
	private boolean isCanEat() {
		return canEat;
	}

	//Checkers play
	public  void restartGame() {
		clearBoard();
		userChoise();
		while(userChoice!=0) {//the user want to play
			printBoard();
			do {//game round
				userTurn();
				if(!gameOver())
					computerTurn();
				else
					break;//end the game
			}
			while(!gameOver());
			restartGame();
		} 
	}

	//place all type square at the board
	private void clearBoard() {
		placeComputerSquares();
		placePlayerSquares();
		placeStarAndLine();
	}

	//place star and line
	private void placeStarAndLine() {
		for (int i=1;i<board.length;i++) {
			for(int j=1;j<board.length;j++) {
				if(board[i][j]==null) {
					if((i+j)%2==0) {
						board[i][j]=new LineSquare(i,j,"-");
					}
					else 
						board[i][j]=new StarSquare(i,j,"*");
				}
			}
		}
	}

	//place the players mammal square
	private void placePlayerSquares() {
		board[6][1]=new ElephantSquare(6,1,1,"E");
		board[6][3]=new MouseSquare(6,3,1,"M");
		board[6][5]=new CatSquare(6,5,1,"C");
		board[6][7]=new DogSquare(6,7,1,"D");
		board[7][2]=new DogSquare(7,2,1,"D");
		board[7][4]=new ElephantSquare(7,4,1,"E");
		board[7][6]=new MouseSquare(7,6,1,"M");
		board[7][8]=new CatSquare(7,8,1,"C");
		board[8][1]=new CatSquare(8,1,1,"C");
		board[8][3]=new DogSquare(8,3,1,"D");
		board[8][5]=new ElephantSquare(8,5,1,"E");
		board[8][7]=new MouseSquare(8,7,1,"M");
	}

	//place the computer mammal square
	private void placeComputerSquares() {
		board[1][2]=new CatSquare(1,2,2,"C");
		board[1][4]=new DogSquare(1,4,2,"D");
		board[1][6]=new ElephantSquare(1,6,2,"E");
		board[1][8]=new MouseSquare(1,8,2,"M");
		board[2][1]=new DogSquare(2,1,2,"D");
		board[2][3]=new ElephantSquare(2,3,2,"E");
		board[2][5]=new MouseSquare (2,5,2,"M");
		board[2][7]=new CatSquare(2,7,2,"C");
		board[3][2]=new ElephantSquare(3,2,2,"E");
		board[3][4]=new MouseSquare(3,4,2,"M");
		board[3][6]=new CatSquare(3,6,2,"C");
		board[3][8]=new DogSquare(3,8,2,"D");
	}

	//insert the user choice if start a game or exit the program
	private  void userChoise() {
		System.out.println("Welcome to Fatma Checkers. To start the game press 1, to exit press 0:");
		Scanner sc= new Scanner(System.in);
		userChoice= sc.nextInt();
	}

	// user turn
	private  void userTurn() {
		this.player=-1;
		setEatHappened(false);//indiCatSquaree if there was an eating in this turn
		isStepHappened=false;////indiCatSquaree if there was a regular step in this turn
		System.out.println("It's your turn, please enter your move.");
		play();
	}

	//user is playing
	private void play() {

		keepAllOptionMoves(allEat,allRegular);//keep user potential moves

		//if its the first step or a double eating
		if(isCanEat()==true&&isEatHappened==true||isStepHappened==false&&isEatHappened==false) {

			if(validTurn(allEat,allRegular)) {
				updateBoard();
				//update the new place of the player at the board
				curRow=newRow;
				curCol=newCol;
				isPossibleQueen();//check if there is an option to upgrade to queen
				printBoard();
				play();// resume turn if there is an option to double eating
			}
		}
	}

	//insert coordinate from user and check if its right, run till its valid
	private boolean validTurn(String[] allEat, String[] allRegular) {

		userWantToStop=false;//indiCatSquaree if user insert "STOP"
		Scanner sc= new Scanner(System.in);
		userMove = sc.next();

		if(userMove.compareTo("STOP")==0) {// user want to quit
			userWantToStop=true;
			return false; //for not continue to play
		}
		if(!validInput()) {//the input does not meet the conditions
			System.out.println("The input is not valid, please enter your move again.\r\n" + "");
			validTurn(allEat,allRegular);
		}
		if(!validMove(allEat,allRegular)) {//if the user move is not one of the potential moves
			System.out.print
			("This move is invalid, please enter a new move.");
			validTurn(allEat,allRegular);				
		}

		return true;
	}

	//check if the user move is one of the potential moves\
	private boolean validInput() {
		if(userMove.length()==5) {
			convertStrToCoord(userMove);
			userMove=setString();
			return true;
		}
		return false;
	}	

	//check if the user move is one of the potential moves
	private boolean validMove(String[] allEat,String[] allRegular) {
		 isOptionFound=false;// indiCatSquaree if the user move found in the arrays of potential moves

		for (int i=0; i<arrLength&&!isOptionFound; i++) {
			if(allRegular[i].compareTo(userMove)==0) {
				isOptionFound=true;
				isStepHappened=true;
			}
			if(allEat[i].compareTo(userMove)==0) {
				deleteRival();
				isOptionFound=true;
			}
		}
		return isOptionFound;
	}

	//check if game is over
	private  boolean gameOver() {
		if(noPieces()||noRightMoves()||userIsQuit())
			return true;

		return false;
	}

	//check if there is no more pieces to one of the players
	private boolean noPieces() {
		int count1=0, count2=0;//counters
		for (int i=1; i<board.length; i++) {
			for (int j=1; j<board.length; j++) {
				if (board[i][j].whichPlayer(this, i, j)==-1)
					count1++;
				if (board[i][j].whichPlayer(this, i, j)==1)
					count2++;
			}
		}
		if(count2==0) {//there is no pieces to the computer
			System.out.println("Congratulations, user has won :)");
			return true;
		}
		if(count1==0) {//there is no pieces to the user
			System.out.println("Sorry, computer has won :(");
			return true;
		}
		return false;
	}

	//return if there is optional moves or not of each player
	private boolean noRightMoves() {
		setEatHappened(false);
		keepAllOptionMoves(allEat,allRegular);//user options

		if(allEat[0].compareTo("-")==0&&allRegular[0].compareTo("-")==0) {//if the list of the optional moves is empty
			System.out.println("Well, unfortunately it’s a Tie…");	
			return true;
		}
		return false;
	}

	//return if the user want to quit
	private boolean userIsQuit() {
		if(userWantToStop) {
			System.out.println("Sorry, computer has won :(");
			return true;
		}
		return false;
	}

	//computer turn
	private void computerTurn() {
		this.player=1;
		setEatHappened(false);
		keepAllOptionMoves(allEat,allRegular);

		if(isCanEat()) {//preferred to eat first
			moveComp(countEats,allEat,"Eat");//operate an eating
		} else {
			moveComp(countRegular,allRegular, "Regular");//operate a regular step
		}
		if(isEatHappened) {//check option to double eating
			keepAllOptionMoves(allEat,allRegular);

			while(isCanEat()) {
				moveComp(countEats,allEat,"Eat");
				keepAllOptionMoves(allEat,allRegular);
			}
		}
		System.out.println("Computer has played.\n");
		isPossibleQueen();
		printBoard();
	}

	//operate a random step of the computer 
	private void moveComp(int Counter, String[] allOptions, String typeMove) {
		int x;
		Random rnd=new Random();
		x = rnd.nextInt(Counter);//counter is the number of options moves
		convertStrToCoord(allOptions[x]);

		if(typeMove.compareTo("Eat")==0) {
			deleteRival();
		}

		updateBoard();
		curRow=newRow;
		curCol=newCol;
	}

	//general functions:
	//insert the coordinates to array and switch the numbers to array.
	private void convertStrToCoord(String str) {
		newRow=Integer.parseInt(str.substring(0,1));
		newCol=Integer.parseInt(str.substring(1,2));
		curRow=Integer.parseInt(str.substring(3,4));
		curCol=Integer.parseInt(str.substring(4));
	}

	private void isPossibleQueen() {
		if((player==1 &&curRow==1) ||(player==2&&curRow==8)) { 

			if (isStepHappened==true || canEat==false)
				board[curRow][curCol].replaceToQueen(this,curRow,curCol);// if there is an option
		}
	}


	//delete the rival soldiers whose eaten
	private  void deleteRival() {
		board[curRow][curCol].deleteRival(this, newRow, newCol);
	}

	//keep the potential moves of one player
	private void keepAllOptionMoves(String[] allEat,String[] allRegular) {
		setCanEat(false);
		countEats=0;
		countRegular=0;
		restartArrays(allEat,allRegular);
		if(!isEatHappened) {//if its the first step
			for (int i=1; i<board.length; i++) {
				for (int j=1; j<board.length; j++) {

					if(board[i][j].whichPlayer(this,i,j)==this.player) {
						board[i][j].keepPossibleEat(this,allEat,this.player);
						board[i][j].keepPossibleRegular(this,allRegular,this.player);
					}
				}

			}

		} else {//if its double eating
			board[curRow][curCol].keepPossibleEat(this,allEat,this.player);
		}
	}

	//print the board
	private void printBoard() {
		System.out.println("The board:");
		System.out.println("\t1\t2\t3\t4\t5\t6\t7\t8");
		for(int i=1;i<board.length;i++) {
			System.out.print((i)+ "\t");
			for(int j=1;j<board.length;j++) {
				System.out.print(board[i][j].toString());
				if(j!=board.length-1)
					System.out.print("\t");
			}

			System.out.println();
		}
	}

	//restart arrays
	private void restartArrays(String[] allEat, String[] allRegular) {
		for (int i=0; i<allEat.length; i++) {
			allEat[i]="-";
			allRegular[i]="-";
		}
	}

	//update the board up to the new values
	private void updateBoard() {
		board[curRow][curCol].updateBoard(this, newRow, newCol);
	}

	//count the average of two numbers
	public int average(int a, int b) {
		return(a+b)/2;
	}

	//convert 4 numbers to a string in form xx-xx
	private String setString() {
		Integer num1 = newRow , num2 = newCol , num3 = curRow , num4 = curCol;
		String placeStr=num1.toString() + num2.toString()+ "-" + num3.toString() + num4.toString();
		return placeStr;
	}
}

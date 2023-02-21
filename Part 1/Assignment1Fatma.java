import java.util.Scanner;

public class Assignment1Fatma{
	static Scanner sc=new Scanner(System.in);

	public static int column=8,row=8,choice ;
	public static String[][] board = new String[8][8];
	public static int[]currentEatMove=new int[2];
	public static String current="R",opponent="W",optionalEat="",optionalMoves="", move="";
	public static boolean gameOver, eat, doubleEat;
	public static void main(String[] args) {
		playGame();
	}

	public static void playGame() {//starts the game while the game not over
		initBoard();
		do{
			printBoard();
			userPlayTurn();
		}while(!gameOver); 
	}

	public static void initBoard() {//place the game players
	
		initMessage();
		for (int i=0; i<column;i++) {  //rows
			for (int j=0; j<row;j++) {  //columns
				if ((i + j) % 2 == 0) 
					board [i][j]= "-";
				else {
					if (i<3)
						board [i][j]= "W";
					else if (i>4)
						board [i][j]= "R";
					else
						board [i][j]= "*";
				}
			}
		}
	}

	public static void initMessage() {
		System.out.println("Welcome to Fatma Checkers. To start the game press 1, to exit press 0:");
		choice= sc.nextInt();
		if(choice<0||choice>1) {//enforce valid input
			System.out.println("this Input is Invalid,please press 1 or 0");
			initMessage();
		}
		if (choice==0)
			gameOver=true;
	}

	public static void printBoard() {//printing the board
		for (int i=0; i<column;i++) {  //rows
			for (int j=0; j<row;j++) {  //columns
				System.out.print(board[i][j]+"\t");
			} //for j
			System.out.println();
		} //for i
	}

	public static void userPlayTurn() {//this function getting the user input
		current="R";
		opponent="W";
		System.out.println("It's your turn, please enter your move:");
		move = sc.next();
		if(move.equals("STOP")) {
			gameOver=true;
			System.out.println("Sorry, computer has won :(");
			playGame();
		}
		else if(validInput())
			validMove();
	}

	public static boolean validInput() {//this function enforces valid input

		if(move.length()!=5 || move.charAt(2)!='-') {
			wrongInput();
			return false;
		}
		for (int i=0 ; i<move.length();i++) {
			if (i!=2) {
				if((move.charAt(i))<'1' || (move.charAt(i))>'8') {
					wrongInput();
					return false;
				}
			}
		}
		return true;
	}

	public static void wrongInput() {
		System.out.println("The input is not valid, please enter your move again.");
		move=sc.next();
		if(validInput())
			validMove();
		else
			wrongInput();
	}

	public static void validMove() {  //casing function thats converts String to int
		char tempA= move.charAt(0);
		int a=tempA-'1';
		char tempB=move.charAt(1);
		int b=tempB-'1';
		char tempC=move.charAt(3);
		int c=tempC-'1';
		char tempD = move.charAt(4);
		int d=tempD-'1';

		validMove(a,b,c,d);
	}

	public static void validMove(int a,int b,int c,int d ) {//this function checks all the valid moves for the player and enforces valid tabs
		if(board[c][d].equals(current) || board[c][d].equals("Q-"+current)) {
			if(isQueen(c,d)) 
				validMoveForQueen(a,b,c,d);
			if (doubleEat)
				letHimEatAgain(a,b,c,d);
			else if (BiasRightUp(a,b,c,d)|| BiasLeftUp(a,b,c,d)) {
				userMakeTheMove(a,b,c,d); 
			}
			else if (canEatRightUp(a,b,c,d)|| canEatLeftUp(a,b,c,d))
				eat(a,b,c,d);

			if(!eat) 
				notValidMove();
		}
		else
			notValidMove();
	}
	public static void validMoveForQueen(int a,int b,int c,int d ) { //this function checks all the valid moves for queen
		checkQueenEat(a,b,c,d);
		if (BiasRightDown(a,b,c,d) ||BiasLeftDown(a,b,c,d)||BiasRightUp(a,b,c,d)|| BiasLeftUp(a,b,c,d))
			userMakeTheMove(a,b,c,d);		

	}
	public static void notValidMove() {//Error message while move is invalid
		System.out.println("This move is invalid, please enter a new move.");
		move=sc.next();
		if(validInput())
			validMove();
	}

	public static void userMakeTheMove(int a,int b, int c,int d ) {//update the board after move and checks if the user have another turn
		board[a][b]=current;
		board[c][d]="*";
		if (eat) {
			if (checkDoubleEat(currentEatMove[0],currentEatMove[1]))
				userPlayTurn();
			else 
				eat=false;
		}
		if (a==0) 
			isNewQueen(a,b);
		printBoard();
		computerPlayTurn();
	}

	public static void eat(int a, int b, int c, int d) {//deletes the player thats eaten now 
		board[(c+a)/2][(b+d)/2]="*";
		eat=true; 
		currentEatMove[0]=a;
		currentEatMove[1]=b;
		userMakeTheMove(a,b,c,d);
	}

	public static boolean checkDoubleEat(int c, int d) {//checks after eat if another eat is possible
		if(canEatLeftUp(c-2,d-2,c,d)||canEatRightUp(c-2,d+2,c,d) ||canEatRightDown(c+2,d-2,c,d) ||canEatLeftDown(c+2,d+2,c,d))
			doubleEat=true;
		else
			doubleEat=false;
		return doubleEat;

	}
	public static void letHimEatAgain(int a,int b, int c,int d ) {//makes another eat if it is possible
		if (canEatRightUp(a,b,c,d)|| canEatLeftUp(a,b,c,d)||canEatRightDown(a,b,c,d)||canEatLeftDown(a,b,c,d))
			eat(a,b,c,d);
		if(eat) {
			doubleEat=false;
			checkDoubleEat(currentEatMove[0],currentEatMove[1]);
		}
		if(doubleEat) 
			userPlayTurn();
		else {
			eat=false;
			computerPlayTurn();
		}
	}
	public static void computerPlayTurn() {//back to this function every computer's turn .function that check possible options
		current="W";
		opponent="R";
		if(eat) {
			if(checkDoubleEat(currentEatMove[0],currentEatMove[1])) {
				keepAllComputerOptions();
				playTheComputerOption();
			}
			else
				userPlayTurn();
		}
		else {
			keepAllComputerOptions();
			playTheComputerOption();
		}
		playGame();
	}

	public static void keepAllComputerOptions() {//function that collect all the possible eats&moves and insert them to  diffrent Strings
		optionalMoves="";
		optionalEat="";
		for (int c=0; c<row;c++) {  //rows
			for (int d=0; d<column;d++) {  //columns
				if((board[c][d]).equals(current)||board[c][d].equals("Q-"+current)) {
					isComputerCanEat(c,d);
					isComputerCanMove(c,d);
				}
			}
		}
	}
	public static void playTheComputerOption() {
		if (optionalEat.length()!=0) {//if eat is possible>>make eat
			optionalEat=makeRandomMove(optionalEat);
			computerMakeTheMove(optionalEat);
		}
		else {//if eat isn't possible>>make move
			optionalMoves=makeRandomMove(optionalMoves);
			computerMakeTheMove(optionalMoves);
		}
	}

	public static void computerMakeTheEat(int a, int b, int c, int d) {//deletes the eaten player
		board[(c+a)/2][(b+d)/2]="*";
		eat=true; 
		doubleEat=false;
		currentEatMove[0]=a;
		currentEatMove[1]=b;
		computerMakeTheMove(a,b,c,d);
	}

	public static void computerMakeTheMove(String choices) {//casing function that converts String to int
		char tempA= choices.charAt(0);
		int a=tempA-'0';
		char tempB=choices.charAt(1);
		int b=tempB-'0';
		char tempC=choices.charAt(2);
		int c=tempC-'0';
		char tempD = choices.charAt(3);
		int d=tempD-'0';

		if (optionalEat.length()!=0) 
			computerMakeTheEat(a,b,c,d);
		else
			computerMakeTheMove(a,b,c,d);	

	}
	public static void computerMakeTheMove(int a, int b, int c, int d) {//this function updates the board after move and checks after eat if another eat is possible
		
		board[a][b]=current;
		board[c][d]="*";
		isNewQueen(a,b);
		System.out.println("Computer has played.");
		printBoard();
		optionalEat="";
		optionalMoves="";
		if (eat) {
			if(!isQueen(a,b))
				current="W";
			if (checkDoubleEat(currentEatMove[0],currentEatMove[1]))
				computerPlayTurn();
			else {
				eat=false;
				userPlayTurn();		
			}
		}
		else
			userPlayTurn();
	}

	public static String makeRandomMove(String choices) {//this function choosing random eat/move from the possible eat/moves
		double numOfOptions;
		if(choices.length()==4)
			return choices;
		else {
			numOfOptions=(choices.length())/4;
			int place=(int)(Math.random()*numOfOptions);
			place=Math.abs((place*4)-4);
			return choices.substring(place, place+4);
		}
	}

	public static void isComputerCanMove(int c, int d) {//checks if the conditions for move is true
		BiasLeftDown(c+1,d-1,c,d);
		BiasRightDown(c+1,d+1,c,d);
		if(isQueen(c,d)) {
			BiasLeftUp(c-1,d-1,c,d);
			BiasRightUp(c-1,d+1,c,d);
		}
	}

	public static void isComputerCanEat(int c, int d) {//checks if the conditions for eat is true
		canEatRightDown(c+2,d+2,c,d);
		canEatLeftDown(c+2,d-2,c,d);
		if(doubleEat || isQueen(c,d))
			canEatAllWays(c,d);
	}

	public static void canEatAllWays(int c, int d) {//adds more possible moves for queen/doubleEat
		canEatRightUp(c-2,d+2,c,d);	
		canEatLeftUp(c-2,d-2,c,d);		
	}

	public static boolean optionalMove(int a,int b,int c,int d ) {
		if((board[c][d].equals(current)||(board[c][d].equals("Q-"+current))) && (board[a][b].equals("*"))) 
			return true;
		return false;
	}

	public static boolean BiasLeftUp(int a,int b,int c,int d )  {//check move conditions among the board borders
		if((c-1)>=0 && (d-1)>=0)	{
			if ((c-a)==1 && (d-b)==1 && optionalMove(a,b,c,d)) {
				if(isUserPlayer()) 
					return true;
				else if (isComputerPlayer()) {
					optionalMoves=optionalMoves+a+b+c+d;
					return true;
				}
			}
		}
		return false;
	}
	public static boolean BiasRightUp(int a,int b,int c,int d )  {//check move conditions among the board borders
		if((c-1)>=0 && (d+1)<=7) {
			if ((c-a)==1 &&(d-b)==-1 && optionalMove(a,b,c,d)) {
				if(isUserPlayer()) 
					return true;
				else if (isComputerPlayer()) {
					optionalMoves=optionalMoves+a+b+c+d;
					return true;
				}
			}
		}
		return false;
	}

	public static boolean BiasLeftDown(int a,int b,int c,int d )  {//check move conditions among the board borders
		if((c+1)<=7 && (d-1)>=0) {
			if ((c-a)==-1 &&(d-b)==1 && optionalMove(a,b,c,d)) { 
				if(isUserPlayer())
					return true;
				else if (isComputerPlayer()) {
					optionalMoves=optionalMoves+a+b+c+d;
					return true;
				}
			}
		}
		return false;
	}

	public static boolean BiasRightDown(int a,int b,int c,int d )  {//check move conditions among the board borders
		if((c+1)<=7 && (d+1)<=7) {
			if ((c-a)==-1 &&(d-b)==-1 && optionalMove(a,b,c,d)) {
				if(isUserPlayer())
					return true;
				else if (isComputerPlayer()) {
					optionalMoves=optionalMoves+a+b+c+d;
					return true;
				}
			}
		}
		return false;
	}

	public static boolean canEatRightUp(int a,int b,int c,int d) {//check eat conditions among the board borders
		if((d+2)<=7 && (c-2)>=0) {
			if ((c-a)==2 && (d-b)==-2 && (board[a][b].equals("*"))) {  //bias right
				if(((board[c-1][d+1].equals(opponent))||(board[c-1][d+1].equals("Q-"+opponent)))){
					optionalEat=optionalEat+a+b+c+d;//adds possible eat to the computer String
					return true;
				}
			}
		}
		return false;
	}
	public static boolean canEatLeftUp(int a,int b,int c,int d) {//check move conditions among the board borders
		if((c-2)>=0 && (d-2)>=0)	{
			if ((c-a)==2 && (d-b)==2 && (board[a][b].equals("*"))) {  //bias left
				if((((board[c-1][d-1].equals(opponent))||(board[c-1][d-1].equals("Q-"+opponent))))){
					optionalEat=optionalEat+a+b+c+d;//adds possible eat to the computer String
					return true;
				}
			}
		}
		return false;
	}

	public static boolean canEatLeftDown(int a, int b, int c, int d) {//check move conditions among the board borders
		if((d-2)>=0 && (c+2)<=7) {
			if ((c-a)==-2 && (d-b)==2 && (board[a][b].equals("*"))){ //bias right
				if(((board[c+1][d-1].equals(opponent))||(board[c+1][d-1].equals("Q-"+opponent)))) {
					optionalEat=optionalEat+a+b+c+d;//adds possible eat to the computer String
					return true;
				}
			}
		}
		return false;
	}
	public static boolean canEatRightDown(int a, int b, int c, int d) {//check move conditions among the board borders
		if((c+2)<=7 && (d+2)<=7) {
			if ((c-a)==-2 && (d-b)==-2&& (board[a][b].equals("*"))) { //bias right
				if((board[c+1][d+1].equals(opponent))||(board[c+1][d+1].equals("Q-"+opponent))) {
					optionalEat=optionalEat+a+b+c+d;//adds possible eat to the computer String
					return true;
				}
			}
		}
		return false;
	}
	public static boolean isUserPlayer() {
		if (current.equals("R")||current.equals("Q-R"))
			return true;
		return false;
	}
	public static boolean isComputerPlayer() {
		if (current.equals("W")|| current.equals("Q-W"))
			return true;
		return false;
	}

	public static void checkQueenEat(int a,int b,int c,int d )  {//check the possible eats for queen
		if(canEatRightDown(a,b,c,d)||canEatLeftDown(a,b,c,d)||canEatRightUp(a,b,c,d)||canEatLeftUp(a,b,c,d))
			eat(a,b,c,d);
		if(eat && checkDoubleEat(c,d)) { 
			if(current.equals("R")) 
				userPlayTurn();
			else 
				computerPlayTurn();
		}
		else if(current.equals("R")) {
			eat=false;
			computerPlayTurn();
		}
		else {
			eat=false;
			userPlayTurn();
		}
	}

	public static void isNewQueen(int a,int b) {//checks if there is new queen among the checkers rules
		if (a==0 && board[a][b].equals("R"))
			board[a][b]="Q-R";
		else if (a==7 && board[a][b].equals("W"))
			board[a][b]="Q-W";
	}

	public static boolean isQueen(int c,int d) {//checks if the current place is queen
		if (board[c][d].equals("Q-W") ||board[c][d].equals("Q-R")) {
		
			return true;
		}
		else
			return false;

	}
	public static boolean isTheGameOver() {//checks the options for gameOver

		int counterUser=0;
		int counterComputer=0;
		for(int i=0;i<row;i++) {
			for(int j=0;j<column;j++) {
				if(board[i][j].equals("R")|| board[i][j].equals("Q-R"))
					counterUser++;
				else if(board[i][j].equals("W")|| board[i][j].equals("Q-W"))
					counterComputer++;
			}
		}
		if(counterUser==0) {
			System.out.println("Sorry, computer has won :(");
			return true;
		}
		else if(counterComputer==0) {
			System.out.println("Congratulations, user has won :)");
			return true;
		}
		else
			return checkTie();
	}
	public static boolean checkTie() {//checks if there is no possible moves for the player/computer
		keepAllComputerOptions();
		if((optionalEat.equals("") && optionalMoves.equals("")) || userStuck()) {
			System.out.println("Well, unfortunately it’s a Tie…");
			return true;
		}
		return false;
	}

	public static boolean userStuck() {//if there is no possible moves>>user stuck
		for(int i=0;i<row;i++) {
			for(int j=0;j<column;j++) {
				if (BiasLeftUp(i-1,j-1,i,j) || BiasRightUp(i-1,j+1,i,j))
					return false;
				if(board[i][j].equals("Q-R")) {
					if(BiasRightDown(i+1,j+1,i,j)||BiasLeftDown(i+1,j-1,i,j))
						return false;
				}
				if(canEatRightUp(i-2,j+2,i,j)||canEatLeftUp(i-2,j-2,i,j))
					return false;
				if(board[i][j].equals("Q-R")) {
					if(canEatRightDown(i+2,j+2,i,j)|| canEatLeftDown(i+2,j-2,i,j))
						return false;
				}
			}
		}
		return true;
	}
}


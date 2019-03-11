 /***
		  * NOTE:
		  * The following project notes allude to an AI opponent.
		  * This was a potential extra credit feature.
		  * As I had already gotten plenty of extra credit from utilizing
		  * the GUI library in this project, I decided to move on from this
		  * project and not add AI. I may do that later just for fun or something.


		  * How I'm going to implement players and AI
		  * There will be a function that just places game pieces.
		  * boolean takeYourTurn(boolean isPlayer1, int row, int column)
		  * There will also be a function that takes input (separate from this).
		  * That function will only be called if the player is human.
		  * 
		  * The AI will be a very elaborate function, perhaps calling other functions
		  * (Okay, I should probably just make it a class then).
		  * It doesn't need to be in the same package because
		  * GameBoard is a public class. It can read the board and set game pieces
		  * by calling GameBoard's public methods.
		  * 
		  * 
		  * 1st, write the answer-receiving function.
		  * 		Done. Actually, GameBoard.SetSpace() suffices...
		  * 
		  * Then implement players. 
		  * 	Simplest way will PROBABLY be to just do it like the knights?
		  * 
		  * AI should be easy, if tedious, to program.
		  * First priority is winning. For each win there are 3 "final moves"
		  * to initiate the win. Priority 1 is scanning for possible wins
		  * and taking them. Priority 2, which can reuse the scanning code from
		  * priority 1, is finding the player's winning moves and blocking them.
		  * Priority 3 is initiating a guaranteed win scenario.
		  * Priority 4 is blocking the opponent's guaranteed win scenario.
		  *
		  * 
		  * ***/

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TicTacToeDriver {
	public static void main(String[] args) {
		
		 char restart;					//Used to reset the game
		 int winType = 0;				//Stores what type of win condition, if any, was met
		 int winner = 0;				//says 1 or 2 when player 1 or 2 wins. 0 if Cat's game
		 String endMsg;
		 
		 
		 do {
			 GameBoard gb = new GameBoard();
			 
			 JOptionPane.showMessageDialog(null, "Welcome to TicTacToe(r)!\n An original game by Carlos Diaz (copyright pending)!");
			 /*This loop contains the meat of the code.
			  * */
			 do {
				//JOptionPane.showMessageDialog(null, "Player 1(X's), it's your turn!");
				 makeMove(gb,SpaceVal.EX);
				 winType = winCheck(gb);
				 if (winType > 0) {
					 winner = 1;
					 break;
				 }
				 if (boardFull(gb))
					 break;
				 //JOptionPane.showMessageDialog(null, "Player 2(O's), it's your turn!");

				 makeMove(gb,SpaceVal.OH); 
				 winType = winCheck(gb);
				 if (winType > 0) {
					 winner = 2;
					 break;
				 }
				 if (boardFull(gb))
					 break;
			 }while(true);
			 
			 if (winner > 0) {
				endMsg = "Player " + winner + "wins!\n";
				switch(winType) {
					case 1:
						endMsg += "Top Row!";
						break;
					case 2:
						endMsg += "Middle Row!";
						break;
					case 3:
						endMsg += "Bottom Row!";
						break;
					case 4:
						endMsg += "Left Column!";
						break;
					case 5:
						endMsg += "Middle Column!";
						break;
					case 6:
						endMsg += "Right Column!";
						break;
					case 7:
						endMsg += "Back Slash Diagonal!";
						break;
					case 8:
						endMsg += "Forward Slash Diagonal!";
						break;
					default:
						//This actually printed once during testing and I died inside.
						endMsg += "Congratulations! Your code is buggy!";
				}
			 }
			 else 
				 endMsg = "Cat's game! Nobody wins!";
			 endMsg += "\nType \"r\" to restart and play again, anything else to exit!";
			 
			 endMsg = printBoard(gb, endMsg);
			 
			 restart = JOptionPane.showInputDialog(endMsg).charAt(0);
			 
		 }while (restart == 'r');
	}
	
	//checks if the board is full
	static boolean boardFull(GameBoard gb) {
		
		for (int i=0; i<3; i++)
			for (int j=0; j<3; j++)
				if (gb.getSpace(i,j)==SpaceVal.EMPTY)
					return false;		
		
		return true;
		
	}
	
	//Called so players can make a move.
	static void makeMove(GameBoard gb, SpaceVal sv) {
		String move;					//stores the move the user types
		String mkMove;
		boolean validMove;				//if false, make user try again.
		int row, col;					//used in GameBoard.setSpace(r,c).
		
		if (sv == SpaceVal.EX)
			mkMove = "Player 1 (X's),";
		else
			mkMove = "Player 2 (O's),";
				
		//I insert this into the printing of the board down below
		mkMove 	 +=   " Make Your Move!\n"
				 + "To put a piece in the top-left"
				 + " corner, type \"a1\" not \"1a.\"";
		
		//Now there's a message within the board printout
		//giving instructions and whatnot
		String board = printBoard(gb, mkMove);
		
		
		do {
			
			move = JOptionPane.showInputDialog(board);
			row = (int)move.charAt(0)-97; //extracting ascii val of 1st char
			col = (int)move.charAt(1)-49; //if they type a1, col = 0; a2, col = 1
		
			validMove = gb.setSpace(row, col, sv);
			if (!validMove)
				JOptionPane.showMessageDialog(null, "Oops! can't do that! try again!");
		}while(validMove == false);
	}
	
	 static String printBoard(GameBoard gb, String s) {
		
		/*This char array will fill up with the X's and O's on the board
		 *for easier printing.
		 */
		String[] c = new String[9];			//array used for printing
		int cnt = 0;
		
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				if (gb.getSpace(i, j) == SpaceVal.EX)
					c[cnt] = "X";
				else if (gb.getSpace(i, j)==SpaceVal.OH)
					c[cnt] = "O";
				else
					c[cnt] = "  ";
				cnt++;
			}
				
	      String ret = "             FSU\n"  
				     + "_______________________________\n\n"
							 
				     + s
				     
				     + "\n\n            1 2 3\n\n"
				       
				     + "        a  " + c[0] + '|' + c[1] + '|' + c[2] + '\n'
				     + "        b  " + c[3] + '|' + c[4] + '|' + c[5] + '\n'
				     + "        c  " + c[6] + '|' + c[7] + '|' + c[8] + '\n'
				     + "_______________________________\n"
				     + "    By Juan \"Carlos\" Diaz";
		
	return ret;
		
	}
	//Returning int for which TYPE of win instead of boolean for 
	//whether or not there IS a win makes the function more versatile.
	//Now I can output which type of win occurred!
	static int winCheck(GameBoard gb) {
		//Top row
		if (
				(gb.getSpace(0,0)==gb.getSpace(0,1)) && 
				(gb.getSpace(0,1)==gb.getSpace(0,2)) &&
				(gb.getSpace(0, 0) != SpaceVal.EMPTY)
		)
			return 1;
		
		//Middle Row
		else if (
				(gb.getSpace(1,0)==gb.getSpace(1,1)) && 
				(gb.getSpace(1,1)==gb.getSpace(1,2)) &&
				(gb.getSpace(1, 0) != SpaceVal.EMPTY)
		)
			return 2;
		
		//Bottom Row
		else if(
				(gb.getSpace(2,0)==gb.getSpace(2,1)) && 
				(gb.getSpace(2,1)==gb.getSpace(2,2)) &&
				(gb.getSpace(2, 0) != SpaceVal.EMPTY)
		)
			return 3;
		
		//Left Column
		else if(
				(gb.getSpace(0,0)==gb.getSpace(1,0)) && 
				(gb.getSpace(1,0)==gb.getSpace(2,0)) &&
				(gb.getSpace(0, 0) != SpaceVal.EMPTY)
		)
			return 4;
		
		//Middle Column
		else if(
				(gb.getSpace(0,1)==gb.getSpace(1,1)) && 
				(gb.getSpace(1,1)==gb.getSpace(2,1)) &&
				(gb.getSpace(0, 1) != SpaceVal.EMPTY)
		)
			return 5;
		
		//Top Column
		else if(
				(gb.getSpace(0,2)==gb.getSpace(1,2)) && 
				(gb.getSpace(1,2)==gb.getSpace(2,2)) &&
				(gb.getSpace(0, 2) != SpaceVal.EMPTY)
		)
			return 6;
		
		//Backslash Diagonal
		else if(
				(gb.getSpace(0,0)==gb.getSpace(1,1)) && 
				(gb.getSpace(1,1)==gb.getSpace(2,2)) &&
				(gb.getSpace(0, 0) != SpaceVal.EMPTY)
		)
			return 7;
		
		//Forward Slash Diagonal
		else if(
				(gb.getSpace(0,2)==gb.getSpace(1,1)) && 
				(gb.getSpace(1,1)==gb.getSpace(2,0)) &&
				(gb.getSpace(0, 2) != SpaceVal.EMPTY)
		)
			return 8;
		else
			return 0;
	}
}

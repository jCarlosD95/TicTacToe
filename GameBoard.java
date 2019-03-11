
public class GameBoard 
{

	private GameSpace [][] board;
	
	
	GameBoard(){
		board = new GameSpace[3][3];
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				board[i][j] = new GameSpace();
	}
	
	public SpaceVal getSpace(int row, int column)
	{return board[row][column].getVal();}
	
	public boolean setSpace(int row, int column, SpaceVal sv){
		
		//Prevents players from overwriting another player's board space
		//or othewrwise performing an illegal move
		if (
				(row > -1 && row < 3) && (column > -1 && column < 3) &&
				(board[row][column].getVal() == SpaceVal.EMPTY)
		) {
			board[row][column].setVal(sv);
			return true;
		}
		else
			return false;
	}
	
	

}


class GameSpace{

	private SpaceVal val;
	
	public GameSpace() 
	{val = SpaceVal.EMPTY;}
	
	public SpaceVal getVal()
	{return val;}
	
	public void setVal(SpaceVal sv) 
	{val = sv;}
}
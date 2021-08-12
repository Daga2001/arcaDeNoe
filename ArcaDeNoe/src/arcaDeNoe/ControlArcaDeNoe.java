/*
 * Programación Interactiva
 * Autor: David Alberto Guzmán - 201942789
 * MiniProyecto 2: Juego arca de Noe.
 */

package arcaDeNoe;

// TODO: Auto-generated Javadoc
/**
 * The Class ControlArcaDeNoe. This class allows to manage the states of the game and the score.
 */
public class ControlArcaDeNoe {

	//attributes
	
	/** The score. */
	private int stateGame, moves, score;
	
	/**
	 * Check state. Checks if the user scored or failed.
	 *
	 * @param twoEquals the two equals. This boolean must be the method which i used to check if there are two equivalent revealed cards.
	 * @param areThereTwoRevealed the are there two revealed. It must be the method used to check if there are two revealed cards.
	 * @param numberCards the number cards. It must be the size of the array list "cards"
	 */
	//states: 1 = fail, 2 = score, 3 = round over, 4 = game over, 5 = nothing happens;
	public void checkState(boolean twoEquals, boolean areThereTwoRevealed, int numberCards) {
		
		if(numberCards == 0) {
			stateGame = 3;
		}
		
		else if(score <= 0 && moves > 1) {
			stateGame = 4;
			moves = 0;
		}
		
		else if(areThereTwoRevealed) {
			
			if(twoEquals) {
				score++;
				moves++;
				stateGame = 2;
			}
			
			else if(!twoEquals && moves > 0) {		
				
				if(score > 0) {
					score--;
				}
				
				moves++;
				stateGame = 1;						
			}
			
			else if(!twoEquals){
				moves++;
				stateGame = 1;
			}
			
		}
		
		else {
			stateGame = 5;
		}
		
	}

	/**
	 * Gets the state game. 
	 *
	 * @return the state game
	 */
	public int getStateGame() {
		return stateGame;
	}

	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	
}

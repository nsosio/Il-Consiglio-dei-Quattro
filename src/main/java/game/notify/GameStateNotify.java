package game.notify;

import java.util.List;

import game.GameState;
import game.Giocatore;
import gameDTO.gameDTO.GameStateDTO;
import view.clientNotify.ClientNotify;
import view.clientNotify.GameStateClientNotify;


public class GameStateNotify implements Notify{

	private GameState gameState;
	private List<Giocatore> giocatori;
	/**
	 * @param gameStateDTO
	 * @param giocatori
	 */
	public GameStateNotify(GameState gameState, List<Giocatore> giocatori) {
		if(gameState==null)
			throw new NullPointerException("Il gamestate non può essere null ");
		if(giocatori==null)
			throw new NullPointerException("I giocatori non possono essere null ");
		this.gameState = gameState;
		this.giocatori = giocatori;
	}
	@Override
	public boolean daInviare(Giocatore giocatore) {
		if (giocatore == null)
			throw new NullPointerException("Il giocatore non può essere null");
		else
			return giocatori.contains(giocatore);
	}
	
	@Override
	public ClientNotify notifyToClientNotify() {
		GameStateDTO gameStateDTO = new GameStateDTO();
		gameStateDTO.inizializza(gameState);
		
		return new GameStateClientNotify(gameStateDTO);
	}
	
	
	
	
}



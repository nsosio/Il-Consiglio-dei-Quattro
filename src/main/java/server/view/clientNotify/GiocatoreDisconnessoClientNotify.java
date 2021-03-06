package server.view.clientNotify;

import client.grafica.Grafica;
import common.gameDTO.GameStateDTO;
import common.gameDTO.GiocatoreDTO;

public class GiocatoreDisconnessoClientNotify implements ClientNotify {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3480623132189967003L;
	private GiocatoreDTO giocatoreDisconnesso;

	/**
	 * notify when one player exit the game
	 * 
	 * @param giocatoreDisconnesso
	 */
	public GiocatoreDisconnessoClientNotify(GiocatoreDTO giocatoreDisconnesso) {
		this.giocatoreDisconnesso = giocatoreDisconnesso;
	}

	/**
	 * show message
	 */
	@Override
	public void stamp(Grafica grafica, GameStateDTO gameStateDTO) {
		String messaggio = "Il giocatore " + giocatoreDisconnesso.getNome() + " ha abbandonato la partita\n";
		grafica.mostraMessaggio(messaggio);
	}

}

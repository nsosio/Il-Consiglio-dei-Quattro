package server.model.notify;

import java.util.List;

import server.model.game.Giocatore;
import server.view.clientNotify.ClassificaClientNotify;
import server.view.clientNotify.ClientNotify;

public class ClassificaNotify implements Notify {

	private List<Giocatore> vincitori;
	private List<Giocatore> perdenti;
	private List<Giocatore> giocatori;

	/**
	 * notify the ranking
	 * 
	 * @param vincitori
	 * @param perdenti
	 * @param giocatori
	 */
	public ClassificaNotify(List<Giocatore> vincitori, List<Giocatore> perdenti, List<Giocatore> giocatori) {

		if(vincitori==null)
			throw new NullPointerException("La lista di vincitori non può essere null");
		if (perdenti == null)
			throw new NullPointerException("La lista di perdenti non può essere null");
		if (giocatori == null)
			throw new NullPointerException("La lista di giocatori non può essere null");
		this.vincitori = vincitori;
		this.perdenti = perdenti;
		this.giocatori = giocatori;
	}

	/**
	 * notified players
	 */
	@Override
	public boolean daInviare(Giocatore giocatore) {
		return giocatori.contains(giocatore);
	}

	/**
	 * create client notify
	 */
	@Override
	public ClientNotify notifyToClientNotify() {
		return new ClassificaClientNotify(vincitori, perdenti);
	}

}

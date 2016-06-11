package server.model.azioni.azioniMarket;

import java.util.Arrays;

import common.azioniDTO.AzioneAcquistoDTO;
import common.azioniDTO.AzioneDTO;
import server.model.azioni.Azione;
import server.model.game.GameState;
import server.model.game.Giocatore;
import server.model.macchinaStati.StartEnd;
import server.model.macchinaStati.StatoAcquistoMarket;
import server.model.market.Offerta;
import server.model.notify.MessageNotify;

public class AzioneAcquisto extends Azione {

	private final int ID=12;
	private Offerta offerta;
	private Giocatore acquirente;

	@Override
	public void eseguiAzione(GameState gameState){
		if (!offerta.getMarketable().acquista(acquirente, offerta)){
			gameState.notifyObserver(new MessageNotify("L'azione non è eseguibile",
					Arrays.asList(gameState.getGiocatoreCorrente())));
		}
		else {
			if (!gameState.getOfferteMarket().isEmpty()) {
				gameState.getOfferteMarket().remove(this.offerta);
				gameState.getStato().transizioneOfferta(gameState);
			}
			else
				gameState.setStato(new StartEnd(gameState));
		}
	}

	/**
	 * @return the offerta
	 */
	public Offerta getOfferta() {
		return offerta;
	}

	/**
	 * @param offerta
	 *            the offerta to set
	 */
	public void setOfferta(Offerta offerta) {
		this.offerta = offerta;
	}

	/**
	 * @return the acquirente
	 */
	public Giocatore getAcquirente() {
		return acquirente;
	}

	/**
	 * @param acquirente
	 *            the acquirente to set
	 */
	public void setAcquirente(Giocatore acquirente) {
		this.acquirente = acquirente;
	}

	@Override
	public boolean isTurno(Giocatore giocatore, GameState gameState) {
		return (giocatore.equals(((StatoAcquistoMarket) gameState.getStato()).getGiocatori().get(0)));
	}

	@Override
	public AzioneDTO getAzioneDTO() {
	return new AzioneAcquistoDTO();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Acquista  [Acquista]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AzioneAcquisto other = (AzioneAcquisto) obj;
		if (ID != other.ID)
			return false;
		return true;
	}



}
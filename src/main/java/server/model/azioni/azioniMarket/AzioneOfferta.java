package server.model.azioni.azioniMarket;

import java.util.Arrays;
import common.azioniDTO.AzioneDTO;
import common.azioniDTO.AzioneOffertaDTO;
import server.model.azioni.Azione;
import server.model.game.GameState;
import server.model.market.Marketable;
import server.model.market.Offerta;
import server.model.notify.MessageNotify;

public class AzioneOfferta extends Azione {
	
	private Marketable marketable;
	private int prezzo;



	@Override
	public void eseguiAzione(GameState gameState){
		if(!marketable.possiede(gameState.getGiocatoreCorrente()))
			gameState.notifyObserver(new MessageNotify("Questa offerta non è effettuabile\n",
					Arrays.asList(gameState.getGiocatoreCorrente()), true));
		else{
			gameState.getOfferteMarket().add(new Offerta(gameState.getGiocatoreCorrente(), marketable, prezzo));
			gameState.getStato().transizioneOfferta(gameState);
		}
	}


	/**
	 * @param marketable the marketable to set
	 */
	public void setMarketable(Marketable marketable) {
		if(marketable==null)
			throw new NullPointerException("L'oggeto in vendita deve essere diverso da null");
		this.marketable = marketable;
	}



	/**
	 * @param prezzo the prezzo to set
	 */
	public void setPrezzo(int prezzo) {
		if(prezzo<0)
			throw new IllegalArgumentException("Il prezzo della vendita non può essere negativo");
		this.prezzo = prezzo;
	}


	@Override
	public AzioneDTO getAzioneDTO() {
		return new AzioneOffertaDTO();
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Fai un'offerta  [Offerta]";
	}

}

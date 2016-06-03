package game;

import game.market.Marketable;
import game.market.Offerta;
import gameDTO.gameDTO.CartaPoliticaDTO;
import gameDTO.gameDTO.MarketableDTO;

public class CartaPolitica implements Marketable {

	private final Colore colore;
	
	/**
	 * @param colore
	 */
	public CartaPolitica(Colore colore) {
		this.colore = colore;
	}


	/**
	 * @return the colore
	 */
	public Colore getColore() {
		return colore;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colore == null) ? 0 : colore.hashCode());
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
		CartaPolitica other = (CartaPolitica) obj;
		if (colore == null) {
			if (other.colore != null)
				return false;
		} else if (!colore.equals(other.colore))
			return false;
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return colore.toString();
	}


	@Override
	public boolean acquista(Giocatore acquirente, Offerta offerta) {
		if(!acquirente.diminuisciRicchezza(offerta.getPrezzo()))
			return false;
		else{
			acquirente.aggiungiCartaPolitica(this);
			offerta.getVenditore().rimuoviCartaPolitica(this);
			offerta.getVenditore().aumentaRicchezza(offerta.getPrezzo());
			return true;
		}
	}


	@Override
	public boolean possiede(Giocatore venditore) {
		if(!venditore.getCartePolitica().contains(this))
			return false;
		else
			return true;
	}


	@Override
	public MarketableDTO instance() {
		return new CartaPoliticaDTO();
	}
	
	


}

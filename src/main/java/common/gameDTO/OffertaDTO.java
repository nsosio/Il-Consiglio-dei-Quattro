package common.gameDTO;

import java.io.Serializable;

import server.model.market.Offerta;

public class OffertaDTO implements Serializable {

	/**
	 * serial version for serializable object
	 */
	private static final long serialVersionUID = -2991358938693502495L;
	/**
	 * current player in market state
	 */
	private GiocatoreDTO giocatoreDTO;
	/**
	 * object that can be sell in market
	 */
	private MarketableDTO marketableDTO;
	/**
	 * set price
	 */
	private int prezzo;

	/**
	 * @return the marketableDTO
	 */
	public MarketableDTO getMarketableDTO() {
		return marketableDTO;
	}

	/**
	 * @param marketableDTO
	 *            the marketableDTO to set
	 */
	public void setMarketableDTO(MarketableDTO marketableDTO) {
		this.marketableDTO = marketableDTO;
	}

	/**
	 * @return the prezzo
	 */
	public int getPrezzo() {
		return prezzo;
	}

	/**
	 * @return the giocatoreDTO
	 */
	public GiocatoreDTO getGiocatoreDTO() {
		return giocatoreDTO;
	}

	/**
	 * @param giocatoreDTO
	 *            the giocatoreDTO to set
	 */
	public void setGiocatoreDTO(GiocatoreDTO giocatoreDTO) {
		this.giocatoreDTO = giocatoreDTO;
	}

	/**
	 * @param prezzo
	 *            the prezzo to set
	 */
	public void setPrezzo(int prezzo) {
		this.prezzo = prezzo;
	}

	/**
	 * inizialization
	 */
	public void inizializza(Offerta o) {
		this.giocatoreDTO = new GiocatoreDTO();
		this.giocatoreDTO.inizializza(o.getVenditore());
		this.setPrezzo(o.getPrezzo());
		this.marketableDTO = o.getMarketable().instance();
		this.marketableDTO.creaMarketableDTO(o.getMarketable());
	}

}

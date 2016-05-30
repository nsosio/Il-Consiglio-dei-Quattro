package gameDTO.gameDTO;

import java.io.Serializable;

import game.CartaPolitica;

public class CartaPoliticaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6234645852702284026L;
	private String colore;

	/**
	 * @return the colore
	 */
	public String getColore() {
		return colore;
	}

	/**
	 * @param colore the colore to set
	 */
	public void setColore(String colore) {
		this.colore = colore;
	}
	
	public void inizializza(CartaPolitica cartaPolitica){
		this.setColore(cartaPolitica.getColore().getColore());
	}
	
	
	
}
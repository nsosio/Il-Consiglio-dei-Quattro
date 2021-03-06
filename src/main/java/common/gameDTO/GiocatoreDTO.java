package common.gameDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import common.azioniDTO.AzioneDTO;
import server.model.bonus.Bonus;
import server.model.bonus.BonusPuntiVittoria;
import server.model.game.CartaPolitica;
import server.model.game.Giocatore;
import server.model.game.TesseraPermesso;

public class GiocatoreDTO implements Serializable {

	/**
	 * serial version for serializable object
	 */
	private static final long serialVersionUID = -2107420956593617286L;

	/*
	 * + name of the player
	 */
	private String nome;
	/**
	 * color of the player
	 */
	private ColoreDTO coloreGiocatore;
	/**
	 * politic cards of the player
	 */
	private List<CartaPoliticaDTO> cartePolitica;
	/**
	 * discovery permit tile of the player
	 */
	private List<TesseraPermessoDTO> tesserePermesso;
	/**
	 * covery permit tile of the player
	 */
	private List<TesseraPermessoDTO> tesserePermessoUsate;
	/**
	 * bonus tiles
	 */
	private int tessereBonus;
	/**
	 * emporiums of the player
	 */
	private int empori;
	/**
	 * helpers of the player
	 */
	private int aiutanti;
	/**
	 * victory points of the player
	 */
	private int punteggioVittoria;
	/**
	 * richness points of the player
	 */
	private int punteggioRicchezza;
	/**
	 * nobility points of the players
	 */
	private int punteggioNobiltà;
	/**
	 * interactive bonus of nobility track available
	 */
	private List<AzioneDTO> bonusNobiltà;

	/**
	 * initializing permit tile and politic cards
	 */
	public GiocatoreDTO() {
		this.cartePolitica = new ArrayList<>();
		this.tesserePermesso = new ArrayList<>();
		this.tesserePermessoUsate = new ArrayList<>();
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the coloreGiocatore
	 */
	public ColoreDTO getColoreGiocatore() {
		return coloreGiocatore;
	}

	/**
	 * @param coloreGiocatore
	 *            the coloreGiocatore to set
	 */
	public void setColoreGiocatore(ColoreDTO coloreGiocatore) {
		this.coloreGiocatore = coloreGiocatore;
	}

	/**
	 * @return the cartePolitica
	 */
	public List<CartaPoliticaDTO> getCartePolitica() {
		return cartePolitica;
	}

	/**
	 * @param cartePolitica
	 *            the cartePolitica to set
	 */
	public void setCartePolitica(List<CartaPoliticaDTO> cartePolitica) {
		this.cartePolitica = cartePolitica;
	}

	/**
	 * @return the tesserePermesso
	 */
	public List<TesseraPermessoDTO> getTesserePermesso() {
		return tesserePermesso;
	}

	/**
	 * @param tesserePermesso
	 *            the tesserePermesso to set
	 */
	public void setTesserePermesso(List<TesseraPermessoDTO> tesserePermesso) {
		this.tesserePermesso = tesserePermesso;
	}

	/**
	 * @return the tesserePermessoUsate
	 */
	public List<TesseraPermessoDTO> getTesserePermessoUsate() {
		return tesserePermessoUsate;
	}

	/**
	 * @param tesserePermessoUsate
	 *            the tesserePermessoUsate to set
	 */
	public void setTesserePermessoUsate(List<TesseraPermessoDTO> tesserePermessoUsate) {
		this.tesserePermessoUsate = tesserePermessoUsate;
	}

	/**
	 * @return the tessereBonus
	 */
	public int getTessereBonus() {
		return tessereBonus;
	}

	/**
	 * @param tessereBonus
	 *            the tessereBonus to set
	 */
	public void setTessereBonus(int tessereBonus) {
		if (tessereBonus < 0)
			throw new IllegalArgumentException("Il valore delle tessere bonus non pò essere negativo");
		this.tessereBonus = tessereBonus;
	}

	/**
	 * @return the empori
	 */
	public int getEmpori() {
		return empori;
	}

	/**
	 * @param empori
	 *            the empori to set
	 */
	public void setEmpori(int empori) {
		if (empori < 0)
			throw new IllegalArgumentException("Il numero degli empori non pò essere negativo");
		this.empori = empori;
	}

	/**
	 * @return the aiutanti
	 */
	public int getAiutanti() {
		return aiutanti;
	}

	/**
	 * @param aiutanti
	 *            the aiutanti to set
	 */
	public void setAiutanti(int aiutanti) {
		if (aiutanti < 0)
			throw new IllegalArgumentException("Il numero degli aiutanti non pò essere negativo");
		this.aiutanti = aiutanti;
	}

	/**
	 * @return the punteggioVittoria
	 */
	public int getPunteggioVittoria() {
		return punteggioVittoria;
	}

	/**
	 * @param punteggioVittoria
	 *            the punteggioVittoria to set
	 */
	public void setPunteggioVittoria(int punteggioVittoria) {
		if (punteggioVittoria < 0)
			throw new IllegalArgumentException("Il valore del punteggio non pò essere negativo");
		this.punteggioVittoria = punteggioVittoria;
	}

	/**
	 * @return the punteggioRicchezza
	 */
	public int getPunteggioRicchezza() {
		return punteggioRicchezza;
	}

	/**
	 * @param punteggioRicchezza
	 *            the punteggioRicchezza to set
	 */
	public void setPunteggioRicchezza(int punteggioRicchezza) {
		if (punteggioRicchezza < 0)
			throw new IllegalArgumentException("Il valore del punteggio ricchezza non pò essere negativo");
		this.punteggioRicchezza = punteggioRicchezza;
	}

	/**
	 * @return the punteggioNobiltà
	 */
	public int getPunteggioNobiltà() {
		return punteggioNobiltà;
	}

	/**
	 * @param punteggioNobiltà
	 *            the punteggioNobiltà to set
	 */
	public void setPunteggioNobiltà(int punteggioNobiltà) {
		if (punteggioNobiltà < 0)
			throw new IllegalArgumentException("Il valore del punteggio nobiltà non pò essere negativo");
		this.punteggioNobiltà = punteggioNobiltà;
	}

	/**
	 * initialization of player
	 * 
	 * @param giocatore
	 */
	public void inizializza(Giocatore giocatore) {

		this.setNome(giocatore.getNome());
		this.setAiutanti(giocatore.getAiutanti().getAiutante());
		this.cartePolitica = new ArrayList<>();
		for (CartaPolitica c : giocatore.getCartePolitica()) {
			CartaPoliticaDTO cartaPoliticaDTO = new CartaPoliticaDTO();
			cartaPoliticaDTO.inizializza(c);
			this.cartePolitica.add(cartaPoliticaDTO);
		}
		ColoreDTO coloreDTO = new ColoreDTO();
		coloreDTO.inizializza(giocatore.getColoreGiocatore());
		this.setColoreGiocatore(coloreDTO);
		this.setEmpori(giocatore.getEmpori().size());
		this.setPunteggioNobiltà(giocatore.getPunteggioNobiltà().getPuntiNobiltà());
		this.setPunteggioVittoria(giocatore.getPunteggioVittoria());
		this.setPunteggioRicchezza(giocatore.getPunteggioRicchezza());
		this.setTesserePermesso(new ArrayList<>());
		for (TesseraPermesso t : giocatore.getTesserePermesso()) {
			TesseraPermessoDTO tesseraPermessoDTO = new TesseraPermessoDTO();
			tesseraPermessoDTO.inizializza(t);
			this.tesserePermesso.add(tesseraPermessoDTO);
		}
		this.setTesserePermessoUsate(new ArrayList<>());
		for (TesseraPermesso t : giocatore.getTesserePermessoUsate()) {
			TesseraPermessoDTO tesseraPermessoUsataDTO = new TesseraPermessoDTO();
			tesseraPermessoUsataDTO.inizializza(t);
			this.tesserePermessoUsate.add(tesseraPermessoUsataDTO);
		}
		int n = 0;
		for (Bonus b : giocatore.getTessereBonus()) {
			n = n + ((BonusPuntiVittoria) b).getPuntiVittoria();
		}
		this.setTessereBonus(n);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "\n" + nome + " [ " + coloreGiocatore + " ]\ncartePolitica = " + cartePolitica + "\ntesserePermesso = "
				+ tesserePermesso + "\ntesserePermessoUsate = " + tesserePermessoUsate + "\ntessereBonus="
				+ tessereBonus + "\nempori = " + empori + "\naiutanti = " + aiutanti + "\npunteggioVittoria = "
				+ punteggioVittoria + "\npunteggioRicchezza = " + punteggioRicchezza + "\npunteggioNobiltà = "
				+ punteggioNobiltà;
	}

	/**
	 * @return the bonusNobiltà
	 */
	public List<AzioneDTO> getBonusNobiltà() {
		return bonusNobiltà;
	}

	/**
	 * @param bonus
	 *            the bonusNobiltà to set
	 */
	public void setBonusNobiltà(List<AzioneDTO> bonus) {
		if (bonus == null)
			throw new NullPointerException("Il bonus nobiltà del giocatore deve essere diverso da null");
		this.bonusNobiltà = bonus;
	}

	/*
	 *
	 * hashcode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coloreGiocatore == null) ? 0 : coloreGiocatore.hashCode());
		return result;
	}

	/*
	 * equals
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof GiocatoreDTO))
			return false;
		GiocatoreDTO other = (GiocatoreDTO) obj;
		if (coloreGiocatore == null) {
			if (other.coloreGiocatore != null)
				return false;
		} else if (!coloreGiocatore.equals(other.coloreGiocatore))
			return false;
		return true;
	}

}

package gameDTO.gameDTO;

import java.io.Serializable;
import java.util.ArrayList;

import bonus.Bonus;
import game.CartaPolitica;
import game.Giocatore;
import game.TesseraPermesso;

public class GiocatoreDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2107420956593617286L;

	private String nome; 
	private ColoreDTO coloreGiocatore;
	private ArrayList<CartaPoliticaDTO> cartePolitica;
	private ArrayList<TesseraPermessoDTO> tesserePermesso;
	private ArrayList<TesseraPermessoDTO> tesserePermessoUsate; 
	private ArrayList<Bonus> tessereBonus;
	private int empori;
	private int aiutanti;
	private int punteggioVittoria;
	private int punteggioRicchezza;
	private int punteggioNobiltà;
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}
	/**
	 * @param nome the nome to set
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
	 * @param coloreGiocatore the coloreGiocatore to set
	 */
	public void setColoreGiocatore(ColoreDTO coloreGiocatore) {
		this.coloreGiocatore = coloreGiocatore;
	}
	/**
	 * @return the cartePolitica
	 */
	public ArrayList<CartaPoliticaDTO> getCartePolitica() {
		return cartePolitica;
	}
	/**
	 * @param cartePolitica the cartePolitica to set
	 */
	public void setCartePolitica(ArrayList<CartaPoliticaDTO> cartePolitica) {
		this.cartePolitica = cartePolitica;
	}
	/**
	 * @return the tesserePermesso
	 */
	public ArrayList<TesseraPermessoDTO> getTesserePermesso() {
		return tesserePermesso;
	}
	/**
	 * @param tesserePermesso the tesserePermesso to set
	 */
	public void setTesserePermesso(ArrayList<TesseraPermessoDTO> tesserePermesso) {
		this.tesserePermesso = tesserePermesso;
	}
	/**
	 * @return the tesserePermessoUsate
	 */
	public ArrayList<TesseraPermessoDTO> getTesserePermessoUsate() {
		return tesserePermessoUsate;
	}
	/**
	 * @param tesserePermessoUsate the tesserePermessoUsate to set
	 */
	public void setTesserePermessoUsate(ArrayList<TesseraPermessoDTO> tesserePermessoUsate) {
		this.tesserePermessoUsate = tesserePermessoUsate;
	}
	/**
	 * @return the tessereBonus
	 */
	public ArrayList<Bonus> getTessereBonus() {
		return tessereBonus;
	}
	/**
	 * @param tessereBonus the tessereBonus to set
	 */
	public void setTessereBonus(ArrayList<Bonus> tessereBonus) {
		this.tessereBonus = tessereBonus;
	}
	/**
	 * @return the empori
	 */
	public int getEmpori() {
		return empori;
	}
	/**
	 * @param empori the empori to set
	 */
	public void setEmpori(int empori) {
		this.empori = empori;
	}
	/**
	 * @return the aiutanti
	 */
	public int getAiutanti() {
		return aiutanti;
	}
	/**
	 * @param aiutanti the aiutanti to set
	 */
	public void setAiutanti(int aiutanti) {
		this.aiutanti = aiutanti;
	}
	/**
	 * @return the punteggioVittoria
	 */
	public int getPunteggioVittoria() {
		return punteggioVittoria;
	}
	/**
	 * @param punteggioVittoria the punteggioVittoria to set
	 */
	public void setPunteggioVittoria(int punteggioVittoria) {
		this.punteggioVittoria = punteggioVittoria;
	}
	/**
	 * @return the punteggioRicchezza
	 */
	public int getPunteggioRicchezza() {
		return punteggioRicchezza;
	}
	/**
	 * @param punteggioRicchezza the punteggioRicchezza to set
	 */
	public void setPunteggioRicchezza(int punteggioRicchezza) {
		this.punteggioRicchezza = punteggioRicchezza;
	}
	/**
	 * @return the punteggioNobiltà
	 */
	public int getPunteggioNobiltà() {
		return punteggioNobiltà;
	}
	/**
	 * @param punteggioNobiltà the punteggioNobiltà to set
	 */
	public void setPunteggioNobiltà(int punteggioNobiltà) {
		this.punteggioNobiltà = punteggioNobiltà;
	}
	
	public void inizializza(Giocatore giocatore){
		this.setNome(giocatore.getNome());
		this.setAiutanti(giocatore.getAiutanti().getAiutante());
		this.cartePolitica=new ArrayList<>();
		for(CartaPolitica c: giocatore.getCartePolitica()){
			CartaPoliticaDTO cartaPoliticaDTO=new CartaPoliticaDTO();
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
		for ( TesseraPermesso t: giocatore.getTesserePermesso()){
			TesseraPermessoDTO tesseraPermessoDTO = new TesseraPermessoDTO();
			tesseraPermessoDTO.inizializza(t);
			this.tesserePermesso.add(tesseraPermessoDTO);
		}
		this.setTesserePermessoUsate(new ArrayList<>());
		for ( TesseraPermesso t: giocatore.getTesserePermessoUsate()){
			TesseraPermessoDTO tesseraPermessoUsataDTO = new TesseraPermessoDTO();
			tesseraPermessoUsataDTO.inizializza(t);
			this.tesserePermessoUsate.add(tesseraPermessoUsataDTO);
		}
		this.setTessereBonus(giocatore.getTessereBonus());	
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GiocatoreDTO [nome=" + nome + ", coloreGiocatore=" + coloreGiocatore + ", cartePolitica="
				+ cartePolitica + ", tesserePermesso=" + tesserePermesso + ", tesserePermessoUsate="
				+ tesserePermessoUsate + ", tessereBonus=" + tessereBonus + ", empori=" + empori + ", aiutanti="
				+ aiutanti + ", punteggioVittoria=" + punteggioVittoria + ", punteggioRicchezza=" + punteggioRicchezza
				+ ", punteggioNobiltà=" + punteggioNobiltà + "]";
	}
	
	
	
}
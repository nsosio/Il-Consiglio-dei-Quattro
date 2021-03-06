package common.gameDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import common.azioniDTO.AzioneDTO;
import server.model.game.Città;
import server.model.game.CittàBonus;
import server.model.game.Colore;
import server.model.game.ColoreCittà;
import server.model.game.Consigliere;
import server.model.game.GameState;
import server.model.game.Regione;
import server.model.game.comparator.ComparatorOrdineAlfabetico;

public class GameStateDTO implements Serializable {

	/**
	 * serial version for serializable object
	 */
	private static final long serialVersionUID = -8629825962043239713L;
	/**
	 * cities in the map
	 */
	private Set<CittàDTO> città;
	/**
	 * region in the map
	 */
	private List<RegioneDTO> regioni;
	/**
	 * king
	 */
	private ReDTO pedinaRE;
	/**
	 * plancia of king
	 */
	private PlanciaReDTO planciaReDTO;
	/**
	 * Counselor
	 */
	private List<ConsigliereDTO> consiglieri;
	/**
	 * available action
	 */
	private List<AzioneDTO> azioniDisponibili;
	/**
	 * player
	 */
	private GiocatoreDTO giocatoreDTO;
	/**
	 * players that aren't the current player
	 */
	private List<GiocatoreDTO> avversari;
	/**
	 * offer
	 */
	private List<OffertaDTO> offerte;
	/**
	 * color bonus tile
	 */
	private List<ColoreBonusDTO> bonusColore;
	/**
	 * name of map
	 */
	private String nomeMappa;

	/**
	 * start game
	 * 
	 * @param gameState
	 */
	public void inizializza(GameState gameState) {

		this.avversari = new ArrayList<>();
		this.bonusColore = new ArrayList<>();
		for (Colore colore : gameState.getBonusColore()) {
			if (colore instanceof ColoreCittà && !((ColoreCittà) colore).isAssegnatoBonus()) {
				ColoreBonusDTO coloreBonusDTO = new ColoreBonusDTO();
				coloreBonusDTO.inizializza((ColoreCittà) colore);
				this.bonusColore.add(coloreBonusDTO);
			}
		}

		this.città = new TreeSet<>(new ComparatorOrdineAlfabetico());
		for (Città c : gameState.getCittà()) {
			if (c instanceof CittàBonus) {
				CittàBonusDTO cittàBonusDTO = new CittàBonusDTO();
				cittàBonusDTO.inizializza((CittàBonus) c);
				città.add(cittàBonusDTO);
			} else {
				CittàDTO cittàDTO = new CittàDTO();
				cittàDTO.inizializza(c);
				città.add(cittàDTO);
			}
		}
		this.regioni = new ArrayList<>();
		for (Regione r : gameState.getRegioni()) {
			RegioneDTO regioneDTO = new RegioneDTO();
			regioneDTO.inizializza(r);
			regioni.add(regioneDTO);
		}
		ReDTO pedinaRe = new ReDTO();
		pedinaRe.inizializza(gameState.getPedinaRe());
		this.setPedinaRE(pedinaRe);
		planciaReDTO = new PlanciaReDTO();
		planciaReDTO.inizializza(gameState.getPlanciaRe());
		this.setPlanciaReDTO(planciaReDTO);
		this.consiglieri = new ArrayList<>();
		for (Consigliere c : gameState.getConsiglieri()) {
			ConsigliereDTO consigliereDTO = new ConsigliereDTO();
			consigliereDTO.inizializza(c);
			consiglieri.add(consigliereDTO);
		}
		this.offerte = new ArrayList<>();
		this.nomeMappa = gameState.getNomeMappa();
	}

	/**
	 * @return the città
	 */
	public Set<CittàDTO> getCittà() {
		return città;
	}

	/**
	 * @param città
	 *            the città to set
	 */
	public void setCittà(Set<CittàDTO> città) {
		if (città == null)
			throw new NullPointerException("Le città sono null");
		this.città = città;
	}

	/**
	 * @return the regioni
	 */
	public List<RegioneDTO> getRegioni() {
		return regioni;
	}

	/**
	 * @param regioni
	 *            the regioni to set
	 */
	public void setRegioni(List<RegioneDTO> regioni) {
		if (regioni == null)
			throw new NullPointerException("Le regioni sono null");
		this.regioni = regioni;
	}

	/**
	 * @return the pedinaRE
	 */
	public ReDTO getPedinaRE() {
		return pedinaRE;
	}

	/**
	 * @param pedinaRE
	 *            the pedinaRE to set
	 */
	public void setPedinaRE(ReDTO pedinaRE) {
		if (pedinaRE == null)
			throw new NullPointerException("La pedina del Re è null");
		this.pedinaRE = pedinaRE;
	}

	/**
	 * @return the planciaReDTO
	 */
	public PlanciaReDTO getPlanciaReDTO() {
		return planciaReDTO;
	}

	/**
	 * @param planciaReDTO
	 *            the planciaReDTO to set
	 */
	public void setPlanciaReDTO(PlanciaReDTO planciaReDTO) {
		if (planciaReDTO == null)
			throw new NullPointerException("La plancia del Re è null");
		this.planciaReDTO = planciaReDTO;
	}

	/**
	 * @return the consiglieri
	 */
	public List<ConsigliereDTO> getConsiglieri() {
		return consiglieri;
	}

	/**
	 * @param consiglieri
	 *            the consiglieri to set
	 */
	public void setConsiglieri(List<ConsigliereDTO> consiglieri) {
		if (consiglieri == null)
			throw new NullPointerException("I cosiglieri sono null");
		this.consiglieri = consiglieri;
	}

	public void setAzioni(List<AzioneDTO> azioni) {
		if (azioni == null)
			throw new NullPointerException("La lista di azioni è null");
		this.azioniDisponibili = azioni;
	}

	/**
	 * @return the azioniDisponibili
	 */
	public List<AzioneDTO> getAzioniDisponibili() {
		return azioniDisponibili;
	}

	public GiocatoreDTO getGiocatoreDTO() {
		return giocatoreDTO;
	}

	/**
	 * @param giocatoreDTO
	 *            the giocatoreDTO to set
	 */
	public void setGiocatoreDTO(GiocatoreDTO giocatoreDTO) {
		if (giocatoreDTO == null)
			throw new NullPointerException("Il giocatore è null");
		this.giocatoreDTO = giocatoreDTO;
	}

	/**
	 * @return the offerte
	 */
	public void setOfferte(List<OffertaDTO> offerte) {
		if (offerte == null)
			throw new NullPointerException("La lista di offerte è null");
		this.offerte = offerte;
	}

	/**
	 * @return the offerte
	 */
	public List<OffertaDTO> getOfferte() {
		return offerte;
	}

	public List<GiocatoreDTO> getAvversari() {

		return this.avversari;
	}

	public void setAvversari(List<GiocatoreDTO> avversari) {
		if (avversari == null)
			throw new NullPointerException("Ci deve essere almeno un avversario");
		this.avversari = avversari;
	}

	/*
	 * to string
	 */
	@Override
	public String toString() {
		return "GameStateDTO [mappa=" + città + "\nregioni=" + regioni + "\npedinaRE=" + pedinaRE + "\nplanciaReDTO="
				+ planciaReDTO + "\nconsiglieri=" + consiglieri + "]";
	}

	public void setNomeMappa(String mappa) {
		this.nomeMappa = mappa;
	}

	/**
	 * @return the nomeMappa
	 */
	public String getNomeMappa() {
		return nomeMappa;
	}

	/**
	 * @return the bonusColore
	 */
	public List<ColoreBonusDTO> getBonusColore() {
		return bonusColore;
	}

	/**
	 * @param bonusColore
	 *            the bonusColore to set
	 */
	public void setBonusColore(List<ColoreBonusDTO> bonusColore) {
		this.bonusColore = bonusColore;
	}

}

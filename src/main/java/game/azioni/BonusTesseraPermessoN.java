package game.azioni;

import game.GameState;
import game.Regione;
import game.TesseraPermesso;
import gameDTO.azioniDTO.AzioneDTO;
import gameDTO.azioniDTO.BonusTesseraPermessoNDTO;

public class BonusTesseraPermessoN extends Azione {

	/**
	 * Prendi una tesseraPermesso a faccia in su senza pagarne il costo
	 */
	
	private Regione regione;
	private TesseraPermesso tesseraScoperta;
	
	@Override
	public void eseguiAzione(GameState gameState){
		
		regione.getTesserePermessoScoperte().remove(tesseraScoperta);
		gameState.getGiocatoreCorrente().getTesserePermesso().add(tesseraScoperta);
		
		regione.getTesserePermessoScoperte().add(regione.getMazzoTesserePermesso().pescaCarte());
	}

	/**
	 * @return the regione
	 */
	public Regione getRegione() {
		return regione;
	}

	/**
	 * @param regione the regione to set
	 */
	public void setRegione(Regione regione) {
		this.regione = regione;
	}

	/**
	 * @return the indiceTesseraScoperta
	 */
	public TesseraPermesso getTesseraScoperta() {
		return tesseraScoperta;
	}

	/**
	 * @param indiceTesseraScoperta the indiceTesseraScoperta to set
	 */
	public void setTesseraScoperta(TesseraPermesso tesseraScoperta) {
		this.tesseraScoperta = tesseraScoperta;
	}

	@Override
	public AzioneDTO getAzioneDTO() {
		return new BonusTesseraPermessoNDTO();
	}
}

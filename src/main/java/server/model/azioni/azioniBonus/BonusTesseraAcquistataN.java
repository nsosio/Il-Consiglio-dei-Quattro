package server.model.azioni.azioniBonus;

import common.azioniDTO.AzioneDTO;
import common.azioniDTO.BonusTesseraAcquistataNDTO;
import server.model.azioni.Azione;
import server.model.bonus.Bonus;
import server.model.game.GameState;
import server.model.game.TesseraPermesso;

public class BonusTesseraAcquistataN extends Azione {
	/**
	 * ottieni i bonus di una tessera permesso che hai comprato in precedenza
	 * anche una di quelle a faccia in giù
	 */
	private final int ID = 14;
	private TesseraPermesso tesseraPermesso;

	@Override
	public void eseguiAzione(GameState gameState) {

		for (Bonus b : tesseraPermesso.getBonus()) {
			b.usaBonus(gameState);
		}
		gameState.getGiocatoreCorrente().getBonusNobiltà().remove(this);
		gameState.getStato().transizioneBonus(gameState);

	}

	/**
	 * @return the indiceTesseraPermesso
	 */
	public TesseraPermesso getTesseraPermesso() {
		return tesseraPermesso;
	}

	/**
	 * @param indiceTesseraPermesso
	 *            the indiceTesseraPermesso to set
	 */
	public void setTesseraPermesso(TesseraPermesso tesseraPermesso) {
		this.tesseraPermesso = tesseraPermesso;
	}

	@Override
	public AzioneDTO getAzioneDTO() {
		return new BonusTesseraAcquistataNDTO();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		BonusTesseraAcquistataN other = (BonusTesseraAcquistataN) obj;
		if (ID != other.ID)
			return false;
		return true;
	}

}
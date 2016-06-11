package common.azioniDTO;

import common.azioniDTO.azioneVisitor.AzioneVisitor;
import common.gameDTO.RegioneDTO;
import server.model.azioni.Azione;
import utility.ParameterException;

public class CambioTesserePermessoDTO implements AzioneDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2237104774390257699L;
	private RegioneDTO regione;


	/**
	 * @return the regione
	 */
	public RegioneDTO getRegione() {
		return regione;
	}

	/**
	 * @param regione the regione to set
	 */
	public void setRegione(RegioneDTO regione) {
		this.regione = regione;
	}

	@Override
	public Azione accept(AzioneVisitor azioneVisitor) throws ParameterException {
		return azioneVisitor.visit(this);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Cambiare le tessere permesso  [V2]";
	}
	
}
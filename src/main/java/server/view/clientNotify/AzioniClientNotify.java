package server.view.clientNotify;

import java.util.List;

import client.grafica.Grafica;
import common.azioniDTO.AzioneDTO;
import common.gameDTO.GameStateDTO;

public class AzioniClientNotify implements ClientNotify {

	private static final long serialVersionUID = -4132525333640241505L;
	private List<AzioneDTO> azioni;

	/**
	 * actions available notify
	 * 
	 * @param azioni
	 */
	public AzioniClientNotify(List<AzioneDTO> azioni) {
		if (azioni == null)
			throw new NullPointerException("Le azioni non possono essere null");
		this.azioni = azioni;
	}

	/**
	 * show available actions
	 */
	@Override
	public void stamp(Grafica grafica, GameStateDTO gameStateDTO) {
		gameStateDTO.setAzioni(azioni);
		grafica.mostraAzioni(azioni);
	}

}

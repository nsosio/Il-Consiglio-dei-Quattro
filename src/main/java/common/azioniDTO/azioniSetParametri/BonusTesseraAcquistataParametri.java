package common.azioniDTO.azioniSetParametri;

import client.grafica.Grafica;
import common.azioniDTO.BonusTesseraAcquistataNDTO;
import common.gameDTO.GameStateDTO;
import common.gameDTO.TesseraPermessoDTO;

public class BonusTesseraAcquistataParametri implements SetterParametri {

	private BonusTesseraAcquistataNDTO bonusTesseraAcquistataNDTO;

	public BonusTesseraAcquistataParametri(BonusTesseraAcquistataNDTO bonusTesseraAcquistataNDTO) {
		this.bonusTesseraAcquistataNDTO=bonusTesseraAcquistataNDTO;
	}

	@Override
	public void setParametri(Grafica grafica, GameStateDTO gameStateDTO) {
		TesseraPermessoDTO tesseraPermessoDTO;
		boolean usata=false;
		int tessera;
		if(gameStateDTO.getGiocatoreDTO().getTesserePermesso().isEmpty()){
			tessera=1;
		}
		if(gameStateDTO.getGiocatoreDTO().getTesserePermessoUsate().isEmpty()){
			tessera =2;
		}
		else{
			tessera=grafica.scegliUsataONonUsata();
		}
		
		if(tessera==1){
			tesseraPermessoDTO=grafica.scegliTesseraGiocatore(gameStateDTO.getGiocatoreDTO().getTesserePermessoUsate());
			usata=true;
			}
		else
			tesseraPermessoDTO=grafica.scegliTesseraGiocatore(gameStateDTO.getGiocatoreDTO().getTesserePermesso());
		bonusTesseraAcquistataNDTO.setTesseraPermesso(tesseraPermessoDTO);
		bonusTesseraAcquistataNDTO.setUsata(usata);
	}

}

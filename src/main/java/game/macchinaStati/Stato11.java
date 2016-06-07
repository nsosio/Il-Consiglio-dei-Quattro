package game.macchinaStati;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import game.GameState;
import game.azioni.AcquistoTesseraPermesso;
import game.azioni.Azione;
import game.azioni.CambioTesseraPermesso;
import game.azioni.CostruzioneAiutoRe;
import game.azioni.CostruzioneTesseraPermesso;
import game.azioni.ElezioneConsigliere;
import game.azioni.ElezioneConsigliereVeloce;
import game.azioni.IngaggioAiutante;
import game.azioni.Passa;
import game.azioni.SecondaAzionePrincipale;
import game.notify.AzioniNotify;
import game.notify.ErrorNotify;

public class Stato11 implements Stato {

	public Stato11(GameState gameState) {
		System.out.println("stato11");
		gameState.notifyObserver(new ErrorNotify("AZIONI PRINCIPALI E VELOCI", Arrays.asList(gameState.getGiocatoreCorrente())));
		gameState.notifyObserver(new AzioniNotify(this.getAzioni(), Arrays.asList(gameState.getGiocatoreCorrente())));
	}
	
	@Override
	public void transizionePrincipale(GameState gameState){
		if(!gameState.isBonusAzionePrincipale()){
		gameState.setStato(new Stato01(gameState));
		}
		else
			gameState.setBonusAzionePrincipale(false);
	}
	
	@Override
	public void transizioneBonus(GameState gameState){
		gameState.setStato(new StatoBonus(gameState,this));
	}
	
	@Override
	public void transizioneVeloce(GameState gameState){
		gameState.setStato(new Stato10(gameState));
	}
	
	@Override
	public List<Azione> getAzioni() {
		return Arrays.asList(new ElezioneConsigliere(), new AcquistoTesseraPermesso(),
				new CostruzioneTesseraPermesso(), new CostruzioneAiutoRe(), new IngaggioAiutante(), new CambioTesseraPermesso(), 
				new ElezioneConsigliereVeloce(), new SecondaAzionePrincipale());
	}

}

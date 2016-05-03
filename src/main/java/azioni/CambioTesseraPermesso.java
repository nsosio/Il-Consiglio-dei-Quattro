package azioni;

import java.util.ArrayList;

import game.Mazzo;
import game.Partita;
import game.Regione;
import game.TesseraPermesso;

public class CambioTesseraPermesso extends AzioneVeloce {

	private Regione regione;

	

	/**
	 * @param partita
	 * @param regione
	 */
	public CambioTesseraPermesso(Partita partita, Regione regione) {
		super(partita);
		this.regione = regione;
	}



	@Override
	public boolean eseguiAzione() {
		RimozioneCarte();
		SostituzioneCarte();
		return true;
	}
	
	/**
	 * remove cards from TesserePermesseScoperte and add to MazzoTesserePermesso
	 */
	private void RimozioneCarte(){
		
		ArrayList<TesseraPermesso> tessereDaRimuovere = regione.getTesserePermessoScoperte();
		Mazzo<TesseraPermesso> mazzoRegione = regione.getMazzoTesserePermesso();
		
		for (int indiceArray=0; indiceArray<tessereDaRimuovere.size(); indiceArray++){
			mazzoRegione.aggiungiCarta(tessereDaRimuovere.remove(indiceArray));
		}
	}
	
	/**
	 * remove cards from MazzoTesserePermesso and add cards to TesserePermessoScoperte
	 */
	private void SostituzioneCarte(){
		
		ArrayList<TesseraPermesso> tessereRegione = regione.getTesserePermessoScoperte();
		Mazzo<TesseraPermesso> mazzoRegione = regione.getMazzoTesserePermesso();
		
			for (int indiceArray=0; indiceArray<tessereRegione.size(); indiceArray++){
				tessereRegione.add(mazzoRegione.pescaCarte());
			}
	
		

		
	}
	
}

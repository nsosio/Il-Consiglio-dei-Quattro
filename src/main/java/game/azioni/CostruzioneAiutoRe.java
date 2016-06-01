package game.azioni;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import bonus.Bonus;
import game.Balcone;
import game.CartaPolitica;
import game.Città;
import game.CittàBonus;
import game.Colore;
import game.ColoreCittà;
import game.Consigliere;
import game.Emporio;
import game.GameState;
import game.Giocatore;
import game.Mappa;
import game.Regione;
import game.notify.ErrorNotify;
import game.notify.GameStateNotify;
import game.notify.GiocatoreNotify;

public class CostruzioneAiutoRe extends AzionePrincipale {

	private Città cittàCostruzione;
	private Balcone balcone;
	private ArrayList<CartaPolitica> carteGiocatore;
	
	/**
	 * execute the action
	 */
	@Override
	public void eseguiAzione(GameState gameState) {
		balcone=gameState.getPlanciaRe().getBalconeRe();
		Mappa mappa= gameState.getMappa();
		if(!controllaColori()){
			gameState.notifyObserver(new ErrorNotify("Errore: i colori delle carte scelte non corrispondon con quelli del balcone!", 
					Arrays.asList(gameState.getGiocatoreCorrente())));
			return;
		}
			
		
		int moneteDovute= calcolaMonete() + 
				2*mappa.minimaDistanza(gameState.getPedinaRe().getCittà(), cittàCostruzione);
		
		if(!paga(moneteDovute, gameState)){
			gameState.notifyObserver(new ErrorNotify("Errore: i soldi non sono sufficienti!",
					Arrays.asList(gameState.getGiocatoreCorrente())));
			return;
		}
			
		if(!pagoAiutanti(gameState)){
			gameState.notifyObserver(new ErrorNotify("Errore: gli aiutanti non sono sufficienti!",
					Arrays.asList(gameState.getGiocatoreCorrente())));
			return;
		}
			
		else{
			gameState.getPedinaRe().setCittà(cittàCostruzione);
			pagaAiutanti(gameState);
			costruisci(gameState);
			prendiBonus(gameState);
			if (cittàCostruzione instanceof CittàBonus)
				controllaCittàColore(((ColoreCittà) cittàCostruzione.getColoreCittà()),
						gameState.getGiocatoreCorrente());
			controllaCittàRegione(cittàCostruzione.getRegione(), gameState.getGiocatoreCorrente());
		}
		
		gameState.notifyObserver(new GameStateNotify(gameState, gameState.getGiocatori()));
		gameState.notifyObserver(new GiocatoreNotify(gameState.getGiocatoreCorrente(), 
				Arrays.asList(gameState.getGiocatoreCorrente())));
		setStatoTransizionePrincipale(gameState); 
	}
	
	private void controllaCittàRegione(Regione regione, Giocatore giocatore) {
		for(Città c: regione.getCittàRegione()){
			if(!c.emporioColore(giocatore.getColoreGiocatore()))
				return;
		}
		giocatore.getTessereBonus().add(regione.getBonusRegione());
		regione.setBonusAssegnato(true);
		
	}
	private void controllaCittàColore(ColoreCittà coloreCittà, Giocatore giocatore) {
		if(coloreCittà.isAssegnatoBonus())
			return;
		for(Città c: coloreCittà.getCittà()){
			if(!c.emporioColore(giocatore.getColoreGiocatore()))
				return;
		}
		giocatore.getTessereBonus().add(coloreCittà.getBonusColore());
		coloreCittà.setAssegnatoBonus(true);
	}
	/**
	 * pay the Aiutanti in case there is/are Emporium in the city where the player wants to build
	 */
	private void pagaAiutanti(GameState gameState){
	    int aiutantiDaPagare = cittàCostruzione.getEmpori().size();
	    gameState.getGiocatoreCorrente().getAiutanti().togliAiutanti(aiutantiDaPagare);
	    
	}
	
	/**
	 * add an emporium to the city where the player wants to build
	 */
	private void costruisci(GameState gameState){
	    Emporio emporio = gameState.getGiocatoreCorrente().getEmpori().remove(0);
	    this.cittàCostruzione.aggiungiEmporio(emporio);
	  }
	  /**
	   * give to the player the bonus of the city connected to the city where he has built an
	   * emporium.
	   */
	  private void prendiBonus(GameState gameState){
	    Colore coloreEmporio = gameState.getGiocatoreCorrente().getColoreGiocatore();
	    HashSet<CittàBonus> cittàCollegate = gameState.getMappa().trovaCittà(cittàCostruzione, coloreEmporio);
	    for ( CittàBonus c: cittàCollegate){
	        for(Bonus b: c.getBonus()){
	          b.usaBonus(gameState);
	        }
	    }
	  }
	    
	  /**
	   * check if the player has enough money to do the action, then it subtract the money due
	   * from the player 
	   * @param moneteDovute
	   * @return true if the player has enough money, false in the other case
	   */
	private boolean paga(int moneteDovute, GameState gameState) {
		if(!gameState.getGiocatoreCorrente().diminuisciRicchezza(moneteDovute))
			return false;
		return true;
	}
	/**
	 * check if the player has enough aiutanti to do the action, then it substract the aiutanti due
	 * from the player
	 * @return true if the player has enough aiutanti, false in the other case
	 */
	private boolean pagoAiutanti(GameState gameState){
	    int numeroEmpori = cittàCostruzione.getEmpori().size(); 
	  
	    if(!cittàCostruzione.getEmpori().isEmpty()) { 
	      if(gameState.getGiocatoreCorrente().getAiutanti().togliAiutanti(numeroEmpori)){
	        return true;
	      }
	    }
	    return false;
	  }
	/**
	 * calculate the amount of money due by the player
	 * @return the value of money that the player has to pay
	 */
	private int calcolaMonete() {
		
		int monete=0;
		int carte=carteGiocatore.size();
		for (CartaPolitica carta: carteGiocatore ){
			if (carta.equals(new CartaPolitica(new Colore("Multicolore")))){
				monete++;
			}
		}
		
		switch (carte){
		case 1: if(carte==1){
		 monete=monete+10;
		}
		break;
		
		case 2: if(carte==2){
		 monete=monete+7;
		}
		break;
		case 3: if(carte==3){
		 monete=monete+4;
		}
		break;
		default: 
		break;
		}
		return monete;
		
		
	}
	/**
	 * check if the cards of the player match the counselors of the balcony
	 * @return true if the cards match, false in the other case
	 */
	private boolean controllaColori() {
		for (CartaPolitica carta: carteGiocatore ){
			if (carta.equals(new CartaPolitica(new Colore("Multicolore")))){
				continue;
			}

			for (Consigliere consigliere: balcone.getConsigliere()){
					if (consigliere.getColore().equals(carta.getColore())){
						balcone.getConsigliere().remove(consigliere);
						break;
					}
					
					else
						return false; //se la carta non matcha
				}
			}
		return true;
		
	}
	/**
	 * @param cittàCostruzione the cittàCostruzione to set
	 */
	public void setCittàCostruzione(Città cittàCostruzione) {
		this.cittàCostruzione = cittàCostruzione;
	}
	/**
	 * @param carteGiocatore the carteGiocatore to set
	 */
	public void setCarteGiocatore(ArrayList<CartaPolitica> carteGiocatore) {
		this.carteGiocatore = carteGiocatore;
	}
	
	
	
	
}

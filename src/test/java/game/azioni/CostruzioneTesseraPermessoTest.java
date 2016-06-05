package game.azioni;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import bonus.Bonus;
import bonus.BonusAiutanti;
import bonus.BonusMoneta;
import bonus.BonusPuntiVittoria;
import game.Città;
import game.CittàBonus;
import game.Colore;
import game.Emporio;
import game.GameState;
import game.Giocatore;
import game.Regione;
import game.TesseraPermesso;


public class CostruzioneTesseraPermessoTest {
	
	static GameState gameState;
	static Regione regione;
	
	@BeforeClass
	public static void init() throws IOException{
		gameState=new GameState();
		ArrayList<Giocatore> giocatori=new ArrayList<>();
		Giocatore giocatore=new Giocatore("Giocatore");
		giocatori.add(giocatore);
		gameState.start(giocatori);
		regione=gameState.getRegioni().get(0);
	}
	
	@Test
	public void testEseguiAzione(){
		//perndo le città per il test
		CittàBonus cittàCostruzione=null;
		CittàBonus cittàVicina=null;
		for (Città c: regione.getCittàRegione()){
			if(c.getNome().equals("Arkon")){
				cittàCostruzione=(CittàBonus) c;
				continue;
			}
			else if(c.getNome().equals("Burgen")){
				cittàVicina=(CittàBonus) c;
				continue;
			}			
		}
		
		cittàVicina.aggiungiEmporio(gameState.getGiocatoreCorrente().getEmpori().get(0));
		
		//metto l'emporio sulla città su cui costruisco
		cittàCostruzione.aggiungiEmporio(new Emporio(new Colore("Presente")));
		
		//creo la tessera permesso
		ArrayList<Città> città=new ArrayList<>();
		città.add(cittàCostruzione);
		TesseraPermesso tesseraPermesso=new TesseraPermesso(città, null, regione);
		
		//creo e setto i parametri per l'azione e la eseguo
		CostruzioneTesseraPermesso azione=new CostruzioneTesseraPermesso();
		azione.setCittàCostruzione(cittàCostruzione);
		azione.setTesseraPermessoScoperta(tesseraPermesso);
		
		int monete=0;
		int aiutanti=0;
		int vittoria=0;
		//tengo conto dei bonus della città su cui costruisco
		for(Bonus b: cittàCostruzione.getBonus()){
			if(b instanceof BonusAiutanti){
				aiutanti+=((BonusAiutanti) b).getAiutanti();
				continue;
			}
			if(b instanceof BonusMoneta){
				monete+=((BonusMoneta) b).getMonete();
				continue;
			}	
			if(b instanceof BonusPuntiVittoria){
				vittoria+=((BonusPuntiVittoria) b).getPuntiVittoria();
				continue;
			}	
		}
		
		//tengo conto dei bonus della città vicina
		for(Bonus b: cittàVicina.getBonus()){
			if(b instanceof BonusAiutanti){
				aiutanti+=((BonusAiutanti) b).getAiutanti();
				continue;
			}
			if(b instanceof BonusMoneta){
				monete+=((BonusMoneta) b).getMonete();
				continue;
			}	
			if(b instanceof BonusPuntiVittoria){
				vittoria+=((BonusPuntiVittoria) b).getPuntiVittoria();
				continue;
			}	
		}
			
		azione.eseguiAzione(gameState);
		
		if(gameState.getGiocatoreCorrente().getEmpori().isEmpty())
			vittoria+=3;

		assertTrue(cittàCostruzione.getEmpori().size()==2);
		assertEquals(aiutanti, gameState.getGiocatoreCorrente().getAiutanti().getAiutante());
		assertEquals(10+monete, gameState.getGiocatoreCorrente().getPunteggioRicchezza());
		assertEquals(vittoria, gameState.getGiocatoreCorrente().getPunteggioVittoria());
		assertTrue(gameState.getGiocatoreCorrente().getTesserePermessoUsate().contains(tesseraPermesso));
		assertTrue(gameState.getGiocatoreCorrente().getTesserePermesso().size()==0);
	}
	
	@Test
	public void testSetandGetTesseraPermesso(){
		TesseraPermesso tesseraPermessoScoperta=gameState.getRegioni().get(0).getMazzoTesserePermesso().getCarte().get(0);
		CostruzioneTesseraPermesso costruzioneTesseraPermesso=new CostruzioneTesseraPermesso();
		costruzioneTesseraPermesso.setTesseraPermessoScoperta(tesseraPermessoScoperta);
		assertTrue(tesseraPermessoScoperta==costruzioneTesseraPermesso.getTesseraPermessoScoperta());
		assertTrue(costruzioneTesseraPermesso.getTesseraPermessoScoperta() instanceof TesseraPermesso);
	}
	
	@Test
	public void testSetandGetCittàCostruzione(){
		Città cittàCostruzione=gameState.getRegioni().get(0).getCittàRegione().get(0);
		CostruzioneTesseraPermesso costruzioneTesseraPermesso=new CostruzioneTesseraPermesso();
		costruzioneTesseraPermesso.setCittàCostruzione(cittàCostruzione);
		assertTrue(cittàCostruzione==costruzioneTesseraPermesso.getCittàCostruzione());
		assertTrue(costruzioneTesseraPermesso.getCittàCostruzione() instanceof Città);
	}
}

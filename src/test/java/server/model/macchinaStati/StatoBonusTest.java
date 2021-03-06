package server.model.macchinaStati;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import server.model.azioni.Azione;
import server.model.azioni.azioniBonus.BonusGettoneN;
import server.model.game.GameState;
import server.model.game.Giocatore;
import server.model.notify.AzioniNotify;
import server.model.notify.MessageNotify;
import server.model.notify.Notify;
import utility.Observer;

public class StatoBonusTest {

	static GameState gameState;
	static List<Notify> notify;

	@BeforeClass
	public static void init() throws IOException{
		notify = new ArrayList<>();
		ArrayList<Giocatore> giocatori=new ArrayList<>();
		Giocatore giocatore=new Giocatore("Giocatore");
		giocatori.add(giocatore);
		gameState=new GameState();
		Observer<Notify> observer = new Observer<Notify>() {

			@Override
			public void update(Notify c) {
				notify.add(c);
			}
		};
		gameState.registerObserver(observer);
		gameState.start(giocatori, "mappa1");
	}

	@Test
	public void testStatoBonus() {
		gameState.getGiocatoreCorrente().getBonusNobiltà().add(new BonusGettoneN());
		Stato11 stato11=new Stato11(gameState);
		notify.clear();
		StatoBonus statoBonus=new StatoBonus(gameState, stato11);
		gameState.setStato(statoBonus);
		assertTrue(gameState.getStato() instanceof StatoBonus);
		assertTrue(notify.get(0) instanceof AzioniNotify);
	}
	
	@Test(expected=NullPointerException.class)
	public void testStatoBonusStatoNull() {
		new StatoBonus(gameState, null);
	}
	
	@Test
	public void testTransizioneBonusNobiltàSenzaBonus() {
		gameState.getGiocatoreCorrente().getBonusNobiltà().add(new BonusGettoneN());
		
		Stato11 stato11=new Stato11(gameState);
		StatoBonus statoBonus=new StatoBonus(gameState, stato11);
		gameState.getGiocatoreCorrente().getBonusNobiltà().clear();
		gameState.setStato(statoBonus);
		statoBonus.transizioneBonus(gameState);

		assertTrue(gameState.getStato() instanceof Stato01);
	}
	
	@Test
	public void testTransizioneBonusNobiltàConBonus() {
		gameState.getGiocatoreCorrente().getBonusNobiltà().add(new BonusGettoneN());

		Stato11 stato11=new Stato11(gameState);
		StatoBonus statoBonus=new StatoBonus(gameState, stato11);
		List<Azione> bonusNobiltà=new ArrayList<>();
		bonusNobiltà.add(new BonusGettoneN());
		gameState.getGiocatoreCorrente().getBonusNobiltà().addAll(bonusNobiltà);
		gameState.setStato(statoBonus);

		notify.clear();
		statoBonus.transizioneBonus(gameState);
		assertTrue(gameState.getStato() instanceof StatoBonus);
		assertTrue(notify.get(0) instanceof AzioniNotify);
	}

	@Test
	public void testGetAzioni() {
		gameState.getGiocatoreCorrente().getBonusNobiltà().add(new BonusGettoneN());
		Stato11 stato11=new Stato11(gameState);
		StatoBonus statoBonus=new StatoBonus(gameState, stato11);
		gameState.setStato(statoBonus);
		assertTrue(statoBonus.getAzioni()==gameState.getGiocatoreCorrente().getBonusNobiltà());
	}
	
	@Test
	public void testTransizionePrincipale() {
		gameState.getGiocatoreCorrente().getBonusNobiltà().add(new BonusGettoneN());
		Stato11 stato11=new Stato11(gameState);
		notify.clear();
		StatoBonus statoBonus=new StatoBonus(gameState, stato11);
		statoBonus.transizionePrincipale(gameState);
 
		assertTrue(notify.get(1) instanceof MessageNotify);
	}

	@Test
	public void testTransizionePescaCarta() {
		gameState.getGiocatoreCorrente().getBonusNobiltà().add(new BonusGettoneN());
		Stato11 stato11=new Stato11(gameState);
		notify.clear();
		StatoBonus statoBonus=new StatoBonus(gameState, stato11);
		statoBonus.transizionePescaCarta(gameState);
 
		assertTrue(notify.get(1) instanceof MessageNotify);
	}
	
	@Test
	public void testTransizioneOfferta() {
		Stato11 stato11=new Stato11(gameState);
		notify.clear();
		StatoBonus statoBonus=new StatoBonus(gameState, stato11);
		statoBonus.transizioneOfferta(gameState);
 
		assertTrue(notify.get(1) instanceof MessageNotify);
	}
	
	@Test
	public void testTransizioneSecondaPrincipale() {
		Stato11 stato11=new Stato11(gameState);
		notify.clear();
		StatoBonus statoBonus=new StatoBonus(gameState, stato11);
		statoBonus.transizioneSecondaPrincipale(gameState);
 
		assertTrue(notify.get(1) instanceof MessageNotify);
	}
	
	@Test
	public void testTransizioneVeloce() {
		Stato11 stato11=new Stato11(gameState);
		notify.clear();
		StatoBonus statoBonus=new StatoBonus(gameState, stato11);
		statoBonus.transizioneVeloce(gameState);
 
		assertTrue(notify.get(1) instanceof MessageNotify);
	}
	
	@Test
	public void testTransizionePassa() {
		Stato11 stato11=new Stato11(gameState);
		notify.clear();
		StatoBonus statoBonus=new StatoBonus(gameState, stato11);
		statoBonus.transizionePassa(gameState);

		assertTrue(gameState.getStato() instanceof StatoBonus);
	}
}

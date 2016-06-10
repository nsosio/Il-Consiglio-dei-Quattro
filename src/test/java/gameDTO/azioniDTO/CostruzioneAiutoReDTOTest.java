package gameDTO.azioniDTO;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import common.azioniDTO.CostruzioneAiutoReDTO;
import common.azioniDTO.azioneVisitor.AzioneVisitor;
import common.azioniDTO.azioneVisitor.AzioneVisitorImpl;
import common.gameDTO.CartaPoliticaDTO;
import common.gameDTO.CittàDTO;
import common.gameDTO.RegioneDTO;
import common.gameDTO.TesseraPermessoDTO;
import server.model.azioni.azioniBonus.BonusTesseraPermessoN;
import server.model.azioni.azioniPrincipali.CostruzioneAiutoRe;
import server.model.game.CartaPolitica;
import server.model.game.Città;
import server.model.game.Colore;
import server.model.game.GameState;
import server.model.game.Giocatore;

public class CostruzioneAiutoReDTOTest {

	static GameState gameState;
	static AzioneVisitor visitor;
	static CittàDTO cittàDTO;
	static Città città;
	static List<CartaPolitica> carte;
	static List<CartaPoliticaDTO> carteGiocatore;

	@BeforeClass
	public static void setUpBeforeClass() throws IOException{
		ArrayList<Giocatore> giocatori=new ArrayList<>();
		Giocatore giocatore=new Giocatore("Giocatore");
		giocatori.add(giocatore);
		gameState=new GameState();
		gameState.start(giocatori);
		gameState.getGiocatoreCorrente().aggiungiCartaPolitica(new CartaPolitica(new Colore("Multicolore")));
		
		città=gameState.getRegioni().get(0).getCittàRegione().get(0);
		cittàDTO=new CittàDTO();
		cittàDTO.inizializza(città);
		carte=new ArrayList<>();
		carteGiocatore=new ArrayList<>();
		CartaPoliticaDTO c=new CartaPoliticaDTO();
		CartaPolitica carta=new CartaPolitica(new Colore("Multicolore"));
		carte.add(carta);
		c.inizializza(carta);
		carteGiocatore.add(c);
		
		visitor = new AzioneVisitorImpl(gameState, gameState.getGiocatoreCorrente());
	}

	@Test
	public void testGetCarteGiocatore() {
		CostruzioneAiutoReDTO costruzione=new CostruzioneAiutoReDTO();
		costruzione.setCarteGiocatore(carteGiocatore);
		
		assertTrue(carteGiocatore==costruzione.getCarteGiocatore());
	}

	@Test
	public void testSetCarteGiocatore() {
		CostruzioneAiutoReDTO costruzione=new CostruzioneAiutoReDTO();
		costruzione.setCarteGiocatore(carteGiocatore);
		
		assertTrue(carteGiocatore==costruzione.getCarteGiocatore());
	}

	@Test
	public void testGetCittà() {
		CostruzioneAiutoReDTO costruzione=new CostruzioneAiutoReDTO();
		costruzione.setCittà(cittàDTO);
		
		assertTrue(cittàDTO==costruzione.getCittà());
	}

	@Test
	public void testSetCittà() {
		CostruzioneAiutoReDTO costruzione=new CostruzioneAiutoReDTO();
		costruzione.setCittà(cittàDTO);
		
		assertTrue(cittàDTO==costruzione.getCittà());
	}

	@Test
	public void testAccept() {
		CostruzioneAiutoReDTO costruzione=new CostruzioneAiutoReDTO();

		costruzione.setCarteGiocatore(carteGiocatore);
		costruzione.setCittà(cittàDTO);
		
		CostruzioneAiutoRe azioneParser=(CostruzioneAiutoRe) costruzione.accept(visitor);
		
		assertTrue(azioneParser.getClass()==CostruzioneAiutoRe.class);
	}

}

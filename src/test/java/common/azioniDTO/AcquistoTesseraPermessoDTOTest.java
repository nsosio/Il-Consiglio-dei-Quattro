package common.azioniDTO;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import common.azioniDTO.AcquistoTesseraPermessoDTO;
import common.azioniDTO.azioneVisitor.AzioneVisitor;
import common.azioniDTO.azioneVisitor.AzioneVisitorImpl;
import common.gameDTO.CartaPoliticaDTO;
import common.gameDTO.RegioneDTO;
import common.gameDTO.TesseraPermessoDTO;
import server.model.azioni.azioniPrincipali.AcquistoTesseraPermesso;
import server.model.game.CartaPolitica;
import server.model.game.Colore;
import server.model.game.GameState;
import server.model.game.Giocatore;
import utility.ParameterException;

public class AcquistoTesseraPermessoDTOTest {

	static GameState gameState;
	static AcquistoTesseraPermessoDTO azione;
	static RegioneDTO regione;
	static List<CartaPoliticaDTO> carte;
	static TesseraPermessoDTO tesseraPermesso;
	static AzioneVisitor visitor;
	
	@BeforeClass
	public static void setUp() throws IOException{
		ArrayList<Giocatore> giocatori=new ArrayList<>();
		Giocatore giocatore=new Giocatore("Giocatore");
		giocatori.add(giocatore);
		gameState=new GameState();
		gameState.start(giocatori, "mappa1");
		
		azione=new AcquistoTesseraPermessoDTO();
		regione=new RegioneDTO();
		regione.inizializza(gameState.getRegioni().get(0));
		tesseraPermesso=new TesseraPermessoDTO();
		tesseraPermesso.inizializza(gameState.getRegioni().get(0).getTesserePermessoScoperte().get(0));
		CartaPoliticaDTO d=new CartaPoliticaDTO();
		d.inizializza(new CartaPolitica(new Colore("Multicolore")));
		carte=new ArrayList<>();
		carte.add(d);
		visitor = new AzioneVisitorImpl(gameState, gameState.getGiocatoreCorrente());
	}

	@Test
	public void testGetRegione() {
		azione.setRegione(regione);
		assertTrue(azione.getRegione()==regione);
	}

	@Test
	public void testSetRegione() {
		azione.setRegione(regione);
		assertTrue(azione.getRegione()==regione);
	}

	@Test
	public void testGetCarte() {
		azione.setCarte(carte);
		assertTrue(carte==azione.getCarte());
	}

	@Test
	public void testSetCarte() {
		azione.setCarte(carte);
		assertTrue(carte==azione.getCarte());
	}

	@Test
	public void testGetTesseraPermesso() {
		azione.setTesseraPermesso(tesseraPermesso);
		assertTrue(tesseraPermesso==azione.getTesseraPermesso());
	}

	@Test
	public void testSetTesseraPermesso() {
		azione.setTesseraPermesso(tesseraPermesso);
		assertTrue(tesseraPermesso==azione.getTesseraPermesso());		
	}

	@Test
	public void testAccept() throws ParameterException {
		ArrayList<CartaPolitica> c=new ArrayList<>();
		CartaPolitica carta=new CartaPolitica(new Colore("Multicolore"));
		gameState.getGiocatoreCorrente().aggiungiCartaPolitica(carta);
		c.add(new CartaPolitica(new Colore("Multicolore")));

		
		AcquistoTesseraPermesso azioneParser=(AcquistoTesseraPermesso) azione.accept(visitor);
		
		assertEquals(c, azioneParser.getCarteGiocatore());
		assertEquals(gameState.getRegioni().get(0).getTesserePermessoScoperte().get(0), azioneParser.getTesseraScoperta());
		assertEquals(gameState.getRegioni().get(0), azioneParser.getRegione());
	}
	
	@Test
	public void testToString(){
		assertEquals("Acquistare una tessera permesso [P2]", azione.toString());
	}

}

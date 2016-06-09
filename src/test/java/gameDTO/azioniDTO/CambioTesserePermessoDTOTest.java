package gameDTO.azioniDTO;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import game.GameState;
import game.Giocatore;
import game.Regione;
import game.azioni.CambioTesseraPermesso;
import gameDTO.azioniDTO.azioneVisitor.AzioneVisitor;
import gameDTO.azioniDTO.azioneVisitor.AzioneVisitorImpl;
import gameDTO.gameDTO.RegioneDTO;

public class CambioTesserePermessoDTOTest {

	static GameState gameState;
	static Regione regione;
	static RegioneDTO regioneDTO;
	static AzioneVisitor visitor;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ArrayList<Giocatore> giocatori=new ArrayList<>();
		Giocatore giocatore=new Giocatore("Giocatore");
		giocatori.add(giocatore);
		gameState=new GameState();
		gameState.start(giocatori);
		
		regione=gameState.getRegioni().get(0);
		regioneDTO=new RegioneDTO();
		regioneDTO.inizializza(regione);
		
		visitor = new AzioneVisitorImpl(gameState, gameState.getGiocatoreCorrente());
	}

	@Test
	public void testGetRegione() {
		CambioTesserePermessoDTO cambio=new CambioTesserePermessoDTO();
		cambio.setRegione(regioneDTO);
		assertTrue(regioneDTO==cambio.getRegione());
	}

	@Test
	public void testSetRegione() {
		CambioTesserePermessoDTO cambio=new CambioTesserePermessoDTO();
		cambio.setRegione(regioneDTO);
		assertTrue(regioneDTO==cambio.getRegione());
	}

	@Test
	public void testAccept() {
		CambioTesserePermessoDTO cambio=new CambioTesserePermessoDTO();
		cambio.setRegione(regioneDTO);
		
		CambioTesseraPermesso azioneVisitor=(CambioTesseraPermesso)cambio.accept(visitor);
		
		assertTrue(regione==azioneVisitor.getRegione());
	}

}

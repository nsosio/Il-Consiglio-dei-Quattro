package gameDTO.gameDTO;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import bonus.BonusPuntiVittoria;
import game.Colore;
import game.GameState;
import game.Giocatore;

public class ColoreDTOTest {

	static Colore colore;
	static GameState gameState;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ArrayList<Giocatore> giocatori=new ArrayList<>();
		Giocatore giocatore=new Giocatore("Giocatore");
		giocatori.add(giocatore);
		gameState=new GameState();
		gameState.start(giocatori);

		colore=new Colore("A");
	}

	@Test
	public void testGetColore() {
		ColoreDTO coloreDTO=new ColoreDTO();
		coloreDTO.setColore("A");
		
		assertEquals("A", coloreDTO.getColore());
	}

	@Test
	public void testSetColore() {
		ColoreDTO coloreDTO=new ColoreDTO();
		coloreDTO.setColore("A");
		
		assertEquals("A", coloreDTO.getColore());
	}

	@Test
	public void testInizializza() {
		ColoreDTO coloreDTO=new ColoreDTO();
		coloreDTO.inizializza(colore);
		
		assertEquals(colore.getColore(), coloreDTO.getColore());
	}

}

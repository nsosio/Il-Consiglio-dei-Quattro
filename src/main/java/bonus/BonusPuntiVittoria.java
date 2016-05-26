package bonus;

import game.GameState;

public class BonusPuntiVittoria extends Bonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5468530680642958233L;
	private int puntiVittoria;

	/**
	 * @param gameState
	 */
	
	public BonusPuntiVittoria(int puntiVittoria) {
		super();
		if(puntiVittoria<=0)
			throw new IllegalArgumentException("Bonus punti vittoria deve ricevere un valore positivo non nullo");
		this.puntiVittoria=puntiVittoria;	
	}
	
	/**
	 * add puntiVittoria to variable punteggioVittoria of Giocatore
	 */
	
	@Override
	public void usaBonus(GameState gameState) {
		gameState.getGiocatoreCorrente().aumentaPuntiVittoria(puntiVittoria);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	@Override
	public String toString() {
		return "BonusPuntiVittoria [puntiVittoria=" + puntiVittoria + "]";
	}
	
	
}

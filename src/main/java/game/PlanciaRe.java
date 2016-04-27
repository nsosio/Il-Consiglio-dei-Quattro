package game;

import java.util.ArrayList;

import bonus.*;

public class PlanciaRe {

	private final Balcone balconeRe;
	private final ArrayList<Bonus> bonusPremioRe;
	private final ArrayList<Bonus> bonusColore;
	private final ArrayList<PunteggioNobiltà> percorsoNobiltà;
	/**
	 * @param balconeRe
	 * @param bonusPremioRe
	 * @param bonusColore
	 * @param percorsoNobiltà
	 */
	public PlanciaRe(Balcone balconeRe, ArrayList<Bonus> bonusPremioRe, ArrayList<Bonus> bonusColore,
			ArrayList<PunteggioNobiltà> percorsoNobiltà) {
		this.balconeRe = balconeRe;
		this.bonusPremioRe = bonusPremioRe;
		this.bonusColore = bonusColore;
		this.percorsoNobiltà = percorsoNobiltà;
	}
	/**
	 * @return the balconeRe
	 */
	public Balcone getBalconeRe() {
		return balconeRe;
	}
	/**
	 * @return the bonusPremioRe
	 */
	public ArrayList<Bonus> getBonusPremioRe() {
		return bonusPremioRe;
	}
	/**
	 * @return the bonusColore
	 */
	public ArrayList<Bonus> getBonusColore() {
		return bonusColore;
	}
	/**
	 * @return the percorsoNobiltà
	 */
	public ArrayList<PunteggioNobiltà> getPercorsoNobiltà() {
		return percorsoNobiltà;
	}
	
	

	
}

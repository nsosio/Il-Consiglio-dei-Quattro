package game.azioni;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bonus.Bonus;
import game.CittàBonus;
import game.Emporio;
import game.GameState;
import game.notify.MessageNotify;
import gameDTO.azioniDTO.AzioneDTO;
import gameDTO.azioniDTO.BonusGettoneNDTO;
import utility.exception.AzioneNonEseguibile;

public class BonusGettoneN extends Azione {

	/*ottieni il bonus di un gettone ricompensa in cui hai un emporio.
	 * non puoi scegliere uno dei gettoni avanzamento ul percorso nobiltà
	 */
	private final int ID=13;
	private List<CittàBonus> città;
	private int numeroGettoni;
	
	/**
	 * current player had to choose a city in which he has an emporio
	 * then the player took bonus that are on GettoneBonus in that city that he choose.
	 * 
	 * @param gameState
	 */
	@Override
	public void eseguiAzione(GameState gameState){
		for(CittàBonus c: città){
			if(!c.getEmpori().contains(new Emporio(gameState.getGiocatoreCorrente().getColoreGiocatore()))){
				gameState.notifyObserver(new MessageNotify("Errore:" + gameState.getGiocatoreCorrente().getNome() 
						+ "non hai un emporio in "+c.getNome(), Arrays.asList(gameState.getGiocatoreCorrente())));
				return;
				}
		}
		if(numeroGettoni>1){
			if(!città.get(0).getBonus().equals(città.get(1).getBonus())){
				gameState.notifyObserver(new MessageNotify("Errore:" + gameState.getGiocatoreCorrente().getNome() 
						+ "devi scegliere due gettoni ricompensa diversi tra loro" , Arrays.asList(gameState.getGiocatoreCorrente())));
				return;
			}
		}
		ArrayList<Bonus> bonus= new ArrayList<>();
		
		for(CittàBonus city:città){
			bonus.addAll(city.getBonus());
		}
		for (Bonus b: bonus){
				b.usaBonus(gameState);
		}
		gameState.getGiocatoreCorrente().getBonusNobiltà().remove(this);
		try {
			gameState.getStato().transizioneBonus(gameState);
		} catch (AzioneNonEseguibile e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the città
	 */
	public List<CittàBonus> getCittà() {
		return città;
	}

	/**
	 * @param città the città to set
	 */
	public void setCittà(List<CittàBonus> città) {
		this.città = città;
	}

	@Override
	public AzioneDTO getAzioneDTO() {
	return new BonusGettoneNDTO();
	}

	/**
	 * @return the numeroGettoni
	 */
	public int getNumeroGettoni() {
		return numeroGettoni;
	}

	/**
	 * @param numeroGettoni the numeroGettoni to set
	 */
	public void setNumeroGettoni(int numeroGettoni) {
		this.numeroGettoni = numeroGettoni;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BonusGettoneN other = (BonusGettoneN) obj;
		if (ID != other.ID)
			return false;
		return true;
	}
	
}

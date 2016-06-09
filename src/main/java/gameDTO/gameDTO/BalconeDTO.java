package gameDTO.gameDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import game.Balcone;
import game.Consigliere;

public class BalconeDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6523476700234911129L;
	private List<ConsigliereDTO> consiglieri;

	/**
	 * @return the consiglieri
	 */
	public List<ConsigliereDTO> getConsiglieri() {
		return consiglieri;
	}

	/**
	 * @param consiglieri the consiglieri to set
	 */
	public void setConsiglieri(List<ConsigliereDTO> consiglieri) {
		this.consiglieri = consiglieri;
	}
	
	public void inizializza(Balcone balcone){
		this.consiglieri=new ArrayList<>();
		for(Consigliere c: balcone.getConsigliere()){
			ConsigliereDTO consigliereDTO = new ConsigliereDTO();
			consigliereDTO.inizializza(c);
			this.getConsiglieri().add(consigliereDTO);
		}
	}
}

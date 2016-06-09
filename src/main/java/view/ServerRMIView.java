package view;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import client.ConnessioneRMIRemota;
import game.GameState;
import game.Giocatore;
import game.azioni.Azione;
import game.notify.Notify;
import gameDTO.azioniDTO.AzioneDTO;
import gameDTO.azioniDTO.azioneVisitor.AzioneVisitor;
import gameDTO.azioniDTO.azioneVisitor.AzioneVisitorImpl;
import gameDTO.gameDTO.GiocatoreDTO;
import server.Server;

public class ServerRMIView extends View implements ServerRMIViewRemote{

	private GameState gameState;
	private Map<ConnessioneRMIRemota, Giocatore> giocatori;

	/**
	 * @param giocatore
	 * @param server
	 */
	public ServerRMIView() {
		this.giocatori=new HashMap<>();
	}

	@Override
	public void update(Notify o) {
		for(ConnessioneRMIRemota c: giocatori.keySet()){
			if(o.daInviare(this.giocatori.get(c))){
				System.out.println("[SERVER] Inviata notifica "+o+" al giocatore "+this.giocatori.get(c).getNome());
				try {
					c.aggiorna(o.notifyToClientNotify());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
			}
		}
	}

	@Override
	public void eseguiAzione(AzioneDTO azioneDTO, ConnessioneRMIRemota connessioneRMIRemota) throws RemoteException {
		AzioneVisitor azioneVisitor = new AzioneVisitorImpl(gameState, this.giocatori.get(connessioneRMIRemota));
		Azione azione = azioneDTO.accept(azioneVisitor);
		System.out.println("[SERVER] Ricevuta l'azione " + azione+
				" dal giocatore "+this.giocatori.get(connessioneRMIRemota).getNome());
		try{
		if (azione.isTurno(this.giocatori.get(connessioneRMIRemota), gameState)){
				//&& gameState.getStato().getAzioni().contains(azione)) {
			System.out.println("[SERVER] Inviata l'azione "+azione);
			this.notifyObserver(azione);
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

	@Override
	public ServerRMIViewRemote register(ConnessioneRMIRemota connessioneRMIRemota, GiocatoreDTO giocatoreDTO)
			throws RemoteException {
		Giocatore giocatore = new Giocatore(giocatoreDTO.getNome());
		this.giocatori.put(connessioneRMIRemota, giocatore);
		Server.getInstance().aggiungiGiocatoreRMI(giocatore, this);
		return this;
	}
	@Override
	public void unregister(ConnessioneRMIRemota connessioneRMIRemota) throws RemoteException{
		Giocatore giocatore=this.giocatori.get(connessioneRMIRemota);
		this.giocatori.remove(connessioneRMIRemota);
		//this.notifyObserver(new Exit(giocatore));
		System.out.println("[SERVER] Il giocatore "+giocatore.getNome().toUpperCase()+ " è stato rimosso");
	}

	@Override
	public void setGameState(GameState gameState) {
		this.gameState=gameState;
	}

	@Override
	public void disconnetti() {
		for(ConnessioneRMIRemota c: this.giocatori.keySet()){
			try {
				c.disconnetti();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.giocatori.remove(c);
		}
	}

}

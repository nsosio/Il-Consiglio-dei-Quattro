package server.view;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import client.connessione.ConnessioneRMIRemota;
import common.azioniDTO.AzioneDTO;
import common.azioniDTO.AzioneMappaDTO;
import common.azioniDTO.ExitDTO;
import common.azioniDTO.azioneVisitor.AzioneVisitor;
import common.azioniDTO.azioneVisitor.AzioneVisitorImpl;
import common.gameDTO.GiocatoreDTO;
import server.Server;
import server.model.azioni.Azione;
import server.model.azioni.Chat;
import server.model.azioni.Exit;
import server.model.game.GameState;
import server.model.game.Giocatore;
import server.model.notify.MessageNotify;
import server.model.notify.Notify;
import server.view.clientNotify.MessageClientNotify;
import utility.ParameterException;
import utility.Utils;

public class ServerRMIView extends View implements ServerRMIViewRemote {

	private GameState gameState;
	private Map<ConnessioneRMIRemota, Giocatore> giocatori;
	private static final Logger log = Logger.getLogger(ServerRMIView.class.getName());

	/**
	 * create a ServerRMIView
	 * 
	 * @param giocatore
	 * @param server
	 */
	public ServerRMIView() {
		this.giocatori = new HashMap<>();
	}

	/**
	 * update the clients
	 * 
	 * @param notify
	 */
	@Override
	public void update(Notify o) {
		for (ConnessioneRMIRemota c : giocatori.keySet()) {
			if (o.daInviare(this.giocatori.get(c))) {
				Utils.print("[SERVER] Inviata notifica " + o + " al giocatore " + this.giocatori.get(c).getNome());
				try {
					c.aggiorna(o.notifyToClientNotify());
				} catch (RemoteException e) {
					try {
						this.unregister(c);
					} catch (RemoteException e1) {
						log.log(Level.SEVERE, "Errore nell'aggiornamento del client", e1);
					}
				}
			}
		}
	}

	/**
	 * parse the action received from a client and execute it
	 * 
	 * @param azioneDTO,
	 *            connessioneRMIRemota
	 */
	@Override
	public void eseguiAzione(AzioneDTO azioneDTO, ConnessioneRMIRemota connessioneRMIRemota) throws RemoteException {
		AzioneVisitor azioneVisitor = new AzioneVisitorImpl(gameState, this.giocatori.get(connessioneRMIRemota));
		Azione azione = null;

		if (azioneDTO instanceof ExitDTO) {
			Exit exit = new Exit();
			exit.setGiocatore(this.giocatori.get(connessioneRMIRemota));
			this.unregister(connessioneRMIRemota);
			this.notifyObserver(exit);
		} else if (azioneDTO instanceof AzioneMappaDTO) {
			Server.setMappa(((AzioneMappaDTO) azioneDTO).getMappa());
		} else {
			try {
				azione = azioneDTO.accept(azioneVisitor);

			} catch (ParameterException e1) {
				update(new MessageNotify(e1.getMessage(), Arrays.asList(gameState.getGiocatoreCorrente()), false));
				Utils.print("[SERVER] Ricevuta l'azione " + azione + " dal giocatore "
						+ this.giocatori.get(connessioneRMIRemota).getNome() + " con errore: " + e1.getMessage());
				return;
			}
			Utils.print("[SERVER] Ricevuta l'azione " + azione + " dal giocatore "
					+ this.giocatori.get(connessioneRMIRemota).getNome());

			if ((azione.isTurno(this.giocatori.get(connessioneRMIRemota), gameState)
					&& gameState.getStato().daEseguire(gameState.getStato().getAzioni(), azione))
					|| (azione instanceof Chat)) {
				Utils.print("[SERVER] Inviata l'azione " + azione);
				this.notifyObserver(azione);
			}

		}

	}

	/**
	 * register the player on the server
	 * 
	 * @param connessioneRMIRemota,
	 *            giocatoreDTO
	 */
	@Override
	public ServerRMIViewRemote register(ConnessioneRMIRemota connessioneRMIRemota, GiocatoreDTO giocatoreDTO)
			throws RemoteException {
		Giocatore giocatore = new Giocatore(giocatoreDTO.getNome());
		this.giocatori.put(connessioneRMIRemota, giocatore);
		Server.getInstance().aggiungiGiocatoreRMI(giocatore, this);
		return this;
	}

	/**
	 * unregister the player from the server
	 * 
	 * @param connessioneRMIRemota
	 */
	@Override
	public void unregister(ConnessioneRMIRemota connessioneRMIRemota) throws RemoteException {
		Utils.print(
				"[SERVER] Il giocatore " + this.giocatori.get(connessioneRMIRemota).getNome() + " è stato rimosso");
		this.giocatori.remove(connessioneRMIRemota);
	}

	/**
	 * set the gameState
	 * 
	 * @param gameState
	 */
	@Override
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	/**
	 * disconnect all the client
	 */
	@Override
	public void disconnetti() {
		this.gameState.unregisterObserver(this);
		for (ConnessioneRMIRemota c : this.giocatori.keySet()) {
			try {
				c.aggiorna(new MessageClientNotify("La partita è finita", false));
				c.disconnetti();
			} catch (RemoteException e) {
				log.log(Level.SEVERE, "Errore nella disconnessione del client", e);
			}
			this.giocatori.remove(c);
		}
	}

	/**
	 * 
	 * @param giocatore
	 * @return connessioneRMIRemota associated to the player
	 */
	public ConnessioneRMIRemota getConnessione(Giocatore giocatore) {
		for (ConnessioneRMIRemota c : giocatori.keySet()) {
			if (this.giocatori.get(c).equals(giocatore))
				return c;
		}
		return null;

	}

}

package client;

import java.rmi.RemoteException;

import gameDTO.azioniDTO.AzioneDTO;
import gameDTO.gameDTO.GameStateDTO;

public interface Connessione {

	public void start() throws RemoteException;

	public void inviaAzione(AzioneDTO action) throws RemoteException;

}
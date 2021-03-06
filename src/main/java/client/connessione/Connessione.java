package client.connessione;

import java.rmi.RemoteException;

import client.grafica.Grafica;
import common.azioniDTO.AzioneDTO;
import common.gameDTO.GameStateDTO;

public interface Connessione {
	/**
	 * this method start the connection
	 * @throws RemoteException
	 */
	public void start() throws RemoteException;

	/**
	 * this method send the action to the server
	 * @param action
	 * @throws RemoteException
	 */
	public void inviaAzione(AzioneDTO action) throws RemoteException;
	
	/**
	 * this method disconnect the client
	 * @throws RemoteException
	 */
	public void disconnetti() throws RemoteException;
	/**
	 * this method set the graphic to the connection
	 * @param grafica
	 * @throws RemoteException
	 */
	public void setGrafica(Grafica grafica) throws RemoteException;
	/**
	 * this method set the gameStateDTO to the connection
	 * @param gameStateDTO
	 * @throws RemoteException
	 */
	public void setGameStateDTO(GameStateDTO gameStateDTO) throws RemoteException;

}

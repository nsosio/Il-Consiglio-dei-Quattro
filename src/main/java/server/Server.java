package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.controller.Controller;
import server.model.game.GameState;
import server.model.game.Giocatore;
import server.view.ServerRMIView;
import server.view.ServerRMIViewRemote;
import server.view.ServerSocketView;
import server.view.View;

public class Server {

	private final static int CONNESSIONESOCKET = 29999;
	private final static int CONNESSIONERMI = 1099;
	private Controller controller;
	private GameState gameState;
	private static Map<GameState, Set<View>> partite;
	private List<Giocatore> giocatori;
	private boolean end = false;
	private final int TIMEOUT = 5000;
	private Timer timer;
	private Registry registry;
	private static Server instance = new Server();

	public Server() {
		Server.partite = new HashMap<>();
		this.gameState = new GameState();
		this.controller = new Controller(gameState);
		Server.partite.put(gameState, new HashSet<>());
		this.giocatori = new ArrayList<>();
	}

	public static Server getInstance() {
		return instance;
	}

	private void startSocket() throws IOException {

		ExecutorService executor = Executors.newCachedThreadPool();

		ServerSocket serverSocket = new ServerSocket(CONNESSIONESOCKET);

		System.out.println("[SERVER] Server pronto sulla porta : " + CONNESSIONESOCKET);

		while (!end) {
			Socket socket = serverSocket.accept();
			ServerSocketView view = new ServerSocketView(socket);
			executor.submit(view);
		}

		serverSocket.close();
	}

	private void startRMI() throws RemoteException, AlreadyBoundException {

		this.registry = LocateRegistry.createRegistry(CONNESSIONERMI);
		System.out.println("[SERVER] Server pronto sulla porta : " + CONNESSIONERMI);

		ServerRMIViewRemote game = new ServerRMIView();
		ServerRMIViewRemote gameRemote = (ServerRMIViewRemote) UnicastRemoteObject.exportObject(game, 0);

		String name = "GIOCO";
		registry.bind(name, gameRemote);
	}

	public synchronized void aggiungiGiocatore(Giocatore giocatore, View view) {
		this.giocatori.add(giocatore);
		System.out.println("[SERVER] Si è connesso il giocatore : " + giocatore.getNome());
		Server.partite.get(gameState).add(view);
		if (giocatori.size() == 2) {
			timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					creaGioco();

				}
			}, TIMEOUT);
		}
	}

	public void aggiungiGiocatoreRMI(Giocatore giocatore, ServerRMIView view) throws RemoteException {
		this.aggiungiGiocatore(giocatore, view);
		String name = "GIOCO";
		registry.rebind(name, view);

	}

	private void creaGioco() {
		try {
			for (View v : Server.partite.get(gameState)) {
				v.setGameState(gameState);
				this.gameState.registerObserver(v);
				v.registerObserver(this.controller);
			}
			this.gameState.start(giocatori);
			System.out.println("[SERVER] Iniziata una nuova partita");
			this.giocatori.clear();
			this.gameState = new GameState();
			this.controller = new Controller(gameState);
			Server.partite.put(this.gameState, new HashSet<>());
			
			ServerRMIViewRemote game = new ServerRMIView();
			ServerRMIViewRemote gameRemote = (ServerRMIViewRemote) UnicastRemoteObject.exportObject(game, 0);

			String name = "GIOCO";
			registry.rebind(name, gameRemote);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void disconnettiClient(GameState gameState) {
		for (View v : Server.partite.get(gameState)) {
			v.disconnetti();
		}
		Server.partite.remove(gameState);
	}

	public static void main(String[] args) throws IOException {
		try {
			Server.getInstance().startRMI();
		} catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Server.getInstance().startSocket();
	}

}
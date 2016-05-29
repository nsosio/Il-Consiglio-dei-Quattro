package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import gameDTO.azioniDTO.AcquistoTesseraPermessoDTO;
import gameDTO.azioniDTO.AzioneDTO;
import gameDTO.azioniDTO.CambioTesserePermessoDTO;
import gameDTO.azioniDTO.CostruzioneAiutoReDTO;
import gameDTO.azioniDTO.CostruzioneTesseraPermessoDTO;
import gameDTO.azioniDTO.ElezioneConsigliereDTO;
import gameDTO.azioniDTO.ElezioneConsigliereVeloceDTO;
import gameDTO.azioniDTO.IngaggioAiutanteDTO;
import gameDTO.azioniDTO.SecondaAzionePrincipaleDTO;
import gameDTO.gameDTO.CartaPoliticaDTO;
import gameDTO.gameDTO.CittàDTO;
import gameDTO.gameDTO.ConsigliereDTO;
import gameDTO.gameDTO.GameStateDTO;
import gameDTO.gameDTO.GiocatoreDTO;
import gameDTO.gameDTO.RegioneDTO;
import gameDTO.gameDTO.TesseraPermessoDTO;

public class ClientOutHandler implements Runnable {

	private ObjectOutputStream socketOut;
	private GameStateDTO gameStateDTO;
	private GiocatoreDTO giocatoreDTO;

	public ClientOutHandler(ObjectOutputStream socketOut, GameStateDTO gameStateDTO, GiocatoreDTO giocatoreDTO) {
		this.socketOut = socketOut;
		this.giocatoreDTO = giocatoreDTO;
		this.gameStateDTO=gameStateDTO;
	}

	@Override
	public void run() {

		System.out.println("RUNNING");
		Scanner stdIn = new Scanner(System.in);

		while (true) {
			AzioneDTO action = null;
			int indice;
			String inputLine = stdIn.nextLine();
			RegioneDTO regioneScelta;
			ArrayList<CartaPoliticaDTO> cartePolitica;
			TesseraPermessoDTO tesseraScelta;
			CittàDTO cittàScelta;
			ConsigliereDTO consigliereScelto;
			AzioniClient azioniClient = new AzioniClient();

			if (inputLine.equals("pesca")) {
				// action=new PescaCarta();

			}

			if (inputLine.equals("elezione consigliere")) {
				consigliereScelto = azioniClient.scegliConsigliere(gameStateDTO.getConsiglieri(), stdIn);
				regioneScelta = azioniClient.scegliRegione(gameStateDTO.getRegioni(), stdIn);

				action = new ElezioneConsigliereDTO(consigliereScelto, regioneScelta);
			}

			if (inputLine.equals("acquisto una tessera permesso")) {
				regioneScelta = azioniClient.scegliRegione(gameStateDTO.getRegioni(), stdIn);
				cartePolitica = azioniClient.scegliCarte(giocatoreDTO.getCartePolitica(), stdIn);
				indice = azioniClient.scegliTesseraRegione(regioneScelta.getTesserePermessoScoperte(), stdIn);

				action = new AcquistoTesseraPermessoDTO(regioneScelta, cartePolitica, indice);

			}

			if (inputLine.equals("Costruire un emporio")) {
				tesseraScelta = azioniClient.scegliTesseraGiocatore(giocatoreDTO.getTesserePermesso(), stdIn);
				cittàScelta = azioniClient.scegliCittà(tesseraScelta.getCittà(), giocatoreDTO.getColoreGiocatore(),
						stdIn);

				action = new CostruzioneTesseraPermessoDTO(tesseraScelta, cittàScelta);

			}

			if (inputLine.equals("Aiuto del re")) {
				cartePolitica = azioniClient.scegliCarte(giocatoreDTO.getCartePolitica(), stdIn);
				cittàScelta = azioniClient.scegliCittà(gameStateDTO.getCittà(), giocatoreDTO.getColoreGiocatore(),
						stdIn);

				action = new CostruzioneAiutoReDTO(cartePolitica, cittàScelta);
			}

			if (inputLine.equals("Ingaggiare un aiutante")) {
				action = new IngaggioAiutanteDTO();
			}

			if (inputLine.equals("cambiare le tessere permesso")) {
				regioneScelta = azioniClient.scegliRegione(gameStateDTO.getRegioni(), stdIn);

				action = new CambioTesserePermessoDTO(regioneScelta);
			}

			if (inputLine.equals("elezione consigliere veloce")) {
				consigliereScelto = azioniClient.scegliConsigliere(gameStateDTO.getConsiglieri(), stdIn);
				regioneScelta = azioniClient.scegliRegione(gameStateDTO.getRegioni(), stdIn);

				action = new ElezioneConsigliereVeloceDTO(regioneScelta, consigliereScelto);
			}
			if (inputLine.equals("azione principale")) {
				new SecondaAzionePrincipaleDTO();
			}

			else
				System.out.println("L'azione non esiste\n Inserire un'azione valida");

			System.out.println("SENDING THE ACTION");

			try {
				if (action != null) {
					socketOut.writeObject(action);
					socketOut.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}

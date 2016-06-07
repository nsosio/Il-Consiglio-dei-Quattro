package it.polimi.ingsw.cg17;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.StringTokenizer;

import bonus.Bonus;
import bonus.BonusAiutanti;
import bonus.BonusAzionePrincipale;
import bonus.BonusCartePolitica;
import bonus.BonusGettoneRicompensa;
import bonus.BonusMoneta;
import bonus.BonusPuntiNobiltà;
import bonus.BonusPuntiVittoria;
import bonus.BonusTesseraAcquistata;
import bonus.BonusTesseraPermesso;
import game.Balcone;
import game.CartaPolitica;
import game.Colore;
import game.ColoreCittà;
import game.ColoreRe;
import game.Consigliere;
import game.GameState;
import game.Mappa;
import game.Regione;
import game.TesseraPermesso;
import utility.Utils;
import game.Mazzo;
import game.PlanciaRe;
import game.PunteggioNobiltà;
import game.Re;
import game.Città;
import game.CittàBonus;

public class Reader {

	private ArrayList<Consigliere> consiglieri; 
	private ArrayList<Regione> regioni ;
	private ArrayList<Città> cities ;
	
	public Reader(){
		this.consiglieri= new ArrayList<Consigliere>();
		this.regioni = new ArrayList<Regione>();
		this.cities = new ArrayList<Città>();
	}

	public PlanciaRe creazionePlanciaRe() throws IOException {
		ArrayList<Bonus> bonusRe = letturaBonusRe();
		Balcone balconeRe = new Balcone(4, consiglieri);
		PlanciaRe planciaRe = new PlanciaRe(balconeRe, bonusRe, letturaPunteggioNobiltà());

		return planciaRe;
	}

	public Re creazioneRe() {
		Re re = new Re(findCittàRe());
		return re;
	}
	
	// costruiscre la mappa e mette le tessere permesso alle regioni
	public Mappa creazioneMappa(String configurazione) throws IOException {
		creazioneCittà(configurazione);
		Mappa mappa = new Mappa(new HashSet<Città>(cities));
		letturaTesserePermesso(cities, regioni);
		return mappa;
	}

	// crea città e gli mette i bonus rotondi
	public void creazioneCittà(String configurazione) throws IOException {
		cities = letturaCittà(configurazione);
		letturaBonusTondiCittà();

	}

	public Mazzo<CartaPolitica> letturaCartePolitica() throws IOException {

		ArrayList<CartaPolitica> cartaPoliticaList = new ArrayList<CartaPolitica>();
		FileReader cartaPolitica = new FileReader("src/main/resources/mappa1ColoriConsiglieriCartePolitica.txt");
		BufferedReader b;
		b = new BufferedReader(cartaPolitica);
		String stringaLetta;
		int quantità = 0;
		Colore colore = null;
		stringaLetta = b.readLine();

		while (true) {
			if (stringaLetta == null)
				break;
			StringTokenizer st = new StringTokenizer(stringaLetta);

			String tmp = st.nextToken();
			if (tmp.equals("Colore")) {
				colore = new Colore(st.nextToken());
			}

			if (tmp.equals("CartePolitica")) {
				quantità = Integer.parseInt(st.nextToken());
				for (int i = 0; i < quantità; i++) {
					CartaPolitica carta = new CartaPolitica(colore);
					cartaPoliticaList.add(carta);
				}
			}
			stringaLetta = b.readLine();
		}
		b.close();
		cartaPolitica.close();
		Collections.shuffle(cartaPoliticaList);
		return new Mazzo<CartaPolitica>(cartaPoliticaList);
	}

	public ArrayList<PunteggioNobiltà> letturaPunteggioNobiltà() throws NumberFormatException, IOException {

		FileReader punteggioNobiltà = new FileReader("src/main/resources/punteggioNobiltà.txt");
		BufferedReader b;
		b = new BufferedReader(punteggioNobiltà);
		String stringaLetta;
		int lunghezza = Integer.parseInt(b.readLine());
		ArrayList<PunteggioNobiltà> nobiltà = new ArrayList<PunteggioNobiltà>(lunghezza);

		for (int i = 0; i < lunghezza; i++) {
			ArrayList<Bonus> bonus = new ArrayList<Bonus>();
			stringaLetta = b.readLine();

			if (!stringaLetta.equals("no")) {
				StringTokenizer st = new StringTokenizer(stringaLetta);
				while (st.hasMoreTokens()) {
					String tmp = st.nextToken();
					if (tmp.equals("BonusAiutanti")) {
						int quantità = Integer.parseInt(st.nextToken());
						bonus.add(new BonusAiutanti(quantità));
					} else if (tmp.equals("BonusGettoneRicompensa")) {
						int quantità = Integer.parseInt(st.nextToken());
						bonus.add(new BonusGettoneRicompensa(quantità));
					} else if (tmp.equals("BonusTesseraPermesso")) {
						bonus.add(new BonusTesseraPermesso());
					} else if (tmp.equals("BonusTesseraPermessoUsata")) {
						bonus.add(new BonusTesseraAcquistata());
					} else if (tmp.equals("BonusAzionePrincipale")) {
						int quantità = Integer.parseInt(st.nextToken());
						bonus.add(new BonusAzionePrincipale(quantità));
					} else if (tmp.equals("BonusPuntiVittoria")) {
						int quantità = Integer.parseInt(st.nextToken());
						bonus.add(new BonusPuntiVittoria(quantità));
					} else if (tmp.equals("BonusCartePolitica")) {
						int quantità = Integer.parseInt(st.nextToken());
						bonus.add(new BonusCartePolitica(quantità));
					} else if (tmp.equals("BonusMoneta")) {
						int quantità = Integer.parseInt(st.nextToken());
						bonus.add(new BonusCartePolitica(quantità));
					}
				}
			}
			nobiltà.add(new PunteggioNobiltà(i, bonus));
		}
		b.close();
		punteggioNobiltà.close();
		return nobiltà;
	}

	public ArrayList<Consigliere> letturaConsigliere() throws IOException {

		FileReader cons = new FileReader("src/main/resources/mappa1ColoriConsiglieriCartePolitica.txt");

		BufferedReader b;
		b = new BufferedReader(cons);
		String stringaLetta;
		int quantità = 0;
		Colore colore = null;
		stringaLetta = b.readLine();

		while (true) {
			if (stringaLetta == null)
				break;
			StringTokenizer st = new StringTokenizer(stringaLetta);

			String tmp = st.nextToken();
			if (tmp.equals("Colore")) {
				colore = new Colore(st.nextToken());
			}

			if (tmp.equals("Consiglieri")) {
				quantità = Integer.parseInt(st.nextToken());
				for (int i = 0; i < quantità; i++) {
					Consigliere consigliere = new Consigliere(colore);
					consiglieri.add(consigliere);
				}
			}
			stringaLetta = b.readLine();
		}
		b.close();
		cons.close();
		Collections.shuffle(consiglieri);
		System.out.println("consiglieri :"+consiglieri.size());
		return consiglieri;
	}

	public ArrayList<Regione> letturaRegioni() throws IOException {

		FileReader reg = new FileReader("src/main/resources/regioni.txt");
		BufferedReader b;
		b = new BufferedReader(reg);
		String stringaLetta;

		while (true) {
			stringaLetta = b.readLine();
			if (stringaLetta == null)
				break;
			StringTokenizer st = new StringTokenizer(stringaLetta);
			while (st.hasMoreTokens()) {
				String nomeregione = st.nextToken();
				int nbonus = 0;
				if (st.hasMoreTokens())
					nbonus = Integer.parseInt(st.nextToken());
				Mazzo<TesseraPermesso> mazzo = new Mazzo<TesseraPermesso>();
				Balcone balcone = new Balcone(4, consiglieri);
				BonusPuntiVittoria bonusPuntiVittoria = new BonusPuntiVittoria(nbonus);
				Regione regione = new Regione(nomeregione, mazzo, bonusPuntiVittoria, balcone);
				regioni.add(regione);
			}
		}
		b.close();
		reg.close();
		return regioni;
	}

	public ArrayList<Città> letturaCittà(String configurazione) throws IOException {

		ArrayList<Colore> coloriCittà = new ArrayList<Colore>();
		FileReader città = new FileReader("src/main/resources/" + configurazione + "Città.txt");
		BufferedReader b;
		b = new BufferedReader(città);
		String stringaLetta;
		stringaLetta = b.readLine();

		// Creo coloricittà salvati in un arraylist
		while (!stringaLetta.equals("CITTA")) {
			StringTokenizer st = new StringTokenizer(stringaLetta);
			String colore = st.nextToken();
			ColoreCittà colorecittà = null;
			ColoreRe coloreRe = null;
			int puntiBonus = 0;
			if (!colore.equals("Re")) {
				if (st.hasMoreTokens()) {
					puntiBonus = Integer.parseInt(st.nextToken());
					colorecittà = new ColoreCittà(colore, new BonusPuntiVittoria(puntiBonus));
					coloriCittà.add(colorecittà);
				}
			} else {
				coloreRe = new ColoreRe(colore);
				coloriCittà.add(coloreRe);
			}
			stringaLetta = b.readLine();
		}

		// Ciclo le regioni poi i colori e setto la città
		for (Regione regione : regioni) {
			String numero = b.readLine();
			if (Utils.isNumeric(numero)) {
				int numerocittà = Integer.parseInt(numero);
				for (int i = 0; i < numerocittà; i++) {
					String tmp = b.readLine();
					StringTokenizer st = new StringTokenizer(tmp);
					while (st.hasMoreTokens()) {
						String nome = st.nextToken();
						String col = st.nextToken();
						for (Colore color : coloriCittà) {
							if (col.equals(color.getColore())) {
								if (!col.equals("Re")) {
									ArrayList<Bonus> bonus = new ArrayList<>();
									ColoreCittà colore = (ColoreCittà) color;
									CittàBonus c = new CittàBonus(nome, regione, colore, bonus);
									cities.add(c);
								} else {
									ColoreRe colore = (ColoreRe) color;
									Città c = new Città(nome, regione, colore);
									cities.add(c);
								}
							}

						}
					}
				}
			}
		}
		String stringa = b.readLine();
		if (stringa != null) {
			if (stringa.equals("CITTACOLLEGATE")) {
				for (Città c : cities) {
					StringTokenizer st = new StringTokenizer(b.readLine());
					if (st.hasMoreTokens()) {
						String nome = st.nextToken();
						if (nome.equals(c.getNome())) {
							while (st.hasMoreTokens()) {
								String cittàCollegata = st.nextToken();
								Città cittàToFind = findCittà(cittàCollegata);
								c.getCittàCollegate().add(cittàToFind);
							}
						} else
							continue;
					}
				}
			}
		}

		b.close();
		città.close();
		return cities;
	}

	public ArrayList<Bonus> letturaBonusRe() throws IOException {

		ArrayList<Bonus> bonusRe = new ArrayList<>();

		FileReader bonus = new FileReader("src/main/resources/bonusRe.txt");
		BufferedReader b;
		b = new BufferedReader(bonus);

		while (true) {
			String letta = b.readLine();
			if (letta == null)
				break;
			bonusRe.add(new BonusPuntiVittoria(Integer.parseInt(letta)));
		}
		b.close();
		bonus.close();
		return bonusRe;
	}

	public void letturaBonusTondiCittà() throws IOException {

		List<ArrayList<Bonus>> listaBonusTondi = new ArrayList<ArrayList<Bonus>>();
		FileReader bonusDelleCittà = new FileReader("src/main/resources/bonusDelleCittà.txt");
		BufferedReader b;
		b = new BufferedReader(bonusDelleCittà);
		String stringaLetta;

		while (true) {
			// legge riga e controlla ci sia scritto qualcosa
			stringaLetta = b.readLine();
			if (stringaLetta == null)
				break;

			// spezzo la riga letta in token e leggo nome bonus e intero per i
			// bonus di cui ne ho bisogno
			StringTokenizer st = new StringTokenizer(stringaLetta);
			ArrayList<Bonus> bonus = new ArrayList<>();

			while (st.hasMoreTokens()) {

				String nomeBonus = st.nextToken();

				if (nomeBonus.equals("BonusCartaPolitica")) {
					int quantità = Integer.parseInt(st.nextToken());
					bonus.add(new BonusCartePolitica(quantità));
				} else if (nomeBonus.equals("BonusMoneta")) {
					int quantità = Integer.parseInt(st.nextToken());
					bonus.add(new BonusMoneta(quantità));
				} else if (nomeBonus.equals("BonusPuntiNobiltà")) {
					int quantità = Integer.parseInt(st.nextToken());
					bonus.add(new BonusPuntiNobiltà(quantità));
				} else if (nomeBonus.equals("BonusAiutanti")) {
					int quantità = Integer.parseInt(st.nextToken());
					bonus.add(new BonusAiutanti(quantità));
				} else if (nomeBonus.equals("BonusPuntiVittoria")) {
					int quantità = Integer.parseInt(st.nextToken());
					bonus.add(new BonusPuntiVittoria(quantità));
				}
			}
			listaBonusTondi.add(bonus);

		}
		b.close();
		bonusDelleCittà.close();
		Collections.shuffle(listaBonusTondi);
		for (Città c : cities) {
			if (c instanceof CittàBonus && !listaBonusTondi.isEmpty()) {
				((CittàBonus) c).setBonus(listaBonusTondi.remove(0));
			}

		}
	}

	public void letturaTesserePermesso(ArrayList<Città> cities, ArrayList<Regione> regioni) throws IOException {

		FileReader t = new FileReader("src/main/resources/tesseraPermesso.txt");
		BufferedReader b;
		b = new BufferedReader(t);
		String stringaLetta;
		stringaLetta = b.readLine();

		while (stringaLetta != null) {

			for (Regione r : regioni) {
				if (stringaLetta == null)
					break;
				while (!stringaLetta.equals("FINEREGIONE")) {
					StringTokenizer st = new StringTokenizer(stringaLetta);
					ArrayList<Città> cit = new ArrayList<>();
					// aggiunge le citta all'arraylist
					while (st.hasMoreTokens()) {
						cit.add(findCittà(st.nextToken()));
					}

					// aggiunge i bonus all'arraylist
					stringaLetta = b.readLine();
					ArrayList<Bonus> bonus = new ArrayList<Bonus>();
					StringTokenizer str = new StringTokenizer(stringaLetta);

					String tmp = str.nextToken();
					while (true) {
						if (tmp.equals("BonusAiutanti")) {
							int quantità = Integer.parseInt(str.nextToken());
							bonus.add(new BonusAiutanti(quantità));
						} else if (tmp.equals("BonusAzionePrincipale")) {
							int quantità = Integer.parseInt(str.nextToken());
							bonus.add(new BonusAzionePrincipale(quantità));
						} else if (tmp.equals("BonusMoneta")) {
							int quantità = Integer.parseInt(str.nextToken());
							bonus.add(new BonusMoneta(quantità));
						} else if (tmp.equals("BonusPuntiVittoria")) {
							int quantità = Integer.parseInt(str.nextToken());
							bonus.add(new BonusPuntiVittoria(quantità));
						} else if (tmp.equals("BonusCartePolitica")) {
							int quantità = Integer.parseInt(str.nextToken());
							bonus.add(new BonusCartePolitica(quantità));
						} else if (tmp.equals("BonusPuntiNobiltà")) {
							int quantità = Integer.parseInt(str.nextToken());
							bonus.add(new BonusPuntiNobiltà(quantità));
						}
						if (str.hasMoreTokens())
							tmp = str.nextToken();
						else
							break;
					}

					new TesseraPermesso(cit, bonus, r);
					stringaLetta = b.readLine();
				}
				stringaLetta = b.readLine();
				r.getMazzoTesserePermesso().mescolaCarte();
				r.getTesserePermessoScoperte().add(r.getMazzoTesserePermesso().pescaCarte());
				r.getTesserePermessoScoperte().add(r.getMazzoTesserePermesso().pescaCarte());
			}
		}
		b.close();
		t.close();
	}

	public Città findCittà(String c) {
		for (Città i : cities) {
			if (i.getNome().equals(c)) {

				return i;
			}
		}
		return null;
	}

	// manca eccezione
	public Regione findRegione(String c) {
		for (Regione i : regioni) {
			if (i.getNome().equals(c)) {

				return i;
			}
		}
		return null;
	}

	public Città findCittàRe() {
		for (Città c : cities) {
			if (c.getColoreCittà().getColore().equals("Re")) {
				return c;
			}
		}
		return null;
	}

}
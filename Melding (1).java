/*
I tillegg til String-objektet som utgjør innholdet i meldingen, må hver melding ha et
sekvensnummer og ID-en til kanalen meldingen kom fra. Denne informasjonen er nødvendig
for å kunne skille meldingene etter kanal og sortere dem i riktig rekkefølge slik at meldingene
fra hver kanal kan skrives ut sammen.
*/

public class Melding implements Comparable<Melding>{
	private String stringen; 			//Meldingen er en String.
	private Kanal kanalen;				//Telegrafist og Kryptograf trenger dette senere.
	private int kanalID;				//Monitor trenger å lagre dette slik at Kryptograf og operasjonsleder kan lese av meldingene fra riktig kanal.
	private int sekvensnummer;			//Operasjonssentral trenger dette senere.
	static private int counter = 0;  	//Sekvensnummer -Unik for hver melding som kommer inn, i monitor blir det sortert i stigende rekkefølge.
	private String stringen_;

	public Melding(String stringen, int kanalID){
		this.stringen = stringen;
		this.kanalID = kanalID;
		this.sekvensnummer = counter++; //Bruker dette til å sortere meldingene i riktig rekkefølge når det skal dekrypteres i monitor.
	}

	public String getStringen(){
		return this.stringen;			//Returnerer melding.
	}
	public int getKanalID(){
		return kanalID;					//Returnerer kanalID som traaden har plukket opp.
	}
	public int getSekvensnummer(){
		return this.sekvensnummer;		//Returnerer sekvensnummer slik at traaden kan lagre rekkefølgen meldingene kom.	

	}
	public void settDekryptertString(String stringen_){
		this.stringen = stringen_;
	}

	public int compareTo(Melding other){
		if (this.kanalID != other.getKanalID()) {
			return this.kanalID-other.getKanalID();
		}
		else{
			return this.sekvensnummer - other.getSekvensnummer();
		}
	}


	@Override
	public String toString(){
		String stringen = "";
		stringen =  "\nKanal ID: "  + getStringen();
		stringen += "\nSekvensnummer: " + getSekvensnummer();
		stringen += "\nMelding: " + getStringen();
		return stringen;

	}
}
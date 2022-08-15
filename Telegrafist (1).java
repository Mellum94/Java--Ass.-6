/*

Hver telegrafist lytter til sin kanal som det kommer krypterte meldinger på. Når han mottar en
melding, gir han den til monitoren. Han gjør dette til det ikke kommer flere meldinger (f.eks.
når han mottar en melding som signaliserer SLUTT). Meldingene i monitoren er krypterte og
må dekrypteres av kryptografene.

*/

public class Telegrafist implements Runnable{
	private Kanal kanalen;				//Referanse til kanalen.
	private KryptertMonitor monitoren; 	//Referanse til monitor, lagret i en LinkedList.
	private int kanalID;

	//public Telegrafist(Kanal kanalen, KryptertMonitor monitoren){
	public Telegrafist(Kanal kanalen, KryptertMonitor monitoren){
		//Hver telegrafist har hver sin kanal og har i oppgave å lytte etter beskjeder. 
		this.kanalen = kanalen;
		this.kanalID = kanalen.hentId(); 
		this.monitoren = monitoren;
	}


	public void run(){
		System.out.println("Telegrafist er i live");

			while(true){
				String stringen = kanalen.lytt();
				if (stringen != null) {
					Melding nyMelding = new Melding(stringen, this.kanalID); // Oppretter ny melding, sekvensnummer blir oppdatert. Meldingen hører til en gitt kanal.
					monitoren.meldingInn(nyMelding); 
					System.out.println("Telegrafist hentet melding og sendte til kryptert monitor. ");
				}
				else{
					monitoren.avsluttTraaden();
					System.out.println("Telegrafist terminerer.");
					return;
				}
			}
			
	
	}//Slutt run()

}//Slutt Telegrafist



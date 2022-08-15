
/*

Kryptograftrådene sin oppgave er å motta meldinger fra monitoren for krypterte meldinger.
Kryptografen skal så dekryptere meldingen og så sende den ferdig dekrypterte meldingen
videre til monitoren for dekrypterte meldinger. Merk at kryptografene ikke trenger å ta
hensyn til sekvensnummer eller kanal-ID når de henter ut meldingene.

*/

public class Kryptograf implements Runnable{
	private Kryptografi kryptografi;
	KryptertMonitor kryptert;  
	private DekryptertMonitor dekryptert;
	private int kanalID;


	public Kryptograf(KryptertMonitor kryptert, DekryptertMonitor dekryptert){
		this.dekryptert = dekryptert;
		this.kryptert = kryptert;
	}


	public void run(){
			try{
				while(true){
					Melding melding = kryptert.hentMelding();
					if(melding== null){
						//ingen flere meldinger å hente i kryptert monitor. Vi må melde dette fra til dekryptert-monitor.
						dekryptert.avsluttTraaden();
						System.out.println("Kryptograf har mottatt null og terminerer");
						return;
					}
					String stringen = melding.getStringen();

					String dekryptertString = Kryptografi.dekrypter(stringen);
					//Thread.sleep(1);

					melding.settDekryptertString(dekryptertString);
					dekryptert.meldingInn(melding);
				}
			}
			catch(InterruptedException e){}
			

	}//Slutt run()
}//Slutt Telegrafist



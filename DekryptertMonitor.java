import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.LinkedList;


public class DekryptertMonitor{
	private LinkedList<Melding> meldingListe = new LinkedList<Melding>();
	Lock laas = new ReentrantLock();
    private Condition harMelding = laas.newCondition();
   	private boolean aktiveTraader = true;
	private int antall_aktive_traader;

	public DekryptertMonitor(int antall_aktive_traader){
		this.antall_aktive_traader = antall_aktive_traader;
	}
   	

	//public void meldingInn(Melding meldingen, KanalID){
	public void meldingInn(Melding meldingen) throws InterruptedException{
			 System.out.println("En tråd forsøker å sette inn melding i dekryptert monitor");
			 laas.lock();
			 try{		 
			 	System.out.println("En tråd satt inn melding i dekryptert monitor");
			 	meldingListe.add(meldingen); //Lagrer meldingen bakerst i køen.
			 	harMelding.signalAll(); //Signaliserer alle sovende traader at listen har fått en melding.
			 	}
			 finally{
			 	laas.unlock();
			 }
	}//meldingInn SLUTT


	public Melding hentMelding() throws InterruptedException{
		laas.lock(); //Setter sperre for andre tråder slik at de ikke kan gjøre endringer samtidig.
		System.out.println("Operasjonsleder forsøker å hente ut en melding");
		Melding meldingenUt = null;
		try{
			boolean bolsk = true;
			//System.out.println("Monitor sender nå meldinger til operasjonssentral.");
			while(meldingListe.size() == 0 && aktiveTraader()){
				//System.out.println("Ingen melding å hente.");
				harMelding.await();
			}
			if(meldingListe.size() == 0 && !aktiveTraader()){
				//Sett at listen inneholder elementer.
				bolsk = false;
				meldingenUt = null;
				System.out.println("DekryptertMonitor er nå tømt. ");
				//harKapasitet.signalAll();
				//meldingenUt = meldingListe.remove(0); //Fjerner melding på indeks null, dvs første.

			}
			else{
				meldingenUt = meldingListe.remove(0);
				System.out.println("Operasjonsleder fikk hentet ut en melding. ");
			}
		}
		finally{
			laas.unlock();
		}
		return meldingenUt;
	}



	public void avsluttTraaden(){
		laas.lock();
		try{
			this.antall_aktive_traader --;
			if (antall_aktive_traader == 0) {
				harMelding.signalAll();
			}
			System.out.println("En kryptograf takket for seg til dekryptert monitor");
		}
		finally{
			laas.unlock();

			}
		}


public boolean aktiveTraader(){
		//laas.lock();
		//try{
		return antall_aktive_traader != 0;
		//}
		//finally{
			//laas.unlock();
		//}
	}


}
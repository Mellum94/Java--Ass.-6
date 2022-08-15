/*

Når alle telegrafistene og kryptografene er ferdige, skal operasjonslederen skrive
meldingene til fil, med en fil for hver kanal. For å kunne sortere meldingene kan det være
nyttig å legge til noe i Melding-klassen. Det kan også være lurt at operasjonslederen har en
beholder(e) for å holde styr på og sortere meldingene. Når meldingene skal skrives til fil, skal
hver melding skal være adskilt av to linjeskift. Pass på at meldingene kommer i riktig
rekkefølge!

*/


import java.util.LinkedList;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;


public class Operasjonsleder implements Runnable{
	private SortertLenkeliste<Melding> meldingliste = new SortertLenkeliste<Melding>();
	private DekryptertMonitor monitoren;
	Lock laas = new ReentrantLock();
	private Condition harMelding = laas.newCondition();

	public Operasjonsleder(DekryptertMonitor monitoren){
		this.monitoren = monitoren;
	}

	public void run(){
		try{
			while(true){
				Melding melding = monitoren.hentMelding();
				if(melding != null){
					meldingliste.leggTil(melding);
					//System.out.println(melding.getStringen());
				}

				if(melding == null){
					System.out.println("Ingen flere meldinger til OP.");
					skrivTilFil();
					return;
				}
			}
		}
		catch(InterruptedException e){}
		finally{}
	}


	private void skrivTilFil(){
		System.out.println("Operasjonsleder skriver nå til fil");
		try{
			File filen = new File("Kanal_nr_1.txt");
			PrintWriter printer = new PrintWriter(filen, "UTF-8");
			int i = 1; 
			for(Melding meldingen: meldingliste){
				if(meldingen.getKanalID() == i){
					//Da listen er sortert trenger bare skrifTilFil å printe etter kanal.
					printer.println(meldingen.getStringen() + "\n");
				} 
				
				else{
					printer.close();
					i = meldingen.getKanalID();
					filen = new File("Kanal_nr_" + i + ".txt");
					printer = new PrintWriter(filen, "utf-8\n");
					printer.println(meldingen.getStringen() );

				}
			
			}
		
		printer.close();
		
		}
		catch(FileNotFoundException | UnsupportedEncodingException f){}
	}
}








//Søppel? 

/*
		try{
			boolean bolsk = true;
			while(bolsk){
				Melding meldingen = monitoren.hentMelding();
				if(Meldingen == null){
					System.out.println("Ingen flere meldinger");
					this.skrivTilFil(dsMeldingliste);
					for(Melding meldinger: dsMeldingliste){
						System.out.println(meldinger.getKanalID() + " " meldinger.getSekvensnummer());
					}
					bolsk = false;
				}
				if(dsMeldingliste.size() == 0){
					System.out.println("ferdig.");
					dsMeldingliste.add(meldinger);
				}
				else{
					
				}
			}
		}
	}
*/
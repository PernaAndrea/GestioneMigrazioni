package it.polito.tdp.borders.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class Simulatore {

	//modello -> qual è lo stato del sistema ad ogni passo 
	private Graph<Country,DefaultEdge> grafo ;
	
	//tipi di evento ->coda prioritaira
	private PriorityQueue<Evento> queue; // fondamentale quando aggiungiamo eventi mentre stiamo simulando,
	//(quando si creano dinamicamente )
	//altrimenti ne posso fare a meno della PriorityQueue ( es. Lab.10 )
	
	//parametri della simulazione
	private int N_MIGRANTI = 1000;
	private Country partenza;
	
	//valori in output
	private int T=-1;
	private Map<Country,Integer> stanziali;
	
	public void init(Country country, Graph<Country,DefaultEdge> grafo) {
		
		this.partenza= country;
		this.grafo = grafo;
		
		//imposto lo stato iniziale
		this.T=1;
		this.stanziali= new HashMap<>();
		//inizializzo la mappa 
		for(Country c  : this.grafo.vertexSet()) {
			stanziali.put(c, 0);
		}
		//creo la coda
		this.queue = new PriorityQueue<Evento>();
		//inserisco il primo evento
		this.queue.add(new Evento(T,partenza,N_MIGRANTI));
		
	}
	
	public void run() {
		
		//finche la coda non si svuota , prendo un evento alla volta e lo eseguo
		Evento e ;
		
		while((e=this.queue.poll()) != null) {
		
			//simulo l'evento e
			this.T = e.getT();
			int nPersone = e.getN();
			Country stato = e.getCountry();
			
			//ottengo i vicini di stato
			List<Country> vicini = Graphs.neighborListOf(grafo, stato);
			
			int migrantiPerStato = (nPersone/2)/vicini.size();//persone che finiscono negli stati vicini
			
			//caso in cui il num di persone che si spostano è minore 
			//del  numero dei vicini allora nessunno si sposta
			if(migrantiPerStato > 0) {
				//le persone si possono muovere
				for(Country confinante : vicini) {
					queue.add(new Evento(e.getT()+1,confinante,migrantiPerStato));
				}
			}
			// aggiorni i valori di uno stato
			int stanziali = (nPersone - migrantiPerStato*vicini.size()) ;
			this.stanziali.put(stato, this.stanziali.get(stato)+stanziali); 
		}
		
	}
	//metodi per farmi ritornare i valori
	public Map<Country,Integer> getStanziali(){
		return this.stanziali;
	}
	public int getT() {
		return this.T;
	}
	
}

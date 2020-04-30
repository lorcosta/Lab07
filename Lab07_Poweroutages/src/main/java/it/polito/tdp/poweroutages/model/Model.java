package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO podao;
	public Map<Integer,Nerc> mapNerc=new HashMap<Integer,Nerc>();
	Integer maxAffected=0;//numero massimo di persone affette 
	Duration hoursOutage;//numero totale di ore della soluzione 
	List<PowerOutage> soluzione;//soluzione della ricorsione
	List<PowerOutage> po;//lista contenente tutti i powerOutages del NERC selezionato
	Duration maxOre;
	Integer maxAnni;
	
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public List<PowerOutage> analyseWorstCase(Duration maxOre, Nerc n, Integer maxAnni) {
		// TODO Da qui parte la ricerca ricorsiva della soluzione che chiamerà
		//la classe Analysis passando i parametri necessari, è possibile richiamare il DAO
		//in questo punto
		List<PowerOutage> parziale= new LinkedList<PowerOutage>();
		po=podao.getPowerOutages(maxOre, n, mapNerc);
		this.maxOre=maxOre;
		this.maxAnni=maxAnni;
		analysisWorstCase(parziale);
		return soluzione;
	}
	/**
	 * Ricerca ricorsiva del worst case dati in input un numero massimo di mesi e di ore di outage
	 * @param maxAnni 
	 * @param maxOre 
	 * @param po è la lista di Power Outages avvenuti nell'area NERC selezionata 
	 * @input X è il numero di anni massimo da dover considerare
	 * @input Y è il numero massimo di ore di outage totali
	 */
	public void analysisWorstCase(List<PowerOutage> parziale) {
		if(peopleAffected(parziale)>maxAffected && totHoursOutage(parziale).compareTo(maxOre)<0) {
			maxAffected=peopleAffected(parziale);
			hoursOutage=totHoursOutage(parziale);
			soluzione=new LinkedList<PowerOutage>(parziale);
			System.out.println(soluzione+"maxAffected: "+maxAffected+" HoursOutage: "+String.format("%d:%02d:%02d",hoursOutage.toHours(), hoursOutage.toMinutesPart(), hoursOutage.toSecondsPart()));
		}//TODO pare che un potenziale problema stia nel fatto che la durata non viene sommata, qualsiasi durata è sempre zero perciò la condizione è sempre soddisfatta
		for(PowerOutage p:po) {
			if(!parziale.contains(p)) {
				parziale.add(p);
				if(totHoursOutage(parziale).compareTo(maxOre)<0 && checkAnni(parziale,maxAnni)) {
					analysisWorstCase(parziale);
				}
				parziale.remove(p);
			}
		}
	}
	/**
	 * Controlla se la soluzione parziale rispetta il vincolo sugli anni 
	 * @param parziale
	 * @param maxAnni
	 * @return true se la soluzione parziale rispetta il vincolo sugli anni, false altrimenti
	 */
	private boolean checkAnni(List<PowerOutage> parziale, Integer maxAnni) {
		//TODO controllo non corretto, quando parziale contiene un solo elemento non restituisce il valore corretto
		LocalDate annoAlto=LocalDate.of(0, 1, 1);
		LocalDate annoBasso=LocalDate.of(3000, 1, 1);
		for(PowerOutage p:parziale) {
			if(p.getDateEventFinished().isBefore(annoBasso)) {
				annoBasso=p.getDateEventFinished();
			}else if(p.getDateEventFinished().isAfter(annoAlto)) {
				annoAlto=p.getDateEventFinished();
			}
		}
		if((annoAlto.getYear()-annoBasso.getYear())<=maxAnni) return true;
		return false;
	}

	/**
	 * Calcola il totale di ore di outage per una soluzione parziale
	 * @param po
	 * @return
	 */
	private Duration totHoursOutage(List<PowerOutage> parziale) {
		//Duration totHours=Duration.ZERO;
		long totHours=0;
		for(PowerOutage p:parziale) {
			totHours+=p.getDurationOutage().toSeconds();
		}
		return Duration.ofSeconds(totHours);
	}
	/**
	 * Calcola il numero totale di persone coinvolte da un disservizio
	 * @param po
	 * @return numeber o people affected into partial solution
	 */
	private Integer peopleAffected(List<PowerOutage> parziale) {
		Integer affected=0;
		for(PowerOutage p:parziale) {
			affected+=p.getCustomersAffected();
		}
		return affected;
	}
}
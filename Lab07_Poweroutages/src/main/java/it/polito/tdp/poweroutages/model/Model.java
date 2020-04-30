package it.polito.tdp.poweroutages.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO podao;
	public Map<Integer,Nerc> mapNerc=new HashMap<Integer,Nerc>();
	Integer maxAffected=0;//numero massimo di persone affette 
	List<PowerOutage> soluzione;//soluzione della ricorsione
	List<PowerOutage> po;//lista contenente tutti i powerOutages del NERC selezionato
	LocalTime maxOre;
	Integer maxAnni;
	
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public List<PowerOutage> analyseWorstCase(LocalTime maxOre, Nerc n, Integer maxAnni) {
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
		if(peopleAffected(parziale)>maxAffected && totHoursOutage(parziale).isBefore(maxOre)) {
			maxAffected=peopleAffected(parziale);
			soluzione=new LinkedList<PowerOutage>(parziale);
			System.out.println(soluzione);
		}
		for(PowerOutage p:po) {
			if(!parziale.contains(p)) {
				parziale.add(p);
				if(totHoursOutage(parziale).isBefore(maxOre) && checkAnni(parziale,maxAnni)) {
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
		LocalDate annoBasso=LocalDate.of(0, 1, 1);
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
	private LocalTime totHoursOutage(List<PowerOutage> parziale) {
		LocalTime totHours=LocalTime.of(0, 0, 0);
		for(PowerOutage p:parziale) {
			totHours.plus(p.getDurationOutage());
		}
		return totHours;
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
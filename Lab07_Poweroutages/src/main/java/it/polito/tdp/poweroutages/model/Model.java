package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO podao;
	public Map<Integer,Nerc> mapNerc=new HashMap<Integer,Nerc>();
	
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	 //PowerOutages p= new PowerOutages(0,new HashMap<Integer,Nerc>(0),1, new LocalDateTime(null, null));

	public void analyseWorstCase(LocalTime maxOre, Nerc n, Integer maxAnni) {
		// TODO Da qui parte la ricerca ricorsiva della soluzione che chiamerà
		//la classe Analysis passando i parametri necessari, è possibile richiamare il DAO
		//in questo punto
		
	}

}

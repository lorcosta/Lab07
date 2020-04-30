package it.polito.tdp.poweroutages.model;

import java.time.LocalTime;
import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		//System.out.println(model.getNercList());
		LocalTime x=LocalTime.of(23,0);
		List<Nerc> NERC=model.getNercList();
    	for(Nerc n:NERC) {
    		model.mapNerc.put(n.getId(),n);
    	}
		model.analyseWorstCase(x,NERC.get(4),1);

	}

}

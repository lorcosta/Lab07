package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		//System.out.println(model.getNercList());
		List<Nerc> NERC=model.getNercList();
    	for(Nerc n:NERC) {
    		model.mapNerc.put(n.getId(),n);
    	}
    	Duration maxOre=Duration.ofHours(200);
		model.analyseWorstCase(maxOre, NERC.get(3), 4);
	}

}

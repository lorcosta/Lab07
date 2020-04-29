package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class PowerOutages {
	private List<Integer> id;
	private Map<Integer, Nerc> nercId;
	private Integer customersAffected;
	private LocalDateTime durationOutage;
}

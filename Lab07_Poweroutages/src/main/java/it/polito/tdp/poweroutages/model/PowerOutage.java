package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.time.LocalDate;

public class PowerOutage {
	private Integer id;
	private Nerc nerc;
	private Integer customersAffected;
	private Duration durationOutage;
	private LocalDate dateEventFinished;
	/**
	 * Costruisce un oggetto di tipo {@link PowerOutage}
	 * @param id
	 * @param nercId
	 * @param customersAffected
	 * @param durationOutage
	 */
	public PowerOutage(Integer id, Nerc nerc, Integer customersAffected,
			Duration durationOutage, LocalDate dateEventFinished) {
		super();
		this.id = id;
		this.nerc = nerc;
		this.customersAffected = customersAffected;
		this.durationOutage = durationOutage;
		this.dateEventFinished=dateEventFinished;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Nerc getNercId() {
		return nerc;
	}
	public void setNercId(Nerc nerc) {
		this.nerc = nerc;
	}
	public Integer getCustomersAffected() {
		return customersAffected;
	}
	public void setCustomersAffected(Integer customersAffected) {
		this.customersAffected = customersAffected;
	}
	public Duration getDurationOutage() {
		return durationOutage;
	}
	public void setDurationOutage(Duration durationOutage) {
		this.durationOutage = durationOutage;
	}
	
	public Nerc getNerc() {
		return nerc;
	}
	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}
	public LocalDate getDateEventFinished() {
		return dateEventFinished;
	}
	public void setDateEventFinished(LocalDate dateEventFinished) {
		this.dateEventFinished = dateEventFinished;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PowerOutage other = (PowerOutage) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return id+" "+customersAffected+" "+dateEventFinished.toString();
	}
	
	
}
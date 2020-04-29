package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class PowerOutages {
	private Integer id;
	private Nerc nerc;
	private Integer customersAffected;
	private LocalTime durationOutage;
	/**
	 * Costruisce un oggetto di tipo {@link PowerOutages}
	 * @param id
	 * @param nercId
	 * @param customersAffected
	 * @param durationOutage
	 */
	public PowerOutages(Integer id, Nerc nerc, Integer customersAffected,
			LocalTime durationOutage) {
		super();
		this.id = id;
		this.nerc = nerc;
		this.customersAffected = customersAffected;
		this.durationOutage = durationOutage;
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
	public LocalTime getDurationOutage() {
		return durationOutage;
	}
	public void setDurationOutage(LocalTime durationOutage) {
		this.durationOutage = durationOutage;
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
		PowerOutages other = (PowerOutages) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}

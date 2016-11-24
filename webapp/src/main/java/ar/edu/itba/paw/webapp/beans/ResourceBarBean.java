package ar.edu.itba.paw.webapp.beans;

import java.util.List;
import java.util.Set;

import ar.edu.itba.model.Resource;

public class ResourceBarBean {
	private Set<Resource> resources;
	private List<Double> rates;
	private int limit;
	
	public ResourceBarBean(Set<Resource> resources, int limit, List<Double> rates){
		this.resources = resources;
		this.limit = limit;
		this.rates = rates;
	}

	public List<Double> getRates() {
		return rates;
	}

	public void setRates(List<Double> rates) {
		this.rates = rates;
	}

	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
}

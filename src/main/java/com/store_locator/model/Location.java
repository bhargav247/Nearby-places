package com.store_locator.model;

import java.util.HashSet;
import java.util.Set;

public class Location {

	private String country_name;
	private String latitude;
	private String longitude;
	private Set<Store> stores = new HashSet<Store>(0);
	public String getCountry_name() {
		return country_name;
	}
	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public Set<Store> getStores() {
		return stores;
	}
	public void setStores(Set<Store> stores) {
		this.stores = stores;
	}
	
	
	
}

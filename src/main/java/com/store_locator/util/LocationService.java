package com.store_locator.util;

import java.util.List;
import java.util.Set;

import com.store_locator.model.Location;
import com.store_locator.model.Store;
import com.store_locator.model.StoreSearchCriteria;

public interface LocationService {

	public final String GEO_CODE_SERVER = "http://maps.googleapis.com/maps/api/geocode/json?";
	//public final String STORE_ADDRESS = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&key=AIzaSyB25AHIi8Chw_hvLPvDd9wIaaV6PTGPxYk";
	//public final String STORE_ADDRESS = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&key=AIzaSyB25AHIi8Chw_hvLPvDd9wIaaV6PTGPxYk";
	//public final String STORE_ADDRESS = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=1.307476,103.883208&radius=500&key=AIzaSyB25AHIi8Chw_hvLPvDd9wIaaV6PTGPxYk";
	public final String STORE_ADDRESS = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
	public final String KEY = "AIzaSyB25AHIi8Chw_hvLPvDd9wIaaV6PTGPxYk";
	public final String DEFAULT_RADIUS = "500";
	public String buildGeoURL(String zipcode);
	public String buildStoreSearchURL(Location location, StoreSearchCriteria storeSearchCriteria);
	public String getResult(String geo_url);
	public List<Location> parseLocation(String response);
	public Set<Store> parseStoreDetail(String response);
}

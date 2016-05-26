package com.store_locator.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import com.store_locator.model.Location;
import com.store_locator.model.Store;
import com.store_locator.model.StoreSearchCriteria;

@Component
public class LocationServiceImpl implements LocationService{

	

	@Override
	public String buildGeoURL(String zipcode) {
		StringBuilder builder = new StringBuilder();

		builder.append(GEO_CODE_SERVER);

		builder.append("address=");
		builder.append(zipcode.replaceAll(" ", "+"));
		builder.append("&sensor=false");

		return builder.toString();
	}

	@Override
	public String getResult(String url) {
		
		InputStream inputStream = null;
		String json = "";

		try {
			@SuppressWarnings("deprecation")
			HttpClient client = new DefaultHttpClient();
			// HttpPost post = new
			// HttpPost("https://maps.googleapis.com/maps/api/geocode/json?address=10115,germany");
			HttpPost post = new HttpPost(url);
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			inputStream = entity.getContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
			StringBuilder sbuild = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sbuild.append(line);
			}
			inputStream.close();
			json = sbuild.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		
		System.out.println("before json");
		return json;
	}

	@Override
	public List<Location> parseLocation(String response) {
		JSONParser parser = new JSONParser();
		List<Location> listLocation = new ArrayList();
		try {

			Object obj = parser.parse(response);

			JSONObject jsonObject = (JSONObject) obj;

			JSONArray jsonArrayResults = (JSONArray) jsonObject.get("results");
			Iterator<JSONObject> resultIterator = jsonArrayResults.iterator();
			// System.out.println("\n\n\n result \n\n\n" + results.size());

			while (resultIterator.hasNext()) {
				// System.out.println(resultIterator.next());

				JSONObject data = resultIterator.next();

				Location location = new Location();

				// get address component for country

				JSONArray jsonArrayAddressComponents = (JSONArray) data.get("address_components");

				Iterator<JSONObject> addresscomponentsIterator = jsonArrayAddressComponents.iterator();
				while (addresscomponentsIterator.hasNext()) {
					JSONObject jsonObjectType = addresscomponentsIterator.next();
					JSONArray jsonArrayType = (JSONArray) jsonObjectType.get("types");
					if (jsonArrayType.get(0).toString().equalsIgnoreCase("country")) {
						location.setCountry_name(jsonObjectType.get("long_name").toString());
					}
				}

				// get geometry for location

				JSONObject jsonObjectGeometry = (JSONObject) data.get("geometry");
				JSONObject jsonObjectLocation = (JSONObject) jsonObjectGeometry.get("location");
				location.setLongitude(jsonObjectLocation.get("lng").toString());
				location.setLatitude(jsonObjectLocation.get("lat").toString());
			listLocation.add(location);

			}

		

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return listLocation;
	}

	@Override
	public String buildStoreSearchURL(Location location, StoreSearchCriteria storeSearchCriteria) {
		StringBuilder builder = new StringBuilder();

		builder.append(STORE_ADDRESS);
		System.out.println(location.getLatitude() + "--" + location.getLongitude());
		if(location.getLatitude().trim().length()>0 && location.getLongitude().trim().length()>0){
			builder.append("location="+location.getLatitude().trim()+","+location.getLongitude().trim());
		}
		if(storeSearchCriteria.getRadius().trim().length() > 0){
			builder.append("&radius=").append(storeSearchCriteria.getRadius().trim());
		}else{
			builder.append("&radius=").append(DEFAULT_RADIUS);
		}
		
		if(storeSearchCriteria.getStore_type().trim().length() > 0){
			builder.append("&type=").append(storeSearchCriteria.getStore_type().trim());
		}
		
		if(storeSearchCriteria.getStore_name().trim().length() > 0){
			builder.append("&name=").append(storeSearchCriteria.getStore_name().trim());
		}
		
	
		builder.append("&key=" + KEY);
		// TODO Auto-generated method stub
		return builder.toString();
	}

	@Override
	public Set<Store> parseStoreDetail(String response) {
		// TODO Auto-generated method stub
		
		Set<Store> stores = new HashSet<Store>(0);
		
		
		try{
			
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(response);
		

			System.out.println(obj);
			JSONObject jb = (JSONObject) obj;

			// now read
			JSONArray jsonObject = (JSONArray) jb.get("results");

			Iterator<JSONObject> jsonObjectIterator = jsonObject.iterator();
			while (jsonObjectIterator.hasNext()) {
				Store store = new Store();
				JSONObject jsonObject2 = (JSONObject) jsonObjectIterator.next();
				JSONObject jsonObject3 = (JSONObject) jsonObject2.get("geometry");
				JSONObject location = (JSONObject) jsonObject3.get("location");
				String name = (String) jsonObject2.get("name");
				store.setStore_name(name);
				stores.add(store);
				//System.out.println(name);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		
		return stores;
	}

	
}

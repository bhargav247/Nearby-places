package com.store_locator.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.store_locator.model.Location;
import com.store_locator.model.Store;
import com.store_locator.model.StoreSearchCriteria;
import com.store_locator.util.LocationServiceImpl;

@Controller
public class LocationController {
	
	@Autowired
	private LocationServiceImpl locationServiceImpl;
	
	@RequestMapping(value = "getlocation", method = RequestMethod.POST)
	public String getLocation(StoreSearchCriteria storeSearchCriteria, Model model){
		
		//LocationServiceImpl locationServiceImpl = new LocationServiceImpl();
		String geo_url = locationServiceImpl.buildGeoURL(storeSearchCriteria.getZipcode());
		String response = locationServiceImpl.getResult(geo_url);
		List<Location> listLocation=locationServiceImpl.parseLocation(response);
		for (int i = 0; i < listLocation.size(); i++) {
			Location location = listLocation.get(i);
			String store_location_url = locationServiceImpl.buildStoreSearchURL(location, storeSearchCriteria);
			response = locationServiceImpl.getResult(store_location_url);
			Set<Store> stores = locationServiceImpl.parseStoreDetail(response);
			location.setStores(stores);
			System.out.println("country :-- " + location.getCountry_name() + "--" + location.getLongitude() + "--" + location.getLatitude());
			System.out.println("store_location_url -- " + store_location_url);
		}
		
		/*System.out.println("\n\nfinal result\n\n");
		for(int i=0;i<listLocation.size();i++){
			Location location = listLocation.get(i);
			System.out.println("country nme " + location.getCountry_name());
			System.out.println("-----------------");
			Iterator<Store> iterator = location.getStores().iterator();
			while(iterator.hasNext()){
				Store store = iterator.next();
				System.out.println("store :: " + store.getStore_name());
			}
			System.out.println("-----------------");
		}*/
		//	get sore result
		
		//
		//System.out.println(response);
		
		/*String s = location.getStore();
		System.out.println(s);*/
		model.addAttribute("listLocation", listLocation);
		return "home";
	}
	
	
}

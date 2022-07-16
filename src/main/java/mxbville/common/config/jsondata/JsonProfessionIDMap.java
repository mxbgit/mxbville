package mxbville.common.config.jsondata;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import com.google.gson.GsonBuilder;

import mxbville.util.MxRef;

public class JsonProfessionIDMap {
	
	public HashMap<String, Integer> proffessionIDMap = new HashMap<String, Integer>();

	public JsonProfessionIDMap() {
		proffessionIDMap = new HashMap<String, Integer>();
		init();
	}
	
	private void init() {
		this.proffessionIDMap.put("villager", 0);
		this.proffessionIDMap.put("peasant", 1);
		this.proffessionIDMap.put("worker", 2);
		this.proffessionIDMap.put("scholar", 3);
		this.proffessionIDMap.put("banker", 4);
		this.proffessionIDMap.put("farmer", 10);
		this.proffessionIDMap.put("butcher", 11);
		this.proffessionIDMap.put("florist", 12);
		this.proffessionIDMap.put("fisherman", 13);
		this.proffessionIDMap.put("hunter", 14);
		this.proffessionIDMap.put("vintner", 15);
		this.proffessionIDMap.put("rancher", 20);
		this.proffessionIDMap.put("cook", 21);
		this.proffessionIDMap.put("sailor", 23);
		this.proffessionIDMap.put("pirat", 24);
		this.proffessionIDMap.put("archer", 25);
	}

}

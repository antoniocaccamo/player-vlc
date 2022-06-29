package it.antanysavage.sm.player.sequences.model;

import java.io.File;
import java.util.HashMap;

public class ResourceManager {

	private HashMap<String, Resource> resources = new HashMap<String, Resource>();
	
	
	public boolean contains(File file) {
		return contains(file.getAbsolutePath());
	}
	
	public boolean contains(String path) {
		return resources.containsKey(path);
	}
	
	public void add(Resource resource) {
		resources.put(resource.getPath().getAbsolutePath(), resource);		
	}

	public Resource get(String path) {
		
		return resources.get(path);
	}
	
	@Override
	public String toString() {		
		return resources.entrySet().size() + " - " + resources.toString();
	}
	
		
}

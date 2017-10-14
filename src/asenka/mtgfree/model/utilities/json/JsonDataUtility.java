package asenka.mtgfree.model.utilities.json;

import java.io.File;
import java.net.URL;
import java.util.Set;

import asenka.mtgfree.model.pojo.MtgSet;

public class JsonDataUtility {

	/**
	 * 
	 */
	private static final String DEFAULT_JSON_FILEPATH = "./resources/data/AllSets-x.json";

	/**
	 * 
	 */
	private static JsonDataUtility singleton;

	/**
	 * 
	 */
	private File jsonFile;
	
	/**
	 * 
	 */
	private Set<MtgSet> sets;

	/**
	 * @param jsonFilepath
	 */
	private JsonDataUtility(String jsonFilepath) {

		this.jsonFile = new File(jsonFilepath);
		
		loadDataFromFile();
	}

	private void loadDataFromFile() {

		// TODO Auto-generated method stub
		
	}

	public static JsonDataUtility getInstance() {

		if (singleton == null) {
			singleton = createInstance(DEFAULT_JSON_FILEPATH);
		}
		return singleton;
	}

	public static JsonDataUtility createInstance(String jsonFilePath) {

		if (singleton != null) {
			throw new RuntimeException("createInstance method already called.");
		} else {
			singleton = new JsonDataUtility(jsonFilePath);
		}
		return singleton;
	}

	public static JsonDataUtility createInstance(URL jsonFileUrl) {

		if (singleton != null) {
			throw new RuntimeException("createInstance method already called.");
		} else {
			singleton = new JsonDataUtility(jsonFileUrl.getPath());
		}
		return singleton;
	}

}

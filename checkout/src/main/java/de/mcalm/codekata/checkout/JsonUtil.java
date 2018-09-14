package de.mcalm.codekata.checkout;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

	public static Map<String, Map<Integer, Double>> fromJson(String json) {

		try {
			ObjectMapper mapper = new ObjectMapper();
			// Map<String, Map<String, Double>> rules = mapper
			// .readValue(new
			// File("C:\\Users\\wiese\\eclipse-workspace\\checkout\\rules.json"),
			// HashMap.class);
			Map<String, Map<String, Double>> rules = mapper.readValue(json, Map.class);

			Set<Entry<String, Map<String, Double>>> entrySet = rules.entrySet();

			Map<String, Map<Integer, Double>> rulesToReturn = new HashMap<>();
			for (Entry<String, Map<String, Double>> entry : entrySet) {
				String item = entry.getKey();
				Map<String, Double> value = entry.getValue();
				Set<Entry<String, Double>> entrySet2 = value.entrySet();
				Map<Integer, Double> ruleValues = new HashMap<>();
				for (Entry<String, Double> entry2 : entrySet2) {
					ruleValues.put(Integer.parseInt(entry2.getKey()), entry2.getValue());
				}
				rulesToReturn.put(item, ruleValues);
			}
			return rulesToReturn;
		} catch (Exception e) {
			Logger.getGlobal().log(Level.SEVERE, "Error occured while parsing json");
			System.exit(-1);
		}
		return null;
	}
}

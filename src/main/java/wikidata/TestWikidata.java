package wikidata;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TestWikidata {

	public static void main(String[] args) throws IOException, ParseException {
		JSONParser parser = new JSONParser();

		try {

			Object obj = parser.parse(new FileReader("wikidataRequest.txt"));

			JSONObject jsonObject = (JSONObject) obj;
			System.out.println(jsonObject.toString());

			// String name = (String) jsonObject.get("article");
			// System.out.println(name);
			//
			// long age = (Long) jsonObject.get("age");
			// System.out.println(age);

			// loop array
			JSONArray msg = (JSONArray) jsonObject.get("items");
			JSONObject locArrObj = (JSONObject) msg.get(0);
			JSONArray msg2 = (JSONArray) locArrObj.get("articles");
			System.out.println(msg2.size());
			for (Object object : msg2) {
				JSONObject locArrObj2 = (JSONObject) object;
				System.out.println(locArrObj2.get("article"));
				System.out.println(locArrObj2.get("views"));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	private static String readFile(String file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");

		while ((line = reader.readLine()) != null) {
			stringBuilder.append(line);
			stringBuilder.append(ls);
		}

		return stringBuilder.toString();
	}

}

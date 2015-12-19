package wikidata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class WikidataScript {

	public static String endPoint = "https://wikimedia.org/api/rest_v1/metrics/pageviews/top/wikidata/all-access/";
	static DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	static int DaysNumber = 0;
	static int ProcessedDays = 0;
	static int DaysReachedLimits = 0;

	public static void usage() {

		System.out.println("WikidataScript starData endDate");
		System.out.println("Dates should have the formate yyyy-MM-dd");
	}

	public static void main(String[] args) throws FileNotFoundException,
			UnsupportedEncodingException {

		if (args.length != 2) {
			usage();
			System.exit(0);
		}

		File dis = new File("filtered");
		if (!dis.exists()) {
			dis.mkdirs();
		}
		PrintWriter writer = new PrintWriter(dis + "/" + "Wikidata.json",
				"UTF-8");

		Date startDate;
		Date endDate;
		try {
			startDate = formatter.parse(args[0]);
			endDate = formatter.parse(args[1]);

			Calendar start = Calendar.getInstance();
			start.setTime(startDate);
			Calendar end = Calendar.getInstance();
			end.setTime(endDate);

			for (Date date = start.getTime(); start.after(end); start.add(
					Calendar.DAY_OF_MONTH, -1), date = start.getTime()) {
				DaysNumber++;
				JSONObject json = runJson(endPoint, date);
				if (json == null) {
					System.out.println("error! Json is null");
					continue;
				} else {

					writer.println(json.toString());
				}
				ProcessedDays++;

			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("The number of processed days: " + ProcessedDays);
		writer.close();
	}

	public static JSONObject runJson(String stringurl, Date date) {
		JSONObject json = null;

		try {
			String dateString = df.format(date);

			String urlWithParams = stringurl + dateString;

			System.out.println(urlWithParams);
			URL url = new URL(urlWithParams);
			URLConnection connection = url.openConnection();

			String line;
			StringBuilder builder = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}

			json = new JSONObject(builder.toString());

		} catch (IOException e) {
			e.printStackTrace();

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return json;
	}

}

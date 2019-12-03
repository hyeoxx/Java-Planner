package directions;

import contents.setting;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DirectionFactory {
	private static URL url;
	private static BufferedReader br;
	private static HttpURLConnection con;
	
	public static point searchPosition(String v1) {
		String x = "", y = "";
		String text = "";
		point ret = null;
		String urls = "https://maps.googleapis.com/maps/api/geocode/json?address="+v1+",+CA&key="+setting.key;
		try {
			
		url = new URL(urls);
		
		con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36");
		br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                
		String temp;
		while ((temp = br.readLine()) != null) {
			text += temp;
                        text += "\n";
		}
		
		JsonParser parser = new JsonParser();
		JsonElement jsonArray = parser.parse(text);
                JsonElement js2 = parser.parse(jsonArray.getAsJsonObject().get("results").toString());
                JsonElement js3 = parser.parse(js2.getAsJsonArray().get(0).getAsJsonObject().get("geometry").toString());
                JsonElement js4 = parser.parse(js3.getAsJsonObject().get("location").toString());
                x = js4.getAsJsonObject().get("lat").toString();
                y = js4.getAsJsonObject().get("lng").toString();
		
		
		ret = new point(x, y);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
                
		return ret;
	}
}

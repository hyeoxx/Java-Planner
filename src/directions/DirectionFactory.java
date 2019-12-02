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
		con.setRequestMethod("GET"); // 이 부분은 없어도 상관 없음
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36"); // 이 부분은 없어도 상관 없음
		//con.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
		// 접속한 홈페이지에서의 정보를 버퍼에 저장
		br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			
		// 버퍼에 저장된 내용을 출력
		String temp;
		while ((temp = br.readLine()) != null) {
			text += temp;
                        text += "\n";
		}
		
		//System.out.println(text);
		
		JsonParser parser = new JsonParser();
		JsonElement jsonArray = parser.parse(text);
                JsonElement js2 = parser.parse(jsonArray.getAsJsonObject().get("results").toString());
                JsonElement js3 = parser.parse(js2.getAsJsonArray().get(0).getAsJsonObject().get("geometry").toString());
                JsonElement js4 = parser.parse(js3.getAsJsonObject().get("location").toString());
                x = js4.getAsJsonObject().get("lat").toString();
                y = js4.getAsJsonObject().get("lng").toString();
                //System.out.println(js2.getAsJsonArray().get(0).getAsJsonObject().get("geometry"));
		
		
		ret = new point(x, y);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}
}

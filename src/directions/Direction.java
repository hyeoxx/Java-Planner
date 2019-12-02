package directions;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.HashMap;
import java.util.Map;

import contents.setting;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Direction {
	
	String origin = "";
	point originPos;
	String dest = "";
	point destPos;
	URL url;
	BufferedReader br;
	HttpURLConnection con;
	
	
	public Direction(String on, point op, String dn, point dp) {
		origin = on;
		originPos = op;
		dest = dn;
		destPos = dp;
	}
	
	public void getDirection() {
		// USE GOOGLE DIRECTIONS API
		String text = "";
		String urls = "https://maps.googleapis.com/maps/api/directions/json?origin="+originPos.x+","+originPos.y+"&destination="+destPos.x+","+destPos.y+"&mode=transit&departure_time=now&key="+setting.key;
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
		
		//  System.out.println(text);
                
		JsonParser parser = new JsonParser();
		JsonElement jsonArray = parser.parse(text);
                
                JsonElement js = parser.parse(
                        parser.parse(
                                parser.parse(jsonArray.getAsJsonObject().get("routes").toString())
                                        .getAsJsonArray()
                                        .get(0)
                                        .getAsJsonObject()
                                        .get("legs")
                                        .toString()
                        ).getAsJsonArray().get(0)
                                .getAsJsonObject()
                                .get("steps")
                                .toString()
                );
                
                JsonElement transit_details = parser.parse(
                        js.getAsJsonArray()
                                .get(1).
                                getAsJsonObject().
                                get("transit_details").
                                toString()
                );
                
                String distance = parser.parse(js.getAsJsonArray().get(1).getAsJsonObject().get("distance").toString()).getAsJsonObject().get("text").toString();
                String duration = parser.parse(js.getAsJsonArray().get(1).getAsJsonObject().get("duration").toString()).getAsJsonObject().get("text").toString();
                String arrival = parser.parse(transit_details.getAsJsonObject().get("arrival_stop").toString()).getAsJsonObject().get("name").toString();
                String arrival_Time = parser.parse(transit_details.getAsJsonObject().get("arrival_time").toString()).getAsJsonObject().get("text").toString();
                String stop = parser.parse(transit_details.getAsJsonObject().get("departure_stop").toString()).getAsJsonObject().get("name").toString();
                String stop_Time = parser.parse(transit_details.getAsJsonObject().get("departure_time").toString()).getAsJsonObject().get("text").toString();
                
		System.out.println("도착지 : " + arrival + " / 예상 도착 시간 : " + arrival_Time +"("+duration+" 소요)"+ " / 가야하는 버스정류장 : "+ stop + " / 예상 버스 도착 시간 : " + stop_Time);
                
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

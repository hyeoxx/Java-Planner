package directions;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.HashMap;
import java.util.Map;

import contents.Content;
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
	
	public Route getDirection() {
		// USE GOOGLE DIRECTIONS API
		String text = "";
                Route route = null;
                
		String urls = "https://maps.googleapis.com/maps/api/directions/json?origin="+originPos.x+","+originPos.y+"&destination="+destPos.x+","+destPos.y+"&mode=transit&departure_time=now&language=ko&key="+Content.key;
		try {
		url = new URL(urls);
		
		con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36");
		br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			
		// 버퍼에 저장된 내용을 출력
		String temp;
		while ((temp = br.readLine()) != null) {
			text += temp;
                        text += "\n";
		}
                
		JsonParser parser = new JsonParser();
		JsonElement je = parser.parse(text);
                
                JsonElement je2 = parser.parse(
                                    parser.parse(je.getAsJsonObject().get("routes").toString())
                                        .getAsJsonArray()
                                        .get(0)
                                        .getAsJsonObject()
                                        .get("legs")
                                        .toString()
                                   );
                
                JsonElement je3 = parser.parse(
                        je2.getAsJsonArray().get(0)
                                .getAsJsonObject()
                                .get("steps")
                                .toString()
                );
                String departure = parser.parse(je2.getAsJsonArray().get(0).toString()).getAsJsonObject().get("start_address").toString().replaceAll("\"", ""); // 출발지 parser.parse(je2.getAsJsonArray().get(0).toString()).getAsJsonObject().get("text").toString()
                String arrival = parser.parse(je2.getAsJsonArray().get(0).toString()).getAsJsonObject().get("end_address").toString().replaceAll("\"", ""); // 도착지
                String distance = parser.parse(parser.parse(je2.getAsJsonArray().get(0).toString()).getAsJsonObject().get("distance").toString()).getAsJsonObject().get("text").toString().replaceAll("\"", ""); // 거리
                String duration = parser.parse(parser.parse(je2.getAsJsonArray().get(0).toString()).getAsJsonObject().get("duration").toString()).getAsJsonObject().get("text").toString().replaceAll("\"", ""); // 소요 시간
                String departure_time = parser.parse(parser.parse(je2.getAsJsonArray().get(0).toString()).getAsJsonObject().get("departure_time").toString()).getAsJsonObject().get("text").toString().replaceAll("\"", ""); // 출발 시간
                String arrival_time = parser.parse(parser.parse(je2.getAsJsonArray().get(0).toString()).getAsJsonObject().get("arrival_time").toString()).getAsJsonObject().get("text").toString().replaceAll("\"", ""); // 예상 도착 시간
                
                route = new Route(departure, arrival, distance, duration, departure_time, arrival_time);
                
                for (int i = 0; i < je3.getAsJsonArray().size(); i++) {
                    JsonElement travel_mode = parser.parse(
                        je3.getAsJsonArray()
                                .get(i).
                                getAsJsonObject().
                                get("travel_mode").
                                toString()
                    );
                    
                    
                    String Key = "";
                    String Value = "";
                    
                    switch (travel_mode.toString()) {
                        case "\"WALKING\"" :
                            Key = parser.parse(
                                    je3.getAsJsonArray()
                                            .get(i)
                                            .getAsJsonObject()
                                            .get("html_instructions")
                                            .toString()
                            ).toString();
                            
                            Value = parser.parse(
                                    parser.parse(
                                            je3.getAsJsonArray()
                                            .get(i)
                                            .getAsJsonObject()
                                            .get("duration")
                                            .toString()
                            ).toString())
                                    .getAsJsonObject()
                                    .get("text")
                                    .toString();
                            break;
                        case "\"TRANSIT\"" :
                            Key = parser.parse(
                                    je3.getAsJsonArray()
                                            .get(i)
                                            .getAsJsonObject()
                                            .get("html_instructions")
                                            .toString()
                            ).toString();
                            
                            Value = parser.parse(
                                    parser.parse(
                                            je3.getAsJsonArray()
                                            .get(i)
                                            .getAsJsonObject()
                                            .get("duration")
                                            .toString()
                            ).toString())
                                    .getAsJsonObject()
                                    .get("text")
                                    .toString();
                            break;
                    }
                    Key = Key.replaceAll("\"", "");
                    Value = Value.replaceAll("\"", "");
                    
                    route.add_route(Key+"/"+Value);
                    
                    //System.out.println(travel_mode);
                }
		} catch (IOException e) {
			e.printStackTrace();
		}
                
                return route;
	}
	
}

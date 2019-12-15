/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import contents.Content;
import contents.Plan;
import directions.Route;
import java.io.FileInputStream;
import java.util.ArrayList;

/**
 *
 * @author hyeoxx
 */
public class ReadJson {
    public static void read() {
        String json = "";
	 try {
	       // 바이트 단위로 파일읽기
	        String filePath = "plans.json"; // 대상 파일
	        FileInputStream fileStream = null; // 파일 스트림
	        
	        fileStream = new FileInputStream( filePath );// 파일 스트림 생성
	        //버퍼 선언
	        byte[ ] readBuffer = new byte[fileStream.available()];
	        while (fileStream.read( readBuffer ) != -1){}
                json = new String(readBuffer);

	        fileStream.close(); //스트림 닫기
	    } catch (Exception e) {
		e.getStackTrace();
	    }
         
        if (json.equals("")) return;
        
	JsonParser parser = new JsonParser();
	JsonElement jsonArray = parser.parse(json);
        JsonObject jo = jsonArray.getAsJsonObject();
        for (int i = 0; i < jo.size(); i++) {
            //System.out.println(jo.entrySet().toArray()[i].toString().split("=")[0]);
            if (!jo.get(jo.entrySet().toArray()[i].toString().split("=")[0]).toString().equals("[]")) {
                ArrayList<Plan> temp = new ArrayList<Plan>();
                JsonArray A = jo.get(jo.entrySet().toArray()[i].toString().split("=")[0]).getAsJsonArray();
                for (int j = 0; j < A.size(); j++) {
                    JsonElement je = A.get(j);
                    Route tempRoute = null;
                    //System.out.println(je.getAsJsonObject().get("route"));
                    if (je.getAsJsonObject().get("route") != null) {
                        JsonObject jo3 = je.getAsJsonObject().get("route").getAsJsonObject();
                        ArrayList<String> tempRoutes = new ArrayList<String>();
                        for (int k = 0; k < (jo3.get("routes").getAsJsonArray().size()); k++) {
                            tempRoutes.add(jo3.get("routes").getAsJsonArray().get(k).toString().replaceAll("\"", ""));
                        }
                        tempRoute = new Route(jo3.get("departure").toString().replaceAll("\"", ""), jo3.get("arrival").toString().replaceAll("\"", ""), jo3.get("distance").toString().replaceAll("\"", ""), jo3.get("duration").toString().replaceAll("\"", ""), jo3.get("departure_time").toString().replaceAll("\"", ""), jo3.get("arrival_time").toString().replaceAll("\"", ""), tempRoutes);
                    }
                    Plan tempPlan = new Plan(je.getAsJsonObject().get("name").toString().replaceAll("\"", ""), je.getAsJsonObject().get("content").toString().replaceAll("\"", ""), tempRoute, je.getAsJsonObject().get("time").toString().replaceAll("\"", ""));
                    temp.add(tempPlan);
                }
                
                Content.Plans.put(Integer.parseInt(jo.entrySet().toArray()[i].toString().split("=")[0]), temp);
            }
        }
        //System.out.println(jsonArray.getAsJsonObject().size());
    }
}

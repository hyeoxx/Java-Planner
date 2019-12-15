/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package directions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hyeoxx
 */
public class Route {
    public String departure = ""; // 출발지
    public String arrival = ""; // 도착지
    public String distance = ""; // 거리
    public String duration = ""; // 소요 시간
    public String departure_time = ""; // 출발 시간
    public String arrival_time = ""; // 예상 도착 시간
    //public Map<String, String> routes = new HashMap<String, String>(); // Key 어디로 어떻게 가는지, Value 예상 소요 시간
    public ArrayList<String> routes = new ArrayList<>();
    
    public Route(String departure_, String arrival_, String distance_, String duration_, String departure_time_, String arrival_time_, ArrayList<String> routes_) {
        this.departure = departure_;
        this.arrival = arrival_;
        this.distance = distance_;
        this.duration = duration_;
        this.departure_time = departure_time_;
        this.arrival_time = arrival_time_;
        routes = routes_;
    }
    
    public Route() {
        
    }
    
    public void add_route(String v1) {
        routes.add(v1);
    }
    
    public ArrayList<String> getRoutes() {
        return routes;
    }
}

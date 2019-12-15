/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contents;

import directions.Route;

/**
 *
 * @author hyeoxx
 */
public class Plan {
    /*
        1. 직접 입력 2. 어디 가야함
    */
    public String name = "";
    public String content = "";
    public Route route = null;
    public String time = ""; // hour/min split("/")
    
    public Plan(String name_, String content_, Route route_, String time_) {
        name = name_;
        content = content_;
        route = route_;
        time = time_;
    }
}

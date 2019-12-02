package direction;

import java.util.HashMap;
import java.util.Map;

import contents.setting;

public class Direction {
	
	String origin = "";
	point originPos;
	String dest = "";
	point destPos;
	
	
	public Direction(String on, point op, String dn, point dp) {
		origin = on;
		originPos = op;
		dest = dn;
		destPos = dp;
	}
	
	public void getDirection() {
		// USE GOOGLE DIRECTIONS API
		String url = "https://maps.googleapis.com/maps/api/directions/json?origin="+originPos.x+","+originPos.y+"&destination="+destPos.x+","+destPos.y+"&mode=transit&departure_time=now&key="+setting.key;
	}
	
}

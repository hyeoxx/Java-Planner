import directions.DirectionFactory;
import directions.point;
import directions.Direction;
import directions.Route;

public class main {
        public static Route route;
        
	public static void main(String[] args) {
		// 주의 : 주소 입력 할 때 띄어쓰기 없이 쭉 이어쓰기
		point a = DirectionFactory.searchPosition("백석대학교"); // 출발지 입력
		point b = DirectionFactory.searchPosition("학익고등학교"); // 도착지 입력
                Direction dir = new Direction("ㅇㅇ", a, "dd", b);
                route = dir.getDirection();
                
                System.out.println("출발지 : "+route.departure);
                System.out.println("현재 위치에서 출발지 까지 가는 예정 시간 : "+route.arrival_time);
                System.out.println("도착지 : "+route.arrival);
                System.out.println("도착 예정 시간 : "+route.arrival_time);
                System.out.println("총 소요 시간 : "+route.duration);
                System.out.println("총 거리 : "+route.distance);
                System.out.println("--------------루트------------");
                for (int i = 0; i < route.getRoutes().size(); i++) {
                    System.out.println(i+"."+route.getRoutes().keySet().toArray()[i] + " ("+route.getRoutes().values().toArray()[i]+")");
                }
	}

}

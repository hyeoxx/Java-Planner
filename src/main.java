import directions.DirectionFactory;
import directions.point;
import directions.Direction;

public class main {

	public static void main(String[] args) {

		// 주의 : 주소 입력 할 때 띄어쓰기 없이 쭉 이어쓰기
		point a = DirectionFactory.searchPosition("백석대학교"); // 출발지 입력
		point b = DirectionFactory.searchPosition("천안신세계백화점"); // 도착지 입력
                Direction dir = new Direction("ㅇㅇ", a, "dd", b);
                dir.getDirection();
	}

}

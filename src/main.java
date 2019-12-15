import contents.Notification;
import directions.Route;
import files.ReadJson;
import gui.CalendarGui;

public class main {
        public static Route route;
        // 20191214
	public static void main(String[] args) {
                ReadJson.read();
                CalendarGui gui = new CalendarGui();
                Notification note = new Notification();
                note.displayTray("10분 뒤 나가셔야합니다.", "현재 시간 오후 2시 40분\n도착 예정시간 3시 25분");
  
     }
}

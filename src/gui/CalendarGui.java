/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;
import contents.Content;
import contents.Notification;
import contents.Plan;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 *
 * @author hyeoxx
 */
       
public class CalendarGui extends JFrame implements ActionListener{

	//North
	JPanel topPane = new JPanel();
	JButton prevBtn = new JButton("◀");
        JButton nextBtn = new JButton("▶");
	JLabel yearLbl = new JLabel("년");
	JLabel monthLbl = new JLabel("월");
	JComboBox<Integer> yearCombo = new JComboBox<Integer>();
	DefaultComboBoxModel<Integer> yearModel = new DefaultComboBoxModel<Integer>();
	JComboBox<Integer> monthCombo = new JComboBox<Integer>();
	DefaultComboBoxModel<Integer> monthModel = new DefaultComboBoxModel<Integer>();

	//Center
	JPanel centerPane = new JPanel(new BorderLayout());
	JPanel titlePane = new JPanel(new GridLayout(1, 7));
	String titleStr[] = {"일", "월", "화", "수", "목", "금", "토"};
	JPanel datePane = new JPanel(new GridLayout(0, 7));

	Calendar now;
        PlanGui pg = new PlanGui();
        
	int year, month, date;

	public CalendarGui() {
            setDefaultCloseOperation(EXIT_ON_CLOSE);	//자원 해제 후 종료
            now = Calendar.getInstance();	//현재 날짜
            year = now.get(Calendar.YEAR);
            month = now.get(Calendar.MONTH)+1;
            date = now.get(Calendar.DATE);

            topPane.add(prevBtn);

            for(int i=year-100; i<=year+50; i++){
		yearModel.addElement(i);
            }
            yearCombo.setModel(yearModel);
            yearCombo.setSelectedItem(year);	//현재 년도 선택
            topPane.add(yearCombo);
		
            topPane.add(yearLbl);

		

            for(int i=1; i<=12; i++){

                monthModel.addElement(i);

            }

            monthCombo.setModel(monthModel);
            monthCombo.setSelectedItem(month);	//현재 월 선택
            topPane.add(monthCombo);
            topPane.add(monthLbl);

		

            topPane.add(nextBtn);

		

            topPane.setBackground(new Color(100, 200, 200));

            add(topPane, "North");

            //Center

            titlePane.setBackground(Color.white);

            for(int i=0; i<titleStr.length; i++){
		JLabel lbl = new JLabel(titleStr[i], JLabel.CENTER);

		if (i == 0) {
			lbl.setForeground(Color.red);
		} else if (i == 6) {
			lbl.setForeground(Color.blue);
		}
		titlePane.add(lbl);
            }

            centerPane.add(titlePane, "North");
            //날짜 출력
            dayPrint(year, month);
            centerPane.add(datePane, "Center");
            add(centerPane, "Center");

            setSize(600, 500);
            setVisible(true);

            prevBtn.addActionListener(this);
            nextBtn.addActionListener(this);
            yearCombo.addActionListener(this);
            monthCombo.addActionListener(this);
            
            Content.mainFrame = this;
	}

	//Overring

	public void actionPerformed(ActionEvent ae){
            Object obj = ae.getSource();

            if(obj instanceof JButton){
                
		JButton eventBtn = (JButton)obj;
		int yy = (Integer)yearCombo.getSelectedItem();
		int mm = (Integer)monthCombo.getSelectedItem();
                
		if(eventBtn.equals(prevBtn)){	//전달
                    if (mm == 1) {
			yy--; mm=12;
                    } else {
			mm--;
                    }				
		}else if(eventBtn.equals(nextBtn)){	//다음달
                    if(mm==12){
					yy++; mm=1;
				}else{
					mm++;
				}
			}
			yearCombo.setSelectedItem(yy);
			monthCombo.setSelectedItem(mm);
		}else if(obj instanceof JComboBox){	//콤보박스 이벤트 발생시
                    createDayStart();
		}
	}
	public void createDayStart(){
            datePane.setVisible(false);	//패널 숨기기
            datePane.removeAll();	//날짜 출력한 라벨 지우기
            dayPrint((Integer)yearCombo.getSelectedItem(), (Integer)monthCombo.getSelectedItem());
            datePane.setVisible(true);	//패널 재출력
	}
        
	public void dayPrint(int y, int m) {
            Calendar cal = Calendar.getInstance();
            cal.set(y, m-1, 1);	//출력할 첫날의 객체 만든다.
            int week = cal.get(Calendar.DAY_OF_WEEK);	//1일에 대한 요일	일요일 : 0
            int lastDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH);	//그 달의 마지막 날
            for(int i=1; i<week; i++){	//날짜 출력 전까지의 공백 출력
		datePane.add(new JLabel(" "));
            }
            for(int i=1; i<=lastDate; i++){
		JLabel lbl = new JLabel(String.valueOf(i), JLabel.CENTER);
		cal.set(y, m-1, i);
		int outWeek = cal.get(Calendar.DAY_OF_WEEK);
		if(outWeek==1){
			lbl.setForeground(Color.red);				
		}else if(outWeek==7){
			lbl.setForeground(Color.BLUE);
		}
                
                int d = Integer.parseInt(lbl.getText().contains("<br>일정있음") ? lbl.getText().split("<br>일정있음")[0].split("<html>")[1] : lbl.getText());
                if (Content.Plans.containsKey(Integer.parseInt(y+""+(m < 10 ? "0"+m : m)+""+(d < 10 ? "0"+d : d))) && !Content.Plans.get(Integer.parseInt(y+""+(m < 10 ? "0"+m : m)+""+(d < 10 ? "0"+d : d))).isEmpty()) {
                    lbl.setText("<html>"+i+"<br>일정있음</html>");
                } else {
                    ArrayList<Plan> temp = new ArrayList<Plan>();
                    Content.Plans.put(Integer.parseInt(y+""+(m < 10 ? "0"+m : m)+""+(d < 10 ? "0"+d : d)), temp);
                }
                lbl.addMouseListener(new MouseAdapter()  
                    {  
                        public void mouseClicked(MouseEvent e)  
                        {
                            pg.setDate(y, m, d, Content.Plans.get(Integer.parseInt(y+""+(m < 10 ? "0"+m : m)+""+(d < 10 ? "0"+d : d))));
                            pg.setVisible(true);
                        }  
                    }); 
            datePane.add(lbl);
        }
	}
}
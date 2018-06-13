package cn.edu.sdu.uims.component.date;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicArrowButton;

import cn.edu.sdu.uims.component.dialog.JDialogModel;

public class CalendarYearMonthPane extends CalendarPane{

	  public CalendarYearMonthPane(){
		  try {
		      jbInit();
		    }
		    catch (Exception e) {
		      e.printStackTrace();
		    }
	  }
	  
	  public CalendarYearMonthPane(JDialogModel dlg) {
//	    super(dlg);
	    this.dlg = dlg;
	    setUndecorated(true);
	    init();
	    this.addWindowListener(new WindowAdapter(){
	      public void windowDeactivated(WindowEvent e) {
	        dispose();
	      }
	    });
	    setBounds(0, 0, 300, 150); //设置窗口大小
	    toFront();
	  }
	  
	  public void init() {
	    mainbord = new JPanel();
	    mainbord.setLayout(new BorderLayout()); //主板BorderLayout
	    getContentPane().setLayout(new BorderLayout()); //设置ContentPane的布局
	    getContentPane().add(mainbord, BorderLayout.CENTER); //把主板加到JWindow中

	    Icon = new JLabel();
	    calendar = Calendar.getInstance();

	    //////////左右小按钮变换年月
	    JPanel todayPanel = new JPanel();//变换年月panel
	    jButtonWest = new BasicArrowButton(BasicArrowButton.WEST);
	    jButtonEast = new BasicArrowButton(BasicArrowButton.EAST);
	    todayPanel.setBackground(ArrowBackground);
	    mainbord.add(todayPanel); //把变换年月panel加到mainbord上

	    todayPanel.setLayout(new BorderLayout());
	    int year = calendar.get(Calendar.YEAR);
	    int month = calendar.get(Calendar.MONTH) + 1;
	    todayLable = new JLabel();
	    todayLable.setText("                          " + year + "年" + month + "月");
	    //Icon.setIcon(icon);
	    todayPanel.add(jButtonWest, BorderLayout.WEST);
	    todayPanel.add(todayLable, BorderLayout.CENTER);
	    todayPanel.add(jButtonEast, BorderLayout.EAST);
	    //事件监听
	    jButtonWest.addActionListener(new SetCalendarPane2()); //左移
	    jButtonEast.addActionListener(new SetCalendarPane()); //右移
	    /////////左右小按钮变换年月

	    updateView();
	    mainbord.setBorder(BorderFactory.createLineBorder(Color.black));
	   // mainbord.add(daysTable, BorderLayout.CENTER); //把daysTable加到mainbord上

	    /////////今天：
	    JPanel todayPanel2 = new JPanel();
	    mainbord.add(todayPanel2, BorderLayout.SOUTH); //把todayPanel2加到mainbord上
	    todayPanel2.setLayout(new BorderLayout());
	    int tyear = calendar.get(Calendar.YEAR);
	    int tmonth = calendar.get(Calendar.MONTH) + 1;
	    int tday = calendar.get(Calendar.DAY_OF_MONTH);
	    todayLable2 = new JLabel();
	    todayLable2.setText("今天:" + tyear + "年" + tmonth + "月" + tday + "日");

	    //Icon.setIcon(icon);
	    todayPanel2.setBackground(todayBackground);
	    todayPanel2.add(Icon, BorderLayout.WEST);
	    todayPanel2.add(todayLable2, BorderLayout.CENTER);
	    todayLable2.addMouseListener(new mouseClick());
	    /////////今天：

	    //mainbord.setBounds(getBounds());////设置mainbord的大小
	    //mainbord.addMouseMotionListener(new makeDispose());

	  }

	  public void updateView() {
	  }

	 
	  public static void main(String[] args)
	  {
		  CalendarYearMonthPane panel = new CalendarYearMonthPane();
		  panel.setSize(300, 80);
		  panel.init();
		  panel.show(true);
	  }

	}

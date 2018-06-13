package cn.edu.sdu.uims.component.date;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicArrowButton;

import cn.edu.sdu.uims.component.dialog.JDialogModel;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class DateField
    extends JPanel {
  Border border1;
  JButton jButtonSet = new BasicArrowButton(BasicArrowButton.SOUTH);

  JInputDatePane dateInputPane = null;
  public CalendarPane calendarPane = null; //注意CalendarPane是JWindow
  JDialogModel dlg;
  public DateField(JDialogModel dlg) {

    this.dlg = dlg;
    calendarPane = dlg.getCalendarPane(); //注意CalendarPane是JWindow
    dateInputPane = new JInputDatePane(this, calendarPane);
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void setDate(int nDate) {
    dateInputPane.setNewDate(nDate);
  }

  public int getDate() {
    return dateInputPane.getDate();
  }

  // 获取日期，格式“2005-10-9” 孙景乐 2005-10-9
  public String getDateString() {
    return dateInputPane.getDateString();
  }


  void jbInit() throws Exception {
    border1 = BorderFactory.createLineBorder(Color.black, 2);
    this.setBackground(Color.white);
    this.setBorder(null);
    this.setOpaque(false);
    this.setPreferredSize(new Dimension(177, 36));
    this.setLayout(null);
    jButtonSet.setBounds(new Rectangle(81, 0, 16, 16));
    jButtonSet.setToolTipText("");
    dateInputPane.setBounds(new Rectangle(0, 0, 81, 19));
    dateInputPane.init();


    this.add(dateInputPane, null);

    this.add(jButtonSet, null);

    jButtonSet.addActionListener(new SetDateByDatePanel());

  };
  public void displayCalenderPane(){
    calendarPane.setDateAttribute(this);
//    calendarPane.setBounds(0, 0, 300, 150); //设置窗口大小
//    calendarPane.setLocationRelativeTo(this.dateInputPane);
//    calendarPane.init();
//    calendarPane.dispose();
//    calendarPane.toFront();
    /*
     xinjia改正了同一个对话框中两个以上的控件时，第二个打开时不是当前时间的问题
     by 王  2005.4.2
    */
    calendarPane.modifyToCurrnetDay();
    //xinjia over
    calendarPane.show();
  }

  class SetDateByDatePanel
      implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (dateInputPane.selectMark != 0) {
        dateInputPane.selectMark = 0;
        dateInputPane.setSelect();
      }
      displayCalenderPane();
    }
  }
}

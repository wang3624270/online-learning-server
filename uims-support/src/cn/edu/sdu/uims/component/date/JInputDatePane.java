package cn.edu.sdu.uims.component.date;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class JInputDatePane
    extends JPanel {
  private Calendar calendar;
  int textLength = 10;
  int date = 19700101;
  int year, month, day;
  int selectMark = 0;
  int fontW = 8, fontH = 12;
  int selectBeg = 0, selectEnd = 0;
  char txt[] = new char[10];
  char txtI[] = new char[10];
  int txtIleng = 0;
  boolean bStartI = false;
  DateField dateField = null;
  CalendarPane calendarPane = null;

  public JInputDatePane(DateField panel, CalendarPane calendarPane) {
    this.calendarPane = calendarPane;
    dateField = panel;
    Calendar t = Calendar.getInstance();
    int nCurrentDate = t.get(t.YEAR) * 10000 + (t.get(t.MONTH) + 1)
        * 100 + t.get(t.DAY_OF_MONTH);
    this.date = nCurrentDate;
    //this.date = CommMethod.getCurrentDate();
  }

  public JInputDatePane(DateField panel, int date) {
    dateField = panel;
    this.date = date;
  }

  void init() {
    calendar = Calendar.getInstance();
    computerYearMonthday();
    setBackground(Color.white);
    //setBorder(new MatteBorder(5,5,30,30,Color.green));

    setToolTipText("");
    addKeyListener(new KeyProcess());
    addMouseListener(new MouseProcess());
    setFocusable(true);
    setBorder(new LineBorder(Color.blue));
    setSelect();
    this.addFocusListener(new FocusListener() {
      public void focusGained(FocusEvent e) {

      }

      public void focusLost(FocusEvent e) {
        selectMark = 0;
        setSelect();
      }
    });
  }

  public void paintComponent(Graphics g) {
    txt[0] = (char) ('0' + year / 1000);
    txt[1] = (char) ('0' + year % 1000 / 100);
    txt[2] = (char) ('0' + year % 100 / 10);
    txt[3] = (char) ('0' + year % 10);
    txt[4] = '-';
    txt[5] = (char) ('0' + month / 10);
    txt[6] = (char) ('0' + month % 10);
    txt[7] = '-';
    txt[8] = (char) ('0' + day / 10);
    txt[9] = (char) ('0' + day % 10);
    g.setColor(Color.white);
    g.fillRect(0, 0, fontW * 10, fontH + 6);
    g.setColor(Color.black);
    for (int i = 0; i < 10; i++) {
      g.drawChars(txt, i, 1, fontW * i, fontH);
    }
    if (selectEnd > selectBeg) {
      g.setXORMode(new Color(255, 255, 0));
      g.fillRect(fontW * selectBeg, 0, fontW * (selectEnd - selectBeg),
                 fontH + 2);
      g.setPaintMode();
    }
  }

  private void computerYearMonthday() {
    year = date / 10000;
    month = date % 10000 / 100;
    day = date % 100;
  }

  public int getDate() {
    return date;
  }

  /**
   * 获取“2005-10-9”格式的日期字符串
   * @return String
   *
   * 孙景乐修改 2005-10-9
   */
  public String getDateString() {
    String monthStr;
    if (month < 10)
      monthStr = "0" + month;
    else
      monthStr = month + "";
    String dayStr;
    if (day < 10)
      dayStr = "0" + day;
    else
      dayStr = "" + day;

    return year + "-" + monthStr + "-" + dayStr;
  }


  public void setNewDate(int date) {
    this.date = date;
    computerYearMonthday();
    repaint();
  }

  void select(int beg, int end) {
    selectBeg = beg;
    selectEnd = end;
  }

  public void setSelect() {
    switch (selectMark) {
      case 0:
        select(0, 0);
        break;
      case 1:
        select(0, 4);
        break;
      case 2:
        select(5, 7);
        break;
      case 3:
        select(8, 10);
        break;
    }
    repaint();
  }

  private void modifyDate(int step) {
    if (selectMark == 0) {
      return;
    }
    int max;
    switch (selectMark) {
      case 1:
        year += step;
        break;
      case 2:
        month += step;
        if (month == 0) {
          month = 12;
        }
        if (month == 13) {
          month = 1;
        }
        break;
      case 3:
        max = getMaxDayOfCurrentMonth();
        day += step;
        if (day == 0) {
          day = max;
        }
        if (day > max) {
          day = 1;
        }
    }
    this.date = year * 10000 + month * 100 + day;

    this.calendarPane.calendar.set(Calendar.YEAR, year);
    this.calendarPane.calendar.set(Calendar.MONTH, month - 1);
    this.calendarPane.calendar.set(Calendar.DAY_OF_MONTH, day);
    this.calendarPane.todayLable.setText("                           " + year +
                                         "年" + month + "月");

    this.calendarPane.updateView();

    repaint();
  }

  //本函数当通过键盘输入后，更新pane中的显示
  void setDateToPane(){
    this.date = year * 10000 + month * 100 + day;
    this.calendarPane.calendar.set(Calendar.YEAR, year);
    this.calendarPane.calendar.set(Calendar.MONTH, month - 1);
    this.calendarPane.calendar.set(Calendar.DAY_OF_MONTH, day);
    this.calendarPane.todayLable.setText("                           " + year +
                                         "年" + month + "月");

    this.calendarPane.updateView();

    repaint();
  }

  int getStringToInt(char[] sChar, int nBegin, int nLength) {
    int nRet = 0;
    int i;
    String str;
    if (nLength > 0) {
      str = "";
      for (i = nBegin; i < (nBegin + nLength); i++) {
        str += sChar[i];
      }
      nRet = Integer.parseInt(str);
    }
    return nRet;
  }

  int getMaxDayOfCurrentMonth() {
    switch (month) {
      case 1:
      case 3:
      case 5:
      case 7:
      case 8:
      case 10:
      case 12:
        return 31;
      case 4:
      case 6:
      case 9:
      case 11:
        return 30;
      case 2:
        if ( (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {

          return 29;

        }
        else {

          return 28;

        }

    }
    return 30;
  }

  class DateFieldButtonProcess
      implements ActionListener {
    public void actionPerformed(ActionEvent e) {
    }
  }

  public void reBackRightOperation() {
    if (!bStartI) {
      return;
    }
    bStartI = false;

  }

  class KeyProcess
      implements KeyListener {
    public void keyPressed(KeyEvent e) {
      int code = e.getKeyCode();
      if (dateField.calendarPane.isVisible() || selectMark == 0) {
        return;
      }
      if( e.getKeyLocation() == KeyEvent.KEY_LOCATION_NUMPAD && code >= 96 && code <=105){
        code = code - 96 + '0';
      }
      if (code >= '0' && code <= '9') {
        if (!bStartI) {
          bStartI = true;
          txtIleng = 0;
        }
        //getInputContext();
        //repaint();
        int max;
        if (selectMark == 1) {
          max = 4;
        }
        else {
          max = 2;
        }
        if (txtIleng < max) { //队列
          txtI[txtIleng] = (char) code;
          txtIleng++;
        }
        else {
          int i;
          for (i = 0; i < max - 1; i++) {
            txtI[i] = txtI[i + 1];
          }
          txtI[max - 1] = (char) code;
        }
        int ntmp;
        ntmp = getStringToInt(txtI, 0, txtIleng);
        //如果输入的位数小于所限制的长度，不坐判断直接赋值
        if (txtIleng < max) {
          if (selectMark == 1) {
            year = ntmp;
          }
          else if (selectMark == 2) {
            month = ntmp;
          }
          else {
            day = ntmp;
          }
        }
        else {
          if (selectMark == 1) {
            if (ntmp >= 1900 && ntmp <= 3000) {
              year = ntmp;
            }
            else {
              year = getStringToInt(txtI, 1, 1);
              int i;
              for (i = 0; i < max - 1; i++) {
                txtI[i] = txtI[i + 1];
              }
              txtIleng--;
            }
          }
          else if (selectMark == 2) {
            if (ntmp >= 1 && ntmp <= 12) {
              month = ntmp;
            }
            else {
              month = getStringToInt(txtI, 1, 1);
              int i;
              for (i = 0; i < max - 1; i++) {
                txtI[i] = txtI[i + 1];
              }
              txtIleng--;
            }
          }
          else {
            if (ntmp >= 0 && ntmp <= getMaxDayOfCurrentMonth()) {
              day = ntmp;
            }
            else {
              day = getStringToInt(txtI, 1, 1);
              int i;
              for (i = 0; i < max - 1; i++) {
                txtI[i] = txtI[i + 1];
              }
              txtIleng--;
            }
          }
        }
        setDateToPane();
        repaint();
      }
      if (code == 8) {
        if (bStartI && txtIleng > 1) {
          txtIleng--;
          repaint();
        }
      }
      else if (code >= 37 && code <= 40) {
        reBackRightOperation();
        switch (code) {
          case 37: //left
            selectMark--;
            if (selectMark == 0) {
              selectMark = 3;
            }
            setSelect();
            break;
          case 38: //up
            modifyDate(1);
            break;
          case 39: // right
            if (selectMark == 0) {
              return;
            }
            selectMark++;
            if (selectMark == 4) {
              selectMark = 1;
            }
            setSelect();
            break;
          case 40: //down
            modifyDate( -1);
            break;
        }
      }
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {

    }
  }

  class MouseProcess
      extends MouseAdapter {
    public void mouseClicked(MouseEvent e) {
      if (dateField.calendarPane.isVisible()) {
        return;
      }

      requestFocus();
     /* if (!requestFocusInWindow()) {
        CommMethod.MessageBoxError("!requestFocusInWindow");
      }*/
      int x = e.getX();
      int y = e.getY();
      int ind = x / fontW;
      if (ind >= 0 && ind < 4) {
        selectMark = 1;
      }
      else if (ind >= 5 && ind < 7) {
        selectMark = 2;
      }
      else if (ind >= 8 && ind < 10) {
        selectMark = 3;
      }
      else {
        selectMark = 0;
      }
      setSelect();
    }
  }
}

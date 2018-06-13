package cn.edu.sdu.uims.component.date;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import cn.edu.sdu.uims.component.dialog.JDialogModel;

public class CalendarPane
//    extends JFrame {
    extends JDialog {

  public static final String WEEK_SUN = "星期日";
  public static final String WEEK_MON = "星期一";
  public static final String WEEK_TUE = "星期二";
  public static final String WEEK_WED = "星期三";
  public static final String WEEK_THU = "星期四";
  public static final String WEEK_FRI = "星期五";
  public static final String WEEK_SAT = "星期六";

  public static final Color background = Color.white;
  public static final Color foreground = Color.black;
  public static final Color headerBackground = new Color(216, 216, 0);
  public static final Color ArrowBackground = new Color(248, 248, 208);
  public static final Color todayBackground = new Color(248, 248, 208);

  public static final Color headerForeground = Color.darkGray;
  public static final Color selectedBackground = Color.blue;
  public static final Color selectedForeground = Color.white;

  protected JPanel mainbord = null; //把组件都放到主板上
  //Icon icon = new ImageIcon("img//icon1.gif");
  //Icon icon2 = new ImageIcon("img//icon2.gif");
  protected JButton jButtonWest;
  protected JButton jButtonEast;

  protected JLabel Icon;

  protected JLabel todayLable;
  protected JLabel todayLable2;

  protected JTable daysTable;
  protected AbstractTableModel daysModel;
  protected Calendar calendar;
  protected JDialogModel dlg;
  protected DateField dateField = null;

  public CalendarPane(JDialogModel dlg) {
//    super(dlg);
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
  public void setDateAttribute(DateField dateField){
    this.dateField = dateField;
    Point pd, p;
    int dwidth, dheight, cwidth, cheight,xo,yo;
    pd = dlg.getLocationOnScreen();
    dwidth = dlg.getWidth();
    dheight = dlg.getHeight();//当前对话框的宽和高
    cwidth = this.getWidth();
    cheight = this.getHeight();//日历panel的宽和高
    Rectangle r = dateField.dateInputPane.getBounds();//日历数字框的大小
    p = dateField.dateInputPane.getLocationOnScreen();
    xo = p.x+ (r.width- cwidth)/2;//使日历数字框在日历panel的中间
    if(xo + cwidth > pd.x + dwidth)
      xo = pd.x + dwidth - cwidth;//如果日历框超出了当前对话框的范围，则使之与对话框右侧对齐，不超出对话框范围
    if(xo < pd.x)
      xo = pd.x;//如上，左侧
    yo = p.y + r.height;
    if(yo + cheight > pd.y + dheight)
      yo = p.y - r.height - cheight;//如上，下侧
    this.setBounds(xo,yo,cwidth,cheight);
  }
  /*
     xinjia改正了同一个对话框中两个以上的控件时，第二个打开时不是当前时间的问题
   by 王  2005.4.2
    */

  void modifyToCurrnetDay(){
    int today = dateField.dateInputPane.getDate();
    int year = today/10000;
    int month = today%10000/100;
    int day = today%100;
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.MONTH, month - 1);
    calendar.set(Calendar.DAY_OF_MONTH, day);
    todayLable.setText("                           " + year +
                       "年" + month + "月");
    updateView();

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
    mainbord.add(todayPanel, BorderLayout.NORTH); //把变换年月panel加到mainbord上

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

    daysModel = new AbstractTableModel() {
      public int getRowCount() {
        return 7;
      }

      public int getColumnCount() {
        return 7;
      }

      public Object getValueAt(int row, int column) {
        if (row == 0) {
          return getHeader(column);
        }
        row--;
        Calendar calendar = (Calendar) CalendarPane.this.calendar.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int dayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int moreDayCount = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int index = row * 7 + column;
        int dayIndex = index - moreDayCount + 1;
        if (index < moreDayCount || dayIndex > dayCount) {
          return null;
        }
        else {
          return new Integer(dayIndex);
        }
      }
    };

    daysTable = new CalendarTable(daysModel, calendar);
    daysTable.setCellSelectionEnabled(true);
    daysTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    daysTable.setDefaultRenderer(daysTable.getColumnClass(0),
                                 new TableCellRenderer() {
      public Component getTableCellRendererComponent(JTable table, Object value,
          boolean isSelected,
          boolean hasFocus, int row, int column) {

        String text = (value == null) ? "" : value.toString();
        JLabel cell = new JLabel(text);
        cell.setOpaque(true);
        if (row == 0) {
          cell.setForeground(headerForeground);
          cell.setBackground(headerBackground);
        }
        else {
          if (isSelected) {
            cell.setForeground(selectedForeground);
            //cell.setIcon(icon2);
            cell.setBackground(selectedBackground);
          }
          else {
            cell.setForeground(foreground);
            cell.setBackground(background);
          }
        }

        return cell;
      }
    });
    updateView();
    mainbord.setBorder(BorderFactory.createLineBorder(Color.black));
    mainbord.add(daysTable, BorderLayout.CENTER); //把daysTable加到mainbord上

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

  class makeDispose
      extends MouseMotionAdapter {
    public void mouseMoved(MouseEvent e) {
      if (e.getPoint().getX() > 199 || e.getPoint().getX() <= 0 ||
          e.getPoint().getY() <= 0 || e.getPoint().getY() >= 149) {
        dispose();
      }

    }

  }

  ///返回给JInput今天的值
  class mouseClick
      extends MouseAdapter {
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH) + 1;
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    int nCurrentDate = calendar.get(calendar.YEAR) * 10000 +
        (calendar.get(calendar.MONTH) + 1)
        * 100 + calendar.get(Calendar.DAY_OF_MONTH);

    public void mouseClicked(MouseEvent e) {

      calendar.set(Calendar.YEAR, year);

      calendar.set(Calendar.MONTH, month - 1);

      calendar.set(Calendar.DAY_OF_MONTH, day);

      todayLable.setText("                           " + year +
                         "年" + month + "月");

      updateView();

      dateField.dateInputPane.setNewDate(nCurrentDate);
      dateField.calendarPane.setVisible(false);
      //dateField.dlg.toFront();
      //dateField.dlg.setEnabled(true);
    }
  }

  public static String getHeader(int index) {
    switch (index) {
      case 0:
        return WEEK_SUN;
      case 1:
        return WEEK_MON;
      case 2:
        return WEEK_TUE;
      case 3:
        return WEEK_WED;
      case 4:
        return WEEK_THU;
      case 5:
        return WEEK_FRI;
      case 6:
        return WEEK_SAT;
      default:
        return null;
    }
  }

  public void updateView() {
    daysModel.fireTableDataChanged();
    daysTable.setRowSelectionInterval(calendar.get(Calendar.WEEK_OF_MONTH),
                                      calendar.get(Calendar.WEEK_OF_MONTH));
    daysTable.setColumnSelectionInterval(calendar.get(Calendar.DAY_OF_WEEK) - 1,
                                         calendar.get(Calendar.DAY_OF_WEEK) - 1);
  }

  public class CalendarTable
      extends JTable {

    private Calendar calendar;

    public CalendarTable(TableModel model, Calendar calendar) {
      super(model);
      this.calendar = calendar;
    }

    public void changeSelection(int row, int column, boolean toggle,
                                boolean extend) {
      super.changeSelection(row, column, toggle, extend);
      if (row == 0) {
        return;
      }
      Object obj = getValueAt(row, column);
      if (obj != null) {
        calendar.set(Calendar.DAY_OF_MONTH, ( (Integer) obj).intValue());
        int nCurrentDate = calendar.get(calendar.YEAR) * 10000 +
            (calendar.get(calendar.MONTH) + 1)
            * 100 + calendar.get(calendar.DAY_OF_MONTH);
        dateField.dateInputPane.setNewDate(nCurrentDate);
      }
      if (obj == null) {

        return;
      }
      dateField.calendarPane.setVisible(false);
      //dateField.dlg.toFront();
      //dateField.dlg.setEnabled(true);
    }
  }

/////////////////////////////////箭头向右////////////////////////////////////////
  class SetCalendarPane
      implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      int year = calendar.get(Calendar.YEAR);
      int month = calendar.get(Calendar.MONTH) + 1;
      int day = calendar.get(Calendar.DAY_OF_MONTH);
      /*修改当本月日期多于下月日期并且刚好在本月多出的日期上点下个月时,产生的bug
          by 王  2005.4.2
          */
      calendar.set(Calendar.DAY_OF_MONTH, 1);//必须有,否则下面的月份设置可能会出错,在上面的那种情况下calendar.get(Calendar.MONTH)会连续+2
      calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
      int maxNextMonthDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
      calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
      calendar.set(Calendar.DAY_OF_MONTH, day);
      //over
      if(day <= maxNextMonthDays){
        if (month < 12) {
          ++month;
          calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
          todayLable.setText("                           " + year +
                             "年" + month + "月");
        }
        else {
          calendar.set(Calendar.DAY_OF_MONTH, 1);
          calendar.set(Calendar.MONTH, 0);
          calendar.set(Calendar.YEAR, ++year);
          todayLable.setText("                           " + year +
                             "年" + 1 + "月");
          int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
          calendar.set(Calendar.DAY_OF_MONTH, day > maxDay ? maxDay : day);
          updateView();
        }
      }
      /*修改当本月日期多于下月日期并且刚好在本月多出的日期上点下个月时,产生的bug
          by 王  2005.4.2
          */
      else{
        if (month < 12) {
          ++month;
          calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
          //修改当本月日期多于下月日期并且刚好在本月多出的日期上点下个月时,产生的bug
          calendar.set(Calendar.DATE, maxNextMonthDays);
          todayLable.setText("                           " + year +
                             "年" + month + "月");
        }
        else {
          calendar.set(Calendar.DAY_OF_MONTH, 1);
          calendar.set(Calendar.MONTH, 0);
          calendar.set(Calendar.YEAR, ++year);
          todayLable.setText("                           " + year +
                             "年" + 1 + "月");
          int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
          calendar.set(Calendar.DAY_OF_MONTH, day > maxDay ? maxDay : day);
          updateView();
        }

      }
      //over
      updateView();

    }
  }

  //////////////////////////////////箭头向右////////////////////////

  ////////////////////////////////////箭头向左///////////////////////////
  class SetCalendarPane2
      implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      int year = calendar.get(Calendar.YEAR);
      int month = calendar.get(Calendar.MONTH);
      int day = calendar.get(Calendar.DAY_OF_MONTH);
      /*修改当本月日期多于上月日期并且刚好在本月多出的日期上点上个月时,产生的bug
          by 王  2005.4.2
       */
      calendar.set(Calendar.DAY_OF_MONTH, 1); //必须有,否则下面的月份设置可能会出错,在上面的那种情况下calendar.get(Calendar.MONTH)会连续-2
      calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
      int maxFormerMonthDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
      calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
      calendar.set(Calendar.DAY_OF_MONTH, day);
      //over
      if(day <= maxFormerMonthDays){
        if (month > 0) {
          --month;
          calendar.set(Calendar.MONTH, month);
          todayLable.setText("                           " + year +
                             "年" + (month + 1) + "月");
        }
        else {
          calendar.set(Calendar.DAY_OF_MONTH, 1);
          calendar.set(Calendar.MONTH, 11);
          calendar.set(Calendar.YEAR, --year);
          todayLable.setText("                           " + year +
                             "年" + 12 + "月");
          int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
          calendar.set(Calendar.DAY_OF_MONTH, day > maxDay ? maxDay : day);

        }
      }
      /*修改当本月日期多于上月日期并且刚好在本月多出的日期上点上个月时,产生的bug
          by 王  2005.4.2
       */
      else{
        if (month > 0) {
          --month;
          calendar.set(Calendar.MONTH, month);
          //修改当本月日期多于上月日期并且刚好在本月多出的日期上点上个月时,产生的bug
          calendar.set(Calendar.DATE, maxFormerMonthDays);
          todayLable.setText("                           " + year +
                             "年" + (month + 1) + "月");
        }
        else {
          calendar.set(Calendar.DAY_OF_MONTH, 1);
          calendar.set(Calendar.MONTH, 11);
          calendar.set(Calendar.YEAR, --year);
          todayLable.setText("                           " + year +
                             "年" + 12 + "月");
          int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
          calendar.set(Calendar.DAY_OF_MONTH, day > maxDay ? maxDay : day);

        }
      }
      //over
      updateView();

    }
  }

  public CalendarPane() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void jbInit() throws Exception {
    this.getContentPane().setLayout(null);
  }

  ///////////////////////////////////////////箭头向左/////////////////////////

}

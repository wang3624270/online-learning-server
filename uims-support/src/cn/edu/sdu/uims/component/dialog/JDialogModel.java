package cn.edu.sdu.uims.component.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JPanel;

import cn.edu.sdu.uims.component.date.CalendarPane;



public class JDialogModel
    extends JDialog {
 // public JMyFrame frame;
  protected CalendarPane calendarPane = null;
  protected ArrayList dlgList = null;
  /**
   * 添加两个全局变量
   * 孙景乐 2005/10/8
   */
 // protected ClientApp clientApp = null; //应用程序对象
 // protected BusinessProcessI bpo = null;//业务处理对象
  protected Font dlgDefaultFont = new Font("Dialog", Font.PLAIN, 12);

  BorderLayout borderLayout1 = new BorderLayout();
  protected JPanel panel1 = new JPanel();

  public JDialogModel(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
    //  this.frame = (JMyFrame) frame;
    //  dlgList = this.frame.getDialogList();
    //  bpo = ((ClientFrame)this.frame).getBusinessProcessObj();
     // clientApp = ((ClientFrame)this.frame).getClientApp();
      this.addWindowListener(new JDialogModel_this_windowAdapter(this));
      jbInit();
      pack();
//      this.setEnabled(false);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  public JDialogModel(JDialog frame, String title, boolean modal) {
            super(frame, title, modal);
            try {
            //  this.frame = (JMyFrame) frame;
            //  dlgList = this.frame.getDialogList();
            //  bpo = ((ClientFrame)this.frame).getBusinessProcessObj();
             // clientApp = ((ClientFrame)this.frame).getClientApp();
              this.addWindowListener(new JDialogModel_this_windowAdapter(this));
              jbInit();
              pack();
//	      this.setEnabled(false);
            }
            catch (Exception ex) {
              ex.printStackTrace();
            }
          }
  public JDialogModel() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void setCenter() {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension dialogSize = this.getSize();
    this.setLocation( (screenSize.width - dialogSize.width) / 2,
                     (screenSize.height - dialogSize.height) / 2);
  }

  public void dispose(JDialog d) {
    super.dispose();
  }

  public void removeCalendarPane() {
    if (calendarPane != null) {
      if (calendarPane.isVisible()) {
        calendarPane.setVisible(false);
      }
    }
  }
  //add liuhaibo 2005-08-16
  //for get the mouse event from Jtable
  public void mouseEventFromJTable(){
    //各子类根据需要重载本函数
    System.out.println("mouseEventFromJTable");
  }

  void this_windowClosing(WindowEvent e) {
    removeCalendarPane();
    dispose(this);
  }

  private void jbInit() throws Exception {

    this.setResizable(false);
  }

  public CalendarPane getCalendarPane() {
    if (calendarPane == null) {
      calendarPane = new CalendarPane(this);
    }
    return calendarPane;
  }

  class JDialogModel_this_windowAdapter
      extends java.awt.event.WindowAdapter {
    JDialogModel adaptee;

    JDialogModel_this_windowAdapter(JDialogModel adaptee) {
      this.adaptee = adaptee;
    }

    public void windowClosing(WindowEvent e) {
      adaptee.this_windowClosing(e);
    }

    public void windowActivated(WindowEvent e) {
      //frame.removeMenudisplayed();
    }
  }
}

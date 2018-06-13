package cn.edu.sdu.uims.frame;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * 创建闪动的托盘图像
 * 
 * @author 黄根华
 */
public class TestTray extends JFrame implements Runnable,ActionListener {

	private SystemTray sysTray;// 当前操作系统的托盘对象

	private TrayIcon trayIcon;// 当前对象的托盘

	private int num = 10;

	Image image = null;

	public TestTray() {
		this.createTrayIcon();// 创建托盘对象
		init();
	}

	/**
	 * 初始化窗体的方法
	 */
	/**
  * 
  */
	public void init() {
		this.setTitle("闪动托盘");
		this.setSize(400, 400);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		// 添加窗口事件,将托盘添加到操作系统的托盘
		this.addWindowListener(new WindowAdapter() {

			public void windowIconified(WindowEvent e) {
				addTrayIcon();
			}
		});

		this.setVisible(true);
	}

	/**
	 * 添加托盘的方法
	 */
	public void addTrayIcon() {
		try {
			sysTray.add(trayIcon);// 将托盘添加到操作系统的托盘
			trayIcon.displayMessage("信息", "有新的会议申请！",
					TrayIcon.MessageType.INFO);
			setVisible(false);// 使得当前的窗口隐藏
			new Thread(this).start();
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 创建系统托盘的对象 步骤: 1,获得当前操作系统的托盘对象 2,创建弹出菜单popupMenu 3,创建托盘图标icon
	 * 4,创建系统的托盘对象trayIcon
	 */
	public void createTrayIcon() {
		sysTray = SystemTray.getSystemTray();// 获得当前操作系统的托盘对象
//		icon = new ImageIcon("1.gif");// 托盘图标
		image = Toolkit.getDefaultToolkit().getImage(
		"d:/123.jpg");
		PopupMenu popupMenu = new PopupMenu();// 弹出菜单
		MenuItem mi = new MenuItem("弹出");
		MenuItem exit = new MenuItem("关闭");
		popupMenu.add(mi);
		popupMenu.add(exit);
		// 为弹出菜单项添加事件
		mi.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				setVisible(true);
				sysTray.remove(trayIcon);
			}
		});
		exit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		trayIcon = new TrayIcon(image, "闪动托盘", popupMenu);
		trayIcon.addActionListener(this);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestTray testTray = new TestTray();
	}

	/*
	 * 线程控制闪动 (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		while (num >= 0) {
			this.trayIcon.setImage(new ImageIcon("").getImage());
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.trayIcon.setImage(image);
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
//			num--;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// 重写actionPerformed方法
			if (e.getSource() == trayIcon) {// 双击托盘图标执行的代码
				this.show(true);
		}
	}

}

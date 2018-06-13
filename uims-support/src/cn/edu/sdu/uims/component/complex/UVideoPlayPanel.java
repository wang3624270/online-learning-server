package cn.edu.sdu.uims.component.complex;

import java.awt.AWTEvent;
import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;

import javax.swing.Timer;
import javax.swing.ToolTipManager;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.MetalSliderUI;

import cn.pilisdk.videoplayer.COM.Util;
import cn.pilisdk.videoplayer.COM.videoPlayer;

public class UVideoPlayPanel extends UComplexPanel {

	public static String file;
	public static Object data[];
	videoPlayer player;
	int imgW, imgH;
	JPanel videoPanel;
	JPanel jp, controlsPane, controlsButton;
	JButton checkButton, start, restart, pause, stop, forward, backward;
	JSlider jsl, volAdj;
	JLabel jl;
	boolean jslFlag = false;
	public boolean isReleased = false;
	public int shallSec;
	Integer type;
	public Object getData() {
		return data;
	}
    
	public void setData(Object obj) {
		data = (Object[]) obj;
		if(jp!=null) {
			remove(jp);
		}
		new initPlayerThread(data[0].toString()).start();
	}
	

	@Override
	public void initContents() {
		// TODO Auto-generated method stub
		setLayout(new BorderLayout());
	}
	
	private class startE implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			player.setStartPlay();
			restart.setVisible(true);
			pause.setEnabled(true);
			forward.setEnabled(true);
			backward.setEnabled(true);
			jsl.setEnabled(true);
			stop.setEnabled(true);
		}
	}

	private class restartE implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Integer type = Integer.valueOf(data[1].toString());
			if (forward.isEnabled() || backward.isEnabled() || jsl.isEnabled() || pause.isEnabled()) {
				forward.setEnabled(false);
				backward.setEnabled(false);
				jsl.setEnabled(false);
				pause.setEnabled(false);
			} else {
				start.setEnabled(true);
			}

			if (!player.isStop()) {
				player.setStopPlay();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			jp.remove(player);
			player = new videoPlayer(data[0].toString(),type,400,600);
			jp.add(player, BorderLayout.BEFORE_FIRST_LINE);
			jp.updateUI();//
			displayTime(0);
			jsl.setMaximum((int) ((double) player.getVideoTotalLength() / 1000000.0));
			jsl.setValue(0);
			player.updateUI();// 加载新的视频后更新封面
			jp.remove(controlsPane);
			jp.remove(controlsButton);
			jp.add(controlsPane, BorderLayout.EAST);
			jp.add(controlsButton, BorderLayout.AFTER_LINE_ENDS);
		}
	}

	private class stopE implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			player.setStopPlay();
			jsl.setEnabled(false);
			start.setEnabled(false);
			pause.setEnabled(false);
			backward.setEnabled(false);
			forward.setEnabled(false);
			/**
			 * Ref:https://stackoverflow.com/questions/45826802/convert-an-imageicon-to-a-base64-string-and-back-to-an-imageicon-without-saving
			 * 参考上面的链接得到一个黑色的图片，用于停止时清除画面。
			 */
			BufferedImage image = new BufferedImage(player.getIcon().getIconWidth(), player.getIcon().getIconHeight(),
					BufferedImage.TYPE_INT_RGB);// 这个的话里面的值全0所以是黑色
			player.setIcon(new ImageIcon(image));
		}
	}

	private class pauseE implements ActionListener {

		private boolean toggle;

		public pauseE() {
			toggle = true;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (toggle) {
				player.setPausePlay();
				pause.setText("继续");
				jsl.setEnabled(false);
				stop.setEnabled(false);
				restart.setEnabled(false);
				forward.setEnabled(false);
				backward.setEnabled(false);
			} else {
				player.setResumePlay();
				pause.setText("暂停");
				jsl.setEnabled(true);
				stop.setEnabled(true);
				restart.setEnabled(true);
				forward.setEnabled(true);
				backward.setEnabled(true);
			}
			toggle = !toggle;
		}
	}

	private class jslE implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			/**
			 * 只取进度条拖动或点击后的最后一次值 设置进度，不然容易卡顿。
			 */
			if (jslFlag) {
				// setValue事件
				jslFlag = false;
				return;
			}
			synchronized (UVideoPlayPanel.this) {
				if (isReleased) {
					isReleased = false;
					player.setPlayTime(shallSec);
				} else {
					return;
				}
			}
		}
	}

	private class forwardE implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			long l = player.getCurPlayTime();
			l += 12000000;// 一次快进12s
			player.setPlayTime((int) ((double) l / 1000000.0));
		}
	}

	private class backE implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			long l = player.getCurPlayTime();
			l -= 12000000;// 一次快退12s
			if (l < 0) {
				l = 0;
			} else if (l >= (player.getVideoTotalLength() - 12000000)) {
				l = 0;
			}
			player.setPlayTime((int) ((double) l / 1000000.0));
		}
	}

	private class jslE2 extends MouseAdapter {
		public int sec;

		public jslE2() {
			// 设置弹出ToolTip延时时间。
			ToolTipManager.sharedInstance().setInitialDelay(0);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			synchronized (UVideoPlayPanel.this) {
				isReleased = true;
				shallSec = sec;// 这是应该设置的秒数。
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			JSlider jsl = (JSlider) e.getSource();
			// System.out.println("当前秒数是"+jsl.getMousePosition().getX()*(double)jsl.getMaximum()/630.0);
			double mpostion = jsl.getMousePosition().getX();
			double vtime = jsl.getMaximum();
			Dimension size = jsl.getPreferredSize();
			sec = (int) (mpostion * vtime / size.getWidth());
			jsl.setToolTipText(Util.getTimeString((long) ((double) sec * 1000000.0)));
		}
	}

	private class volE implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			int i = ((JSlider) e.getSource()).getValue();
			float j = (float) i / 100.0f;
			player.setVol(j);
		}
	}

	public void displayTime(long value) {
		jl.setText(Util.getTimeString(value) + " / " + player.getTotalString());
	}

	
	class initPlayerThread extends Thread{
		String file;
		public initPlayerThread(String file) {
			this.file = file;
		}
		@Override
		public void run(){
			type=Integer.valueOf(data[1].toString());
			jp = new JPanel();
			jp.setBounds(100, 100, 1000, 800);
			player = new videoPlayer(data[0].toString(),type,400,600);

			jp.add(player, BorderLayout.WEST);

			controlsPane = new JPanel();
			controlsButton = new JPanel();
			start = new JButton("开始");
			start.addActionListener(new startE());
			controlsButton.add(start);

			restart = new JButton("重播");
			restart.addActionListener(new restartE());
			controlsButton.add(restart);
			restart.setVisible(false);

			pause = new JButton("暂停");
			pause.addActionListener(new pauseE());
			controlsButton.add(pause);
			pause.setEnabled(false);

			stop = new JButton("停止");
			stop.addActionListener(new stopE());
			controlsButton.add(stop);
			stop.setEnabled(false);

			forward = new JButton("快进");
			forward.addActionListener(new forwardE());
			controlsButton.add(forward);
			forward.setEnabled(false);

			backward = new JButton("快退");
			backward.addActionListener(new backE());
			controlsButton.add(backward);
			backward.setEnabled(false);

			JLabel label1 = new JLabel("进度:");
			controlsPane.add(label1);
			jsl = new JSlider(0, (int) ((double) player.getVideoTotalLength() / 1000000.0), 0);
			jsl.addChangeListener(new jslE());
			jsl.setEnabled(false);
			jslE2 je2 = new jslE2();
			jsl.addMouseListener(je2);
			jsl.addMouseMotionListener(je2);
			jsl.setUI(new MetalSliderUI() {
				@Override
				protected void scrollDueToClickInTrack(int direction) {
					int value = slider.getValue();
					if (slider.getOrientation() == JSlider.HORIZONTAL) {
						int x = slider.getMousePosition().x;
						value = this.valueForXPosition(x);
					} else {
						int y = slider.getMousePosition().y;
						value = this.valueForYPosition(y);
					}
					slider.setValue(value);
				}
			});
			controlsPane.add(jsl);
			jl = new JLabel();
			displayTime(0);
			new Timer(100, new ActionListener() {
				int oldValue, newValue;
				long msec;

				@Override
				public void actionPerformed(ActionEvent e) {
					msec = player.getCurPlayTime();
					newValue = (int) ((double) msec / 1000000.0);
					if (newValue != oldValue) {
						oldValue = newValue;
						displayTime(msec);
						jslFlag = true;
						jsl.setValue(newValue);
					}
				}
			}).start();

			controlsPane.add(jl);
			JLabel label2 = new JLabel("音量:");
			controlsPane.add(label2);
			volAdj = new JSlider(0, 100, 40);
			volAdj.setPreferredSize(new Dimension(100, 20));
			volAdj.setUI(new MetalSliderUI() {
				@Override
				protected void scrollDueToClickInTrack(int direction) {
					int value = slider.getValue();
					if (slider.getOrientation() == JSlider.HORIZONTAL) {
						value = this.valueForXPosition(slider.getMousePosition().x);
					} else {
						value = this.valueForYPosition(slider.getMousePosition().y);
					}
					slider.setValue(value);
				}
			});
			volAdj.addChangeListener(new volE());
			controlsPane.add(volAdj);

			jp.add(controlsPane, BorderLayout.EAST);
			jp.add(controlsButton, BorderLayout.AFTER_LINE_ENDS);

			add(jp, BorderLayout.CENTER);

		}
	}

}
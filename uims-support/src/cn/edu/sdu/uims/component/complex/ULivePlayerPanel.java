package cn.edu.sdu.uims.component.complex;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import javax.swing.JPanel;
import javax.swing.JSlider;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.MetalSliderUI;

import cn.pilisdk.videoplayer.COM.videoPlayer;

public class ULivePlayerPanel extends UComplexPanel {
	
	public static String file ;
	videoPlayer player;
	JPanel videoPanel;
	JPanel jp, controlsPane, controlsButton;
	JButton  start, stop;
	JSlider  volAdj;
	Integer type=0;
	public Object getData() {
		return file;
	}
    
	public void setData(Object obj) {
		file = (String) obj;
		new initPlayerThread(file).start();
	}
	

	@Override
	public void initContents() {
		// TODO Auto-generated method stub
		setLayout(new BorderLayout());
	}
	
	private class startE implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(type==1) {
				if (!player.isStop()) {
					player.setStopPlay();
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				jp.remove(player);
				player = new videoPlayer(file,1,400,600);
				jp.add(player, BorderLayout.BEFORE_FIRST_LINE);
				jp.updateUI();//
				player.updateUI();// 加载新的视频后更新封面
				jp.remove(controlsPane);
				jp.remove(controlsButton);
				jp.add(controlsPane, BorderLayout.EAST);
				jp.add(controlsButton, BorderLayout.AFTER_LINE_ENDS);
				player.setStartPlay();
				stop.setEnabled(true);
				start.setEnabled(false);
			}else {
				player.setStartPlay();
				stop.setEnabled(true);
				start.setEnabled(false);
			}			
		}
	}

	
	private class stopE implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			player.setStopPlay();		
			start.setEnabled(true);	
			stop.setEnabled(false);
			type=1;
			/**
			 * Ref:https://stackoverflow.com/questions/45826802/convert-an-imageicon-to-a-base64-string-and-back-to-an-imageicon-without-saving
			 * 参考上面的链接得到一个黑色的图片，用于停止时清除画面。
			 */
			BufferedImage image = new BufferedImage(player.getIcon().getIconWidth(), player.getIcon().getIconHeight(),
					BufferedImage.TYPE_INT_RGB);// 这个的话里面的值全0所以是黑色
			player.setIcon(new ImageIcon(image));
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

	
	class initPlayerThread extends Thread{
		String file;
		public initPlayerThread(String file) {
			this.file = file;
		}
		@Override
		public void run(){
//			type=Integer.valueOf(data[1].toString());
			jp = new JPanel();
			jp.setBounds(100, 100, 1000, 800);
			player = new videoPlayer(file,1,400,600);

			jp.add(player, BorderLayout.WEST);

			controlsPane = new JPanel();
			controlsButton = new JPanel();
			start = new JButton("开始");
			start.addActionListener(new startE());
			controlsButton.add(start);

			stop = new JButton("停止");
			stop.addActionListener(new stopE());
			controlsButton.add(stop);
			stop.setEnabled(false);
						
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

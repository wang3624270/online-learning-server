package cn.edu.sdu.uims.component.complex;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

public class UVideoPlayFrame {
	private final JFrame frame;
	private JButton pauseButton, rewindButton, skipButton;
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
	private static final String VIDEO_PLAY_TEMP_FILE = "c:\\run\\videotemp.MP4";

	public UVideoPlayFrame(String fileName){
		frame = new JFrame("视频播放");
        frame.setBounds(100, 100, 600, 400);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mediaPlayerComponent.release();
                frame.dispose();
            }
        });
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		Container c = frame.getContentPane();
		c.setLayout(new BorderLayout());
		c.add(mediaPlayerComponent, BorderLayout.CENTER);
		JPanel controlsPane = new JPanel();
		pauseButton = new JButton("暂停");
		controlsPane.add(pauseButton);
		rewindButton = new JButton("重播");
		controlsPane.add(rewindButton);
		skipButton = new JButton("前进");
		controlsPane.add(skipButton);
		pauseButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        mediaPlayerComponent.getMediaPlayer().pause();
		    }
		});

		rewindButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	 mediaPlayerComponent.getMediaPlayer().play();
		    }
		});

		skipButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	mediaPlayerComponent.getMediaPlayer().skip(10000);
		    }
		});
		c.add(controlsPane, BorderLayout.SOUTH);
        frame.setVisible(true);
        mediaPlayerComponent.getMediaPlayer().playMedia(fileName);
	}
    public static void play(final String fileName) {
        new NativeDiscovery().discover();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UVideoPlayFrame(fileName);
            }
        });
    }
    public static void play(byte [] data) {
    	try {
		FileOutputStream out = new FileOutputStream(VIDEO_PLAY_TEMP_FILE); 
		out.write(data);
		out.close();
    	}catch(Exception e ) {
    		e.printStackTrace();
    		return;
    	}
    	play(VIDEO_PLAY_TEMP_FILE);
    }
    	
}

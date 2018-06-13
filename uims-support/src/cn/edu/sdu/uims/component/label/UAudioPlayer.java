package cn.edu.sdu.uims.component.label;

import java.io.FileOutputStream;

import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

public class UAudioPlayer {
    private  AudioMediaPlayerComponent mediaPlayerComponent = null;
	private static final String AUDIO_PLAY_TEMP_FILE = "c:\\run\\audiotemp.wav";

	public  UAudioPlayer() {
		try {
	        new NativeDiscovery().discover();
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                mediaPlayerComponent = new AudioMediaPlayerComponent();
	                mediaPlayerComponent.getMediaPlayer().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
	                    @Override
	                    public void stopped(MediaPlayer mediaPlayer) {
	                        exit(0);
	                    }
	                    @Override
	                    public void finished(MediaPlayer mediaPlayer) {
	                        exit(0);
	                    }

	                    @Override
	                    public void error(MediaPlayer mediaPlayer) {
	                        exit(1);
	                    }
	                });
	                mediaPlayerComponent.getMediaPlayer().playMedia(AUDIO_PLAY_TEMP_FILE);
	              }
	        });
		}catch(Exception e1){
			e1.printStackTrace();
		}
	}
    private void exit(int result) {
    	if(mediaPlayerComponent != null)
   			mediaPlayerComponent.release();
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new UAudioPlayer();
	}
	public static void audioPlay(byte [] data) {
		try {
			FileOutputStream out = new FileOutputStream(AUDIO_PLAY_TEMP_FILE); 
			out.write(data);
			out.close();		
			new UAudioPlayer();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}

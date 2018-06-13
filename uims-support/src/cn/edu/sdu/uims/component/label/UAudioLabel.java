package cn.edu.sdu.uims.component.label;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileOutputStream;

import javax.swing.SwingUtilities;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.uims.util.UimsUtils;
import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;

public class UAudioLabel extends ULabel implements MouseListener{

	private ListOptionInfo  info= null;
    private  AudioMediaPlayerComponent mediaPlayerComponent = null;
	private static final String AUDIO_PLAY_TEMP_FILE = "c:\\run\\audiotemp.wav";
	public void initContents() {
		this.addMouseListener(this);
	}

	
	public Object getData() {
		// TODO Auto-generated method stub
		return info;
	}
	public void setData(Object obj) {
		// TODO Auto-generated method stub
			if(obj instanceof ListOptionInfo) {
				info = (ListOptionInfo) obj;
			}
			if(info == null )
				this.setText("");
			else
				this.setText(info.getLabel());
	}
    private void exit(int result) {
    	if(mediaPlayerComponent != null)
   			mediaPlayerComponent.release();
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(info == null || info.getValue() == null)
			return;
		Integer attachId = new Integer(info.getValue());
		byte [] data = UimsUtils.getDataOfAttachFile(attachId);
		try {
			FileOutputStream out = new FileOutputStream(AUDIO_PLAY_TEMP_FILE); 
			out.write(data);
			out.close();
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

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

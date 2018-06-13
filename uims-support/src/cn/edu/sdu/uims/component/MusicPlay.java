package cn.edu.sdu.uims.component;
import java.io.FileInputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
public class MusicPlay {
    public MusicPlay() {
    	
    }
     public static void main(String[] args) throws Exception {
    	FileInputStream in = new FileInputStream("D:\\test.wav");
		 AudioStream as = new AudioStream(in); 
		 AudioPlayer.player.start(as);
    }
}

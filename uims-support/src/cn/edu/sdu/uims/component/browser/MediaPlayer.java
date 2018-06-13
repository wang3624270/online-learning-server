package cn.edu.sdu.uims.component.browser;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.stage.Stage;

public class MediaPlayer extends Application {
//    private static final String MEDIA_URL = "http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv";
// private static final String MEDIA_URL = "file:///D:/serviceVideo.mp4";
  private static final String MEDIA_URL = "file:///D:/serviceAudio.caf";
    private static String arg1;

    @Override public void start(Stage stage) {
        stage.setTitle("Media Player");
        Group root = new Group();
        Scene scene = new Scene(root,600,265);
        // create media player
        Media media = new Media((arg1 != null) ? arg1 : MEDIA_URL);
        javafx.scene.media.MediaPlayer mediaPlayer = new javafx.scene.media.MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        MediaControl mediaControl = new MediaControl(mediaPlayer);
        scene.setRoot(mediaControl);
        scene.getStylesheets().add(MediaPlayer.class.getResource("mediaplayer.css").toExternalForm());
        // show stage
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            arg1 = args[0];
        }
        Application.launch(args);
    }
}
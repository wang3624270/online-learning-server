package cn.edu.sdu.uims.component.browser;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

public class MediaControl extends BorderPane {

    private MediaPlayer mp;
    private MediaView mediaView;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;
    private Duration duration;
    private Slider timeSlider;
    private Label playTime;
    private CheckBox repeatBox;  
    private Slider volumeSlider;
    private HBox mediaBar;

    @Override
    protected void layoutChildren() {
       
        if (mediaView != null && getBottom() != null) {
            mediaView.setFitWidth(getWidth());
            mediaView.setFitHeight(getHeight() - getBottom().prefHeight(-1));
        }
        super.layoutChildren();
        if (mediaView != null) {
            mediaView.setTranslateX((((Pane) getCenter()).getWidth() - mediaView.prefWidth(-1)) / 2);
            mediaView.setTranslateY((((Pane) getCenter()).getHeight() - mediaView.prefHeight(-1)) / 2);
        }
    }

    @Override
    protected double computeMinWidth(double height) {
        return mediaBar.prefWidth(-1);
    }

    @Override
    protected double computeMinHeight(double width) {
        return 200;
    }

    @Override
    protected double computePrefWidth(double height) {
        return Math.max(mp.getMedia().getWidth(), mediaBar.prefWidth(height));
    }

    @Override
    protected double computePrefHeight(double width) {
        return mp.getMedia().getHeight() + mediaBar.prefHeight(width);
    }

    @Override
    protected double computeMaxWidth(double height) {
        return Double.MAX_VALUE;
    }

    @Override
    protected double computeMaxHeight(double width) {
        return Double.MAX_VALUE;
    } 
    
    public MediaControl(final MediaPlayer mp) {
        this.mp = mp;
        setId("mediaControl");
        
        mediaView = new MediaView(mp);
        Pane mvPane = new Pane();
        mvPane.getChildren().add(mediaView);
        mvPane.setId("mediaViewPane");
        setCenter(mvPane);

        mediaBar = new HBox();
        mediaBar.setAlignment(Pos.CENTER);
        mediaBar.setPadding(new Insets(5, 10, 5, 10));
        BorderPane.setAlignment(mediaBar, Pos.CENTER);

        final Button playButton = new Button(">");
        playButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                updateValues();
                Status status = mp.getStatus();

                if (status == Status.UNKNOWN
                        || status == Status.HALTED) {
                    System.out.println("Player is in a bad or unknown state, can't play.");
                    return;
                }

                if (status == Status.PAUSED
                        || status == Status.READY
                        || status == Status.STOPPED) {
                    // rewind the movie if we're sitting at the end
                    if (atEndOfMedia) {
                        mp.seek(mp.getStartTime());
                        atEndOfMedia = false;
                        playButton.setText(">");
                        updateValues();
                    }
                    mp.play();
                    playButton.setText("||");
                } else {
                    mp.pause();
                }
            }
        });

        mp.currentTimeProperty().addListener(new InvalidationListener() {

            @Override
            public void invalidated(Observable ov) {
                updateValues();
            }
        });

        mp.setOnPlaying(new Runnable() {

            public void run() {
                if (stopRequested) {
                    mp.pause();
                    stopRequested = false;
                } else {
                    playButton.setText("||");
                }
            }
        });

        mp.setOnPaused(new Runnable() {

            public void run() {
                playButton.setText(">");
            }
        });

        mp.setOnReady(new Runnable() {

            public void run() {
                duration = mp.getMedia().getDuration();
                updateValues();
            }
        });

        mp.setOnEndOfMedia(new Runnable() {

            public void run() {
               if (repeatBox.isSelected()) {
                    mp.seek(mp.getStartTime());
                } else {
                    playButton.setText(">");
                    stopRequested = true;
                    atEndOfMedia = true;
               
                  
                }
            }
        });

        mediaBar.getChildren().add(playButton);
        // Add spacer
        Label spacer = new Label("   ");
        spacer.setMaxWidth(Double.MAX_VALUE);
        mediaBar.getChildren().add(spacer);

        Label timeLabel = new Label("Time: ");
        timeLabel.setMinWidth(Control.USE_PREF_SIZE);
        mediaBar.getChildren().add(timeLabel);

        timeSlider = new Slider();
        timeSlider.setMaxWidth(Double.MAX_VALUE);
        timeSlider.setMinWidth(50);
        HBox.setHgrow(timeSlider, Priority.ALWAYS);
        timeSlider.valueProperty().addListener(new InvalidationListener() {

            @Override
            public void invalidated(Observable ov) {
                if (timeSlider.isValueChanging()) {
                    // multiply duration by percentage calculated by slider position
                    if (duration != null) {
                        mp.seek(duration.multiply(timeSlider.getValue() / 100.0));
                    }
                    updateValues();
                }
            }
        });
        mediaBar.getChildren().add(timeSlider);

        playTime = new Label();
        playTime.setPrefWidth(130);
        playTime.setMinWidth(50);
        mediaBar.getChildren().add(playTime);

        Label volumeLabel = new Label("Vol: ");
        volumeLabel.setMinWidth(Control.USE_PREF_SIZE);
        mediaBar.getChildren().add(volumeLabel);

        volumeSlider = new Slider();
        volumeSlider.setPrefWidth(70);
        volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
        volumeSlider.setMinWidth(30);
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (volumeSlider.isValueChanging()) {
                    mp.setVolume(volumeSlider.getValue() / 100.0);
                }
            }
        });
        mediaBar.getChildren().add(volumeSlider);
        
        Label repeatLabel = new Label("  Loop: ");
        repeatLabel.setPrefWidth(50);
        repeatLabel.setMinWidth(25);
        mediaBar.getChildren().add(repeatLabel);

        repeatBox = new CheckBox();
        repeatBox.setSelected(true);
        mediaBar.getChildren().add(repeatBox);
       
        setBottom(mediaBar);
    }

    protected void updateValues() {
        if (playTime != null && timeSlider != null && volumeSlider != null) {
            Platform.runLater(new Runnable() {

                public void run() {
                    Duration currentTime = mp.getCurrentTime();
                    playTime.setText(formatTime(currentTime, duration));
                    timeSlider.setDisable(duration.isUnknown());
                    if (!timeSlider.isDisabled() && duration.greaterThan(Duration.ZERO) && !timeSlider.isValueChanging()) {
                        timeSlider.setValue(currentTime.divide(duration.toMillis()).toMillis() * 100.0);
                    }
                    if (!volumeSlider.isValueChanging()) {
                        volumeSlider.setValue((int) Math.round(mp.getVolume() * 100));
                    }
                }
            });
        }
    }

    private static String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;

        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int) Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;

            if (durationHours > 0) {
                return String.format("%d:%02d:%02d/%d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds,
                        durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d/%02d:%02d",
                        elapsedMinutes, elapsedSeconds,
                        durationMinutes, durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d",
                        elapsedMinutes, elapsedSeconds);
            }
        }
    }
}

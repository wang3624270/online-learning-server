package cn.edu.sdu.uims.component.complex;

import static javafx.concurrent.Worker.State.FAILED;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import cn.edu.sdu.uims.component.complex.def.UWebViewTemplate;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.def.UEventAttribute;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;


public class UMapViewPanel extends UComplexPanel {
	private final JFXPanel jfxPanel = new JFXPanel();
	private WebEngine engine;

	private final JPanel panel = new JPanel(new BorderLayout());
	private final JLabel lblStatus = new JLabel();
	private final JProgressBar progressBar = new JProgressBar();
	private  boolean actionEventCanSend = false;
	private boolean isFinished = false;
	@Override
	public void initContents() {
		// TODO Auto-generated method stub
		initComponents();
		loadURL();
		isFinished = true;
	}

	public void initComponents() {
		createScene();

		progressBar.setPreferredSize(new Dimension(150, 18));
		progressBar.setStringPainted(true);

		JPanel statusBar = new JPanel(new BorderLayout(5, 0));
		statusBar.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
		statusBar.add(lblStatus, BorderLayout.CENTER);
		statusBar.add(progressBar, BorderLayout.EAST);
		panel.add(jfxPanel, BorderLayout.CENTER);
		panel.add(statusBar, BorderLayout.SOUTH);

		this.setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);
	}

	private void createScene() {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				WebView view = new WebView();
				engine = view.getEngine();

				engine.titleProperty().addListener(
						new ChangeListener<String>() {
							@Override
							public void changed(
									ObservableValue<? extends String> observable,
									String oldValue, final String newValue) {
								SwingUtilities.invokeLater(new Runnable() {
									@Override
									public void run() {
									}
								});
							}
						});

				engine.setOnStatusChanged(new EventHandler<WebEvent<String>>() {
					@Override
					public void handle(final WebEvent<String> event) {
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								lblStatus.setText(event.getData());
							}
						});
					}
				});

				engine.locationProperty().addListener(
						new ChangeListener<String>() {
							@Override
							public void changed(
									ObservableValue<? extends String> ov,
									String oldValue, final String newValue) {
								SwingUtilities.invokeLater(new Runnable() {
									@Override
									public void run() {
									}
								});
							}
						});

				engine.getLoadWorker().workDoneProperty()
						.addListener(new ChangeListener<Number>() {
							@Override
							public void changed(
									ObservableValue<? extends Number> observableValue,
									Number oldValue, final Number newValue) {
								SwingUtilities.invokeLater(new Runnable() {
									@Override
									public void run() {
										progressBar.setValue(newValue
												.intValue());
									}
								});
							}
						});

				engine.getLoadWorker().exceptionProperty()
						.addListener(new ChangeListener<Throwable>() {

							public void changed(
									ObservableValue<? extends Throwable> o,
									Throwable old, final Throwable value) {
								if (engine.getLoadWorker().getState() == FAILED) {
									SwingUtilities.invokeLater(new Runnable() {
										@Override
										public void run() {
											JOptionPane
													.showMessageDialog(
															panel,
															(value != null) ? engine
																	.getLocation()
																	+ "\n"
																	+ value.getMessage()
																	: engine.getLocation()
																			+ "\nUnexpected error.",
															"Loading error...",
															JOptionPane.ERROR_MESSAGE);
										}
									});
								}
							}
						});

				jfxPanel.setScene(new Scene(view));
			}
		});
	}

	public void loadURL() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				UWebViewTemplate te = (UWebViewTemplate) elementTemplate;
				String urlStr = "baidumap.html";
				if (te != null && te.url != null)
					urlStr = te.url;
				URL url = Thread.currentThread().getContextClassLoader()
						.getResource(urlStr);
				engine.load(url.toExternalForm());
				engine.getLoadWorker().stateProperty()
						.addListener(new ChangeListener<State>() {
							@Override
							public void changed(
									ObservableValue<? extends State> ov,
									State oldState, State newState) {
								if (newState == State.SUCCEEDED) {
									JSObject win = (JSObject) engine
											.executeScript("window");
									win.setMember("app",
											new WebEventProcessApp());
								}
							}
						});

			}
		});
	}

	private static String toURL(String str) {
		try {
			return new URL(str).toExternalForm();
		} catch (MalformedURLException exception) {
			return null;
		}
	}
	public void executeScript(final String scripStr){
		if(!isFinished)
			return;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println(scripStr);
					engine.executeScript(scripStr);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}

	public void setMapPoint(Double longitude, Double latitude){
		String scripStr = "window.map.panTo(new BMap.Point("+longitude + "," + latitude+"));";
		executeScript(scripStr);
	}
	public void setCurrentCity(String city){
		String scripStr = "window.map.centerAndZoom('"+city + "',11);";		
		executeScript(scripStr);
	}
	public void test() {
		setCurrentCity("济南");
	}
	public void searchAddress(String address) {
		String scripStr = "window.local.search('"+address + "');";
		executeScript(scripStr);
	}
	public void addEvents(UEventAttribute events[]) {
		int i;
		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_ACTION)) {
				actionEventCanSend = true;
			}
		}
	}

	public void processScriptCommand(String str){
		if(actionEventCanSend) {
			ActionEvent e= new ActionEvent(this.getAWTComponent(),0,str); 
			this.getUParent().getEventAdaptor().actionPerformed(e);
		}		
	}
	public class WebEventProcessApp {
		public void click(String a) {
			processScriptCommand("mapClick:"+a);
		}
	}
}

package cn.edu.sdu.uims.component.complex;

import static javafx.concurrent.Worker.State.FAILED;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import cn.edu.sdu.common.util.Base64;
import cn.edu.sdu.uims.component.complex.def.UWebAppTemplate;
import cn.edu.sdu.uims.frame.MyProperties;
import cn.edu.sdu.uims.util.UimsUtils;
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

public class UWebAppPanel extends UComplexPanel {

	private WebEngine engine;

	private  JFXPanel jfxPanel = null;
	private  JPanel panel = null;
	private JLabel lblStatus = null;

	private JButton btnGo = null;
	private JTextField txtURL = null;
	private JProgressBar progressBar = null;
	private String perNum = null;
	private String urlStr = null;
	private String webServer = null;
	private boolean isCanNavigation = false;
	private String token;
	private boolean isLoadFinished = true;
	private String requestCmd = null;
	private String toURL(String str) {
		if (str == null)
			return null;
		try {
			return new URL(str).toExternalForm();
		} catch (MalformedURLException exception) {
			return null;
		}
	}

	public String getApplyUrl() {
		if(perNum == null)
			return null;
		String pas = "[perNum:" + perNum;
		if(requestCmd != null) {
			pas += "|requestCmd:"  +requestCmd;
		}
		pas += "]";
		pas = new String(Base64.encode(pas.getBytes()));
		String url = webServer +"/login?token="+token+"&systemParas=" + pas;
		return url;
	}

	@Override
	public void initContents() {
		// TODO Auto-generated method stub
		if(this.getUParent() != null) {
			HashMap map = this.getUParent().getParameters();
			if(map != null) 
				requestCmd = (String)map.get("cmd");
		}
		UWebAppTemplate tp = (UWebAppTemplate)elementTemplate;
		if(tp.isUserNavigation && !tp.isClientUser) 
			isCanNavigation = true;
		else
			isCanNavigation = false;
		if(tp.isClientUser) {
			perNum = UimsUtils.userTokenClientSide.getLoginName();
		}
		if(tp.webServer != null) {
			webServer = tp.webServer;
		}else {
			MyProperties myProperties = MyProperties.createProperties();
			webServer = myProperties.getProperty(MyProperties.WEBSERVER);
		}
		if(tp.isMenuCommand) {
			token = "clientRequestCommand";
		}else {
			token = "clientRequestUser";
		}
		if(tp.requestCmd != null)
			requestCmd = tp.requestCmd;
		if(tp.defaultUrl != null) {
			urlStr = tp.defaultUrl;
		}else {
			if(tp.isUserNavigation) {
				urlStr = getApplyUrl();
			}
		}
		initComponents();
		loadURL(urlStr);
	}

	private void initComponents() {
		jfxPanel = new JFXPanel();
		panel = new JPanel(new BorderLayout());
		lblStatus = new JLabel();

		btnGo = new JButton("Go");
		txtURL = new JTextField();
		progressBar = new JProgressBar();

		createScene();

		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				perNum = txtURL.getText();
				urlStr = getApplyUrl();
				loadURL(urlStr);
			}
		};

		btnGo.addActionListener(al);
		txtURL.addActionListener(al);

		progressBar.setPreferredSize(new Dimension(150, 18));
		progressBar.setStringPainted(true);

		JPanel topBar = new JPanel(new BorderLayout(5, 0));
		topBar.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
		topBar.add(txtURL, BorderLayout.CENTER);
		topBar.add(btnGo, BorderLayout.EAST);

		JPanel statusBar = new JPanel(new BorderLayout(5, 0));
		statusBar.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
		statusBar.add(lblStatus, BorderLayout.CENTER);
		statusBar.add(progressBar, BorderLayout.EAST);

		if(isCanNavigation)
			panel.add(topBar, BorderLayout.NORTH);
		panel.add(jfxPanel, BorderLayout.CENTER);
		panel.add(statusBar, BorderLayout.SOUTH);

		this.setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);
		// setPreferredSize(new Dimension(1024, 600));
	}

	private void createScene() {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				WebView view = new WebView();
				engine = view.getEngine();

				engine.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
					@Override
					public void changed(ObservableValue<? extends State> ov,
							State oldState, State newState) {
						if (newState == State.SUCCEEDED) {
						}
					}
				});
				engine.titleProperty().addListener(
						new ChangeListener<String>() {
							@Override
							public void changed(
									ObservableValue<? extends String> observable,
									String oldValue, final String newValue) {
								SwingUtilities.invokeLater(new Runnable() {
									@Override
									public void run() {
										// SimpleSwingBrowser.this.setTitle(newValue);
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
//								System.out.println("state:" + event);
//								lblStatus.setText(event.getData());
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
//										txtURL.setText(newValue);
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
										if(newValue.intValue() >= 99) {
											isLoadFinished = true;							
										}
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
				Scene scene =  new Scene(view);
				jfxPanel.setScene(scene);
			}
		});
	}

	public void loadURL1(final String url) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				String tmp = toURL(url);
				engine.load(tmp);
			}
		});
	}
	public void loadURL(final String url) {
		new Thread(){
			public void run() {
/*				
				while(!isLoadFinished) {
					try {
					Thread.sleep(1000);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				isLoadFinished = false; */
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						String tmp = toURL(url);
						engine.load(tmp);
					}
				});
			}
		}.start();
	}
	
	public void setData(Object o) {
		if (o == null)
			urlStr = null;
		else
			urlStr = webServer +o.toString();
		loadURL(urlStr);
	}
	
	public void requestWebCommand(String cmd) {
		if(cmd == null || cmd.length() == 0)
			return;
		urlStr = webServer +"/"+ cmd;
		loadURL(urlStr);		
	}
}

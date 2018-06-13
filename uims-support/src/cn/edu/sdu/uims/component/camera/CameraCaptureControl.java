package cn.edu.sdu.uims.component.camera;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import de.humatic.dsj.DSCapture;
import de.humatic.dsj.DSCapture.CaptureDevice;
import de.humatic.dsj.DSFilterInfo;
import de.humatic.dsj.DSFiltergraph;
import de.humatic.dsj.DSJUtils;

public class CameraCaptureControl extends JPanel implements ActionListener,
		ItemListener, java.beans.PropertyChangeListener {

	JComboBox cameraCombo = null;
	JButton cameraSureButton = null;
	JButton cameraPropertyButton = null;
	String[] devs;
	int nowDevNum = 0;
	int captureBoxWidth = 0;
	int captureBoxHeight = 0;
	double mediaWidth = 0;
	double mediaHeight = 0;
	private DSFilterInfo[][] dsi;
	CameraCaptureBox captureBox;
	DSFiltergraph dsfg;
	CaptureDevice avd;

	Dimension mediaDimension;
	Box baseBox = null;

	public CameraCaptureControl() {
		super();
		int i;
		baseBox = Box.createHorizontalBox();
		this.setLayout(new GridLayout(1, 1));
		this.add(baseBox);
		cameraSureButton = new JButton("切换设备");
		cameraPropertyButton = new JButton("设备属性页...");
		dsi = DSCapture.queryDevices(1);

		devs = new String[dsi[0].length];
		for (i = 0; i < dsi[0].length; i++)
			devs[i] = dsi[0][i].getName();
		cameraCombo = new JComboBox(devs);
		cameraCombo.setSelectedIndex(0);
		// cameraCombo.addActionListener(this);
		cameraCombo.addItemListener(this);

		dsfg = new DSCapture(0, dsi[0][nowDevNum], false,
				dsi[1][dsi[1].length - 1], this);
		avd = ((DSCapture) dsfg).getActiveVideoDevice();
		mediaWidth = dsfg.getMediaDimension().getWidth();
		mediaHeight = dsfg.getMediaDimension().getHeight();

		// /////////////////////////////////////
		cameraCombo.setMaximumSize(new Dimension(240, 30));
		cameraSureButton.setMaximumSize(new Dimension(120, 30));
		cameraSureButton.addActionListener(this);

		cameraPropertyButton.setMaximumSize(new Dimension(120, 30));
		cameraPropertyButton.addActionListener(this);
		baseBox.add(cameraCombo);
		baseBox.add(cameraSureButton);
		baseBox.add(cameraPropertyButton);

	}

	public BufferedImage getCurrentCaptureImage() {
		if (captureBox != null)
			return captureBox.getCurrentCaptureImage();
		else
			return null;
	}
	public BufferedImage getCurrentClipCaptureImage() {
		if (captureBox != null)
			return captureBox.getCurrentClipCaptureImage();
		else
			return null;
	}

	public void initCaptureBox(int containerWidth, int containerHieght) {

		double mediaRatio = (double) mediaWidth / (double) mediaHeight;

		captureBox = new CameraCaptureBox(dsfg, containerWidth,
				containerHieght, mediaRatio);
	}

	public void showCaptureBox() {
		captureBox.showCamera();
	}

	public void setPictureDistinationJanel(PicturePanel panel) {
		captureBox.distinationPanel = panel;
	}

	public CameraCaptureBox getCaptureBox() {
		return this.captureBox;
	}

	public void closeDevice() {
		dsfg.dispose();
	}

	// /////////////
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("切换设备")) {
			dsfg.dispose();
			dsfg = new DSCapture(0, dsi[0][nowDevNum], false,
					dsi[1][dsi[1].length - 1], this);
			avd = ((DSCapture) dsfg).getActiveVideoDevice();
			mediaWidth = dsfg.getMediaDimension().getWidth();
			mediaHeight = dsfg.getMediaDimension().getHeight();
			captureBox.reloadCamera(dsfg);
		}
		if (e.getActionCommand().equals("设备属性页...")) {
			avd.showDialog(DSCapture.CaptureDevice.WDM_DEVICE);
		}
	}

	// //////////////

	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			nowDevNum = cameraCombo.getSelectedIndex();
		}

	}

	@Override
	public void propertyChange(PropertyChangeEvent pe) {
		// TODO Auto-generated method stub
		switch (DSJUtils.getEventType(pe)) {

		}

	}
}
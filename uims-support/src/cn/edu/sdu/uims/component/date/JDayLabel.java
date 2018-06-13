package cn.edu.sdu.uims.component.date;

import java.awt.Dimension;
import java.awt.Graphics;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import cn.edu.sdu.uims.util.UimsUtils;

/**
 * <p>
 * Title: UComboBoxDate
 * </p>
 * <p>
 * Description:JDayLable 带选择日期功能的JLabel
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author <a href="mailto:sunkingxie@hotmail.com"'>Sunking</a>
 * @version 1.0
 */

public class JDayLabel extends JLabel {
	private static ImageIcon todayIcon = (ImageIcon) UimsUtils
			.getCSClientIcon("today.gif");

	Date date = null;
	ImageIcon currentIcon = null;

	/**
	 * 日期格式（TODAY/TIP用）
	 */
	final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	/**
	 * 日格式
	 */
	final SimpleDateFormat dayFormat = new SimpleDateFormat("d");

	public JDayLabel(Date date) {
		this(date, true);
	}

	public JDayLabel(Date date, boolean isSmallLabel) {
		setPreferredSize(new Dimension(40, 20));
		setToolTipText(dateFormat.format(date));
		this.date = date;
		if (isSmallLabel) {
			setHorizontalAlignment(JLabel.CENTER);
			setText(dayFormat.format(date));
			Date d = new Date();
			if (dateFormat.format(date).equals(dateFormat.format(d))) {
				currentIcon = todayIcon;
			}
		} else {
			setText("Today:" + dateFormat.format(new Date()));
			setIcon(todayIcon);
			// setBorder(BorderFactory.createLineBorder(Color.RED));
			setHorizontalAlignment(JLabel.LEFT);
		}
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void paint(Graphics g) {
		super.paint(g);
		if (currentIcon != null && isEnabled()) {
			int x = (this.getWidth() - currentIcon.getIconWidth()) / 2;
			int y = (this.getHeight() - currentIcon.getIconHeight()) / 2;
			currentIcon.paintIcon(this, g, x, y);
		}
	}
}

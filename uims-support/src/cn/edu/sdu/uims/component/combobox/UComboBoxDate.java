package cn.edu.sdu.uims.component.combobox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxUI;

import com.sun.java.swing.plaf.motif.MotifComboBoxUI;
import com.sun.java.swing.plaf.windows.WindowsComboBoxUI;

import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.component.date.CalendarPane;
import cn.edu.sdu.uims.component.date.JCalendarPanel;
import cn.edu.sdu.uims.component.date.JDateDocument;
import cn.edu.sdu.uims.component.date.SingleObjectComboBoxModel;
import cn.edu.sdu.uims.def.UComboBoxDateTemplate;

/**
 * <p>
 * Title:OpenSwing
 * </p>
 * <p>
 * Description: UComboBoxDate 日期选择框<BR>
 * 履历:<BR>
 * 2004/03/26 根据网友caiyj的建议引入了recoon写的关于JDateDocument的校验方法<BR>
 * 2004/04/02 根据网友caiyj提交的BUG,修正了做为TableCellEditor时日期选择面板弹不出问题<BR>
 * 2005/04/17 修正了弹出面板不能显示当前输入框中的日期<BR>
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
public class UComboBoxDate extends UComboBox implements Serializable {
	/**
	 * 日期格式类型
	 */
	public static final int STYLE_CN_DATE = 0;
	public static final int STYLE_CN_DATE1 = 1;
	public static final int STYLE_CN_DATETIME = 2;
	public static final int STYLE_CN_DATETIME1 = 3;

	public UComboBoxDateTemplate template;
	/**
	 * 日期格式类型
	 */
	private int formatStyle = STYLE_CN_DATE;
	/**
	 * 当前设置日期格式
	 */
	private SimpleDateFormat dateFormat = null;

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	/**
	 * 只有一个值的ComboBoxModel
	 */
	private SingleObjectComboBoxModel model = new SingleObjectComboBoxModel();
	JDateDocument dateDocument = null;
	JTextField textField = null;

	/**
	 * 构造式
	 */
	public UComboBoxDate() throws UnsupportedOperationException {
		this(STYLE_CN_DATE);

	}

	public UComboBoxDate(Integer formatStyle)
			throws UnsupportedOperationException {
		this(formatStyle, null);
	}

	public UComboBoxDate(Integer formatStyle, Date initialDatetime)
			throws UnsupportedOperationException {

		this.setStyle(formatStyle);
		// 设置可编辑
		this.setEditable(true);
		// 设置编辑器属性(只能输入正确日期)
		textField = ((JTextField) getEditor().getEditorComponent());
//		textField.setSelectedTextColor(new Color(255,0,0));
//		textField.setSelectionColor(new Color(255,0,0));
//		textField.setForeground(new Color(255,0,0));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		dateDocument = new JDateDocument(textField, this.dateFormat);
		textField.setDocument(dateDocument);
		// 设置Model为单值Model
		this.setModel(model);
		// 设置当前选择日期
		this.setSelectedItem(initialDatetime);
	}

	/**
	 * 为日期文本框添加actionListener
	 * 
	 * @param listener
	 *            actionListener
	 * @param actionCmd
	 *            action command
	 */
	
	public void addActionListenerToTextField(ActionListener listener,
			String actionCmd) {
		textField.addActionListener(listener);
		textField.setActionCommand(actionCmd);
	}

	/**
	 * 设置日期格式 STYLE_CN_DATE STYLE_CN_DATE1 STYLE_CN_DATETIME STYLE_CN_DATETIME1
	 * 
	 * @param formatStyle
	 *            int
	 */
	public void setStyle(int formatStyle) throws UnsupportedOperationException {
		this.formatStyle = formatStyle;
		dateFormat = getDateFormat(formatStyle);
		model.setDateFormat(dateFormat);
		if (dateDocument != null) {
			dateDocument.setDateFormat(dateFormat);
		}
	}

	/**
	 * 取得指定类型的日期格式
	 * 
	 * @param formatStyle
	 *            int
	 * @return SimpleDateFormat
	 * @throws UnsupportedOperationException
	 */
	private static SimpleDateFormat getDateFormat(int formatStyle)
			throws UnsupportedOperationException {
		switch (formatStyle) {
		case STYLE_CN_DATE:
			return new SimpleDateFormat("yyyy/MM/dd");
		case STYLE_CN_DATE1:
			return new SimpleDateFormat("yyyy-MM-dd");
		case STYLE_CN_DATETIME:
			return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		case STYLE_CN_DATETIME1:
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		default:
			throw new UnsupportedOperationException(
					"invalid formatStyle parameter!");
		}
	}

	/**
	 * 取得指定类型的日期格式字符串
	 * 
	 * @param formatStyle
	 *            int
	 * @return SimpleDateFormat
	 * @throws UnsupportedOperationException
	 */
	private static String getDateFormatStr(int formatStyle)
			throws UnsupportedOperationException {
		switch (formatStyle) {
		case STYLE_CN_DATE:
			return "yyyy/MM/dd";
		case STYLE_CN_DATE1:
			return "yyyy-MM-dd";
		case STYLE_CN_DATETIME:
			return "yyyy/MM/dd HH:mm:ss";
		case STYLE_CN_DATETIME1:
			return "yyyy-MM-dd HH:mm:ss";
		default:
			throw new UnsupportedOperationException(
					"invalid formatStyle parameter!");
		}
	}

	/**
	 * 取得日期格式 STYLE_CN_DATE STYLE_CN_DATE1 STYLE_CN_DATETIME STYLE_CN_DATETIME1
	 * 
	 * @return int
	 */
	public int getStyle() {
		return formatStyle;
	}

	public void setAddedDatas(Object[] o) {
	}

	public Object getData() {
		Date d = null;
		try {
			d = getSelectedDate();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return d;
	}

	public void setData(Object o) {
		if (o == null) {
			setSelectedItem(null);
			textField.setText("");
//	        textField.setForeground(null);
			clear();
		}else {
			try {
				setSelectedItem(dateFormat.format((Date) o));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 取得当前选择的日期
	 * 
	 * @return Date
	 */
	public Date getSelectedDate() throws ParseException {
		String dateStr = getSelectedItem().toString();
		if(dateStr==null || "".equals(dateStr))
			return null;
		else
			return dateFormat.parse(getSelectedItem().toString());
	}

	/**
	 * 
	 * @return 格式为yyyy-mm-dd的日期字符串 如果格式不正确返回null
	 */
	public String getSelectedDateStr() {
		Date selectedDate = null;
		try {
			selectedDate = this.getSelectedDate();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return null;
			// e.printStackTrace();
		}
		if (selectedDate != null) {
			return new SimpleDateFormat(this.getDateFormatStr(formatStyle))
					.format(selectedDate);
		} else {
			return null;
		}
	}

	/**
	 * 设置当前选择的日期
	 * 
	 * @return Date
	 */
	public void setSelectedDate(Date date) throws ParseException {
		if (date == null) {
			return;
		}
		this.setSelectedItem(dateFormat.format(date));
	}

	/**
	 * 设置当前日期
	 * 
	 * @param dateStr
	 *            日期 字符串
	 * @throws ParseException
	 *             日期 转换 异常
	 */
	public void setSelectedDateStr(String dateStr) throws ParseException {
		Date selectedDate = new Date();
		if (dateStr != null) {
			SimpleDateFormat format = UComboBoxDate
					.getDateFormat(UComboBoxDate.STYLE_CN_DATE1);
			selectedDate = format.parse(dateStr);
		}
		this.setSelectedItem(selectedDate);
	}

	public void setSelectedItem(Object anObject) {
		model.setSelectedItem(anObject);
		super.setSelectedItem(anObject);
	}


	/**
	 * 更新UI
	 */
	public void updateUI() {
		ComboBoxUI cui = (ComboBoxUI) UIManager.getUI(this);
		if (cui instanceof MetalComboBoxUI) {
			cui = new MetalDateComboBoxUI();
		} else if (cui instanceof MotifComboBoxUI) {
			cui = new MotifDateComboBoxUI();
		} else {
			cui = new WindowsDateComboBoxUI();
		}
		setUI(cui);
	}
	class DatePopup extends BasicComboPopup implements ChangeListener {
		JCalendarPanel calendarPanel = null;

		public DatePopup(JComboBox box) {
			super(box);
			setLayout(new BorderLayout());
			calendarPanel = new JCalendarPanel();
			calendarPanel.addDateChangeListener(this);
			add(calendarPanel, BorderLayout.CENTER);
			setBorder(BorderFactory.createEmptyBorder());
		}

		/**
		 * 显示弹出面板
		 */
		protected void firePropertyChange(String propertyName, Object oldValue,
				Object newValue) {
			if (propertyName.equals("visible")) {
				if (oldValue.equals(Boolean.FALSE)
						&& newValue.equals(Boolean.TRUE)) { // SHOW
					try {
						String strDate = comboBox.getSelectedItem().toString();
						if(strDate!= null && strDate.length()!=0) {
							Date selectionDate = dateFormat.parse(strDate);
							calendarPanel.setSelectedDate(selectionDate);
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} else if (oldValue.equals(Boolean.TRUE)
						&& newValue.equals(Boolean.FALSE)) { // HIDE
				}
			}
			super.firePropertyChange(propertyName, oldValue, newValue);
		}

		public void stateChanged(ChangeEvent e) {
			Date selectedDate = (Date) e.getSource();
			String strDate = dateFormat.format(selectedDate);
			if (comboBox.isEditable() && comboBox.getEditor() != null) {
				comboBox.configureEditor(comboBox.getEditor(), strDate);
			}
			comboBox.setSelectedItem(strDate);
			comboBox.setPopupVisible(false);
		}
	}

	// UI Inner classes -- one for each supported Look and Feel
	/**
	 * <p>
	 * Title: OpenSwing
	 * </p>
	 * <p>
	 * Description: MetalDateComboBoxUI
	 * </p>
	 * <p>
	 * Copyright: Copyright (c) 2004
	 * </p>
	 * <p>
	 * Company:
	 * </p>
	 * 
	 * @author <a href="mailto:sunkingxie@hotmail.com">SunKing</a>
	 * @version 1.0
	 */
	class MetalDateComboBoxUI extends MetalComboBoxUI {
		protected ComboPopup createPopup() {
			return new DatePopup(comboBox);
		}
	}

	/**
	 * 
	 * <p>
	 * Title: OpenSwing
	 * </p>
	 * <p>
	 * Description: WindowsDateComboBoxUI
	 * </p>
	 * <p>
	 * Copyright: Copyright (c) 2004
	 * </p>
	 * <p>
	 * Company:
	 * </p>
	 * 
	 * @author <a href="mailto:sunkingxie@hotmail.com">SunKing</a>
	 * @version 1.0
	 */
	class WindowsDateComboBoxUI extends WindowsComboBoxUI {
		protected ComboPopup createPopup() {
			return new DatePopup(comboBox);
		}
	}

	/**
	 * 
	 * <p>
	 * Title: OpenSwing
	 * </p>
	 * <p>
	 * Description: MotifDateComboBoxUI
	 * </p>
	 * <p>
	 * Copyright: Copyright (c) 2004
	 * </p>
	 * <p>
	 * Company:
	 * </p>
	 * 
	 * @author <a href="mailto:sunkingxie@hotmail.com">SunKing</a>
	 * @version 1.0
	 */
	class MotifDateComboBoxUI extends MotifComboBoxUI {
		protected ComboPopup createPopup() {
			return new DatePopup(comboBox);
		}
	}

	public Object getLabel(Object value) {
		// TODO Auto-generated method stub
		return value;
	}

	public UComboBoxDateTemplate getTemplate() {
		return template;
	}

	public void setTemplate(UTemplate template) {
		this.template = (UComboBoxDateTemplate) template;
	}
	public int getDefautFormatStyle(){
		return STYLE_CN_DATE;
	}
	public void initContents() {
		if (template != null && template.style != -1) {
			super.initContents();
			this.setStyle(template.style);
		} else {
			this.setStyle(getDefautFormatStyle());
		}
		this.setEditable(true);
		// 设置编辑器属性(只能输入正确日期)
		textField = ((JTextField) getEditor().getEditorComponent());
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		dateDocument = new JDateDocument(textField, this.dateFormat);
		textField.setDocument(dateDocument);
		// 设置Model为单值Model
		this.setModel(model);
		// 设置当前选择日期
		if(template != null && !(template.defaultCurrentDate))
			this.setSelectedItem(null);
		else 
			this.setSelectedItem(new Date());
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				if (canActionEvent) {
					getUParent().getEventAdaptor().actionPerformed(ae);
				}
			}
		});
	}
	public CalendarPane getCalendarPaneObject(){
		return null;
		
	}
	public void clear() {

//		this.setStyle(formatStyle);
		// 设置可编辑
		// 设置编辑器属性(只能输入正确日期)
		textField = ((JTextField) getEditor().getEditorComponent());
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		dateDocument = new JDateDocument(textField, this.dateFormat);
		textField.setDocument(dateDocument);
		// 设置Model为单值Model
		setModel(model);
	}
	
	public void setColor(UColor c) {
		// TODO Auto-generated method stub
		if(c != null) {
			textField.setForeground(c.color);
		}
	}

}
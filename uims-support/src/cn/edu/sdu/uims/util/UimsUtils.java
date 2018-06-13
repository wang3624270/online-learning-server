package cn.edu.sdu.uims.util;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.dom4j.Element;
import org.sdu.rmi.RmiClientRequest;
import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;
import org.sdu.rmi.UserTokenClientSide;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.common.form.UForm;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.common.util.Base64;
import cn.edu.sdu.common.util.DateTimeTool;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.component.browser.SimpleSwingBrowser;
import cn.edu.sdu.uims.component.complex.UVideoPlayFrame;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.component.hsscanner.VideoPlayer;
import cn.edu.sdu.uims.component.label.UAudioPlayer;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.dataexport.DataExportTemplate;
import cn.edu.sdu.uims.def.dataimport.DataImportTemplate;
import cn.edu.sdu.uims.def.dataimport.ItemTemplate;
import cn.edu.sdu.uims.graph.form.UPdfViewDataForm;
import cn.edu.sdu.uims.graph.form.USinglePdfViewDataForm;
import cn.edu.sdu.uims.pi.ClientMainI;
import cn.edu.sdu.uims.pi.UClientFrameI;
import cn.edu.sdu.uims.service.UFactory;

public class UimsUtils {

	public static final int WEAKPAS_YES = 1; // 是弱密码
	public static final int WEAKPAS_NO = 0; // 其他问题
	public static final String DATAITEM_SEPARATE = "|";
	public static final String systemTitle = "系统提示";
	public static final String KEYVALUE_SEPARATE = ":";
	public static int YES_OPTION = JOptionPane.YES_OPTION;
	public static int NO_OPTION = JOptionPane.NO_OPTION;
	public static UserTokenClientSide userTokenClientSide;

	public  static  String  getIntegerListToSqlString(List<Integer>idList){
		if(idList == null || idList.size()== 0)
			return null;
		String str = "(";
		for(int i= 0; i <idList.size()-1;i++) {
			str += idList.get(i) + ",";
		}
		str += idList.get(idList.size()-1) +")";
		return str;
	}
	
	public  static  String [] getArraysFromString(String str){//把字符串两两一组存到数组里
		if(str == null || str.length() == 0)
			return null;
		String s[] = new String[str.length()/2];
		for(int i = 0;i < s.length;i++) {
			s[i] = str.substring(i*2, (i+1)*2);
		}
		return s;
	}
	public  static  String  getStringFromArrays(String str[]){//把数组中存的连成字符串
		if(str == null || str.length == 0)
			return null;
		String s = "";
		for(int i = 0;i < str.length;i++) {
			s += str[i];
		}
		return s;
	}

	public static int validateWeakPassword(String passwd) {
		// 长度检测
		if (passwd == null || passwd.length() < 8) {
			return WEAKPAS_YES;
		}
//		boolean up = false; // 是否有大写字母
		boolean low = false; // 是否有小写字母
		boolean num = false; // 是否有数字
		boolean other = false; // 是否有非法字符
		char[] chars = passwd.toCharArray();
		for (char c : chars) {
			if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
				low = true;
			} 
//			else if (c >= 'A' && c <= 'Z') {
//				up = true;
//			}
			 else if (c >= '0' && c <= '9') {
					num = true;
				} 
			else {
				other = true;
			}
		}
		if (other) {
			return WEAKPAS_YES;
		} 
//		else if (up && low && num) {
//			return WEAKPAS_NO;
//		} 
		else if (low && num) {
			return WEAKPAS_NO;
		} 
		else
			return WEAKPAS_YES;
	}

	
	static public void messageBoxInfoByPromptName(String name) {
		String s = UFactory.getModelSession().getPromptInfoByName(name, UimsFactory.getClientMainI().isEnglishVersion());
		messageBoxInfo(null,s);
	}
	public static String checkParameterWithFialedNull(String parameter) {
		if (checkParameter(parameter))
			return null;
		else
			return parameter;
	}

	public static boolean checkParameter(String parameter) {
		if (parameter == null || parameter.length() == 0)
			return true;
		parameter = parameter.toLowerCase();
		if (parameter.indexOf('(') >= 0) {
			return false;
		} else if (parameter.indexOf(')') >= 0) {
			return false;
		} else if (parameter.indexOf('#') >= 0) {
			return false;
		} else if (parameter.indexOf('=') >= 0) {
			return false;
		} else if (parameter.indexOf('>') >= 0) {
			return false;
		} else if (parameter.indexOf('<') >= 0) {
			return false;
		} else if (parameter.indexOf('$') >= 0) {
			return false;
		} else if (parameter.indexOf('%') >= 0) {
			return false;
		} else if (parameter.indexOf('*') >= 0) {
			return false;
		} else if (parameter.indexOf(" and ") >= 0) {
			return false;
		} else if (parameter.indexOf(" or ") >= 0) {
			return false;
		}

		return true;
	}

	public static ItemTemplate getItemTemplate(String type) {
		if (type.equals("date") || type.equals("Date")) {
			return null;
		}
		String className = null;
		if (type.startsWith("common")) {
			className = "cn.edu.sdu.commoncomponent.bs.ItemTemplate" + type.substring(0, 1).toUpperCase()
					+ type.substring(1, type.length());

		} else {
			className = "cn.edu.sdu.uims.def.item.ItemTemplate";
			if (type.length() > 0) {
				className += type.substring(0, 1).toUpperCase() + type.substring(1, type.length());
			}
		}
		if (className == null)
			return null;
		try {
			return (ItemTemplate) Class.forName(className).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static DataImportTemplate getDataImportTemplate(String type) {
		String className = "cn.edu.sdu.uims.def.dataimport.DataImportTemplate" + type.substring(0, 1).toUpperCase()
				+ type.substring(1, type.length());
		try {
			return (DataImportTemplate) Class.forName(className).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static DataExportTemplate getDataExportTemplate(String type) {
		String className = "cn.edu.sdu.uims.def.dataexport.DataExportTemplate" + type.substring(0, 1).toUpperCase()
				+ type.substring(1, type.length());
		try {
			return (DataExportTemplate) Class.forName(className).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getStringSQLFromStringArrays(String a[]) {
		if (a == null || a.length == 0)
			return null;
		String s = "";
		for (int i = 0; i < a.length; i++) {
			s += "'" + a[i] + "'";
			if (i < a.length - 1)
				s += ",";
		}
		return s;
	}

	public static String getIntegerSQLFromIntegerArrays(Integer a[]) {
		if (a == null || a.length == 0)
			return null;
		String s = "";
		int i = 0;
		for (; i < a.length; i++) {
			s += a[i] + ",";
		}
		if (i == a.length)
			s = s.substring(0, s.length() - 1);
		return s;
	}

	public static Integer[] getIntegerArraysFromStringSQL(String sql) {
		if (sql == null || sql.length() == 0)
			return null;
		StringTokenizer sz = new StringTokenizer(sql, ",");
		Integer a[] = new Integer[sz.countTokens()];
		String s;
		for (int i = 0; i < a.length; i++) {
			s = sz.nextToken();
			a[i] = new Integer(s.substring(0, s.length()));
		}
		return a;
	}

	public static Integer[] getStringArraysFromIntegerSQL(String sql) {
		if (sql == null || sql.length() == 0)
			return null;
		StringTokenizer sz = new StringTokenizer(sql, ",");
		Integer a[] = new Integer[sz.countTokens()];
		String s;
		for (int i = 0; i < a.length; i++) {
			s = sz.nextToken();
			a[i] = new Integer(s.substring(1, s.length() - 1));
		}
		return a;
	}

	public static String[] getStringArraysFromStringSQL(String sql) {
		if (sql == null || sql.length() == 0)
			return null;
		StringTokenizer sz = new StringTokenizer(sql, ",");
		String a[] = new String[sz.countTokens()];
		String s;
		for (int i = 0; i < a.length; i++) {
			s = sz.nextToken();
			if (s.charAt(0) == '\'')
				a[i] = s.substring(1, s.length() - 1);
			else
				a[i] = s;
		}
		return a;
	}

	public static String getStyleValue(String style) {
		if (style != null && style.length() > 1 && style.charAt(0) == '#') {
			return UFactory.getModelSession().getItemStyleTemplate(style.substring(1, style.length()));
		} else
			return style;
	}

	public static String getMultiRowString(Element e, String itemName) {
		Iterator it1;
		Element e1 = null;
		String str;
		StringBuffer sb = null;
		it1 = e.elementIterator(itemName);
		while (it1.hasNext()) {
			if (sb == null)
				sb = new StringBuffer(1024);
			e1 = (Element) it1.next();
			str = e1.attributeValue("value");
			sb.append(str + "");
		}
		if (sb == null)
			return null;
		else
			return sb.toString();
	}

	public static HashMap<String, Method> getSetMethodLabelAsKey(String formClassName, List<ItemTemplate> itemList) {
		String methodName;
		ItemTemplate item;
		Method method;
		String name;
		Class c = null;
		try {
			c = Class.forName(formClassName);
		} catch (Exception e) {
			return null;
		}
		if (c == null)
			return null;
		if (itemList == null || itemList.size() == 0)
			return null;
		HashMap<String, Method> setMethodMap = new HashMap<String, Method>();
		for (int i = 0; i < itemList.size(); i++) {
			item = itemList.get(i);
			name = item.getName();
			if (name == null || name.equals(""))
				continue;
			methodName = name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
			try {
				if (item.getIndex() == null)
					method = c.getMethod("get" + methodName);
				else
					method = c.getMethod("get" + methodName, Integer.class);
				item.setValueClassType(method.getReturnType());
				if (item.getIndex() == null)
					method = c.getMethod("set" + methodName, item.getValueClassType());
				else
					method = c.getMethod("set" + methodName, Integer.class, item.getValueClassType());
				setMethodMap.put(item.getLabel(), method);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return setMethodMap;
	}

	public static String objectToString(Object o, String format) {
		if (o == null)
			return "";
		if (o instanceof ListOptionInfo) {
			ListOptionInfo info = (ListOptionInfo) o;
			return info.getLabel();
		} else if (o instanceof Date) {
			if (format == null)
				format = "yyyy-MM-dd";
			return DateTimeTool.getTimeStr((Date) o, format);
		} else if (o instanceof Double) {
			if (format == null) {
				format = "0.##";
			}
			DecimalFormat fmt = new DecimalFormat(format);
			return fmt.format(o);
			// } else if (o instanceof BSumDataFormI) {
			// return ((BSumDataFormI) o).getNoLabel();
		} else
			return o.toString();
	}

	public static List getJsonArrayFromListOptionInfoList(List list) {
		List array = new ArrayList();
		ListOptionInfo info;
		for (int i = 0; i < list.size(); i++) {
			Map object = new HashMap();
			info = (ListOptionInfo) list.get(i);
			object.put("value", info.getValue());
			object.put("label", info.getLabel());
			array.add(object);
		}
		return array;
	}

	public static HashMap<String, ItemTemplate> getItemMapLableAsKey(List<ItemTemplate> itemList) {
		HashMap<String, ItemTemplate> map = new HashMap<String, ItemTemplate>();
		for (int i = 0; i < itemList.size(); i++) {
			map.put(itemList.get(i).getLabel(), itemList.get(i));
		}
		return map;

	}

	static public void messageBoxError(String s) {
		messageBoxError(null, s);
	}

	static public void messageBoxError(Component paraent, String s) {
		JOptionPane.showMessageDialog(null, s, systemTitle, JOptionPane.ERROR_MESSAGE);
	}

	static public void messageBoxInfo(String s) {
		messageBoxInfo(null, s);
	}

	static public void messageBoxInfo(Component paraent, String s) {
		JOptionPane.showMessageDialog(paraent, s, systemTitle, JOptionPane.INFORMATION_MESSAGE);
	}

	static public int messageBoxChoice(String s) {
		return messageBoxChoice(null, s);
	}

	static public int messageBoxChoice(Component paraent, String s) {
		int nResult;
		nResult = JOptionPane.showConfirmDialog(paraent, s, systemTitle, JOptionPane.YES_NO_OPTION);
		return nResult;
	}

	public static HashMap<String, String> stringToHashMap(String str) {
		if (str == null || str.equals("") || str.length() < 2)
			return null;
		HashMap<String, String> map = new HashMap<String, String>();
		String tempString = str.substring(1, str.length() - 1);
		StringTokenizer ss = new StringTokenizer(tempString, DATAITEM_SEPARATE);
		String item;
		int pos;
		String key, value;
		while (ss.hasMoreElements()) {
			item = ss.nextToken();
			pos = item.indexOf(KEYVALUE_SEPARATE);
			key = item.substring(0, pos);
			value = item.substring(pos + 1, item.length());
			map.put(key, value);
		}
		return map;
	}

	public static HashMap<String, String> stringToHashMap2(String str) {
		if (str == null || str.equals("") || str.length() < 2)
			return null;
		HashMap<String, String> map = new HashMap<String, String>();
		String tempString = str.substring(1, str.length() - 1);
		StringTokenizer ss = new StringTokenizer(tempString, ";");
		String item;
		int pos;
		String key, value;
		while (ss.hasMoreElements()) {
			item = ss.nextToken();
			pos = item.indexOf(KEYVALUE_SEPARATE);
			key = item.substring(0, pos);
			value = item.substring(pos + 1, item.length());
			map.put(key, value);
		}
		return map;
	}

	public static Icon getCSClientIcon(String path) {

		if (path == null)
			return null;
		URL url = UimsUtils.class.getResource("/"+path);
//		Thread.currentThread().getContextClassLoader().getResource(path);
//		URL url = Thread.currentThread().getContextClassLoader().getResource(path);
		if (url != null) {
			return new ImageIcon(url);
		} else {
			return null;
		}

	}

	public static BufferedImage getIconImageByFileName(String fileName) {
		if (fileName != null) {
			// return new ImageIcon(fileName);
			InputStream inputStream = UimsUtils.class.getResourceAsStream(fileName);
			try {
				return ImageIO.read(inputStream);
			} catch (Exception e) {
				return null;
			}
		} else {
			return null;
		}

	}

	public static Image getCSClientImage(String path) {

		if (path == null)
			return null;
		URL url = Thread.currentThread().getContextClassLoader().getResource(path);
		if (url != null) {
			return Toolkit.getDefaultToolkit().getImage(url);
		} else {
			return null;
		}

	}

	public static ListOptionInfo getPleaseSelectInfo() {
		return new ListOptionInfo(null, "-1", "请选择...", "Please select...");
	}

	public static ListOptionInfo getSelectAllInfo(String value) {
		return new ListOptionInfo(null, value, "全部", "All");
	}

	public static String getComboBoxSelectedValue(JComboBox box) {
		if (box.getSelectedIndex() == -1) {
			return null;
		}
		ListOptionInfo listOptionInfo = (ListOptionInfo) box.getSelectedItem();
		return listOptionInfo.getValue();
	}

	public static void setItemSelectedInComboBox(Object value, JComboBox jComboBox) {

		if (jComboBox == null) {
			return;
		}

		int itemCount = jComboBox.getItemCount();
		// 循环遍历判断选中项的位置
		for (int i = 0; i < itemCount; i++) {

			ListOptionInfo listOptionInfo = (ListOptionInfo) jComboBox.getItemAt(i);

			if (listOptionInfo.getValue() != null && value != null) {
				String valueStr = value.toString();
				if (listOptionInfo.getValue().equals(valueStr)) {
					jComboBox.setSelectedIndex(i);
					return;
				}
			} else if (listOptionInfo.getValue() == null && value == null) {
				jComboBox.setSelectedIndex(i);
				return;
			}

		}
		jComboBox.setSelectedIndex(-1);
	}

	public static Icon getIconByFileName(String fileName) {
		if (fileName != null) {
			InputStream inputStream = UimsUtils.class.getResourceAsStream(fileName);
			if (inputStream == null) {
				System.out.println(fileName + "图像找不到！");
				return null;
			}
			return getIconByInputStream(inputStream);
		} else {
			return null;
		}

	}

	public static Icon getIconByInputStream(InputStream inputStream) {
		byte[] buffer;
		try {
			buffer = new byte[inputStream.available()];
			inputStream.read(buffer);
			return new ImageIcon(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static List<String> getMultiStringList(String s, int charMax) {
		if (s == null || s.length() == 0)
			return null;
		List<String> retList = new ArrayList<String>();
		StringTokenizer sz = new StringTokenizer(s);
		String ss;
		int rn;
		int row = 0;
		int c = 0, len = 0;
		int n = s.length();
		while (sz.hasMoreTokens()) {
			ss = sz.nextToken();
			n = ss.length();
			rn = n / charMax;
			if (n % charMax != 0)
				rn++;
			row = 0;
			c = 0;
			while (row < rn && c < n) {
				if (c + charMax < n) {
					len = charMax;
				} else {
					len = n - c;
				}
				retList.add(ss.substring(c, c + len));
				c += len;
				row++;
			}
		}
		return retList;
	}

	public static FlowLayout getFlowLayout(int alignment, int hgap, int vgap) {
		int align;
		if (alignment == UConstants.ALIGNMENT_CENTER) {
			align = FlowLayout.CENTER;
		} else if (alignment == UConstants.ALIGNMENT_LEFT) {
			align = FlowLayout.LEFT;
		} else if (alignment == UConstants.ALIGNMENT_RIGHT) {
			align = FlowLayout.RIGHT;
		} else if (alignment == UConstants.ALIGNMENT_LEADING) {
			align = FlowLayout.LEADING;
		} else if (alignment == UConstants.ALIGNMENT_TRAILING) {
			align = FlowLayout.TRAILING;
		} else {
			align = FlowLayout.CENTER;
		}
		return new FlowLayout(align, hgap, vgap);
	}

	public static String getBorderLayoutValue(int alignment) {
		if (alignment == UConstants.ALIGNMENT_BOTTOM) {
			return BorderLayout.SOUTH;
		} else if (alignment == UConstants.ALIGNMENT_LEFT) {
			return BorderLayout.WEST;
		} else if (alignment == UConstants.ALIGNMENT_RIGHT) {
			return BorderLayout.EAST;
		} else if (alignment == UConstants.ALIGNMENT_TOP) {
			return BorderLayout.NORTH;
		} else {
			return BorderLayout.CENTER;
		}
	}

	public static boolean panelLayoutIsPageLayout(String layout) {
		if (layout.equals(UConstants.LAYOUTMODE_PAGE))
			return true;
		if (layout.equals(UConstants.LAYOUTMODE_PAGE_LIST))
			return true;
		if (layout.equals(UConstants.LAYOUTMODE_PAGE_ICON))
			return true;
		if (layout.equals(UConstants.LAYOUTMODE_PAGE_TREE))
			return true;
		if (layout.equals(UConstants.LAYOUTMODE_PAGE_LABEL))
			return true;
		return false;
	}

	public static void downloadFile(String remoteFilePath, String localFilePath) {
		URL urlfile = null;
		HttpURLConnection httpUrl = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		File f = new File(localFilePath);
		try {
			urlfile = new URL(remoteFilePath);
			httpUrl = (HttpURLConnection) urlfile.openConnection();
			httpUrl.connect();
			int size = httpUrl.getContentLength();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			bos = new BufferedOutputStream(new FileOutputStream(f));
			int len = 2048;
			byte[] b = new byte[len];
			while ((len = bis.read(b)) != -1) {
				bos.write(b, 0, len);
			}
			bos.flush();
			bis.close();
			httpUrl.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				bis.close();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void startDialog(String dlgName, UForm sForm) {
		try {
			UTemplate temp = (UTemplate) UFactory.getModelSession().getTemplate(UConstants.MAPKEY_DIALOG, dlgName);
			UDialogPanel item = (UDialogPanel) temp.newComponent();
			if (item != null) {
				item.setOkDisplay(false);
				item.setCancelDisplay(false);
				item.SetOwner(null);
				item.setComponentName(dlgName);
				item.setTemplate(temp);
				item.setDialogForm(sForm);
				item.init();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void openDocment(byte[] data, String fileName) {
		if (data == null) {
			messageBoxInfo("数据不存在，不能打开！");
			return;
		}
		if (fileName == null) {
			messageBoxInfo("文件类型错，不能打开！");
			return;
		}
		int index = fileName.lastIndexOf('.');
		if (index < 0)
			return;
		String str = fileName.substring(index + 1, fileName.length()).toUpperCase();
		if (str.equals("PDF")) {
			UPdfViewDataForm pForm = new UPdfViewDataForm();
			pForm.setData(data);
			USinglePdfViewDataForm sForm = new USinglePdfViewDataForm();
			sForm.setData(pForm);
			String dlgName = "uimsPdfPrintPreviewDialog";
			startDialog(dlgName, sForm);
		} else if (str.equals("MP4")) {
			UVideoPlayFrame.play(data);
		} else if (str.equals("WAV")) {
			UAudioPlayer.audioPlay(data);
		} else {
			try {
				File file = new File("c:\\" + fileName);
				OutputStream out = new FileOutputStream(file);
				out.write(data);
				out.close();
				if (str.equals("mp3") || str.equals("mov") || str.equals("3gp")) {
					VideoPlayer player = new VideoPlayer(file);
				} else
					Desktop.getDesktop().open(file);
			} catch (Exception e) {
				messageBoxInfo("文件类型错，不能打开！");
				return;
			}
		}
	}

	public static BufferedImage rotateImage(final BufferedImage bufferedimage, final int degree) {
		int w = bufferedimage.getWidth();
		int h = bufferedimage.getHeight();
		BufferedImage img;
		img = new BufferedImage(h, w, bufferedimage.getType());
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				if (degree == -90) {
					img.setRGB(h - 1 - j, i, bufferedimage.getRGB(i, j));
				} else if (degree == 90) {
					img.setRGB(j, w - 1 - i, bufferedimage.getRGB(i, j));
				}
			}
		}
		return img;
	}

	public static BufferedImage getImageBufferOfAttachFile(Integer attachId) {
		if (attachId == null)
			return null;
		BufferedImage img = null;
		RmiRequest request = new RmiRequest();
		request.add("attachId", attachId + "");
		request.add("isFileDataInDB", UimsFactory.getClientMainI().isFileDataInDB());
		request.setCmd("attachmentDownloadAttachmentFile");
		RmiResponse respond = requestProcesser(request);
		if (null == respond.getErrorMsg()) {
			byte[] b = (byte[]) respond.get(RmiKeyConstants.KEY_BYTES);
			if (b != null) {
				try {
					img = ImageIO.read(new ByteArrayInputStream(b));
				} catch (Exception e) {
				}
			}
		}
		return img;
	}

	public static byte[] getDataOfAttachFile(Integer attachId) {
		if (attachId == null)
			return null;
		RmiRequest request = new RmiRequest();
		request.add("attachId", attachId + "");
		request.add("isFileDataInDB", UimsFactory.getClientMainI().isFileDataInDB());
		request.setCmd("attachmentDownloadAttachmentFile");
		RmiResponse respond = requestProcesser(request);
		if (null == respond.getErrorMsg()) {
			return (byte[]) respond.get(RmiKeyConstants.KEY_BYTES);
		}
		return null;
	}

	public static byte[] getLocalFileData(File file) {
		int len = (int) file.length();
		byte data[] = new byte[len];
		try {
			FileInputStream in = new FileInputStream(file);
			in.read(data);
			in.close();
			return data;
		} catch (Exception e) {
			return null;
		}
	}

	public static Integer uploadAttachFileFromData(byte data[], Integer attachId, String attachType, Integer ownerId,
			String fileName, String folderName) {
		RmiRequest request = new RmiRequest();
		String cmd = null;
		if (attachId != null) {
			request.add("attachId", attachId);
			request.setCmd("attachmentReplaceAttachmentFileOfAttachId");
		} else {
			request.add("attachType", attachType);
			request.add("ownerId", ownerId);
			request.add("fileName", fileName);
			request.add("folderName", folderName);
			request.add("isFileDataInDB", UimsFactory.getClientMainI().isFileDataInDB());
			request.setCmd("attachmentUpLoadAttachmentFile");
		}
		request.add("fileData", data);
		RmiResponse respond = requestProcesser(request);
		if (respond.getErrorMsg() != null) {
			UimsUtils.messageBoxInfo(respond.getErrorMsg());
			return null;
		} else {
			return (Integer) respond.get("attachId");
		}
	}

	public static void openURl(final String url) {
		openURl(url, false);
	}

	public static void openURl(final String url, final boolean isUrlDisplay) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SimpleSwingBrowser browser = new SimpleSwingBrowser(isUrlDisplay);
				browser.setVisible(true);
				browser.loadURL(url);
			}
		});
	}

	public static void openBsApplication() {
		String url = UimsFactory.getClientMainI().getWebSeverUrl();
		String perNum = UimsUtils.userTokenClientSide.getLoginName();
		String pas = "[perNum:" + perNum + "]";
		pas = new String(Base64.encode(pas.getBytes()));
		url += "?token=clientRequestUser&systemParas=" + pas;
		final String urlf = url;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SimpleSwingBrowser browser = new SimpleSwingBrowser(false);
				browser.setVisible(true);
				browser.loadURL(urlf);
			}
		});
	}

	public static void openAttachHtml(Integer attachId) {
		String webServer = UimsFactory.getClientMainI().getWebSeverUrl();
		String perNum = UimsUtils.userTokenClientSide.getLoginName();
		String requestCmd = "/attachment/page_downloadHtml.do?attachId=" + attachId;
		String pas = "[perNum:" + perNum + "|requestCmd:" + requestCmd + "]";
		pas = new String(Base64.encode(pas.getBytes()));
		String url = webServer + "/login?token=clientRequestCommand&systemParas=" + pas;
		final String urlf = url;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SimpleSwingBrowser browser = new SimpleSwingBrowser(false);
				browser.setVisible(true);
				browser.loadURL(urlf);
			}
		});
	}

	public static void openBsRequest(String requestCmd) {
		String webServer = UimsFactory.getClientMainI().getWebSeverUrl();
		String perNum = UimsUtils.userTokenClientSide.getLoginName();
		String pas = "[perNum:" + perNum + "|requestCmd:" + requestCmd + "]";
		pas = new String(Base64.encode(pas.getBytes()));
		String url = webServer + "/login?token=clientRequestCommand&systemParas=" + pas;
		final String urlf = url;
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				SimpleSwingBrowser browser = new SimpleSwingBrowser(false);
				browser.setVisible(true);
				browser.loadURL(urlf);
			}
		});
	}

	public static RmiResponse requestProcesser(RmiRequest request) {
		return requestProcesser(null, request);
	}

	public static RmiResponse requestProcesser(String appName, RmiRequest request) {
		RmiResponse response = null;
		Logger logger = null;
		Cursor cursor = null;
		UClientFrameI f = null;
		ClientMainI c = UimsFactory.getClientMainI();
		if (c != null) {
			Boolean isCursor = (Boolean) request.get("isCursor");
			f = c.getUClientFrame();
			if (f != null && (isCursor == null || isCursor.booleanValue())) {
				cursor = f.getCursor();
				f.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			}
			if (c.requestIsLog())
				logger = c.getLogger();
			request.add("userTokenClientSide", userTokenClientSide);
			request.add(RmiKeyConstants.KEY_ENGLISH_VERSION, c.isEnglishVersion());
			c.addObjectSystemRequestAttribute(request);
		}
		Date ds, de;
		ds = new Date();
		response = RmiClientRequest.getClientRequest().request(request);
		if (logger != null) {
			de = new Date();
			String info = request.getCmd();
			if (UimsUtils.userTokenClientSide != null) {
				info += "," + UimsUtils.userTokenClientSide.getLoginName();
			}
			info += "," + ds + "," + (de.getTime() - ds.getTime());
			if(response.getErrorMsg() != null) {
				info += ";error:" + response.getErrorMsg();
			}
			logger.info(info);
		}
		if (cursor != null && f != null) {
			f.setCursor(cursor);
		}
		return response;
	}
}

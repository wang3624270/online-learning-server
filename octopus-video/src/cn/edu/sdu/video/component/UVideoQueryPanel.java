package cn.edu.sdu.video.component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.common.util.DateTimeTool;
import cn.edu.sdu.commoncomponent.form.ExtendItemObject;
import cn.edu.sdu.commonplatform.util.BaeProTownUtil;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.component.combobox.UComboBox;
import cn.edu.sdu.uims.component.combobox.UComboBoxDate;
import cn.edu.sdu.uims.component.combobox.UComboBoxI;
import cn.edu.sdu.uims.component.complex.UQueryComplexBasePanel;
import cn.edu.sdu.uims.form.impl.CommonQueryBaseForm;
import cn.edu.sdu.uims.util.UimsUtils;
import cn.edu.sdu.video.form.VideoQueryForm;

public class UVideoQueryPanel extends UQueryComplexBasePanel {

	protected VideoQueryForm dataForm = null;

	protected JTextField nameTextField;
	protected JTextField titleTextField;
	
	protected UComboBoxI typeComboBox;
	protected UComboBoxDate startDateComboBox;
	protected UComboBoxDate endDateComboBox;
	protected UComboBoxI yearComboBox;
	protected UComboBoxI liveStateComboBox;
	
	protected JButton queryButton;
	protected JButton clearButton;
	protected JButton computeButton;
	protected JButton importButton;
	protected JButton exportButton;
	protected JLabel blankLabel;
	protected  JCheckBox isVisableCheckBox;

		
	private boolean isCanEvent = false;
	private boolean isRemoteCatalog = false;

	public  CommonQueryBaseForm getDataForm(){
		return dataForm;
	}
	public  void setDataForm(CommonQueryBaseForm form){
		dataForm = (VideoQueryForm)form;		
	}
	
	public  List<String> getDefaultVisibleNameList() {
		List visibleNameList = new ArrayList<String>();
		visibleNameList.add("name");
		visibleNameList.add("title");
		visibleNameList.add("type");
		visibleNameList.add("year");
		visibleNameList.add("isVisable");
		visibleNameList.add("startDate");
		visibleNameList.add("endDate");
		visibleNameList.add("liveState");
		visibleNameList.add("query");
		visibleNameList.add("clear");
		visibleNameList.add("compute");
		visibleNameList.add("import");
		visibleNameList.add("export");
		
		return visibleNameList;
	}

	public void setDefaultFormData(HashMap parameters,
			VideoQueryForm dataForm) {
		if (parameters == null)
			return;
		String str;
		
		str = (String) parameters.get("startDate");
		if (str != null && !str.equals("")) {
			dataForm.setStartDate(DateTimeTool.getDateByString(str));
		}
		str = (String) parameters.get("endDate");
		if (str != null && !str.equals("")) {
			dataForm.setEndDate(DateTimeTool.getDateByString(str));
		}
	}
	
	public  String getQueryNameCmd(HashMap parameters) {
		if (parameters == null || parameters.get("queryCmd") == null) {
			return "query";
		} else if (parameters.get("queryCmd").toString().equals(""))
			return null;
		else
			return parameters.get("queryCmd").toString();
	}

	public  String getClearNameCmd(HashMap parameters) {
		if (parameters == null || parameters.get("clearCmd") == null)
			return "clear";
		else
			return parameters.get("clearCmd").toString();
	}
	public  String getComputeNameCmd(HashMap parameters) {
		if (parameters == null || parameters.get("computeCmd") == null)
			return "compute";
		else
			return parameters.get("computeCmd").toString();
	}
	public  String getImportNameCmd(HashMap parameters) {
		if (parameters == null || parameters.get("importCmd") == null)
			return "import";
		else
			return parameters.get("importCmd").toString();
	}
	public  String getExportNameCmd(HashMap parameters) {
		if (parameters == null || parameters.get("exportCmd") == null)
			return "export";
		else
			return parameters.get("exportCmd").toString();
	}


	public  String getStartDateLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null
					|| parameters.get("startDateEnLabel") == null) {
				return  "startDate";
			} else if (parameters.get("startDateEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("startDateEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("startDateLabel") == null) {
				return  "开始日期";
			} else if (parameters.get("startDateLabel").toString().equals(""))
				return null;
			else
				return parameters.get("startDateLabel").toString();
		}
	}

	public  String getEndDateLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("endDateEnLabel") == null) {
				return "endDate";
			} else if (parameters.get("endDateEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("endDateEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("endDateLabel") == null) {
				return  "结束日期";
			} else if (parameters.get("endDateLabel").toString().equals(""))
				return null;
			else
				return parameters.get("endDateLabel").toString();
		}
	}
	
	public  String getLiveStateLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("liveStateEnLabel") == null) {
				return  "liveState";
			} else if (parameters.get("liveStateEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("liveStateEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("liveStateLabel") == null) {
				return  "状态";
			} else if (parameters.get("liveStateLabel").toString().equals(""))
				return null;
			else
				return parameters.get("liveStateLabel").toString();
		}
	}
	public  String getNameLabel(Boolean isEnglishiVersion,
			HashMap parameters) {		
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("nameEnLabel") == null) {
				return  "name";
			} else if (parameters.get("nameEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("nameEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("nameLabel") == null) {
				return  "名字";
			} else if (parameters.get("nameLabel").toString().equals(""))
				return null;
			else
				return parameters.get("nameLabel").toString();
		}
	}

	public  String getTitleLabel(Boolean isEnglishiVersion,
			HashMap parameters) {		
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("titleEnLabel") == null) {
				return  "title";
			} else if (parameters.get("titleEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("titleEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("titleLabel") == null) {
				return  "关键字";
			} else if (parameters.get("titleLabel").toString().equals(""))
				return null;
			else
				return parameters.get("titleLabel").toString();
		}
	}
	
	public  String getTypeLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("typeEnLabel") == null) {
				return  "type";
			} else if (parameters.get("typeEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("typeEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("typeLabel") == null) {
				return  "类型";
			} else if (parameters.get("typeLabel").toString().equals(""))
				return null;
			else
				return parameters.get("typeLabel").toString();
		}
	}

	public  String getYearLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("yearEnLabel") == null) {
				return  "year";
			} else if (parameters.get("yearEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("yearEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("yearLabel") == null) {
				return  "年";
			} else if (parameters.get("yearLabel").toString().equals(""))
				return null;
			else
				return parameters.get("yearLabel").toString();
		}
	}
	

	
	public  String getBlankNameLabel(HashMap parameters) {
		if (parameters == null || parameters.get("blankNameLabel") == null)
			return "      ";
		else
			return parameters.get("blankNameLabel").toString();
	}
	
	
	public  String getQueryNameLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("queryEnLabel") == null) {
				return  "query";
			} else if (parameters.get("queryEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("queryEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("queryLabel") == null) {
				return  "查询";
			} else if (parameters.get("queryLabel").toString().equals(""))
				return null;
			else
				return parameters.get("queryLabel").toString();
		}
	}

	public  String getClearNameLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("clearEnLabel") == null) {
				return  "clear";
			} else if (parameters.get("clearEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("clearEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("clearLabel") == null) {
				return  "清除";
			} else if (parameters.get("clearLabel").toString().equals(""))
				return null;
			else
				return parameters.get("clearLabel").toString();
		}
	}
	public  String getComputeNameLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("computeEnLabel") == null) {
				return "compute";
			} else if (parameters.get("computeEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("computeEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("computeLabel") == null) {
				return  "计算";
			} else if (parameters.get("computeLabel").toString().equals(""))
				return null;
			else
				return parameters.get("computeLabel").toString();
		}
	}

	public  Color getComputeColor(	HashMap parameters) {
		String colorName = null;
			if (parameters != null  && parameters.get("computeColor") != null) {
				colorName = parameters.get("computeColor").toString();
			}
			if("red".equals(colorName)) {
				return new Color(255,0,0);
			}
			return null;
	}
	
	public  String getImportNameLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("importEnLabel") == null) {
				return  "import";
			} else if (parameters.get("importEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("importEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("importLabel") == null) {
				return  "导入";
			} else if (parameters.get("importLabel").toString().equals(""))
				return null;
			else
				return parameters.get("importLabel").toString();
		}
	}

	public  String getIsVisableLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("isVisableEnLabel") == null) {
				return "isVisable";
			} else if (parameters.get("isVisableEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("isVisableEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("isVisableLabel") == null) {
				return "是否可见";
			} else if (parameters.get("isVisableLabel").toString().equals(""))
				return null;
			else
				return parameters.get("isVisableLabel").toString();
		}
	}

	public  String getExportNameLabel(Boolean isEnglishiVersion,
			HashMap parameters) {
		if (isEnglishiVersion) {
			if (parameters == null || parameters.get("exportEnLabel") == null) {
				return  "export";
			} else if (parameters.get("exportEnLabel").toString().equals(""))
				return null;
			else
				return parameters.get("exportEnLabel").toString();
		} else {
			if (parameters == null || parameters.get("exportLabel") == null) {
				return  "导出";
			} else if (parameters.get("exportLabel").toString().equals(""))
				return null;
			else
				return parameters.get("exportLabel").toString();
		}
	}
	
	
	@Override
	public void initContents() {
		super.initContents();
		visibleNameList = getVissibleNameList(parameters);
		getExtendNameList();
		initDataForm();
		getRoleControlActionMap();
		initContentComponent();
	}


	public VideoQueryForm getDefautDataForm() {
		return new VideoQueryForm();
	}

	public void setDefaultFormData() {
		setDefaultFormData(parameters, dataForm);
	}

	public void initContentComponent() {
		int h = 25;
		int i;
		String label;
		String comName;
		Iterator it;
		boolean isEnglishVersion = UimsFactory.getClientMainI().isEnglishVersion();
		this.setLayout(new FlowLayout(getLayoutType(parameters),1,0));
		ExtendItemObject obj;
		JLabel l;
		isCanEvent = false;
		initextendComponent(h);
		if (visibleNameList != null) {
			for (i = 0; i < visibleNameList.size(); i++) {
				comName = visibleNameList.get(i);
				l = null;
				if (addSelfComponent(comName, h))
					continue;
				if (comName.equals("type")) {
					label = getTypeLabel(isEnglishVersion,parameters);
					if (label != null) {
						l = new JLabel(label);
					}
					typeComboBox = getComboBox("type",getTypeList(),10,80,h);
					typeComboBox.setSelectedIndex(-1);
					addComponentToContainer(this, l,typeComboBox.getAWTComponent());
				}else if (comName.equals("year")) {
					label = getYearLabel(isEnglishVersion,parameters);
					if (label != null) {
						l = new JLabel(label);
					}
					yearComboBox = getComboBox("year",getYearList(),10,80,h);
					yearComboBox.setSelectedIndex(-1);
					addComponentToContainer(this, l,yearComboBox.getAWTComponent());
				}else if (comName.equals("startDate")) {
					label = getStartDateLabel(
							isEnglishVersion, parameters);
					if (label != null) {
						l = new JLabel(label);
					}
					startDateComboBox = new UComboBoxDate(
							UComboBoxDate.STYLE_CN_DATE1);
					startDateComboBox.setMaximumRowCount(20);
					startDateComboBox.setPreferredSize(new Dimension(100, h));
					startDateComboBox.addActionListener(this);
					addComponentToContainer(this, l,startDateComboBox);
					setComStatusAttribute(startDateComboBox, "startDate");
				} else if (comName.equals("endDate")) {
					label = getEndDateLabel(isEnglishVersion,
							parameters);
					if (label != null) {
						l = new JLabel(label);
					}
					endDateComboBox = new UComboBoxDate(
							UComboBoxDate.STYLE_CN_DATE1);
					endDateComboBox.setMaximumRowCount(20);
					endDateComboBox.setPreferredSize(new Dimension(100, h));
					endDateComboBox.addActionListener(this);
					addComponentToContainer(this, l,endDateComboBox);
					setComStatusAttribute(endDateComboBox, "endDate");
				}
				else if (comName.equals("startEndDate")) {
					label = getStartDateLabel(
							isEnglishVersion, parameters);
					if (label != null) {
						l = new JLabel(label);
					}
					startDateComboBox = new UComboBoxDate(
							UComboBoxDate.STYLE_CN_DATE1);
					startDateComboBox.setMaximumRowCount(20);
					startDateComboBox.setPreferredSize(new Dimension(100, h));
					startDateComboBox.addActionListener(this);
					setComStatusAttribute(startDateComboBox, "startDate");
					endDateComboBox = new UComboBoxDate(
							UComboBoxDate.STYLE_CN_DATE1);
					endDateComboBox.setMaximumRowCount(20);
					endDateComboBox.setPreferredSize(new Dimension(100, h));
					endDateComboBox.addActionListener(this);
					addComponentToContainer(this, l,startDateComboBox, endDateComboBox);
					setComStatusAttribute(endDateComboBox, "endDate");
				}else if (comName.equals("liveState")) {
					label = getLiveStateLabel(isEnglishVersion,parameters);
					if (label != null) {
						l = new JLabel(label);
					}
					liveStateComboBox = getComboBox("liveState",getLiveStateList(),10,70,h);
					liveStateComboBox.setSelectedIndex(-1);
					addComponentToContainer(this, l,liveStateComboBox.getAWTComponent());
				}else if (comName.equals("isVisable")) {
					isVisableCheckBox = new JCheckBox(
							getIsVisableLabel(
									isEnglishVersion, parameters));
					add(isVisableCheckBox);
					setComStatusAttribute(isVisableCheckBox, "isVisable");
					isVisableCheckBox.setSelected(true);
				} else if (comName.equals("name")) {
					label = getNameLabel(isEnglishVersion,parameters);
					if (label != null) {
						l = new JLabel(label);
					}
					nameTextField = new JTextField();
					nameTextField.setColumns(8);
					nameTextField.setPreferredSize(new Dimension(60, h));
					nameTextField.addActionListener(this);
					addComponentToContainer(this, l,nameTextField);
					setComStatusAttribute(nameTextField,"name");
				} else if (comName.equals("title")) {
					label = getTitleLabel(isEnglishVersion,parameters);
					if (label != null) {
						l = new JLabel(label);
					}
					titleTextField = new JTextField();
					titleTextField.setColumns(8);
					titleTextField.setPreferredSize(new Dimension(60, h));
					titleTextField.addActionListener(this);
					addComponentToContainer(this, l,titleTextField);
					setComStatusAttribute(titleTextField,"title");
				} else if (comName.equals("blank")) {
					label = getBlankNameLabel(parameters);
					l = new JLabel(label);
					add(l);
				}else if (comName.equals("query")) {
					queryButton = new JButton(
							getQueryNameLabel(
									isEnglishVersion, parameters));
					queryButton.setActionCommand(getQueryNameCmd(parameters));
					queryButton.addActionListener(this);
					add(queryButton);
					setComStatusAttribute(queryButton, "query");
				} else if (comName.equals("clear")) {
					clearButton = new JButton(
							getClearNameLabel(
									isEnglishVersion, parameters));
					clearButton.setActionCommand(getClearNameCmd(parameters));
					clearButton.addActionListener(this);
					add(clearButton);
					setComStatusAttribute(clearButton, "clear");
				} else if (comName.equals("compute")) {
					computeButton = new JButton(
							getComputeNameLabel(
									isEnglishVersion, parameters));
					computeButton.setActionCommand(getComputeNameCmd(parameters));
					computeButton.addActionListener(this);
					Color c = this.getComputeColor(parameters);
					if(c != null)
						computeButton.setForeground(c);
					add(computeButton);
					setComStatusAttribute(computeButton, "compute");
				}else if (comName.equals("import")) {
					importButton = new JButton(
							getImportNameLabel(
									isEnglishVersion, parameters));
					importButton.setActionCommand(getImportNameCmd(parameters));
					importButton.addActionListener(this);
					add(importButton);
					setComStatusAttribute(importButton, "import");
				}else if (comName.equals("export")) {
					exportButton = new JButton(
							getExportNameLabel(
									isEnglishVersion, parameters));
					exportButton.setActionCommand(getExportNameCmd(parameters));
					exportButton.addActionListener(this);
					add(exportButton);
					setComStatusAttribute(exportButton, "export");
				} else if (extendComboBoxMap != null
						&& extendComboBoxMap.get(comName) != null) {
					obj = extendComboBoxMap.get(comName);
					if (obj != null) {
						if (obj.label != null)
							l = new JLabel(obj.label);
						addComponentToContainer(this, l,(UComboBox) obj.com);
						setComStatusAttribute((UComboBox) obj.com, comName);
					}
				} else if (extendFieldMap != null
						&& extendFieldMap.get(comName) != null) {
					obj = extendFieldMap.get(comName);
					if (obj != null) {
						if (obj.label != null)
							l = new JLabel(obj.label);
						addComponentToContainer(this, l,(JTextField) obj.com);
						setComStatusAttribute((JTextField) obj.com, comName);
					}
				} else if (extendButtonMap != null
						&& extendButtonMap.get(comName) != null) {
					obj = extendButtonMap.get(comName);
					if (obj != null) {
						add((JButton) obj.com);
						setComStatusAttribute((JButton) obj.com, comName);
					}
				}
			}
		}
		isCanEvent = true;
}

	public void actionPerformed(ActionEvent e) {
		if(!isCanEvent)
			return;
		super.actionPerformed(e);
		String cmd = e.getActionCommand();
		if(e.getSource()==clearButton) {
			clearSelectItems();
		}
		if (processComponentChange(e)) {

		}
		if (e.getSource() == typeComboBox) {
			typeChanged();
		}else if (e.getSource() == yearComboBox) {
			yearChanged();
		}else if (actionEventCanSend && this.getUParent() != null && cmd != null) {
			ActionEvent e1 = new ActionEvent(this, e.getID(), cmd);
			this.getUParent().getEventAdaptor().actionPerformed(e1);
		}
	}


	public Integer getLiveStateByComboBox() {
		return changeStringToInteger(getSelectItemOfComboBox(liveStateComboBox));
	}

	public void setLiveStateToComboBox(Integer liveState) {
		setSelectItemOfComboBox(liveStateComboBox, liveState);
	}
	public String getTypeByComboBox() {
		return getSelectItemOfComboBox(typeComboBox);
	}

	public void setTypeToComboBox(String type) {
		setSelectItemOfComboBox(typeComboBox, type);
	}
	public String[] getTypesByComboBox() {
		return getSelectItemsOfComboBox(typeComboBox);
	}

	public void setTypesToComboBox(String[] types) {
		setSelectItemsOfComboBox(typeComboBox, types);
	}


	public String getYearByComboBox() {
		return getSelectItemOfComboBox(yearComboBox);
	}

	public void setYearToComboBox(String year) {
		setSelectItemOfComboBox(yearComboBox, year);
	}
	public String[] getYearsByComboBox() {
		return getSelectItemsOfComboBox(yearComboBox);
	}

	public void setYearsToComboBox(String[] years) {
		setSelectItemsOfComboBox(yearComboBox, years);
	}
	
	
	public  Integer changeStringToInteger(String str) {
		if (str == null || str.length() == 0)
			return null;
		else
			return new Integer(str);
	}
	public  Integer[] changeStringsToIntegers(String strs[]) {
		if (strs == null || strs.length == 0)
			return null;
		else {
			Integer is[] = new Integer[strs.length];
			{
				for (int i = 0; i < strs.length; i++) {
					if (strs[i] == null || strs[i].length() == 0)
						is[i] = null;
					else
						is[i] = new Integer(strs[i]);
				}
			}
			return is;
		}
	}
	
	
	public void setStartDateToComboBox(Date startDate ) {
		if(startDateComboBox==null || startDate==null || "".equals(startDate)){
			startDateComboBox.setData(startDate);
			return;
		}
		startDateComboBox.setData(startDate);
	}
	public void setEndDateToComboBox(Date endDate) {
		if(endDateComboBox==null || endDate==null || "".equals(endDate)){
			endDateComboBox.setData(endDate);
			return;
		}
		endDateComboBox.setData(endDate);
	}
	
	public String getNameByTextField() {
		if (nameTextField == null)
			return null;
		return nameTextField.getText();
	}

	public void setNameToTextField(String str) {
		if (nameTextField == null)
			return;
		if (str == null)
			nameTextField.setText("");
		else
			nameTextField.setText(str);
	}
	
	public String getTitleByTextField() {
		if (titleTextField == null)
			return null;
		return titleTextField.getText();
	}

	public void setTitleToTextField(String str) {
		if (titleTextField == null)
			return;
		if (str == null)
			titleTextField.setText("");
		else
			titleTextField.setText(str);
	}
	
	public void componentToForm() {
		dataForm.setName(this.getNameByTextField());
		dataForm.setTitle(this.getTitleByTextField());
		
		dataForm.setType(this.getTypeByComboBox());
		dataForm.setTypes(this.getTypesByComboBox());
		dataForm.setLiveState(this.getLiveStateByComboBox());
		dataForm.setYear(this.getYearByComboBox());
		dataForm.setYears(this.getYearsByComboBox());
		dataForm.setIsVisable(this.getIsVisableByCheckBox());

		try {
			if (startDateComboBox != null)
				dataForm.setStartDate(startDateComboBox.getSelectedDate());
			if (endDateComboBox != null)
				dataForm.setEndDate(endDateComboBox.getSelectedDate());
		} catch (Exception e) {

		}
		comboBoxToExtendData();
		textFieldToExtendData();

	}

	public void formToComponent() {
		setNameToTextField(dataForm.getName());
		setTitleToTextField(dataForm.getTitle());
		setTypeToComboBox(dataForm.getType());
		setTypesToComboBox(dataForm.getTypes());
		setLiveStateToComboBox(dataForm.getLiveState());
		setYearToComboBox(dataForm.getYear());
		setYearsToComboBox(dataForm.getYears());
		setIsVisableToCheckBox(dataForm.getIsVisable());

		if (startDateComboBox != null)
			startDateComboBox.setData(dataForm.getStartDate());
		if (endDateComboBox != null)
			endDateComboBox.setData(dataForm.getEndDate());
		setExtendDataToComboBox();
		setExtendDataToTextField();

	}

	public Component getCompoent(String comName) {
		ExtendItemObject obj;
		Component com = getSelfComponent(comName);
		if (com != null)
			return null;
		if (comName.equals("name")) {
			return this.nameTextField;
		}else if (comName.equals("title")) {
			return this.titleTextField;
		}else if (comName.equals("type")) {
			return typeComboBox.getAWTComponent();
		}else if (comName.equals("year")) {
			return yearComboBox.getAWTComponent();
		}else if (comName.equals("startDate")) {
			return startDateComboBox;
		}else if (comName.equals("endDate")) {
			return endDateComboBox;
		}else if (comName.equals("liveState")) {
			return liveStateComboBox.getAWTComponent();
		}if (comName.equals("isVisable")) {
			return isVisableCheckBox;
		} else if (comName.equals("query")) {
			return queryButton;
		} else if (comName.equals("clear")) {
			return clearButton;
		} else if (comName.equals("compute")) {
			return computeButton;
		} else if (comName.equals("import")) {
			return importButton;
		} else if (comName.equals("export")) {
			return exportButton;
		} else if (extendComboBoxMap != null
				&& extendComboBoxMap.get(comName) != null) {
			obj = extendComboBoxMap.get(comName);
			if (obj != null) {
				return (Component) obj.com;
			} else
				return null;
		} else if (extendFieldMap != null
				&& extendFieldMap.get(comName) != null) {
			obj = extendFieldMap.get(comName);
			if (obj != null) {
				return (Component) obj.com;
			} else
				return null;
		} else if (extendButtonMap != null
				&& extendButtonMap.get(comName) != null) {
			obj = extendButtonMap.get(comName);
			if (obj != null) {
				return (Component) obj.com;
			} else
				return null;
		}
		return null;
	}
	public void clearSelectItems(){
		initDataForm();
		formToComponent();
	}
	
	private List getCombBoxListByCmd(String cmd){
		RmiRequest request = new RmiRequest();
		request.setCmd(cmd);
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() == null) {
			return (List)respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return null;
	}
	
	public List  getLiveStateList(){
		return getDictionaryTypeCodeList("ZBZTLBM");
	}
	public void clear (){
		this.clearSelectItems();
	}
	public List  getTypeList(){
		return getDictionaryTypeCodeList("BFZYLXM");
	}
	public void typeChanged(){
		
	}
	
	public void yearChanged(){
		
	}
	
	public Boolean getIsVisableByCheckBox() {
		if (isVisableCheckBox == null)
			return false;
		if(isVisableCheckBox.isSelected()){
			return true;
		}else
			return false;
	}

	public void setIsVisableToCheckBox(Boolean b) {
		if (isVisableCheckBox == null)
			return;
		if (b == null)
			isVisableCheckBox.setSelected(false);
		else
			isVisableCheckBox.setSelected(b);
	}
	
}

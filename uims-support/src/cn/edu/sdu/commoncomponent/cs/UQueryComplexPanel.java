package cn.edu.sdu.commoncomponent.cs;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.common.pi.ClientDataDictionaryI;
import cn.edu.sdu.commoncomponent.form.CommonBaseDataQueryForm;
import cn.edu.sdu.commoncomponent.form.CommonQueryForm;
import cn.edu.sdu.commoncomponent.form.ExtendItemObject;
import cn.edu.sdu.commoncomponent.util.CommonQueryClientUtils;
import cn.edu.sdu.commoncomponent.util.UCommonQueryUtils;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.component.combobox.UComboBoxDate;
import cn.edu.sdu.uims.component.combobox.UComboBoxI;
import cn.edu.sdu.uims.component.complex.UQueryComplexBasePanel;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.form.impl.CommonQueryBaseForm;
import cn.edu.sdu.uims.util.DataProcessUtils;
import cn.edu.sdu.uims.util.UimsUtils;

public class UQueryComplexPanel extends UQueryComplexBasePanel {

	protected CommonQueryForm dataForm = null;
	public static final String XQDYLXM = "XQDYLXM";
	protected UComboBoxI areaComboBox;
	protected UComboBoxI campusComboBox;
	protected UComboBoxI facultyComboBox;
	protected UComboBoxI collegeTypeComboBox;
	protected UComboBoxI collegeComboBox;
	protected UComboBoxI college1ComboBox;
	protected UComboBoxI college2ComboBox;
	protected UComboBoxI processComboBox;
	protected UComboBoxI yearComboBox;
	protected UComboBoxI monthComboBox;
	protected UComboBoxI statusComboBox;
	protected UComboBoxI checkStatusComboBox;
	protected UComboBoxI customComboBox;
	protected UComboBoxDate startDateComboBox;
	protected UComboBoxDate queryDateComboBox;
	protected UComboBoxDate endDateComboBox;
	protected JTextField perNumTextField;
	protected JTextField perNameTextField;
	protected JTextField collegeNameTextField;
	protected JTextField inputNum0TextField;
	protected JTextField inputNum1TextField;
	protected JTextField prompt0TextField;
	protected JTextField prompt1TextField;
	protected  JCheckBox selectCheckBox;
	protected JButton queryButton;
	protected JButton computeButton;
	protected JButton clearButton;
	protected JButton exportButton;
	protected JButton button0;
	protected JButton button1;
	protected JButton button2;

	protected String areaNum = null;
	protected String campusNum = null;
	protected String collegeType = null;
	protected Integer collegeId = null;
	protected Integer collegeId1 = null;
	protected Integer collegeId2 = null;
	protected boolean classMark = false;

	protected String[] areaNums = null;
	protected String[] campusNums = null;
	protected Integer[] collegeIds = null;
	protected Integer[] collegeId1s = null;
	protected Integer[] collegeId2s = null;

	protected List customList;
	public  CommonQueryBaseForm getDataForm(){
		return dataForm;
	}
	public  void setDataForm(CommonQueryBaseForm form){
		dataForm = (CommonQueryForm)form;
	}
	public Integer getCollegeId(){
		if(collegeComboBox != null)
			return this.getCollegeIdByComboBox();
		else 
			return null;
	}

	@Override
	public void initContents() {
		super.initContents();
		visibleNameList = getVissibleNameList(parameters);
//		customList = getCustomList();
		doCmdMap = new HashMap();
		getExtendNameList();
		initDataForm();
		getRoleControlActionMap();
		initContentComponent();
//		initDefaultAction();
	}
	public void initDefaultAction(){
		if(this.collegeComboBox == null )
			this.collegeChanged();
	}

	public void setDefaultFormData() {
		UCommonQueryUtils.setDefaultFormData(parameters, dataForm);
	}

	public void initContentComponent() {
		int h = 25;
		int i;
		String label;
		String comName;
		ExtendItemObject obj;
		boolean isEnglishVersion = UimsFactory.getClientMainI().isEnglishVersion();
		this.setLayout(new FlowLayout(getLayoutType(parameters),1,0));
		initextendComponent(h);
		JLabel l;
		if (visibleNameList != null) {
			for (i = 0; i < visibleNameList.size(); i++) {
				comName = visibleNameList.get(i);
				l = null;
				if (addSelfComponent(comName, h))
					continue;
				if (comName.equals("area")) {
					label = UCommonQueryUtils.getAreaLael(isEnglishVersion,
							parameters);
					if (label != null) {
						l = new JLabel(label);
					}
					areaComboBox = getComboBox("area",getAreaList(),10,80,h);
					addComponentToContainer(this, l,areaComboBox.getAWTComponent());
				} else if (comName.equals("campus")) {
					label = UCommonQueryUtils.getCampusLael(isEnglishVersion,
							parameters);
					if (label != null) {
						l = new JLabel(label);
					}
					campusComboBox = getComboBox("campus",getCampusList(),10,100,h);
					addComponentToContainer(this, l,campusComboBox.getAWTComponent());
				} else if (comName.equals("collegeType")) {
					label = UCommonQueryUtils.getCollegeTypeLable(isEnglishVersion,
							parameters);
					if (label != null) {
						l = new JLabel(label);
					}
					collegeTypeComboBox = getComboBox("collegeType",getCollegeTypeList(),20,150,h);
					addComponentToContainer(this, l,collegeTypeComboBox.getAWTComponent());
				} else if (comName.equals("process")) {
					if (processComboBox == null) {
						label = UCommonQueryUtils.getProcessLable(
								isEnglishVersion, parameters);
						if (label != null) {
							l = new JLabel(label);
						}
					}
					processComboBox = getComboBox("process",getProcessList(),20,150,h);
					addComponentToContainer(this, l,processComboBox.getAWTComponent());
				} else if (comName.equals("college")) {
					classMark = true;
					if (collegeComboBox == null) {
						label = UCommonQueryUtils.getCollegeLable(
								isEnglishVersion, parameters);
						if (label != null) {
							l = new JLabel(label);
						}
					}
					collegeComboBox = getComboBox("college",getCollegeList(),20,150,h);
					addComponentToContainer(this, l,collegeComboBox.getAWTComponent());
				} else if (comName.equals("college1")) {
					classMark = true;
					if (college1ComboBox == null) {
						label = UCommonQueryUtils.getCollege1Lable(
								isEnglishVersion, parameters);
						if (label != null) {
							l = new JLabel(label);
						}
					}
					college1ComboBox = getComboBox("college1",getCollege1List(),20,150,h);
					addComponentToContainer(this, l,college1ComboBox.getAWTComponent());
				} else if (comName.equals("college2")) {
					classMark = true;
					if (college2ComboBox == null) {
						label = UCommonQueryUtils.getCollege2Lable(
								isEnglishVersion, parameters);
						if (label != null) {
							l = new JLabel(label);
						}
					}
					college2ComboBox = getComboBox("college2",getCollege2List(),20,150,h);
					addComponentToContainer(this, l,college2ComboBox.getAWTComponent());
				} else if (comName.equals("faculty")) {
					label = UCommonQueryUtils.getFacultyLael(isEnglishVersion,
							parameters);
					if (label != null) {
						l = new JLabel(label);
					}
					facultyComboBox = getComboBox("faculty",getFacultyList(),20,150,h);
					addComponentToContainer(this, l,facultyComboBox.getAWTComponent());
				} else if (comName.equals("year")) {
					label = UCommonQueryUtils.getYearLabel(isEnglishVersion,
							parameters);
					if (label != null) {
						l = new JLabel(label);
					}
					yearComboBox = getComboBox("year",getYearList(),10,80,h);
					addComponentToContainer(this, l,yearComboBox.getAWTComponent());
				} else if (comName.equals("month")) {
					label = UCommonQueryUtils.getMonthLabel(isEnglishVersion,
							parameters);
					if (label != null) {
						l = new JLabel(label);
					}
					monthComboBox = getComboBox("month",getMonthList(),10,80,h);
					addComponentToContainer(this, l,monthComboBox.getAWTComponent());
					setComStatusAttribute(monthComboBox.getAWTComponent(), "month");
				} else if (comName.equals("status")) {
					label = UCommonQueryUtils.getStatusLabel(isEnglishVersion,
							parameters);
					if (label != null) {
						l = new JLabel(label);
					}
					statusComboBox = getComboBox("status",getStatusList(),20,100,h);
					addComponentToContainer(this, l,statusComboBox.getAWTComponent());
				} else if (comName.equals("checkStatus")) {
					label = UCommonQueryUtils.getCheckStatusLabel(isEnglishVersion,
							parameters);
					if (label != null) {
						l = new JLabel(label);
					}
					checkStatusComboBox = getComboBox("checkStatus",getCheckStatusList(),20,100,h);
					addComponentToContainer(this, l,checkStatusComboBox.getAWTComponent());
				} else if (comName.equals("custom") && customList != null) {
					label = UCommonQueryUtils.getCustomLabel(isEnglishVersion,
							parameters);
					if (label != null) {
						l = new JLabel(label);
					}
					customComboBox = getComboBox("custom",getCustomList(),10,100,h);
					addComponentToContainer(this, l,customComboBox.getAWTComponent());
				} else if (comName.equals("startDate")) {
					label = UCommonQueryUtils.getStartDateLabel(
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
				} else if (comName.equals("queryDate")) {
					label = UCommonQueryUtils.getQueryDateLabel(
							isEnglishVersion, parameters);
					if (label != null) {
						l = new JLabel(label);
					}
					queryDateComboBox = new UComboBoxDate(
							UComboBoxDate.STYLE_CN_DATE1);
					queryDateComboBox.setMaximumRowCount(20);
					queryDateComboBox.setPreferredSize(new Dimension(100, h));
					queryDateComboBox.addActionListener(this);
					addComponentToContainer(this, l,queryDateComboBox);
					setComStatusAttribute(queryDateComboBox, "queryDate");
				} else if (comName.equals("endDate")) {
					label = UCommonQueryUtils.getEndDateLabel(isEnglishVersion,
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
					label = UCommonQueryUtils.getStartDateLabel(
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
				}else if (comName.equals("perNum")) {
					label = UCommonQueryUtils.getPerNumLabel(isEnglishVersion,parameters);
					if (label != null) {
						l = new JLabel(label);
					}
					perNumTextField = new JTextField();
					perNumTextField.setColumns(10);
					perNumTextField.setPreferredSize(new Dimension(100, h));
					perNumTextField.addActionListener(this);
					doCmdMap.put(perNumTextField,"query");
					addComponentToContainer(this, l,perNumTextField);
					setComStatusAttribute(perNumTextField,"perNum");
				} else if (comName.equals("perName")) {
					label = UCommonQueryUtils.getPerNameLabel(isEnglishVersion,parameters);
					if (label != null) {
						l = new JLabel(label);
					}
					perNameTextField = new JTextField();
					perNameTextField.setColumns(8);
					perNameTextField.setPreferredSize(new Dimension(60, h));
					perNameTextField.addActionListener(this);
					doCmdMap.put(perNameTextField,"query");
					addComponentToContainer(this, l,perNameTextField);
					setComStatusAttribute(perNameTextField,"perName");
				} else if (comName.equals("collegeName")) {
					label = UCommonQueryUtils.getCollegeNameLabel(isEnglishVersion,parameters);
					if (label != null) {
						l = new JLabel(label);
					}
					collegeNameTextField = new JTextField();
					collegeNameTextField.setColumns(8);
					collegeNameTextField.setPreferredSize(new Dimension(60, h));
					collegeNameTextField.addActionListener(this);
					addComponentToContainer(this, l,collegeNameTextField);
					setComStatusAttribute(collegeNameTextField,"collegeName");
				}else if (comName.equals("inputNum0")) {
					label = UCommonQueryUtils.getInputNum0Label(isEnglishVersion,parameters);
					if (label != null) {
						l = new JLabel(label);
					}
					inputNum0TextField = new JTextField();
					inputNum0TextField.setColumns(10);
					inputNum0TextField.setPreferredSize(new Dimension(100, h));
					inputNum0TextField.addActionListener(this);
					addComponentToContainer(this, l,inputNum0TextField);
					setComStatusAttribute(inputNum0TextField,"inputNum0");
				}else if (comName.equals("inputNum1")) {
					label = UCommonQueryUtils.getInputNum1Label(isEnglishVersion,parameters);
					if (label != null) {
						l = new JLabel(label);
					}
					inputNum1TextField = new JTextField();
					inputNum1TextField.setColumns(10);
					inputNum1TextField.setPreferredSize(new Dimension(100, h));
					inputNum1TextField.addActionListener(this);
					addComponentToContainer(this, l,inputNum1TextField);
					setComStatusAttribute(inputNum1TextField,"inputNum1");
				}else if (comName.equals("prompt0")) {
					label = UCommonQueryUtils.getPrompt0Label(isEnglishVersion,parameters);
					if (label != null) {
						l = new JLabel(label);
					}
					prompt0TextField = new JTextField();
					prompt0TextField.setColumns(10);
					prompt0TextField.setPreferredSize(new Dimension(100, h));
					addComponentToContainer(this, l,prompt0TextField);
					setComStatusAttribute(prompt0TextField,"prompt0");
					prompt0TextField.setEditable(false);
				}else if (comName.equals("prompt1")) {
					label = UCommonQueryUtils.getPrompt1Label(isEnglishVersion,parameters);
					if (label != null) {
						l = new JLabel(label);
					}
					prompt1TextField = new JTextField();
					prompt1TextField.setColumns(10);
					prompt1TextField.setPreferredSize(new Dimension(100, h));
					addComponentToContainer(this, l,prompt1TextField);
					setComStatusAttribute(prompt1TextField,"prompt1");
					prompt1TextField.setEditable(false);
				}else if (comName.equals("select")) {
					selectCheckBox = new JCheckBox(
							UCommonQueryUtils.getSelectLabel(
									isEnglishVersion, parameters));
					add(selectCheckBox);
					setComStatusAttribute(selectCheckBox, "select");
				}else if (comName.equals("button0")) {
					button0 = new JButton(
							UCommonQueryUtils.getButton0NameLabel(
									isEnglishVersion, parameters));
					button0.setActionCommand(UCommonQueryUtils
							.getButton0NameCmd(parameters));
					button0.addActionListener(this);
					add(button0);
					setComStatusAttribute(button0, "button0");
				}else if (comName.equals("button1")) {
					button1 = new JButton(
							UCommonQueryUtils.getButton1NameLabel(
									isEnglishVersion, parameters));
					button1.setActionCommand(UCommonQueryUtils
							.getButton1NameCmd(parameters));
					button1.addActionListener(this);
					add(button1);
					setComStatusAttribute(button1, "button1");
				}else if (comName.equals("button2")) {
					button2 = new JButton(
							UCommonQueryUtils.getButton2NameLabel(
									isEnglishVersion, parameters));
					button2.setActionCommand(UCommonQueryUtils
							.getButton2NameCmd(parameters));
					button2.addActionListener(this);
					add(button2);
					setComStatusAttribute(button2, "button2");
				}else if (comName.equals("query")) {
					queryButton = new JButton(
							UCommonQueryUtils.getQueryNameLabel(
									isEnglishVersion, parameters));
					queryButton.setActionCommand(UCommonQueryUtils
							.getQueryNameCmd(parameters));
					queryButton.addActionListener(this);
					add(queryButton);
					setComStatusAttribute(queryButton, "query");
				} else if (comName.equals("export")) {
					exportButton = new JButton(
							UCommonQueryUtils.getExportNameLabel(
									isEnglishVersion, parameters));
					exportButton.setActionCommand(UCommonQueryUtils
							.getExportNameCmd(parameters));
					exportButton.addActionListener(this);
					add(exportButton);
					setComStatusAttribute(exportButton, "export");
				}else if (comName.equals("compute")) {
					computeButton = new JButton(
							UCommonQueryUtils.getComputeNameLabel(
									isEnglishVersion, parameters));
					computeButton.setActionCommand(UCommonQueryUtils
							.getComputeNameCmd(parameters));
					computeButton.addActionListener(this);
					add(computeButton);
					setComStatusAttribute(computeButton, "compute");
				} else if (comName.equals("clear")) {
					clearButton = new JButton(
							UCommonQueryUtils.getClearNameLabel(
									isEnglishVersion, parameters));
					clearButton.setActionCommand(UCommonQueryUtils
							.getClearNameCmd(parameters));
					clearButton.addActionListener(this);
					add(clearButton);
					setComStatusAttribute(clearButton, "clear");
				} else if (extendComboBoxMap != null
						&& extendComboBoxMap.get(comName) != null) {
					obj = extendComboBoxMap.get(comName);
					if (obj != null) {
						if (obj.label != null)
							l = new JLabel(obj.label);
						addComponentToContainer(this, l,((UComboBoxI) obj.com).getAWTComponent());
						setComStatusAttribute(((UComboBoxI) obj.com).getAWTComponent(), comName);
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
	}


	public List getDictionaryTypeCodeList(String... typecode) {
		ClientDataDictionaryI util = UimsFactory.getClientDataDictionaryI();
		if (util == null)
			return null;
		List list = (ArrayList) util.getComboxListByCode(typecode);
		return list;
	}

	public List getAreaList() {
		return getDictionaryTypeCodeList(XQDYLXM);
	}

	public List getCampusList() {
		RmiRequest request = new RmiRequest();
		RmiResponse respond = null;
		request.add("bType", UCommonQueryUtils.getBType(parameters));
		request.add("areaNum", areaNum);
		request.add("areaNums", areaNums);
		request.setCmd("commonBaseDataQueryRequestCampusList");
		respond = UimsUtils.requestProcesser(request);
		List list = null;
		if (respond.getErrorMsg() == null) {
			list = (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return list;
	}

	public List getFacultyList() {
		RmiRequest request = new RmiRequest();
		RmiResponse respond = null;
		request.add("bType", UCommonQueryUtils.getBType(parameters));
		request.setCmd("commonBaseDataQueryRequestFacultyList");
		respond = UimsUtils.requestProcesser(request);
		List list = null;
		if (respond.getErrorMsg() == null) {
			list = (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return list;
	}

	public List getCollegeTypeList() {
		String dict = UCommonQueryUtils.getCollegeTypeDict(parameters);
		return getDictionaryTypeCodeList(dict);
	}
	public List getCollegeList() {
		RmiRequest request = new RmiRequest();
		RmiResponse respond = null;
		if (dataForm == null) {
			dataForm = new CommonQueryForm();
		}
		request.add("beanName",
				UCommonQueryUtils.getBaseCollegeBeanName(parameters));
		request.add("bType", UCommonQueryUtils.getBType(parameters));
		request.add("campusNum", dataForm.getCampusNum());
		request.add("campusNums", dataForm.getCampusNums());
//		request.add("secondTypeCode", dataForm.getSecondTypeCode());
//		request.add("secondTypeCodes", dataForm.getSecondTypeCodes());
		request.add("collegeType", dataForm.getCollegeType());
//		request.add("collegeFilterMark",
//				UCommonQueryUtils.isCollegeFilterMark(parameters));
		request.setCmd("commonBaseDataQueryRequestCollegeList");
		respond = UimsUtils.requestProcesser(request);
		List list = null;
		if (respond.getErrorMsg() == null) {
			list = (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return list;
	}

	public List getCollege1List() {
		if(dataForm == null)
			return null;
		return CommonQueryClientUtils.getInstance().getSecondCollegeList( dataForm.getCollegeId());
	}
	public List getCollege2List() {
		if(dataForm == null)
			return null;
		return CommonQueryClientUtils.getInstance().getSecondCollegeList( dataForm.getCollegeId1());
	}

	public List getStatusList() {
		String dict =UCommonQueryUtils.getStatusDictionary(parameters);
		if(dict != null && dict.length() != 0)
			return this.getDictionaryTypeCodeList(dict);
		RmiRequest request = new RmiRequest();
		request.add("bType", UCommonQueryUtils.getBType(parameters));
		request.setCmd("commonBaseDataQueryRequestStatusList");
		request.add("beanName", UCommonQueryUtils.getStatusBeanName(parameters));
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() == null) {
			return (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return null;

	}
	public List getCheckStatusList() {
		ClientDataDictionaryI util = UimsFactory.getClientDataDictionaryI();
		if (util != null)
			return  util.getComboxListByCode("YBSHZTLXM");
		return null;

	}

	public void addEvents(UEventAttribute events[]) {
		int i;
		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_ACTION)) {
				actionEventCanSend = true;
			}
		}
	}

	public boolean processComponentChange(ActionEvent e) {
		return false;
	}
	
	
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		String cmd = e.getActionCommand();
		if(e.getSource()==clearButton) {
			clearSelectItems();
		}
		if (processComponentChange(e)) {

		} else if (e.getSource() == areaComboBox) {
			areaChanged();
		} else if (e.getSource() == campusComboBox) {
			campusChanged();
		} else if (e.getSource() == collegeTypeComboBox) {
			collegeTypeChanged();
		} else if (e.getSource() == collegeComboBox) {
			collegeChanged();
		} else if (e.getSource() == college1ComboBox) {
			college1Changed();
		} else if (e.getSource() == college2ComboBox) {
			college2Changed();
		} else if (e.getSource() == processComboBox) {
			processChanged();
		} else if (e.getSource() == facultyComboBox) {
			facultyChanged();
		} else if (e.getSource() == yearComboBox) {
			yearChanged();
		} else if (e.getSource() == monthComboBox) {
			monthChanged();
		} else if (e.getSource() == statusComboBox) {
			statusChanged();
		} else if (e.getSource() == checkStatusComboBox) {
			checkStatusChanged();
		} else if (e.getSource() == customComboBox) {
			customChanged();
		}
		String cmd1 = (String)doCmdMap.get(e.getSource());
		if(cmd1 != null) {
			cmd = cmd1;
		}
		if (actionEventCanSend && this.getUParent() != null && cmd != null) {
			ActionEvent e1 = new ActionEvent(this, e.getID(), cmd);
			this.getUParent().getEventAdaptor().actionPerformed(e1);
		}
	}

	public String getAreaNumByComboBox() {
		return UCommonQueryUtils.getSelectItemOfComboBox(areaComboBox);
	}

	public void setAreaNumToComboBox(String str) {
		UCommonQueryUtils.setSelectItemOfComboBox(areaComboBox, str);
	}

	public String[] getAreaNumsByComboBox() {
		return UCommonQueryUtils.getSelectItemsOfComboBox(areaComboBox);
	}

	public void setAreaNumsToComboBox(String[] str) {
		UCommonQueryUtils.setSelectItemsOfComboBox(areaComboBox, str);
	}

	public String getCampusNumByComboBox() {
		return UCommonQueryUtils.getSelectItemOfComboBox(campusComboBox);
	}

	public void setCampusNumToComboBox(String str) {
		UCommonQueryUtils.setSelectItemOfComboBox(campusComboBox, str);
	}

	public String[] getCampusNumsByComboBox() {
		return UCommonQueryUtils.getSelectItemsOfComboBox(campusComboBox);
	}

	public void setCampusNumsToComboBox(String str[]) {
		UCommonQueryUtils.setSelectItemsOfComboBox(campusComboBox, str);
	}
	
	public Integer getFacultyIdByComboBox() {
		return UCommonQueryUtils.changeStringToInteger(UCommonQueryUtils
				.getSelectItemOfComboBox(facultyComboBox));
	}

	public void setFacultyIdToComboBox(Integer id) {
		UCommonQueryUtils.setSelectItemOfComboBox(facultyComboBox, id);
	}

	public Integer[] getFacultyIdsByComboBox() {
		return UCommonQueryUtils.changeStringsToIntegers(UCommonQueryUtils
				.getSelectItemsOfComboBox(facultyComboBox));
	}

	public void setFacultyIdsToComboBox(Integer[] id) {
		UCommonQueryUtils.setSelectItemsOfComboBox(facultyComboBox, id);
	}
	
	public String getCollegeTypeByComboBox() {
		return UCommonQueryUtils.getSelectItemOfComboBox(collegeTypeComboBox);
	}

	public void setCollegeTypeToComboBox(String collegeType) {
		UCommonQueryUtils.setSelectItemOfComboBox(collegeComboBox, collegeType);
	}
	public String[] getCollegeTypesByComboBox() {
		return UCommonQueryUtils.getSelectItemsOfComboBox(collegeTypeComboBox);
	}

	public void setCollegeTypesToComboBox(String[] collegeType) {
		UCommonQueryUtils.setSelectItemsOfComboBox(collegeComboBox, collegeType);
	}

	public Integer getProcessIdByComboBox() {
		return UCommonQueryUtils.changeStringToInteger(UCommonQueryUtils
				.getSelectItemOfComboBox(processComboBox));
	}

	public void setProcessIdToComboBox(Integer id) {
		UCommonQueryUtils.setSelectItemOfComboBox(processComboBox, id);
	}

	public Integer[] getProcessIdsByComboBox() {
		return UCommonQueryUtils.changeStringsToIntegers(UCommonQueryUtils
				.getSelectItemsOfComboBox(processComboBox));
	}

	public void setProcessIdsToComboBox(Integer[] ids) {
		UCommonQueryUtils.setSelectItemsOfComboBox(processComboBox, ids);
	}

	public Integer getCollegeIdByComboBox() {
		return UCommonQueryUtils.changeStringToInteger(UCommonQueryUtils
				.getSelectItemOfComboBox(collegeComboBox));
	}

	public void setCollegeIdToComboBox(Integer id) {
		UCommonQueryUtils.setSelectItemOfComboBox(collegeComboBox, id);
	}

	public Integer[] getCollegeIdsByComboBox() {
		return UCommonQueryUtils.changeStringsToIntegers(UCommonQueryUtils
				.getSelectItemsOfComboBox(collegeComboBox));
	}

	public void setCollegeIdsToComboBox(Integer[] ids) {
		UCommonQueryUtils.setSelectItemsOfComboBox(collegeComboBox, ids);
	}

	public Integer getCollegeId1ByComboBox() {
		return UCommonQueryUtils.changeStringToInteger(UCommonQueryUtils
				.getSelectItemOfComboBox(college1ComboBox));
	}

	public void setCollegeId1ToComboBox(Integer id) {
		UCommonQueryUtils.setSelectItemOfComboBox(college1ComboBox, id);
	}

	public Integer[] getCollegeId1sByComboBox() {
		return UCommonQueryUtils.changeStringsToIntegers(UCommonQueryUtils
				.getSelectItemsOfComboBox(college1ComboBox));
	}

	public void setCollegeId1sToComboBox(Integer[] ids) {
		UCommonQueryUtils.setSelectItemsOfComboBox(college1ComboBox, ids);
	}

	public Integer getCollegeId2ByComboBox() {
		return UCommonQueryUtils.changeStringToInteger(UCommonQueryUtils
				.getSelectItemOfComboBox(college2ComboBox));
	}

	public void setCollegeId2ToComboBox(Integer id) {
		UCommonQueryUtils.setSelectItemOfComboBox(college2ComboBox, id);
	}

	public Integer[] getCollegeId2sByComboBox() {
		return UCommonQueryUtils.changeStringsToIntegers(UCommonQueryUtils
				.getSelectItemsOfComboBox(college2ComboBox));
	}

	public void setCollegeId2sToComboBox(Integer[] ids) {
		UCommonQueryUtils.setSelectItemsOfComboBox(college2ComboBox, ids);
	}
	
	public String getYearByComboBox() {
		return UCommonQueryUtils.getSelectItemOfComboBox(yearComboBox);
	}

	public void setYearToComboBox(String year) {
		UCommonQueryUtils.setSelectItemOfComboBox(yearComboBox, year);
	}

	public String[] getYearsByComboBox() {
		return UCommonQueryUtils.getSelectItemsOfComboBox(yearComboBox);
	}

	public void setYearsToComboBox(String year[]) {
		UCommonQueryUtils.setSelectItemsOfComboBox(yearComboBox, year);
	}
	
	public String getMonthByComboBox() {
		return UCommonQueryUtils.getSelectItemOfComboBox(monthComboBox);
	}

	public void setMonthToComboBox(String month) {
		UCommonQueryUtils.setSelectItemOfComboBox(monthComboBox, month);
	}

	public String[] getMonthsByComboBox() {
		return UCommonQueryUtils.getSelectItemsOfComboBox(monthComboBox);
	}

	public void setMonthsToComboBox(String month[]) {
		UCommonQueryUtils.setSelectItemsOfComboBox(monthComboBox, month);
	}

	
	public String getStatusByComboBox() {
		return UCommonQueryUtils.getSelectItemOfComboBox(statusComboBox);
	}

	public void setStatusToComboBox(String str) {
		UCommonQueryUtils.setSelectItemOfComboBox(statusComboBox, str);
	}

	public String[] getStatussByComboBox() {
		return UCommonQueryUtils.getSelectItemsOfComboBox(statusComboBox);
	}

	public void setStatussToComboBox(String str[]) {
		UCommonQueryUtils.setSelectItemsOfComboBox(statusComboBox, str);
	}

	public String getCheckStatusByComboBox() {
		return UCommonQueryUtils.getSelectItemOfComboBox(checkStatusComboBox);
	}

	public void setCheckStatusToComboBox(String str) {
		UCommonQueryUtils.setSelectItemOfComboBox(checkStatusComboBox, str);
	}

	public String[] getCheckStatussByComboBox() {
		return UCommonQueryUtils.getSelectItemsOfComboBox(checkStatusComboBox);
	}

	public void setCheckStatussToComboBox(String str[]) {
		UCommonQueryUtils.setSelectItemsOfComboBox(checkStatusComboBox, str);
	}

	public Integer getCustomIdByComboBox() {
		return UCommonQueryUtils.changeStringToInteger(UCommonQueryUtils
				.getSelectItemOfComboBox(customComboBox));
	}

	public void setCustomIdToComboBox(Integer id) {
		UCommonQueryUtils.setSelectItemOfComboBox(customComboBox, id);
	}

	public Integer[] getCustomIdsByComboBox() {
		return UCommonQueryUtils.changeStringsToIntegers(UCommonQueryUtils
				.getSelectItemsOfComboBox(customComboBox));
	}

	public void setCustomIdsToComboBox(Integer[] ids) {
		UCommonQueryUtils.setSelectItemOfComboBox(customComboBox, ids);
	}
	
	public String getPerNumByTextField() {
		if (perNumTextField == null)
			return null;
		return perNumTextField.getText();
	}

	public void setPerNumToTextField(String str) {
		if (perNumTextField == null)
			return;
		if (str == null)
			perNumTextField.setText("");
		else
			perNumTextField.setText(str);
	}

	public String getInputNum0ByTextField() {
		if (inputNum0TextField == null)
			return null;
		return inputNum0TextField.getText();
	}

	public void setInputNum0ToTextField(String str) {
		if (inputNum0TextField == null)
			return;
		if (str == null)
			inputNum0TextField.setText("");
		else
			inputNum0TextField.setText(str);
	}

	public String getInputNum1ByTextField() {
		if (inputNum1TextField == null)
			return null;
		return inputNum1TextField.getText();
	}

	public void setInputNum1ToTextField(String str) {
		if (inputNum1TextField == null)
			return;
		if (str == null)
			inputNum1TextField.setText("");
		else
			inputNum1TextField.setText(str);
	}

	public String getPerNameByTextField() {
		if (perNameTextField == null)
			return null;
		return perNameTextField.getText();
	}

	public void setPerNameToTextField(String str) {
		if (perNameTextField == null)
			return;
		if (str == null)
			perNameTextField.setText("");
		else
			perNameTextField.setText(str);
	}
	public String getCollegeNameByTextField() {
		if (collegeNameTextField == null)
			return null;
		return collegeNameTextField.getText();
	}

	public void setCollegeNameToTextField(String str) {
		if (collegeNameTextField == null)
			return;
		if (str == null)
			collegeNameTextField.setText("");
		else
			collegeNameTextField.setText(str);
	}

	public Boolean getSelectByCheckBox() {
		if (selectCheckBox == null)
			return false;
		if(selectCheckBox.isSelected()){
			return true;
		}else
			return false;
	}

	public void setSelectToCheckBox(Boolean b) {
		if (selectCheckBox == null)
			return;
		if (b == null)
			selectCheckBox.setSelected(false);
		else
			selectCheckBox.setSelected(b);
	}
	
	public void componentToForm() {
		dataForm.setAreaNum(this.getAreaNumByComboBox());
		dataForm.setCampusNum(this.getCampusNumByComboBox());
		dataForm.setCollegeType(this.getCollegeTypeByComboBox());
		dataForm.setProcessId(this.getProcessIdByComboBox());
		dataForm.setCollegeId(this.getCollegeId());
		dataForm.setCollegeId1(this.getCollegeId1ByComboBox());
		dataForm.setCollegeId2(this.getCollegeId2ByComboBox());
		dataForm.setYear(this.getYearByComboBox());
		dataForm.setMonth(this.getMonthByComboBox());
		dataForm.setStatus(this.getStatusByComboBox());
		dataForm.setCheckStatus(this.getCheckStatusByComboBox());
		dataForm.setCustomId(this.getCustomIdByComboBox());

		dataForm.setAreaNums(this.getAreaNumsByComboBox());
		dataForm.setCampusNums(this.getCampusNumsByComboBox());
		dataForm.setCollegeType(this.getCollegeTypeByComboBox());
		dataForm.setProcessIds(this.getProcessIdsByComboBox());
		dataForm.setCollegeIds(this.getCollegeIdsByComboBox());
		dataForm.setCollegeId1s(this.getCollegeId1sByComboBox());
		dataForm.setCollegeId2s(this.getCollegeId2sByComboBox());
		dataForm.setYears(this.getYearsByComboBox());
		dataForm.setMonths(this.getMonthsByComboBox());
		dataForm.setStatuss(this.getStatussByComboBox());
		dataForm.setCheckStatuss(this.getCheckStatussByComboBox());
		dataForm.setCustomIds(this.getCustomIdsByComboBox());

		dataForm.setPerName(this.getPerNameByTextField());
		dataForm.setPerNum(this.getPerNumByTextField()); 
		dataForm.setCollegeName(this.getCollegeNameByTextField()); 
		dataForm.setInputNum0(this.getInputNum0ByTextField()); 
		dataForm.setInputNum1(this.getInputNum1ByTextField());
		dataForm.setSelect(this.getSelectByCheckBox());
		
		try {
			if (queryDateComboBox != null)
				dataForm.setQueryDate(queryDateComboBox.getSelectedDate());
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
		setAreaNumToComboBox(dataForm.getAreaNum());
		setCampusNumToComboBox(dataForm.getCampusNum());
		setCollegeTypeToComboBox(dataForm.getCollegeType());
		setProcessIdToComboBox(dataForm.getProcessId());
		setCollegeIdToComboBox(dataForm.getCollegeId());
		setCollegeId1ToComboBox(dataForm.getCollegeId1());
		setCollegeId2ToComboBox(dataForm.getCollegeId2());
		setYearToComboBox(dataForm.getYear());
		setMonthToComboBox(dataForm.getMonth());
		setStatusToComboBox(dataForm.getStatus());
		setCheckStatusToComboBox(dataForm.getCheckStatus());
		setCustomIdToComboBox(dataForm.getCustomId());

		setAreaNumsToComboBox(dataForm.getAreaNums());
		setCampusNumsToComboBox(dataForm.getCampusNums());
		setCollegeTypeToComboBox(dataForm.getCollegeType());
		setProcessIdsToComboBox(dataForm.getProcessIds());
		setCollegeIdsToComboBox(dataForm.getCollegeIds());
		setCollegeId1sToComboBox(dataForm.getCollegeId1s());
		setCollegeId2sToComboBox(dataForm.getCollegeId2s());
		setYearsToComboBox(dataForm.getYears());
		setMonthsToComboBox(dataForm.getMonths());
		setStatussToComboBox(dataForm.getStatuss());
		setCheckStatussToComboBox(dataForm.getCheckStatuss());
		setCustomIdsToComboBox(dataForm.getCustomIds());
		
		setPerNameToTextField(dataForm.getPerName());
		setPerNumToTextField(dataForm.getPerNum());
		setCollegeNameToTextField(dataForm.getCollegeName());
		setInputNum0ToTextField(dataForm.getInputNum0());
		setInputNum1ToTextField(dataForm.getInputNum1());
		setSelectToCheckBox(dataForm.getSelect());
		
		if (queryDateComboBox != null)
			queryDateComboBox.setData(dataForm.getQueryDate());
		if (startDateComboBox != null)
			startDateComboBox.setData(dataForm.getStartDate());
		if (endDateComboBox != null)
			endDateComboBox.setData(dataForm.getEndDate());
		setExtendDataToComboBox();
		setExtendDataToTextField();

	}

	public void setExtendDataToComboBox() {
		if (extendComboBoxMap == null || dataForm == null)
			return;
		JComboBox f;
		Object o;
		ExtendItemObject obj;
		String name;
		Iterator it;
		try {
			it = extendComboBoxMap.keySet().iterator();
			while (it.hasNext()) {
				name = (String) it.next();
				obj = extendComboBoxMap.get(name);
				if (obj.getMethod != null) {
					o = obj.getMethod.invoke(dataForm);
				} else {
					o = dataForm.getAttributeData(obj.member);
				}
				UCommonQueryUtils.setSelectItemOfComboBox((UComboBoxI) obj.com,
						o);
				if (obj.getMethods != null) {
					o = obj.getMethods.invoke(dataForm);
				} else {
					o = dataForm.getAttributeData(obj.members);
				}
				UCommonQueryUtils.setSelectItemsOfComboBox((UComboBoxI) obj.com,
						o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void comboBoxToExtendData() {
		if (extendComboBoxMap == null || dataForm == null)
			return;
		JTextField f;
		Object o;
		ExtendItemObject obj;
		String name;
		Iterator it;
		try {
			it = extendComboBoxMap.keySet().iterator();
			while (it.hasNext()) {
				name = (String) it.next();
				obj = extendComboBoxMap.get(name);
				if (obj.setMethod == null) {
					dataForm.setAttributeData(obj.member, UCommonQueryUtils
							.getSelectItemOfComboBox((UComboBoxI) obj.com));
				} else {
					obj.setMethod.invoke(dataForm, UCommonQueryUtils
							.getSelectItemOfComboBox((UComboBoxI) obj.com));
				}
				if (obj.setMethods == null) {
					dataForm.setAttributeDatas(obj.members, UCommonQueryUtils
							.getSelectItemsOfComboBox((UComboBoxI) obj.com));
				} else {
					String g[] =UCommonQueryUtils
							.getSelectItemsOfComboBox((UComboBoxI) obj.com);
					obj.setMethods.invoke(dataForm, (Object)g);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setExtendDataToTextField() {
		String name;
		Iterator it;
		if (extendFieldMap == null || dataForm == null)
			return;
		JTextField f;
		Object o;
		ExtendItemObject obj;
		try {
			it = extendFieldMap.keySet().iterator();
			while (it.hasNext()) {
				name = (String) it.next();
				obj = extendFieldMap.get(name);
				if (obj.getMethod != null) {
					o = obj.getMethod.invoke(dataForm);
				} else {
					o = dataForm.getAttributeData(obj.member);
				}
				f = (JTextField) obj.com;
				if (o == null)
					f.setText("");
				else
					f.setText(o.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void textFieldToExtendData() {
		String name;
		Iterator it;
		if (extendFieldMap == null || dataForm == null)
			return;
		JTextField f;
		Object o;
		ExtendItemObject obj;
		try {
			it = extendFieldMap.keySet().iterator();
			while (it.hasNext()) {
				name = (String) it.next();
				obj = extendFieldMap.get(name);
				f = (JTextField) obj.com;
				if (obj.getMethod == null) {
					dataForm.setAttributeData(obj.member, f.getText());
				} else {
					o = DataProcessUtils.changeStringToObject(
							obj.getMethod.getReturnType(), f.getText());
					obj.setMethod.invoke(dataForm, o);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isQuestCampusComboBoxListData() {
		String str;
		str = dataForm.getAreaNum();
		if (str == null && areaNum != null || str != null && areaNum == null)
			return true;
		if (str != null && areaNum != null && !str.equals(areaNum))
			return true;
		String []strs = dataForm.getAreaNums();
		if (strs == null && areaNums != null || strs != null && areaNums == null)
			return true;
		if (strs != null && areaNums != null && !str.equals(areaNums))
			return true;
		return false;
	}

	public void updataCampusComboxBoxListData() {
		if (isQuestCampusComboBoxListData()) {
			areaNum = dataForm.getAreaNum();
			if (campusComboBox != null)
				campusComboBox.setAddedDatas(getCampusList());
		}

	}

	public boolean isQuestCollegeComboBoxListData() {
		String str;
		str = dataForm.getCampusNum();
		if (str == null && campusNum != null || str != null
				&& campusNum == null)
			return true;
		if (str != null && campusNum != null && !str.equals(campusNum))
			return true;
		if(!areEqual(dataForm.getCampusNums(),campusNums))
			return true;
		return false;
	}

	public void updataCollegeComboxBoxListData() {
		if (isQuestCollegeComboBoxListData()) {
			campusNum = dataForm.getCampusNum();
			if (collegeComboBox != null)
				collegeComboBox.setAddedDatas(getCollegeList());
		}
	}

	public void areaChanged() {
		dataForm.setAreaNum(this.getAreaNumByComboBox());
		updataCampusComboxBoxListData();
	}

	public void campusChanged() {
		dataForm.setCampusNum(this.getCampusNumByComboBox());
		dataForm.setCampusNums(this.getCampusNumsByComboBox());
		updataCollegeComboxBoxListData();
	}

	public void collegeTypeChanged() {
		dataForm.setCollegeType(this.getCollegeTypeByComboBox());
		dataForm.setCollegeTypes(this.getCollegeTypesByComboBox());
		updataCollegeComboxBoxListData();
	}

	public void processChanged() {
	}
	
	public void facultyChanged() {
		dataForm.setFacultyId(this.getFacultyIdByComboBox());
		dataForm.setFacultyIds(this.getFacultyIdsByComboBox());
		updataCollegeComboxBoxListData();
	}

	public void collegeChanged() {
	}
	public void college1Changed() {
	}
	public void college2Changed() {
	}

	public void setData(Object obj) {
		if (obj != null && obj instanceof CommonBaseDataQueryForm) {
			// dataForm = (CommonBaseDataQueryForm)obj;
			formToComponent();
		}
	}

	public Object getData() {
		componentToForm();
		return dataForm;
	}

	public void yearChanged() {
		UComponentI com = this.getUParent();
//		String comName = com.getComponentName();
		com = com.getSubComponent("scholarQueryPanel");
		if(com!=null){
			CommonBaseDataQueryForm form = (CommonBaseDataQueryForm) com.getData();
			String year = (String) form.getYear();
			RmiRequest req = new RmiRequest();
			req.add("year", year);
			req.setCmd("studentGetFreeScholarAllocationItemListOptionInfoListOfYear");//获得itemList
			RmiResponse res = UimsUtils.requestProcesser(req);
			if(res.getErrorMsg()==null){
				List list = (List) res.get(RmiKeyConstants.KEY_FORMLIST);
//				UQueryComplexPanel cp = (UQueryComplexPanel)this.getQueryPanel();
				JComboBox jcom = (JComboBox)this.getCompoent("itemId");
				jcom.removeAllItems();
				jcom.addItem(new ListOptionInfo("-1","请选择"));
				if(list!=null && list.size()>0){
					for(int i= 0;i <list.size();i++) {
						jcom.addItem(list.get(i));
					}
				}
				jcom.setEnabled(true);
			}
		}
	}
	
	public void monthChanged() {

	}

	public void statusChanged() {
	}
	public void checkStatusChanged() {
	}

	public void customChanged() {
	}

	public void updataExtendComboxAddedData(String comName, List list) {

		if (extendComboBoxMap == null)
			return;
		ExtendItemObject obj = extendComboBoxMap.get(comName);
		if (obj == null)
			return;
		UComboBoxI tf = (UComboBoxI) (obj.com);
		if (tf == null)
			return;
		tf.setAddedDatas(list);
	}

	public List getCustomList() {
		if(this.getUParent() == null)
			return null;
		String panelName = this.getUParent().getComponentName();
		RmiRequest request = new RmiRequest();
		request.setCmd("customqueryGetCustomIdListOptionInfoList");
		request.add("panelName", panelName);
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() == null) {
			return (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return null;

	}

	public Component getSelfComponent(String comName) {
		return null;
	}

	public Component getCompoent(String comName) {
		ExtendItemObject obj;
		Component com = getSelfComponent(comName);
		if (com != null)
			return null;
		if (comName.equals("area")) {
			return areaComboBox.getAWTComponent();
		}
		if (comName.equals("campus")) {
			return campusComboBox.getAWTComponent();
		}
		if (comName.equals("collegeType")) {
			return collegeTypeComboBox.getAWTComponent();
		}
		if (comName.equals("college")) {
			return collegeComboBox.getAWTComponent();
		}
		if (comName.equals("faculty")) {
			return facultyComboBox.getAWTComponent();
		}
		if (comName.equals("year")) {
			return yearComboBox.getAWTComponent();
		}
		if (comName.equals("month")) {
			return monthComboBox.getAWTComponent();
		}
		if (comName.equals("status")) {
			return statusComboBox.getAWTComponent();
		} else if (comName.equals("checkStatus")) {
			return checkStatusComboBox.getAWTComponent();
		} else if (comName.equals("custom")) {
			return customComboBox.getAWTComponent();
		}
		else if (comName.equals("queryDate")) {
			return queryDateComboBox;
		}
		else if (comName.equals("startDate")) {
			return startDateComboBox;
		}
		else if (comName.equals("endDate")) {
			return endDateComboBox;
		} else if (comName.equals("button0")) {
			return button0;
		} else if (comName.equals("button1")) {
			return button1;
		} else if (comName.equals("button2")) {
			return button2;
		} else if (comName.equals("query")) {
			return queryButton;
		} else if (comName.equals("export")) {
			return exportButton;
		} else if (comName.equals("compute")) {
			return computeButton;
		} else if (comName.equals("clear")) {
			return clearButton;
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
	public void updataPromptValue(String promptName, Object obj){
		String value ="";
		if(obj != null) {
			value = obj.toString();
		}
		if(promptName.equals("prompt0")) {
			this.prompt0TextField.setText(value);
		}else if(promptName.equals("prompt1")) {
			this.prompt1TextField.setText(value);
		}
	}
	public List getProcessList(){
		return CommonQueryClientUtils.getInstance().getProcessOptionList();
	}

}
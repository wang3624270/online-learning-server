package cn.edu.sdu.commoncomponent.cs;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.commoncomponent.form.CommonBaseDataQueryForm;
import cn.edu.sdu.commoncomponent.form.CommonQueryForm;
import cn.edu.sdu.commoncomponent.util.UCommonQueryUtils;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.component.combobox.UComboBox;
import cn.edu.sdu.uims.component.combobox.UComboBoxI;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.util.UimsUtils;

public class UCommonBaseDataQueryPanel extends UQueryComplexPanel {

	private UComboBoxI secondTypeLevel1ComboBox;
	private UComboBoxI secondTypeLevel2ComboBox;
	private UComboBoxI secondTypeLevel3ComboBox;
	private UComboBoxI secondTypeCodeComboBox;
	private UComboBoxI majorComboBox;	
	private UComboBoxI gradeComboBox;
	private UComboBoxI grade1ComboBox;
	private UComboBoxI classComboBox;
	private UComboBoxI loanStatusComboBox;
	private UComboBoxI loanStatusNameComboBox;
	private UComboBoxI groupComboBox;
	private UComboBoxI scholarTypeComboBox;
	private UComboBoxI termComboBox;
	private UComboBoxI studyStateComboBox;
	private UComboBoxI processStateComboBox;
	private UComboBoxI reviewModeComboBox;
	private UComboBoxI leaveCauseComboBox;
	private JTextField perEngNameTextField;
	private JTextField perEnglishFamilyNameTextField;
	private JTextField perEnglishGivenNameTextField;

	private JTextField perIdCardTextField;
	private JTextField mobilePhoneTextField;
	private UComboBoxI dataSourceComboBox;
	
	private JTextField examNoTextField;
	private JTextField courseNumTextField;
	private JTextField courseNameTextField;
	private UComboBoxI courseNoComboBox;
	private UComboBoxI teachTaskTypeComboBox;
	private UComboBoxI graDegreeDateComboBox;
	
	private JButton conditionQueryButton;


	public static final int MAJOR_CLASS_QUERY_MODE_GRADMS = 1;
	public static final int MAJOR_CLASS_QUERY_MODE_ZHJWXT = 2;
	public static final int MAJOR_CLASS_QUERY_MODE_SC = 3;

	private String secondType = null;
	private String secondTypes[] = null;
	private Integer majorId = null;
	private String grade = null;

	public int getDefaultQueryMode() {
		return UFactory.getModelSession().getEnvironmentTemplate().defautMajorClassMode;
	}
	public CommonQueryForm getDefautDataForm(){
		return new CommonBaseDataQueryForm();
	}
	public void setDefaultFormData(){
		super.setDefaultFormData();
		CommonBaseDataQueryForm f = getBaseDataForm();
		getBaseDataForm().setPerTypeCode(UCommonQueryUtils.getPerTypeCode(parameters));
		UCommonQueryUtils.setDefaultBaseDataFormData(parameters, f);
		if(parameters == null)
			return;
		String str = (String) parameters.get("defaultCollegeId");
		if(str != null) {
			if(str.equals("userCollegeId")) {
				f.setCollegeId(UimsFactory.getClientMainI().getManageCollegeId());
			}else {
				f.setCollegeId(new Integer(str));			
			}
		}
		
	}
	public CommonBaseDataQueryForm getBaseDataForm(){
		return (CommonBaseDataQueryForm)dataForm;
	}
	public Integer getCollegeId(){
		if(collegeComboBox != null)
			return this.getCollegeIdByComboBox();
		else 
			return getBaseDataForm().getCollegeId();
	}
	public boolean addSelfComponent(String comName, int h) {
		String label; 
		JLabel l = null;
		Boolean b = UimsFactory.getClientMainI().isEnglishVersion();
		 if (comName.equals("stuTypeLevel") || comName.equals("teaTypeLevel") || comName.equals("secondTypeLevel")) {
				classMark = true;
				label = UCommonQueryUtils.getSecondTypeLabel(b,parameters);
				if (label != null) {
					l = new JLabel(label);
				}
				secondTypeLevel1ComboBox = getComboBox("secondTypeLevel",null,20,80,h);

				secondTypeLevel2ComboBox = getComboBox("secondTypeLevel",null,20,80,h);

				secondTypeLevel3ComboBox = getComboBox("secondTypeLevel",null,20,80,h);
				addComponentToContainer(this, l,secondTypeLevel1ComboBox.getAWTComponent(),secondTypeLevel2ComboBox.getAWTComponent(),secondTypeLevel3ComboBox.getAWTComponent());
				return true;
			} else if (comName.equals("stuTypeCode") || comName.equals("teaTypeCode") || comName.equals("secondTypeCode")) {
				classMark = true;
				label = UCommonQueryUtils.getSecondTypeLabel(b,parameters);
				if (label != null) {
					l = new JLabel(label);
				}
				secondTypeCodeComboBox = getComboBox("secondTypeCode",getSecondTypeCodeList(),20,150,h);
				addComponentToContainer(this, l,secondTypeCodeComboBox.getAWTComponent());
				return true;
			} else if (comName.equals("major")) {
				classMark = true;
				label = UCommonQueryUtils.getMajorLabel(b,parameters);
				if (label != null) {
					l = new JLabel(label);
				}
				majorComboBox = getComboBox("major",getMajorList(),20,150,h);
				addComponentToContainer(this, l,majorComboBox.getAWTComponent());
				return true;
			} else if (comName.equals("grade")) {
				classMark = true;
				label = UCommonQueryUtils.getGradeLabel(b,parameters);
				if (label != null) {
					l = new JLabel(label);
				}
				gradeComboBox = getComboBox("grade",UCommonQueryUtils.getGradeList(),20,70,h);
				addComponentToContainer(this, l,gradeComboBox.getAWTComponent());
				return true;
			} else if (comName.equals("grade1")) {
				classMark = true;
				label = UCommonQueryUtils.getGrade1Label(b,parameters);
				if (label != null) {
					l = new JLabel(label);
				}
				grade1ComboBox = getComboBox("grade1",UCommonQueryUtils.getGradeList(),20,70,h);
				addComponentToContainer(this, l,grade1ComboBox.getAWTComponent());
				return true;
			} else if (comName.equals("class")) {
				label = UCommonQueryUtils.getClassLabel(b,parameters);
				if (label != null) {
					l = new JLabel(label);
				}
				classComboBox = getComboBox("class",getClassList(),20,100,h);
				addComponentToContainer(this, l,classComboBox.getAWTComponent());
				return true;
			} else if (comName.equals("loanStatus")) {
				label = UCommonQueryUtils.getLoanStatusLabel(b,parameters);
				if (label != null) {
					l = new JLabel(label);
				}
				loanStatusComboBox = getComboBox("loanStatus",getLoanStatusList(),20,200,h);
				addComponentToContainer(this, l,loanStatusComboBox.getAWTComponent());
				return true;
			}else if (comName.equals("loanStatusName")) {
				label = UCommonQueryUtils.getLoanStatusNameLabel(b,parameters);
				if (label != null) {
					l = new JLabel(label);
				}
				loanStatusNameComboBox = getComboBox("loanStatusName",getLoanStatusNameList(),8,80,h);
				addComponentToContainer(this, l,loanStatusNameComboBox.getAWTComponent());
				return true;
			}else if (comName.equals("group")) {
				label = UCommonQueryUtils.getGroupLabel(b,parameters);
				if (label != null) {
					l = new JLabel(label);
				}
				groupComboBox = getComboBox("group",getGroupList(),20,100,h);
				addComponentToContainer(this, l,groupComboBox.getAWTComponent());
				return true;
			}else if (comName.equals("scholarType")) {
				label = UCommonQueryUtils.getScholarTypeLabel(b,parameters);
				if (label != null) {
					l = new JLabel(label);
				}
				scholarTypeComboBox = getComboBox("scholarType",getScholarTypeList(),20,100,h);
				addComponentToContainer(this, l,scholarTypeComboBox.getAWTComponent());
				return true;
			} else if (comName.equals("term")) {
				label = UCommonQueryUtils.getTermLabel(b,parameters);
				if (label != null) {
					l = new JLabel(label);
				}
				termComboBox = getComboBox("term",getTermList(),20,200,h);
				addComponentToContainer(this, l,termComboBox.getAWTComponent());
				return true;
			}else if (comName.equals("processState")) {
				label = UCommonQueryUtils.getProcessStateLabel(b,parameters);
				if (label != null) {
					l = new JLabel(label);
				}
				processStateComboBox = getComboBox("processState",getProcessStateList(),20,200,h);
				addComponentToContainer(this, l,processStateComboBox.getAWTComponent());
				return true;
			}else if (comName.equals("reviewMode")) {
				label = UCommonQueryUtils.getReviewModeLabel(b,parameters);
				if (label != null) {
					l = new JLabel(label);
				}
				reviewModeComboBox = getComboBox("reviewState",getReviewModeList(),20,200,h);
				addComponentToContainer(this, l,reviewModeComboBox.getAWTComponent());
				return true;
			}
			else if (comName.equals("studyState")) {
				label = UCommonQueryUtils.getStudyStateLabel(b,parameters);
				if (label != null) {
					l = new JLabel(label);
				}
				studyStateComboBox = getComboBox("studyState",getStudyStateList(),20,200,h);
				addComponentToContainer(this, l,studyStateComboBox.getAWTComponent());
				return true;
			} else if (comName.equals("leaveCause")) {
				label = UCommonQueryUtils.getLeaveCauseLabel(b,parameters);
				if (label != null) {
					l = new JLabel(label);
				}
				leaveCauseComboBox = getComboBox("leaveCause",getLeaveCauseList(),20,200,h);
				addComponentToContainer(this, l,leaveCauseComboBox.getAWTComponent());
				return true;
			} else if (comName.equals("graDegreeDate")) {
				classMark = true;
				label = UCommonQueryUtils.getGraDegreeDateLabel(b,parameters);
				if (label != null) {
					l = new JLabel(label);
				}
				graDegreeDateComboBox = getComboBox("graDegreeDate",getGraDegreeDateList(),20,70,h);
				addComponentToContainer(this, l,graDegreeDateComboBox.getAWTComponent());
				return true;
			}else if (comName.equals("perEngName")) {
				label = UCommonQueryUtils.getPerEngNameLabel(b,parameters);
				if (label != null) {
					l = new JLabel(label);
				}
				perEngNameTextField = new JTextField();
				perEngNameTextField.setColumns(8);
				perEngNameTextField.setPreferredSize(new Dimension(60, h));
				perEngNameTextField.addActionListener(this);
				doCmdMap.put(perEngNameTextField,"query");
				addComponentToContainer(this, l,perEngNameTextField);
				setComStatusAttribute(perEngNameTextField,"perEngName");
				return true;
			}else if (comName.equals("perEnglishGivenName")) {
				label = UCommonQueryUtils.getPerEnglishGivenNameLabel(b,parameters);
				if (label != null) {
					l = new JLabel(label);
				}
				perEnglishGivenNameTextField = new JTextField();
				perEnglishGivenNameTextField.setColumns(8);
				perEnglishGivenNameTextField.setPreferredSize(new Dimension(60, h));
				perEnglishGivenNameTextField.addActionListener(this);
				doCmdMap.put(perEnglishGivenNameTextField,"query");
				addComponentToContainer(this, l,perEnglishGivenNameTextField);
				setComStatusAttribute(perEnglishGivenNameTextField,"perEnglishGivenName");
				return true;
			}else if (comName.equals("perEnglishFamilyName")) {
				label = UCommonQueryUtils.getPerEnglishFamilyNameLabel(b,parameters);
				if (label != null) {
					l = new JLabel(label);
				}
				perEnglishFamilyNameTextField = new JTextField();
				perEnglishFamilyNameTextField.setColumns(8);
				perEnglishFamilyNameTextField.setPreferredSize(new Dimension(60, h));
				perEnglishFamilyNameTextField.addActionListener(this);
				doCmdMap.put(perEnglishFamilyNameTextField,"query");
				addComponentToContainer(this, l,perEnglishFamilyNameTextField);
				setComStatusAttribute(perEnglishFamilyNameTextField,"perEnglishFamilyName");
				return true;
			}else if (comName.equals("perIdCard")) {
				label = UCommonQueryUtils.getPerIdCardLabel(b,parameters);
				if (label != null) {
					l = new JLabel(label);
				}
				perIdCardTextField = new JTextField();
				perIdCardTextField.setColumns(10);
				perIdCardTextField.setPreferredSize(new Dimension(100, h));
				perIdCardTextField.addActionListener(this);
				addComponentToContainer(this, l,perIdCardTextField);
				setComStatusAttribute(perIdCardTextField,"perIdCard");
				return true;
			} else if (comName.equals("mobilePhone")) {
				label = UCommonQueryUtils.getMobilePhoneLabel(b,parameters);
				if (label != null) {
					l = new JLabel(label);
				}
				mobilePhoneTextField = new JTextField();
				mobilePhoneTextField.setColumns(8);
				mobilePhoneTextField.setPreferredSize(new Dimension(60, h));
				mobilePhoneTextField.addActionListener(this);
				addComponentToContainer(this, l,mobilePhoneTextField);
				setComStatusAttribute(mobilePhoneTextField,"mobilePhone");
				return true;
			} else if (comName.equals("examNo")) {
				label = UCommonQueryUtils.getExamNoLabel(b,parameters);
				if (label != null) {
					l = new JLabel(label);
				}
				examNoTextField = new JTextField();
				examNoTextField.setColumns(10);
				examNoTextField.setPreferredSize(new Dimension(100, h));
				examNoTextField.addActionListener(this);
				addComponentToContainer(this, l,examNoTextField);
				setComStatusAttribute(examNoTextField,"examNo");
				return true;
			} else if (comName.equals("courseNum")) {
				label = UCommonQueryUtils.getCourseNumLabel(b,parameters);
				if (label != null) {
					l = new JLabel(label);
				}
				courseNumTextField = new JTextField();
				courseNumTextField.setColumns(10);
				courseNumTextField.setPreferredSize(new Dimension(100, h));
				courseNumTextField.addActionListener(this);
				addComponentToContainer(this, l,courseNumTextField);
				setComStatusAttribute(courseNumTextField,"courseNum");
				return true;
			} else if (comName.equals("courseName")) {
				label = UCommonQueryUtils.getCourseNameLabel(b,parameters);
				if (label != null) {
					l = new JLabel(label);
				}
				courseNameTextField = new JTextField();
				courseNameTextField.setColumns(10);
				courseNameTextField.setPreferredSize(new Dimension(100, h));
				courseNameTextField.addActionListener(this);
				addComponentToContainer(this, l,courseNameTextField);
				setComStatusAttribute(courseNameTextField,"courseName");
				return true;
			}else if (comName.equals("courseNo")) {
				label = UCommonQueryUtils.getCourseNoLabel(b,parameters);
				if (label != null) {
					l = new JLabel(label);
				}
				courseNoComboBox = getComboBox("courseNo",getCourseNoList(),20,100,h);
				addComponentToContainer(this, l,courseNoComboBox.getAWTComponent());
				return true;
			}else if (comName.equals("teachTaskType")) {
				label = UCommonQueryUtils.getTeachTaskTypeLabel(b,parameters);
				if (label != null) {
					l = new JLabel(label);
				}
				teachTaskTypeComboBox = getComboBox("teachTaskType",getTeachTaskTypeList(),20,100,h);
				addComponentToContainer(this, l,teachTaskTypeComboBox.getAWTComponent());
				return true;
			}else if (comName.equals("conditionQuery")) {
				conditionQueryButton = new JButton("条件查");
				conditionQueryButton.setActionCommand("conditionQuery");
				conditionQueryButton.addActionListener(this);
				add(conditionQueryButton);
				setComStatusAttribute(conditionQueryButton,"conditionQuery");
				return true;
			}else if (comName.equals("dataSource")) {
				label = UCommonQueryUtils.getDataSourceLabel(b,parameters);
				if (label != null) {
					l = new JLabel(label);
				}
				dataSourceComboBox = getComboBox("dataSource",getDataSourceList(),20,100,h);
				addComponentToContainer(this, l,dataSourceComboBox.getAWTComponent());
				return true;
			}
		 return false;
	}

	public boolean processComponentChange(ActionEvent e) {
		if (e.getSource() == secondTypeLevel1ComboBox) {
			secondTypeLevel1Changed();
			return true;
		} else if (e.getSource() == secondTypeLevel2ComboBox) {
			secondTypeLevel2Changed();
			return true;
		} else if (e.getSource() == secondTypeLevel3ComboBox) {
			secondTypeLevel3Changed();
			return true;
		} else if (e.getSource() == secondTypeCodeComboBox) {
			secondTypeCodeChanged();
			return true;
		} else if (e.getSource() == majorComboBox) {
			majorChanged();
			return true;
		} else if (e.getSource() == gradeComboBox) {
			gradeChanged();
			return true;
		} else if (e.getSource() == grade1ComboBox) {
			grade1Changed();
			return true;
		} else if (e.getSource() == classComboBox) {
			classChanged();
			return true;
		} else if (e.getSource() == groupComboBox) {
			groupChanged();
			return true;
		} else if (e.getSource() == scholarTypeComboBox) {
			scholarTypeChanged();
			return true;
		} else if (e.getSource() == termComboBox) {
			termChanged();
			return true;
		} else if (e.getSource() == processStateComboBox) {
			processStateChanged();
			return true;
		} else if (e.getSource() == reviewModeComboBox) {
			reviewModeChanged();
			return true;
		} else if (e.getSource() == studyStateComboBox) {
			studyStateChanged();
			return true;
		} else if (e.getSource() == leaveCauseComboBox) {
			LeaveCauseChanged();
			return true;
		}else if (e.getSource() == dataSourceComboBox) {
			dataSourceChanged();
			return true;
		} else if (e.getSource() == graDegreeDateComboBox) {
			graDegreeDateChanged();
			return true;
		}else if(e.getSource()== courseNumTextField){
			courseNumInputed();
			return true;
		}else if(e.getSource()== courseNameTextField){
			courseNameInputed();
			return true;
		}
		else if (e.getSource() == conditionQueryButton) {
			UConditionCommonQueryDialog dlg = new UConditionCommonQueryDialog();
			if (dlg.getReturnValue() == dlg.RETURN_OK) {
				String sqlReslut = dlg.getSqlResult();
				((CommonBaseDataQueryForm)dataForm).setSqlReslut(sqlReslut);
			}
			return true;
		}
		return false;
	}

	
	public void formToComponent() {
		super.formToComponent();
		CommonBaseDataQueryForm dForm = getBaseDataForm(); 
		setClassIdToComboBox(dForm.getClassId());
		setLoanStatusToComboBox(dForm.getLoanStatus());
		setLoanStatusNameToComboBox(dForm.getLoanStatusName());
		setGroupIdToComboBox(dForm.getGroupId());
		setScholarTypeToComboBox(dForm.getScholarType());
		setMajorIdToComboBox(dForm.getMajorId());
		setTermIdToComboBox(dForm.getTermId());
		setProcessStateToComboBox(dForm.getProcessState());
		setReviewModeToComboBox(dForm.getReviewMode());
		setStudyStateToComboBox(dForm.getStudyState());
		setLeaveCauseToComboBox(dForm.getLeaveCause());
		setSecondTypeLevel1ToComboBox(dForm.getSecondTypeLevel1());
		setSecondTypeLevel2ToComboBox(dForm.getSecondTypeLevel2());
		setSecondTypeLevel3ToComboBox(dForm.getSecondTypeLevel3());
		setSecondTypeCodeToComboBox(dForm.getSecondTypeCode());
		setGradeToComboBox(dForm.getGrade());
		setGrade1ToComboBox(dForm.getGrade1());
		setCourseNoToComboBox(dForm.getCourseNo());
		setTeachTaskTypeToComboBox(dForm.getTeachTaskType());
		setGraDegreeDateToComboBox(dForm.getGraDegreeDate());

		setClassIdsToComboBox(dForm.getClassIds());
		setLoanStatussToComboBox(dForm.getLoanStatuss());
		setLoanStatusNamesToComboBox(dForm.getLoanStatusNames());
		setGroupIdsToComboBox(dForm.getGroupIds());
		setScholarTypesToComboBox(dForm.getScholarTypes());
		setMajorIdsToComboBox(dForm.getMajorIds());
		setTermIdsToComboBox(dForm.getTermIds());
		setProcessStatesToComboBox(dForm.getProcessStates());
		setReviewModesToComboBox(dForm.getReviewModes());
		setStudyStatesToComboBox(dForm.getStudyStates());
		setLeaveCausesToComboBox(dForm.getLeaveCauses());
		setSecondTypeLevel1sToComboBox(dForm.getSecondTypeLevel1s());
		setSecondTypeLevel2sToComboBox(dForm.getSecondTypeLevel2s());
		setSecondTypeLevel3sToComboBox(dForm.getSecondTypeLevel3s());
		setSecondTypeCodesToComboBox(dForm.getSecondTypeCodes());
		setGradesToComboBox(dForm.getGrades());
		setGrade1sToComboBox(dForm.getGrade1s());
		setCourseNosToComboBox(dForm.getCourseNos());
		setTeachTaskTypesToComboBox(dForm.getTeachTaskTypes());
		setCourseNoToComboBox(dForm.getCourseNo());
		setTeachTaskTypeToComboBox(dForm.getTeachTaskType());
		setGraDegreeDatesToComboBox(dForm.getGraDegreeDates());
		
		setExamNoToTextField(dForm.getExamNo());
		setCourseNumToTextField(dForm.getCourseNum());
		setCourseNameToTextField(dForm.getCourseName());
		setPerEngNameToTextField(dForm.getPerEngName());
		setPerEnglishFamilyNameToTextField(dForm.getPerEnglishFamilyName());
		setPerEnglishGivenNameToTextField(dForm.getPerEnglishGivenName());
		setPerIdCardToTextField(dForm.getPerIdCard());
		setMobilePhoneToTextField(dForm.getMobilePhone());
	}
	
	public void collegeChanged() {
		dataForm.setCollegeId(this.getCollegeId());
		dataForm.setCollegeIds(this.getCollegeIdsByComboBox());
		updataCollegeId1ComboxBoxListData();
		updataMajorComboxBoxListData();
	}
	public void college1Changed() {
		dataForm.setCollegeId1(this.getCollegeId1ByComboBox());
		dataForm.setCollegeId1s(this.getCollegeId1sByComboBox());
		updataCollegeId2ComboxBoxListData();
		updataMajorComboxBoxListData();
	}
	public void college2Changed() {
		dataForm.setCollegeId2(this.getCollegeId2ByComboBox());
		dataForm.setCollegeId2s(this.getCollegeId2sByComboBox());
		updataMajorComboxBoxListData();
	}
	
	public void componentToForm() {
		super.componentToForm();
		CommonBaseDataQueryForm dForm = getBaseDataForm(); 

		dForm.setClassId(this.getClassIdByComboBox());
		dForm.setGroupId(this.getGroupIdByComboBox());
		dForm.setScholarType(this.getScholarTypeByComboBox());
		dForm.setMajorId(this.getMajorIdByComboBox());
		dForm.setTermId(this.getTermIdByComboBox());
		dForm.setLoanStatus(this.getLoanStatusByComboBox());
		dForm.setLoanStatusName(getLoanStatusNameByComboBox());
		dForm.setProcessState(this.getProcessStateByComboBox());
		dForm.setReviewMode(this.getReviewModeByComboBox());
		dForm.setStudyState(this.getStudyStateByComboBox());
		dForm.setLeaveCause(this.getLeaveCauseByComboBox());
		dForm.setSecondTypeLevel1(this.getSecondTypeLevel1ByComboBox());
		dForm.setSecondTypeLevel2(this.getSecondTypeLevel2ByComboBox());
		dForm.setSecondTypeLevel3(this.getSecondTypeLevel3ByComboBox());
		dForm.setSecondTypeCode(this.getSecondTypeCodeByComboBox());
		dForm.setGrade(this.getGradeByComboBox());
		dForm.setGrade1(this.getGrade1ByComboBox());
		dForm.setCourseNo(this.getCourseNoByComboBox());
		dForm.setTeachTaskType(this.getTeachTaskTypeByComboBox());
		dForm.setGraDegreeDate(this.getGraDegreeDateByComboBox());

		dForm.setClassIds(this.getClassIdsByComboBox());
		dForm.setGroupIds(this.getGroupIdsByComboBox());
		dForm.setScholarTypes(this.getScholarTypesByComboBox());
		dForm.setMajorIds(this.getMajorIdsByComboBox());
		dForm.setTermIds(this.getTermIdsByComboBox());
		dForm.setLoanStatuss(this.getLoanStatussByComboBox());
		dForm.setLoanStatusNames(this.getLoanStatusNamesByComboBox());
		dForm.setProcessStates(this.getProcessStatesByComboBox());
		dForm.setReviewModes(this.getReviewModesByComboBox());
		dForm.setStudyStates(this.getStudyStatesByComboBox());
		dForm.setLeaveCauses(this.getLeaveCausesByComboBox());
		dForm.setSecondTypeLevel1s(this.getSecondTypeLevel1sByComboBox());
		dForm.setSecondTypeLevel2s(this.getSecondTypeLevel2sByComboBox());
		dForm.setSecondTypeLevel3s(this.getSecondTypeLevel3sByComboBox());
		dForm.setSecondTypeCodes(this.getSecondTypeCodesByComboBox());
		dForm.setGrades(this.getGradesByComboBox());
		dForm.setGrade1s(this.getGrade1sByComboBox());
		dForm.setCourseNos(this.getCourseNosByComboBox());
		dForm.setTeachTaskTypes(this.getTeachTaskTypesByComboBox());
		dForm.setGraDegreeDate(this.getGraDegreeDateByComboBox());
		
		dForm.setPerEngName(this.getPerEngNameByTextField());
		dForm.setPerEnglishFamilyName(this.getPerEnglishFamilyNameByTextField());
		dForm.setPerEnglishGivenName(this.getPerEnglishGivenNameByTextField());
		dForm.setPerIdCard(this.getPerIdCardByTextField());
		dForm.setMobilePhone(this.getMobilePhoneByTextField()); 
		dForm.setExamNo(this.getExamNoByTextField());
		dForm.setCourseNum(this.getCourseNumByTextField());
		dForm.setCourseName(this.getCourseNameByTextField());  
	}

//-----------------	
	
	public void setComboxBoxSecondTypeItem() {
		setComboxBoxSecondType123Item();
		setComboxBoxSecondTypeCodeItem();
	}

	public void setComboxBoxSecondTypeCodeItem() {
		if (secondTypeCodeComboBox == null)
			return;
		secondTypeCodeComboBox.setAddedDatas(getSecondTypeCodeList());
	}

	public void setComboxBoxSecondType123Item() {
		if (secondTypeLevel1ComboBox == null || secondTypeLevel2ComboBox == null
				|| secondTypeLevel3ComboBox == null)
			return;
		List a[] = getSecondTypeLevelList();
		if (a == null) {
			secondTypeLevel1ComboBox.setAddedDatas((List)null);
			secondTypeLevel2ComboBox.setAddedDatas((List)null);
			secondTypeLevel3ComboBox.setAddedDatas((List)null);
		} else {
			secondTypeLevel1ComboBox.setAddedDatas(a[0]);
			secondTypeLevel2ComboBox.setAddedDatas(a[1]);
			secondTypeLevel3ComboBox.setAddedDatas(a[2]);
		}
	}

	public List getSecondTypeCodeList() {
		List list = UCommonQueryUtils.getAddedCodeList("secondTypeCodeAddedData",parameters);
		if(list != null && list.size() != 0)
			return list;
		RmiRequest request = new RmiRequest();
		request.add("bType", UCommonQueryUtils.getBType(parameters));
		request.add("perTypeCode", getBaseDataForm().getPerTypeCode());
		request.setCmd("commonBaseDataQueryRequestSecondTypeCodeList");
		request.add("beanName", UCommonQueryUtils.getStatusBeanName(parameters));		
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() == null) {
			return (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return null;
	}


	public List[] getSecondTypeLevelList() {
		// 根据角色获取可操作的学生类型
		RmiRequest request = new RmiRequest();
		request.add("bType", UCommonQueryUtils.getBType(parameters));
		request.add("perTypeCode", getBaseDataForm().getPerTypeCode());
		request.setCmd("commonBaseDataQueryRequestSecondTypeLevelList");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() == null) {
			return (List[]) respond.get(RmiKeyConstants.KEY_ARRAY);
		}
		return null;
	}

	public List getMajorList(String perTypeCode,Integer collegeId,Integer[] collegeIds, String secondType, String [] secondTypes) {
		RmiRequest request = new RmiRequest();
		request.add("bType", UCommonQueryUtils.getBType(parameters));
		request.add("perTypeCode", perTypeCode);
		request.add("collegeId", collegeId);
		request.add("collegeIds", collegeIds);
		request.add("secondType", secondType);
		request.add("secondTypes", secondTypes);
//		request.add("stuTypeCollegeKey",UCommonQueryUtils.getStuTypeCollegeKey(parameters));
//		request.add("collegeMapKey",UCommonQueryUtils.getCollegeMapKey(parameters));
		request.setCmd("commonBaseDataQueryRequestMajorList");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() == null) {
			return (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return null;
	}
	
	public List getMajorList() {
		Integer cId, cIds[];
		cId = dataForm.getCollegeId2();
		cIds = dataForm.getCollegeId2s();
		if((cId == null || collegeId.equals(-1)) && (cIds == null || cIds.length ==0)) {
			cId = dataForm.getCollegeId1();
			cIds = dataForm.getCollegeId1s();
		}
		if((cId == null || cId.equals(-1)) && (cIds == null || cIds.length ==0)) {
			cId = dataForm.getCollegeId();
			cIds = dataForm.getCollegeIds();
		}
		return getMajorList(getBaseDataForm().getPerTypeCode(),cId, cIds,getBaseDataForm().getSecondType(),
				getBaseDataForm().getSecondTypes());
	}

	public List getClassList() {
		return getClassList(dataForm.getCollegeId(), dataForm.getCollegeIds(),
				getBaseDataForm().getSecondType(),
				getBaseDataForm().getSecondTypes(),
				getBaseDataForm().getMajorId(),
				getBaseDataForm().getMajorIds(), 
				getBaseDataForm().getGrade(), getBaseDataForm().getGrades(),
				getBaseDataForm().getPerTypeCode());
	}

	public List getClassList(Integer collegeId,Integer[] collegeIds, String secondType, String[] secondTypes, 
			Integer majorId,Integer[] majorIds, String grade,String[] grades, String perTypeCode) {
		RmiRequest request = new RmiRequest();
		if (classMark) {
			request.add("bType", UCommonQueryUtils.getBType(parameters));
			request.add("collegeId", collegeId);
			request.add("collegeIds", collegeIds);
			request.add("secondType", secondType);
			request.add("secondTypes", secondTypes);
			request.add("perTypeCode", perTypeCode);
			request.add("majorId", majorId);
			request.add("majorIds", majorIds);
			request.add("grade", grade);
			request.add("grades", grades);
			request.setCmd("commonBaseDataQueryRequestClassList");
		} else {
			request.add("bType", UCommonQueryUtils.getBType(parameters));
			request.add("beanName", UCommonQueryUtils
					.getBaseClassBeanName(parameters));
			request.setCmd("commonBaseDataQueryRequestClassListByBean");
		}
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() == null) {
			return (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return null;
	}
	
	public List getLoanStatusList() {
		RmiRequest request = new RmiRequest();
		request.setCmd("commonBaseDataQueryRequestLoanStatusList");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() == null) {
			return (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return null;
	}
	public List getLoanStatusNameList() {
		RmiRequest request = new RmiRequest();
		request.setCmd("commonBaseDataQueryRequestLoanStatusNameList");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() == null) {
			return (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return null;
	}

	public List getScholarTypeList() {
		List list = null;
		RmiRequest request = new RmiRequest();
		request.add("beanName", "getScholarTypeListProcessRuleBean");
		request.setCmd("commonBaseDataQueryRequestScholarTypeList");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() == null) {
			list= (List)respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return list;
	}

	
	public List getGroupList() {
		return getClassList(dataForm.getCollegeId(), dataForm.getCollegeIds(),
				getBaseDataForm().getSecondType(),getBaseDataForm().getSecondTypes(),
				getBaseDataForm().getMajorId(),getBaseDataForm().getMajorIds(),
				getBaseDataForm().getGrade(),getBaseDataForm().getGrades(),
				getBaseDataForm().getPerTypeCode());
	}

	public List getGroupList(Integer collegeId, Integer[] collegeIds,
			String secondType,String secondTypes[],
			Integer majorId,Integer majorIds[],
			String grade,String grades[], String perTypeCode) {
		RmiRequest request = new RmiRequest();
		request.add("bType", UCommonQueryUtils.getBType(parameters));
		request.add("collegeId", collegeId);
		request.add("collegeIds", collegeIds);
		request.add("secondType", secondType);
		request.add("secondTypes", secondTypes);
		request.add("perTypeCode", perTypeCode);
		request.add("majorId", majorId);
		request.add("majorIds", majorIds);
		request.add("grade", grade);
		request.add("grades", grades);
		request.setCmd("commonBaseDataQueryRequestClassList");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() == null) {
			return (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return null;
	}

	public List getTermList() {
		RmiRequest request = new RmiRequest();
		request.add("bType", UCommonQueryUtils.getBType(parameters));
		request.add("isTermPre", UCommonQueryUtils.getIsTermPre(parameters));
		request.setCmd("commonBaseDataQueryRequestTermList");
		request.add("beanName", UCommonQueryUtils
				.getStatusBeanName(parameters));		
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() == null) {
			return (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return null;
	}

	public List getGraDegreeDateList() {
		RmiRequest request = new RmiRequest();
		request.setCmd("commonBaseDataQueryRequestGraDegreeDateList");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() == null) {
			return (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return null;
	}
	
	public List getStudyStateList() {
		return getDictionaryTypeCodeList("XSZXZT");
	}
	public List getProcessStateList() {
		RmiRequest request = new RmiRequest();
		request.add("bType", UCommonQueryUtils.getBType(parameters));
		request.setCmd("commonBaseDataQueryRequestProcessStateList");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() == null) {
			return (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return null;
	}
	
	public List getReviewModeList() {
		return getDictionaryTypeCodeList("XWLWPYFSLXM");
	}

	public List getLeaveCauseList() {
		if(studyStateComboBox == null)
			return null;
		Integer s = this.getStudyStateByComboBox();
		Integer ss[] = this.getStudyStatesByComboBox();
		if(s == null && (ss== null || ss.length == 0)) {
			return null;
		}else if(s != null) {
			if(s.equals(4)) {
				return getDictionaryTypeCodeList("XSYJLKZT");
			}else if(s.equals(5)){
				return getDictionaryTypeCodeList("XSZSLKZT");
			}
			else
				return null;
		}else {
			List list = new ArrayList();
			List list1;
			for(int i = 0; i <ss.length;i++) {
				if(s.equals(4)) {
					list.add(getDictionaryTypeCodeList("XSYJLKZT"));
				}else if(s.equals(5)){
					list.add(getDictionaryTypeCodeList("XSZSLKZT"));
				}
			}
			if(list.size() >0)
				return list;
			else
				return null;
		}
	}

	public List getDataSourceList() {
		List list = null;
		RmiRequest request = new RmiRequest();
		request.add("bType", UCommonQueryUtils.getBType(parameters));
		request.setCmd("commonBaseDataQueryRequestDataSourceList");
		RmiResponse respond = UimsUtils.requestProcesser(
				request);
		if (respond.getErrorMsg() == null) {
			list = (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		List exList = UCommonQueryUtils.getExpendDataSource(parameters);
		if(exList != null) {
			if(list == null)
				list = exList;
			else {
				for(int i = 0; i< exList.size();i++)
					list.add(exList.get(i));
			}
		}
		return list;

	}

	public List getCourseNoList(){
		return null;
	}

	public List getTeachTaskTypeList(){
		RmiRequest request = new RmiRequest();
		RmiResponse respond = null;
		request.add("bType", UCommonQueryUtils.getBType(parameters));
		request.setCmd("commonBaseDataQueryRequestTeachTaskTypeList");
		respond = UimsUtils.requestProcesser(request);
		List list = null;
		if (respond.getErrorMsg() == null) {
			list = (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return list;
	}

	public Integer getMajorIdByComboBox() {
		return UCommonQueryUtils.changeStringToInteger(UCommonQueryUtils
				.getSelectItemOfComboBox(majorComboBox));
	}
		
	public void setMajorIdToComboBox(Integer id) {
		UCommonQueryUtils.setSelectItemOfComboBox(majorComboBox, id);
	}

	public Integer[] getMajorIdsByComboBox() {
		return UCommonQueryUtils.changeStringsToIntegers(UCommonQueryUtils
				.getSelectItemsOfComboBox(majorComboBox));
	}
		
	public void setMajorIdsToComboBox(Integer[] ids) {
		UCommonQueryUtils.setSelectItemsOfComboBox(majorComboBox, ids);
	}


	public Integer getClassIdByComboBox() {
		return UCommonQueryUtils.changeStringToInteger(UCommonQueryUtils
				.getSelectItemOfComboBox(classComboBox));
	}

	public void setClassIdToComboBox(Integer id) {
		UCommonQueryUtils.setSelectItemOfComboBox(classComboBox, id);
	}

	public Integer[] getClassIdsByComboBox() {
		return UCommonQueryUtils.changeStringsToIntegers(UCommonQueryUtils
				.getSelectItemsOfComboBox(classComboBox));
	}

	public void setClassIdsToComboBox(Integer[] ids) {
		UCommonQueryUtils.setSelectItemsOfComboBox(classComboBox, ids);
	}
	
	public String getLoanStatusByComboBox() {
		return UCommonQueryUtils
				.getSelectItemOfComboBox(loanStatusComboBox);
	}
	public void setLoanStatusToComboBox(String id) {
		UCommonQueryUtils.setSelectItemOfComboBox(loanStatusComboBox, id);
	}

	public String[] getLoanStatussByComboBox() {
		return UCommonQueryUtils
				.getSelectItemsOfComboBox(loanStatusComboBox);
	}
	public void setLoanStatussToComboBox(String[] id) {
		UCommonQueryUtils.setSelectItemOfComboBox(loanStatusComboBox, id);
	}
	
	public String getLoanStatusNameByComboBox() {
		return UCommonQueryUtils
				.getSelectItemOfComboBox(loanStatusNameComboBox);
	}
	
	public void setLoanStatusNameToComboBox(String id) {
		UCommonQueryUtils.setSelectItemOfComboBox(loanStatusNameComboBox, id);
	}

	public String [] getLoanStatusNamesByComboBox() {
		return UCommonQueryUtils
				.getSelectItemsOfComboBox(loanStatusNameComboBox);
	}
	
	public void setLoanStatusNamesToComboBox(String[] id) {
		UCommonQueryUtils.setSelectItemsOfComboBox(loanStatusNameComboBox, id);
	}

	public Integer getGroupIdByComboBox() {
		return UCommonQueryUtils.changeStringToInteger(UCommonQueryUtils
				.getSelectItemOfComboBox(groupComboBox));
	}

	public void setGroupIdToComboBox(Integer id) {
		UCommonQueryUtils.setSelectItemOfComboBox(groupComboBox, id);
	}

	public Integer[] getGroupIdsByComboBox() {
		return UCommonQueryUtils.changeStringsToIntegers(UCommonQueryUtils
				.getSelectItemsOfComboBox(groupComboBox));
	}

	public void setGroupIdsToComboBox(Integer[] id) {
		UCommonQueryUtils.setSelectItemsOfComboBox(groupComboBox, id);
	}

	public String getScholarTypeByComboBox() {
		return UCommonQueryUtils
				.getSelectItemOfComboBox(scholarTypeComboBox);
	}

	public void setScholarTypeToComboBox(String id) {
		UCommonQueryUtils.setSelectItemOfComboBox(scholarTypeComboBox, id);
	}

	public String[] getScholarTypesByComboBox() {
		return UCommonQueryUtils
				.getSelectItemsOfComboBox(scholarTypeComboBox);
	}

	public void setScholarTypesToComboBox(String[] id) {
		UCommonQueryUtils.setSelectItemsOfComboBox(scholarTypeComboBox, id);
	}
	
	
	public Integer getTermIdByComboBox() {
		return UCommonQueryUtils.changeStringToInteger(UCommonQueryUtils
				.getSelectItemOfComboBox(termComboBox));
	}
	public void setTermIdToComboBox(Integer id) {
		UCommonQueryUtils.setSelectItemOfComboBox(termComboBox, id);
	}

	public Integer[] getTermIdsByComboBox() {
		return UCommonQueryUtils.changeStringsToIntegers(UCommonQueryUtils
				.getSelectItemsOfComboBox(termComboBox));
	}
	public void setTermIdsToComboBox(Integer[] ids) {
		UCommonQueryUtils.setSelectItemsOfComboBox(termComboBox, ids);
	}

	public String getProcessStateByComboBox() {
		return UCommonQueryUtils.getSelectItemOfComboBox(processStateComboBox);
	}
	public void setProcessStateToComboBox(String id) {
		UCommonQueryUtils.setSelectItemOfComboBox(processStateComboBox, id);
	}

	public Integer[] getProcessStatesByComboBox() {
		return UCommonQueryUtils.changeStringsToIntegers(UCommonQueryUtils
				.getSelectItemsOfComboBox(processStateComboBox));
	}

	public void setProcessStatesToComboBox(Integer[] ids) {
		UCommonQueryUtils.setSelectItemsOfComboBox(processStateComboBox, ids);
	}
	

	public String  getReviewModeByComboBox() {
		return UCommonQueryUtils
				.getSelectItemOfComboBox(reviewModeComboBox);
	}
	public void setReviewModeToComboBox(String id) {
		UCommonQueryUtils.setSelectItemOfComboBox(reviewModeComboBox, id);
	}

	public String[] getReviewModesByComboBox() {
		return UCommonQueryUtils
				.getSelectItemsOfComboBox(reviewModeComboBox);
	}

	public void setReviewModesToComboBox(String[] ids) {
		UCommonQueryUtils.setSelectItemsOfComboBox(reviewModeComboBox, ids);
	}
	
	
	public Integer getStudyStateByComboBox() {
		return UCommonQueryUtils.changeStringToInteger(UCommonQueryUtils
				.getSelectItemOfComboBox(studyStateComboBox));
	}
	public void setStudyStateToComboBox(Integer id) {
		UCommonQueryUtils.setSelectItemOfComboBox(studyStateComboBox, id);
	}

	public Integer[] getStudyStatesByComboBox() {
		return UCommonQueryUtils.changeStringsToIntegers(UCommonQueryUtils
				.getSelectItemsOfComboBox(studyStateComboBox));
	}

	public void setStudyStatesToComboBox(Integer[] ids) {
		UCommonQueryUtils.setSelectItemsOfComboBox(studyStateComboBox, ids);
	}

	public Integer getLeaveCauseByComboBox() {
		return UCommonQueryUtils.changeStringToInteger(UCommonQueryUtils
				.getSelectItemOfComboBox(leaveCauseComboBox));
	}
	public void setLeaveCauseToComboBox(Integer id) {
		UCommonQueryUtils.setSelectItemOfComboBox(leaveCauseComboBox, id);
	}

	public Integer[] getLeaveCausesByComboBox() {
		return UCommonQueryUtils.changeStringsToIntegers(UCommonQueryUtils
				.getSelectItemsOfComboBox(leaveCauseComboBox));
	}

	public void setLeaveCausesToComboBox(Integer[] ids) {
		UCommonQueryUtils.setSelectItemsOfComboBox(leaveCauseComboBox, ids);
	}
	
	public String getSecondTypeLevel1ByComboBox() {
		return UCommonQueryUtils.getSelectItemOfComboBox(secondTypeLevel1ComboBox);
	}

	public void setSecondTypeLevel1ToComboBox(String str) {
		UCommonQueryUtils.setSelectItemOfComboBox(secondTypeLevel1ComboBox, str);
	}

	public String[] getSecondTypeLevel1sByComboBox() {
		return UCommonQueryUtils.getSelectItemsOfComboBox(secondTypeLevel1ComboBox);
	}

	public void setSecondTypeLevel1sToComboBox(String[] str) {
		UCommonQueryUtils.setSelectItemsOfComboBox(secondTypeLevel1ComboBox, str);
	}
	
	public String getSecondTypeLevel2ByComboBox() {
		return UCommonQueryUtils.getSelectItemOfComboBox(secondTypeLevel2ComboBox);
	}

	public void setSecondTypeLevel2ToComboBox(String str) {
		UCommonQueryUtils.setSelectItemOfComboBox(secondTypeLevel2ComboBox, str);
	}

	public String[] getSecondTypeLevel2sByComboBox() {
		return UCommonQueryUtils.getSelectItemsOfComboBox(secondTypeLevel2ComboBox);
	}

	public void setSecondTypeLevel2sToComboBox(String [] str) {
		UCommonQueryUtils.setSelectItemsOfComboBox(secondTypeLevel2ComboBox, str);
	}

	public String getSecondTypeLevel3ByComboBox() {
		return UCommonQueryUtils.getSelectItemOfComboBox(secondTypeLevel3ComboBox);
	}

	public void setSecondTypeLevel3ToComboBox(String str) {
		UCommonQueryUtils.setSelectItemOfComboBox(secondTypeLevel3ComboBox, str);
	}

	public String[] getSecondTypeLevel3sByComboBox() {
		return UCommonQueryUtils.getSelectItemsOfComboBox(secondTypeLevel3ComboBox);
	}

	public void setSecondTypeLevel3sToComboBox(String[] str) {
		UCommonQueryUtils.setSelectItemsOfComboBox(secondTypeLevel3ComboBox, str);
	}
	
	public String getSecondTypeCodeByComboBox() {
		return UCommonQueryUtils.getSelectItemOfComboBox(secondTypeCodeComboBox);
	}

	public void setSecondTypeCodeToComboBox(String str) {
		UCommonQueryUtils.setSelectItemOfComboBox(secondTypeCodeComboBox, str);
	}

	public String[] getSecondTypeCodesByComboBox() {
		return UCommonQueryUtils.getSelectItemsOfComboBox(secondTypeCodeComboBox);
	}

	public void setSecondTypeCodesToComboBox(String[] str) {
		UCommonQueryUtils.setSelectItemsOfComboBox(secondTypeCodeComboBox, str);
	}


	public String getGradeByComboBox() {
		return UCommonQueryUtils.getSelectItemOfComboBox(gradeComboBox);
	}

	public void setGradeToComboBox(String str) {
		UCommonQueryUtils.setSelectItemOfComboBox(gradeComboBox, str);
	}

	public String[] getGradesByComboBox() {
		return UCommonQueryUtils.getSelectItemsOfComboBox(gradeComboBox);
	}

	public void setGradesToComboBox(String[] str) {
		UCommonQueryUtils.setSelectItemOfComboBox(gradeComboBox, str);
	}

	public String getGrade1ByComboBox() {
		return UCommonQueryUtils.getSelectItemOfComboBox(grade1ComboBox);
	}

	public void setGrade1ToComboBox(String str) {
		UCommonQueryUtils.setSelectItemOfComboBox(grade1ComboBox, str);
	}

	public String[] getGrade1sByComboBox() {
		return UCommonQueryUtils.getSelectItemsOfComboBox(grade1ComboBox);
	}

	public void setGrade1sToComboBox(String[] str) {
		UCommonQueryUtils.setSelectItemOfComboBox(grade1ComboBox, str);
	}

	public String getGraDegreeDateByComboBox() {
		return UCommonQueryUtils.getSelectItemOfComboBox(graDegreeDateComboBox);
	}

	public void setGraDegreeDateToComboBox(String str) {
		UCommonQueryUtils.setSelectItemOfComboBox(graDegreeDateComboBox, str);
	}

	public String[] getGraDegreeDatesByComboBox() {
		return UCommonQueryUtils.getSelectItemsOfComboBox(graDegreeDateComboBox);
	}

	public void setGraDegreeDatesToComboBox(String[] str) {
		UCommonQueryUtils.setSelectItemOfComboBox(graDegreeDateComboBox, str);
	}
	
	public String getDataSourceByComboBox() {
		return UCommonQueryUtils.getSelectItemOfComboBox(dataSourceComboBox);
	}

	public void setDataSourceToComboBox(String str) {
		UCommonQueryUtils.setSelectItemOfComboBox(dataSourceComboBox, str);
	}

	public String[] getDataSourcesByComboBox() {
		return UCommonQueryUtils.getSelectItemsOfComboBox(dataSourceComboBox);
	}

	public void setDataSourcesToComboBox(String[] str) {
		UCommonQueryUtils.setSelectItemsOfComboBox(dataSourceComboBox, str);
	}

	public String getCourseNoByComboBox() {
		return UCommonQueryUtils.getSelectItemOfComboBox(courseNoComboBox);
	}
	public void setCourseNoToComboBox(String str) {
		UCommonQueryUtils.setSelectItemOfComboBox(courseNoComboBox, str);
	}
	public String[] getCourseNosByComboBox() {
		return UCommonQueryUtils.getSelectItemsOfComboBox(courseNoComboBox);
	}
	public void setCourseNosToComboBox(String[] str) {
		UCommonQueryUtils.setSelectItemOfComboBox(courseNoComboBox, str);
	}

	public String getTeachTaskTypeByComboBox() {
		return UCommonQueryUtils.getSelectItemOfComboBox(teachTaskTypeComboBox);
	}

	public void setTeachTaskTypeToComboBox(String str) {
		UCommonQueryUtils.setSelectItemOfComboBox(teachTaskTypeComboBox, str);
	}

	public String[] getTeachTaskTypesByComboBox() {
		return UCommonQueryUtils.getSelectItemsOfComboBox(teachTaskTypeComboBox);
	}

	public void setTeachTaskTypesToComboBox(String[] str) {
		UCommonQueryUtils.setSelectItemOfComboBox(teachTaskTypeComboBox, str);
	}

	public String getPerEngNameByTextField() {
		if (perEngNameTextField == null)
			return null;
		return perEngNameTextField.getText();
	}

	public void setPerEngNameToTextField(String str) {
		if (perEngNameTextField == null)
			return;
		if (str == null)
			perEngNameTextField.setText("");
		else
			perEngNameTextField.setText(str);
	}

	public String getPerEnglishGivenNameByTextField() {
		if (perEnglishGivenNameTextField == null)
			return null;
		return perEnglishGivenNameTextField.getText();
	}

	public void setPerEnglishGivenNameToTextField(String str) {
		if (perEnglishGivenNameTextField == null)
			return;
		if (str == null)
			perEnglishGivenNameTextField.setText("");
		else
			perEnglishGivenNameTextField.setText(str);
	}

	public String getPerEnglishFamilyNameByTextField() {
		if (perEnglishFamilyNameTextField == null)
			return null;
		return perEnglishFamilyNameTextField.getText();
	}

	public void setPerEnglishFamilyNameToTextField(String str) {
		if (perEnglishFamilyNameTextField == null)
			return;
		if (str == null)
			perEnglishFamilyNameTextField.setText("");
		else
			perEnglishFamilyNameTextField.setText(str);
	}
	
	public String getPerIdCardByTextField() {
		if (perIdCardTextField == null)
			return null;
		return perIdCardTextField.getText();
	}

	public void setPerIdCardToTextField(String str) {
		if (perIdCardTextField == null)
			return;
		if (str == null)
			perIdCardTextField.setText("");
		else
			perIdCardTextField.setText(str);
	}

	public String getMobilePhoneByTextField() {
		if (mobilePhoneTextField == null)
			return null;
		return mobilePhoneTextField.getText();
	}

	public void setMobilePhoneToTextField(String str) {
		if (mobilePhoneTextField == null)
			return;
		if (str == null)
			mobilePhoneTextField.setText("");
		else
			mobilePhoneTextField.setText(str);
	}
	
	public String getExamNoByTextField() {
		if (examNoTextField == null)
			return null;
		return examNoTextField.getText();
	}

	public void setExamNoToTextField(String str) {
		if (examNoTextField == null)
			return;
		if (str == null)
			examNoTextField.setText("");
		else
			examNoTextField.setText(str);
	}

	public String getCourseNumByTextField() {
		if (courseNumTextField == null)
			return null;
		return courseNumTextField.getText();
	}

	public void setCourseNumToTextField(String str) {
		if (courseNumTextField == null)
			return;
		if (str == null)
			courseNumTextField.setText("");
		else
			courseNumTextField.setText(str);
	}

	public String getCourseNameByTextField() {
		if (courseNameTextField == null)
			return null;
		return courseNameTextField.getText();
	}

	public void setCourseNameToTextField(String str) {
		if (courseNameTextField == null)
			return;
		if (str == null)
			courseNameTextField.setText("");
		else
			courseNameTextField.setText(str);
	}
	
	
	public boolean isQuestMajorComboBoxListData() {
		Integer id, ids[],id1, ids1[],id2, ids2[];
		String str;
		id = dataForm.getCollegeId();
		id1 = dataForm.getCollegeId1();
		id2 = dataForm.getCollegeId2();
		if (id == null && collegeId != null || id != null && collegeId == null || id!= null && !id.equals(collegeId) ||
		  id1 == null && collegeId1 != null || id1 != null && collegeId1 == null || id1!= null && !id1.equals(collegeId1) ||
		  id2 == null && collegeId2 != null || id2 != null && collegeId2 == null || id2!= null && !id2.equals(collegeId2) )
			return true;
		if (!areEqual(dataForm.getCollegeIds(),collegeIds) || !areEqual(dataForm.getCollegeId1s(),collegeId1s) ||
				!areEqual(dataForm.getCollegeId2s(),collegeId2s))
			return true;
		str = getBaseDataForm().getSecondType();
		if (str == null && secondType != null || str != null && secondType == null || str != null && !str.equals(secondType))
			return true;
		if (!areEqual(getBaseDataForm().getSecondTypes(),secondTypes))
			return true;
		return false;
	}
	public void updataCollegeId1ComboxBoxListData() {
		if (college1ComboBox != null) {
				collegeId = dataForm.getCollegeId();
				collegeIds = dataForm.getCollegeIds();
				college1ComboBox.setAddedDatas(this.getCollege1List());
		}
	}
	public void updataCollegeId2ComboxBoxListData() {
		if (college2ComboBox != null) {
			collegeId1 = dataForm.getCollegeId1();
			collegeId1s = dataForm.getCollegeId1s();
			college2ComboBox.setAddedDatas(this.getCollege2List());
		}
	}

	public void updataMajorComboxBoxListData() {
		if (majorComboBox != null) {
//			if (isQuestMajorComboBoxListData()) {
				collegeId = dataForm.getCollegeId();
				collegeId1 = dataForm.getCollegeId1();
				collegeId2 = dataForm.getCollegeId2();
				secondType = getBaseDataForm().getSecondType();
				collegeIds = dataForm.getCollegeIds();
				collegeId1s = dataForm.getCollegeId1s();
				collegeId2s = dataForm.getCollegeId2s();
				secondTypes = getBaseDataForm().getSecondTypes();
					majorComboBox.setAddedDatas(getMajorList());
//			}
		}
	}

	public boolean isQuestClassComboBoxListData() {
		Integer id;
		String str;

		id = getBaseDataForm().getMajorId();
		if (id == null && majorId != null || id != null && majorId == null)
			return true;
		if (id != null && majorId != null && !id.equals(majorId))
			return true;
		str = getBaseDataForm().getGrade();
		if (str == null && grade != null || str != null && grade == null)
			return true;
		if (str != null && grade != null && !str.equals(grade))
			return true;
		return false;
	}

	public void updataClassComboxBoxListData() {
		if (isQuestClassComboBoxListData()) {
			majorId = getBaseDataForm().getMajorId();
			grade = getBaseDataForm().getGrade();
			if (classComboBox != null)
				classComboBox.setAddedDatas(getClassList());
		}
	}
	
	public void secondTypeLevel1Changed() {
		getBaseDataForm().setSecondTypeLevel1(this.getSecondTypeLevel1ByComboBox());
		updataMajorComboxBoxListData();
	}

	public void secondTypeLevel2Changed() {
		getBaseDataForm().setSecondTypeLevel2(this.getSecondTypeLevel2ByComboBox());
		updataMajorComboxBoxListData();
	}

	public void secondTypeLevel3Changed() {
		getBaseDataForm().setSecondTypeLevel3(this.getSecondTypeLevel3ByComboBox());
		updataMajorComboxBoxListData();
	}

	public void updataCollegeComboxBoxListData() {
		if (collegeComboBox != null)
			collegeComboBox.setAddedDatas(getCollegeList());
	}

	public void secondTypeCodeChanged() {
		getBaseDataForm().setSecondTypeCode(this.getSecondTypeCodeByComboBox());
		getBaseDataForm().setSecondTypeCodes(this.getSecondTypeCodesByComboBox());
		updataMajorComboxBoxListData();
	}


	public void majorChanged() {
		getBaseDataForm().setMajorId(this.getMajorIdByComboBox());
		updataClassComboxBoxListData();
	}

	public void gradeChanged() {
		getBaseDataForm().setGrade(this.getGradeByComboBox());
		updataClassComboxBoxListData();
	}
	public void grade1Changed() {
	}
	public void classChanged() {

	}

	public void groupChanged() {

	}
	public void scholarTypeChanged() {

	}

	public void termChanged() {

	}
	public void processStateChanged() {

	}
	public void reviewModeChanged() {

	}

	public void studyStateChanged() {
		if (leaveCauseComboBox != null)
			leaveCauseComboBox.setAddedDatas(getLeaveCauseList());

	}
	public void LeaveCauseChanged() {

	}

	public void dataSourceChanged() {
	}
	public void graDegreeDateChanged() {
	}

	
	public void courseNumInputed(){
		String str = courseNumTextField.getText();
		if(str == null || str.length()== 0)
			return;
		List list = getCourseNoByCourseNumOrName(str, null);
		courseNoComboBox.setAddedDatas(list);		
	}
	public void courseNameInputed(){
		String str = courseNumTextField.getText();
		if(str == null || str.length()== 0)
			return;
		List list = getCourseNoByCourseNumOrName(null, str);
		courseNoComboBox.setAddedDatas(list);
	}
	public List getCourseNoByCourseNumOrName(String courseNum, String courseName){
		String taskType = getTeachTaskTypeByComboBox();
		if(taskType == null || taskType.length() == 0 || taskType.equals("-1"))
			taskType = UCommonQueryUtils.getTeachTaskType(parameters);
		RmiRequest request = new RmiRequest();
		RmiResponse respond = null;
		request.add("bType", UCommonQueryUtils.getBType(parameters));
		request.add("courseNum", courseNum);
		request.add("courseName", courseName);
		request.add("teachTaskType",taskType);
		request.add("beanName", UCommonQueryUtils.getCourseBeanName(parameters));
		request.setCmd("commonBaseDataQueryRequestCourseNoList");
		respond = UimsUtils.requestProcesser(request);
		List list = null;
		if (respond.getErrorMsg() == null) {
			list = (List) respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		return list;
	}
	public Component getSelfComponent(String comName) {
		String label; 
		 if (comName.equals("stuTypeLevel") || comName.equals("teaTypeLevel") || comName.equals("secondTypeLevel")) {
				return null;
			} else if (comName.equals("stuTypeCode") || comName.equals("teaTypeCode") || comName.equals("secondTypeCode")) {
				return secondTypeCodeComboBox.getAWTComponent();
			} else if (comName.equals("major")) {
				return majorComboBox.getAWTComponent();
			} else if (comName.equals("grade")) {
				return gradeComboBox.getAWTComponent();
			} else if (comName.equals("grade1")) {
				return grade1ComboBox.getAWTComponent();
			} else if (comName.equals("class")) {
				return classComboBox.getAWTComponent();
			} else if (comName.equals("loanStatus")) {
				return loanStatusComboBox.getAWTComponent();
			}else if (comName.equals("loanStatusName")) {
				return loanStatusNameComboBox.getAWTComponent();
			}else if (comName.equals("group")) {
				return groupComboBox.getAWTComponent();
			}else if (comName.equals("scholarType")) {
				return scholarTypeComboBox.getAWTComponent();
			} else if (comName.equals("term")) {
				return termComboBox.getAWTComponent();
			} else if (comName.equals("processState")) {
				return processStateComboBox.getAWTComponent();
			} else if (comName.equals("reviewMode")) {
				return reviewModeComboBox.getAWTComponent();
			} else if (comName.equals("studyState")) {
				return studyStateComboBox.getAWTComponent();
			} else if (comName.equals("leaveCause")) {
				return leaveCauseComboBox.getAWTComponent();
			} else if (comName.equals("perNum")) {
				return perNumTextField;
			} else if (comName.equals("perName")) {
				return perNameTextField;
			} else if (comName.equals("perEngName")) {
				return perEngNameTextField;
			} else if (comName.equals("perEnglishGivenName")) {
				return perEnglishGivenNameTextField;
			} else if (comName.equals("perEnglishFamilyName")) {
				return perEnglishFamilyNameTextField;
			}else if (comName.equals("perIdCard")) {
				return perIdCardTextField;
			} else if (comName.equals("mobilePhone")) {
				return mobilePhoneTextField;
			} else if (comName.equals("examNo")) {
				return examNoTextField;
			} else if (comName.equals("courseNum")) {
				return courseNumTextField;
			} else if (comName.equals("courseName")) {
				return courseNameTextField;
			}else if (comName.equals("courseNo")) {
				return courseNoComboBox.getAWTComponent();
			}else if (comName.equals("teachTaskType")) {
				return teachTaskTypeComboBox.getAWTComponent();
			}else if (comName.equals("conditionQuery")) {
				return conditionQueryButton;
			}else if (comName.equals("dataSource")) {
				return dataSourceComboBox.getAWTComponent();
			}
		 return null;
	}
	public UComboBox getProcessStateComboBox(){
			return (UComboBox)processStateComboBox;
	}
}
